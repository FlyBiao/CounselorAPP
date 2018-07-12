package com.cesaas.android.counselor.order.activity.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.bean.MyWorkBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;


/**
 * Author FGB
 * Description 我的工作数据适配器
 * Created 2017/4/6 19:55
 * Version 1.zero
 */
public class MyWorkAdapter extends SwipeMenuAdapter<MyWorkAdapter.DefaultViewHolder> {

    private List<ShopTaskListBean> mMyWorkBeanList;
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;

    public MyWorkAdapter(List<ShopTaskListBean> mMyWorkBeanList,Context mContext){
        this.mMyWorkBeanList=mMyWorkBeanList;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_work, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(mMyWorkBeanList.get(position).getTitle(),mMyWorkBeanList.get(position).getNotifyName()
                ,mMyWorkBeanList.get(position).getDurationTime(),mMyWorkBeanList.get(position).getStatus());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mMyWorkBeanList == null ? 0 : mMyWorkBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_work_title,tv_work_date,work_content,tv_work_status;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_work_title = (TextView) itemView.findViewById(R.id.tv_work_title);
            tv_work_date= (TextView) itemView.findViewById(R.id.tv_work_date);
            work_content = (TextView) itemView.findViewById(R.id.work_content);
            tv_work_status= (TextView) itemView.findViewById(R.id.tv_work_status);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String content,int date,int status) {
            this.tv_work_title.setText(title);
            this.work_content.setText(content);
            int day=date/24;
            int h=date%24;
            this.tv_work_date.setText("还有"+day+"天"+h+"小时");
            switch (status){
                case 0://待确认
                    this.tv_work_status.setText("待确认");
                    this.tv_work_status.setTextColor(mContext.getResources().getColor(R.color.base_color));
                    break;
                case 1://待提交
                    this.tv_work_status.setText("待提交");
                    this.tv_work_status.setTextColor(mContext.getResources().getColor(R.color.base_color));
                    break;
                case 2://待审核
                    this.tv_work_status.setText("待审核");
                    this.tv_work_status.setTextColor(mContext.getResources().getColor(R.color.base_color));
                    break;
            }
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
