package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultUpdateShopStaffBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 修改店员
 * @author FGB
 *
 */
public class UpdateShopStaffNet extends BaseNet {
	public UpdateShopStaffNet(Context context) {
		super(context, true);
		this.uri = "User/SW/Counselor/Save";
	}

	public void setData(String name,String nickName,String mobile,String sex,int type,int counselorId) {
		try {
			data.put("COUNSELOR_NAME", name);
			data.put("COUNSELOR_NICKNAME", nickName);
			data.put("COUNSELOR_MOBILE", mobile);
			data.put("COUNSELOR_SEX", sex);
			data.put("COUNSELOR_TYPE", type);
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
		ResultUpdateShopStaffBean bean=gson.fromJson(rJson, ResultUpdateShopStaffBean.class);
		EventBus.getDefault().post(bean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
	}

}
