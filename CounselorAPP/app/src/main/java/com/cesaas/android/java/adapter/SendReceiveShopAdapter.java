package com.cesaas.android.java.adapter;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.java.bean.ShopListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 发货 收货
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class SendReceiveShopAdapter extends BaseQuickAdapter<ShopListBean, BaseViewHolder> {

    private List<ShopListBean> mData;

    private Context mContext;
    private  Activity mActivity;

    public SendReceiveShopAdapter(List<ShopListBean> mData, Activity activity, Context ct) {
        super( R.layout.item_send_receive_shop,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopListBean item) {

        helper.setText(R.id.tv_org_name,item.getOrganizationName());
        helper.setText(R.id.tv_shop_name,item.getShopName());
    }
}
