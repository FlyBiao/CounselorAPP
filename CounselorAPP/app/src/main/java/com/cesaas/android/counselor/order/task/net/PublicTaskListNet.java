package com.cesaas.android.counselor.order.task.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.task.bean.ResultPublicTaskBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 共有务列表net
 * @author FGB
 *
 */
public class PublicTaskListNet extends BaseNet{

	public PublicTaskListNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Task/TaskList";
	}
	
	public void setData(int PageIndex){
		try {
			
			data.put("PageIndex", PageIndex);
			data.put("PageSize", 20);
			data.put("UserTicket",
					AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mPostNet();
	}

	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultPublicTaskBean bean=gson.fromJson(rJson, ResultPublicTaskBean.class);
		EventBus.getDefault().post(bean);
		
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "error:"+e.getLocalizedMessage()+"err:"+err);
	}
	
}
