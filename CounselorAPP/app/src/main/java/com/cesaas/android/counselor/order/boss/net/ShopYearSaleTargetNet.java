package com.cesaas.android.counselor.order.boss.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.boss.bean.ResultShopDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopSalesBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取店铺年销售
 * Created at 2017/9/6 10:21
 * Version 1.0
 */

public class ShopYearSaleTargetNet extends BaseNet {

    public ShopYearSaleTargetNet(Context context) {
        super(context, true);
        this.uri="Order/sw/RetailReport/ShopYearSaleTarget";
    }

    public void setData(int ShopType) {
        try {
            data.put("ShopType",ShopType);//1直营，2加盟，3代理，4联营,5线上
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData() {
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
        ResultShopDataBean bean=JsonUtils.fromJson(rJson,ResultShopDataBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "获取店铺年销售"+e+"==err==="+err);
    }
}
