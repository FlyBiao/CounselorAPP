package com.cesaas.android.counselor.order.boss.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.boss.bean.ResultShopListBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultTaskChartListBean;
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
 * Description 会员服务PACD统计(服务类别图形)
 * Created at 2017/8/18 14:09
 * Version 1.0
 */
public class TaskChartListNet extends BaseNet {

    public TaskChartListNet(Context context) {
        super(context, true);
        this.uri="MemberTask/Sw/Task/TaskChartList";
    }

    public void setData(String FromDate, String ToDate, JSONArray ShopIds,int ServiceType) {
        try {
            data.put("FromDate",FromDate);
            data.put("ToDate",ToDate);
            data.put("ShopIds",ShopIds);
            data.put("ServiceType",ServiceType);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultTaskChartListBean bean=JsonUtils.fromJson(rJson,ResultTaskChartListBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
        Log.i(Constant.TAG, "ShopSalesNet"+e+"==err==="+err);
    }
}
