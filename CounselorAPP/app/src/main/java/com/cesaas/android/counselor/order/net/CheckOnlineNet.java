package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultCheckOnlineBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 检查粉丝在线状态
 * @author FGB
 *
 */
public class CheckOnlineNet extends BaseNet{

	public CheckOnlineNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Fans/CheckOnline";
	}
	
	public void setData(String fansId) {
		try {
			data.put("Id", fansId);
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
		ResultCheckOnlineBean lbean = gson.fromJson(rJson, ResultCheckOnlineBean.class);
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "e==="+e+"==err==="+err);
	}

}
