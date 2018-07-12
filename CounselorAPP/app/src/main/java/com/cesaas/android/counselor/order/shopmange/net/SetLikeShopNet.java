package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultSetLikeShopBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 设置喜欢商品Net
 * Created 2017/4/28 17:07
 * Version 1.0
 */
public class SetLikeShopNet extends BaseNet {
    public SetLikeShopNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Collocation/SetLike";
    }

    public void setData(String IsLike,String Id){
        try{
            data.put("",IsLike);
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
        ResultSetLikeShopBean bean= JsonUtils.fromJson(rJson,ResultSetLikeShopBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
