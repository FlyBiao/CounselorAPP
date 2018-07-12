package com.cesaas.android.counselor.order.adapter;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation.ConversationType;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.express.view.ExpressListActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 我的接单订单数据适配器
 * 
 * @author FGB
 * 
 */
public class SendOrderAdapter extends BaseAdapter {
	
	private ListView lv;
	private Context ct;
	private Activity activity;
	private AbPrefsUtil abpUtil;
	
	private TextView tv_my_receive_user;
	private TextView tv_order_createTime;
	private TextView tv_order_id;
	private TextView tv_order_session1;
	private TextView tv_order_session;
	private TextView tv_order_type;
	private TextView tv_express_send;
	private TextView tv_scan_send;
	
	private List<GetByCounselorBean> data= new ArrayList<GetByCounselorBean>();
	private List<GetByCounselorBeanItemBean> list = new ArrayList<GetByCounselorBeanItemBean>();
	
	public SendOrderAdapter(Context ct, Activity activity) {
		this.ct = ct;
		this.activity = activity;
	}

	public SendOrderAdapter(Context ct, List<GetByCounselorBean> data) {
		this.ct = ct;
		this.data = (ArrayList<GetByCounselorBean>) data;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GetByCounselorBean> list) {
		this.data = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		
		convertView = LayoutInflater.from(ct).inflate(R.layout.item_all_order_state, parent, false);
		lv=(ListView) convertView.findViewById(R.id.list_receive_order_things);
		tv_my_receive_user=(TextView) convertView.findViewById(R.id.tv_my_receive_user);
		tv_order_createTime=(TextView) convertView.findViewById(R.id.tv_order_createTime);
		tv_order_id=(TextView) convertView.findViewById(R.id.tv_order_id);
		tv_order_session1=(TextView) convertView.findViewById(R.id.tv_order_session1);
		tv_order_session=(TextView) convertView.findViewById(R.id.tv_order_session);
		tv_order_type=(TextView) convertView.findViewById(R.id.tv_order_type);
		tv_express_send=(TextView) convertView.findViewById(R.id.tv_express_send);
		tv_scan_send=(TextView) convertView.findViewById(R.id.tv_scan_send);
		
		final GetByCounselorBean bean=data.get(position);
		
		tv_my_receive_user.setText(bean.NickName+"("+bean.Mobile+")");
		tv_order_createTime.setText(bean.CreateDate);
		tv_order_id.setText(bean.TradeId);
		
		if(bean.ExpressType==0){//物流发货
			tv_order_session1.setVisibility(View.VISIBLE);
			tv_order_session.setVisibility(View.GONE);
			tv_order_type.setText("物流订单");
			tv_order_type.setTextColor(ct. getResources().getColor(R.color.forestgreen));
			
			//物流订单发货点击事件监听
			tv_express_send.setVisibility(View.VISIBLE);
			tv_express_send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Skip.mNext(activity, ExpressListActivity.class);
				}
			});
			
	  }else if(bean.ExpressType==1){//到店自提扫描发货
		  tv_order_session1.setVisibility(View.GONE);
		  tv_order_session.setVisibility(View.VISIBLE);
		  tv_order_type.setText("到店自提");
		  tv_order_type.setTextColor(ct.getResources().getColor(R.color.color_title_bar));
	
		//扫描发货点击事件监听
//		holder.getView(R.id.tv_express_send).setVisibility(View.GONE);
//		holder.getView(R.id.tv_scan_send).setVisibility(View.VISIBLE);
//		holder.setOnClickListener(R.id.tv_scan_send, new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Skip.mNext(getActivity(), ScanSendActivity.class);
//			}
//		});
		
	}
		
		list = new ArrayList<GetByCounselorBeanItemBean>();
		for (int i = 0; i < bean.OrderItem.size(); i++) {
			tv_order_id.setText("" + bean.OrderItem.get(i).OrderId);
			GetByCounselorBeanItemBean itemBean=new ResultGetByCounselorBean().new GetByCounselorBeanItemBean();
			itemBean=bean.OrderItem.get(i);
			list.add(itemBean);
		}
		
		MyReceiveOrderThingsAdapter adapter=new MyReceiveOrderThingsAdapter(ct, list);
		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View listItem = adapter.getView(i, null, lv);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = lv.getLayoutParams();
		params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		lv.setLayoutParams(params);
		
		/**
		 * 查看订单详情
		 */
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

				Bundle bundle = new Bundle();
				bundle.putString("TradeId", data.get(position).OrderItem.get(i).OrderId);
				Skip.mNextFroData(activity, OrderDetailActivity.class, bundle);
			}
		});
		
		lv.setAdapter(adapter);
		
		//会话
		tv_order_session.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (ct.getApplicationInfo().packageName.equals(App.getCurProcessName(ct))) 
				{abpUtil = AbPrefsUtil.getInstance();
				/**
				 * IMKit SDK调用第二步,建立与服务器的连接
				 */
				RongIM.connect(abpUtil.getString("RongToken"),new RongIMClient.ConnectCallback() {

							/**
							 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App
							 * Server 重新请求一个新的 Token
							 */
							@Override
							public void onTokenIncorrect() {
								Toast.makeText(ct, "onTokenIncorrect", 0).show();
							}

							/**
							 * 连接融云成功
							 * 
							 * @param userid
							 *            当前 token
							 */
							@SuppressWarnings("static-access")
							@Override
							public void onSuccess(String userid) {

								// 启动单聊会话界面
								if (RongIM.getInstance() != null)
									RongIM.getInstance().startPrivateChat(ct, bean.VipId, bean.CounselorName);

								// 设置自己发出的消息监听器
								if (RongIM.getInstance() != null) {
									RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
								}

								// 设置接收消息的监听器。
								RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());

								// 扩展功能自定义
								InputProvider.ExtendProvider[] provider = {
										new ContactsProvider(RongContext.getInstance()),// 自定义推荐订单
										new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
										new ImageInputProvider(RongContext.getInstance()),// 图片
										new CameraInputProvider(RongContext.getInstance()),// 相机
										new LocationInputProvider(RongContext.getInstance()),// 地理位置

								};
								RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE,provider);

								// 注册自定义消息
								RongIM.registerMessageType(CustomizeMessage.class);
								RongIM.registerMessageType(CustomizeOrderMessage.class);
								
								// 注册消息模板
								RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(ct, activity));
								//注册自定义订单消息模板
				            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(ct, activity));
				            	
							}

							/**
							 * 连接融云失败
							 * 
							 * @param errorCode
							 *            错误码，可到官网 查看错误码对应的注释
							 */
							@Override
							public void onError(RongIMClient.ErrorCode errorCode) {
								Toast.makeText(ct, "连接失败", 0).show();
							}
						});
			}
				
			}
		});
		
		return convertView;
	}

	

}
