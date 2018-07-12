package com.cesaas.android.counselor.order.member.net.results;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultAddCounselorMonthGoalBean;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultGetListCounselorDayGoalBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 查询店员每日目标分配情况
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class GetListCounselorDayGoalNet extends BaseNet {
    public GetListCounselorDayGoalNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/SaleGoal/GetListCounselorDayGoal";
    }

    public void setData(String Date,int CounselorId){
        try{
            data.put("Date",Date);
            data.put("CounselorId",CounselorId);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetListCounselorDayGoalBean bean= JsonUtils.fromJson(rJson,ResultGetListCounselorDayGoalBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
