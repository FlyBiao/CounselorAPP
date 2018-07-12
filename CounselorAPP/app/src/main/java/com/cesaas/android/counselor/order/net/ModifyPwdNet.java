package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.LoginBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 修改密码请求
 * 
 * @author Evan
 *
 */
public class ModifyPwdNet extends BaseNet {

	public ModifyPwdNet(Context context) {
		super(context, true);
		this.uri = "User/Sw/Password/Reset";
	}

	public void setData(String opwd, String npwd) {
		try {
			data.put("OldPassword", opwd);
			data.put("NewPassword", npwd);
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
		LoginBean lbean = gson.fromJson(rJson, LoginBean.class);
		if (lbean.IsSuccess)
			EventBus.getDefault().post(lbean);
		ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
	}

}
