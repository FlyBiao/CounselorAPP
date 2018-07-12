package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultReceivingFonfirmOrderBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 *  标记有货
 * @author fgb
 *
 */
public class ReceivingFonfirmOrderNet extends BaseNet{

	public ReceivingFonfirmOrderNet(Context context) {
		super(context, true);
		this.uri="Order/Sw/Order/ReceivingFonfirmOrder";
	}
	
	public void setData(String OrderId){
		try {
			data.put("OrderId", OrderId);
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
		ResultReceivingFonfirmOrderBean lbean = gson.fromJson(rJson, ResultReceivingFonfirmOrderBean.class);

		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			EventBus.getDefault().post(lbean);
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}

}
