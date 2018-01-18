package com.graction.developer.lumi.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Graction06 on 2018-01-18.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<TabItem> items;
    private OnTabSelected onTabSelected;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    public FragmentAdapter(FragmentManager fm, OnTabSelected onTabSelected) {
        super(fm);
        items = new ArrayList<>();
        this.onTabSelected = onTabSelected;
    }

    public FragmentAdapter(FragmentManager fm, ArrayList<TabItem> items) {
        super(fm);
        this.items = items;
    }

    public FragmentAdapter(FragmentManager fm, ArrayList<TabItem> items, OnTabSelected onTabSelected) {
        super(fm);
        this.items = items;
        this.onTabSelected = onTabSelected;
    }

    public void setOnTabSelected(OnTabSelected onTabSelected) {
        this.onTabSelected = onTabSelected;
    }

    public void setItems(ArrayList<TabItem> items) {
        this.items = items;
    }

    public void addItem(TabItem tabItem) {
        this.items.add(tabItem);
    }

    @Override
    public Fragment getItem(int position) {
        TabItem item = items.get(position);
        if (onTabSelected != null)
            onTabSelected.onSelected(position, item);
        return item.getFragment();
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    public class TabItem {
        private Fragment fragment;
        private int resIcon;

        public TabItem(Fragment fragment, int resIcon) {
            this.fragment = fragment;
            this.resIcon = resIcon;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;
        }

        public int getResIcon() {
            return resIcon;
        }

        public void setResIcon(int resIcon) {
            this.resIcon = resIcon;
        }
    }

    public interface OnTabSelected {
        void onSelected(int index, TabItem item);
    }
}
