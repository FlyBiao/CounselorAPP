package com.cesaas.android.java.adapter.require;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.require.RequireListBean;
import com.cesaas.android.java.utils.MoveDeliveryStatusUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 补货列表
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class RequireListAdapter extends BaseQuickAdapter<RequireListBean, BaseViewHolder> {

    private List<RequireListBean> mData;

    private Context mContext;
    private  Activity mActivity;

    public RequireListAdapter(List<RequireListBean> mData, Activity activity, Context ct) {
        super( R.layout.item_require_list,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(BaseViewHolder helper, final RequireListBean item) {

        helper.setText(R.id.tv_sort_desc,R.string.fa_caret_down);
        helper.setTypeface(R.id.tv_sort_desc, App.font);
        helper.setText(R.id.tv_shop_icon,R.string.business_school);
        helper.setTypeface(R.id.tv_shop_icon, App.font);
        helper.setText(R.id.tv_time_icon,R.string.fa_clock);
        helper.setTypeface(R.id.tv_time_icon, App.font);

        helper.setText(R.id.tv_num,String.valueOf(item.getNum()));
        helper.setText(R.id.requier_no,item.getNo());
        helper.setText(R.id.tv_shop_name,item.getOriginShopName());
        helper.setText(R.id.tv_time, AbDateUtil.getDateYMDs(item.getCreateTime()));
        switch (item.getType()){
            case 1:
                helper.setText(R.id.tv_status,"订货单");
                break;
            case 2:
                helper.setText(R.id.tv_status,"补单发货");
                break;
            case 3:
                helper.setText(R.id.tv_status,"铺货单");
                break;
            default:
                helper.setText(R.id.tv_status,"补单发货");
                break;
        }

        if(item.getRemark()!=null && !"".equals(item.getRemark())){
            helper.setText(R.id.tv_remark,item.getRemark());
        }

        MoveDeliveryStatusUtils.getStatus(helper,item.getStatus(),mContext);

        helper.setOnClickListener(R.id.ll_handle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getStatus()==40){
                    ToastFactory.getLongToast(mContext,"该订单已确认");
                }else{
                    BottonDialogUtils.getRequireDialog(mContext,mActivity,item);
                }
            }
        });
    }
}
