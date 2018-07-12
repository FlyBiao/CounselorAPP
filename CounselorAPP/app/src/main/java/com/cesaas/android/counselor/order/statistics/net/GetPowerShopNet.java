package com.cesaas.android.counselor.order.statistics.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetAreaGainBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultSalesThanScreenBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import junit.framework.Test;

import org.json.JSONArray;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description  报表业务-获取店铺
 * Created 2017/3/16 15:32
 * Version 1.zero
 */
public class GetPowerShopNet extends BaseNet {
    public GetPowerShopNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/AchievementReport/GetPowerShop";
    }

    public void setData(){
        try{
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }


    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        CacheJsonBean strJson=new CacheJsonBean();
        strJson.setStrJson(rJson);
        Gson gson=new Gson();
        ResultSalesThanScreenBean bean=gson.fromJson(rJson,ResultSalesThanScreenBean.class);
        EventBus.getDefault().post(bean);
        EventBus.getDefault().post(strJson);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
