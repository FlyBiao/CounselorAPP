package com.cesaas.android.counselor.order.member.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionBean;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 会员分配Adapter
 * Created 2017/4/11 18:33
 * Version 1.zero
 */
public class MemberDistributionAdapter extends SwipeMenuAdapter<MemberDistributionAdapter.DefaultViewHolder>{

    //
    SparseBooleanArray mSelectedPositions =new SparseBooleanArray();

    private List<MemberDistributionBean> mMemberDistributionBeanList;
    private OnItemClickListener mOnItemClickListener;
    public static Context mContext;
    public static Activity mActivity;

    public MemberDistributionAdapter(List<MemberDistributionBean> mMemberDistributionBeanList, Context mContext){
        this.mContext=mContext;
        this.mMemberDistributionBeanList=mMemberDistributionBeanList;
    }

    //用来为Adapter 里的数据item设置标记，默认每个条目为false，选中的话就设置为true
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_distridution, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    //绑定界面，设置监听
    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {

        holder.setData(mMemberDistributionBeanList.get(position).getCOUNSELOR_NAME()
                ,mMemberDistributionBeanList.get(position).getCOUNSELOR_NICKNAME()
        ,mMemberDistributionBeanList.get(position).getFANS_NUM()
        ,mMemberDistributionBeanList.get(position).getCOUNSELOR_LEVELID()+""
        ,mMemberDistributionBeanList.get(position).getCOUNSELOR_ICON());
        holder.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mMemberDistributionBeanList == null ? 0 : mMemberDistributionBeanList.size();
    }


    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_member_name,tv_member_grade,tv_member_point;
        MyImageViewWidget ivw_member_icon;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_member_name = (TextView) itemView.findViewById(R.id.tv_member_name);
            tv_member_grade= (TextView) itemView.findViewById(R.id.tv_member_grade);
            tv_member_point= (TextView) itemView.findViewById(R.id.tv_member_point);
            ivw_member_icon= (MyImageViewWidget) itemView.findViewById(R.id.ivw_member_icon);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String nickName,int point,String grade,String imgUrl) {
            if(name!=null && !"".equals(name)){
                this.tv_member_name.setText(name);
            }else {
                this.tv_member_name.setText(nickName);
            }

            if(imgUrl!=null && !"".equals(imgUrl)){
                Glide.with(mContext).load(imgUrl).into(this.ivw_member_icon);
            }else{
                this.ivw_member_icon.setImageResource(R.mipmap.ic_launcher);
            }

            this.tv_member_grade.setText(grade);
            this.tv_member_point.setText(""+point);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
