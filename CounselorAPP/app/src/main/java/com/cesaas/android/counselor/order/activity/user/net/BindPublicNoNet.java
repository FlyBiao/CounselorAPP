package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;

import com.cesaas.android.counselor.order.activity.user.bean.ResultBindPublicNoBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 绑定公众号Net
 * Created at 2017/5/16 18:22
 * Version 1.0
 */

public class BindPublicNoNet extends BaseNet {
    public BindPublicNoNet(Context context) {
        super(context, true);
        this.uri="User/SW/Counselor/BindWx";
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
        ResultBindPublicNoBean bean= JsonUtils.fromJson(rJson,ResultBindPublicNoBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
