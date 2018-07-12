package com.cesaas.android.counselor.order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.GroupBean;
import com.cesaas.android.counselor.order.bean.ResultGroupItemBeen;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 机构
 * Created at 2018/1/9 11:21
 * Version 1.0
 */

public class OrganzationAdapter extends SwipeMenuAdapter<OrganzationAdapter.DefaultViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<GroupBean> shopList;

    private static Context ct;

    public OrganzationAdapter(List<GroupBean> titles, Context ct) {
        this.shopList = titles;
        this.ct=ct;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return shopList == null ? 0 : shopList.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organzation, parent, false);
    }

    @Override
    public OrganzationAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrganzationAdapter.DefaultViewHolder holder, final int position) {
        holder.setData(shopList.get(position).groupInfo,shopList.get(position).getGroupItemBeen().size());
        holder.ll_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultGroupItemBeen resultGroupItemBeen = new ResultGroupItemBeen();
                resultGroupItemBeen.setShopListBeen(shopList.get(position).getGroupItemBeen());
                EventBus.getDefault().post(resultGroupItemBeen);
            }
        });
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cbCheckBox;
        TextView tv_organzation_name,tv_shop_count;
        LinearLayout ll_select;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            tv_organzation_name= (TextView) itemView.findViewById(R.id.tv_organzation_name);
            tv_shop_count= (TextView) itemView.findViewById(R.id.tv_shop_count);
            ll_select= (LinearLayout) itemView.findViewById(R.id.ll_select);
        }

        public void setData(String organzationName,int size) {
            if(organzationName!=null && !"".equals(organzationName)){
                this.tv_organzation_name.setText(organzationName);
            }else{
                this.tv_organzation_name.setText("其他");
            }

            this.tv_shop_count.setText(size+" 个店铺");
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
