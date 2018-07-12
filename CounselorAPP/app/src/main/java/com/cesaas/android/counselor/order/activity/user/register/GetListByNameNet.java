package com.cesaas.android.counselor.order.activity.user.register;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.ResultShopNameListBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取注册店铺列表Net
 * Created at 2017/5/6 14:34
 * Version 1.0
 */

public class GetListByNameNet extends BaseNet {
    public GetListByNameNet(Context context) {
        super(context, true);
        this.uri="Marketing/SW/Shop/GetListByName";
    }

    public void setData(String Name,String Code){
        try{
            data.put("Name", Name);//店铺名称
            data.put("Code", Code);//企业授权码
//            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }


    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        Log.i("test","=========企业名称:"+rJson);
        ResultShopNameListBean bean= JsonUtils.fromJson(rJson,ResultShopNameListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG,err);
    }
}
