package com.cesaas.android.java.adapter;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.java.bean.inventory.InventoryGetGoodsListBean;
import com.cesaas.android.java.bean.inventory.InventoryGetSubListBean;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 盘点商品列表
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class InventoryGoodsListAdapter extends BaseItemDraggableAdapter<InventoryGetSubListBean, BaseViewHolder> {

    private List<InventoryGetSubListBean> mData;
    private Context mContext;
    private  Activity mActivity;

    public InventoryGoodsListAdapter(List<InventoryGetSubListBean> mData, Activity activity, Context ct) {
        super( R.layout.item_inventory_goods_list,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final InventoryGetSubListBean item) {
        helper.setText(R.id.tv_code_no,item.getBarcodeNo());
        helper.setText(R.id.tv_num,String.valueOf(item.getNum()));
        helper.setText(R.id.tv_color,item.getSkuValue1());

    }
}
