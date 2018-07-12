package com.cesaas.android.counselor.order.label.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.ResultGetAllTagListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取当前店家所有标签
 * @author FGB
 *
 */
public class GetAllTagListNet extends BaseNet{

	public GetAllTagListNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Tag/GetAllTagList";
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
	
	public void setData(int PageSize,int PageIndex){
		try {
			
			data.put("PageSize",PageSize);
			data.put("PageIndex",PageIndex);
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
		Log.i(Constant.TAG, "label===" + rJson);
		Gson gson=new Gson();
		ResultGetAllTagListBean bean=gson.fromJson(rJson, ResultGetAllTagListBean.class);
		
		//通过EventBus发送消息事件
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
