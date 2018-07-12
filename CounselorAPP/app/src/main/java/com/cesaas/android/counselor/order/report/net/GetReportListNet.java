package com.cesaas.android.counselor.order.report.net;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ReportCenterCacheJsonBean;
import com.cesaas.android.counselor.order.report.bean.ResultReportCenterBean;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 获取报表中心报表列表net
 * Created 2017/3/25 14:32
 * Version 1.zero
 */
public class GetReportListNet extends BaseNet {
    public GetReportListNet(Context context) {
        super(context, true);
        this.uri="User/Sw/ReportCustom/GetReportList";
    }

    /**
     *
     * @param PageIndex 当前页
     * @param QueryString 查询字符串
     * @param BigSortId 大类Id
     * @param SmallSortId 小类Id
     */
    public void setData(int PageIndex,String QueryString,int BigSortId,int SmallSortId){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",20);
            data.put("QueryString",QueryString);
            data.put("BigSortId",BigSortId);
            data.put("SmallSortId",SmallSortId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }

    public void setData(int PageIndex,String QueryString,int BigSortId){
        try{
            data.put("PageIndex",PageIndex);
            data.put("PageSize",20);
            data.put("QueryString",QueryString);
            data.put("BigSortId",BigSortId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
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
        ReportCenterCacheJsonBean strJson=new ReportCenterCacheJsonBean();
        strJson.setStrJson(rJson);
        ResultReportCenterBean bean= JsonUtils.fromJson(rJson,ResultReportCenterBean.class);
        EventBus.getDefault().post(bean);
        EventBus.getDefault().post(strJson);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
