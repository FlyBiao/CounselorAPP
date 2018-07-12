package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSendTextWxMsBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 发送文本消息
 * @author FGB
 *
 */
public class SendTextWxMsNet extends BaseNet{

	public SendTextWxMsNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Msg/SendTextWxMsg";
	}
	
	public void setData(String Content,JSONArray fans){
		try {
			
			data.put("Content", Content);
			data.put("Fans", fans);
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
		ResultSendTextWxMsBean bean=gson.fromJson(rJson, ResultSendTextWxMsBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "文本消息="+err+"error:"+e);
	}

}
