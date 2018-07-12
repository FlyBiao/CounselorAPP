package com.cesaas.android.counselor.order.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.bean.ResultSerachFansListBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * //按粉丝查询列表
 * @author FGB
 *
 */
public class SerachFansListNet extends BaseNet{

	public SerachFansListNet(Context context) {
		super(context, true);
		this.uri="User/Sw/Fans/GetList";
	}
	
	public void setData(int page,String queryString){
		try {
			
			data.put("PageIndex", page);//当前页
			data.put("PageSize", Constant.PAGE_SIZE);//每页显示条数
			data.put("QueryString", queryString);//查询条件
			data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mPostNet();
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultSerachFansListBean bean=gson.fromJson(rJson, ResultSerachFansListBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "SerachFansListNet===" + e + "********=err==" + err);
	}

}
