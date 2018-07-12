package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSetRemarkBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 设置粉丝备注
 * @author FGB
 *
 */
public class SetRemarkNet extends BaseNet{

	public SetRemarkNet(Context context) {
		super(context, true);
		this.uri="User/SW/Fans/SetRemark";
	}
	
	public void setData(String name,String id){
		try {
			
			data.put("Remark", name);
			data.put("VipId", id);
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
		ResultSetRemarkBean bean=gson.fromJson(rJson, ResultSetRemarkBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "error=="+err);
	}

}
