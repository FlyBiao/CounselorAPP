package com.cesaas.android.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.order.bean.ResultOkOutOrderBean;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 20:41
 * Version 1.0
 */

public class OrderBackNet  extends BaseNet {

    public OrderBackNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/order/OrderBack";
    }

    public void setData(){
        try {
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"err:"+rJson);

    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
