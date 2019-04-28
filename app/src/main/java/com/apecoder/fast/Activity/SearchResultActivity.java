package com.apecoder.fast.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apecoder.fast.R;
import com.apecoder.fast.config.BaseUrl;
import com.apecoder.fast.util.AppValidationMgr;
import com.apecoder.fast.util.OtherUtils;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends AppCompatActivity
{

	@BindView(R.id.toolbar)
    Toolbar toolbar;
	@BindView(R.id.activity_search_result)
    LinearLayout activitySearchResult;

    AgentWeb mAgentWeb;
    String url;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		 ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_webview_finish);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        url = getIntent().getStringExtra("key");
        if(!AppValidationMgr.isURL(url)){
            url = BaseUrl.BASE_BAIDU_SEARCH_URL+getIntent().getStringExtra("key");
            Log.e("不是网址>>>>",url);
        }else{
            if(url.contains("http://")||url.contains("https://")){
            }else{
                url = "http://"+url;
            }

        }
        Log.e("是网址>>>>",url);
//        mAgentWeb = AgentWeb.with(this)//传入Activity
//                .setAgentWebParent(activitySearchResult, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
//                .useDefaultIndicator()// 使用默认进度条
////                .setIndicatorColorWithHeight(ContextCompat.getColor(this,R.color.progress_color),3)
////                .defaultProgressBarColor() // 使用默认进度条颜色
//                .set(new ChromeClientCallbackManager.ReceivedTitleCallback() {
//                    @Override
//                    public void onReceivedTitle(WebView view, String title) {
//                        toolbar.setTitle(title);
//                    }
//                }) //设置 Web 页面的 title 回调
//                .setWebViewClient(mWebViewClient)
//                .createAgentWeb()
//                .ready()
//                .go(url);

        mAgentWeb =  AgentWeb.with(this)
                .setAgentWebParent(activitySearchResult, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
//        mAgentWeb.getAgentWebSettings().getWebSettings().setSupportZoom(true);
//        addBGChild((FrameLayout) mAgentWeb.getWebCreator().getGroup()); // 得到 AgentWeb 最底层的控件

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(SearchResultActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
    //WebViewClient
    private WebViewClient mWebViewClient=new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String murl, Bitmap favicon) {
            //do you  work
            url=murl;
            Log.e("onPageStarted>>>>","这是新的网址"+url );
        }

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webview,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_share:
                Intent localIntent = new Intent("android.intent.action.SEND");
                localIntent.setType("text/plain");
                localIntent.putExtra("android.intent.extra.TEXT", url);
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(localIntent);
                break;
            case R.id.menu_copy_link:
                if (OtherUtils.copyText(this,url)) {
                    Snackbar.make(activitySearchResult, "链接复制成功", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.menu_open_with:
                OtherUtils.openWithBrowser(this, url);
                break;
        }
        return true;
    }


    protected void addBGChild(FrameLayout frameLayout) {

        TextView mTextView=new TextView(frameLayout.getContext());
        mTextView.setText("技术由 AgentWeb 提供");
        mTextView.setTextSize(16);
        mTextView.setTextColor(Color.parseColor("#727779"));
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"));
        FrameLayout.LayoutParams mFlp=new FrameLayout.LayoutParams(-2,-2);
        mFlp.gravity= Gravity.CENTER_HORIZONTAL;
        final float scale = frameLayout.getContext().getResources().getDisplayMetrics().density;
        mFlp.topMargin= (int) (15 * scale + 0.5f);
        frameLayout.addView(mTextView,0,mFlp);
    }
}
