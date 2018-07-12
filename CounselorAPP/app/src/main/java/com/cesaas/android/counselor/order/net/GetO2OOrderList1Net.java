package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetO2OOrderListBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 发送订单请求
 * @author fgb
 *
 */
public class GetO2OOrderList1Net extends BaseNet{

	public GetO2OOrderList1Net(Context context) {
		super(context, true);
		this.uri="Order/Sw/Order/GetO2OOrderList1";
	}
	
	public void setData(){
		try {
			data.put("PageIndex", 1);
			data.put("orderDist_status", 0);
			data.put("PageSize", Constant.PAGE_SIZE);
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		mPostNet(); // 开始请求网络
	}
	
	public void setData(int page,int OrderStatus){
		try {
			data.put("PageIndex", page);
			data.put("OrderStatus", OrderStatus);
			data.put("PageSize", Constant.PAGE_SIZE);
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
		ResultGetO2OOrderListBean lbean = gson.fromJson(rJson, ResultGetO2OOrderListBean.class);
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
