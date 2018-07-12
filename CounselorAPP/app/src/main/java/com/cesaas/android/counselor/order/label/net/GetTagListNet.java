package com.cesaas.android.counselor.order.label.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.ResultGetTagListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取标签列表
 * @author fgb
 *
 */
public class GetTagListNet extends BaseNet{

	public GetTagListNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Tag/GetTagList";
	}
	
	public void setData(){
		try {
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//开始请求网络
		mPostNet();
	}
	
	public void setData(int categoryId ){
		try {
			data.put("CategoryId",categoryId);
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
		ResultGetTagListBean bean=gson.fromJson(rJson, ResultGetTagListBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		// TODO Auto-generated method stub
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
