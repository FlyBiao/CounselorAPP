package com.cesaas.android.counselor.order.activity.user.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.bean.CompanyBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/3/17 18:32
 * Version 1.0
 */

public class CompanyAdapter extends BaseQuickAdapter<CompanyBean, BaseViewHolder> {
    private List<CompanyBean> mData;

    public CompanyAdapter(List<CompanyBean> mData) {
        super( R.layout.item_company_info,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyBean item) {
       helper.setText(R.id.tv_company_name,item.getCompanyName());
    }
}
