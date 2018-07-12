package com.cesaas.android.counselor.order.boss.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.boss.bean.ResultGetShopMonthInfoBean;
import com.cesaas.android.counselor.order.boss.bean.ResultGetShopMonthSumBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取店铺目标达成情况列表
 * Created at 2017/8/18 9:56
 * Version 1.0
 */

public class GetShopMonthInfoNet extends BaseNet {

    public GetShopMonthInfoNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/SaleGoal/GetShopMonthInfo";
    }

    public void setData(String Year,String Month,int IsDistribution) {
        try {
            data.put("Year",Year);
            data.put("Month", Month);
            data.put("IsDistribution", IsDistribution);//是否分配（0全部，1是，2否）
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData(String Year, String Month, int IsDistribution, JSONArray ShopId) {
        try {
            data.put("Year",Year);
            data.put("Month", Month);
            data.put("IsDistribution", IsDistribution);//是否分配[0全部，1是，2否]
            data.put("ShopId", ShopId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetShopMonthInfoBean bean= JsonUtils.fromJson(rJson,ResultGetShopMonthInfoBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "ShopSalesNet"+e+"==err==="+err);
    }
}
