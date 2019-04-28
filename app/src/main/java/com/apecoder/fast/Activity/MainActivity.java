package com.apecoder.fast.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apecoder.fast.R;
import com.apecoder.fast.util.ImeUtil;
import com.apecoder.fast.widget.ClearEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{

	@BindView(R.id.clear_edittext)
	ClearEditText clearEdittext;
	@BindView(R.id.toolbar)
    Toolbar toolbar;
	@BindView(R.id.activity_main)
    LinearLayout activityMain;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		// AgentWeb mAgentWeb = AgentWeb.with(this)//传入Activity
		// .setAgentWebParent(activity_main, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件
		// ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
		// .useDefaultIndicator()// 使用默认进度条
		// .setIndicatorColorWithHeight(ContextCompat.getColor(this,R.color.progress_color),2)
		//// .defaultProgressBarColor() // 使用默认进度条颜色
		// .setReceivedTitleCallback(new ChromeClientCallbackManager.ReceivedTitleCallback() {
		// @Override
		// public void onReceivedTitle(WebView view, String title) {
		//
		// }
		// }) //设置 Web 页面的 title 回调
		// .createAgentWeb()//
		// .ready()
		// .go("http://www.jd.com");

		clearEdittext.setOnEditorActionListener(new ClearEditText.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent)
			{
				if (i == EditorInfo.IME_ACTION_GO)
				{
					if (TextUtils.isEmpty(clearEdittext.getText().toString()))
					{
						Toast.makeText(MainActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
						return false;
					}
					Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
					intent.putExtra("key", clearEdittext.getText().toString());
					startActivity(intent);
				}
				return false;
			}
		});


		Intent i_getvalue = getIntent();
		String action = i_getvalue.getAction();

		if(Intent.ACTION_VIEW.equals(action)){
			Uri uri = i_getvalue.getData();
			if(uri != null){
//				String name = uri.getQueryParameter("name");
//				String age= uri.getQueryParameter("age");
				Log.e("uri.toString》》》",uri.toString());
			}
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				ImeUtil.showSoftKeyboard(clearEdittext);
			}
		}, 100);
	}
}
