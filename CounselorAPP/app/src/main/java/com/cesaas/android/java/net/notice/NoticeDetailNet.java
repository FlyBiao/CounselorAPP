package com.cesaas.android.java.net.notice;

import android.content.Context;

import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.TestBaseNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.java.bean.BaseUserSessionBean;
import com.cesaas.android.java.bean.notice.ResultNoticeDetailsBean;
import com.cesaas.android.java.bean.notice.ResultNoticeListListBean;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 通知单详情
 * Created at 2017/7/24 17:10
 * Version 1.0
 */

public class NoticeDetailNet extends TestBaseNet {

    public NoticeDetailNet(Context context) {
        super(context, true);
        this.uri="drp/noticeDetail";
    }


    public void setData(long id){
        try {
            data.put("id", id);
            data.put("session", BaseUserSessionBean.getUserSession());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mPostNet(); // 开始请求网络
    }

    @Override
    protected void mSuccess(String rJson) {
        super.mSuccess(rJson);
        ResultNoticeDetailsBean bean= JsonUtils.fromJson(rJson,ResultNoticeDetailsBean.class);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void mFail(HttpException e, String err) {
        super.mFail(e, err);

    }
}
