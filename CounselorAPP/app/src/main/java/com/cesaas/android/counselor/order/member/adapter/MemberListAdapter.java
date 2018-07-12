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
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 会员列表Adapter
 * Created 2017/4/11 18:33
 * Version 1.zero
 */
public class MemberListAdapter extends SwipeMenuAdapter<MemberListAdapter.DefaultViewHolder>{

    //
    SparseBooleanArray mSelectedPositions =new SparseBooleanArray();

    private List<Fans> mMemberDistributionBeanList;
    private OnItemClickListener mOnItemClickListener;
    public static Context mContext;
    public static Activity mActivity;



    public MemberListAdapter(List<Fans> mMemberDistributionBeanList, Context mContext){
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_list, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    //绑定界面，设置监听
    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {

        holder.setData(mMemberDistributionBeanList.get(position).FANS_NICKNAME
        ,mMemberDistributionBeanList.get(position).FANS_MOBILE
        ,mMemberDistributionBeanList.get(position).FANS_POINT
        ,mMemberDistributionBeanList.get(position).FANS_BIRTHDAY
        ,mMemberDistributionBeanList.get(position).FANS_GRADE
        ,mMemberDistributionBeanList.get(position).FANS_ICON);
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
        TextView tv_member_name,tv_member_grade,tv_member_mobile,tv_member_point,tv_member_birthday;
        MyImageViewWidget ivw_member_icon;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_member_name = (TextView) itemView.findViewById(R.id.tv_member_name);
            tv_member_grade= (TextView) itemView.findViewById(R.id.tv_member_grade);
            tv_member_mobile= (TextView) itemView.findViewById(R.id.tv_member_mobile);
            tv_member_point= (TextView) itemView.findViewById(R.id.tv_member_point);
            tv_member_birthday= (TextView) itemView.findViewById(R.id.tv_member_birthday);
            ivw_member_icon= (MyImageViewWidget) itemView.findViewById(R.id.ivw_member_icon);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String mobile,String point,String birthday,String grade,String imgUrl) {
            if(name!=null || name!=""){
                this.tv_member_name.setText(name);
            }else{
                this.tv_member_name.setText("找不到该会员");
            }

            if(birthday!=null){
                this.tv_member_birthday.setText("生日 "+birthday);
            }else{
                this.tv_member_birthday.setVisibility(View.GONE);
            }

            if(imgUrl!=null){
                Glide.with(mContext).load(imgUrl).into(this.ivw_member_icon);
            }else{
                Glide.with(mContext).load(imgUrl).error(R.drawable.no_picture).into(this.ivw_member_icon);
            }

            if(mobile!=null){
                this.tv_member_mobile.setText(mobile);
            }else{
                this.tv_member_mobile.setText("暂无手机号");
            }

            this.tv_member_grade.setText(grade);
            this.tv_member_point.setText("会员积分 "+point);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
