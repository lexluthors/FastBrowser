package com.apecoder.fast.bean;

/**
 * Description: 用于显示网页title
 * Data：2019/4/29-16:19
 * Author: Allen
 */
public class EventTitle {
    public EventTitle(String title, String flag) {
        this.title = title;
        this.flag = flag;
    }

    private String title;
    private String flag;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
