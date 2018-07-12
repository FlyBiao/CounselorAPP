package com.cesaas.android.counselor.order.activity.main.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 任务列表
 * Created at 2018/1/20 11:40
 * Version 1.0
 */

public class MainTaskListAdapter extends BaseQuickAdapter<ShopTaskListBean, BaseViewHolder> {

    private List<ShopTaskListBean> mData;

    public MainTaskListAdapter(List<ShopTaskListBean> mData) {
        super( R.layout.item_main_task,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopTaskListBean item) {
        helper.setText(R.id.tv_task_title,item.getTitle());
        helper.setText(R.id.tv_task_time,AbDateUtil.getDateYMDs(item.getCreteTime()));
        if(item.getCreteTime()!=null && !"".equals(item.getCreteTime())){
            helper.setText(R.id.tv_task_day, AbDateUtil.formats(item.getCreteTime()));
        }else{
            helper.setText(R.id.tv_task_day,"");
        }

        switch (item.getTaskLevel()){
            case 0:
                helper.setText(R.id.tv_task_level,"普通");
                break;
            case 1:
                helper.setText(R.id.tv_task_level,"重要不紧急");
                break;
            case 2:
                helper.setText(R.id.tv_task_level,"紧急不重要");
                break;
            case 3:
                helper.setText(R.id.tv_task_level,"重要且紧急");
                break;
        }switch (item.getFlowStatus()){
            case 0:
                helper.setText(R.id.tv_task_status,"待处理");
                break;
            case 1:
                helper.setText(R.id.tv_task_status,"已完成");
                break;
           default:
               helper.setText(R.id.tv_task_status,"未知");
               break;
        }
    }
}
