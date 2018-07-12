package com.cesaas.android.counselor.order.member.adapter.member;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.results.GetListCounselorMonthGoalBean;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员业绩adapter
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberResultsAdapter extends BaseQuickAdapter<GetListCounselorMonthGoalBean, BaseViewHolder> {


    private List<GetListCounselorMonthGoalBean> mData;
    private Activity mActivity;
    private Context mContext;


    public MemberResultsAdapter(List<GetListCounselorMonthGoalBean> mData, Activity activity, Context context) {
        super( R.layout.item_results_member,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetListCounselorMonthGoalBean item) {

        helper.setText(R.id.tv_member_name,item.getName());
        helper.setTypeface(R.id.tv_user_icon, App.font);
        helper.setText(R.id.tv_user_icon,R.string.user);
        helper.setText(R.id.tv_card_target,item.getCardTarget()+"");
        helper.setTypeface(R.id.tv_sales_icon, App.font);
        helper.setText(R.id.tv_sales_icon,R.string.fa_dot_circle);
        helper.setText(R.id.tv_sales_target,"￥"+item.getSalesTarget());
        helper.setTypeface(R.id.tv_surpass_icon, App.font);
        helper.setText(R.id.tv_surpass_icon,R.string.fa_copyright);
        helper.setText(R.id.tv_surpass_target,"￥"+item.getSalesSurpass());

        if(item.getImage()!=null && !"".equals(item.getImage()) && !"NULL".equals(item.getImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_user_icon));
        }else{
            helper.setImageResource(R.id.ivw_user_icon,R.mipmap.ic_launcher);
        }
    }
}
