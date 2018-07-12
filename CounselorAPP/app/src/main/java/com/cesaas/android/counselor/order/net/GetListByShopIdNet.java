package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 按店铺查询粉丝
 * @author fgb
 *
 */
public class GetListByShopIdNet extends BaseNet{

	public GetListByShopIdNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Fans/GetListByShopId";
	}
	
	public void setData() {
		try {
			data.put("PageIndex", 1);//当前页
			data.put("PageSize", Constant.PAGE_SIZE);//每页显示条数
			data.put("isNotAssign", false);//true表示查询未分配顾问的粉丝，false标识查询本店所有粉丝
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	
	public void setData(int page,String fansMobile) {
		try {
			data.put("PageIndex", page);//
			data.put("PageSize", Constant.PAGE_SIZE);
			data.put("isNotAssign", false);//true表示查询未分配顾问的粉丝，false标识查询本店所有粉丝
			data.put("Key", fansMobile);//按手机号查询店铺粉丝
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mPostNet(); // 开始请求网络
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson = new Gson();
		ResultGetListByShopIdBean lbean = gson.fromJson(rJson, ResultGetListByShopIdBean.class);
		
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "GetListByShopIdNet===" + e + "********=err==" + err);
	}

}
