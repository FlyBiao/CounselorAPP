package com.cesaas.android.counselor.order.member.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.adapter.shop.ScreenShopAdapter;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.VipListBean;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberQueryAdapter extends RecyclerView.Adapter<MemberQueryAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<VipListBean> mMyLiveList;
    private OnItemClickListener mOnItemClickListener;

    public MemberQueryAdapter(Context context) {
        this.context = context;
    }

    public void notifyAdapter(List<VipListBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<VipListBean> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public MemberQueryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_query, parent, false);
        MemberQueryAdapter.ViewHolder holder = new MemberQueryAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final MemberQueryAdapter.ViewHolder holder, final int position) {
        final VipListBean myLive = mMyLiveList.get(holder.getAdapterPosition());
        holder.tv_create_date.setText(R.string.fa_calendar);
        holder.tv_create_date.setTypeface( App.font);
        holder.tv_mobile.setText(R.string.fa_phone);
        holder.tv_mobile.setTypeface(App.font);
        holder.tv_birthday.setText(R.string.fa_birthday);
        holder.tv_birthday.setTypeface(App.font);
        holder.tv_amount.setText(R.string.fa_database);
        holder.tv_amount.setTypeface(App.font);

        holder.tv_vip_name.setText(myLive.getName());
        holder.tv_vip_grade.setText(myLive.getGradeTitle());
        holder.tv_member_mobile.setText(myLive.getPhone());
        holder.tv_sales_points.setText(myLive.getPoints()+"");
        if(myLive.getCreateDate()!=null && !"".equals(myLive.getCreateDate())){
            holder.tv_member_create_date.setText(AbDateUtil.getDateYMDs(myLive.getCreateDate()));
        }else{
            holder.tv_member_create_date.setText("");
        }

        if(myLive.getBrithday()!=null && !"".equals(myLive.getBrithday())){
            holder.tv_member_birthday.setText(AbDateUtil.getDateYMDs(myLive.getBrithday()));
        }else{
            holder.tv_member_birthday.setText("");
        }

        if(myLive.getImage()!=null && !"".equals(myLive.getImage()) && !"NULL".equals(myLive.getImage())){
            // 加载网络图片
            Glide.with(context).load(myLive.getImage()).crossFade().into(holder.ivw_member_icon);
        }else{
            holder.ivw_member_icon.setImageResource(R.mipmap.ic_launcher);
        }

        if (myLive.isSelect()) {
            holder.check_box.setImageResource(R.mipmap.check);
        } else {
            holder.check_box.setImageResource(R.mipmap.check_not);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyLiveList);
            }
        });
    }

    public void setOnItemClickListener(MemberQueryAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos,List<VipListBean> myLiveList);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_create_date)
        TextView tv_create_date;
        @BindView(R.id.tv_mobile)
        TextView tv_mobile;
        @BindView(R.id.tv_birthday)
        TextView tv_birthday;
        @BindView(R.id.tv_amount)
        TextView tv_amount;
        @BindView(R.id.tv_vip_name)
        TextView tv_vip_name;
        @BindView(R.id.tv_vip_grade)
        TextView tv_vip_grade;
        @BindView(R.id.tv_member_create_date)
        TextView tv_member_create_date;
        @BindView(R.id.tv_member_birthday)
        TextView tv_member_birthday;
        @BindView(R.id.tv_member_mobile)
        TextView tv_member_mobile;
        @BindView(R.id.tv_sales_points)
        TextView tv_sales_points;
        @BindView(R.id.check_box)
        ImageView check_box;
        private CircleImageView ivw_member_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            ivw_member_icon= (CircleImageView) itemView.findViewById(R.id.ivw_member_icon);
        }
    }
}

