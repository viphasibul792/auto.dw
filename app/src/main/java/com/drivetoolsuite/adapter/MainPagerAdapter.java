package com.drivetoolsuite.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.drivetoolsuite.fragments.BulkDownloaderFragment;
import com.drivetoolsuite.fragments.LinkCreatorFragment;

/**
 * Pager adapter hosting the two tabs of the app:
 * position 0 -> Link Creator, position 1 -> Bulk Downloader.
 */
public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new LinkCreatorFragment();
        }
        return new BulkDownloaderFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
