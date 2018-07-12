package com.cesaas.android.counselor.order.store.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.bean.ResultReadStoreTaskBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 阅读门店任务Net
 * Created 2017/3/6 11:17
 * Version 1.zero
 */
public class ReadStoreTaskNet extends BaseNet{
    public ReadStoreTaskNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/ShopDisplay/Read";
    }

    public void setData(String DisplayId,String FlowId,String TaskId){
        try{
            data.put("DisplayId",DisplayId);
            data.put("FlowId",FlowId);
            data.put("TaskId",TaskId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultReadStoreTaskBean bean=gson.fromJson(rJson,ResultReadStoreTaskBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"确定阅读任务："+err+"=="+e);
    }
}
