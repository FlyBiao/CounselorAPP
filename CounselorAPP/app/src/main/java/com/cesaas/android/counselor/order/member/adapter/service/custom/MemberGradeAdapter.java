package com.cesaas.android.counselor.order.member.adapter.service.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.member.bean.service.custom.CustomServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultVipGradeBean;
import com.cesaas.android.counselor.order.member.net.service.ColseServiceNet;
import com.cesaas.android.counselor.order.member.net.service.ServiceRemarkNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员等级
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberGradeAdapter extends BaseQuickAdapter<ResultVipGradeBean.VipGradeBean, BaseViewHolder> {

    private List<ResultVipGradeBean.VipGradeBean> mData;
    private Activity mActivity;
    private Context mContext;

    public MemberGradeAdapter(List<ResultVipGradeBean.VipGradeBean> mData, Activity activity, Context context) {
        super( R.layout.item_member_vip,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ResultVipGradeBean.VipGradeBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
    }
}
