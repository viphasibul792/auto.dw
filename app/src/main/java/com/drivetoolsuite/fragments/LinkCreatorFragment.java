package com.drivetoolsuite.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.drivetoolsuite.R;
import com.drivetoolsuite.util.DriveLinkParser;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;

/**
 * Tab 1 - "Link Creator".
 *
 * Native port of the HTML app's creator tab:
 *  - user pastes any text / messy links into the input box
 *  - "Generate Links" extracts Google Drive file IDs with the original regex
 *    and writes "Lecture:NN + converted download link" lines into the output box
 *  - "Copy Links" copies the generated text to the system clipboard
 *  - an alert is shown when no valid Drive link is found (JS alert() equivalent)
 */
public class LinkCreatorFragment extends Fragment {

    private EditText creatorInput;
    private EditText creatorOutput;
    private View copyContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_link_creator, container, false);

        creatorInput = root.findViewById(R.id.creatorInput);
        creatorOutput = root.findViewById(R.id.creatorOutput);
        copyContainer = root.findViewById(R.id.copyContainer);

        root.findViewById(R.id.generateLinksBtn).setOnClickListener(v -> generateDownloadLinks());
        root.findViewById(R.id.copyLinksBtn).setOnClickListener(v -> copyOutputText());

        return root;
    }

    /** JS function generateDownloadLinks(). */
    private void generateDownloadLinks() {
        String inputText = creatorInput.getText().toString();
        List<String> fileIds = DriveLinkParser.extractDriveFileIds(inputText);

        if (fileIds.isEmpty()) {
            // JS: alert('কোনো বৈধ গুগল ড্রাইভ লিঙ্ক বা File ID পাওয়া যায়নি!')
            showAlert(getString(R.string.alert_no_drive_links));
            creatorOutput.setText("");
            copyContainer.setVisibility(View.GONE);
            return;
        }

        // JS: extractedIds.forEach((fileId, index) => {
        //        formattedOutput += `Lecture:${String(index+1).padStart(2,'0')}
        //          \nhttps://drive.google.com/u/0/uc?id=${fileId}&export=download\n\n`; })
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < fileIds.size(); i++) {
            formatted.append(String.format(Locale.US,
                    "Lecture:%02d\nhttps://drive.google.com/u/0/uc?id=%s&export=download\n\n",
                    i + 1, fileIds.get(i)));
        }

        creatorOutput.setText(formatted.toString().trim()); // JS: .trim()
        copyContainer.setVisibility(View.VISIBLE);          // JS: display = 'block'
    }

    /** JS function copyOutputText() -> ClipboardManager. */
    private void copyOutputText() {
        String output = creatorOutput.getText().toString();
        if (output.isEmpty()) {
            return;
        }
        ClipboardManager clipboard =
                (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(getString(R.string.app_name), output));
        // JS: alert('লিঙ্কগুলো কপি করা হয়েছে!')
        showAlert(getString(R.string.alert_links_copied));
    }

    private void showAlert(String message) {
        new MaterialAlertDialogBuilder(requireContext())
                .setMessage(message)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
