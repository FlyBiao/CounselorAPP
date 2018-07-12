package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultBandBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.report.bean.ResultDayTotalBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * 获取店铺，店员日目标，店员开卡目标和新增粉丝数
 * 
 * @author FGB
 * 
 */
public class NewGetDayTotalNet extends BaseNet {

	public NewGetDayTotalNet(Context context) {
		super(context, true);
		this.uri = "Marketing/Sw/SaleGoal/NewGetDayTotal";
	}

	public void setData(String StartTime,String EndTime) {
		try {
			data.put("StartDate",StartTime);
			data.put("EndDate",EndTime);
			data.put("UserTicket",
					AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}

	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		ResultDayTotalBean bean= JsonUtils.fromJson(rJson,ResultDayTotalBean.class);
		EventBus.getDefault().post(bean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
