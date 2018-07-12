package com.cesaas.android.counselor.order.task.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.task.bean.MyTaskBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 我的任务
 * Created 2017/3/27 15:20
 * Version 1.zero
 */
public class MyTaskAdapter extends SwipeMenuAdapter<MyTaskAdapter.DefaultViewHolder>{

    private List<MyTaskBean> myTaskBeanList;
    private OnItemClickListener mOnItemClickListener;

    public MyTaskAdapter(List<MyTaskBean> myTaskBeanList) {
        this.myTaskBeanList = myTaskBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_task, parent, false);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(myTaskBeanList.get(position).getTitle(),myTaskBeanList.get(position).getDate());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public int getItemCount() {
        return myTaskBeanList == null ? 0 : myTaskBeanList.size();
    }


    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_my_task_title,tv_my_task_day;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_my_task_title = (TextView) itemView.findViewById(R.id.tv_my_task_title);
            tv_my_task_day= (TextView) itemView.findViewById(R.id.tv_my_task_day);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String day) {
            this.tv_my_task_title.setText(title);
            this.tv_my_task_day.setText(""+day);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
