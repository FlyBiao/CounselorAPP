package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.pos.bean.ResultGetTicketInfoBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 优惠券查询
 * @author FGB
 *
 */
public class GetTicketInfoNet extends BaseNet{

	public GetTicketInfoNet(Context context) {
		super(context, true);
		this.uri = "Marketing/Sw/Ticket/GetTicketInfo";
	}

	public void setData(String UniqueCode) {
		try {
			
			data.put("UniqueCode", UniqueCode);
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson = new Gson();
		ResultGetTicketInfoBean lbean = gson.fromJson(rJson, ResultGetTicketInfoBean.class);
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			EventBus.getDefault().post(lbean);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "HttpException===" + e + "********=err==" + err);
	}

}
