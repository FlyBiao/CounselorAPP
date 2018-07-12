package com.cesaas.android.counselor.order.earnings.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.bean.WebViewStates;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.earnings.activity.OwerEarningsFragment.JavascriptInterface;
import com.cesaas.android.counselor.order.earnings.bean.StatisticalBean;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;

/**
 * 店铺收益
 * @author FGB
 *
 */
public class ShopEarningsFragment extends BaseFragment{
	
	private View view;
	private WebView wv_shop_earnings;
	private WaitDialog dialog;
	protected AbPrefsUtil prefs;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.shop_earnings_fragment, container,false);
		wv_shop_earnings=(WebView) view.findViewById(R.id.wv_shop_earnings);
		swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshShopLayout);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		userInfoNet=new UserInfoNet(getContext());
		userInfoNet.setData();
		
		prefs = AbPrefsUtil.getInstance();
		dialog = new WaitDialog(getActivity());
		loadWebData();
	}
	
	/**
	 * 接收EventBus Pos消息
	 * @param msg 消息实体类
	 */
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
	 * 加载H5数据
	 */
	public void loadWebData(){
		/**
		 * 下面代码体验webview的强大功能=====================================
		 */
		WebSettings settings = wv_shop_earnings.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		
		wv_shop_earnings.setWebViewClient(new WebViewClient() {


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
		
		wv_shop_earnings.loadUrl(Urls.SHOPINCOME);// 加载网页
		javascriptInterface = new JavascriptInterface(getContext());
		wv_shop_earnings.addJavascriptInterface(javascriptInterface, "appHome");
		
		initSwipeRefreshLayout();
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
            	wv_shop_earnings.reload();
	            	
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
		
		/**
		 * WebView 我的收益
		 * @param postData
		 */
		@SuppressWarnings("unused")
		@android.webkit.JavascriptInterface
		public void postMessage(String postData) {
			try {
				Gson g=new Gson();
				StatisticalBean bean=g.fromJson(postData, StatisticalBean.class);

				if (bean!= null) {
					 if (bean.type == 2) {//业绩详情 :0推荐订单 1发货订单
						 for (int i = 0; i < bean.param.size(); i++) {
							 if(bean.param.get(i)==0){//推荐订单
									Skip.mNext(getActivity(), DevelopCommisonActivity.class);
									
								}else if(bean.param.get(i)==1){//发货订单
									Skip.mNext(getActivity(), SendCommisionActivity.class);
								}
							}
					} 
					else {
						ToastFactory.getToast(mContext, "你想干吗?");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
