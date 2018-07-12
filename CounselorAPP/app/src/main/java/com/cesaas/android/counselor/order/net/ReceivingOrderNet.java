package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.RsultReceivingOrderBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.RsultReceivingOrderDetailBean;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 *
 * @author fgb
 *
 */
public class ReceivingOrderNet extends BaseNet{

	private int type;
	public ReceivingOrderNet(Context context,int type) {
		super(context, true);
		this.uri="OrderRoute/sw/order/ReceivingOrder";
		this.type=type;
	}

	public ReceivingOrderNet(Context context) {
		super(context, true);
		this.uri="OrderRoute/sw/order/ReceivingOrder";
	}
	
	public void setData(int OrderId){
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
		if(type==1){
			RsultReceivingOrderBean lbean = JsonUtils.fromJson(rJson, RsultReceivingOrderBean.class);
			EventBus.getDefault().post(lbean);
		}else if(type==2){
			RsultReceivingOrderDetailBean lbean = JsonUtils.fromJson(rJson, RsultReceivingOrderDetailBean.class);
			EventBus.getDefault().post(lbean);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}

}
