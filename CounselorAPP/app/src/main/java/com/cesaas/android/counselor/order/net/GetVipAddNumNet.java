package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultBandBean;
import com.cesaas.android.counselor.order.bean.ResultGetVipAddNumBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * (新)获取会员新增数
 * 
 * @author FGB
 * 
 */
public class GetVipAddNumNet extends BaseNet {

	public GetVipAddNumNet(Context context) {
		super(context, true);
		this.uri = "Marketing/Sw/SaleGoal/GetVipAddNum";
	}

	public void setData(String StartDate,String EndDate) {
		try {
			data.put("StartDate",StartDate);
			data.put("EndDate",EndDate);
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
		ResultGetVipAddNumBean lbean = gson.fromJson(rJson, ResultGetVipAddNumBean.class);
		EventBus.getDefault().post(lbean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
