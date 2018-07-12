package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultChangeShop;
import com.cesaas.android.counselor.order.bean.ResultShopPowerBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * 切换店铺
 * 
 * @author FGB
 * 
 */
public class ChangeShopNet extends BaseNet {

	public ChangeShopNet(Context context) {
		super(context, true);
		this.uri = "User/Sw/Account/ChangeShop";
	}

	public void setData(int ShopId) {
		try {
			data.put("ShopId",ShopId);
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
		ResultChangeShop lbean = JsonUtils.fromJson(rJson, ResultChangeShop.class);
		EventBus.getDefault().post(lbean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
