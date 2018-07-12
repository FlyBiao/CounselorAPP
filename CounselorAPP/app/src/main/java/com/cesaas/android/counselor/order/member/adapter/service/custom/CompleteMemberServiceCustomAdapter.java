package com.cesaas.android.counselor.order.member.adapter.service.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
import com.cesaas.android.counselor.order.member.net.service.ColseServiceNet;
import com.cesaas.android.counselor.order.member.net.service.ServiceRemarkNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 会员服务-完成生日祝福
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class CompleteMemberServiceCustomAdapter extends BaseQuickAdapter<CustomServiceBean, BaseViewHolder> {

    private LinearLayout ll_close_service,ll_service_remark,ll_call_phone,ll_send_msg;
    private EditText et_service_remark;

    private List<CustomServiceBean> mData;
    private Activity mActivity;
    private Context mContext;

    private Dialog bottomDialog;
    private Dialog remarkDialog;
    private View dialogContentView;

    public CompleteMemberServiceCustomAdapter(List<CustomServiceBean> mData, Activity activity, Context context) {
        super( R.layout.item_member_complete_service_visit,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CustomServiceBean item) {
        helper.setText(R.id.tv_service_title,item.getTitle());
        if(item.getDate()!=null && !"".equals(item.getDate())){
            String str=item.getDate();
            helper.setText(R.id.tv_create_date, AbDateUtil.getDateYMDs(item.getDate()));
            String dateType=str.substring(4,5);
            if(dateType.equals("/")){
                helper.setText(R.id.tv_day, AbDateUtil.formatDateStr2Desc(item.getDate(),"yyyy/MM/dd HH:mm:ss"));
            }else{
                helper.setText(R.id.tv_day,AbDateUtil.formatDateStr2Desc(item.getDate(),"yyyy-MM-dd HH:mm:ss"));
            }
        }else{
            helper.setText(R.id.tv_create_date,"暂无时间");
        }
        helper.setText(R.id.tv_member_name,item.getName());
        switch (item.getStatus()){
            case 10:
                helper.setText(R.id.tv_service_status,"待处理");
                helper.setBackgroundRes(R.id.tv_service_status,R.drawable.button_ellipse_green_bg);
                break;
            case 20:
                helper.setText(R.id.tv_service_status,"已完成");
                helper.setBackgroundRes(R.id.tv_service_status,R.drawable.button_ellipse_translucent_bg);
                break;
            case 30:
                helper.setText(R.id.tv_service_status,"已关闭");
                helper.setBackgroundRes(R.id.tv_service_status,R.drawable.button_ellipse_translucent_bg);
                break;
        }
        if(item.getRemark()!=null && !"".equals(item.getRemark())){
            helper.setText(R.id.tv_remark,item.getRemark());
        }else{
            helper.setText(R.id.tv_remark,"暂无备注!");
        }
        if(item.getImage()!=null && !"".equals(item.getImage()) && !"NULL".equals(item.getImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_user_icon));
        }else{
            helper.setImageResource(R.id.ivw_user_icon,R.mipmap.ic_launcher);
        }

        helper.setOnClickListener(R.id.ll_member_details, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("Id",item.getId());
                bundle.putInt("VipId",item.getVipId());
                bundle.putString("Name",item.getName());
                bundle.putString("Phone",item.getPhone());
                bundle.putString("Date",item.getDate());
                bundle.putString("Desc",item.getDesc());
                bundle.putString("Remark",item.getRemark());
                bundle.putString("Title",item.getTitle());
                bundle.putInt("Status",item.getStatus());
                Skip.mNextFroData(mActivity,MemberReturnVisitDetailActivity.class,bundle);
            }
        });helper.setOnClickListener(R.id.ll_member_info, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("Id",item.getId());
                bundle.putInt("VipId",item.getVipId());
                bundle.putString("Name",item.getName());
                bundle.putString("Phone",item.getPhone());
                bundle.putString("Date",item.getDate());
                bundle.putString("Desc",item.getDesc());
                bundle.putString("Remark",item.getRemark());
                bundle.putString("Title",item.getTitle());
                bundle.putInt("Status",item.getStatus());
                Skip.mNextFroData(mActivity,MemberReturnVisitDetailActivity.class,bundle);
            }
        });
//        helper.setOnClickListener(R.id.ll_member_audit, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showBottonDialog(item);
//            }
//        });helper.setOnClickListener(R.id.ll_remark, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setRemarkDialog(item);
//            }
//        });
    }

    public void showBottonDialog(final CustomServiceBean bean){
        bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_content_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_send_msg= (LinearLayout) bottomDialog.findViewById(R.id.ll_send_msg);
        ll_call_phone= (LinearLayout) bottomDialog.findViewById(R.id.ll_call_phone);
        ll_close_service= (LinearLayout) bottomDialog.findViewById(R.id.ll_close_service);
        ll_close_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                ToastFactory.getLongToast(mContext,"该服务已完成！");
            }
        });
        ll_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                ToastFactory.getLongToast(mContext,"该服务已完成！");
            }
        });ll_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                ToastFactory.getLongToast(mContext,"该服务已完成！");
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    public void setRemarkDialog(final CustomServiceBean bean){
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
                new MyAlertDialog(mContext).mInitShow("服务备注", "确认添加"+bean.getName()+" "+bean.getTitle()+" 备注？",
                        "我知道", "点错了", new MyAlertDialog.ConfirmListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                remarkDialog.dismiss();
                                ServiceRemarkNet net=new ServiceRemarkNet(mContext);
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
