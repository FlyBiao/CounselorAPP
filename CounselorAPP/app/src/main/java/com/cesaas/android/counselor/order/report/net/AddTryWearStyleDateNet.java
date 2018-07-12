package com.cesaas.android.counselor.order.report.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultAddTryWearDateBean;
import com.cesaas.android.counselor.order.report.bean.ResultTryWearListDateBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 添加试穿款号数据
 * Created 2017/4/20 19:49
 * Version 1.zero
 */
public class AddTryWearStyleDateNet extends BaseNet {
    public AddTryWearStyleDateNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/StyleTry/Add";
        }

    public void setData(String BarcodeCode){
        try{

            data.put("BarcodeCode",BarcodeCode);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));

        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i(Constant.TAG,"试穿款号："+rJson);
        ResultAddTryWearDateBean bean= JsonUtils.fromJson(rJson,ResultAddTryWearDateBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}

