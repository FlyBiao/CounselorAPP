package com.cesaas.android.order.net.retail;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.order.bean.retail.ResultRetailOrderBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 零售单查询
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class PosRetailOrderListNet extends BaseNet {

    public PosRetailOrderListNet(Context context) {
        super(context, true);
        this.uri="Order/Sw/Retail/PosOrderList";
    }

    public void setData(String start_date,String end_date,int page){
        try {
            data.put("PageIndex", page);
            data.put("PageSize", 50);
            data.put("start_date", start_date);//开始时间
            data.put("end_date", end_date);//结束时间
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(String start_date,String end_date,int page,int RetailSure,int RetailCheck){
        try {
            data.put("PageIndex", page);
            data.put("PageSize", 50);
            data.put("start_date", start_date);
            data.put("end_date", end_date);
            data.put("RetailSure", RetailSure);//0:未登账 1:已登账
            data.put("RetailCheck", RetailCheck);//0:未审核 1:审核
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultRetailOrderBean bean= JsonUtils.fromJson(rJson,ResultRetailOrderBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
