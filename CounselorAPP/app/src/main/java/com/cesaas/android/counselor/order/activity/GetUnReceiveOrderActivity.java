/*package com.cesaas.android.counselor.order.activity;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation.ConversationType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.MainActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.GetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.GetUnReceiveOrderNet;
import com.cesaas.android.counselor.order.net.OrderBackNet;
import com.cesaas.android.counselor.order.net.ReceivingFonfirmOrderNet;
import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

*//**
	* 待接单页面
	* @author FGB
	*
	*/
/*
@ContentView(R.layout.activity_order_layout)
public class GetUnReceiveOrderActivity extends BasesActivity{

@ViewInject(R.id.load_more_list)
private LoadMoreListView mLoadMoreListView;
@ViewInject(R.id.refresh_and_load_more)
private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
private static GetUnReceiveOrderActivity fragment;
@ViewInject(R.id.il_receive_back)
private LinearLayout il_receive_back;

private GetUnReceiveOrderNet net;
private ReceivingFonfirmOrderNet fonfirmOrderNet;//标识有货请求
private OrderBackNet backNet;//表示无货

private boolean refresh=false;
private static int pageIndex = 1;

private ArrayList<GetUnReceiveOrderBean> orderLis = new ArrayList<GetUnReceiveOrderBean>();

public static Handler handler=new Handler(){
	public void handleMessage(android.os.Message msg) {
		
		pageIndex=1;
		fragment.loadData(pageIndex);
	};
};

@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	MainActivity.stop=true;
	fragment=this;
	
	initAdapterAndSetData();
	initData();
	initListener();
	initBack();
}

*//**
	* 初始化Adapter和设置数据
	*/
/*
public void initAdapterAndSetData(){
mLoadMoreListView.setAdapter(new CommonAdapter<GetUnReceiveOrderBean>(mContext,R.layout.item_user_order,orderLis) {

	@Override
	public void convert(ViewHolder holder, final GetUnReceiveOrderBean bean,final int postion) {
		
		holder.setText(R.id.tv_order_create_date, "下单时间:" +bean.CreateDate);
		holder.setText(R.id.tv_order_no, "订单号:"+bean.TradeId);
		
		if(bean.ExpressType==zero){
			holder.setText(R.id.tv_express, "快递");
			
		}else if(bean.ExpressType==1){
			holder.setText(R.id.tv_express,"到店自提");
		}
		
		for (int i = zero; i < bean.OrderItem.size(); i++) {
			holder.setText(R.id.tv_order_attr,bean.OrderItem.get(i).Attr);
			holder.setText(R.id.tv_order_name,bean.OrderItem.get(i).Title);
			holder.setText(R.id.tv_quantity,"x"+bean.OrderItem.get(i).Quantity);
			holder.setText(R.id.tv_type_code,"款号:"+bean.OrderItem.get(i).StyleCode);
			holder.setImageBitmapUtils(R.id.iv_img,bitmapUtils,bean.OrderItem.get(i).ImageUrl);
		}
		
		//标识有货
		holder.setOnClickListener(R.id.btn_rob_order, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bean.OrderStatus!=10 || bean.OrderStatus!=30){
					
					fonfirmOrderNet=new ReceivingFonfirmOrderNet(GetUnReceiveOrderActivity.this.mContext);
					fonfirmOrderNet.setData(bean.OrderItem.get(zero).OrderId);
					
					Bundle bundle = new Bundle();
					bundle.putString("OrderId", bean.OrderItem.get(zero).OrderId);
					Skip.mNextFroData(mActivity, ReceiveOrderAactivity.class,bundle);//跳转到接单详情页面
				}
				
			}
		});
		
		//标识无货
		holder.setOnClickListener(R.id.tv_not_huo, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(bean.OrderStatus!=10 || bean.OrderStatus!=30){
					backNet=new OrderBackNet(mContext);
					backNet.setData(bean.OrderItem.get(zero).OrderId);
					
//							OrderAdapter.this.data.clear();
					orderLis.clear();
					new Handler().postDelayed(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							GetUnReceiveOrderActivity.handler.obtainMessage().sendToTarget();
							
						}
					}, 100);
					
				}
				
			}
		});
		
		//扫描验货
		holder.setOnClickListener(R.id.tv_check, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String barcodeCode=orderLis.get(postion).OrderItem.get(zero).BarcodeCode.toString();
				Bundle bundle=new Bundle();
				bundle.putString("barcodeCode", barcodeCode);
				Skip.mNextFroData(mActivity, ScanResultActivity.class,bundle);
				
			}
		});
		
		//会话
		holder.setOnClickListener(R.id.tv_huihua, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mContext.getApplicationInfo().packageName.equals(App.getCurProcessName(mContext))) {
					prefs=AbPrefsUtil.getInstance();
			        *//**
						* IMKit SDK调用第二步,建立与服务器的连接
						*/
/*
RongIM.connect(prefs.getString("RongToken"), new RongIMClient.ConnectCallback() {

*//**
	* Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
	*/
/*
@Override
public void onTokenIncorrect() {
Toast.makeText(mContext, "onTokenIncorrect", zero).show();
}

*//**
	* 连接融云成功
	* @param userid 当前 token
	*/
/*
@SuppressWarnings("static-access")
@Override
public void onSuccess(String userid) {

//启动单聊会话界面
if (RongIM.getInstance() != null)
   RongIM.getInstance().startPrivateChat(mContext, bean.VipId, "title");

//设置自己发出的消息监听器
if (RongIM.getInstance() != null) {
	  RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
	}

//设置接收消息的监听器。
RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());

//扩展功能自定义
InputProvider.ExtendProvider[] provider = {
  new ContactsProvider(RongContext.getInstance()),//自定义推荐订单
  new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
  new ImageInputProvider(RongContext.getInstance()),//图片
  new CameraInputProvider(RongContext.getInstance()),//相机
  new LocationInputProvider(RongContext.getInstance()),//地理位置
//					            	  new VoIPInputProvider(RongContext.getInstance())// 语音通话
  
};
RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE, provider);

//注册自定义消息
RongIM.registerMessageType(CustomizeMessage.class);
RongIM.registerMessageType(CustomizeOrderMessage.class);

//注册消息模板
RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(mContext,mActivity));
//注册自定义订单消息模板
RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(mContext,mActivity));
}

*//**
	* 连接融云失败
	* @param errorCode 错误码，可到官网 查看错误码对应的注释
	*/
/*
@Override
public void onError(RongIMClient.ErrorCode errorCode) {
Toast.makeText(mContext, "ErrorCode", zero).show();
}
});
}

}
});
}
});
}


*//**
	* 查看详情
	*/
/*
public void initListener() {
mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		Bundle bundle = new Bundle();
		bundle.putString("TradeId", orderLis.get(position).TradeId);
		Skip.mNextFroData(mActivity, GetUnReceiveOrderDetailActivity.class,
				bundle);
	}
});
}

*//**
	* 初始化数据
	*/
/*
public void initData() {

loadData(1);
mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
// 设置下拉刷新监听
mRefreshAndLoadMoreView
		.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				refresh=true;
				pageIndex = 1;
				loadData(pageIndex);
			}
		});
// 设置加载监听
mLoadMoreListView
		.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				refresh=false;
				loadData(pageIndex + 1);
			}
		});

}

protected void loadData(final int page) {
// TODO Auto-generated method stub

if (page == 1) {
	orderLis.clear();
}
net = new GetUnReceiveOrderNet(mContext);
net.setData(page);

pageIndex = page;

}

public void onEventMainThread(ResultGetUnReceiveOrderBean msg) {

if (msg != null) {
	if (msg.TModel.size() > zero&&msg.TModel.size()==10) {
		mLoadMoreListView.setHaveMoreData(true);
	} else {
		mLoadMoreListView.setHaveMoreData(false);
	}
	if(msg.TModel.size()!=zero){
		orderLis.addAll(msg.TModel);
		*//**
			* 按照时间排序显示
			*//*
			Collections.sort(orderLis, new Comparator<GetUnReceiveOrderBean>() {
			@Override
			public int compare(GetUnReceiveOrderBean lhs, GetUnReceiveOrderBean rhs) {
			
			Date date0=null;
			Date date1=null;
			for (int i = zero; i < orderLis.size(); i++) {
			date0=AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
			date1=AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
			}
			
			// 对日期字段进行升序，如果欲降序可采用after方法  
			if (date0.after(date1)) {  
			
			return 1;  
			}  
			
			return -1;
			}
			});
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
			
			}
			
			}
			
			public void initBack(){
			il_receive_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Skip.mBack(mActivity);
			}
			});
			}
			
			@Override
			protected void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			MainActivity.activity.start();
			}
			}
			
			*/

package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.OrderAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.GetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.net.GetUnReceiveOrderNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 待接单页面
 * 
 * @author FGB
 *
 */
@ContentView(R.layout.activity_order_layout)
public class GetUnReceiveOrderActivity extends BasesActivity {

	private GetUnReceiveOrderNet net;
	private OrderAdapter adapter;

	private static int pageIndex = 1;
	@ViewInject(R.id.load_more_list)
	private LoadMoreListView mLoadMoreListView;
	@ViewInject(R.id.refresh_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
	private static GetUnReceiveOrderActivity fragment;
//	@ViewInject(R.id.il_receive_back)
//	private LinearLayout il_receive_back;

	private ArrayList<GetUnReceiveOrderBean> orderLis = new ArrayList<GetUnReceiveOrderBean>();
	private boolean refresh = false;

	public static Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			pageIndex = 1;
			fragment.loadData(pageIndex);
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		fragment = this;
		adapter = new OrderAdapter(mContext, mActivity);
		mLoadMoreListView.setAdapter(adapter);
		initData();
//		initBack();
	}


	/**
	 * 初始化数据
	 */
	public void initData() {

		loadData(1);
		mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
		mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
		// 设置下拉刷新监听
		mRefreshAndLoadMoreView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				refresh = true;
				pageIndex = 1;
				loadData(pageIndex);

			}
		});
		// 设置加载监听
		mLoadMoreListView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
			@Override
			public void onLoadMore() {

				refresh = false;
				loadData(pageIndex + 1);
			}
		});

	}

	protected void loadData(final int page) {

		net = new GetUnReceiveOrderNet(mContext);
		net.setData(page);

		pageIndex = page;
	}

	public void onEventMainThread(ResultGetUnReceiveOrderBean msg) {

		if (msg != null) {

			if (msg.TModel.size() != 0) {
				if (pageIndex == 1) {
					orderLis.clear();
				}
				orderLis.addAll(msg.TModel);
				/**
				 * 按照时间排序显示
				 */
				Collections.sort(orderLis, new Comparator<GetUnReceiveOrderBean>() {
					@Override
					public int compare(GetUnReceiveOrderBean lhs, GetUnReceiveOrderBean rhs) {

						Date date0 = null;
						Date date1 = null;
						for (int i = 0; i < orderLis.size(); i++) {
							date0 = AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
							date1 = AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
						}

						// 对日期字段进行升序，如果欲降序可采用after方法
						if (date0.after(date1)) {
							return 1;
						}
						return -1;
					}
				});
			}

			// 当加载完成之后设置此时不在刷新状态
			adapter.updateListView(orderLis);
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
			if (msg.TModel.size() > 0 && msg.TModel.size() == 10) {
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
				// if(refresh){
				// Toast.makeText(mContext, "暂时没有数据哦!",
				// Toast.LENGTH_SHORT).show();
				// }

			}
		}

	}

//	public void initBack() {
//		il_receive_back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Skip.mBack(mActivity);
//			}
//		});
//	}
}
