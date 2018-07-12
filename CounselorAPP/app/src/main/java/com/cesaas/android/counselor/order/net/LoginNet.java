package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.LoginBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.MD5;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 登录请求
 * @author FGB
 *
 */
public class LoginNet extends BaseNet{

	public LoginNet(Context context, String user, String pwd) {
		super(context, true);
		this.uri = "User/Sw/Account/Login";
		try {
			data.put("account", user.replace(" ", ""));
			data.put("password", new MD5().toMD5(pwd));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public LoginNet(Context context, String user, String pwd,String Equipment,String Address) {
		super(context, true);
		this.uri = "User/Sw/Account/Login";
		try {
			data.put("EquipmentCode",Equipment);
			data.put("Address",Address);
			data.put("account", user.replace(" ", ""));
			data.put("password", new MD5().toMD5(pwd));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public LoginNet(Context context, String user, String pwd,int TId) {
		super(context, true);
		this.uri = "User/Sw/Account/Login";
		try {
			data.put("TId",TId);
//			data.put("EquipmentCode",Equipment);
//			data.put("Address",Address);
			data.put("account", user.replace(" ", ""));
			data.put("password", new MD5().toMD5(pwd));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson = new Gson();
		LoginBean lbean = gson.fromJson(rJson, LoginBean.class);
		EventBus.getDefault().post(lbean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "HttpException==="+e+"---ERROR:="+err);
	}

}
