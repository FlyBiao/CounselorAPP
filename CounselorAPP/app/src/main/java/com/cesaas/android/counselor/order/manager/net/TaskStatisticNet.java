package com.cesaas.android.counselor.order.manager.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskStatisticBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 任务统计
 * Created at 2017/9/14 11:26
 * Version 1.0
 */

public class TaskStatisticNet extends BaseNet {
    public TaskStatisticNet(Context context) {
        super(context, true);
        this.uri="ShopTask/Sw/ShopTask/TaskStatistic";
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
        ResultTaskStatisticBean bean= JsonUtils.fromJson(rJson,ResultTaskStatisticBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "任务统计"+err);
    }
}
