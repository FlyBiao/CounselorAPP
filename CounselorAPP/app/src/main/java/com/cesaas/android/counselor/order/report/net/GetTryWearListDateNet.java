package com.cesaas.android.counselor.order.report.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultTryWearListDateBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取试穿分析数据
 * Created 2017/4/20 19:49
 * Version 1.zero
 */
public class GetTryWearListDateNet extends BaseNet {
    public GetTryWearListDateNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/StyleTry/GetList";
        }

    public void setData(int PageIndex){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultTryWearListDateBean bean= JsonUtils.fromJson(rJson,ResultTryWearListDateBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}

