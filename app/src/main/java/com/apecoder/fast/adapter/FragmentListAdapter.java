package com.apecoder.fast.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.apecoder.fast.R;
import com.apecoder.fast.bean.FragmentData;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * description:
 * author: Allen
 * date: 2018/11/8 10:03
 */
public class FragmentListAdapter extends BaseQuickAdapter<FragmentData, BaseViewHolder> {
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
    }

}
