package com.cesaas.android.java.adapter.review;

import android.app.Activity;
import android.content.Context;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.java.bean.review.Title;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * Author FGB
 * Description 评价Item
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class ReViewItemAdapter extends BaseQuickAdapter<Title, BaseViewHolder> {

    private List<Title> mData;
    private Context mContext;
    private  Activity mActivity;

    public ReViewItemAdapter(List<Title> mData, Context ct, Activity activity) {
        super( R.layout.item_review_item,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Title item) {
        helper.setText(R.id.tv_review_c1,R.string.fa_check_circle_o);
        helper.setTypeface(R.id.tv_review_c1, App.font);
        helper.setText(R.id.tv_title,item.getTitle());
    }
}
