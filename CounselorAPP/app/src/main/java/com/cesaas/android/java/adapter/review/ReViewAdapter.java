package com.cesaas.android.java.adapter.review;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.cesaas.android.java.bean.review.GetEvaluationModelBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 评价
 * Created at 2017/8/28 9:51
 * Version 1.0
 */

public class ReViewAdapter extends BaseQuickAdapter<GetEvaluationModelBean, BaseViewHolder> {

    private List<GetEvaluationModelBean> mData;
    private Context mContext;
    private  Activity mActivity;

    private RecyclerView rv_review_item;
    private ReViewItemAdapter reViewItemAdapter;

    public ReViewAdapter(List<GetEvaluationModelBean> mData, Context ct, Activity activity) {
        super( R.layout.item_review,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=ct;
    }

    @Override
    protected void convert(BaseViewHolder helper, final GetEvaluationModelBean item) {
        helper.setText(R.id.tv_score1,R.string.fa_star_o);
        helper.setTypeface(R.id.tv_score1, App.font);
        helper.setText(R.id.tv_score2,R.string.fa_star_o);
        helper.setTypeface(R.id.tv_score2, App.font);
        helper.setText(R.id.tv_score3,R.string.fa_star_o);
        helper.setTypeface(R.id.tv_score3, App.font);
        helper.setText(R.id.tv_score4,R.string.fa_star_o);
        helper.setTypeface(R.id.tv_score4, App.font);
        helper.setText(R.id.tv_score5,R.string.fa_star_o);
        helper.setTypeface(R.id.tv_score5, App.font);
        helper.setText(R.id.tv_review_icon,R.string.fa_commenting_o);
        helper.setTypeface(R.id.tv_review_icon, App.font);

        helper.setText(R.id.tv_create_name,item.getClientName());
        helper.setText(R.id.tv_suggestion,item.getSuggestion());

        if(item.getTotalValue()>=0){
            helper.setText(R.id.tv_rateValue,"+"+item.getTotalValue());
            helper.setTextColor(R.id.tv_rateValue,mContext.getResources().getColor(R.color.red));
        }else{
            helper.setText(R.id.tv_rateValue,"-"+item.getTotalValue());
            helper.setTextColor(R.id.tv_rateValue,mContext.getResources().getColor(R.color.colorAccent1));
        }

        if(item.getCreateTime()!=null && !"".equals(item.getCreateTime())){
            helper.setText(R.id.review_date, AbDateUtil.getDateYMDs(item.getCreateTime()));
            helper.setText(R.id.review_time,AbDateUtil.formats(item.getCreateTime()));
        }else{
            helper.setText(R.id.review_date,"暂无日期");
        }

        switch (item.getRateValue()){
            case 1:
                helper.setText(R.id.tv_score1,R.string.fa_star);
                break;
            case 2:
                helper.setText(R.id.tv_score1,R.string.fa_star);
                helper.setText(R.id.tv_score2,R.string.fa_star);
                break;
            case 3:
                helper.setText(R.id.tv_score1,R.string.fa_star);
                helper.setText(R.id.tv_score2,R.string.fa_star);
                helper.setText(R.id.tv_score3,R.string.fa_star);
                break;
            case 4:
                helper.setText(R.id.tv_score1,R.string.fa_star);
                helper.setText(R.id.tv_score2,R.string.fa_star);
                helper.setText(R.id.tv_score3,R.string.fa_star);
                helper.setText(R.id.tv_score4,R.string.fa_star);
                break;
            case 5:
                helper.setText(R.id.tv_score1,R.string.fa_star);
                helper.setText(R.id.tv_score2,R.string.fa_star);
                helper.setText(R.id.tv_score3,R.string.fa_star);
                helper.setText(R.id.tv_score4,R.string.fa_star);
                helper.setText(R.id.tv_score5,R.string.fa_star);
                break;
        }

        rv_review_item=helper.getView(R.id.rv_review_item);
        rv_review_item.setLayoutManager(new LinearLayoutManager(mContext));
        reViewItemAdapter=new ReViewItemAdapter(item.getRecords().getValue(),mContext,mActivity);
        rv_review_item.setAdapter(reViewItemAdapter);



    }
}
