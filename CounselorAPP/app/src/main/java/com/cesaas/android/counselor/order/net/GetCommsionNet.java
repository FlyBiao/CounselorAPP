package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultGetCommsion;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取我的收益佣金统计
 * @author FGB
 *
 */
public class GetCommsionNet extends BaseNet{

	public GetCommsionNet(Context context) {
		super(context, true);
		this.uri="Order/Sw/Order/GetCommsion";
	}
	
	public void setData(int BonusStatus,int BonusType,int page){
		try {
			data.put("BonusStatus",BonusStatus );//zero:暂时冻结1:可以结算2:已经收3:不能结算
			data.put("BonusType", BonusType);//zero:推荐佣金 ,1: 发货佣金
			data.put("PageIndex", page);
			data.put("PageSize", Constant.PAGE_SIZE);
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
		ResultGetCommsion lbean = gson.fromJson(rJson, ResultGetCommsion.class);
		
		if (lbean.IsSuccess) {
			EventBus.getDefault().post(lbean);
		} else {
			ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
		}
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}

}
