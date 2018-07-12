package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultActivityResultBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：营销活动结果
 * 创建日期：2016/12/21 17:14
 * 版    本：1.zero
 * 修订历史：
 * ================================================
 */
public class GetActivityResultNet extends BaseNet {
    public GetActivityResultNet(Context context) {
        super(context, true);
        this.uri = "Order/Sw/PromotionActivity/GetActivityResult";
    }

    public void setData(int activityId,JSONArray styleArray) {
        try {
            data.put("ActivityId", activityId);
            data.put("Styles", styleArray);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        } catch (Exception e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson = new Gson();
        ResultActivityResultBean lbean = gson.fromJson(rJson, ResultActivityResultBean.class);
        EventBus.getDefault().post(lbean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "营销活动=" + e + "..=err=" + err);
    }
}
