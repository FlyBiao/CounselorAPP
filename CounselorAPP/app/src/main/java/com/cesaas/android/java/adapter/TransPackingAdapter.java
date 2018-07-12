package com.cesaas.android.java.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.cesaas.android.java.ui.activity.SetTransPackingExpressActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 调拨单装箱列表
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class TransPackingAdapter extends BaseQuickAdapter<MoveDeliveryBoxListBean, BaseViewHolder> {

    private String no;
    private List<MoveDeliveryBoxListBean> mData;

    private Context mContext;
    private  Activity mActivity;
    private int type;

    public TransPackingAdapter(List<MoveDeliveryBoxListBean> mData,String no, Activity activity, Context ct,int type) {
        super( R.layout.item_trans_packing,mData);
        this.mData=mData;
        this.no=no;
        this.mActivity=activity;
        this.mContext=ct;
        this.type=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MoveDeliveryBoxListBean item) {
        helper.setText(R.id.tv_fly,R.string.fa_plane);
        helper.setTypeface(R.id.tv_fly, App.font);
        helper.setText(R.id.tv_set,R.string.fa_pencil);
        helper.setTypeface(R.id.tv_set, App.font);

        helper.setText(R.id.tv_boxNo,String.valueOf(item.getBoxNo()));
        helper.setText(R.id.tv_num,String.valueOf(item.getNum()));
        helper.setText(R.id.tv_create_packing_name,item.getCreateName());
        if(item.getExpressNo()!=null && !"".equals(item.getExpressNo())){
            helper.setVisible(R.id.tv_express,false);
            helper.setText(R.id.tv_expressName,item.getExpressName()+":");
            helper.setText(R.id.tv_expressNo,item.getExpressNo());
        }else{
            helper.setVisible(R.id.tv_express,true);
        }
        if(item.getExpressRemark()!=null && !"".equals(item.getExpressRemark())){
            helper.setText(R.id.tv_remark,item.getExpressRemark());
        }

        helper.setOnClickListener(R.id.tv_set_express, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putLong("boxId",item.getBoxId());
                bundle.putInt("boxNo",item.getBoxNo());
                bundle.putString("no",no);
                bundle.putInt("netType",type);
                Skip.mNextFroData(mActivity,SetTransPackingExpressActivity.class,bundle);
            }
        });
    }
}
