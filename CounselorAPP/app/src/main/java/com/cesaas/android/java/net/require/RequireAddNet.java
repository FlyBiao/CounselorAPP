package com.cesaas.android.java.net.require;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.require.ResultRequireAddBean;
import com.cesaas.android.java.bean.require.ResultRequireConfirmBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 新增补货
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class RequireAddNet extends TestBaseNet {

    public RequireAddNet(Context context) {
        super(context, true);
            this.uri="drp/requireAdd";
    }

    public void setData(int addType, JSONObject model){
        try {
            data.put("model",model);
            data.put("addType",addType);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultRequireAddBean bean= JsonUtils.fromJson(rJson,ResultRequireAddBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
