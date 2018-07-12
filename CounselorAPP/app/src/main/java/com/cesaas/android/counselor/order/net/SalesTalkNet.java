package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;

import com.cesaas.android.counselor.order.bean.ResultSalesTalkBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 常用话术
 * @author fgb
 *
 */
public class SalesTalkNet extends BaseNet{

	public SalesTalkNet(Context context) {
		super(context,true);
		this.uri="User/Sw/SalesTalk/GetList";
	}
	
	public void setData(int page) {
		try {
			data.put("PageIndex", page);
			data.put("PageSize", Constant.PAGE_SIZE);
			data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson = new Gson();
		ResultSalesTalkBean lbean = gson.fromJson(rJson, ResultSalesTalkBean.class);
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
	}

}
