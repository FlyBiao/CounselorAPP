package com.cesaas.android.counselor.order.member.adapter;

import android.app.Dialog;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.main.NewMainActivity;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.MemberServiceResultBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberServiceRecordAdapter extends BaseQuickAdapter<MemberServiceResultBean, BaseViewHolder> {

    private List<MemberServiceResultBean> mData;

    public MemberServiceRecordAdapter(List<MemberServiceResultBean> mData) {
        super( R.layout.item_member_service_record,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MemberServiceResultBean item) {
        if(item.getDate()!=null && !"".equals(item.getDate())){
            helper.setText(R.id.tv_service_day, AbDateUtil.formats(item.getDate()));
        }
        helper.setText(R.id.tv_service_date,item.getDate());
        helper.setText(R.id.tv_service_name,item.getName());
        if(item.getContent()!=null && !"".equals(item.getContent())){
            helper.setText(R.id.tv_service_remark,item.getContent());
            helper.setText(R.id.tv_remark,"内容:");
        }else{
            helper.setText(R.id.tv_service_remark,item.getRemark());
            helper.setText(R.id.tv_remark,"备注:");
        }
        switch (item.getServiceType()){
            case 1:
                helper.setText(R.id.tv_service_types,"电话-");
                break;
            case 2:
                helper.setText(R.id.tv_service_types,"其他-");
                break;
            default:
                helper.setText(R.id.tv_service_types,"未知沟通方式-");
                break;
        }
        switch (item.getServiceResult()){
            case 1:
                helper.setText(R.id.tv_service_result,"接听或有回复");
                break;
            case 2:
                helper.setText(R.id.tv_service_result,"没反馈");
                break;
            case 3:
                helper.setText(R.id.tv_service_result,"拒绝沟通");
                break;
            default:
                break;
        }
        switch (item.getGoShop()){
            case 1:
                helper.setText(R.id.tv_go_shop,"愿意来店"+"("+AbDateUtil.getDateYMDs(item.getGoShopDate())+")");
                break;
            case 2:
                helper.setText(R.id.tv_go_shop,"不愿意来店");
                break;
            case 3:
                helper.setText(R.id.tv_go_shop,"不确定"+"("+AbDateUtil.getDateYMDs(item.getGoShopDate())+")");
                break;
            default:
                helper.setText(R.id.tv_go_shop,"不确定");
                break;
        }
        switch (item.getType()){
            case 0:
                helper.setText(R.id.tv_service_type,"常规服务");
                break;
            case 1:
                helper.setText(R.id.tv_service_type,"销售回访");
                break;
            case 2:
                helper.setText(R.id.tv_service_type,"生日祝福");
                break;
            case 3:
                helper.setText(R.id.tv_service_type,"节日安排");
                break;
            case 4:
                helper.setText(R.id.tv_service_type,"休眠激活");
                break;
            case 99:
                helper.setText(R.id.tv_service_type,"定制会员");
                break;
            default:
                break;
        }
        helper.setOnClickListener(R.id.tv_service_remark, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getContent()!=null && !"".equals(item.getContent())){
                    showContent("服务内容",item.getContent());
                }else{
                    showContent("服务备注",item.getRemark());
                }
            }
        });
    }


    public void showContent(String title,String content){
        new MyAlertDialog(mContext).mInitShow(title, content,
            "我知道", "已阅读", new MyAlertDialog.ConfirmListener() {
                @Override
                public void onClick(Dialog dialog) {

                }
            }, null);
    }
}
