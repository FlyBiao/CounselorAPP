package com.cesaas.android.counselor.order.label.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.ResultMultiplayerSetBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 为多人设置统一标签
 * @author FGB
 *
 */
public class MultiplayerSetNet extends BaseNet{

	public MultiplayerSetNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Tag/MultiplayerSet";
	}
	
	public void setData(JSONArray vipId,int tagId,String categoryId){
		
		try {
			data.put("VipId",vipId);//vipid
			data.put("TagId",tagId);//标签ID
			data.put("CategoryId",categoryId);//标签类型
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//开始请求网络
		mPostNet();
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultMultiplayerSetBean bean=gson.fromJson(rJson, ResultMultiplayerSetBean.class);
		EventBus.getDefault().post(bean);
		
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
