package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.BaseBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultAttentionBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 关注/取消关注shop
 * Created 2017/4/27 21:02
 * Version 1.0
 */
public class AttentionShopNet extends BaseNet {
    public AttentionShopNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Style/Attention";
    }

    public void setData(int Id,int Status){
        try{
            data.put("Id",Id);
            data.put("Status",Status);//1关注  0：取消
            data.put("UserTicket",
                    AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultAttentionBean bean= JsonUtils.fromJson(rJson,ResultAttentionBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
