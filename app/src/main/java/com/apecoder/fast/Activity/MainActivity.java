package com.apecoder.fast.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apecoder.fast.R;
import com.apecoder.fast.bean.FragmentData;
import com.apecoder.fast.bean.TabEvent;
import com.apecoder.fast.fragment.WebFragment;
import com.apecoder.fast.util.OtherUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

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

    //	@BindView(R.id.clear_edittext)
//	ClearEditText clearEdittext;
//	@BindView(R.id.toolbar)
//    Toolbar toolbar;
//	@BindView(R.id.activity_main)
//    LinearLayout activityMain;
    Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        WebFragment webFragment = WebFragment.newInstance("主页", "");
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
                EventBus.getDefault().post(new TabEvent(1));
                break;
            case R.id.qianjin:
                EventBus.getDefault().post(new TabEvent(2));
                break;
            case R.id.setting:
                EventBus.getDefault().post(new TabEvent(3));
                break;
            case R.id.home:
                EventBus.getDefault().post(new TabEvent(4));
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
    Dialog dialog;

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
                FragmentData fragmentData = new FragmentData();
                WebFragment webFragment = WebFragment.newInstance("主页", "");
                fragmentData.setTitle("主页");
                fragmentData.setFragment(webFragment);
                fragmentData.setIcon(bitmap);
                fragmentDataList.add(fragmentData);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.show();
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
            helper.setText(R.id.tv,mListBean.getTitle());
            mLogoGoods.setImageBitmap(mListBean.getIcon());
            ImageView close = helper.getView(R.id.close_fragment);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fragmentDataList.size()==1){
                        return;
                    }
                    fragmentDataList.remove(helper.getLayoutPosition());
                    notifyDataSetChanged();
                }
            });
        }

    }

}
