package com.cesaas.android.counselor.order.member.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultVipOrderStatisticBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;


/**
 * Author FGB
 * Description 会员订单统计
 * Created at 2017/11/18 11:57
 * Version 1.0
 */

public class VipOrderStatisticNet extends BaseNet{
    public VipOrderStatisticNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/RetailReport/VipOrderStatistic";
    }

    public void setData(String VipId){
        try{
            data.put("VipId", VipId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultVipOrderStatisticBean bean= JsonUtils.fromJson(rJson,ResultVipOrderStatisticBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
