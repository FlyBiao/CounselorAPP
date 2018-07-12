package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetShopOrderBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 店铺订单
 * @author fgb
 *
 */
public class GetShopOrderNet extends BaseNet{

	public GetShopOrderNet(Context context) {
		super(context, true);
		this.uri="Order/Sw/Order/GetShopOrder";
	}

	public void setData(int page,int OrderStatus) {
		try {
			data.put("OrderStatus", OrderStatus);
			data.put("PageIndex", page);
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
		ResultGetShopOrderBean lbean = gson.fromJson(rJson, ResultGetShopOrderBean.class);
		
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "GetShopOrderNet===" + e + "********=err==" + err);
	}
}
