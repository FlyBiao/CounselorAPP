package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultOfflineConfrimBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 发货
 * @author fgb
 *
 */
public class OfflineConfrimNet extends BaseNet{


	public OfflineConfrimNet(Context context) {
		super(context, true);
		this.uri="Order/Sw/Express/OfflineConfrim";
	}
	
	public void setData(String OrderId,String ExpressId,String WayBillNo,double Freight,double Weight){
		try {
			data.put("OrderId", OrderId);//订单号
			data.put("ExpressId", ExpressId);//物流公司编号
			data.put("WayBillNo", WayBillNo);//快递编号
			data.put("Freight", Freight);//邮费
			data.put("Weight", Weight);//重量
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
		ResultOfflineConfrimBean lbean = gson.fromJson(rJson, ResultOfflineConfrimBean.class);
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
