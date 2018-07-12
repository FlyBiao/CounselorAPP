package com.cesaas.android.counselor.order.member.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.member.bean.service.ServiceListBean;
import com.cesaas.android.counselor.order.member.net.service.SerRemarkNet;
import com.cesaas.android.counselor.order.member.service.MemberServiceCheckDetailActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberServiceCheckAdapter extends BaseQuickAdapter<ServiceListBean, BaseViewHolder> {

    private List<ServiceListBean> mData;
    private Activity mActivity;
    private double finish=0;
    private double quantity=0;
    private double progress=0;
    private LinearLayout ll_service_remark;
    private EditText et_service_remark;
    private Dialog remarkDialog;
    private View dialogContentView;

    public MemberServiceCheckAdapter(List<ServiceListBean> mData,Activity activity) {
        super( R.layout.item_member_service_check,mData);
        this.mData=mData;
        this.mActivity=activity;
}

    @Override
    protected void convert(final BaseViewHolder helper, final ServiceListBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        if(item.getCreateDate()!=null && !"".equals(item.getCreateDate())){
            helper.setText(R.id.tv_create_date, AbDateUtil.getDateYMDs(item.getCreateDate()));
        }else{
            helper.setText(R.id.tv_create_date,"暂无发布日期");
        }
        if(item.getEndDate()!=null && !"".equals(item.getEndDate())){
            helper.setText(R.id.tv_end_time,AbDateUtil.getDateYMDs(item.getEndDate()));
        }else{
            helper.setText(R.id.tv_end_time,"暂无截至日期");
        }
        finish=item.getFinish();
        helper.setText(R.id.tv_finish,item.getFinish()+"");
        quantity=item.getQuantity();
        helper.setText(R.id.tv_quantity,item.getQuantity()+"");
        if(finish!=0){
            progress=finish/quantity*100;
            helper.setProgress(R.id.pb_progress,(int)progress);
        }else{
            helper.setProgress(R.id.pb_progress,0);
        }
        if (item.getRemark()!=null && !"".equals(item.getRemark())){
            helper.setText(R.id.tv_service_remark,item.getRemark());
        }else{
            helper.setText(R.id.tv_service_remark,"未设置备注");
        }

        helper.setOnClickListener(R.id.ll_check_service_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                b.putInt("Id",mData.get(helper.getAdapterPosition()).getId());
                b.putInt("Status",mData.get(helper.getAdapterPosition()).getStatus());
                b.putInt("Finish",mData.get(helper.getAdapterPosition()).getFinish());
                b.putInt("Quantity",mData.get(helper.getAdapterPosition()).getQuantity());
                b.putString("Title",mData.get(helper.getAdapterPosition()).getTitle());
                b.putString("CreateDate",mData.get(helper.getAdapterPosition()).getCreateDate());
                b.putString("EndDate",mData.get(helper.getAdapterPosition()).getEndDate());
                b.putString("Remark",mData.get(helper.getAdapterPosition()).getRemark());
                Skip.mNextFroData(mActivity,MemberServiceCheckDetailActivity.class,b);
            }
        });
        helper.setOnClickListener(R.id.ll_add_remark, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRemarkDialog(item);
            }
        });

        switch (item.getStatus()){
            case 10:
                helper.setText(R.id.tv_service_status,"进行中");
                helper.setBackgroundRes(R.id.tv_service_status,R.drawable.button_red_bg);
                helper.setTextColor(R.id.tv_end,mContext.getResources().getColor(R.color.red));
                helper.setTextColor(R.id.tv_finish,mContext.getResources().getColor(R.color.red));
                helper.setTextColor(R.id.tv_quantity,mContext.getResources().getColor(R.color.red));
                helper.setTextColor(R.id.tv_xie,mContext.getResources().getColor(R.color.red));
                break;
            case 20:
                helper.setText(R.id.tv_service_status,"已完成");
                helper.setBackgroundRes(R.id.tv_service_status,R.drawable.button_ellipse_huise_bg);
                helper.setTextColor(R.id.tv_end,mContext.getResources().getColor(R.color.defalult_text_color));
                helper.setTextColor(R.id.tv_finish,mContext.getResources().getColor(R.color.defalult_text_color));
                helper.setTextColor(R.id.tv_quantity,mContext.getResources().getColor(R.color.defalult_text_color));
                helper.setTextColor(R.id.tv_xie,mContext.getResources().getColor(R.color.defalult_text_color));
                break;
            case 30:
                helper.setText(R.id.tv_service_status,"已关闭");
                helper.setBackgroundRes(R.id.tv_service_status,R.drawable.button_ellipse_huise_bg);
                helper.setTextColor(R.id.tv_end,mContext.getResources().getColor(R.color.defalult_text_color));
                helper.setTextColor(R.id.tv_finish,mContext.getResources().getColor(R.color.defalult_text_color));
                helper.setTextColor(R.id.tv_quantity,mContext.getResources().getColor(R.color.defalult_text_color));
                helper.setTextColor(R.id.tv_xie,mContext.getResources().getColor(R.color.defalult_text_color));
                break;
            default:
                break;
        }
    }

    public void setRemarkDialog(final ServiceListBean bean){
        remarkDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_remark, null);
        remarkDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        et_service_remark= (EditText) remarkDialog.findViewById(R.id.et_service_remark);
        ll_service_remark= (LinearLayout) remarkDialog.findViewById(R.id.ll_service_remark);
        ll_service_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(mContext).mInitShow("服务备注", "确认添加"+bean.getTitle()+" 备注？",
                        "我知道", "点错了", new MyAlertDialog.ConfirmListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                remarkDialog.dismiss();
                                SerRemarkNet net=new SerRemarkNet(mContext);
                                net.setData(bean.getId(),et_service_remark.getText().toString());
                            }
                        }, null);
            }
        });

        remarkDialog.getWindow().setGravity(Gravity.BOTTOM);
        remarkDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        remarkDialog.setCanceledOnTouchOutside(true);
        remarkDialog.show();
    }
}
