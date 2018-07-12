package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.report.bean.ResultIntoShopBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 进店登记查询
 * Created 2017/4/24 18:05
 * Version 1.zero
 */
public class GetCustomersNet extends BaseNet {
    public GetCustomersNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/Sw/NumberOfStores/GetCustomers";
    }

    public void setData(int PageIndex){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
//            data.put("Date", AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultIntoShopBean bean= JsonUtils.fromJson(rJson,ResultIntoShopBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
