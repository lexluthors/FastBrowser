package com.apecoder.fast.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apecoder.fast.R;
import com.apecoder.fast.bean.EventTitle;
import com.apecoder.fast.bean.FragmentData;
import com.apecoder.fast.bean.OutLinkEvent;
import com.apecoder.fast.bean.TabEvent;
import com.apecoder.fast.fragment.WebFragment;
import com.apecoder.fast.util.OtherUtils;
import com.apecoder.fast.widget.DiyDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

import static com.apecoder.fast.fragment.WebFragment.ARG_PARAM3;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.houtui)
    ImageView houtui;
    @BindView(R.id.qianjin)
    ImageView qianjin;
    @BindView(R.id.setting)
    ImageView setting;
    @BindView(R.id.home)
    ImageView home;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.fragmentNum)
    TextView fragmentNum;

    //	@BindView(R.id.clear_edittext)
//	ClearEditText clearEdittext;
//	@BindView(R.id.toolbar)
//    Toolbar toolbar;
//	@BindView(R.id.activity_main)
//    LinearLayout activityMain;
    Bitmap bitmap;
    FragmentTransaction transaction;
    String currentFlag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        transaction = getSupportFragmentManager().beginTransaction();
        WebFragment webFragment = WebFragment.newInstance("主页", "");
        currentFlag = webFragment.getArguments().getString(ARG_PARAM3);
        transaction.add(R.id.container, webFragment);
        transaction.commitAllowingStateLoss();


        FragmentData fragmentData = new FragmentData();
        fragmentData.setTitle("主页");
        fragmentData.setFragment(webFragment);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_fast);
        fragmentData.setIcon(bitmap);
        fragmentDataList.add(fragmentData);

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                        }
                    }
                });
    }

    @OnClick({R.id.houtui, R.id.qianjin, R.id.setting, R.id.home, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.houtui:
                EventBus.getDefault().post(new TabEvent(1, currentFlag));
                break;
            case R.id.qianjin:
                EventBus.getDefault().post(new TabEvent(2, currentFlag));
                break;
            case R.id.setting:
                EventBus.getDefault().post(new TabEvent(3, currentFlag));
                break;
            case R.id.home:
                EventBus.getDefault().post(new TabEvent(4, currentFlag));
                break;
            case R.id.add:
                showDialog();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    RecyclerView recyclerView;
    List<FragmentData> fragmentDataList = new ArrayList<>();
    FragmentListAdapter adapter;
    DiyDialog dialog;

    private void addFragment() {
        adapter = new FragmentListAdapter(fragmentDataList);
        View view = getLayoutInflater().inflate(R.layout.dialog_list, null);
        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayout linearLayout = view.findViewById(R.id.add_fragment);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        dialog = OtherUtils.getMenuDialog(MainActivity.this, view);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = getSupportFragmentManager().beginTransaction();
                hideFragment();
                dialog.dismiss();
                FragmentData fragmentData = new FragmentData();
                WebFragment webFragment = WebFragment.newInstance("主页", "");
                fragmentData.setTitle("主页");
                fragmentData.setFragment(webFragment);
                fragmentData.setIcon(bitmap);
                fragmentDataList.add(fragmentData);
                adapter.notifyDataSetChanged();
                transaction.add(R.id.container, webFragment);
                transaction.show(webFragment);
                transaction.commit();
                fragmentNum.setText(String.valueOf(fragmentDataList.size()));
                currentFlag = webFragment.getArguments().getString(ARG_PARAM3);

            }
        });
        dialog.show();

        adapter.setOnItemClickListener((adapter, view1, position) -> {
            dialog.dismiss();
            transaction = getSupportFragmentManager().beginTransaction();
            hideFragment();
            transaction.show(fragmentDataList.get(position).getFragment());
            transaction.commit();
            currentFlag = fragmentDataList.get(position).getFragment().getArguments().getString(ARG_PARAM3);
        });
    }

    private void hideFragment() {
        for (int i = 0; i < fragmentDataList.size(); i++) {
            transaction.hide(fragmentDataList.get(i).getFragment());
//            if(currentFlag.equals(fragmentDataList.get(i).getFragment().getArguments().getString(ARG_PARAM3))){
//            }
        }
    }

    private void removeFragment(Fragment fragment, String timestamp) {
        transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragmentDataList.size(); i++) {
            String flag = fragmentDataList.get(i).getFragment().getArguments().getString(ARG_PARAM3);
            if (TextUtils.isEmpty(flag)) {
                continue;
            }
            if (flag.contentEquals(timestamp)) {
                transaction.remove(fragmentDataList.get(i).getFragment()).commit();
                fragmentDataList.remove(i);
                adapter.notifyDataSetChanged();
                if (fragmentDataList.size() > 0) {
                    //显示最后一个
                    transaction.show(fragmentDataList.get(fragmentDataList.size() - 1).getFragment());
                    currentFlag = fragmentDataList.get(fragmentDataList.size() - 1).getFragment().getArguments().getString(ARG_PARAM3);
                } else {
                    //新增一个fragment
                    dialog.dismiss();
                    dialog.cancel();
                    transaction = getSupportFragmentManager().beginTransaction();
                    FragmentData fragmentData = new FragmentData();
                    WebFragment webFragment = WebFragment.newInstance("主页", "");
                    fragmentData.setTitle("主页");
                    fragmentData.setFragment(webFragment);
                    fragmentData.setIcon(bitmap);
                    fragmentDataList.add(fragmentData);
                    transaction.add(R.id.container, webFragment);
                    transaction.show(webFragment);
                    transaction.commit();
                    currentFlag = webFragment.getArguments().getString(ARG_PARAM3);
                }
                adapter.notifyDataSetChanged();
                break;
            }
        }
        fragmentNum.setText(String.valueOf(fragmentDataList.size()));
    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        if (Build.VERSION.SDK_INT == 28) {
            try {
                ViewParent viewRootImpl = getWindow().getDecorView().getParent();
                Class viewRootImplClass = viewRootImpl.getClass();

                Field mAttachInfoField = viewRootImplClass.getDeclaredField("mAttachInfo");
                mAttachInfoField.setAccessible(true);
                Object mAttachInfo = mAttachInfoField.get(viewRootImpl);
                Class mAttachInfoClass = mAttachInfo.getClass();

                Field mHasWindowFocusField = mAttachInfoClass.getDeclaredField("mHasWindowFocus");
                mHasWindowFocusField.setAccessible(true);
                mHasWindowFocusField.set(mAttachInfo, true);
                boolean mHasWindowFocus = (boolean) mHasWindowFocusField.get(mAttachInfo);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return super.dispatchKeyEvent(event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //eventbus监听回调，主线程,更新列表title
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventTitle eventTitle) {
        //更新列表title
        for (int i = 0; i < fragmentDataList.size(); i++) {
            String flag = fragmentDataList.get(i).getFragment().getArguments().getString(ARG_PARAM3);
            assert flag != null;
            if (flag.equals(eventTitle.getFlag())) {
                fragmentDataList.get(i).setTitle(eventTitle.getTitle());
                if (null != adapter) {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }


    private void showDialog() {
        if (dialog == null) {
            addFragment();
        } else {
            dialog.show();
        }
    }

    class FragmentListAdapter extends BaseQuickAdapter<FragmentData, BaseViewHolder> {
        Context mContext;
        String mAttchment;
        String mDatetime;

        public FragmentListAdapter(List<FragmentData> mData) {
            super(R.layout.item_list, mData);
        }

        public void setAttachment(String attchment, Context context, String dateTime) {
            mContext = context;
            mAttchment = attchment;
            mDatetime = dateTime;
        }

        @Override
        protected void convert(BaseViewHolder helper, FragmentData mListBean) {
            ImageView mLogoGoods = (ImageView) helper.getView(R.id.iv);
            helper.setText(R.id.tv, mListBean.getTitle());
            mLogoGoods.setImageBitmap(mListBean.getIcon());
            ImageView close = helper.getView(R.id.close_fragment);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fragmentDataList.size() == 1) {
                        return;
                    }
                    removeFragment(mListBean.getFragment(), mListBean.getFragment().getArguments().getString(ARG_PARAM3));
//                    fragmentDataList.remove(helper.getAdapterPosition());
//                    notifyDataSetChanged();
                }
            });
        }

    }


    /**
     * 使用singleTask启动模式的Activity在系统中只会存在一个实例。
     * 如果这个实例已经存在，intent就会通过onNewIntent传递到这个Activity。
     * 否则新的Activity实例被创建。
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getDataFromBrowser(intent);
    }

    /**
     * 作为三方浏览器打开传过来的值
     * Scheme: https
     * host: www.jianshu.com
     * path: /p/1cbaf784c29c
     * url = scheme + "://" + host + path;
     */
    private void getDataFromBrowser(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            try {
                String scheme = data.getScheme();
                String host = data.getHost();
                String path = data.getPath();
                String text = "Scheme: " + scheme + "\n" + "host: " + host + "\n" + "path: " + path;
                Log.e("data啊噶是噶是大概", text);
                String url = scheme + "://" + host + path;
                EventBus.getDefault().post(new OutLinkEvent(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
