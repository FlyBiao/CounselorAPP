package com.cesaas.android.counselor.order.boss.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.GroupBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 店铺业绩-店铺业绩排行[店铺列表]
 * Created at 2017/8/18 14:09
 * Version 1.0
 */
public class ShopListNet extends BaseNet {

    public ShopListNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/Shop/GetShopAll";
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
        Log.i("test","店铺列表："+rJson);
        ResultShopListBean bean=JsonUtils.fromJson(rJson,ResultShopListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "ShopSalesNet"+e+"==err==="+err);
    }
}
