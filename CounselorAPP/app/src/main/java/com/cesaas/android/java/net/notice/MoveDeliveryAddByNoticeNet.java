package com.cesaas.android.java.net.notice;

import android.content.Context;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.notice.ResultMoveDeliveryAddByNoticeBean;
import com.cesaas.android.java.bean.notice.ResultNoticeListListBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 按通知调拨
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class MoveDeliveryAddByNoticeNet extends TestBaseNet {

    private int type;
    public MoveDeliveryAddByNoticeNet(Context context,int type) {
        super(context, true);
        this.type=type;
        if(type==1){//退货
            this.uri="drp/refundDeliveryAddByNotice";
        }else{
            this.uri="drp/moveDeliveryAddByNotice";
        }
    }


    public void setData(long noticeId,String shipmentsDate,int kind,String remark){
        try {
            data.put("noticeId", noticeId);
            data.put("shipmentsDate", shipmentsDate);
            data.put("kind", kind);
            data.put("remark", remark);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultMoveDeliveryAddByNoticeBean bean= JsonUtils.fromJson(rJson,ResultMoveDeliveryAddByNoticeBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
