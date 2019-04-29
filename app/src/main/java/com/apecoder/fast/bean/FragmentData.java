package com.apecoder.fast.bean;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

/**
 * Description:
 * Data：2019/4/29-16:19
 * Author: Allen
 */
public class FragmentData {

    private Fragment fragment;
    private String title;
    private String url;
    private String imageUrl;
    private Bitmap icon;

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
