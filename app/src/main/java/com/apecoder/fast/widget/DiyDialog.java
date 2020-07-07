package com.apecoder.fast.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewParent;

import java.lang.reflect.Field;

public class DiyDialog extends Dialog {
    public DiyDialog(@NonNull Context context) {
        super(context);
    }

    public DiyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DiyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

//    @Override
//    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
//        if (Build.VERSION.SDK_INT == 26) {
//            try {
//                ViewParent viewRootImpl = getWindow().getDecorView().getParent();
//                Class viewRootImplClass = viewRootImpl.getClass();
//
//                Field mAttachInfoField = viewRootImplClass.getDeclaredField("mAttachInfo");
//                mAttachInfoField.setAccessible(true);
//                Object mAttachInfo = mAttachInfoField.get(viewRootImpl);
//                Class mAttachInfoClass = mAttachInfo.getClass();
//
//                Field mHasWindowFocusField = mAttachInfoClass.getDeclaredField("mHasWindowFocus");
//                mHasWindowFocusField.setAccessible(true);
//                mHasWindowFocusField.set(mAttachInfo, true);
//                boolean mHasWindowFocus = (boolean) mHasWindowFocusField.get(mAttachInfo);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return super.dispatchKeyEvent(event);
//    }
}
