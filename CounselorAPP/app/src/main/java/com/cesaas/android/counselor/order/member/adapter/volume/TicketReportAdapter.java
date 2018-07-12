package com.cesaas.android.counselor.order.member.adapter.volume;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.TickeUseInfotReportBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 券报表
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class TicketReportAdapter extends BaseQuickAdapter<TickeUseInfotReportBean, BaseViewHolder> {

    private List<TickeUseInfotReportBean> mData;
    private Activity mActivity;
    private Context mContext;

    public TicketReportAdapter(List<TickeUseInfotReportBean> mData, Activity activity, Context context) {
        super( R.layout.item_ticket_report,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final TickeUseInfotReportBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_cared,item.getCardName());
        helper.setText(R.id.tv_money,"￥"+String.valueOf(item.getPrice()));
        helper.setText(R.id.tv_mobile,item.getMobile());
        helper.setText(R.id.tv_start_date, AbDateUtil.getDateYMDs(item.getStartDate()));
        helper.setText(R.id.tv_end_date,AbDateUtil.getDateYMDs(item.getEndDate()));
        if(item.getIsUsed()==1){
            helper.setText(R.id.tv_is_use,"已使用");
        }else{
            helper.setText(R.id.tv_is_use,"未使用");
        }

        //0现金券1礼品券2抵值券3折扣券
        switch (item.getTicketType()){
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
            default:
                helper.setText(R.id.tv_status,"未知券类型");
                break;
        }

        if(item.getVipIcon()!=null && !"".equals(item.getVipIcon()) && !"NULL".equals(item.getVipIcon())){
            // 加载网络图片
            Glide.with(mContext).load(item.getVipIcon()).crossFade().into((ImageView) helper.getView(R.id.ivw_user_icon));
        }else{
            helper.setImageResource(R.id.ivw_user_icon,R.mipmap.ic_launcher);
        }
    }
}
