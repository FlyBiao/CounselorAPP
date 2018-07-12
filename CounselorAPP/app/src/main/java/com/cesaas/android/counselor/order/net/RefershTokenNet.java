package com.cesaas.android.counselor.order.net;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.lidroid.xutils.exception.HttpException;

/**
 * 刷新融云Token(检查用户状态)
 * @author FGB
 *
 */
public class RefershTokenNet extends BaseNet{

	public RefershTokenNet(Context context) {
		super(context, true);
		this.uri="User/Sw/User/RefershToken";
	}
	
	public void setData() {
		try {
			data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
//		Gson gson = new Gson();
//		ResultOrderBean lbean = gson.fromJson(rJson, ResultOrderBean.class);
//		if (lbean.IsSuccess) {
//			EventBus.getDefault().post(lbean);
//		} else {
//			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
//		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}


}
