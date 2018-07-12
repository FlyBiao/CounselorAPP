package com.cesaas.android.counselor.order.member.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.ResultCounselorListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberServiceSelectAdapter extends BaseQuickAdapter<ResultCounselorListBean.CounselorListBean, BaseViewHolder> {

    private List<ResultCounselorListBean.CounselorListBean> mData;

    public MemberServiceSelectAdapter(List<ResultCounselorListBean.CounselorListBean> mData) {
        super( R.layout.item_member_service_select,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultCounselorListBean.CounselorListBean item) {
        helper.setText(R.id.tv_shoppers,item.getName());
//        helper.setTypeface(R.id.tv_create_date, App.font);

    }
}
