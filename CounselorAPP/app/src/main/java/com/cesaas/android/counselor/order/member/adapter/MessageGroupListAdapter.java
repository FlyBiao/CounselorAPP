package com.cesaas.android.counselor.order.member.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.MessageGroupActivity;
import com.cesaas.android.counselor.order.member.MessageGroupAnotherActivity;
import com.cesaas.android.counselor.order.member.bean.MessageListBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 短信群发列表Adapter
 * Created 2017/4/15 11:39
 * Version 1.zero
 */
public class MessageGroupListAdapter extends SwipeMenuAdapter<MessageGroupListAdapter.DefaultViewHolder>{

    private List<MessageListBean> mMessageGroupLists;
    private OnItemClickListener mOnItemClickListener;
    private Activity mActivity;

    public MessageGroupListAdapter(List<MessageListBean> mMessageGroupLists,Activity mActivity){
        this.mMessageGroupLists=mMessageGroupLists;
        this.mActivity=mActivity;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_list, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, final int position) {
        holder.setData(mMessageGroupLists.get(position).getMsg(),mMessageGroupLists.get(position).getCreateTime());
        holder.setOnItemClickListener(mOnItemClickListener);

        //再发一条
        holder.tv_send_another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("content",mMessageGroupLists.get(position).getMsg());
                Skip.mNextFroData(mActivity, MessageGroupActivity.class,bundle);
//                Skip.mNextFroData(mActivity, MessageGroupAnotherActivity.class,bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  mMessageGroupLists == null ? 0 : mMessageGroupLists.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_message_time,tv_message_content,tv_send_another;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_message_content = (TextView) itemView.findViewById(R.id.tv_message_content);
            tv_message_time= (TextView) itemView.findViewById(R.id.tv_message_time);
            tv_send_another= (TextView) itemView.findViewById(R.id.tv_send_another);


        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String msgContent,String time) {
            this.tv_message_time.setText(time);
            this.tv_message_content.setText(msgContent);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
