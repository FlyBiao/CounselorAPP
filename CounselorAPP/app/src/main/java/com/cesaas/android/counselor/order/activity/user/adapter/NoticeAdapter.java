package com.cesaas.android.counselor.order.activity.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.bean.NoticeBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/6/26 16:16
 * Version 1.0
 */

public class NoticeAdapter extends SwipeMenuAdapter<NoticeAdapter.DefaultViewHolder> {

    private List<NoticeBean> noticeBeanList;
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;

    public NoticeAdapter(List<NoticeBean>  noticeBeanList, Context mContext){
        this.noticeBeanList=noticeBeanList;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
    }

    @Override
    public NoticeAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new NoticeAdapter.DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final NoticeAdapter.DefaultViewHolder holder, final int position) {
        holder.setData(noticeBeanList.get(position).getTitle(),noticeBeanList.get(position).getCreateTime(),noticeBeanList.get(position).getStatus());
        holder.setOnItemClickListener(mOnItemClickListener);

    }

    @Override
    public int getItemCount() {
        return noticeBeanList == null ? 0 : noticeBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_notice_title,tv_notice_type,tv_notice_time;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_notice_title= (TextView) itemView.findViewById(R.id.tv_notice_title);
            tv_notice_time= (TextView) itemView.findViewById(R.id.tv_notice_time);
            tv_notice_type= (TextView) itemView.findViewById(R.id.tv_notice_type);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String noticeTitle,String noticeTime,int noticeType) {
            this.tv_notice_title.setText(noticeTitle);
            this.tv_notice_time.setText(noticeTime);
            if(noticeType==0){
                this.tv_notice_type.setText("未读");
                this.tv_notice_type.setTextColor(mContext.getResources().getColor(R.color.red));
            }else{
                this.tv_notice_type.setText("已读");
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
