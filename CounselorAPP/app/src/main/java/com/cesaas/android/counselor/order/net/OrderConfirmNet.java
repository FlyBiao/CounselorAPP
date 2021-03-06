package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultOrderConfirmBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 确认订单
 * 
 * @author FGB
 * 
 */
public class OrderConfirmNet extends BaseNet {

	public OrderConfirmNet(Context context) {
		super(context, true);
		this.uri = "Order/Sw/Order/ConfirmOrder";
	}

	public void setData(String OrderId,String OrderDistId) {
		try {

			data.put("OrderId", OrderId);
			data.put("OrderDistId", OrderDistId);
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
		ResultOrderConfirmBean lbean = gson.fromJson(rJson,
				ResultOrderConfirmBean.class);

		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "**=HttpException=" + e + "..=err=" + err);
	}

}
