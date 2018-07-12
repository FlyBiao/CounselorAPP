package com.cesaas.android.counselor.order.shopmange.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shoptask.bean.CounselorListBean;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 店员管理Adapter
 * Created 2017/4/20 18:46
 * Version 1.zero
 */
public class ClerkMangeAdapter extends SwipeMenuAdapter<ClerkMangeAdapter.DefaultViewHolder>{

    private List<CounselorListBean>  mShopTaskListBeanList;
    private List<SelectCounselorListBean> selectCounselorListBeen=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;
    static Typeface font;

    public ClerkMangeAdapter(List<CounselorListBean>  mShopTaskListBeanList, Context mContext,Typeface font){
        this.mShopTaskListBeanList=mShopTaskListBeanList;
        this.mContext=mContext;
        this.font=font;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clerk_manger, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {
        holder.setData(mShopTaskListBeanList.get(position).getCOUNSELOR_NICKNAME(),mShopTaskListBeanList.get(position).getSHOP_NAME(),
                        mShopTaskListBeanList.get(position).getCOUNSELOR_LEVELNAME(),mShopTaskListBeanList.get(position).getCOUNSELOR_MOBILE(),
                        mShopTaskListBeanList.get(position).getFANS_NUM(),mShopTaskListBeanList.get(position).getCOUNSELOR_ICON(),mShopTaskListBeanList.get(position).getCOUNSELOR_INUSE());
        holder.setOnItemClickListener(mOnItemClickListener);

        holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.toString();
                // 调整选定条目
                if (isChecked== true) {
                    SelectCounselorListBean bean=new SelectCounselorListBean();
                    bean.setCOUNSELOR_ID(mShopTaskListBeanList.get(position).getCOUNSELOR_ID());
                    bean.setCOUNSELOR_NICKNAME(mShopTaskListBeanList.get(position).getCOUNSELOR_NICKNAME());
                    bean.setCOUNSELOR_ICON(mShopTaskListBeanList.get(position).getCOUNSELOR_ICON());
                    bean.setPosition(position);
                    selectCounselorListBeen.add(bean);
                    EventBus.getDefault().post(selectCounselorListBeen);
                } else {
                    for (Iterator it = selectCounselorListBeen.iterator(); it.hasNext();){
                        SelectCounselorListBean value= (SelectCounselorListBean) it.next();
                        if(value.getPosition()==position){
                            it.remove();
                        }
                    }
                    EventBus.getDefault().post(selectCounselorListBeen);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShopTaskListBeanList == null ? 0 : mShopTaskListBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_contact_name,tv_contact_shop_name,tv_clerk_grade,tv_contact_mobile,tv_clerk_count,tv_icon_mobile,tv_icon_user,tv_clerk_status;
        CircleImageView ivw_contact_icon;
        OnItemClickListener mOnItemClickListener;
        CheckBox cbCheckBox;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_contact_name = (TextView) itemView.findViewById(R.id.tv_contact_name);
            tv_contact_shop_name= (TextView) itemView.findViewById(R.id.tv_contact_shop_name);
            tv_clerk_grade= (TextView) itemView.findViewById(R.id.tv_clerk_grade);
            tv_contact_mobile= (TextView) itemView.findViewById(R.id.tv_contact_mobile);
            tv_clerk_count= (TextView) itemView.findViewById(R.id.tv_clerk_count);
            tv_icon_mobile= (TextView) itemView.findViewById(R.id.tv_icon_mobile);
            tv_icon_user= (TextView) itemView.findViewById(R.id.tv_icon_user);
            tv_clerk_status= (TextView) itemView.findViewById(R.id.tv_clerk_status);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            ivw_contact_icon = (CircleImageView) itemView.findViewById(R.id.ivw_contact_icon);
            tv_icon_mobile.setTypeface(font);
            tv_icon_mobile.setText(R.string.mobile);
            tv_icon_user.setTypeface(font);
            tv_icon_user.setText(R.string.user);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String contactName,String contactShopName,String grade,String mobile,int clerkCount,String icon,int status) {
            this.tv_contact_name.setText(contactName);
            this.tv_clerk_grade.setText(grade);
            this.tv_contact_mobile.setText(mobile);
            this.tv_clerk_count.setText(clerkCount+"");
            switch (status){
                case 1:
                    this.tv_clerk_status.setText("在职");
                    this.tv_clerk_status.setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
                    break;
                case 0:
                    this.tv_clerk_status.setText("离职");
                    this.tv_clerk_status.setTextColor(mContext.getResources().getColor(R.color.white_pressed));
                    break;
                case -1:
                    this.tv_clerk_status.setText("申请中");
                    this.tv_clerk_status.setTextColor(mContext.getResources().getColor(R.color.result_points));
                    break;
            }
            if(contactShopName!=null && !"".equals(contactShopName)){
                this.tv_contact_shop_name.setText(contactShopName);
            }else{
                this.tv_contact_shop_name.setText("找不到该店员店铺");
                tv_contact_shop_name.setTextColor(mContext.getResources().getColor(R.color.hui));
            }
            if(icon!=null && !"".equals(icon)){
                Glide.with(mContext).load(icon).into(this.ivw_contact_icon);
            }else{
                this.ivw_contact_icon.setImageResource(R.mipmap.not_user_icon);
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
