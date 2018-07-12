package com.cesaas.android.order.adapter.retail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.VipListBean;
import com.cesaas.android.order.bean.retail.SearchByBarcodeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author FGB
 * Description
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class QueryShopAdapter extends RecyclerView.Adapter<QueryShopAdapter.ViewHolder> {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<SearchByBarcodeBean> mMyLiveList;
    private OnItemClickListener mOnItemClickListener;

    public QueryShopAdapter(Context context) {
        this.context = context;
    }

    public void notifyAdapter(List<SearchByBarcodeBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<SearchByBarcodeBean> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public QueryShopAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_retail_shop_query, parent, false);
        QueryShopAdapter.ViewHolder holder = new QueryShopAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final QueryShopAdapter.ViewHolder holder, final int position) {
        final SearchByBarcodeBean myLive = mMyLiveList.get(holder.getAdapterPosition());
        holder.tv_org_name.setText(mMyLiveList.get(position).getAreaName());
        if (mMyLiveList.get(position).getShopName()!=null && !"".equals(mMyLiveList.get(position).getShopName())){
            holder.tv_org_name.setText(mMyLiveList.get(position).getShopName());
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

    public void setOnItemClickListener(QueryShopAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<SearchByBarcodeBean> myLiveList);
    }
    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_org_name)
        TextView tv_org_name;
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.check_box)
        ImageView check_box;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

