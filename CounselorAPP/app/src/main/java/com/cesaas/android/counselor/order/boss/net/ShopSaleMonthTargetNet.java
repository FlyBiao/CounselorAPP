package com.cesaas.android.counselor.order.boss.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.boss.bean.ResultShopDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopMonthDataBean;
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
 * Description 店铺及业绩-店铺销售/目标【月】
 * Created at 2017/8/18 9:56
 * Version 1.0
 */

public class ShopSaleMonthTargetNet extends BaseNet {

    public ShopSaleMonthTargetNet(Context context) {
        super(context, true);
//        this.uri="Order/Sw/RetailReport/ShopSaleTarget";//已废弃
        this.uri="Order/Sw/RetailReport/ShopMonthSaleTarget";
    }

    public void setData(int ShopType) {
        try {
//            data.put("StartDate", AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"));
//            data.put("EndDate",AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
            data.put("ShopType",ShopType);//1直营，2加盟，3代理，4联营,5线上
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    public void setData() {
        try {
//            data.put("StartDate", AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"));
//            data.put("EndDate",AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultShopMonthDataBean bean= JsonUtils.fromJson(rJson,ResultShopMonthDataBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "店铺及业绩-店铺销售/目标【月】"+e+"==err==="+err);
    }
}
