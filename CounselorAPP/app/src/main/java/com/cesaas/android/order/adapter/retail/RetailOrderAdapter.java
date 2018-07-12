package com.cesaas.android.order.adapter.retail;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.order.bean.retail.RetailOrderBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员-all
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class RetailOrderAdapter extends BaseQuickAdapter<RetailOrderBean, BaseViewHolder> {

    private List<RetailOrderBean> mData;
    private Activity mActivity;
    private Context mContext;
    private boolean isShow=false;

    public RetailOrderAdapter(List<RetailOrderBean> mData, Activity activity, Context context) {
        super( R.layout.item_retail_order,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final RetailOrderBean item) {
        helper.setText(R.id.tv_vip_name,item.getSyncCode());
        helper.setText(R.id.tv_quantity,String.valueOf(item.getQuantity()));
        helper.setText(R.id.tv_payment,"￥"+item.getPayment());


        if(item.getRetailCheck()==1 && item.getRetailSure()==1){

        }

        if(item.getCreateTime()!=null && !"".equals(item.getCreateTime())){
            helper.setText(R.id.tv_date,AbDateUtil.getDateYMDs(item.getCreateTime()));
        }
        if(item.getActivityName()!=null && !"".equals(item.getActivityName())){
            helper.setText(R.id.tv_activity_name,item.getActivityName());
        }else{
            helper.setText(R.id.tv_activity_name,"没有使用优惠");
        }
        if(item.getMobile()!=null && !"".equals(item.getMobile())){
            helper.setText(R.id.tv_vip_name,item.getVipName());
            helper.setText(R.id.tv_mobile,item.getMobile());
        }else{
            helper.setText(R.id.tv_vip_name,"无会员");
        }
        helper.setText(R.id.tv_bottom,R.string.fa_sort_desc);
        helper.setTypeface(R.id.tv_bottom, App.font);
        helper.setText(R.id.tv_retail_user_icon,R.string.user);
        helper.setTypeface(R.id.tv_retail_user_icon, App.font);
        helper.setText(R.id.tv_circle,R.string.fa_circle);
        helper.setTypeface(R.id.tv_circle, App.font);
        helper.setText(R.id.tv_clock,R.string.lately_contact);
        helper.setTypeface(R.id.tv_clock, App.font);

        helper.setOnClickListener(R.id.ll_show, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow==false){
                    isShow=true;
                    helper.setVisible(R.id.ll_retail_info,true);
                    helper.setText(R.id.tv_bottom,R.string.fa_sort_up);
                }else{
                    isShow=false;
                    helper.setVisible(R.id.ll_retail_info,false);
                    helper.setText(R.id.tv_bottom,R.string.fa_sort_desc);
                }
            }
        });

    }
}
