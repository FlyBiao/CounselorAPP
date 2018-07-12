package com.cesaas.android.counselor.order.report.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.report.bean.ResultDeleteTryWearDateBean;
import com.cesaas.android.counselor.order.report.bean.ResultTryWearListDateBean;
import com.cesaas.android.counselor.order.report.net.DeleteTryWearStyleDateNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.materialdialog.MaterialDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/4/20 20:05
 * Version 1.zero
 */
public class TryWearDataAdapter extends SwipeMenuAdapter<TryWearDataAdapter.DefaultViewHolder>{

    private List<ResultTryWearListDateBean.TryWearListDateBean> mTryWearListDateBeanList;
    private OnItemClickListener mOnItemClickListener;
    private MaterialDialog mMaterialDialog;
    private Context ct;

    public TryWearDataAdapter(List<ResultTryWearListDateBean.TryWearListDateBean> mTryWearListDateBeanList,Context ct){
        this.mTryWearListDateBeanList=mTryWearListDateBeanList;
        this.ct=ct;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_try_wear, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, final int position) {
        holder.setData(mTryWearListDateBeanList.get(position).getTitle(),mTryWearListDateBeanList.get(position).getCreateTime()
                ,mTryWearListDateBeanList.get(position).getCreateName(),mTryWearListDateBeanList.get(position).getNo()
        ,mTryWearListDateBeanList.get(position).getColor(),mTryWearListDateBeanList.get(position).getSize());
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
                                    DeleteTryWearStyleDateNet deleteTryWearStyleDateNet=new DeleteTryWearStyleDateNet(ct);
                                    deleteTryWearStyleDateNet.setData(mTryWearListDateBeanList.get(position).getId()+"");
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
        return mTryWearListDateBeanList == null ? 0 : mTryWearListDateBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_try_wear_goods,tv_try_wear_time,tv_try_wear_record;
        ImageView iv_del;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_try_wear_goods = (TextView) itemView.findViewById(R.id.tv_try_wear_goods);
            tv_try_wear_time= (TextView) itemView.findViewById(R.id.tv_try_wear_time);
            tv_try_wear_record = (TextView) itemView.findViewById(R.id.tv_try_wear_record);
            iv_del= (ImageView) itemView.findViewById(R.id.iv_del);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String time,String record,String code,String color,String size) {
            this.tv_try_wear_goods.setText(title+"("+code+")"+color+" "+size);
            this.tv_try_wear_time.setText(time);
            this.tv_try_wear_record.setText(record);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
