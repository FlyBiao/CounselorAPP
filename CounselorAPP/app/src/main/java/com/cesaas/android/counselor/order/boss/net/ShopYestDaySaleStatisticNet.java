package com.cesaas.android.counselor.order.boss.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.boss.bean.ResultCrmDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopYestDaySaleStatisticBean;
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
 * Description
 * Created at 2017/8/22 13:40
 * Version 1.0
 */

public class ShopYestDaySaleStatisticNet extends BaseNet {

    public ShopYestDaySaleStatisticNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/RetailReport/ShopYestDaySaleStatistic";
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
//        Log.i("test","ShopYestDaySaleStatisticNet==="+rJson);
        ResultShopYestDaySaleStatisticBean bean= JsonUtils.fromJson(rJson,ResultShopYestDaySaleStatisticBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "ShopSalesNet"+e+"==err==="+err);
    }
}
