package com.apecoder.fast.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tony on 2017/9/18.
 */

public class ThreadUtils {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private static ExecutorService sExecutorService = Executors.newCachedThreadPool();

    //Runnable：任务，必须依附于某一个线程
    //Thread:线程，线程用来执行任务
    //Process：进程
    //保证r这个任务一定是在主线程中执行
    public static void runOnUiThread(Runnable r){

        if(Looper.myLooper() == Looper.getMainLooper()) {
            //主线程
            //new Thread(r).start(); 一旦new了Thread就一定是子线程
            r.run();
        } else {
            //new Thread(r).start()
            sHandler.post(r);
        }

    }

    //保证r一定在子线程中得到执行
    public static void runOnSubThread(Runnable r) {
//        new Thread(r).start();
        //线程池的概念，线程池里面装的是线程，使用线程池可以达到线程的复用，提高性能
        sExecutorService.submit(r);//将r丢到线程池中，线程池中的线程就会来执行r这个任务
    }

}
