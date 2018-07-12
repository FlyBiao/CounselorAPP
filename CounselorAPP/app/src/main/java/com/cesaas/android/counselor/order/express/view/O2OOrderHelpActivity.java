package com.cesaas.android.counselor.order.express.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 物流发货订单
 * @author fgb
 *
 */
public class O2OOrderHelpActivity extends BasesActivity{

	private WebView wv_o2oorder_help;
	private LinearLayout iv_help_back;
	private WaitDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_o2ohelp_layout);
		dialog = new WaitDialog(mContext);
		wv_o2oorder_help=(WebView) findViewById(R.id.wv_o2oorder_help);
		iv_help_back=(LinearLayout) findViewById(R.id.iv_help_back);
		
		initBack();
		
		/**
		 * 下面代码体验webview的强大功能=====================================
		 */
		WebSettings settings = wv_o2oorder_help.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		
		wv_o2oorder_help.setWebViewClient(new WebViewClient() {


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
		
		
		
		wv_o2oorder_help.loadUrl("http://sw.cesaas.com/mob/page?gzno=2014102410503312&no=259&platform=pc");// 加载网页
	}
	
	public void  initBack(){
		iv_help_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
