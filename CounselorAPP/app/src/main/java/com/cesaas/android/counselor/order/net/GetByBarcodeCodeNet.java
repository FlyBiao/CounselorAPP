package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetByBarcodeCodeBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取商品条码
 * @author FGB
 *
 */
public class GetByBarcodeCodeNet extends BaseNet{

	public GetByBarcodeCodeNet(Context context) {
		super(context, true);
		this.uri="Marketing/Sw/Style/GetByBarcodeCode";
	}
	
	public void setData(long BarcodeCode) {
		try {
			data.put("BarcodeCode",BarcodeCode);
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}

	public void setData(String BarcodeCode) {
		try {
			data.put("BarcodeCode",BarcodeCode);
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
		ResultGetByBarcodeCodeBean lbean = gson.fromJson(rJson, ResultGetByBarcodeCodeBean.class);
		EventBus.getDefault().post(lbean);

	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "SetCounselor===" + e + "********=err==" + err);
	}

}
