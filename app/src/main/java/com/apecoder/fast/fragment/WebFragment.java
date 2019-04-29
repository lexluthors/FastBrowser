package com.apecoder.fast.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apecoder.fast.R;
import com.apecoder.fast.bean.ClickEvent;
import com.apecoder.fast.bean.TabEvent;
import com.apecoder.fast.config.BaseUrl;
import com.apecoder.fast.util.AppValidationMgr;
import com.apecoder.fast.util.ImeUtil;
import com.apecoder.fast.util.OtherUtils;
import com.apecoder.fast.widget.ClearEditText;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.github.ikidou.fragmentBackHandler.FragmentBackHandler;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WebFragment extends Fragment implements FragmentBackHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "url";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.clear_edittext)
    ClearEditText clearEdittext;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.flush)
    ImageView flush;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AgentWeb mAgentWeb;
    String url = "";
    Bitmap icon;

    public WebFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance(String param1, String param2) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.content_main2, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initData();
        return view;
    }

    private void initData() {
//        toolbar.setNavigationIcon(R.drawable.ic_webview_finish);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
//        toolbar.setNavigationIcon(R.drawable.ic_webview_finish);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
        clearEdittext.setOnEditorActionListener(new ClearEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_GO) {
                    if (TextUtils.isEmpty(clearEdittext.getText().toString())) {
                        Toast.makeText(getActivity(), "请输入内容", Toast.LENGTH_SHORT).show();
                        return false;
                    }
//                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
//                    intent.putExtra("key", clearEdittext.getText().toString());
//                    startActivity(intent);
                    url = clearEdittext.getText().toString();
                    if (!AppValidationMgr.isURL(url)) {
                        url = BaseUrl.BASE_BAIDU_SEARCH_URL + clearEdittext.getText().toString();
                        Log.e("不是网址>>>>", url);
                    } else {
                        if (url.contains("http://") || url.contains("https://")) {
                        } else {
                            url = "http://" + url;
                        }
                    }
                    Log.e("是网址>>>>", url);

                    ImeUtil.hideSoftKeyboard(titleText);
                    clearEdittext.setVisibility(View.GONE);
                    titleText.setVisibility(View.VISIBLE);

                    if (mAgentWeb == null) {
                        mAgentWeb = AgentWeb.with(getActivity())
                                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                                .useDefaultIndicator()
                                .setWebViewClient(mWebViewClient)
                                .setWebChromeClient(mWebChromeClient)
                                .createAgentWeb()
                                .ready()
                                .go(url);
                    } else {
                        mAgentWeb.getWebCreator().getWebView().loadUrl(url);
                    }
                }
                return false;
            }
        });

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url2, Bitmap favicon) {
            //do you  work
            flush_flag = false;
            flush.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_close24));
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("", "完成了" + url);
            flush.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_flush242));
            flush_flag = true;
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
//            clearEdittext.setText(title);
            titleText.setText(title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon2) {
            super.onReceivedIcon(view, icon2);
            icon = icon2;
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_webview, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.menu_flush:
                mAgentWeb.getWebCreator().getWebView().reload();
                break;
            case R.id.menu_share:
                Intent localIntent = new Intent("android.intent.action.SEND");
                localIntent.setType("text/plain");
                localIntent.putExtra("android.intent.extra.TEXT", url);
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(localIntent);
                break;
            case R.id.menu_copy_link:
                if (OtherUtils.copyText(getActivity(), url)) {
                    Snackbar.make(container, "链接复制成功", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.menu_open_with:
                OtherUtils.openWithBrowser(getActivity(), url);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ImeUtil.showSoftKeyboard(clearEdittext);
            }
        }, 100);
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
    }

    boolean flush_flag = true;//是否正在显示刷新按钮

    @OnClick({R.id.title_text, R.id.flush})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_text:
                titleText.setVisibility(View.GONE);
                clearEdittext.setVisibility(View.VISIBLE);
                clearEdittext.requestFocus();
                clearEdittext.setFocusable(true);
                clearEdittext.setSelection(clearEdittext.length());
                ImeUtil.showSoftKeyboard(clearEdittext);
                break;
            case R.id.flush:
                if(flush_flag){
                    if(mAgentWeb!=null){
                        flush.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_close24));
                        mAgentWeb.getWebCreator().getWebView().reload();
                    }
                }else {
                    //停止加载
                    if(mAgentWeb!=null){
                        mAgentWeb.getWebCreator().getWebView().stopLoading();
                    }
                    flush.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.ic_flush242));
                }
                break;
        }
    }

    //eventbus监听回调，主线程
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TabEvent tabEvent) {
        Log.e("外面","ddddddd");
        if(!isHidden()){
            if(mAgentWeb==null){
                return;
            }
            WebView webView = mAgentWeb.getWebCreator().getWebView();
            Log.e("","bbbbbbbbbbb");
            //可见的
            switch (tabEvent.getEvent()){
                case 1:
                    //后退
                    if(webView.canGoBack()){
                        webView.goBack();
                    }
                    break;
                case 2:
                    if(webView.canGoForward()){
                        webView.goForward();
                    }
                    break;
                case 3:
                    break;
                case 4:
                    //主页
                    webView.loadUrl("https://www.baidu.com");
                    break;
                case 5:
                    break;
            }
        }
    }

    //监听返回键，处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKeyEvent(ClickEvent clickEvent) {
        if(!isHidden()){
            if (mAgentWeb.handleKeyEvent(clickEvent.getKeyCode(), clickEvent.getEvent())) {
                return;
            }
        }
    }

    @Override
    public boolean onBackPressed() {
        if(mAgentWeb==null){
            return false;
        }
        WebView webView = mAgentWeb.getWebCreator().getWebView();
        if (webView.canGoBack()) {
            //外理返回键
            webView.goBack();
            return true;
        } else {
            // 如果不包含子Fragment
            // 或子Fragment没有外理back需求
            // 可如直接 return false;
            // 注：如果Fragment/Activity 中可以使用ViewPager 代替 this
            //
            return BackHandlerHelper.handleBackPress(this);
        }
    }

    public String getTitle(){
        if(titleText==null){
            return "主页";
        }
        return titleText.getText().toString();
    }
    public Bitmap getIcon(){
        return icon;
    }
}
