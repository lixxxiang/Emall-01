package com.example.emall_core.ui.recycler.test;

/**
 * Created by lixiang on 2018/2/23.
 */

public class App {
    private int mDrawable;
    private String mName;
    private float mRating;

    public App(String name, int drawable, float rating) {
        mName = name;
        mDrawable = drawable;
        mRating = rating;
    }

    public float getRating() {
        return mRating;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public String getName() {
        return mName;
    }
}
