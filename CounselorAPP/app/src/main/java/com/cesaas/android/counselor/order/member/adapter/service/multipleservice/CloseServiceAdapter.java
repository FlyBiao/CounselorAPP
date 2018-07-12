package com.cesaas.android.counselor.order.member.adapter.service.multipleservice;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.adapter.MemberQueryAdapter;
import com.cesaas.android.counselor.order.member.bean.service.VipListBean;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.CloseServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.multipleservice.MultipleServiceBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author FGB
 * Description
 * Created at 2018/4/25 10:11
 * Version 1.0
 */

public class CloseServiceAdapter extends RecyclerView.Adapter<CloseServiceAdapter.ViewHolder> {

    private Activity mActivity;
    private List<MultipleServiceBean> mData;
    private Context context;

    private OnItemClickListener mOnItemClickListener;

    public CloseServiceAdapter(Context context) {
        this.context = context;
    }

    public void notifyAdapter(List<MultipleServiceBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mData = myLiveList;
        } else {
            this.mData.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<MultipleServiceBean> getDataList() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        return mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_close_service, parent, false);
        CloseServiceAdapter.ViewHolder holder = new CloseServiceAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(final CloseServiceAdapter.ViewHolder holder, final int position) {
        final MultipleServiceBean myLive = mData.get(holder.getAdapterPosition());
        if(myLive.getTaskDate()!=null && !"".equals(myLive.getTaskDate())){
            holder.tv_service_date.setText(AbDateUtil.getDateYMDs(myLive.getTaskDate()));
        }else{
            holder.tv_service_date.setText("");
        }
        holder.tv_service_title.setText(myLive.getTitle());

        if (myLive.isSelect()) {
            holder.check_box.setImageResource(R.mipmap.check);
        } else {
            holder.check_box.setImageResource(R.mipmap.check_not);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mData);
            }
        });

    }

    public void setOnItemClickListener(CloseServiceAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<MultipleServiceBean> myLiveList);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_service_title)
        TextView tv_service_title;
        @BindView(R.id.tv_service_date)
        TextView tv_service_date;
        @BindView(R.id.iv_check)
        ImageView check_box;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
