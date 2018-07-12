package com.cesaas.android.counselor.order.member.adapter.service.apply;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.apply.MemberApplyListBean;
import com.cesaas.android.counselor.order.member.net.service.AgreeNet;
import com.cesaas.android.counselor.order.member.net.service.RejectNet;
import com.cesaas.android.counselor.order.member.service.MemberUpdateDetailActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 手机和生日修改申请列表-已拒绝
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberRejectInfoAdapter extends BaseQuickAdapter<MemberApplyListBean, BaseViewHolder> {

    private Activity mActivity;
    private List<MemberApplyListBean> mData;

    public MemberRejectInfoAdapter(List<MemberApplyListBean> mData, Activity activity) {
        super( R.layout.item_member_reject_info,mData);
        this.mData=mData;
        this.mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MemberApplyListBean item) {
        helper.setText(R.id.tv_name,item.getUserName());
        helper.setText(R.id.tv_shop_name,item.getShopName());
        helper.setText(R.id.tv_apply_name,item.getApplyName());
        helper.setText(R.id.tv_apply_remark,item.getApplyRemark());
        helper.setText(R.id.tv_sure_name,item.getConfirmName());
        helper.setText(R.id.tv_sure_remark,item.getConfirmRemark());

        if(item.getConfirmDate()!=null && !"".equals(item.getConfirmDate())){
            helper.setText(R.id.tv_sure_date,item.getConfirmDate()+"("+AbDateUtil.formats(item.getConfirmDate())+")");
        }

        if(item.getApplyDate()!=null && !"".equals(item.getApplyDate())){
            helper.setText(R.id.tv_create_date, item.getApplyDate()+"("+AbDateUtil.formats(item.getApplyDate())+")");
        }

        if(item.getType()==1){
            helper.setVisible(R.id.ll_mobile,true);
            helper.setText(R.id.tv_service_title,"更改手机号");
            helper.setText(R.id.tv_InfoOld,item.getInfoOld());
            helper.setText(R.id.tv_InfoNew,item.getInfoNew());
        }else{
            helper.setVisible(R.id.ll_birth,true);
            helper.setText(R.id.tv_service_title,"更改生日");
            if(item.getInfoOld()!=null && !"".equals(item.getInfoOld())){
                helper.setText(R.id.tv_birth_InfoOld,AbDateUtil.getDateYMDs(item.getInfoOld()));
            }
            if(item.getInfoNew()!=null  && !"".equals(item.getInfoNew())){
                helper.setText(R.id.tv_birth_InfoNew,AbDateUtil.getDateYMDs(item.getInfoNew()));
            }
        }
        if(item.getStatus()==10){
            helper.setText(R.id.tv_status,"待审核");
        }else if(item.getStatus()==20){
            helper.setText(R.id.tv_status,"已同意");
        }else if(item.getStatus()==30){
            helper.setText(R.id.tv_status,"已拒绝");
        }else{
            helper.setText(R.id.tv_status,"已超时");
        }
        if(item.getUserImage()!=null && !"".equals(item.getUserImage()) && !"NULL".equals(item.getUserImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getUserImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_user_icon));
        }else{
            helper.setImageResource(R.id.ivw_user_icon,R.mipmap.ic_launcher);
        }

//        helper.setOnClickListener(R.id.ll_info_details, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle b=new Bundle();
//                Skip.mNext(mActivity,MemberUpdateDetailActivity.class);
//            }
//        });
    }
}
