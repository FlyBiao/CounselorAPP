package com.cesaas.android.counselor.order.earnings.activity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetSubMenuPowerBean;
import com.cesaas.android.counselor.order.bean.WebViewStates;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.net.GetSubMenuPowerNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.json.JSONException;
import org.json.JSONObject;

@ContentView(R.layout.earnings_view_pager_layout)
public class EarningsActivity extends BasesActivity{

	private ArrayList<Fragment> fragments;

	@ViewInject(R.id.viewPager_earnings)
	private ViewPager viewPager_earnings;
	@ViewInject(R.id.tab_ower)
	private TextView tab_ower;
	@ViewInject(R.id.tab_shop)
	private TextView tab_shop;
	@ViewInject(R.id.earnings_line)
	private View earnings_line;
	@ViewInject(R.id.ll_back_earnings)
	private LinearLayout ll_back_earnings;

	private int line_width;

	private String aPPDatacenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

			Bundle bundle=getIntent().getExtras();
			if(bundle!=null){
				aPPDatacenter=bundle.getString("APP_datacenter");
			}

		initTextViewAnimator();
		initDatas();
		mBack();
	}

	/**
	 * 初始化TextView动画
	 */
	public void initTextViewAnimator(){
		ViewPropertyAnimator.animate(tab_ower).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_ower).scaleY(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_shop).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_shop).scaleY(1.1f).setDuration(0);
	}

	@SuppressWarnings("deprecation")
	public void initDatas(){
		fragments = new ArrayList<Fragment>();
		fragments.add(new OwerEarningsFragment());
		fragments.add(new ShopEarningsFragment());
		line_width = getWindowManager().getDefaultDisplay().getWidth()
				/ fragments.size();
		earnings_line.getLayoutParams().width = line_width;
		earnings_line.requestLayout();

		viewPager_earnings.setAdapter(new FragmentStatePagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		});

		viewPager_earnings.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				changeState(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				float tagerX = arg0 * line_width + arg2 / fragments.size();
				ViewPropertyAnimator.animate(earnings_line).translationX(tagerX)
						.setDuration(0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		tab_shop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager_earnings.setCurrentItem(1);
				//initData();
			}
		});

		tab_ower.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager_earnings.setCurrentItem(0);
			}
		});
	}


	/* 根据传入的值来改变状态 */
	private void changeState(int arg0) {
		if (arg0 == 0) {
			tab_ower.setTextColor(getResources().getColor(R.color.color_title_bar));
			tab_shop.setTextColor(getResources().getColor(R.color.black));
//			ViewPropertyAnimator.animate(tab_app).scaleX(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_app).scaleY(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleX(1.2f)
//					.setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleY(1.2f)
//					.setDuration(200);

		} else {
			tab_shop.setTextColor(getResources().getColor(R.color.color_title_bar));
			tab_ower.setTextColor(getResources().getColor(R.color.black));
//			ViewPropertyAnimator.animate(tab_app).scaleX(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_app).scaleY(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleX(1.2f)
//					.setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleY(1.2f)
//					.setDuration(200);
		}
	}

	public void mBack(){
		ll_back_earnings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);

			}
		});
	}

}




/**
 * 收益页面
 * @author FGB
 *
 */
//@ContentView(R.layout.activity_myicome_webview)
//public class EarningsActivity extends BasesActivity{
//
//	@ViewInject(R.id.wv_myicome)
//	private WebView wv_myincome;
//	@ViewInject(R.id.iv_back_myicome)
//	private LinearLayout iv_back_myicome;
//	private WaitDialog dialog;
//
//	private JavascriptInterface javascriptInterface;
//
//	@ViewInject(R.id.refresh_webview_layout)
//	 private SwipeRefreshLayout swipeRefreshLayout;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		dialog = new WaitDialog(mActivity);
//		loadWebData();
//
//		//返回上一个页面
//		iv_back_myicome.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Skip.mBack(mActivity);
//			}
//		});
//	}
//
//
//	/**
//	 * 加载H5数据
//	 */
//	public void loadWebData(){
//		/**
//		 * 下面代码体验webview的强大功能=====================================
//		 */
//		WebSettings settings = wv_myincome.getSettings();
//		settings.setJavaScriptEnabled(true);// 代表设置支持JS
//		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
//		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
//
//		wv_myincome.setWebViewClient(new WebViewClient() {
//			/**
//			 * 所有跳转的链接都会在此方法中调用
//			 * @param view
//			 * @param url
//			 * @return
//			 */
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(url);
//
//				return true;
//			}
//
//			/**
//			 * 网页开始加载
//			 * @param view
//			 * @param url
//			 * @param favicon
//			 */
//			@Override
//			public void onPageStarted(WebView view, String url, Bitmap favicon) {
//				super.onPageStarted(view, url, favicon);
//				dialog.mStart();
//			}
//
//			/**
//			 * 网页加载结束
//			 * @param view
//			 * @param url
//			 */
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				super.onPageFinished(view, url);
//				dialog.mStop();
//			}
//		});
//
//		wv_myincome.loadUrl(Urls.MYINCOME);// 加载网页
//		javascriptInterface = new JavascriptInterface(mContext);
//		wv_myincome.addJavascriptInterface(javascriptInterface, "appHome");
//
////		initSwipeRefreshLayout();
//	}
//
//	/**
//	 * 下拉刷新
//	 */
//	public void initSwipeRefreshLayout(){
//		 //设置刷新时动画的颜色，可以设置4个
//       swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
//       		android.R.color.holo_red_light,
//       		android.R.color.holo_orange_light,
//       		android.R.color.holo_green_light);
//
//       //设置下拉刷新
//       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//
//             @Override
//             public void onRefresh() {
////           	  dialog.mStop();
//           	//下拉重新加载
//           	  wv_myincome.reload();
//
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                    }
//                }, 3000);
//            }
//        });
//	}
//
//	/**
//	 * 定义JS交互接口
//	 */
//	class JavascriptInterface {
//		private static final String TAG = "JavascriptInterface";
//		Context mContext;
//
//		JavascriptInterface(Context c) {
//			mContext = c;
//		}
//
//		/**
//		 * 返回UserToken
//		 * @return
//		 */
//	@android.webkit.JavascriptInterface
//	public String getUserInfo() {
//
//		return prefs.getString("token");
//	}
//
//
//		/**
//		 * WebView 我的收益
//		 * @param postData
//		 */
//	@SuppressWarnings("unused")
//	@android.webkit.JavascriptInterface
//	public void postMessage(String postData) {
//		try {
//			JSONObject mJson = new JSONObject(postData);
//			String type = mJson.optString("type");
//			String value = mJson.optString("value");
//
//			if (type != null) {
//				 if (type == "2") {//业绩详情 :0推荐订单 1发货订单
//					Gson gson = new Gson();
//					WebViewStates states = gson.fromJson(value, WebViewStates.class);
//
//					if(states.states==0){//推荐订单
//						Skip.mNext(mActivity, DevelopCommisonActivity.class);
//
//					}else if(states.states==1){//发货订单
//						Skip.mNext(mActivity, SendCommisionActivity.class);
//					}
//
//				}
//				else {
//					ToastFactory.getToast(mContext, "你想干吗?");
//				}
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}
//
//}
//}
