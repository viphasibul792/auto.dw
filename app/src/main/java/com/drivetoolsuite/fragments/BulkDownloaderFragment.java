package com.drivetoolsuite.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.drivetoolsuite.R;
import com.drivetoolsuite.util.DriveLinkParser;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Tab 2 - "Bulk Downloader".
 *
 * Native port of the HTML app's downloader tab:
 *  - user pastes a list of links (each line may contain any surrounding text)
 *  - "Start Downloads" extracts every http(s) URL with the original regex
 *  - the button is disabled and the status line is updated while downloads are
 *    triggered one by one with a 1.2 s delay (JS setTimeout chain)
 *  - each link is enqueued with the system DownloadManager, the native
 *    equivalent of the hidden-iframe download trigger used in the browser;
 *    the system shows its own download notifications
 *  - a final "✅ সবকটি ফাইলের ডাউনলোড প্রক্রিয়া সম্পন্ন হয়েছে!" status is shown
 *    and the button is re-enabled
 *  - the "টিপস" note box is reproduced with its bold/italic spans
 */
public class BulkDownloaderFragment extends Fragment {

    private static final String TAG = "BulkDownloader";

    /** Mirrors the JS setTimeout(triggerNextDownload, 1200). */
    private static final long TRIGGER_DELAY_MS = 1200L;

    private EditText linksInput;
    private TextView statusView;
    private MaterialButton downloadBtn;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<Runnable> pendingRunnables = new ArrayList<>();
    private boolean downloading = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bulk_downloader, container, false);

        linksInput = root.findViewById(R.id.linksInput);
        statusView = root.findViewById(R.id.status);
        downloadBtn = root.findViewById(R.id.downloadBtn);

        downloadBtn.setOnClickListener(v -> startBulkDownload());

        // Reproduce the HTML <p> with <b>Start Downloads</b> and the note box
        // with <b>টিপস:</b>, <i>"Allow multiple downloads"</i> and <b>Allow</b>.
        setRichText((TextView) root.findViewById(R.id.downloaderDescription),
                getString(R.string.downloader_description),
                getString(R.string.downloader_description_bold));

        TextView noteView = root.findViewById(R.id.noteText);
        SpannableString note = new SpannableString(getString(R.string.note));
        applyStyleSpan(note, getString(R.string.note_tips), Typeface.BOLD);
        applyStyleSpan(note, getString(R.string.note_allow_multiple), Typeface.ITALIC);
        // The second "Allow" (the button name) is bold. Skip the first one,
        // which sits inside the italic "Allow multiple downloads" phrase.
        String italicPart = getString(R.string.note_allow_multiple);
        int searchFrom = note.toString().indexOf(italicPart) + italicPart.length();
        int allowBtnStart = note.toString().indexOf(getString(R.string.note_allow_button), searchFrom);
        if (allowBtnStart >= 0) {
            note.setSpan(new StyleSpan(Typeface.BOLD), allowBtnStart,
                    allowBtnStart + getString(R.string.note_allow_button).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        noteView.setText(note);

        return root;
    }

    @Override
    public void onDestroyView() {
        // Stop any pending download-trigger chain if the fragment goes away.
        handler.removeCallbacksAndMessages(null);
        pendingRunnables.clear();
        downloading = false;
        super.onDestroyView();
    }

    /** JS function startBulkDownload(). */
    private void startBulkDownload() {
        if (downloading) {
            return;
        }

        String text = linksInput.getText().toString();
        List<String> urls = DriveLinkParser.extractUrls(text);

        if (urls.isEmpty()) {
            // JS: alert('টেক্সট বক্সে কোনো বৈধ ড্রাইভ লিঙ্ক পাওয়া যায়নি!')
            showAlert(getString(R.string.alert_no_urls));
            return;
        }

        downloading = true;
        downloadBtn.setEnabled(false); // JS: downloadBtn.disabled = true
        statusView.setText(String.format(Locale.US,
                getString(R.string.status_starting), urls.size()));

        triggerNext(urls, 0);
    }

    /**
     * JS: function triggerNextDownload() { ... setTimeout(triggerNextDownload, 1200); }
     * Triggers one download, updates the status line, then schedules the next.
     */
    private void triggerNext(List<String> urls, int index) {
        if (!isAdded() || !downloading) {
            return;
        }

        if (index >= urls.size()) {
            // JS: statusDiv.innerText = '✅ সবকটি ফাইলের ডাউনলোড প্রক্রিয়া সম্পন্ন হয়েছে!';
            statusView.setText(R.string.status_complete);
            downloadBtn.setEnabled(true);
            downloading = false;
            return;
        }

        enqueueDownload(urls.get(index), index + 1);
        // JS: statusDiv.innerText = `ডাউনলোড হচ্ছে: ${index} / ${matches.length}`;
        statusView.setText(String.format(Locale.US,
                getString(R.string.status_progress), index + 1, urls.size()));

        Runnable next = () -> triggerNext(urls, index + 1);
        pendingRunnables.add(next);
        handler.postDelayed(next, TRIGGER_DELAY_MS);
    }

    /**
     * Native equivalent of the JS hidden-iframe download trigger:
     * enqueue the URL with the system DownloadManager. The system download UI
     * handles the actual file transfer and shows notifications, so the app
     * needs no storage or network permissions.
     */
    private void enqueueDownload(String url, int number) {
        try {
            DownloadManager downloadManager =
                    (DownloadManager) requireContext().getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle(getString(R.string.download_notification_title, number));
            request.setDescription(url);
            request.setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setAllowedOverMetered(true);
            request.setAllowedOverRoaming(false);
            downloadManager.enqueue(request);
        } catch (Exception e) {
            Log.e(TAG, "Failed to enqueue download: " + url, e);
            Toast.makeText(requireContext(), R.string.download_enqueue_error,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void showAlert(String message) {
        new MaterialAlertDialogBuilder(requireContext())
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    /** Bolds a substring inside a TextView (HTML <b>…</b> equivalent). */
    private void setRichText(TextView view, String fullText, String boldPart) {
        SpannableString spannable = new SpannableString(fullText);
        applyStyleSpan(spannable, boldPart, Typeface.BOLD);
        view.setText(spannable);
    }

    /** Applies a bold/italic span to the first occurrence of {@code part}. */
    private void applyStyleSpan(SpannableString text, String part, int style) {
        int start = text.toString().indexOf(part);
        if (start >= 0) {
            text.setSpan(new StyleSpan(style), start, start + part.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
