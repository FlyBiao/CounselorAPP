package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSendImageWxMsBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 发送图片
 * @author FGB
 *
 */
public class SendImageWxMsgNet extends BaseNet{

	public SendImageWxMsgNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Msg/SendImageWxMsg";
	}
	
	public void setData(String Image,JSONArray fans){
		try {
			
			data.put("Image",Image);
			data.put("Fans",fans);
			data.put("UserTicket",
					AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mPostNet();
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultSendImageWxMsBean bean=gson.fromJson(rJson, ResultSendImageWxMsBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "发送图片Fail："+err+"error:"+e);
	}

}
