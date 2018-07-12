package com.cesaas.android.order.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.order.bean.ResultReceiveOrderDetailsBean;
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

public class ReceiveOrderDetailsNetTest extends BaseNet {

    public ReceiveOrderDetailsNetTest(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/order/OrderDetail";
    }

    public void setData(int OrderId){
        try {
            data.put("OrderId",OrderId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultReceiveOrderDetailsBean bean=gson.fromJson(rJson,ResultReceiveOrderDetailsBean.class);
        EventBus.getDefault().post(bean);

    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
