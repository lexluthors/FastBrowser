package com.apecoder.fast.bean;

/**
 * Description:
 * Data：2019/4/29-16:19
 * Author: Allen
 */
public class ClickEvent {

    private int keyCode;
    private android.view.KeyEvent event;

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public android.view.KeyEvent getEvent() {
        return event;
    }

    public void setEvent(android.view.KeyEvent event) {
        this.event = event;
    }
}
