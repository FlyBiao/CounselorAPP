package com.cesaas.android.counselor.order.boss.adapter.shop;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.GetShopMonthInfoBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.utils.MoveDeliveryStatusUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 获取店铺目标达成情况列表
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class GetShopMonthInfoAdapter extends BaseQuickAdapter<GetShopMonthInfoBean, BaseViewHolder> {

    private List<GetShopMonthInfoBean> mData;

    private Context mContext;
    private  Activity mActivity;

    public GetShopMonthInfoAdapter(List<GetShopMonthInfoBean> mData, Activity activity, Context ct) {
        super( R.layout.item_get_shop_month_info,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetShopMonthInfoBean item) {

        helper.setText(R.id.tv_OrganizationName,item.getOrganizationName());
        helper.setText(R.id.tv_shop_name,item.getShopName());

        helper.setText(R.id.tv_Goal,"￥"+item.getGoal());
        helper.setText(R.id.tv_SurpassGoal,"￥"+item.getSurpassGoal());

        helper.setText(R.id.tv_Sale,"￥"+item.getSale());
        helper.setText(R.id.tv_SaleReach,item.getSaleReach());

        helper.setText(R.id.tv_CardNum,String.valueOf(item.getCardNum()));
        helper.setText(R.id.tv_CardGoal,String.valueOf(item.getCardGoal()));

        if(item.getCardNum()>=item.getCardGoal()){
            helper.setTextColor(R.id.tv_finish,mContext.getResources().getColor(R.color.colorAccent2));
            helper.setTextColor(R.id.tv_CardNum,mContext.getResources().getColor(R.color.colorAccent2));
        }else{
            helper.setTextColor(R.id.tv_finish,mContext.getResources().getColor(R.color.red));
            helper.setTextColor(R.id.tv_CardNum,mContext.getResources().getColor(R.color.red));
        }
    }
}
