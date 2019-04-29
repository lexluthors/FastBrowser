package com.apecoder.fast.bean;

/**
 * Description:
 * Data：2019/4/29-16:19
 * Author: Allen
 */
public class TabEvent {
    public TabEvent(int event) {
        this.event = event;
    }

    //1,2,3,4,5 后退，前进，设置，主页
    private int event;

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }
}
