package com.cesaas.android.counselor.order.salestalk.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.salestalk.bean.ResultCustomListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 自定义话术列表
 * @author fgb
 *
 */
public class GetCustomListNet extends BaseNet{

	public GetCustomListNet(Context context) {
		super(context, true);
		this.uri="User/Sw/SalesTalk/GetCustomList";
	}
	
	public void setData(int page){
		try {
			data.put("PageIndex", page);
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
		ResultCustomListBean bean=gson.fromJson(rJson, ResultCustomListBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "GetCustomListNet=error:"+err);
	}


}
