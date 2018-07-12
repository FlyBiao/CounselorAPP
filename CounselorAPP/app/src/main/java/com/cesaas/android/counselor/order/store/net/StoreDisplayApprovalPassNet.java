package com.cesaas.android.counselor.order.store.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayApprovaBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayApprovaPassBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * 门店陈列审核不通过Net
 * Created by FGB on 2017/3/1.
 */

public class StoreDisplayApprovalPassNet extends BaseNet{
    public StoreDisplayApprovalPassNet(Context context) {
        super(context, true);
        this.uri="ShopBusiness/SW/ShopDisplay/Check";
    }
    public void setData(String SheetId, String Review,String FlowId,String TaskId, JSONArray Images){
        try{
            data.put("SheetId",SheetId);//陈列任务Id
            data.put("Review",Review);//总评价
            data.put("Images",Images);//总评价
            data.put("Status",0);
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
        ResultStoreDisplayApprovaPassBean bean=gson.fromJson(rJson,ResultStoreDisplayApprovaPassBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"门店陈列审核不通过："+err+"=="+e);
    }
}
