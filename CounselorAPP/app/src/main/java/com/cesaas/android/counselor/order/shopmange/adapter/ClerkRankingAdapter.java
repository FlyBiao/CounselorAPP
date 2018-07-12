package com.cesaas.android.counselor.order.shopmange.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.shopmange.bean.ClerkRankingBean;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 店员排名
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class ClerkRankingAdapter extends BaseQuickAdapter<ClerkRankingBean, BaseViewHolder> {

    private List<ClerkRankingBean> mData;
    private Activity mActivity;
    private Context mContext;
    private boolean isShow=false;

    public ClerkRankingAdapter(List<ClerkRankingBean> mData, Activity activity, Context context) {
        super( R.layout.item_clerk_ranking,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ClerkRankingBean item) {
        helper.setText(R.id.tv_clerk_name,item.getCounselorName());
        helper.setText(R.id.tv_discount,String.valueOf(item.getDiscount())+"折");
        helper.setText(R.id.tv_quantity,String.valueOf(item.getQuantity()));
        helper.setText(R.id.tv_payMent,"￥"+String.valueOf(item.getPayMent()));
        helper.setText(R.id.tv_top,String.valueOf(helper.getAdapterPosition()+1));
        helper.setText(R.id.tv_relative, item.getCompletion()+"%");
        helper.setProgress(R.id.pb_complete,(int)item.getCompletion());
        if(item.getCity()!=null && !"".equals(item.getCity())){
            helper.setText(R.id.tv_shop_name,item.getCity()+"-"+item.getShopName());
        }else{
            helper.setText(R.id.tv_shop_name,item.getShopName());
        }

        switch (helper.getAdapterPosition()+1){
            case 1:
                helper.setBackgroundRes(R.id.ll_top,R.mipmap.top1);
                break;
            case 2:
                helper.setBackgroundRes(R.id.ll_top,R.mipmap.top2);
                break;
            case 3:
                helper.setBackgroundRes(R.id.ll_top,R.mipmap.top3);
                break;
            default:
                helper.setBackgroundRes(R.id.ll_top,R.mipmap.top4);
                break;
        }

    }
}
