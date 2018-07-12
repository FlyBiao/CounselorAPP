package com.cesaas.android.counselor.order.workbench.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.shopmange.bean.RecentPurchaseBean;
import com.cesaas.android.counselor.order.signin.bean.SigninTypeBean;

import java.util.List;

/**
 * Author FGB
 * Description 已购买商品Adapter
 * Created 2017/3/20 11:02
 * Version 1.zero
 */
public class ReturnVisitGalleryRecyclerAdapter extends RecyclerView.Adapter<ReturnVisitGalleryRecyclerAdapter.ViewHolder> {

    private List<RecentPurchaseBean> url;
    private LayoutInflater mInflater;
    public Context mContext;

    public ReturnVisitGalleryRecyclerAdapter(Context context, List<RecentPurchaseBean> url){
        this.url=url;
        this.mContext=context;
        mInflater=LayoutInflater.from(context);
    }

    /**
     * 创建Item View  然后使用ViewHolder来进行承载
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view=mInflater.inflate(R.layout.item_return_visit_shop,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecyclerViewItemClickListener!=null){
                    onRecyclerViewItemClickListener.onItemClick(view,(int)view.getTag());
                }
            }
        });

        return viewHolder;
    }

    /**
     * 进行绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(url.get(position).getImageUrl()).into(holder.iv_return_shop_img);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return url.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    static class ViewHolder extends RecyclerView.ViewHolder {
         ImageView iv_return_shop_img;
        public ViewHolder(View view){
            super(view);
            iv_return_shop_img=(ImageView)view.findViewById(R.id.iv_return_shop_img);
        }
    }

    /**
     * 类似ListView的 onItemClickListener接口
     */
    public interface OnRecyclerViewItemClickListener{
        /**
         * Item View发生点击回调的方法
         * @param view   点击的View
         * @param position  具体Item View的索引
         */
        void onItemClick(View view, int position);
    }

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
