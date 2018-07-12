package com.cesaas.android.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.RsultReceivingOrderBean;
import com.cesaas.android.counselor.order.bean.RsultRejectOrderBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
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

public class RejectOrderNet extends BaseNet {

    public RejectOrderNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/order/reject";
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
        Gson gson = new Gson();
        RsultRejectOrderBean lbean = gson.fromJson(rJson, RsultRejectOrderBean.class);
        if (lbean.IsSuccess) {
            EventBus.getDefault().post(lbean);
        } else {
            ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
