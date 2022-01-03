package com.market.dokan.adapter;

import androidx.fragment.app.Fragment;

public class Tabs {
    private int id;
    private String title;
    private Fragment fragment;

    public Tabs(int id, String title, Fragment fragment) {
        this.id = id;
        this.title = title;
        this.fragment = fragment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
