package com.cesaas.android.counselor.order.activity.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ShopPowerBean;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.view.HandleTaskActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 任务列表
 * Created at 2018/1/20 11:40
 * Version 1.0
 */

public class TaskListAdapter extends BaseQuickAdapter<ShopTaskListBean, BaseViewHolder> {

    private List<ShopTaskListBean> mData;
    private Activity mActivity;

    public TaskListAdapter(List<ShopTaskListBean> mData,Activity activity) {
        super( R.layout.item_task,mData);
        this.mData=mData;
        this.mActivity=activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopTaskListBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_create_time,item.getCreteTime());
        helper.setText(R.id.tv_shop_name,item.getShopName());
        if(item.getDescription()!=null){
            helper.setText(R.id.tv_description,item.getDescription());
        }else{
            helper.setText(R.id.tv_description,"暂无备注！");
        }
        helper.setOnClickListener(R.id.ll_task_info, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("workid",item.getWorkId());
                bundle.putInt("flowid",item.getFlowId());
                bundle.putInt("formid",item.getFormId());
                Skip.mNextFroData(mActivity,HandleTaskActivity.class,bundle);
            }
        });
        helper.setOnClickListener(R.id.ll_handle_task, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottonDialogUtils.showBottonTaskDialog(mContext,mActivity,item,1);
            }
        });
    }
}
