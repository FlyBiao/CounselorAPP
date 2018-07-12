package com.cesaas.android.order.net.retail;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.order.bean.retail.ResultStockRouteTypeBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 查询店铺订单调货方式
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class StockRouteTypeNet extends BaseNet {

    public StockRouteTypeNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/stock/StockRouteType";
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
        ResultStockRouteTypeBean bean=JsonUtils.fromJson(rJson,ResultStockRouteTypeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
