package com.cesaas.android.counselor.order.activity.main.adapter;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 接单列表
 * Created at 2018/1/20 11:40
 * Version 1.0
 */

public class MainOrderAdapter extends BaseQuickAdapter<UnReceiveOrderBean, BaseViewHolder> {

    private List<UnReceiveOrderBean> mData;

    public MainOrderAdapter(List<UnReceiveOrderBean> mData) {
        super( R.layout.item_main_order,mData);
        this.mData=mData;
    }

    @Override
    protected void convert(BaseViewHolder helper, UnReceiveOrderBean item) {
        helper.setText(R.id.tv_address,item.getAddress());
        helper.setText(R.id.tv_status,"待接单");
        helper.setText(R.id.tv_user,item.getConsignee());
        helper.setText(R.id.tv_mobile,item.getMobile());
        helper.setText(R.id.tv_create_time,AbDateUtil.getDateYMDs(item.getCreateTime()));
        helper.setText(R.id.tv_date, AbDateUtil.formats(item.getCreateTime()));
    }
}
