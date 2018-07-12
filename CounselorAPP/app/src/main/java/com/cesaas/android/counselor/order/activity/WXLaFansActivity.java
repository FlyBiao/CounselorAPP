package com.cesaas.android.counselor.order.activity;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.webview.base.BaseUserInfo;

/**
 * 微信拉粉【H5页面】
 * @author fgb
 *
 */
public class WXLaFansActivity extends BasesActivity{
	
	private LinearLayout iv_lafans_back;
	private WebView wv_wx_lafans;
	
	private WaitDialog dialog;
	private JavascriptInterface javascriptInterface;
	private SwipeRefreshLayout swipeRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wx_lafans_layout);
		
		dialog = new WaitDialog(mContext);

		initView();
		loadWebData();
		initSwipeRefreshLayout();
		
	}

	/**
	 * 加载网页数据
	 */
	private void loadWebData() {
		WebSettings settings = wv_wx_lafans.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		settings.setAllowFileAccess(true);// 设置允许访问文件数据
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		
		wv_wx_lafans.setWebViewClient(new WebViewClient() {

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
		
		wv_wx_lafans.loadUrl(Urls.WEIXIN_LA_FANS);// 加载网页
		javascriptInterface = new JavascriptInterface(mContext);
		wv_wx_lafans.addJavascriptInterface(javascriptInterface, "appHome");
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshShopLayout);
		wv_wx_lafans=(WebView) findViewById(R.id.wv_wx_lafans);
		//这一句是设置js 对话框
		wv_wx_lafans.setWebChromeClient(new WebChromeClient());
		//不加上，会显示白边  
		wv_wx_lafans.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		iv_lafans_back=(LinearLayout) findViewById(R.id.iv_lafans_back);
		iv_lafans_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}

	/**
	 * 下拉刷新
	 */
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
//          	  dialog.mStop();
          	//下拉重新加载
            	wv_wx_lafans.reload();
	            	
               new Handler().postDelayed(new Runnable() {
              	 
                   @Override
                   public void run() {
                       swipeRefreshLayout.setRefreshing(false);
                   }
               }, 3000);
           }
       });
	}
	
	
	/** 
	 * 定义JS交互接口
	 * @author fgb
	 * 
	 */
	class JavascriptInterface {
		Context mContext;

		JavascriptInterface(Context c) {
			mContext = c;
		}
		
		/**
		 * 返回UserToken
		 * @return UserToken
		 */
		@android.webkit.JavascriptInterface
		public String getUserInfo() {

			return prefs.getString("token");
		}
		
		
		@android.webkit.JavascriptInterface
		public JSONObject GetManeuverInfo() { 
			try{ 
				JSONObject test=new JSONObject(); 
				test.put("names", "webview交互"); 
				return test;
			
			}catch(Exception e){ 
			} 
				return null; 
		} 
		
		/**
		 * 返回用户信息
		 * @return
		 */
		@android.webkit.JavascriptInterface
		public String appUserInfo(){
			return JsonUtils.toJson(BaseUserInfo.getUserInfo(prefs));
		}
		
	}

}
