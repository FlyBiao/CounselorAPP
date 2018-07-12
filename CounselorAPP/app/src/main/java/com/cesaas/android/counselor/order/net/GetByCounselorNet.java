package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 我的订单【发货订单/粉丝订单】ByDevelop：粉丝订单 : zero   发货订单 :1
 * 
 * @author FGB
 * 
 */
public class GetByCounselorNet extends BaseNet {

	public GetByCounselorNet(Context context) {
		super(context, true);
		this.uri = "Order/Sw/Order/GetByCounselor";
	}

	public void setData(int BonusType,int OrderStatus,int page) {
		try {
			data.put("BonusType", BonusType);//0:粉丝   1：发货
			data.put("OrderStatus",OrderStatus);//10:待付款，20：订单支付中，30：待发货，40：已发货，80：退款，100：已完成
			data.put("PageIndex",page);
			data.put("PageSize", Constant.PAGE_SIZE);
			data.put("UserTicket",
					AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mPostNet(); // 开始请求网络
	}
	
	
	public void setData(int BonusType,int page) {
		try {
			data.put("BonusType", BonusType);//zero:粉丝   1：发货
			data.put("OrderStatus",null);//
			data.put("PageIndex",page);
			data.put("PageSize", 10);
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
		ResultGetByCounselorBean lbean = gson.fromJson(rJson,ResultGetByCounselorBean.class);
		
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
