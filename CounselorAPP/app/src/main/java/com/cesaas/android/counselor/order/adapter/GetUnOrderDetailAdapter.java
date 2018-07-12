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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ExpressActivity;
import com.cesaas.android.counselor.order.activity.OrderShopDetailActivity;
import com.cesaas.android.counselor.order.activity.ScanSendActivity;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.GetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.OrderItemBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 接单订单详情数据适配器
 * @author FGB
 *
 */
public class GetUnOrderDetailAdapter extends BaseAdapter{

private static final String TAG = "GetUnOrderDetailAdapter";
	
	private Context ct;
	private List<OrderDetailBean> data=new ArrayList<OrderDetailBean>();
	private List<GetUnReceiveOrderBean> data2=new ArrayList<GetUnReceiveOrderBean>();
//	private List<DistributionOrderBean> data2=new ArrayList<DistributionOrderBean>();
	
	public static BitmapUtils bitmapUtils;
	
	private LinearLayout ll_un_receive_order;
	private LinearLayout ll_un_receive_price;
	private LinearLayout ll_un_receive_shop;
	private LinearLayout ll_un_order_address;//收货地址
	
	private ListView lv;
	private List<OrderItemBean> list = new ArrayList<OrderItemBean>();
	
	private AbPrefsUtil abpUtil;
	private Activity activity;
	
	public GetUnOrderDetailAdapter(Context ct,Activity activity,List<OrderDetailBean> data,List<GetUnReceiveOrderBean> data2){
		this.ct = ct;
		this.activity=activity;
		this.data = data;
		this.data2=data2;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<OrderDetailBean> list,List<GetUnReceiveOrderBean> data2) {
		this.data = list;
		this.data2=data2;
		notifyDataSetChanged();
	}
	

	public void remove(OrderDetailBean user) {
		this.data.remove(user);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_un_order_detail, null);
			viewHolder.tv_user=(TextView)convertView.findViewById(R.id.tv_user);
			viewHolder.order_user=(TextView)convertView.findViewById(R.id.order_user);
			viewHolder.tv_un_order_user_mobile = (TextView) convertView.findViewById(R.id.tv_un_order_user_mobile);
			viewHolder.tv_un_expressType=(TextView) convertView.findViewById(R.id.tv_un_expressType);
			viewHolder.tv_un_ordere_date=(TextView) convertView.findViewById(R.id.tv_un_ordere_date);
			viewHolder.tv_un_order_address=(TextView) convertView.findViewById(R.id.tv_un_order_address);
			viewHolder.tv_un_orderDetail_State=(TextView) convertView.findViewById(R.id.tv_un_orderDetail_State);
			viewHolder.tv_un_order_commision_develop=(TextView) convertView.findViewById(R.id.tv_un_order_commision_develop);
			viewHolder.tv_un_order_send_commision=(TextView) convertView.findViewById(R.id.tv_un_order_send_commision);
			viewHolder.tv_un_order_pay_price=(TextView) convertView.findViewById(R.id.tv_un_order_pay_price);
			viewHolder.tv_un_order_total_price=(TextView) convertView.findViewById(R.id.tv_un_order_total_price);
			viewHolder.iv_un_order_img=(ImageView) convertView.findViewById(R.id.iv_un_order_img);
			viewHolder.tv_un_goods_name=(TextView) convertView.findViewById(R.id.tv_un_goods_name);
			viewHolder.tv_un_attr=(TextView) convertView.findViewById(R.id.tv_un_attr);
			viewHolder.tv_un_order_shop_barcodecode=(TextView) convertView.findViewById(R.id.tv_un_order_shop_barcodecode);
			viewHolder.tv_un_order_shop_stylecode=(TextView) convertView.findViewById(R.id.tv_un_order_shop_stylecode);
			viewHolder.tv_un_order_uantity=(TextView) convertView.findViewById(R.id.tv_un_order_uantity);
			viewHolder.tv_un_order_detail_session=(TextView) convertView.findViewById(R.id.tv_un_order_detail_session);
			viewHolder.tv_un_send_order_detail=(TextView) convertView.findViewById(R.id.tv_un_send_order_detail);
			viewHolder.tv_un_scan_orderSend_detail=(TextView) convertView.findViewById(R.id.tv_un_scan_orderSend_detail);
			viewHolder.ll_un_order_address=(LinearLayout) convertView.findViewById(R.id.ll_un_order_address);
			viewHolder.ll_un_r_date=(LinearLayout) convertView.findViewById(R.id.ll_un_r_date);
			viewHolder.ll_order_shop_detail=(LinearLayout) convertView.findViewById(R.id.ll_order_shop_detail);
			
			ll_un_receive_order=(LinearLayout) convertView.findViewById(R.id.ll_un_receive_order);
			ll_un_receive_price=(LinearLayout) convertView.findViewById(R.id.ll_un_receive_price);
			ll_un_receive_shop=(LinearLayout) convertView.findViewById(R.id.ll_un_receive_shop);
			ll_un_order_address=(LinearLayout) convertView.findViewById(R.id.ll_un_order_address);
			
			viewHolder.tv_un_order_id=(TextView) convertView.findViewById(R.id.tv_un_order_id);
			
			lv=(ListView) convertView.findViewById(R.id.list_un_order_detail_things);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(data.size()!=0){
			OrderDetailBean detail=data.get(position);
			
			viewHolder.tv_un_order_id.setText(detail.OrderId);
			viewHolder.order_user.setText(detail.NickName);
			viewHolder.tv_un_ordere_date.setText(detail.CreateDate);
			
			if(detail!=null){

				viewHolder.tv_un_order_user_mobile.setText(detail.Mobile);
				viewHolder.tv_un_order_pay_price.setText(Double.toString(detail.PayPrice));
				viewHolder.tv_un_order_total_price.setText(Double.toString(detail.TotalPrice));
				
				if(detail.ExpressType==0){//快递方式取货
					viewHolder.tv_un_expressType.setText("快递");
					ll_un_order_address.setVisibility(View.VISIBLE);
					viewHolder.tv_un_order_address.setText(detail.Province+detail.City+detail.District+detail.Address);
					
					viewHolder.tv_un_expressType.setTextColor(ct.getResources().getColor(R.color.forestgreen));
					ll_un_receive_order.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.layer_list2));
					ll_un_receive_price.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.layer_list2));
					ll_un_receive_shop.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.layer_list2));
					
				}else if(detail.ExpressType==1){//到店自提取货
					viewHolder.tv_un_expressType.setText("到店自提");
					viewHolder.tv_user.setVisibility(View.VISIBLE);
					
					viewHolder.tv_un_expressType.setTextColor(ct.getResources().getColor(R.color.color_title_bar));
					ll_un_receive_order.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.layer_list));
					ll_un_receive_price.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.layer_list));
					ll_un_receive_shop.setBackgroundDrawable(ct.getResources().getDrawable(R.drawable.layer_list));
				}
			}
		}
		
		if(data2.size()!=0){
			final GetUnReceiveOrderBean detail2=data2.get(0);
			for (int i = 0; i < data2.size(); i++) {
				
//				if(data2.get(i).BonusType==zero){//推荐佣金
//					viewHolder.tv_un_order_commision_develop.setText(""+detail2.OrderBonus);
//				}else{
//					viewHolder.tv_un_order_commision_develop.setText("zero.zero");
//				}
//				
//				if(data2.get(i).BonusType==1){//发货佣金
//					viewHolder.tv_un_order_send_commision.setText(""+detail2.OrderBonus);
//				}else{
//					viewHolder.tv_un_order_send_commision.setText("zero.zero");
//				}
			}
			
			
			list = new ArrayList<OrderItemBean>();
			
			for(int i=0;i<detail2.OrderItem.size();i++){
				OrderItemBean itemBean=new ResultGetUnReceiveOrderBean().new OrderItemBean();
//				DistributionOrderItemBean itemBean=new ResultDistributionOrderBean().new DistributionOrderItemBean();
				itemBean=detail2.OrderItem.get(i);
//				itemBean.getStyleCode() = "款号:" + detail2.OrderItem.get(i).getStyleCode();
				list.add(itemBean);
			}
			
			ReceiveOrderDetailThingsAdapter adapter=new ReceiveOrderDetailThingsAdapter(activity, list);
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
				lv.setAdapter(adapter);
				
				/**
				 * 查看订单详情
				 */
				lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

						Bundle bundle = new Bundle();
						bundle.putString("Title", list.get(i).getTitle());
						bundle.putString("ImageUrl", list.get(i).getImageUrl());
						bundle.putString("StyleCode", list.get(i).getStyleCode());
						bundle.putString("BarcodeCode", list.get(i).getBarcodeCode());
						bundle.putDouble("Price", list.get(i).getPrice());
						bundle.putString("Attr", list.get(i).getAttr());
						Skip.mNextFroData(activity, ShopDetailActivity.class, bundle);
					}
				});
				
				for(int i=0;i<detail2.OrderItem.size();i++){
					bitmapUtils.display(viewHolder.iv_un_order_img, detail2.OrderItem.get(i).getImageUrl(), App.mInstance().bitmapConfig);
					viewHolder.tv_un_order_uantity.setText("x"+detail2.OrderItem.get(i).getQuantity());
					viewHolder.tv_un_attr.setText(detail2.OrderItem.get(i).getAttr());
					viewHolder.tv_un_goods_name.setText(detail2.OrderItem.get(i).getTitle());
					viewHolder.tv_un_order_shop_barcodecode.setText("条码:"+detail2.OrderItem.get(i).getBarcodeCode());
					viewHolder.tv_un_order_shop_stylecode.setText("款号:"+detail2.OrderItem.get(i).getStyleCode());
					
				}
				
				if(detail2.OrderStatus==10){//未支付
					viewHolder.tv_un_orderDetail_State.setText("未支付");
					
				}
				else if(detail2.OrderStatus == 11){//待审核
					viewHolder.tv_un_orderDetail_State.setText("待审核");
				}
				else if(detail2.OrderStatus==20){//支付中
					viewHolder.tv_un_orderDetail_State.setText("支付中");
					
				}else if(detail2.OrderStatus==30){//已付款未发货
					viewHolder.tv_un_orderDetail_State.setText("用户已支付");
					if(detail2.ExpressType==0){//物流发货
						viewHolder.tv_un_send_order_detail.setVisibility(View.VISIBLE);
						viewHolder.tv_un_send_order_detail.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {//点击发货
							Bundle bundle=new Bundle();
							bundle.putString("expressOrderId", detail2.TradeId);
							Skip.mNextFroData((Activity)ct, ExpressActivity.class,bundle);
						}
					});
				}else if(detail2.ExpressType==1){//到店自提
					viewHolder.tv_un_scan_orderSend_detail.setVisibility(View.VISIBLE);
					viewHolder.tv_un_scan_orderSend_detail.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Skip.mNext((Activity)ct, ScanSendActivity.class);
						}
					});
				}
									
				}else if(detail2.OrderStatus==40 ){//已发货
					viewHolder.tv_un_orderDetail_State.setText("已发货物流订单");
					
				}
				else if(detail2.OrderStatus==80){//已退款
					viewHolder.tv_un_orderDetail_State.setText("已退款");
				}
				else if(detail2.OrderStatus==81){
					viewHolder.tv_un_orderDetail_State.setText("订单已取消");
				}
				else if(detail2.OrderStatus==100){//已退款
					viewHolder.tv_un_orderDetail_State.setText("订单完成");
				}
				
//			}
				
				//商品详情
				viewHolder.ll_order_shop_detail.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("oId", detail2.TradeId);
						Skip.mNextFroData(activity, OrderShopDetailActivity.class,bundle);
					}
				});
			
			/**
			 * 会话
			 */
				viewHolder.tv_un_order_detail_session.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (ct.getApplicationInfo().packageName.equals(App.getCurProcessName(ct))) {
						abpUtil=AbPrefsUtil.getInstance();
				        /**
				         * IMKit SDK调用第二步,建立与服务器的连接
				         */
				        RongIM.connect(abpUtil.getString("RongToken"), new RongIMClient.ConnectCallback() {

							/**
				             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
				             */
				            @Override
				            public void onTokenIncorrect() {
				            	Toast.makeText(ct, "onTokenIncorrect", 0).show();
				            }

				            /**
				             * 连接融云成功
				             * @param userid 当前 token
				             */
				            @Override
				            public void onSuccess(String userid) {
				            	
				            	//启动单聊会话界面
				            	if (RongIM.getInstance() != null)
				            	   RongIM.getInstance().startPrivateChat(ct, detail2.VipId, "title");
				            	
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
				            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(ct,activity));
				            }

				            /**
				             * 连接融云失败
				             * @param errorCode 错误码，可到官网 查看错误码对应的注释
				             */
				            @Override
				            public void onError(RongIMClient.ErrorCode errorCode) {
				            	Toast.makeText(ct, "ErrorCode", 0).show();
				            }
				        });
				    }
				}
			});
			
			}
		
		
		return convertView;
	}
	
	
	static class ViewHolder {
		TextView tv_un_order_user_mobile;//订单用户手机
		TextView tv_order_remark;//订单备注
		TextView tv_un_expressType;//取货方式
		TextView tv_un_ordere_date;//收货方式
		TextView tv_un_order_address;
		TextView tv_un_orderDetail_State;//订单状态
		TextView tv_un_order_commision_develop;//推荐佣金
		TextView tv_un_order_send_commision;//推荐佣金
		TextView tv_un_order_pay_price;//支付金额
		TextView tv_un_order_total_price;//总金额
		ImageView iv_un_order_img;//订单图片
		TextView tv_un_goods_name;//订单title
		TextView tv_un_attr;//规格
		TextView tv_un_order_shop_barcodecode;//条码
		TextView tv_un_order_shop_stylecode;//款号
		TextView tv_un_order_uantity;//数量
		TextView tv_un_order_detail_session;//会话
		TextView tv_un_send_order_detail;//物流发货
		TextView tv_un_scan_orderSend_detail;//扫描发货
		
		TextView tv_user;
		TextView order_user;
		
		private TextView tv_un_order_id;
		
		LinearLayout ll_un_order_address;
		LinearLayout ll_un_r_date;
		LinearLayout ll_order_shop_detail;
		
	}

}
