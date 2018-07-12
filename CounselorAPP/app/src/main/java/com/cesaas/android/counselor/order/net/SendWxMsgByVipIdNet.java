package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSendWxMsgByVipIdBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 给离线的用户发送微信消息
 * 
 * @author fgb
 * 
 */
public class SendWxMsgByVipIdNet extends BaseNet {

	public SendWxMsgByVipIdNet(Context context) {
		super(context, true);
		this.uri = "User/Sw/Msg/SendWxMsgByVipId";
	}

	public void setData(String fansId,String Content,String Image) {
		try {

			data.put("Id", fansId);
			data.put("Content",Content);
			data.put("Image", Image);
			data.put("Type", 1);
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
		ResultSendWxMsgByVipIdBean lbean = gson.fromJson(rJson, ResultSendWxMsgByVipIdBean.class);
		 if (lbean.IsSuccess) {
		 EventBus.getDefault().post(lbean);
		 } else {
		 ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		 }
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		
		Log.i(Constant.TAG, "ERROR===" + err.toString()+"=="+e.getStackTrace());
	}

}
