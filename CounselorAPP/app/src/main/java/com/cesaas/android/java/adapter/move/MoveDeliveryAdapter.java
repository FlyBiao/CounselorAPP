package com.cesaas.android.java.adapter.move;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.utils.MoveDeliveryStatusUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 调拨单
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class MoveDeliveryAdapter extends BaseQuickAdapter<MoveDeliveryListBeanBean, BaseViewHolder> {

    private List<MoveDeliveryListBeanBean> mData;

    private Context mContext;
    private  Activity mActivity;
    private int type;

    public MoveDeliveryAdapter(List<MoveDeliveryListBeanBean> mData, Activity activity, Context ct,int type) {
        super( R.layout.item_mover_delivery,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
        this.type=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MoveDeliveryListBeanBean item) {
        helper.setText(R.id.tv_sort_desc,R.string.fa_caret_down);
        helper.setTypeface(R.id.tv_sort_desc, App.font);
        helper.setText(R.id.tv_shop_icon,R.string.business_school);
        helper.setTypeface(R.id.tv_shop_icon, App.font);
        helper.setText(R.id.tv_org_icon,R.string.fa_long_arrow_right);
        helper.setTypeface(R.id.tv_org_icon, App.font);
        helper.setText(R.id.tv_send_time,R.string.fa_clock);
        helper.setTypeface(R.id.tv_send_time, App.font);
        helper.setText(R.id.tv_create_time,R.string.fa_clock);
        helper.setTypeface(R.id.tv_create_time, App.font);

        helper.setText(R.id.tv_no,item.getNo());
        helper.setText(R.id.tv_originOrganizationTitle,item.getOriginOrganizationTitle());
        helper.setText(R.id.tv_originShopNo,item.getOriginShopNo()+"-");
        helper.setText(R.id.tv_originShopName,item.getOriginShopName());
        helper.setText(R.id.tv_receiveOrganizationTitle,item.getReceiveOrganizationTitle());
        helper.setText(R.id.tv_receiveShopNo,item.getReceiveShopNo()+"-");
        helper.setText(R.id.tv_receiveShopName,item.getReceiveShopName());
        helper.setText(R.id.tv_num,String.valueOf(item.getNum()));

        if(item.getCreateTime()!=null && !"".equals(item.getCreateTime())){
            helper.setText(R.id.tv_createTime, AbDateUtil.getDateYMDs(item.getCreateTime()));
        }
        if(item.getSubmitTime()!=null && !"".equals(item.getSubmitTime())){
            helper.setText(R.id.tv_submitTime,AbDateUtil.getDateYMDs(item.getShipmentsDate()));
        }else{
            if(type==1){
                helper.setText(R.id.tv_submitTime,"未退货");
            }else{
                helper.setText(R.id.tv_submitTime,"未发货");
            }
        }
        if(item.getRemark()!=null && !"".equals(item.getRemark())){
            helper.setText(R.id.tv_remark,item.getRemark());
        }

        MoveDeliveryStatusUtils.getStatus(helper,item.getStatus(),mContext);
//        MoveDeliveryStatusUtils.getType(helper,item.getType(),mContext);
        MoveDeliveryStatusUtils.getCategory(helper,item.getCategory(),mContext);

        helper.setOnClickListener(R.id.ll_handle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getStatus()==40){
                    ToastFactory.getLongToast(mContext,"该订单已确认");
                }else{
                    BottonDialogUtils.showBottonDialog(mContext,mActivity,item,type);
                }
            }
        });
    }
}
