package com.cesaas.android.counselor.order.store.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplaySubmitBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * 门店陈列任务提交
 * Created by FGB on 2017/3/1.
 */

public class StoreDisplaySubmitNet extends BaseNet{
    public StoreDisplaySubmitNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/ShopDisplay/Submit";
    }

    public void setData(String DisplayId,String FlowId,String TaskId, String Remark, JSONArray Images){
        try{
            data.put("SheetId",DisplayId);
            data.put("FlowId",FlowId);
            data.put("TaskId",TaskId);
            data.put("Remark",Remark);
            data.put("Images",Images);
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
        ResultStoreDisplaySubmitBean bean=gson.fromJson(rJson,ResultStoreDisplaySubmitBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"陈列提交结果："+err+"=="+e);
    }
}
