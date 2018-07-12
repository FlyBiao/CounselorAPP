package com.cesaas.android.java.adapter.move;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 装箱列表
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class BaseBoxAdapter extends BaseQuickAdapter<MoveDeliveryBoxListBean, BaseViewHolder> {

    private List<MoveDeliveryBoxListBean> mData;
    private Context mContext;
    private  Activity mActivity;

    public BaseBoxAdapter(List<MoveDeliveryBoxListBean> mData,Context ct, Activity activity) {
        super( R.layout.item_base_box,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MoveDeliveryBoxListBean item) {
        helper.setText(R.id.tv_box_no,String.valueOf(item.getBoxNo()));
    }
}
