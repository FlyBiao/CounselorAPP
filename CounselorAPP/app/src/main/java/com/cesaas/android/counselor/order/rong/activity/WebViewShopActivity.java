package com.cesaas.android.counselor.order.rong.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;

/**
 * 推荐商品页面
 * @author fgb
 *
 */
public class WebViewShopActivity extends BasesActivity{

	private WebView wv_web;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			url=bundle.getString("url");
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_shop_layout);
		
		wv_web=(WebView) findViewById(R.id.wv_web);
		
		/**
		 * 下面代码体验webview的强大功能=====================================
		 */
		WebSettings settings = wv_web.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		
		wv_web.setWebViewClient(new WebViewClient() {


			/**
			 * 所有跳转的链接都会在此方法中调用
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				System.out.println("跳转的url" + url);
				view.loadUrl(url);

				return true;
			}
		});
		
		wv_web.loadUrl(url);// 加载网页
	}
}
