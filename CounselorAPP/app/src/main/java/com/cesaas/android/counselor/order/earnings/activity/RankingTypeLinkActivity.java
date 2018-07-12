package com.cesaas.android.counselor.order.earnings.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
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
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * TOP排行榜类型页面
 * @author FGB
 *
 */
public class RankingTypeLinkActivity extends BasesActivity implements OnClickListener{

	private WebView wv_ranking_type;
	private WaitDialog dialog;
	private LinearLayout iv_ranking_type_back;
	
	private JavascriptInterface javascriptInterface;
	 private SwipeRefreshLayout swipeRefreshLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking_type_webview);
		dialog = new WaitDialog(mContext);
		initView();
		
		/**
		 * 下面代码体验webview的强大功能=====================================
		 */
		WebSettings settings = wv_ranking_type.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		settings.setSupportZoom(true); // 支持缩放 
		
		// 设置 缓存模式
		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		// 开启 DOM storage API 功能
		settings.setDomStorageEnabled(true);
		settings.setBuiltInZoomControls(true);
		settings.setAllowFileAccess(true);
		
		wv_ranking_type.setWebViewClient(new WebViewClient() {

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
		// 获取Intent的传参
		String url = getIntent().getStringExtra("typeRankingUrl");
		wv_ranking_type.loadUrl(url);// 加载网页
		javascriptInterface = new JavascriptInterface(mContext);
		wv_ranking_type.addJavascriptInterface(javascriptInterface, "appHome");
		
//		initSwipeRefreshLayout();
	}
	
	/**
	 * 初始化视图控件
	 */
	public void initView(){
		wv_ranking_type=(WebView) findViewById(R.id.wv_ranking_type);
		iv_ranking_type_back=(LinearLayout) findViewById(R.id.iv_ranking_type_back);
//		swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.refresh_ranking_layout);
		iv_ranking_type_back.setOnClickListener(this);
	}
	
	/**
	 * 初始化下拉刷新
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
//            	 dialog.mStop();
            	 //下拉重新加载
            	 wv_ranking_type.reload();
	            	
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
	public void onClick(View v) {
		Skip.mBack(mActivity);
		
	}
	
	/**
	 * 定义JS交互接口
	 * @author cebsaas
	 *
	 */
	class JavascriptInterface {
		Context mContext;
		JavascriptInterface(Context c) {
			mContext = c;
		}
		
		/**
		 * 返回Json字符串
		 * @return
		 */
		@android.webkit.JavascriptInterface
		public String getUserInfo() {
			return prefs.getString("token");
		}
		
		/**
		 * 返回用户参数信息
		 * 
		 * @return 用户店铺ID  VIPID
		 */
		@android.webkit.JavascriptInterface
		public String getParams() {
			String shopId=prefs.getString("shopId");
			String vipId=prefs.getString("vipId");
			StringBuilder sb=new StringBuilder();
			sb.append(shopId).append(",").append(vipId);
			return sb.toString();
		}
	}
}
