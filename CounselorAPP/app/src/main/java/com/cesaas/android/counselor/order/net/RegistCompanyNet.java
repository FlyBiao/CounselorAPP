package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultRegistCompanyBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 商家注册
 * @author FGB
 *
 */
public class RegistCompanyNet extends BaseNet{

	public RegistCompanyNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Regist/RegistCompany";
	}
	
	public void setData(String company,String mobile,String code,String password,String contact){
		try {
			data.put("company", company);
			data.put("mobile", mobile);
			data.put("code", code);
			data.put("password", password);
			data.put("contact", contact);
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
		ResultRegistCompanyBean bean=gson.fromJson(rJson, ResultRegistCompanyBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "err:"+err+"=="+e);
	}

}
