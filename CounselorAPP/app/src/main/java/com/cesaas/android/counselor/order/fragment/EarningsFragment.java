package com.cesaas.android.counselor.order.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.WebViewStates;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.earnings.activity.DevelopCommisonActivity;
import com.cesaas.android.counselor.order.earnings.activity.SendCommisionActivity;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的收益 Created by FGB on 2016/3/7.
 */
public class EarningsFragment extends BaseFragment {
	
	@ViewInject(R.id.wv_myicome)
	private WebView wv_myincome;
	private WaitDialog dialog;
	
	private JavascriptInterface javascriptInterface;
	
	@ViewInject(R.id.refresh_webview_layout)
	 private SwipeRefreshLayout swipeRefreshLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = new WaitDialog(getActivity());
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_myicome_webview,
				container, false);
		ViewUtils.inject(this, view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		/**
		 * 下面代码体验webview的强大功能=====================================
		 */
		WebSettings settings = wv_myincome.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		
		wv_myincome.setWebViewClient(new WebViewClient() {


			/**
			 * 所有跳转的链接都会在此方法中调用
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);

				return true;
			}
			
			/**
			 * 网页开始加载
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				dialog.mStart();
			}
			
			/**
			 * 网页加载结束
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				dialog.mStop();
			}
		});
		
		wv_myincome.loadUrl(Urls.MYINCOME);// 加载网页
		javascriptInterface = new JavascriptInterface(getContext());
		wv_myincome.addJavascriptInterface(javascriptInterface, "appHome");
		
		initSwipeRefreshLayout();
	}
	

	public void initSwipeRefreshLayout(){
		 //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, 
        		android.R.color.holo_red_light, 
        		android.R.color.holo_orange_light, 
        		android.R.color.holo_green_light);
        
        //设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
        	             
              @Override
              public void onRefresh() {
//            	  dialog.mStop();
            	//下拉重新加载
            	  wv_myincome.reload();
	            	
                 new Handler().postDelayed(new Runnable() {
                	 
                     @Override
                     public void run() {
                         swipeRefreshLayout.setRefreshing(false);
                     }
                 }, 3000);
             }
         });
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 定义JS交互接口
	 * 
	 * @author fgb
	 * 
	 */
	class JavascriptInterface {
		private static final String TAG = "JavascriptInterface";
		Context mContext;

		JavascriptInterface(Context c) {
			mContext = c;
		}
	
	/**
	 * 返回UserToken
	 * 
	 * @return UserToken
	 */
	@android.webkit.JavascriptInterface
	public String getUserInfo() {

		return abpUtil.getString("token");
	}
	
	
	/**
	 * WebView 我的收益
	 * 
	 * @param postData
	 */
	@SuppressWarnings("unused")
	@android.webkit.JavascriptInterface
	public void postMessage(String postData) {
		try {
			JSONObject mJson = new JSONObject(postData);
			String type = mJson.optString("type");
			String value = mJson.optString("value");

			if (type != null) {
				 if (type == "2") {//业绩详情 :0推荐订单 1发货订单
					Gson gson = new Gson();
					WebViewStates states = gson.fromJson(value, WebViewStates.class);
					
					if(states.states==0){//推荐订单
						Skip.mNext(getActivity(), DevelopCommisonActivity.class);
						
					}else if(states.states==1){//发货订单
						Skip.mNext(getActivity(), SendCommisionActivity.class);
					}
					
				} 
				else {
					ToastFactory.getToast(mContext, "你想干吗?");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
}

}
