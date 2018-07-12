package com.cesaas.android.java.net.require;

import android.content.Context;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.require.ResultRequireGoodsDetailBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 补货商品条码列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class RequireGoodsDetailNet extends TestBaseNet {

    public RequireGoodsDetailNet(Context context) {
        super(context, true);
        this.uri="drp/requireGoodsDetail";
    }

    public void setData(int pageIndex,JSONObject filterConditionList){
        try {
            data.put("filterConditionList", filterConditionList);
            data.put("start", pageIndex);
            data.put("limit", Constant.PAGE_SIZE);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultRequireGoodsDetailBean bean= JsonUtils.fromJson(rJson,ResultRequireGoodsDetailBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
