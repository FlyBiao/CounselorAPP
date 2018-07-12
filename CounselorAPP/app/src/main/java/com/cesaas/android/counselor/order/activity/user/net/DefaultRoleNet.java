package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultCompanyBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultDefaultRoleBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description DefaultRole
 * Created at 2018/3/15 10:51
 * Version 1.0
 */

public class DefaultRoleNet extends BaseNet {
    public DefaultRoleNet(Context context) {
        super(context, true);
        this.uri="User/Sw/Account/DefaultRole";
    }

    public void setData(String authCode){
        try{
            data.put("authCode",authCode);
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
        ResultDefaultRoleBean bean= JsonUtils.fromJson(rJson,ResultDefaultRoleBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"消息err:"+err);
    }
}