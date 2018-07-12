package com.cesaas.android.counselor.order.activity.user.adapter;

import android.app.Activity;
import android.content.Context;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.bean.ResultDefaultRoleBean;
import com.cesaas.android.counselor.order.boss.bean.member.MemberServiceCategory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员-all
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberRoleAdapter extends BaseQuickAdapter<ResultDefaultRoleBean.DefaultRoleBean, BaseViewHolder> {

    private List<ResultDefaultRoleBean.DefaultRoleBean> mData;
    private Activity mActivity;
    private Context mContext;

    public MemberRoleAdapter(List<ResultDefaultRoleBean.DefaultRoleBean> mData, Activity activity, Context context) {
        super( R.layout.item_member_service_category,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ResultDefaultRoleBean.DefaultRoleBean item) {
        helper.setText(R.id.tv_category_name,item.getRoleName());
    }
}
