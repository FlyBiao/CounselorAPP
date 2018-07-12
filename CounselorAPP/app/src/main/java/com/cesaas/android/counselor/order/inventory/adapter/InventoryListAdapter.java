package com.cesaas.android.counselor.order.inventory.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.inventory.ui.InventoryDetailsActivity;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.java.bean.inventory.InventoryListBeanBean;
import com.cesaas.android.java.utils.MoveDeliveryStatusUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class InventoryListAdapter extends BaseQuickAdapter<InventoryListBeanBean, BaseViewHolder> {

    private List<InventoryListBeanBean> mData;

    private Context mContext;
    private  Activity mActivity;

    public InventoryListAdapter(List<InventoryListBeanBean> mData, Activity activity,Context ct) {
        super( R.layout.item_inventory,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final InventoryListBeanBean item) {

        helper.setText(R.id.tv_sort_desc,R.string.fa_caret_down);
        helper.setTypeface(R.id.tv_sort_desc, App.font);
        helper.setText(R.id.tv_shop_icon,R.string.business_school);
        helper.setTypeface(R.id.tv_shop_icon, App.font);
        helper.setText(R.id.tv_time,R.string.fa_clock);
        helper.setTypeface(R.id.tv_time, App.font);

        helper.setText(R.id.inventory_no,item.getNo());
        helper.setText(R.id.tv_shop_name,item.getShopName());
        helper.setText(R.id.tv_inventory_num,String.valueOf(item.getNum()));
        helper.setText(R.id.tv_difference_num,String.valueOf(item.getDifferenceNum()));
        helper.setText(R.id.tv_create_time, AbDateUtil.getDateYMDs(item.getCreateTime()));
        helper.setText(R.id.tv_cream_name,item.getCreateName());

        if(item.getType()==0){
            helper.setText(R.id.tv_inventory_type,"全盘");
        }else{
            helper.setText(R.id.tv_inventory_type,"抽盘");
        }

        if(item.getRemark()!=null && !"".equals(item.getRemark())){
            helper.setText(R.id.tv_remark,item.getRemark());
        }

        MoveDeliveryStatusUtils.getIneentoryStatus(helper,item.getStatus(),mContext);

        helper.setOnClickListener(R.id.tv_manager, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottonDialogUtils.showBottonDialog(mContext,mActivity,item);
            }
        });
    }
}
