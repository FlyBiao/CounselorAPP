package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultShopSaleCompareBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 店铺销售金额-首页
 * Created at 2018/3/19 10:03
 * Version 1.0
 */

public class ShopSaleCompareNet extends BaseNet {
    public ShopSaleCompareNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Report/ShopSaleCompare";
    }

    public void setData(String StartDate,String EndDate){
        try{
            data.put("StartDate",StartDate);
            data.put("EndDate",EndDate);
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }

        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultShopSaleCompareBean bean= JsonUtils.fromJson(rJson,ResultShopSaleCompareBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
