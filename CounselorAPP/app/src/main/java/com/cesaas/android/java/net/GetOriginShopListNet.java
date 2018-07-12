package com.cesaas.android.java.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.ResultSendShopBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 发货店铺列表Net
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class GetOriginShopListNet extends TestBaseNet {

    public GetOriginShopListNet(Context context) {
        super(context, true);
        this.uri="drp/getOriginShopList";
    }

    public void setData(){
        try {

            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultSendShopBean bean= JsonUtils.fromJson(rJson,ResultSendShopBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
