package com.cesaas.android.counselor.order.earnings.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 排行榜页面
 * @author fgb
 *
 */
public class RanKingActivity extends BasesActivity implements OnClickListener{

	private WebView wv_ranking;
	private WaitDialog dialog;
	private LinearLayout iv_ranking_back;
	
	private JavascriptInterface javascriptInterface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_webview);
		dialog = new WaitDialog(mContext);
		wv_ranking=(WebView) findViewById(R.id.wv_ranking);
		iv_ranking_back=(LinearLayout) findViewById(R.id.iv_ranking_back);
		iv_ranking_back.setOnClickListener(this);
		
		/**
		 * 下面代码体验webview的强大功能=====================================
		 */
		WebSettings settings = wv_ranking.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		
		wv_ranking.setWebViewClient(new WebViewClient() {


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
		
		wv_ranking.loadUrl(Urls.RANKING);// 加载网页
//		javascriptInterface = new JavascriptInterface(mContext);
//		wv_ranking.addJavascriptInterface(javascriptInterface, "appHome");
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.iv_ranking_back:
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
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

		return prefs.getString("token");
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
				if (type == "1") {
//					 通过intent传递URL地址， 跳转到品牌详情页
					Skip.mNextFroData(mActivity,RankingTypeLinkActivity.class, "typeRankingUrl",
							value);
				} else if (type == "2") {//
//					Skip.mNextFroData(getActivity(),MagazineActivity.class, "magazineUrl",value);
//					Log.i(TAG, "value2=:"+value);
					
				}
				else if (type == "3") {//
//					Skip.mNextFroData(getActivity(),MagazineActivity.class, "magazineUrl",value);
//					Log.i(TAG, "value2=:"+value);
					
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
