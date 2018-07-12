package com.cesaas.android.counselor.order.store.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayDetailBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * 店铺陈列详情Net
 * Created by FGB on 2017/3/1.
 */

public class StoreDisplayDetailNet extends BaseNet{
    public StoreDisplayDetailNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/ShopDisplay/GetOne";
    }

    public void setData(String SheetId){
        try {
            data.put("FlowId",SheetId);
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
        ResultStoreDisplayDetailBean bean=gson.fromJson(rJson,ResultStoreDisplayDetailBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"陈列列表详情："+err+"=="+e);
    }
}
