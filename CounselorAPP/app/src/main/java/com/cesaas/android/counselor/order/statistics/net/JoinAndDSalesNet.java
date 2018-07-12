package com.cesaas.android.counselor.order.statistics.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.statistics.bean.ResultJoinAndDSalesBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import junit.framework.Test;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 直营加盟Net
 * Created 2017/3/14 16:37
 * Version 1.zero
 */
public class JoinAndDSalesNet extends BaseNet {
    public JoinAndDSalesNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/JoinInAndDirectSales/JoinAndDSales";
    }

    public void setData(){
        try{
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Gson gson=new Gson();
        ResultJoinAndDSalesBean bean=gson.fromJson(rJson,ResultJoinAndDSalesBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"直营加盟："+err+"=="+e);
    }
}
