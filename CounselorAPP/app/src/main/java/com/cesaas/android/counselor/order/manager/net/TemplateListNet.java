package com.cesaas.android.counselor.order.manager.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.bean.ResultTemplateListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;


/**
 * Author FGB
 * Description TemplateList
 * Created at 2017/9/19 8:31
 * Version 1.0
 */

public class TemplateListNet extends BaseNet {
    public TemplateListNet(Context context) {
        super(context, true);
        this.uri="ShopTask/Sw/ShopTask/TemplateList";
    }

    public void setData() {
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
        Log.i(Constant.TAG, "TemplateListNet"+rJson);
        ResultTemplateListBean bean= JsonUtils.fromJson(rJson,ResultTemplateListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "TemplateListNet"+err);
    }
}
