package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultCartBean;
import com.cesaas.android.counselor.order.bean.ResultVipOrderListBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;


/**
 * 会员购买订单记录
 * @author fgb
 *
 */
public class VipOrderListNet extends BaseNet{

	public VipOrderListNet(Context context) {
		super(context, true);
//		this.uri="Order/Sw/Order/VipOrderList";
		this.uri="Order/Sw/Retail/VipOrderList";
	}
	
	public void setData(int page, String VipId, JSONArray Sort){
		try {
			data.put("PageIndex", page);
			data.put("VipId", VipId);
			data.put("PageSize", 3);
			data.put("Sort", Sort);
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		mPostNet(); // 开始请求网络
	}

	public void setData(int page, String VipId){
		try {
			data.put("PageIndex", page);
			data.put("VipId", VipId);
			data.put("PageSize", 3);
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		ResultVipOrderListBean lbean = JsonUtils.fromJson(rJson, ResultVipOrderListBean.class);
		EventBus.getDefault().post(lbean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}
}
