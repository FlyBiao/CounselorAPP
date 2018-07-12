package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultMySubscriptionBean;
import com.cesaas.android.counselor.order.report.bean.SubcriptionCacheJsonBean;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取我的订阅列表
 * Created 2017/3/25 14:33
 * Version 1.zero
 */
public class GetMyReportListNet extends BaseNet {
    public GetMyReportListNet(Context context) {
        super(context, true);
        this.uri="User/Sw/ReportCustom/GetMyReportList";
    }

    public void setData(int PageIndex,String QueryString){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",20);
            data.put("QueryString",QueryString);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        SubcriptionCacheJsonBean strJson=new SubcriptionCacheJsonBean();
        strJson.setStrJson(rJson);
        ResultMySubscriptionBean bean= JsonUtils.fromJson(rJson,ResultMySubscriptionBean.class);
        EventBus.getDefault().post(bean);
        EventBus.getDefault().post(strJson);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
