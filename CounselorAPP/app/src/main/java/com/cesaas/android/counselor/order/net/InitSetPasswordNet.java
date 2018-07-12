package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.InitSetPasswordBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 重置初始化密码
 * @author FGB
 *
 */
public class InitSetPasswordNet extends BaseNet {
	public InitSetPasswordNet(Context context) {
		super(context, true);
		this.uri = "User/SW/Counselor/SetPassword";
	}

	public void setData(int pwd,int counselorId) {
		try {
			data.put("COUNSELOR_PASSWORD", pwd);
			data.put("COUNSELOR_ID", counselorId);
			data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}

	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson = new Gson();
		InitSetPasswordBean bean=gson.fromJson(rJson, InitSetPasswordBean.class);
		EventBus.getDefault().post(bean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
	}

}
