package com.cesaas.android.counselor.order.task.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayBean;
import com.cesaas.android.counselor.order.store.bean.ResultTaskListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取任务列表
 * Created 2017/3/28 16:29
 * Version 1.zero
 */
public class GetTaskList extends BaseNet {
    public GetTaskList(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/ShopDisplay/GetList";
    }

    public void setData(int PageIndex){
        try {
            data.put("PageIndex",PageIndex);
            data.put("PageSize",20);
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
        ResultTaskListBean bean=gson.fromJson(rJson,ResultTaskListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"陈列列表："+err+"=="+e);
    }
}
