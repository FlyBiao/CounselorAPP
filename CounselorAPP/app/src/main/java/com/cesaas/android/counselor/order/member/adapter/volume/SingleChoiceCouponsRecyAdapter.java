package com.cesaas.android.counselor.order.member.adapter.volume;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.volume.TicketListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;

import java.util.List;


/**
 * Created by fgb on 2017/3/4.
 */

public class SingleChoiceCouponsRecyAdapter extends RecyclerView.Adapter {

    public List<TicketListBean> list;

    public SingleChoiceCouponsRecyAdapter(List<TicketListBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coupons, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        MyHolder holder = (MyHolder) viewHolder;
        TicketListBean item = list.get(position);
        if (item.isChecked()) {
            holder.check_box.setImageResource(R.mipmap.check);
        } else {
            holder.check_box.setImageResource(R.mipmap.check_not);
        }
        holder.tv_title.setText(item.getTICKET_TITLE());
        int number=item.getTICKET_NUMS()-item.getTICKET_GET_NUMS();
        holder.tv_number.setText(String.valueOf(number));

        if(item.getTICKET_TYPE()==1){//生日卷
            holder.tv_is_bir.setText(R.string.fa_birthday);
            holder.tv_is_bir.setTypeface(App.font);
//            holder.tv_birthday.setText("(生日)");
        }
        else{
            holder.tv_is_bir.setText("");
        }

        if(item.getTICKET_STARTDATE()!=null && !"".equals(item.getTICKET_STARTDATE())){
            holder.tv_start_date.setText(AbDateUtil.getDateYMDs(item.getTICKET_STARTDATE()));
        }else{
            holder.tv_start_date.setText("暂无时间");
        }
        if(item.getTICKET_ENDDATE()!=null && !"".equals(item.getTICKET_ENDDATE())){
            holder.tv_end_date.setText( AbDateUtil.getDateYMDs(item.getTICKET_ENDDATE()));
        }else{
            holder.tv_end_date.setText("暂无时间");
        }
        if (item.getTICKET_USEREMARK()!=null && !"".equals(item.getTICKET_USEREMARK())){
            holder.tv_remark.setText(item.getTICKET_USEREMARK());
        }else{
            holder.tv_remark.setText("暂无使用说明！");
        }

        if(item.getTICKET_RANDOM()==0){
            holder.ll_money.setVisibility(View.GONE);
            holder.tv_money.setVisibility(View.VISIBLE);
            holder.tv_money.setText("￥"+item.getTICKET_MONEY());
        }else{//随机金额
            holder.tv_money.setVisibility(View.GONE);
            holder.ll_money.setVisibility(View.VISIBLE);
            holder.tv_min_money.setText(String.valueOf(item.getTICKET_MIN_MONEY()));
            holder.tv_max_money.setText(String.valueOf(item.getTICKET_MAX_MONEY()));
        }

        switch (item.getTICKET_TRADETYPE()){
            //券类型:0:现金券 1:礼品券 2:抵值券 3:折扣券
            case 0:
                holder.tv_status.setText("现金券");
                break;
            case 1:
                holder.tv_status.setText("礼品券");
                break;
            case 2:
                holder.tv_status.setText("抵值券");
                break;
            case 3:
                holder.tv_status.setText("折扣券");
                break;
        }


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

        public TextView tv_is_bir,tv_birthday,tv_status,tv_start_date,tv_end_date,tv_number;
        public TextView tv_title,tv_money,tv_min_money,tv_max_money,tv_remark;
        private LinearLayout ll_money;
        public ImageView check_box;

        public MyHolder(View itemView) {
            super(itemView);
            check_box= (ImageView) itemView.findViewById(R.id.check_box);
            tv_is_bir = (TextView) itemView.findViewById(R.id.tv_is_bir);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_birthday = (TextView) itemView.findViewById(R.id.tv_birthday);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_start_date = (TextView) itemView.findViewById(R.id.tv_start_date);
            tv_end_date = (TextView) itemView.findViewById(R.id.tv_end_date);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_money = (TextView) itemView.findViewById(R.id.tv_money);
            tv_min_money = (TextView) itemView.findViewById(R.id.tv_min_money);
            tv_max_money = (TextView) itemView.findViewById(R.id.tv_max_money);
            tv_remark = (TextView) itemView.findViewById(R.id.tv_remark);
            ll_money = (LinearLayout) itemView.findViewById(R.id.ll_money);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
