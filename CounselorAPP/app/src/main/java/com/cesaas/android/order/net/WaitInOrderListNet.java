package com.cesaas.android.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.order.bean.ResultWaitInOrderBean;
import com.cesaas.android.order.bean.ResultWaitOutOrderBean;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class WaitInOrderListNet extends BaseNet {

    public WaitInOrderListNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/order/OrderList";
    }

    public void setData(int page,int OrderStatus){
        try {
//            data.put("OrderStatus",OrderStatus);//   0 : 待路由1：待接单 2：已接单 3：退回
            data.put("PageIndex", page);
            data.put("PageSize", 50);
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
        Gson gson=new Gson();
        ResultWaitInOrderBean bean=gson.fromJson(rJson,ResultWaitInOrderBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
