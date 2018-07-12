package com.cesaas.android.counselor.order.adapter;

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

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/9 11:21
 * Version 1.0
 */

public class QueryShopAdapter extends SwipeMenuAdapter<QueryShopAdapter.DefaultViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<ShopListBean> shopList;
    private List<SelectShopListBean> listBeen=new ArrayList<>();

    private SparseBooleanArray mCheckStates = new SparseBooleanArray();

    private static Context ct;

    public QueryShopAdapter(List<ShopListBean> titles,Context ct) {
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
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
    }

    @Override
    public QueryShopAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( QueryShopAdapter.DefaultViewHolder holder, final int position) {
        holder.setData(shopList.get(position).getShopName(),shopList.get(position).getAreaName(),shopList.get(position).isCheck());
        holder.cbCheckBox.setTag(position);
        holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int pos = (int) buttonView.getTag();
                // 调整选定条目
                if(isChecked){
                    mCheckStates.put(pos, true);
                    SelectShopListBean shopListBean=new SelectShopListBean();
                    shopListBean.setAreaId(shopList.get(position).getAreaId());
                    shopListBean.setAreaName(shopList.get(position).getAreaName());
                    shopListBean.setOrganizationId(shopList.get(position).getOrganizationId());
                    shopListBean.setOrganizationName(shopList.get(position).getOrganizationName());
                    shopListBean.setShopName(shopList.get(position).getShopName());
                    shopListBean.setJoinShop(shopList.get(position).getMobile());
                    shopListBean.setShopId(shopList.get(position).getShopId());
                    shopListBean.setPos(position);
                    listBeen.add(shopListBean);
                    EventBus.getDefault().post(listBeen);
                }else{
                    mCheckStates.delete(pos);
                    for (Iterator it = listBeen.iterator(); it.hasNext();){
                        SelectShopListBean value= (SelectShopListBean) it.next();
                        if(value.getPos()==position){
                            it.remove();
                        }
                    }
                    if(listBeen.size()!=0){
                        EventBus.getDefault().post(listBeen);
                    }
                }
            }
        });
        holder.cbCheckBox.setChecked(mCheckStates.get(position, false));
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckBox cbCheckBox;
        TextView tv_shop_name,tv_shop_join;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            tv_shop_name= (TextView) itemView.findViewById(R.id.tv_shop_name);
            tv_shop_join= (TextView) itemView.findViewById(R.id.tv_shop_join);
        }

        public void setData(String shopName,String areaName,boolean cb) {
            this.tv_shop_name.setText(shopName);
            this.tv_shop_join.setText(areaName);
            if(cb==true){
                this.cbCheckBox.setChecked(true);
            }else{
                this.cbCheckBox.setChecked(false);
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
