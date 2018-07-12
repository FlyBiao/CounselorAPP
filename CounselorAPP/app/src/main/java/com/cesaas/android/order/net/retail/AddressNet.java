package com.cesaas.android.order.net.retail;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

/**
 * Author FGB
 * Description 根据商品条码查询sku以及有货店铺列表信息
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class AddressNet extends BaseNet {

    public AddressNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/stock/address";
    }

    public void setData(){
        try {
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
