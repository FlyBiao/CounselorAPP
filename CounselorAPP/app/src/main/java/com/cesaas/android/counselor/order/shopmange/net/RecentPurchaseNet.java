package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultRecentPurchaseBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 最近购买商品Net
 * Created 2017/4/28 9:46
 * Version 1.0
 */
public class RecentPurchaseNet extends BaseNet{
    public RecentPurchaseNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Order/RecentPurchase";
    }

    public void setData(int PageIndex,int VipId){
        try{
            data.put("VipId",VipId);
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
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
        ResultRecentPurchaseBean bean= JsonUtils.fromJson(rJson,ResultRecentPurchaseBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
