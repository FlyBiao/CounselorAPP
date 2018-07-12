package com.cesaas.android.counselor.order.manager.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.manager.bean.ClerkBean;
import com.cesaas.android.counselor.order.manager.bean.TaskBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 新建任务执行人Adapter
 * Created at 2017/9/12 16:42
 * Version 1.0
 */

public class AddTaskExecutorAdapter extends BaseQuickAdapter<ClerkBean,BaseViewHolder>{
    public AddTaskExecutorAdapter(List<ClerkBean> data) {
        super(R.layout.item_add_task_executor, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClerkBean item) {
        helper.setText(R.id.tv_executor_shop,item.getShop());
        helper.setText(R.id.tv_executor_name,item.getName());
//        helper.setImageResource(R.id.tv_executor_icon, item.getIcon());
        // 加载网络图片
        Glide.with(mContext).load(item.getIcon()).crossFade().into((ImageView) helper.getView(R.id.tv_executor_icon));

        if(item.getId()==2008){
            helper.getView(R.id.iv_del_img).setVisibility(View.GONE);
        }
    }
}
