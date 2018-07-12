package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultIntoShopBean;
import com.cesaas.android.counselor.order.report.bean.ResultIntoShopCountBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 进店登记查询Count
 * Created 2017/4/24 18:05
 * Version 1.zero
 */
public class GetCustomersCountNet extends BaseNet {
    public GetCustomersCountNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/Sw/NumberOfStores/GetCustomers";
    }

    public void setData(){
        try{
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultIntoShopCountBean bean= JsonUtils.fromJson(rJson,ResultIntoShopCountBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
