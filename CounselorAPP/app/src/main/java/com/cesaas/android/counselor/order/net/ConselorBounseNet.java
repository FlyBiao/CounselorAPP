package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultConselorBounseBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取提现金额
 * Created at 2017/6/27 9:21
 * Version 1.0
 */

public class ConselorBounseNet extends BaseNet{
    public ConselorBounseNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Bonus/ConselorBounse";
    }


    public void setData(){
        try {
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet();
    }


    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultConselorBounseBean bean= JsonUtils.fromJson(rJson,ResultConselorBounseBean.class);
        EventBus.getDefault().post(bean);
    }


    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"提现ERROR："+err);
    }
}
