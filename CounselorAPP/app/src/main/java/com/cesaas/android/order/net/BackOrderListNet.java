package com.cesaas.android.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.order.bean.BackOrderBean;
import com.cesaas.android.order.bean.ResultBackBean;
import com.cesaas.android.order.bean.ResultWaitInOrderBean;
import com.cesaas.android.order.bean.ResultWaitOutOrderBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 退单列表Net
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class BackOrderListNet extends BaseNet {

    public BackOrderListNet(Context context) {
        super(context, true);
        this.uri="OrderRoute/Sw/Order/GetByCounselor";
    }

    public void setData(int page,int OrderStatus,String start_date,String end_date,JSONArray Sort){
        try {
            data.put("OrderStatus",OrderStatus);//  3：退单
            data.put("start_date", start_date);
            data.put("end_date", end_date);
            data.put("Sort", Sort);
            data.put("PageIndex", page);
            data.put("PageSize", 50);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultBackBean bean= JsonUtils.fromJson(rJson,ResultBackBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
