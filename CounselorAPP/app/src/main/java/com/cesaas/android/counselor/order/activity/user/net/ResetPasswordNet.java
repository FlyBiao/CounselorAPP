package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;

import com.cesaas.android.counselor.order.activity.user.bean.ResultResetPwdBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 重置密码
 * Created at 2017/5/14 16:35
 * Version 1.0
 */

public class ResetPasswordNet extends BaseNet{
    public ResetPasswordNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Regist/Reset";
    }

    public void setData(String Code,String PassWord,String Mobile){
        try{
            data.put("Code",Code);
            data.put("PassWord",PassWord);
            data.put("Mobile",Mobile);
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
        ResultResetPwdBean bean= JsonUtils.fromJson(rJson,ResultResetPwdBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
