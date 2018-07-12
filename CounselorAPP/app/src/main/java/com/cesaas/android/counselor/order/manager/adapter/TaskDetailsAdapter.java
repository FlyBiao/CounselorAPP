package com.cesaas.android.counselor.order.manager.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.manager.bean.TaskDetailsBean;
import com.cesaas.android.counselor.order.manager.bean.TaskListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 任务Details Adapter
 * Created at 2017/9/12 16:42
 * Version 1.0
 */

public class TaskDetailsAdapter extends BaseQuickAdapter<TaskDetailsBean,BaseViewHolder>{
    public TaskDetailsAdapter(List<TaskDetailsBean> data) {
        super(R.layout.item_task_details, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskDetailsBean item) {
        helper.setText(R.id.tv_name,item.getNotifyName());
        helper.setText(R.id.tv_shop_name,item.getShopName());
        helper.setText(R.id.tv_task_create_time,item.getCreateTime());
        if(item.getImageUrl()!=null && !item.getImageUrl().equals("")){
            // 加载网络图片
            Glide.with(mContext).load(item.getImageUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_user_img));
        }else{
            helper.setImageResource(R.id.iv_user_img,R.mipmap.ic_launcher);
        }

        if(item.getStatus()==0){//未处理
            helper.setText(R.id.tv_task_status,"未处理");
        }else{
            helper.setText(R.id.tv_task_status,"已结束");
        }
    }
}
