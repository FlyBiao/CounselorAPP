package com.cesaas.android.counselor.order.store.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * 店铺陈列列表Net
 * Created by FGB on 2017/3/1.
 */

public class StoreDisplayNet extends BaseNet{
    public StoreDisplayNet(Context context) {
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
        ResultStoreDisplayBean bean=gson.fromJson(rJson,ResultStoreDisplayBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"陈列列表："+err+"=="+e);
    }
}
