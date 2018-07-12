package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.ResultBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 
 * 修改昵称请求
 * 
 * @author FGB
 *
 */
public class SetNickNet extends BaseNet {

	public SetNickNet(Context context) {
		super(context, true);
		this.uri = "User/Sw/User/ModifyName";
	}

	public void setData(String nick) {
		try {
			data.put("QueryString", nick);
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
		ResultBean lbean = gson.fromJson(rJson, ResultBean.class);
		if (lbean.IsSuccess) {
			lbean.type = "nick";
			EventBus.getDefault().post(lbean);
		} else
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
	}

}
