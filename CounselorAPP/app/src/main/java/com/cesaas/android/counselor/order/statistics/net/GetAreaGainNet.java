package com.cesaas.android.counselor.order.statistics.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetAreaGainBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 报表业务-总销量对比
 * Created 2017/3/16 11:41
 * Version 1.zero
 */
public class GetAreaGainNet  extends BaseNet {
    public GetAreaGainNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/AchievementReport/GetAreaGain";
    }

    public void setData(JSONArray AR_Model){
        try{
            data.put("ShopIDS",AR_Model);
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
        ResultGetAreaGainBean bean=gson.fromJson(rJson,ResultGetAreaGainBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"销量对比:"+err+"=="+e);
    }
}
