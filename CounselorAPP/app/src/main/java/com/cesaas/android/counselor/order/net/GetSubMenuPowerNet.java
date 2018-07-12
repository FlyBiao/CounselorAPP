package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetSubMenuPowerBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.MenuPowerNet;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取子菜单权限
 * @author FGB
 *
 */
public class GetSubMenuPowerNet extends MenuPowerNet{

	public GetSubMenuPowerNet(Context context) {
		super(context, true);
		this.uri="Android/menuJson.json";
	}
	
	public void setData(){
		mPostNet();
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultGetSubMenuPowerBean bean=gson.fromJson(rJson, ResultGetSubMenuPowerBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "子菜单权限===" + e + "********=err==" + err);
	}

}
