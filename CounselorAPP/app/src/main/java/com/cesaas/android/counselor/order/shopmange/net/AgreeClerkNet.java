package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultAgreeClerkBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 审核店员
 * Created at 2017/6/19 17:43
 * Version 1.0
 */

public class AgreeClerkNet extends BaseNet {
    public AgreeClerkNet(Context context) {
        super(context, true);
        this.uri="User/SW/Counselor/Agree";
    }

    public void setData(int Counselor_Id){
        try{
            data.put("Counselor_Id",Counselor_Id);
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
        ResultAgreeClerkBean bean= JsonUtils.fromJson(rJson,ResultAgreeClerkBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"审核店员："+err);
    }
}
