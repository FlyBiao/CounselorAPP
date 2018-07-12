package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.bean.ResultShopPowerBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * 获取店铺权限所属店铺列表
 * 
 * @author FGB
 * 
 */
public class ShopPowerNet extends BaseNet {
	private ACache mCache;

	public ShopPowerNet(Context context,ACache mCache) {
		super(context, true);
		this.uri = "User/sw/user/GetShopPower";
		this.mCache=mCache;
	}

	public void setData() {
		try {
			data.put("UserTicket",
					AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}

	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		mCache.put("ShopPower",rJson);
		ResultShopPowerBean lbean = JsonUtils.fromJson(rJson, ResultShopPowerBean.class);
		EventBus.getDefault().post(lbean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
