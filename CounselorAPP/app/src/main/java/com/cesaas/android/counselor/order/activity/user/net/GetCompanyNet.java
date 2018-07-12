package com.cesaas.android.counselor.order.activity.user.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultCompanyBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultMessageBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取公司信息以及授权码
 * Created at 2018/3/15 10:51
 * Version 1.0
 */

public class GetCompanyNet extends BaseNet {
    private ACache mCache;
    public GetCompanyNet(Context context,ACache mCache) {
        super(context, true);
        this.uri="user/sw/account/GetCompany";
        this.mCache=mCache;
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
        ResultCompanyBean bean= JsonUtils.fromJson(rJson,ResultCompanyBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,"消息err:"+err);
    }
}