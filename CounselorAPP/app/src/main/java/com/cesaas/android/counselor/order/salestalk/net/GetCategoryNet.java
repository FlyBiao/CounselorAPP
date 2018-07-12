package com.cesaas.android.counselor.order.salestalk.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.salestalk.bean.ResultGetCategoryBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取话术分类列表
 * @author fgb
 *
 */
public class GetCategoryNet extends BaseNet{

	public GetCategoryNet(Context context) {
		super(context, true);
		this.uri="User/Sw/SalesTalk/GetCategory";
	}
	
	public void setData(){
		try {
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
		ResultGetCategoryBean bean=gson.fromJson(rJson, ResultGetCategoryBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "话术分类列表:=error:"+err);
	}

}
