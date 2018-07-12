package com.cesaas.android.counselor.order.net;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultCartBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.bean.ResultGetLstByVipTagBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.eventbus.EventBus;


/**
 * 根据会员标签查询推荐商品
 * @author fgb
 *
 */
public class GetLstByVipTagNet extends BaseNet{

	public GetLstByVipTagNet(Context context) {
		super(context, true);
		this.uri="Marketing/Sw/Style/getLstByVipTag";
	}
	
	public void setData(int page,String VipId){
		try {
			data.put("PageIndex", page);
			data.put("VipId", VipId);
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
		ResultGetLstByVipTagBean lbean = JsonUtils.fromJson(rJson, ResultGetLstByVipTagBean.class);
		EventBus.getDefault().post(lbean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
	}
}
