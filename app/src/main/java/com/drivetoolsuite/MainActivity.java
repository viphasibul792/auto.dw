package com.drivetoolsuite;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.drivetoolsuite.adapter.MainPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Main screen of the Google Drive Tool Suite.
 *
 * Recreates the HTML app's layout: a white rounded "container" card on a light
 * grey page background, with the app title, the two navigation tabs
 * (Link Creator / Bulk Downloader) and the tab content hosted in a ViewPager2.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 pager = findViewById(R.id.pager);
        TabLayout tabs = findViewById(R.id.tabs);

        pager.setAdapter(new MainPagerAdapter(this));

        // Wire the two HTML "tab buttons" to the native tab strip.
        new TabLayoutMediator(tabs, pager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.tab_link_creator);
            } else {
                tab.setText(R.string.tab_bulk_downloader);
            }
        }).attach();
    }
}
