package com.cesaas.android.order.net.retail;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.order.bean.retail.ResultSearchByBarcodeBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 根据商品条码查询sku以及有货店铺列表信息
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class SearchByBarcodeNet extends BaseNet {

    public SearchByBarcodeNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/sw/Stock/SearchByBarcode";
    }

    public void setData(int Quantity, String BarcodeNo){
        try {
            data.put("BarcodeNo",BarcodeNo);
            data.put("Quantity",Quantity);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultSearchByBarcodeBean bean= JsonUtils.fromJson(rJson,ResultSearchByBarcodeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
