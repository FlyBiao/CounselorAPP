package com.cesaas.android.counselor.order.salestalk.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.salestalk.bean.ResultCustomDeleteBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 删除自定义话术
 * @author fgb
 *
 */
public class CustomDeleteNet extends BaseNet{

	public CustomDeleteNet(Context context) {
		super(context, true);
		this.uri="User/Sw/SalesTalk/CustomDelete";
	}
	
	public void setData(int Id){
		try {
			data.put("Id", Id);
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
		ResultCustomDeleteBean bean=gson.fromJson(rJson, ResultCustomDeleteBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "error:"+err);
	}

}
