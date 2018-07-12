package com.cesaas.android.counselor.order.task.fragment;

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
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.google.gson.Gson;

/**
 * 店铺任务详情
 * @author FGB
 *
 */
public class ShopTaskDetailActivity extends BasesActivity implements OnClickListener{

	private int taskId;
	private LinearLayout ll_shop_task_back;
	private WebView wv_shop_task_webview;
	
	private WaitDialog dialog;
	private JavascriptInterface javascriptInterface;
	private SwipeRefreshLayout swipeRefreshLayout;
	
	public String mobile;//手机号
    public String icon;//用户头像
    public String name;//用户名称
    public String nickName;//用户昵称
    public String sex;//性别
    public String shopId;//店铺ID
    public String shopName;//店铺名称
    public String shopMobile;//店铺电话
    public String typeName;//用户身份：店员，店长
    public String typeId;//1：店长，2：店员
    public String vipId;
    public String ticket;//生成拉粉二维码用票
    public String imToken;
    public String counselorId;//顾问ID
    public String gzNo;//公众号GzNo
    public int tId;
    
    public String shopPostCode;//商品提交码
    public String shopProvince;//商品所在省
    public String shopAddress;//商品所在地址
    public String shopArea;//商品所在区域
    public String shopCity;//商品所在城市
    
    private UserInfoNet userInfoNet;
	private UserInfo userInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_task_detail);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			taskId=bundle.getInt("TaskId");
		}
		
		dialog = new WaitDialog(mContext);
		
		userInfoNet=new UserInfoNet(mContext);
		userInfoNet.setData();
		
		initView();
		loadWebViewData();
		initSwipeRefreshLayout();
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		ll_shop_task_back=(LinearLayout) findViewById(R.id.ll_shop_task_back);
		ll_shop_task_back.setOnClickListener(this);
		wv_shop_task_webview=(WebView) findViewById(R.id.wv_shop_task_webview);
		swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.task_swipeRefreshShopLayout);
		
	}
	
	/**
	 * 加载H5页面数据
	 */
	public void loadWebViewData(){
		WebSettings settings = wv_shop_task_webview.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		settings.setAllowFileAccess(true);// 设置允许访问文件数据
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		
		wv_shop_task_webview.setWebViewClient(new WebViewClient() {

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
		
		wv_shop_task_webview.loadUrl(Urls.SHOP_TASK_DETAIL+taskId);// 加载网页
		javascriptInterface = new JavascriptInterface(mContext);
		wv_shop_task_webview.addJavascriptInterface(javascriptInterface, "appHome");	
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_shop_task_back:
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
	}
	
	public void onEventMainThread(ResultUserBean msg) {

		if (msg != null) {
			mobile=msg.TModel.Mobile;
		    icon=msg.TModel.Icon;//用户头像
		    name=msg.TModel.Name;//用户名称
		    nickName=msg.TModel.NickName;//用户昵称
		    sex=msg.TModel.Sex;//性别
		    shopId=msg.TModel.ShopId;//店铺ID
		    shopName=msg.TModel.ShopName;//店铺名称
		    shopMobile=msg.TModel.ShopMobile;//店铺电话
		    typeName=msg.TModel.TypeName;//用户身份：店员，店长
		    typeId=msg.TModel.TypeId;//1：店长，2：店员
		    vipId=msg.TModel.VipId+"";
		    ticket=msg.TModel.Ticket;//生成拉粉二维码用票
		    imToken=msg.TModel.ImToken;
		    counselorId=msg.TModel.CounselorId;//顾问ID
		    gzNo=msg.TModel.GzNo;//公众号GzNo
		    tId=msg.TModel.tId;
		    
		    shopPostCode=msg.TModel.ShopPostCode;//商品提交码
		    shopProvince=msg.TModel.ShopProvince;//商品所在省
		    shopAddress=msg.TModel.ShopAddress;//商品所在地址
		    shopArea=msg.TModel.ShopArea;//商品所在区域
		    shopCity=msg.TModel.ShopCity;//商品所在城市
		    
		}
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
            	wv_shop_task_webview.reload();
	            	
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
		
		/**
		 * 返回用户信息
		 * @return
		 */
		@android.webkit.JavascriptInterface
		public String appUserInfo(){
			
			userInfo=new UserInfo();
			userInfo.setCounselorId(counselorId);
			userInfo.setGzNo(gzNo);
			userInfo.setIcon(icon);
			userInfo.setImToken(imToken);
			userInfo.setName(name);
			userInfo.setMobile(mobile);
			userInfo.setNickName(nickName);
			userInfo.setSex(sex);
			userInfo.setShopAddress(shopAddress);
			userInfo.setShopArea(shopArea);
			userInfo.setShopCity(shopCity);
			userInfo.setShopId(shopId);
			userInfo.setShopMobile(shopMobile);
			userInfo.setShopName(shopName);
			userInfo.setShopPostCode(shopPostCode);
			userInfo.setShopProvince(shopProvince);
			userInfo.setTicket(ticket);
			userInfo.setTypeId(typeId);
			userInfo.setTypeName(typeName);
			userInfo.setVipId(vipId);
			userInfo.settId(tId);
			
			Gson gson=new Gson();
			String obj=gson.toJson(userInfo);
			
			return obj;
		}
		
	}
}
