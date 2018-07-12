package com.cesaas.android.counselor.order.label.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.ResultAddTagBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 添加标签
 * @author FGB
 *
 */
public class AddTagNet extends BaseNet{

	public AddTagNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Tag/AddTag";
	}
	
	public void setData(int categoryId,String value){
		try {
			data.put("CategoryId", categoryId);//标签类型ID
			data.put("Value", value);//标签值
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
		ResultAddTagBean bean=gson.fromJson(rJson, ResultAddTagBean.class);
		
		//通过EventBus发送消息事件
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
