package com.cesaas.android.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.order.bean.ResultSendOfflineOrderBean;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2017/7/25 14:10
 * Version 1.0
 */

public class SendOfflineOrderNet extends BaseNet {
    public SendOfflineOrderNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/order/SendOfflineOrder";
    }

    public void setData(String OrderId,String ExpressId,String WayBillNo,double Freight,double Weight,String ExpressName){
        try {
            data.put("OrderId", OrderId);//订单号
            data.put("ExpressId", ExpressId);//物流公司编号
            data.put("WayBillNo", WayBillNo);//快递编号
            data.put("Freight", Freight);//邮费
            data.put("Weight", Weight);//重量
            data.put("ExpressName", ExpressName);//重量
            data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mPostNet(); // 开始请求网络
    }

    public void setData(int ExpressId,String ExpressName,String WayBillNo,int OrderId){
        try {
            data.put("ExpressId",ExpressId);
            data.put("ExpressName",ExpressName);
            data.put("WayBillNo",WayBillNo);
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
        Log.i(Constant.TAG,"err:"+rJson);
        ResultSendOfflineOrderBean bean= JsonUtils.fromJson(rJson,ResultSendOfflineOrderBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
