package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.DepositBean;
import com.cesaas.android.counselor.order.bean.ResultActionPowerBean;
import com.cesaas.android.counselor.order.bean.ResultLoginActionPowerBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 获取用户操作权限
 * @author fgb
 *
 */
public class ActionPowerNet extends BaseNet{

	private int type=1;
	private ACache mCache;

	public ActionPowerNet(Context context,int type) {
		super(context, true);
		this.uri="User/Sw/Power/GetActionPower";
		this.type=type;
	}
	public ActionPowerNet(Context context,int type,ACache mCache) {
		super(context, true);
		this.uri="User/Sw/Power/GetActionPower";
		this.type=type;
		this.mCache=mCache;
	}
	
	public void setData(){
		try {
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		mCache.put("ActionPower",rJson);
		if(type==1){
			ResultActionPowerBean lbean = JsonUtils.fromJson(rJson, ResultActionPowerBean.class);
			EventBus.getDefault().post(lbean);
		}else{
			ResultLoginActionPowerBean lbean = JsonUtils.fromJson(rJson, ResultLoginActionPowerBean.class);
			EventBus.getDefault().post(lbean);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}

}
