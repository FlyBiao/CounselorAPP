package com.cesaas.android.counselor.order.manager.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.manager.bean.TaskBean;
import com.cesaas.android.counselor.order.manager.bean.TaskListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 任务Adapter
 * Created at 2017/9/12 16:42
 * Version 1.0
 */

public class TaskAdapter extends BaseQuickAdapter<TaskListBean,BaseViewHolder>{
    public TaskAdapter(List<TaskListBean> data) {
        super(R.layout.item_task_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskListBean item) {
        helper.setText(R.id.tv_task_title,item.getTaskTitle());
        helper.setText(R.id.tv_task_date,item.getStartDate());
        helper.setText(R.id.tv_week,AbDateUtil.getWeek(item.getStartDate(),"yyyy-MM-dd HH:mm:ss"));
        helper.setText(R.id.tv_number,item.getWorkQuantity()+"人");
        if(item.getEndDate()!=null){
            helper.setText(R.id.tv_month,AbDateUtil.getDateMonth(item.getEndDate())+"月");
            helper.setText(R.id.tv_day,AbDateUtil.getDateDay(item.getEndDate())+"");
        }

        if(item.getFlowStatus()==0){//草稿
            helper.setImageResource(R.id.iv_status,R.mipmap.task);
        }else{//已发布
            helper.setImageResource(R.id.iv_status,R.mipmap.check);
        }
    }
}
