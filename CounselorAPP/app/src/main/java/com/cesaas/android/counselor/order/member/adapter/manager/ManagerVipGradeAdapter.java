package com.cesaas.android.counselor.order.member.adapter.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.manager.VipGradeNumsListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 店长管理店员-会员等级列表
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class ManagerVipGradeAdapter extends BaseQuickAdapter<VipGradeNumsListBean, BaseViewHolder> {

    private List<VipGradeNumsListBean> mData;
    private Activity mActivity;
    private Context mContext;
    private boolean isShow=false;
    private TextView tv_grade_bg;

    public ManagerVipGradeAdapter(List<VipGradeNumsListBean> mData, Activity activity, Context context) {
        super( R.layout.item_manager_vip_grade,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final VipGradeNumsListBean item) {
        helper.setText(R.id.tv_grade_name,item.getVipGradeName());
        tv_grade_bg=helper.getView(R.id.tv_grade_bg);
        if (item.getFontColor()!=null && !"".equals(item.getFontColor())){
            tv_grade_bg.setBackgroundColor(Color.parseColor(item.getFontColor()));
        }else{
            tv_grade_bg.setBackgroundColor(mContext.getResources().getColor(R.color.new_base_bg));
        }
    }
}
