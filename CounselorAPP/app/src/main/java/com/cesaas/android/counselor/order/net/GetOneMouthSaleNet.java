package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSalesInfoBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取本月销售额报数数据
 * @author FGB
 *
 */
public class GetOneMouthSaleNet extends BaseNet{

	public GetOneMouthSaleNet(Context context) {
		super(context, true);
		this.uri="Marketing/Sw/SaleGoal/GetOneMouthSale";
	}
	
	public void setData(int year,int month) {
		try {
			data.put("year", year);//年份
			data.put("month", month);//月份
			data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson = new Gson();
		ResultSalesInfoBean bean=gson.fromJson(rJson, ResultSalesInfoBean.class);
		EventBus.getDefault().post(bean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "e==="+e+"==err==="+err);
	}

}
