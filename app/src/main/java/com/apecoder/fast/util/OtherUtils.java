package com.apecoder.fast.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
}
