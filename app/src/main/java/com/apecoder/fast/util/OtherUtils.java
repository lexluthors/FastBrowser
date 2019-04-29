package com.apecoder.fast.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.apecoder.fast.R;

/**
 * Created by Tony on 2017/9/5.
 */

public class OtherUtils {
    /**
     * 复制文字到剪切板
     * @param context 上下文
     * @param copyText 要复制的文本
     */
    public static boolean copyText(Context context, String copyText) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newRawUri("gankUrl", Uri.parse(copyText));
        cmb.setPrimaryClip(data);
        return true;
    }

    /**
     * 使用浏览器打开
     *
     * @param context 上下文
     * @param uriStr  链接地址
     */
    public static void openWithBrowser(Context context, String uriStr) {
        Uri uri = Uri.parse(uriStr);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    public static Dialog getMenuDialog(Activity context, View view) {

        final Dialog dialog = new Dialog(context, R.style.MenuDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        int screenW = getScreenWidth(context);
        lp.width = screenW;
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        return dialog;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
}
