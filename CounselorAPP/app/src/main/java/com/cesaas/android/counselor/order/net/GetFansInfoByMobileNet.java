package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ShopVipBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 根据粉丝手机号查询会员信息
 * @author FGB
 *
 */
public class GetFansInfoByMobileNet extends BaseNet{

	public GetFansInfoByMobileNet(Context context) {
		super(context, true);
		this.uri="User/SW/Fans/GetFansInfoByMobile";
	}
	
	public void setData(int type,String mobile){
		try {
			data.put("Type", type);
			data.put("Val", mobile);
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
		ShopVipBean bean=gson.fromJson(rJson,ShopVipBean.class);
		EventBus.getDefault().post(bean);
		
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "e:"+e+"===err:"+err);
	}

}
