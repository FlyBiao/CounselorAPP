package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.CargoInfo;
import com.cesaas.android.counselor.order.bean.DeliverInfo;
import com.cesaas.android.counselor.order.bean.ResultCreateOrderBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 在线下单
 * @author FGB
 *
 */
public class CreateOrderNet extends BaseNet{

	public CreateOrderNet(Context context) {
		super(context, true);
//		this.uri="Order/Sw/Express/CreateOrder";
		this.uri="OrderRoute/Sw/Express/CreateOrder";
	}
	
	public void setData(String OrderId,int payMethod,String expressId,String Remark,JSONObject cargoInfo,JSONObject deliverInfo){
		try {
			
			data.put("OrderId", OrderId);
			data.put("PayMethod", payMethod);
			data.put("ExpressId", expressId);
			data.put("Remark", Remark);
			data.put("CargoInfo", cargoInfo);
			data.put("DeliverInfo",deliverInfo);
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mPostNet(); // 开始请求网络
	}
	
	
	/**
	 * 测试
	 */
	public void setData(){
		try {
			CargoInfo info=new CargoInfo();
			DeliverInfo info2=new DeliverInfo();
			
			data.put("CargoInfo", info.getCargoInfo());
			data.put("DeliverInfo",info2.getDeliverInfo());
			data.put("OrderId", "1311182584680543136");
			data.put("ExpressId", "1");
			data.put("Remark", "ZH和你那边");
			
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
		ResultCreateOrderBean lbean = gson.fromJson(rJson, ResultCreateOrderBean.class);
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			EventBus.getDefault().post(lbean);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}

}
