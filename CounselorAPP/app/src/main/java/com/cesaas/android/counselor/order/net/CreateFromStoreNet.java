package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.pos.bean.ResultCreateFromStoreBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 收银
 * @author FGB
 *
 */
public class CreateFromStoreNet extends BaseNet {

	public CreateFromStoreNet(Context context) {
		super(context, true);
		this.uri = "Order/Sw/StoreCashi/CreateFromStore";
	}

	public void setData(String OrderSyncCode,String Unique,int OrderType,double PayMent,double TotalPrice,JSONArray jsonArray){
		try {
			
			data.put("OrderSyncCode",OrderSyncCode);//单号
			data.put("Unique",Unique);//加密唯一编码:(生成规则：SwApp+单号OrderSyncCode)
			data.put("OrderType",OrderType);//订单类型:1积分订单 2付款订单
			data.put("PayMent",PayMent);//支付金额
			data.put("TotalPrice", TotalPrice);//总金额
			data.put("OrderItem", jsonArray);//OrderItem
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mPostNet(); // 开始请求网络
	}
	
	public void setData(String OrderSyncCode,String Unique,double PayMent,
			String NickName,double TotalPrice,String VipId,String OpenId,
			String Mobile,String Province,String District,String City,String Address,
			JSONArray jsonArray){
		try {
			data.put("OrderSyncCode",OrderSyncCode);//单号
			data.put("Unique",Unique);//加密唯一编码:(生成规则：SwApp+单号OrderSyncCode)
			data.put("OrderType",2);//订单类型:1积分订单 2付款订单
			data.put("CouponId", "73");//优惠券ID
			data.put("PayMent",PayMent);//支付金额
			data.put("NickName", NickName);//会员昵称
			data.put("TotalPrice", TotalPrice);//总金额
			data.put("VipId", VipId);//会员ID
			data.put("OpenId", OpenId);//会员No
			data.put("Mobile", Mobile);//会员手机号
			data.put("Province", Province);//省
			data.put("District", District);//区
			data.put("City", City);//市
			data.put("Address", Address);//地址
			data.put("OrderItem", jsonArray);//OrderArray
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		mPostNet(); // 开始请求网络
	}

	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultCreateFromStoreBean lbean=gson.fromJson(rJson, ResultCreateFromStoreBean.class);
		if(lbean.IsSuccess){
			EventBus.getDefault().post(lbean);
		}else{
			EventBus.getDefault().post(lbean);
		}
		
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "Fans===" + e + "********=err==" + err);
	}

}
