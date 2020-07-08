package com.apecoder.fast.bean;

/**
 * Description: 用于显示网页title
 * Data：2019/4/29-16:19
 * Author: Allen
 */
public class OutLinkEvent {
    public OutLinkEvent(String url ) {
        this.url = url;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
