package com.cesaas.android.counselor.order.member.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionBean;
import com.cesaas.android.counselor.order.member.bean.PublicMemberBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 会员分配Adapter
 * Created 2017/4/11 18:33
 * Version 1.zero
 */
public class PublicMemberAdapter extends SwipeMenuAdapter<PublicMemberAdapter.DefaultViewHolder>{

    private List<PublicMemberBean> mMemberDistributionBeanList;
    private OnItemClickListener mOnItemClickListener;
    public static Context mContext;
    public static Activity mActivity;

    public PublicMemberAdapter(List<PublicMemberBean> mMemberDistributionBeanList, Context mContext){
        this.mContext=mContext;
        this.mMemberDistributionBeanList=mMemberDistributionBeanList;
    }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_public_member, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    //绑定界面，设置监听
    @Override
    public void onBindViewHolder(DefaultViewHolder holder,final int position) {

        holder.setData(mMemberDistributionBeanList.get(position).getNickName()
        ,mMemberDistributionBeanList.get(position).getTId()+""
        ,mMemberDistributionBeanList.get(position).getPoint()
        ,mMemberDistributionBeanList.get(position).getBirthday()
        ,mMemberDistributionBeanList.get(position).getCardName()
        ,mMemberDistributionBeanList.get(position).getImage());
        holder.setOnItemClickListener(mOnItemClickListener);


        holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.toString();
                // 调整选定条目
                if (isChecked== true) {

                } else {
                    mMemberDistributionBeanList.remove(position);
                }
            }
        });
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
        CheckBox cbCheckBox;
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
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String mobile,int point,String birthday,String grade,String imgUrl) {
            if(birthday!=null){
                this.tv_member_birthday.setText("生日 "+ AbDateUtil.toDateYMD(birthday));
            }else{
                this.tv_member_birthday.setText("暂无填写生日");
            }
            this.tv_member_name.setText(name);
            this.tv_member_mobile.setText(mobile);
            this.tv_member_grade.setText(grade);
            this.tv_member_point.setText(""+point);
            Glide.with(mContext).load(imgUrl).into(this.ivw_member_icon);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
