package com.cesaas.android.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.order.bean.ResultUnReceiveOrderBean;
import com.cesaas.android.order.bean.ResultUnReceiveOrderDetailBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 待接单
 * Created at 2017/7/24 17:00
 * Version 1.0
 */

public class UnReceiveOrderNet extends BaseNet {

    private int type;

    public UnReceiveOrderNet(Context context,int type) {
        super(context, true);
        this.uri="OrderRoute/sw/order/UnReceiveOrder";
        this.type=type;
    }

    public UnReceiveOrderNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/order/UnReceiveOrder";
    }

    public void setData(int page,JSONArray Sort){
        try {
            data.put("Sort", Sort);
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
        if(type==1){
            ResultUnReceiveOrderBean bean= JsonUtils.fromJson(rJson,ResultUnReceiveOrderBean.class);
            EventBus.getDefault().post(bean);
        }else if(type==2){
            ResultUnReceiveOrderDetailBean bean= JsonUtils.fromJson(rJson,ResultUnReceiveOrderDetailBean.class);
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
