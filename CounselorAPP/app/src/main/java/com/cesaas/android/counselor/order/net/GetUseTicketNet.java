package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetUserTicketListBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取优惠券Net
 * @author FGB
 *
 */
public class GetUseTicketNet extends BaseNet{

	public GetUseTicketNet(Context context) {
		super(context, true);
		this.uri = "Marketing/Sw/Ticket/GetUseTicket";
	}
	
	public void setData(String vipId){
		try {
			
			data.put("FansId", vipId);
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
		Gson gson=new Gson();
		ResultGetUserTicketListBean bean=gson.fromJson(rJson,ResultGetUserTicketListBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "e:"+e+"===err:"+err);
	}

}
