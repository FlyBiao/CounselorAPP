package com.cesaas.android.counselor.order.member.adapter.service.check;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.check.CheckServiceDetailsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberCheckCompleteServiceAdapter extends BaseQuickAdapter<CheckServiceDetailsBean, BaseViewHolder> {

    private List<CheckServiceDetailsBean> mData;

    public MemberCheckCompleteServiceAdapter(List<CheckServiceDetailsBean> mData) {
        super( R.layout.item_member_check_complete_service,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckServiceDetailsBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_CounselorName,"导购-"+item.getCounselorName());
        helper.setText(R.id.tv_crate_date,item.getDate());
        helper.setText(R.id.tv_service_date,item.getDate());
        helper.setText(R.id.tv_remark,item.getRemark());
        if(item.getServiceType()==1){
            helper.setText(R.id.tv_service_type,"电话");
        }else{
            helper.setText(R.id.tv_service_type,"其他");
        }

        if(item.getServiceType()==1){
            helper.setText(R.id.tv_service_result,"接听了");
        }else if(item.getServiceType()==2){
            helper.setText(R.id.tv_service_result,"没反馈");
        }else if(item.getServiceType()==3){
            helper.setText(R.id.tv_service_result,"拒绝沟通");
        }

        if(item.getGoShop()==1){
            helper.setText(R.id.tv_go_shop,"愿意来店");
        }else if(item.getGoShop()==2){
            helper.setText(R.id.tv_go_shop,"不愿意来店");
        }else{
            helper.setText(R.id.tv_go_shop,"不确定");
        }

        if(item.getStatus()==10){
            helper.setText(R.id.tv_service_status,"待处理");
        }else if(item.getStatus()==20){
            helper.setText(R.id.tv_service_status,"已完成");
        }else if(item.getStatus()==30){
            helper.setText(R.id.tv_service_status,"已关闭");
        }


        helper.setText(R.id.tv_user,R.string.user_o);
        helper.setTypeface(R.id.tv_user, App.font);
        helper.setText(R.id.tv_phone,R.string.fa_phone);
        helper.setTypeface(R.id.tv_phone, App.font);
        helper.setText(R.id.tv_times,R.string.fa_clock);
        helper.setTypeface(R.id.tv_times, App.font);
        helper.setText(R.id.tv_comments,R.string.fa_comments);
        helper.setTypeface(R.id.tv_comments, App.font);
    }
}
