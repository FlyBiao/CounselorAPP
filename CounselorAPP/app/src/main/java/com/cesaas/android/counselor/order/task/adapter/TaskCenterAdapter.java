package com.cesaas.android.counselor.order.task.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.task.bean.TaskCenterBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DateUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 任务中心Adapter
 * Created 2017/3/27 15:20
 * Version 1.zero
 */
public class TaskCenterAdapter extends SwipeMenuAdapter<TaskCenterAdapter.DefaultViewHolder>{

    private List<TaskCenterBean> taskCenterBeanList;
    private OnItemClickListener mOnItemClickListener;

    public TaskCenterAdapter(List<TaskCenterBean> taskCenterBeanList) {
        this.taskCenterBeanList = taskCenterBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_center, parent, false);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {

        holder.setData(taskCenterBeanList.get(position).getTitle(),taskCenterBeanList.get(position).getCreteTime());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public int getItemCount() {
        return taskCenterBeanList == null ? 0 : taskCenterBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_task_title,tv_task_day,tv_date_zimu;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_task_title = (TextView) itemView.findViewById(R.id.tv_task_title);
            tv_task_day= (TextView) itemView.findViewById(R.id.tv_task_day);
            tv_date_zimu= (TextView) itemView.findViewById(R.id.tv_date_zimu);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String day) {
            this.tv_task_title.setText(title);
            this.tv_task_day.setText(""+ AbDateUtil.toYMD(day));
            String mm=AbDateUtil.toMarch(day);
            if(mm.equals("01")){
                this.tv_date_zimu.setText(""+ DateUtils.JAN);
            }else if(mm.equals("02")){
                this.tv_date_zimu.setText(""+ DateUtils.FEB);
            }else if(mm.equals("03")){
                this.tv_date_zimu.setText(""+ DateUtils.MAR);
            }else if(mm.equals("04")){
                this.tv_date_zimu.setText(""+ DateUtils.APR);
            }else if(mm.equals("05")){
                this.tv_date_zimu.setText(""+ DateUtils.MAY);
            }else if(mm.equals("06")){
                this.tv_date_zimu.setText(""+ DateUtils.JUN);
            }else if(mm.equals("07")){
                this.tv_date_zimu.setText(""+ DateUtils.JUL);
            }else if(mm.equals("08")){
                this.tv_date_zimu.setText(""+ DateUtils.AUG);
            }else if(mm.equals("09")){
                this.tv_date_zimu.setText(""+ DateUtils.SEPT);
            }else if(mm.equals("10")){
                this.tv_date_zimu.setText(""+ DateUtils.OCT);
            }else if(mm.equals("11")){
                this.tv_date_zimu.setText(""+ DateUtils.NOV);
            }else{
                this.tv_date_zimu.setText(""+ DateUtils.DEC);
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
