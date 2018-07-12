package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultBigSortAllBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultSortAllBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取商品大类Net
 * Created at 2017/5/4 20:54
 * Version 1.0
 */

public class GetBigSortAllNet extends BaseNet {
    public GetBigSortAllNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Style/GetSortAll";
    }

    public void setData(int Type){
        try{
            data.put("Type",Type);//1：大类，2：小类
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
        Log.i(Constant.TAG,"大类:"+rJson);
        ResultBigSortAllBean bean= JsonUtils.fromJson(rJson,ResultBigSortAllBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
