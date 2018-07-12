package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultResetPwdBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultSalerSaleCompareBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 个人销售金额-首页
 * Created at 2018/3/19 10:04
 * Version 1.0
 */

public class SalerSaleCompareNet extends BaseNet {
    public SalerSaleCompareNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Report/SalerSaleCompare";
    }

    public void setData(String StartDate,String EndDate){
        try{
            data.put("StartDate",StartDate);
            data.put("EndDate",EndDate);
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
        ResultSalerSaleCompareBean bean= JsonUtils.fromJson(rJson,ResultSalerSaleCompareBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
