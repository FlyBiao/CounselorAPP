package com.cesaas.android.counselor.order.label.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.ResultGetVipTagBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 根据会员Id查询该会员所属的标签
 * @author FGB
 *
 */
public class GetVipTagNet extends BaseNet{

	public GetVipTagNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Tag/GetVipTag";
	}
	
	public void setData(String vipId){
		try {
			data.put("VipId", vipId);//会员VipId
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//开始请求网络
		mPostNet();
	}
	
	public void setData(){
		try {
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//开始请求网络
		mPostNet();
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultGetVipTagBean bean=gson.fromJson(rJson, ResultGetVipTagBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
