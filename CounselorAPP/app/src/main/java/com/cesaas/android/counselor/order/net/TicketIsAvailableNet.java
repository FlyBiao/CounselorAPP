package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.pos.bean.ResultTicketIsAvailableBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 验证优惠券是否可以使用
 * @author FGB
 *
 */
public class TicketIsAvailableNet extends BaseNet{

	public TicketIsAvailableNet(Context context) {
		super(context, true);
		this.uri = "Marketing/Sw/Ticket/TicketIsAvailable";
	}

	public void setData(String  CouponId,JSONArray GoodsArray,double Discount) {
		try {
			data.put("CouponId", CouponId);//优惠券ID
			data.put("GoodsArray", GoodsArray);//商品列表
			data.put("Discount", Discount);//全局商品折扣
			data.put("UserTicket",
					AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson = new Gson();
		ResultTicketIsAvailableBean lbean = gson.fromJson(rJson, ResultTicketIsAvailableBean.class);
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
