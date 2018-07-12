package com.cesaas.android.order.net.retail;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.order.bean.retail.ResultRouteBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 发起店铺调货
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class RouteNet extends BaseNet {

    public RouteNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/Order/Create";
    }

    public void setData(String ShopId,String ShopNo,String VipMobile,
                        String RetailRemark,String Consignee,String City,String Province,
                        String District,String Address, JSONArray Item){
        try {
            data.put("OrderItem",Item);
            data.put("ShopId",ShopId);
            data.put("ShopNo",ShopNo);
            data.put("Mobile",VipMobile);
            data.put("RetailRemark",RetailRemark);
            data.put("Consignee",Consignee);
            data.put("City",City);
            data.put("Province",Province);
            data.put("District",District);
            data.put("Address",Address);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData(String VipMobile,
                        String RetailRemark,String Consignee,String City,String Province,
                        String District,String Address, JSONArray Item){
        try {
            data.put("OrderItem",Item);
            data.put("Mobile",VipMobile);
            data.put("RetailRemark",RetailRemark);
            data.put("Consignee",Consignee);
            data.put("City",City);
            data.put("Province",Province);
            data.put("District",District);
            data.put("Address",Address);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultRouteBean bean= JsonUtils.fromJson(rJson,ResultRouteBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
