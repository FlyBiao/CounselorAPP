package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultExpressBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.OrderExpressNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 物流公司
 * @author fgb
 *
 */
public class ExpressNet extends OrderExpressNet{

	public ExpressNet(Context context) {
		super(context, true);
		this.uri="Express/Sw/Express";
	}
	
	public void setData(){
		try {
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Log.i("test","物流公司："+rJson);
		ResultExpressBean lbean = JsonUtils.fromJson(rJson, ResultExpressBean.class);
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}

}
