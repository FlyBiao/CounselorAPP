package com.cesaas.android.counselor.order.stafftest.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultStaffTestCompleteBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 已完成考试Net
 * @author fgb
 *
 */
public class StaffCompleteTestNet extends BaseNet{
	public StaffCompleteTestNet(Context context) {
		super(context, true);
		this.uri="ShopBusiness/SW/Paper/getList";
	}
	
	public void setData(int Status,int page){
		try {
			
			data.put("Status", Status);//zero：未开始考试 1：开始考试 2：考试完成
			data.put("PageIndex", page);
			data.put("PageSize", 20);
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mPostNet(); // 开始请求网络
	}
	
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Log.i(Constant.TAG, "list="+rJson);
		Gson gson = new Gson();
		ResultStaffTestCompleteBean lbean = gson.fromJson(rJson, ResultStaffTestCompleteBean.class);
		
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			EventBus.getDefault().post(lbean);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}
}
