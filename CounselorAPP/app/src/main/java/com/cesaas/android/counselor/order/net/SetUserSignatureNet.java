package com.cesaas.android.counselor.order.net;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.ResultSetUserSignatureBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created 2017/4/27 14:57
 * Version 1.0
 */
public class SetUserSignatureNet extends BaseNet {
    public SetUserSignatureNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Counselor/UpdateCounselorSignatture";
    }

    public void setData(String UserSignature){
        try{
            data.put("Content",UserSignature);
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
        ResultSetUserSignatureBean bean= JsonUtils.fromJson(rJson,ResultSetUserSignatureBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
