package com.cesaas.android.counselor.order.workbench.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.workbench.bean.WorkbenchBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 工作台数据适配器
 * Created 2017/4/7 15:55
 * Version 1.zero
 */
public class WorkbenchAdapter extends SwipeMenuAdapter<WorkbenchAdapter.DefaultViewHolder> {

    private List<WorkbenchBean> mWorkbenchBeanList;
    private OnItemClickListener mOnItemClickListener;

    public WorkbenchAdapter(List<WorkbenchBean> mWorkbenchBeanList){
        this.mWorkbenchBeanList=mWorkbenchBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_work_bench, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.setData(mWorkbenchBeanList.get(position).getTitle(),
                mWorkbenchBeanList.get(position).getContent(),
                mWorkbenchBeanList.get(position).getStatus());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mWorkbenchBeanList == null ? 0 : mWorkbenchBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_work_bench_title,tv_work_bench_content;
        ImageView iv_work_bench_icon;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_work_bench_title = (TextView) itemView.findViewById(R.id.tv_work_bench_title);
            tv_work_bench_content= (TextView) itemView.findViewById(R.id.tv_work_bench_content);
            iv_work_bench_icon= (ImageView) itemView.findViewById(R.id.iv_work_bench_icon);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String content,int status) {
            this.tv_work_bench_title.setText(title);
            this.tv_work_bench_content.setText(content);
            switch (status){
                case 1://巡店
                    this.iv_work_bench_icon.setImageResource(R.mipmap.shop_visit);

                    break;
                case 2://回访
                    this.iv_work_bench_icon.setImageResource(R.mipmap.return_visit);

                    break;
                case 3://陈列
                    this.iv_work_bench_icon.setImageResource(R.mipmap.display);

                    break;
                case 4://工作
                    this.iv_work_bench_icon.setImageResource(R.mipmap.work);
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
