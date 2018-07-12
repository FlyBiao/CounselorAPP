package com.cesaas.android.counselor.order.member.net.service;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultCloseBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 终止会员服务
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class CloseNet extends BaseNet {
    public CloseNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Project/Close";
    }

    public void setData(int Id){
        try{
            data.put("Id",Id);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultCloseBean bean= JsonUtils.fromJson(rJson,ResultCloseBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
