package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultAddShopStaffBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 添加店员
 * @author FGB
 *
 */
public class AddShopStaffNet extends BaseNet{

	public AddShopStaffNet(Context context) {
		super(context, true);
		this.uri="User/SW/Counselor/Add";
	}
	
	public void setData(String sex,int type,String name,String nickName,String mobile,int levelId) {
		try {
			data.put("COUNSELOR_SEX", sex);//zero 男 ，1 女
			data.put("COUNSELOR_TYPE", type);//zero 店员 ，1 店长
			data.put("COUNSELOR_NAME", name);//姓名
			data.put("COUNSELOR_NICKNAME", nickName);//昵称
			data.put("COUNSELOR_MOBILE", mobile);//手机
			data.put("COUNSELOR_LEVELID", levelId);//等级ID
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
		ResultAddShopStaffBean bean=gson.fromJson(rJson, ResultAddShopStaffBean.class);
		EventBus.getDefault().post(bean);
	}

	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "e==="+e+"==err==="+err);
	}
}