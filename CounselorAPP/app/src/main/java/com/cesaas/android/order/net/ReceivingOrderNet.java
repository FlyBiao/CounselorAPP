package com.cesaas.android.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 19:20
 * Version 1.0
 */

public class ReceivingOrderNet extends BaseNet {

    public ReceivingOrderNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/order/ReceivingOrder";
    }

    public void setData(int page,int OrderId){
        try {
            data.put("OrderId",OrderId);// id
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
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
