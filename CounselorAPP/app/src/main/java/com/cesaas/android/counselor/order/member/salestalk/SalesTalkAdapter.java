package com.cesaas.android.counselor.order.member.salestalk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 销售话术
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class SalesTalkAdapter extends BaseQuickAdapter<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean, BaseViewHolder> {

    private List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> mData;
    private Activity mActivity;
    private Context mContext;

    public SalesTalkAdapter(List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> mData, Activity activity, Context context) {
        super( R.layout.item_talk,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean item) {
        helper.setText(R.id.tv_content,item.Content);
    }
}
