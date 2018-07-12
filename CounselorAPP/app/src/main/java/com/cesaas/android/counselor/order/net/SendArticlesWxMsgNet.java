package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSendArticlesWxMsBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 发送图文
 * @author FGB
 *
 */
public class SendArticlesWxMsgNet extends BaseNet{

	public SendArticlesWxMsgNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Msg/SendArticlesWxMsg";
	}
	
	public void setData(String Title,String Description,String Image,String Url,JSONArray fans){
		try {
			
			data.put("Title", Title);
			data.put("Description", Description);
			data.put("Image",Image);
			data.put("Url", Url);
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
		ResultSendArticlesWxMsBean bean=gson.fromJson(rJson, ResultSendArticlesWxMsBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "图文发送："+err+"error:"+e);
	}

}
