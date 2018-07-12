package com.cesaas.android.counselor.order.salestalk.net;

import io.rong.eventbus.EventBus;
import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

/**
 * 获取分类下的话术列表
 * @author fgb
 *
 */
public class GetListNet extends BaseNet{

	public GetListNet(Context context) {
		super(context, true);
		this.uri="User/Sw/SalesTalk/GetList";
	}
	
	public void setData(int categoryId,int page){
		try {
			data.put("CategoryId", categoryId);
			data.put("PageIndex", page);
			data.put("PageSize", Constant.PAGE_SIZE);
			data.put("UserTicket",
					AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
		} catch (Exception e) {
			e.printStackTrace();
		}
		mPostNet();
	}
	
	@Override
	protected void mSuccess(String rJson) {
		super.mSuccess(rJson);
		Gson gson=new Gson();
		ResultSalesTalkCategoryListBean bean=gson.fromJson(rJson, ResultSalesTalkCategoryListBean.class);
		EventBus.getDefault().post(bean);
	}
	
	@Override
	protected void mFail(HttpException e, String err) {
		super.mFail(e, err);
		Log.i(Constant.TAG, "GetListNet=error:"+err);
	}

}
