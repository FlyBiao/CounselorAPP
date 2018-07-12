package com.cesaas.android.counselor.order.label.net;

import io.rong.eventbus.EventBus;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSetTagBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 设置标签
 * @author FGB
 *
 */
public class SetTagNet extends BaseNet{

	public SetTagNet(Context context) {
		super(context, true);
		this.uri = "User/Sw/Tag/SetTag";
	}

	public void setData(String vipId,JSONArray tagId,String categoryId) {
		try {
			data.put("VipId", vipId);//会员标签
			data.put("TagId", tagId);//标签编号
			data.put("categoryId", categoryId);//分类编号
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mPostNet(); // 开始请求网络
	}

	public void setData(int vipId,JSONArray tagId) {
		try {
			data.put("VipId", vipId);//会员标签
			data.put("TagId", tagId);//标签编号
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mPostNet(); // 开始请求网络
	}

	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		ResultSetTagBean lbean = JsonUtils.fromJson(rJson, ResultSetTagBean.class);
		EventBus.getDefault().post(lbean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "**=HttpException=" + e + "..=err=" + err);
	}
}
