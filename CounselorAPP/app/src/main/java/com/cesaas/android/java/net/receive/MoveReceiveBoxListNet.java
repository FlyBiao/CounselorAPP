package com.cesaas.android.java.net.receive;

import android.content.Context;

import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.receive.ResultReceiveBoxListBean;
import com.cesaas.android.java.bean.receive.ResultReceiveBoxListsBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取箱列表 /收货
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveReceiveBoxListNet extends TestBaseNet {

    private int type;
    public MoveReceiveBoxListNet(Context context,int type) {
        super(context, true);
        this.type=type;
        this.uri="drp/receiveGetBoxList";
    }

    public void setData(long pId){
        try {
            data.put("pId",pId);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        if(type==1){
            ResultReceiveBoxListsBean bean= JsonUtils.fromJson(rJson,ResultReceiveBoxListsBean.class);
            EventBus.getDefault().post(bean);
        }else{
            ResultReceiveBoxListBean bean= JsonUtils.fromJson(rJson,ResultReceiveBoxListBean.class);
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
