package com.cesaas.android.counselor.order.manager.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskDetailsBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 任务工作
 * Created at 2017/9/14 11:22
 * Version 1.0
 */

public class TaskWorkListNet extends BaseNet{
    public TaskWorkListNet(Context context) {
        super(context, true);
        this.uri="ShopTask/Sw/ShopTask/TaskWork";
    }

    public void setData(int TaskId,int Status,int PageIndex) {
        try {
            data.put("TaskId",TaskId);
            data.put("Status",Status);
            data.put("PageSize",30);
            data.put("PageIndex",PageIndex);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG, "worklist"+rJson);
        ResultTaskDetailsBean bean= JsonUtils.fromJson(rJson,ResultTaskDetailsBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "worklist"+err);
    }
}
