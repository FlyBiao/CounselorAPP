package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSendCodeBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.SendCodeBaseNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取验证码Net
 * @author FGB
 *
 */
public class SendCodeNet extends SendCodeBaseNet{

	public SendCodeNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Regist/SendCode";
	}
	
	public void setData(String mobile,String authCode){
		try {
			data.put("mobile", mobile);
			data.put("authCode",authCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mPostNet();
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultSendCodeBean bean=gson.fromJson(rJson, ResultSendCodeBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "err:"+err+"=="+e);
	}

}
