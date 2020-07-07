package com.apecoder.fast;


import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * APPLICATION
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setUpThreePlatformParams();
    }

    /**
     * description: 实例化第三方库
     * author: liujie
     * date: 2017/12/27 15:19
     */
    private void setUpThreePlatformParams() {
        //解决android N（>=24）系统以上分享 路径为file://时的 android.os.FileUriExposedException异常
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }


    public static App getInstance() {
        return instance;
    }

}
