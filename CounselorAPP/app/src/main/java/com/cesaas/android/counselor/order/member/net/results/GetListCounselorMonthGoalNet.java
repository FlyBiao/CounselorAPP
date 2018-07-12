package com.cesaas.android.counselor.order.member.net.results;

import android.content.Context;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultGetListCounselorMonthGoalBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 查询店员月度目标分配情况
 * Created at 2018/3/10 15:37
 * Version 1.0
 */

public class GetListCounselorMonthGoalNet extends BaseNet {
    public GetListCounselorMonthGoalNet(Context context) {
        super(context, true);
        this.uri="Marketing/Sw/SaleGoal/GetListCounselorMonthGoal";
    }

    public void setData(String Date){
        try{
            data.put("Date",Date);
            data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
        }catch (Exception e){
            e.printStackTrace();
        }
        mPostNet();
    }
    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultGetListCounselorMonthGoalBean bean= JsonUtils.fromJson(rJson,ResultGetListCounselorMonthGoalBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);
    }
}
