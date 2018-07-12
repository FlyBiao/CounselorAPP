package com.cesaas.android.java.adapter.move;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.java.bean.notice.NoticeListListBean;

import java.util.List;

/**
 * Author FGB
 * Description 参照通知调拨列表
 * Created at 2018/6/22 11:57
 * Version 1.0
 */

public class MoveDeliveryNoticeListAdapter extends RecyclerView.Adapter {

    public List<NoticeListListBean> list;

    public MoveDeliveryNoticeListAdapter(List<NoticeListListBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move_notice_list, parent, false);
        return new MoveDeliveryNoticeListAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MoveDeliveryNoticeListAdapter.MyHolder holder = (MoveDeliveryNoticeListAdapter.MyHolder) viewHolder;
        NoticeListListBean item = list.get(position);
        if (item.isChecked()) {
            holder.check_box.setImageResource(R.mipmap.check);
        } else {
            holder.check_box.setImageResource(R.mipmap.check_not);
        }
        holder.tv_no.setText(item.getNo());
        if(item.getCreateTime()!=null && !"".equals(item.getCreateTime())){
            holder.tv_date.setText(AbDateUtil.getDateYMDs(item.getCreateTime()));
        }
        holder.tv_send_shop.setText(item.getOriginOrganizationTitle()+"-"+item.getOriginShopName());
        holder.tv_receive_shop.setText(item.getReceiveOrganizationTitle()+"-"+item.getReceiveShopName());
        holder.tv_num.setText(String.valueOf(item.getNum()));

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView tv_no,tv_date,tv_send_shop,tv_receive_shop,tv_num;
        public ImageView check_box;

        public MyHolder(View itemView) {
            super(itemView);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_no = (TextView) itemView.findViewById(R.id.tv_no);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_send_shop = (TextView) itemView.findViewById(R.id.tv_send_shop);
            tv_receive_shop = (TextView) itemView.findViewById(R.id.tv_receive_shop);
            check_box= (ImageView) itemView.findViewById(R.id.check_box);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private MoveDeliveryNoticeListAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(MoveDeliveryNoticeListAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
