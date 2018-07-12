package com.cesaas.android.counselor.order.shopmange.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shopmange.bean.ResultShopMatchBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 商品搭配Net
 * Created at 2017/5/16 16:15
 * Version 1.0
 */

public class ShopMatchNet extends BaseNet {
    public ShopMatchNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Collocation/GetList";
    }

    public void setData(int PageIndex,String SearchTxt,String Season){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",30);
            data.put("SearchTxt",SearchTxt);
            data.put("Season",Season);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultShopMatchBean bean= JsonUtils.fromJson(rJson,ResultShopMatchBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
