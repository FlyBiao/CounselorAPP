package com.cesaas.android.java.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.java.bean.GetStyleNoByPidBean;
import com.cesaas.android.java.bean.inventory.InventoryGetDiffSubListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 根据pid获取款号列表
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class GetStyleNoByPidAdapter extends BaseQuickAdapter<InventoryGetDiffSubListBean, BaseViewHolder> {

    private List<InventoryGetDiffSubListBean> mData;

    private Context mContext;
    private  Activity mActivity;

    public GetStyleNoByPidAdapter(List<InventoryGetDiffSubListBean> mData, Activity activity, Context ct) {
        super(R.layout.item_get_style_no_pid,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final InventoryGetDiffSubListBean item) {

        helper.setText(R.id.tv_sku,item.getSkuValue1());
        helper.setText(R.id.tv_size,item.getSkuValue2());
        helper.setText(R.id.tv_stock,String.valueOf(item.getStockNum()));
        helper.setText(R.id.tv_zaitu,String.valueOf(item.getTransitNum()));
        helper.setText(R.id.tv_inventoryNum,String.valueOf(item.getInventoryNum()));
        helper.setText(R.id.tv_differenceNum,String.valueOf(item.getDifferenceNum()));

    }
}
