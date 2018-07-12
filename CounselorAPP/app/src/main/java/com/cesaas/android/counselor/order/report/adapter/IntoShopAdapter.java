package com.cesaas.android.counselor.order.report.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.report.bean.IntoShopBean;
import com.cesaas.android.counselor.order.report.net.DeleteCustomersNet;
import com.cesaas.android.counselor.order.report.net.DeleteTryWearStyleDateNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.materialdialog.MaterialDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 进店Adapter
 * Created 2017/4/24 17:19
 * Version 1.zero
 */
public class IntoShopAdapter extends SwipeMenuAdapter<IntoShopAdapter.DefaultViewHolder>{

    private List<IntoShopBean> mIntoShopBeen;
    private OnItemClickListener mOnItemClickListener;
    private MaterialDialog mMaterialDialog;
    private Context ct;

    public IntoShopAdapter(List<IntoShopBean> mIntoShopBeen,Context ct){
        this.ct=ct;
        this.mIntoShopBeen=mIntoShopBeen;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_into_shop, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }


    @Override
    public void onBindViewHolder(DefaultViewHolder holder, final int position) {
        holder.setData(mIntoShopBeen.get(position).getNumOfCustomer(),mIntoShopBeen.get(position).getCreateTime());
        holder.setOnItemClickListener(mOnItemClickListener);

        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog=new MaterialDialog(ct);
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("温馨提示！")
                            .setMessage(
                                    "确定要删除该记录信息吗？")
                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    //执行删除操作
                                    DeleteCustomersNet customersNet=new DeleteCustomersNet(ct);
                                    customersNet.setData(mIntoShopBeen.get(position).getId());
                                }
                            })
                            .setNegativeButton("取消",
                                    new View.OnClickListener() {
                                        @Override public void onClick(View v) {
                                            mMaterialDialog.dismiss();
                                            ToastFactory.getLongToast(ct,"已取消删除！");
                                        }
                                    })
                            .setCanceledOnTouchOutside(true).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mIntoShopBeen == null ? 0 : mIntoShopBeen.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_into_shop_number,tv_into_shop_time;
        ImageView iv_del;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_into_shop_number = (TextView) itemView.findViewById(R.id.tv_into_shop_number);
            tv_into_shop_time= (TextView) itemView.findViewById(R.id.tv_into_shop_time);
            iv_del= (ImageView) itemView.findViewById(R.id.iv_del);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(int number,String time) {
            this.tv_into_shop_number.setText(number+"");
            this.tv_into_shop_time.setText(time);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
