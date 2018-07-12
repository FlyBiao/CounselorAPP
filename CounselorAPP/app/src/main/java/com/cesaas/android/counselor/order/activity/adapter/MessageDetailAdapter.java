package com.cesaas.android.counselor.order.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.bean.MessageBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/5/22 16:42
 * Version 1.0
 */

public class MessageDetailAdapter extends SwipeMenuAdapter<MessageDetailAdapter.DefaultViewHolder>{

    private List<MessageBean> messageBeanList;
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;

    public MessageDetailAdapter(List<MessageBean>  messageBeanList,Context mContext){
        this.messageBeanList=messageBeanList;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(messageBeanList.get(position).getDescription(),messageBeanList.get(position).getNotifyTime());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return messageBeanList == null ? 0 : messageBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_notify_time,tv_notify_description;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_notify_time = (TextView) itemView.findViewById(R.id.tv_notify_time);
            tv_notify_description= (TextView) itemView.findViewById(R.id.tv_notify_description);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String time) {
            this.tv_notify_time.setText(time);
            this.tv_notify_description.setText(title);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
