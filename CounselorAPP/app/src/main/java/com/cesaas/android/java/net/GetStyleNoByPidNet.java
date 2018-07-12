package com.cesaas.android.java.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.inventory.ResultGetStyleNoByPidBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 根据pid获取款号列表
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class GetStyleNoByPidNet extends TestBaseNet {

    public GetStyleNoByPidNet(Context context) {
        super(context, true);
        this.uri="drp/getStyleNoByPid";
    }

    public void setData(int pageIndex,long pid,long id){
        try {
            data.put("pId", pid);
            data.put("id", id);
            data.put("start", pageIndex);
            data.put("limit", Constant.PAGE_SIZE);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData(int pageIndex,long pid){
        try {
            data.put("pId", pid);
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
        ResultGetStyleNoByPidBean bean= JsonUtils.fromJson(rJson,ResultGetStyleNoByPidBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
