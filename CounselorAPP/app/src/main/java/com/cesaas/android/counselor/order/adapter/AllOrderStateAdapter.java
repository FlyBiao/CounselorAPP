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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ExpressActivity;
import com.cesaas.android.counselor.order.activity.ScanResultActivity;
import com.cesaas.android.counselor.order.activity.ScanSendActivity;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBean;
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
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 所有分销订单状态Adapter
 * 
 * @author FGB
 * 
 */
public class AllOrderStateAdapter extends BaseAdapter {

	private Context ct;
	
	TextView tv_sendOrder_title;
	TextView tv_sendOrder_att;
	TextView tv_order_id;
	TextView tv_order_createTime;
	TextView tv_order_total;
	TextView tv_expressType;
	TextView tv_order_quantity;
	ImageView iv_goods_img;
	
	private List<GetByCounselorBean> data = new ArrayList<GetByCounselorBean>();

	public static final String TAG = "AllOrderStateAdapter";

	public static BitmapUtils bitmapUtils;
	private Activity activity;

	private TextView tv_orderState;
	private TextView tv_order_session;
	private TextView tv_express_send;
	private TextView tv_scan_send;
	private TextView tv_scan_check;
	
	private TextView tv_order_code;

	private String consigneeName;
	private String mobile;
	private String city;

	private AbPrefsUtil abpUtil;
	
	public static GetByCounselorBean bean;
	
	public AllOrderStateAdapter(Context ct, Activity activity) {
		this.ct = ct;
		this.activity = activity;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(
				ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}

	public AllOrderStateAdapter(Context ct, List<GetByCounselorBean> data) {
		this.ct = ct;
		this.data = data;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(
				ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
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

	public void remove(GetByCounselorBean order) {
		this.data.remove(order);
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
	public View getView( final int position, View convertView, ViewGroup parent) {

			convertView = LayoutInflater.from(ct).inflate(R.layout.item_all_order_state, parent, false);
			tv_order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
			tv_order_createTime = (TextView) convertView.findViewById(R.id.tv_order_createTime);
//			tv_order_quantity = (TextView) convertView.findViewById(R.id.tv_order_quantity);
//			tv_sendOrder_title = (TextView) convertView.findViewById(R.id.tv_sendOrder_title);
//			tv_sendOrder_att = (TextView) convertView.findViewById(R.id.tv_sendOrder_att);
//			iv_goods_img = (ImageView) convertView.findViewById(R.id.iv_shop_img);
//			
//			tv_order_code=(TextView) convertView.findViewById(R.id.tv_order_code);

			tv_orderState = (TextView) convertView.findViewById(R.id.tv_orderState);
			tv_order_session = (TextView) convertView.findViewById(R.id.tv_order_session);
			tv_express_send = (TextView) convertView.findViewById(R.id.tv_express_send);
			tv_scan_send=(TextView) convertView.findViewById(R.id.tv_scan_send);
//			tv_scan_check=(TextView) convertView.findViewById(R.id.tv_scan_check);


		bean = data.get(position);

		if (bean != null) {
			tv_order_id.setText("订单号:" + bean.TradeId);
			tv_order_createTime.setText("下单时间:" +bean.CreateDate);
			
			for (int i = 0; i < bean.OrderItem.size(); i++) {
				
				tv_order_code.setText("款号:"+bean.OrderItem.get(i).StyleCode);
				tv_order_quantity.setText("x"+ bean.OrderItem.get(i).Quantity);
				tv_sendOrder_title.setText(bean.OrderItem.get(i).Title);
				tv_sendOrder_att.setText(bean.OrderItem.get(i).Attr);
				bitmapUtils.display(iv_goods_img,bean.OrderItem.get(i).ImageUrl,App.mInstance().bitmapConfig);
				
			}

			if (bean.OrderStatus == 10) {// 未支付
				tv_orderState.setText("未支付");

			} 
			else if(bean.OrderStatus == 11){//待审核
				tv_orderState.setText("待审核");
			}
			else if (bean.OrderStatus == 20) {// 支付中
				tv_orderState.setText("支付中");

			} else if (bean.OrderStatus == 30) {// 未发货
				tv_orderState.setText("用户已支付");
				
				if(bean.ExpressType==0){//物流发货
					tv_express_send.setVisibility(View.VISIBLE);
					tv_scan_send.setVisibility(View.GONE);
					tv_express_send.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("expressOrderId", bean.TradeId);
						Skip.mNextFroData((Activity) ct, ExpressActivity.class,bundle);
						
					}
				});
			}else if(bean.ExpressType==1){//到店自提扫描发货
				tv_scan_send.setVisibility(View.VISIBLE);
				tv_express_send.setVisibility(View.GONE);
				tv_scan_send.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Bundle bundle=new Bundle();
						Skip.mNext((Activity)ct, ScanSendActivity.class);
					}
				});
//				
			}
				
			} else if (bean.OrderStatus == 40) {// 已发货
				sendExpressOrder();

			} 
			else if (bean.OrderStatus == 80) {// 已退款
				tv_orderState.setText("已退款");
			}
			else if(bean.OrderStatus == 81){//取消订单
				tv_orderState.setText("订单已取消");
			}
			else if (bean.OrderStatus == 100) {// 已退款
				tv_orderState.setText("订单完成");
				tv_order_session.setVisibility(View.GONE);
			}

		}
		
		
		/**
		 * 扫描验货
		 */
		tv_scan_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String barcodeCode=data.get(position).OrderItem.get(0).BarcodeCode.toString();
				Bundle bundle=new Bundle();
				bundle.putString("barcodeCode", barcodeCode);
				Skip.mNextFroData((Activity)ct, ScanResultActivity.class,bundle);
			}
		});

		/**
		 * 会话
		 */
		tv_order_session.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (ct.getApplicationInfo().packageName.equals(App.getCurProcessName(ct))) {abpUtil = AbPrefsUtil.getInstance();
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
		
		
		/**
		 * 获取用户昵称和头像
		 */
//		RongIM.getInstance().refreshUserInfoCache(new UserInfo(fans.FANS_ID, fans.FANS_NICKNAME, Uri.parse(fans.FANS_ICON)));

		return convertView;
	}

	/**
	 * 已发货物流订单详情
	 */
	private void sendExpressOrder() {
		tv_orderState.setText("已发货物流订单");
		tv_express_send.setVisibility(View.GONE);
		tv_orderState.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 待写/此处写查看物流订单详情

			}
		});
	}

}
