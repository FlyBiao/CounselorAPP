package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultAddSaleGoalBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 添加门店报数
 * @author FGB
 *
 */
public class AddSaleGoalNet extends BaseNet{

	public AddSaleGoalNet(Context context) {
		super(context, true);
		this.uri="Marketing/Sw/SaleGoal/Add";
	}
	
	public void setData(int orderCount,int productCount,double saleValue,JSONArray Competitor,int IsYesterDay) {
		try {
			data.put("orderCount", orderCount);//订单数
			data.put("productCount", productCount);//商品销售数量
			data.put("saleValue", saleValue);//销售额
			data.put("Competitor", Competitor);//同类对比
			data.put("IsYesterDay",IsYesterDay);//是否昨天销售信息[1:昨天，zero：当天]
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
		ResultAddSaleGoalBean bean=gson.fromJson(rJson, ResultAddSaleGoalBean.class);
		EventBus.getDefault().post(bean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "e==="+e+"==err==="+err);
	}

}
