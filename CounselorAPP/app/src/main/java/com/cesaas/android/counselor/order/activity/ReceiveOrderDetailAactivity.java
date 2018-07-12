package com.cesaas.android.counselor.order.activity;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation.ConversationType;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.OrderDetailAdapter;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.express.view.ExpressListActivity;
import com.cesaas.android.counselor.order.fragment.ReceiveOrderFragment;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.GetDistributionOrderNet;
import com.cesaas.android.counselor.order.net.OrderDetailNet;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 接单详情页面
 * @author FGB
 *
 */
@ContentView(R.layout.order_detail_layout)
public class ReceiveOrderDetailAactivity extends BasesActivity{

	@ViewInject(R.id.lv_order_detail)
	private ListView lv_order_detail;
	@ViewInject(R.id.iv_orderdetail_back)
	private LinearLayout iv_backDetail;
	
	@ViewInject(R.id.ll_session)
	private LinearLayout ll_session;
	@ViewInject(R.id.ll_express_order)
	private LinearLayout ll_express_order;
	@ViewInject(R.id.tv_express_send_order)
	private TextView tv_express_send_order;
	@ViewInject(R.id.tv_order_detail_session)
	private TextView tv_order_detail_session;
	@ViewInject(R.id.tv_order_session)
	private TextView tv_order_session;
	
	private String tradeId;
	private String orderId;
	
	private int expressType;//物流类型  1：到店自提，zero：物流发货
	private String vipId;
	private String nickName;
	
	private OrderDetailNet detailNet;
	private GetDistributionOrderNet distributionOrderNet;
	
	private OrderDetailAdapter detailAdapter;
	private ArrayList<OrderDetailBean> orderDetailList= new ArrayList<OrderDetailBean>();
	private ArrayList<DistributionOrderBean> orderDetailList2= new ArrayList<DistributionOrderBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			tradeId=bundle.getString("TradeId");
		}
//		
		detailNet=new OrderDetailNet(mContext);
		detailNet.setData(tradeId);
		
		distributionOrderNet=new GetDistributionOrderNet(mContext);
		distributionOrderNet.setData(tradeId);
		
		session();
		
		initBack();
	}
		
	public void initData() {
		if (orderDetailList.size() >0) {
			detailAdapter = new OrderDetailAdapter(mContext,mActivity,orderDetailList,orderDetailList2);
			lv_order_detail.setAdapter(detailAdapter);
		}
	}
	public void onEventMainThread(ResultOrderDetailBean msg) {
		this.orderDetailList.addAll(msg.TModel);
		
		for (int i = 0; i < orderDetailList.size(); i++) {
			expressType=orderDetailList.get(i).ExpressType;
			orderId=orderDetailList.get(i).OrderId;
		}
		
		if(expressType==0){//快递方式取货
			ll_express_order.setVisibility(View.VISIBLE);
			
			 //物流发货
			tv_express_send_order.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putString("TradeId", orderId);
					Skip.mNextFroData(mActivity, ExpressListActivity.class, bundle);
				}
			});
			
		}else if(expressType==1){//到店自提取货
			ll_session.setVisibility(View.VISIBLE);
			
		}
		
		initData();
	}
	public void onEventMainThread(ResultDistributionOrderBean msg) {
		this.orderDetailList2.addAll(msg.TModel);
		
		for (int i = 0; i < orderDetailList2.size(); i++) {
			vipId=orderDetailList2.get(i).VipId;
			nickName=orderDetailList2.get(i).NickName;
		}
		
		initData();
	}
	
	/**
	 * 会话
	 */
	public void session(){
		
		tv_order_detail_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mContext.getApplicationInfo().packageName.equals(App.getCurProcessName(mContext))) {
					prefs=AbPrefsUtil.getInstance();
			        /**
			         * IMKit SDK调用第二步,建立与服务器的连接
			         */
			        RongIM.connect(prefs.getString("RongToken"), new RongIMClient.ConnectCallback() {

						/**
			             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
			             */
			            @Override
			            public void onTokenIncorrect() {
			            	Toast.makeText(mContext, "onTokenIncorrect", 0).show();
			            }

			            /**
			             * 连接融云成功
			             * @param userid 当前 token
			             */
			            @Override
			            public void onSuccess(String userid) {
			            	
			            	//启动单聊会话界面
			            	if (RongIM.getInstance() != null)
			            	   RongIM.getInstance().startPrivateChat(mContext, vipId, nickName);
			            	
			            	//设置自己发出的消息监听器
			            	if (RongIM.getInstance() != null) {
			            		  RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
			            		}
			            	
			            	//设置接收消息的监听器。
			            	RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
			            	
			            	//扩展功能自定义
			            	InputProvider.ExtendProvider[] provider = {
			            	  new ContactsProvider(RongContext.getInstance()),//自定义推荐订单
			            	  new ImageInputProvider(RongContext.getInstance()),//图片
			            	  new CameraInputProvider(RongContext.getInstance()),//相机
			            	  new LocationInputProvider(RongContext.getInstance()),//地理位置
			            	  
			            	};
			            	RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE, provider);
			            	
			            	//注册自定义消息
			            	RongIM.registerMessageType(CustomizeMessage.class);
			            	//注册消息模板
			            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(mContext,mActivity));
			            }

			            /**
			             * 连接融云失败
			             * @param errorCode 错误码，可到官网 查看错误码对应的注释
			             */
			            @Override
			            public void onError(RongIMClient.ErrorCode errorCode) {
			            	Toast.makeText(mContext, "ErrorCode", 0).show();
			            }
			        });
			    }
			}
		});
		
		tv_order_session.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mContext.getApplicationInfo().packageName.equals(App.getCurProcessName(mContext))) {
					prefs=AbPrefsUtil.getInstance();
			        /**
			         * IMKit SDK调用第二步,建立与服务器的连接
			         */
			        RongIM.connect(prefs.getString("RongToken"), new RongIMClient.ConnectCallback() {

						/**
			             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
			             */
			            @Override
			            public void onTokenIncorrect() {
			            	Toast.makeText(mContext, "onTokenIncorrect", 0).show();
			            }

			            /**
			             * 连接融云成功
			             * @param userid 当前 token
			             */
			            @Override
			            public void onSuccess(String userid) {
			            	
			            	//启动单聊会话界面
			            	if (RongIM.getInstance() != null)
			            	   RongIM.getInstance().startPrivateChat(mContext, vipId, nickName);
			            	
			            	//设置自己发出的消息监听器
			            	if (RongIM.getInstance() != null) {
			            		  RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
			            		}
			            	
			            	//设置接收消息的监听器。
			            	RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
			            	
			            	//扩展功能自定义
			            	InputProvider.ExtendProvider[] provider = {
			            	  new ContactsProvider(RongContext.getInstance()),//自定义推荐订单
			            	  new ImageInputProvider(RongContext.getInstance()),//图片
			            	  new CameraInputProvider(RongContext.getInstance()),//相机
			            	  new LocationInputProvider(RongContext.getInstance()),//地理位置
			            	  
			            	};
			            	RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE, provider);
			            	
			            	//注册自定义消息
			            	RongIM.registerMessageType(CustomizeMessage.class);
			            	//注册消息模板
			            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(mContext,mActivity));
			            }

			            /**
			             * 连接融云失败
			             * @param errorCode 错误码，可到官网 查看错误码对应的注释
			             */
			            @Override
			            public void onError(RongIMClient.ErrorCode errorCode) {
			            	Toast.makeText(mContext, "ErrorCode", 0).show();
			            }
			        });
			    }
			}
		});
	}
	
	
	/**
	 * 返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
				
			}
		}, 100);
		
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 返回上一个页面
	 */
	public void initBack(){
		//
		iv_backDetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
						
					}
				}, 100);
			}
		});
	}
}
