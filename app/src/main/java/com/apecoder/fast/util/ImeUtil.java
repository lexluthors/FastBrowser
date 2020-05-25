package com.apecoder.fast.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Tony on 2017/9/5.
 */

public class ImeUtil
{
	/**
	 * 显示和隐藏软键盘 View ： EditText、TextView isShow : true = show , false = hide
	 *
	 * @param context
	 * @param view
	 * @param isShow
	 */
	public static void popSoftKeyboard(Context context, View view, boolean isShow)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (isShow)
		{
			view.requestFocus();
			imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		}
		else
		{
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/**
	 * 显示软键盘
	 *
	 * @param view
	 */
	public static void showSoftKeyboard(View view)
	{
		Context context = view.getContext();
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		view.requestFocus();
		imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	/**
	 * 隐藏软键盘
	 *
	 * @param view
	 */
	public static void hideSoftKeyboard(View view)
	{
		Context context = view.getContext();
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
