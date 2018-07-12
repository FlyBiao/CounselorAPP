package com.cesaas.android.counselor.order.training;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 培训相关详情【H5页面】
 * @author FGB
 *
 */
public class TrainingRelatedDetailActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout iv_treaining_back;
	private WebView wv_training_detail;
	private WaitDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_detail_layout);
		
		initWebView();
	}
	
	public void initWebView(){
		wv_training_detail=(WebView) findViewById(R.id.wv_training_detail);
		
		iv_treaining_back=(LinearLayout) findViewById(R.id.iv_treaining_back);
		iv_treaining_back.setOnClickListener(this);
		
		dialog = new WaitDialog(mContext);
		
		//支持javascript
		wv_training_detail.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放 
		wv_training_detail.getSettings().setSupportZoom(true); 
		// 设置出现缩放工具 
		wv_training_detail.getSettings().setBuiltInZoomControls(true);
		//扩大比例的缩放
		wv_training_detail.getSettings().setUseWideViewPort(true);
		//自适应屏幕
		wv_training_detail.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		wv_training_detail.getSettings().setLoadWithOverviewMode(true);
		
		wv_training_detail.setWebViewClient(new WebViewClient() {


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
		
		wv_training_detail.loadUrl("http://tongxiehui.net/by/55844.html");//【测试】 加载网页
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_treaining_back://返回
			Skip.mBack(mActivity);
			break;

		}
		
	}
}
