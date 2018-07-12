package com.cesaas.android.counselor.order.global;

import android.content.Context;
import android.util.Log;

import com.cesaas.android.counselor.order.activity.user.bean.RegisterAuthorizationBean;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.utils.AbDataPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.MD5;
import com.cesaas.android.counselor.order.utils.NetworkUtil;
import com.cesaas.android.counselor.order.utils.SwitchServerUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * 网络请求基类
 * 
 * @author Evan
 *
 */
public class SendCodeBaseNet {

	public Context mContext;
	protected String uri;
	protected JSONObject data;
	private boolean ishow;
	private WaitDialog dialog;
	protected AbDataPrefsUtil abData;
	private StringBuffer auth;

	public SendCodeBaseNet(Context context, boolean show) {
		this.mContext = context;
		data = new JSONObject();
		abData = AbDataPrefsUtil.getInstance();
		this.ishow = show;
		if (show)
			dialog = new WaitDialog(context);
	}

	/**
	 * 获取授权
	 * @return
	 */
	public String getToken() {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append(AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN))
		.append("SW-AppAuthorizationToken")
				.append(AbPrefsUtil.getInstance().getString(Constant.SPF_TIME, ""));
		return new MD5().toMD5(sbuf.toString());
	}
	
	/**
	 * 开始请求网络
	 */
	public void mPostNet() {
		if (App.mInstance().getNetType() != NetworkUtil.NO_NETWORK) {
			RequestParams params = new RequestParams();
			try {
				params.setBodyEntity(new StringEntity(data.toString(), "UTF-8"));
				params.addHeader("Content-Type", "application/json");
				String token = this.getToken();
				String time = AbPrefsUtil.getInstance().getString(Constant.SPF_TIME, "");
				if (!"".equals(token) && !"".equals(time)) {
					auth= new StringBuffer();
					auth.append("SW-Authorization ").append(token).append(";").append(time);
					params.addHeader("Authorization", auth.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Log.i("onSuccess","请求Url："+ Constant.IP + uri + "\n" + data+ "\n" );
			HttpUtils http = new HttpUtils();
			http.send(HttpMethod.POST, SwitchServerUtils.getServerUrl() + uri, params, new RequestCallBack<HttpUtils>() {
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					super.onLoading(total, current, isUploading);
					if (isUploading) {
					} else {
					}
				}

				//加载刷新
				@Override
				public void onStart() {
					super.onStart();
					if (ishow && dialog != null)
						dialog.show();
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					try {
						if (ishow && dialog != null)
							dialog.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mFail(arg0, arg1);
				}

				@Override
				public void onSuccess(ResponseInfo<HttpUtils> responseInfo) {
					Log.i("onSuccess",
									responseInfo.result
									+"\n"+"statusCode:"+responseInfo.statusCode
									+"\n"+responseInfo.contentType
									+"\n"+"contentEncoding:"+responseInfo.contentEncoding
									+"\n"+"contentLength:"+responseInfo.contentLength
									+"\n"+"reasonPhrase:"+responseInfo.reasonPhrase
									+"\n"+"hashCode:"+responseInfo.hashCode()
									+"\n"+"locale:"+responseInfo.locale
									+"\n"+"protocolVersion:"+responseInfo.protocolVersion
									+"\n"+"getAllHeaders:"+responseInfo.getAllHeaders()
									+"\n"+"responseInfo:"+responseInfo
									+"\n Authorization====="+responseInfo.getFirstHeader("Authorization")
									+"\n"+"responseInfo:"+responseInfo);

					if(responseInfo.getFirstHeader("Authorization")!=null && responseInfo.getAllHeaders()!=null){
						RegisterAuthorizationBean authorizationBean=new RegisterAuthorizationBean();
						authorizationBean.setAuthorization(responseInfo.getFirstHeader("Authorization").toString());
						EventBus.getDefault().post(authorizationBean);
					}

					try {
						if (ishow && dialog != null)
							dialog.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
					mSuccess(responseInfo.result + "");
				}
			});
		} else {
			ToastFactory.show(App.mInstance(), "未Intent网络，请检查后重试！", ToastFactory.CENTER);
		}
	}

	public String getAccount() {
		return AbPrefsUtil.getInstance().getString(Constant.SPF_ACCOUNT);
	}

	

	protected void mSuccess(String rJson) {
	}

	protected void mFail(HttpException e, String err) {
		Log.i(Constant.TAG,"\n\n" +e+"err："+err);
		ToastFactory.show(mContext, "服务器连接或返回错误！", ToastFactory.CENTER);
	}

}
