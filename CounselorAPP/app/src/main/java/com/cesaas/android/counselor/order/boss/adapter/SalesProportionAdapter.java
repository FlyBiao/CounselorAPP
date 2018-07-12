package com.cesaas.android.counselor.order.boss.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.SalesProportionBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/1/20 11:40
 * Version 1.0
 */

public class SalesProportionAdapter extends BaseQuickAdapter<SalesProportionBean, BaseViewHolder> {

    private List<SalesProportionBean> mData;

    public SalesProportionAdapter(List<SalesProportionBean> mData) {
        super( R.layout.item_proportion,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, SalesProportionBean item) {
        helper.setText(R.id.tv_category,item.getName());
        helper.setText(R.id.tv_sales_number,item.getNumber1()+"");
        helper.setText(R.id.tv_sales_proportion,item.getNumber2()+"%");
        helper.setText(R.id.tv_sku_proportion,item.getNumber3()+"%");
        helper.setText(R.id.tv_discount,item.getNumber4()+"%");

    }
}
