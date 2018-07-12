package com.cesaas.android.counselor.order.boss.adapter.member;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.member.MemberServiceCategory;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
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
 * Description 会员-all
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberServiceCategoryAdapter extends BaseQuickAdapter<MemberServiceCategory, BaseViewHolder> {

    private List<MemberServiceCategory> mData;
    private Activity mActivity;
    private Context mContext;

    public MemberServiceCategoryAdapter(List<MemberServiceCategory> mData, Activity activity, Context context) {
        super( R.layout.item_member_service_category,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MemberServiceCategory item) {
        helper.setText(R.id.tv_category_name,item.getCategoryName());
    }
}
