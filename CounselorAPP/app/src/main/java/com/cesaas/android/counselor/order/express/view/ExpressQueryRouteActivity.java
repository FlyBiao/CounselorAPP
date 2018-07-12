package com.cesaas.android.counselor.order.express.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

/**
 * 物流查询页面
 * @author fgb
 *
 */
public class ExpressQueryRouteActivity extends BasesActivity{

	private WebView wv_express;
	private TextView tv_express_back;
	private String orderId;
	
	private OrderDetailNet detailNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview_express_layout);
		
		wv_express=(WebView) findViewById(R.id.wv_express);
		tv_express_back=(TextView) findViewById(R.id.tv_express_back);
		mBack();
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			orderId=bundle.getString("TradeId");
		}
//
//		detailNet=new OrderDetailNet(mContext);
//		detailNet.setData(orderId);
		initWebView(orderId);
	}
	
	public void initWebView(String expressId){
		/**
		 * 下面代码体验webview的强大功能=====================================
		 */
		WebSettings settings = wv_express.getSettings();
		settings.setJavaScriptEnabled(true);// 代表设置支持JS
		settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
		settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
		
		wv_express.setWebViewClient(new WebViewClient() {


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
		
		wv_express.loadUrl(Constant.Express_QUERY+expressId);// 加载网页
	}
	

	private void mBack() {
		tv_express_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
		
	}
	
	/**
	 * 订单详情
	 * @author FGB
	 *
	 */
	public class OrderDetailNet extends BaseNet{
		
		private String expressShipNo;

		public OrderDetailNet(Context context) {
			super(context, true);
			this.uri="Order/Sw/Order/GetOrder";
		}
		
		public void setData(String TradeId){
			try {
				data.put("TradeId",TradeId);
				data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			mPostNet(); // 开始请求网络
		}
		
		
		@Override
		protected void mSuccess(String rJson) {
			super.mSuccess(rJson);
			Log.i(Constant.TAG, "订单详情==="+rJson);
			Gson gson=new Gson();
			ResultOrderDetailBean lbean = gson.fromJson(rJson, ResultOrderDetailBean.class);

			if(lbean.IsSuccess==true && lbean.TModel!=null){
				for (int i = 0; i < lbean.TModel.size(); i++) {
					expressShipNo=lbean.TModel.get(i).ExpressShipNo;
				}
				initWebView(expressShipNo);
				
			}else{
				ToastFactory.show(mContext, "获取物流订单失败！"+lbean.Message, ToastFactory.CENTER);
			}
		}

		@Override
		protected void mFail(HttpException e, String err) {
			super.mFail(e, err);
			Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
		}

	}
}
