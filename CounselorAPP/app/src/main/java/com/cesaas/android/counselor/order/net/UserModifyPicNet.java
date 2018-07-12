package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultUserPicBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 修改头像请求
 * 
 * @author FGB
 *
 */
public class UserModifyPicNet extends BaseNet {

	public UserModifyPicNet(Context context) {
		super(context, true);
//		this.uri = "User/Sw/User/UploadHeadIcon";
		this.uri = "YiQiao/Sw/User/UpdateIcon";
	}

	public void setData(String img) {
		try {
			data.put("Url",img);
			data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}

	public void setData(String img,int i) {
		try {
			data.put("Url",img);
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
		ResultUserPicBean lbean = gson.fromJson(rJson, ResultUserPicBean.class);
			EventBus.getDefault().post(lbean);

	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
	}

}
