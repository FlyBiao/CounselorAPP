package com.cesaas.android.counselor.order.member.adapter.volume;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.TicketListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 优惠券
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class CouponsAdapter extends BaseQuickAdapter<TicketListBean, BaseViewHolder> {

    private List<TicketListBean> mData=new ArrayList<>();
    private Activity mActivity;
    private Context mContext;

    private MyOnItemClickListener myOnItemClickListener;

    public CouponsAdapter(List<TicketListBean> mData, Activity activity, Context context) {
        super( R.layout.item_coupons,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TicketListBean item) {
        TicketListBean myLive=mData.get(helper.getAdapterPosition());
        helper.setText(R.id.tv_title,item.getTICKET_TITLE());
        int number=item.getTICKET_NUMS()-item.getTICKET_GET_NUMS();
        helper.setText(R.id.tv_number,String.valueOf(number));

        if(item.getTICKET_TYPE()==1){//生日卷
            helper.setText(R.id.tv_is_bir,R.string.fa_birthday);
            helper.setTypeface(R.id.tv_is_bir, App.font);
            helper.setText(R.id.tv_birthday,"(生日)");
        }else{
            helper.setText(R.id.tv_is_bir,"");
        }

        if(item.getTICKET_STARTDATE()!=null && !"".equals(item.getTICKET_STARTDATE())){
            helper.setText(R.id.tv_start_date, AbDateUtil.getDateYMDs(item.getTICKET_STARTDATE()));
        }else{
            helper.setText(R.id.tv_start_date, "暂无时间");
        }
        if(item.getTICKET_ENDDATE()!=null && !"".equals(item.getTICKET_ENDDATE())){
            helper.setText(R.id.tv_end_date, AbDateUtil.getDateYMDs(item.getTICKET_ENDDATE()));
        }else{
            helper.setText(R.id.tv_end_date, "暂无时间");
        }
        if (item.getTICKET_USEREMARK()!=null && !"".equals(item.getTICKET_USEREMARK())){
            helper.setText(R.id.tv_remark,item.getTICKET_USEREMARK());
        }else{
            helper.setText(R.id.tv_remark,"暂无使用说明！");
        }

        if(item.getTICKET_RANDOM()==0){
            helper.setVisible(R.id.ll_money,false);
            helper.setVisible(R.id.tv_money,true);
            helper.setText(R.id.tv_money,"￥"+item.getTICKET_MONEY());
        }else{//随机金额
            helper.setVisible(R.id.tv_money,false);
            helper.setVisible(R.id.ll_money,true);
            helper.setText(R.id.tv_min_money,String.valueOf(item.getTICKET_MIN_MONEY()));
            helper.setText(R.id.tv_max_money,String.valueOf(item.getTICKET_MAX_MONEY()));
        }

        switch (item.getTICKET_TRADETYPE()){
            //券类型:0:现金券 1:礼品券 2:抵值券 3:折扣券
            case 0:
                helper.setText(R.id.tv_status,"现金券");
                break;
            case 1:
                helper.setText(R.id.tv_status,"礼品券");
                break;
            case 2:
                helper.setText(R.id.tv_status,"抵值券");
                break;
            case 3:
                helper.setText(R.id.tv_status,"折扣券");
                break;
        }

        if (myLive.isSelect()) {
            helper.setImageResource(R.id.check_box,R.mipmap.check);
        } else {
            helper.setImageResource(R.id.check_box,R.mipmap.check_not);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemClickListener.onItemClickListener(helper.getAdapterPosition(), mData);
            }
        });
    }

    public void notifyAdapter(List<TicketListBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mData = myLiveList;
        } else {
            this.mData.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<TicketListBean> getMyLiveList() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        return mData;
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public interface MyOnItemClickListener {
        void onItemClickListener(int pos, List<TicketListBean> myLiveList);
    }
}
