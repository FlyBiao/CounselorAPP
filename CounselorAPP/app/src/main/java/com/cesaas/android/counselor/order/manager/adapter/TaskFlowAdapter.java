package com.cesaas.android.counselor.order.manager.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskFlowBean;
import com.cesaas.android.counselor.order.manager.bean.TaskDetailsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 任务Details Adapter
 * Created at 2017/9/12 16:42
 * Version 1.0
 */

public class TaskFlowAdapter extends BaseQuickAdapter<ResultTaskFlowBean.TaskFlowBean,BaseViewHolder>{
    public TaskFlowAdapter(List<ResultTaskFlowBean.TaskFlowBean> data) {
        super(R.layout.item_task_flow, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultTaskFlowBean.TaskFlowBean item) {
        helper.setText(R.id.tv_task_flow_name,item.getTitle());
        helper.setText(R.id.tv_task_crate_date,item.getCreateTime());
        helper.setText(R.id.tv_task_remark,item.getRemark());


    }
}
