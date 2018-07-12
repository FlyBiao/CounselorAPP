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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderDetailBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 佣金订单详情数据适配器
 * @author FGB
 *
 */
public class OrderEarningsDetailAdapter extends BaseAdapter{

private static final String TAG = "OrderEarningsDetailAdapter";
	
	private Context ct;
	private List<OrderDetailBean> data=new ArrayList<OrderDetailBean>();
	private List<DistributionOrderBean> data2=new ArrayList<DistributionOrderBean>();
	
	public static BitmapUtils bitmapUtils;
	
	private TextView tv_order_earnings_session;
	private TextView tv_send_order_detail;
	private LinearLayout consignee_name;
	
	private AbPrefsUtil abpUtil;
	private Activity activity;
	

	public OrderEarningsDetailAdapter(Context ct,Activity activity,List<OrderDetailBean> data,List<DistributionOrderBean> data2){
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
	public void updateListView(List<OrderDetailBean> list,List<DistributionOrderBean> lsit2) {
		this.data = list;
		this.data2=lsit2;
//		notifyDataSetChanged();
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
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_order_earnings_detail, null);
			viewHolder.tv_order_earnings_user = (TextView) convertView.findViewById(R.id.tv_order_earnings_user);
			viewHolder.tv_order_user_earnings_mobile = (TextView) convertView.findViewById(R.id.tv_order_user_earnings_mobile);
			viewHolder.tv_order_earnings_remark = (TextView) convertView.findViewById(R.id.tv_order_earnings_remark);
			viewHolder.tv_order_earnings_pay_price=(TextView) convertView.findViewById(R.id.tv_order_earnings_pay_price);
			viewHolder.tv_order_earnings_total_price=(TextView) convertView.findViewById(R.id.tv_order_earnings_total_price);
			viewHolder.tv_order_earnings_commision_develop=(TextView) convertView.findViewById(R.id.tv_order_earnings_commision_develop);
			viewHolder.tv_order_earnings_send_commision=(TextView) convertView.findViewById(R.id.tv_order_earnings_send_commision);
			viewHolder.tv_earnings_orderid=(TextView) convertView.findViewById(R.id.tv_earnings_orderid);
			viewHolder.tv_order_earnings_create_date=(TextView) convertView.findViewById(R.id.tv_order_earnings_create_date);
			viewHolder.iv_earnings_goods_img=(ImageView) convertView.findViewById(R.id.iv_earnings_goods_img);
			viewHolder.tv_earnings_uantity=(TextView) convertView.findViewById(R.id.tv_earnings_uantity);
			viewHolder.tv_earnings_attr=(TextView) convertView.findViewById(R.id.tv_earnings_attr);
			viewHolder.tv_earnings_goods_name=(TextView) convertView.findViewById(R.id.tv_earnings_goods_name);
			viewHolder.tv_order_earnings_State=(TextView) convertView.findViewById(R.id.tv_order_earnings_State);
			viewHolder.tv_order_earnings_date=(TextView) convertView.findViewById(R.id.tv_order_earnings_date);
			viewHolder.tv_order_expressType=(TextView) convertView.findViewById(R.id.tv_order_expressType);
			viewHolder.tv_order_commision_develop=(TextView) convertView.findViewById(R.id.tv_order_commision_develop);
			viewHolder.tv_order_send_commision=(TextView) convertView.findViewById(R.id.tv_order_send_commision);
			viewHolder.tv_address=(TextView) convertView.findViewById(R.id.tv_address);
			viewHolder.ll_address=(LinearLayout) convertView.findViewById(R.id.ll_address);
			
			tv_order_earnings_session=(TextView) convertView.findViewById(R.id.tv_order_earnings_session);
			consignee_name=(LinearLayout) convertView.findViewById(R.id.consignee_name);
			
			
			//
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		if(data.size()!=0){
			final OrderDetailBean detail=data.get(position);
			
			if(detail!=null){
				viewHolder.tv_order_earnings_user.setText(detail.ConsigneeName);
				viewHolder.tv_order_user_earnings_mobile.setText(detail.Mobile);
				viewHolder.tv_order_earnings_remark.setText(detail.OrderRemark);
				viewHolder.tv_order_earnings_pay_price.setText(Double.toString(detail.PayPrice));
				viewHolder.tv_order_earnings_total_price.setText(Double.toString(detail.TotalPrice));
				
				if(detail.ExpressType==0){
					viewHolder.tv_order_expressType.setText("快递");
					viewHolder.ll_address.setVisibility(View.VISIBLE);
					viewHolder.tv_address.setText(detail.Province+detail.City+detail.District+detail.Address);
					
				}else if(detail.ExpressType==1){
					viewHolder.tv_order_expressType.setText("到店自提");
					consignee_name.setVisibility(View.GONE);
				}
			}
		}
		
		if(data2.size()!=0){
			final DistributionOrderBean detail2=data2.get(0);
			
			for (int i = 0; i < data2.size(); i++) {
				
				if(data2.get(i).BonusType==0 && data2.get(i).BonusType==1){//推荐佣金 && 发货佣金
					viewHolder.tv_order_earnings_commision_develop.setText(""+detail2.OrderBonus);
					viewHolder.tv_order_earnings_send_commision.setText(""+detail2.OrderBonus);
				}
				else if(data2.get(i).BonusType==0){//推荐佣金
					viewHolder.tv_order_earnings_commision_develop.setText(""+detail2.OrderBonus);
//					viewHolder.tv_order_earnings_send_commision.setVisibility(View.GONE);
//					viewHolder.tv_order_send_commision.setVisibility(View.GONE);
				}
				
				else if(data2.get(i).BonusType==1){//发货佣金
					viewHolder.tv_order_earnings_send_commision.setText(""+detail2.OrderBonus);
//					viewHolder.tv_order_earnings_commision_develop.setVisibility(View.GONE);
//					viewHolder.tv_order_commision_develop.setVisibility(View.GONE);
				}
			}
				
				viewHolder.tv_earnings_orderid.setText("订单号:"+detail2.TradeId);
				viewHolder.tv_order_earnings_create_date.setText("下单时间:"+detail2.CreateDate);
				viewHolder.tv_order_earnings_date.setText(detail2.ReserveDate);
				
				for(int i=0;i<detail2.OrderItem.size();i++){
					bitmapUtils.display(viewHolder.iv_earnings_goods_img, detail2.OrderItem.get(i).ImageUrl, App.mInstance().bitmapConfig);
					viewHolder.tv_earnings_uantity.setText("x"+detail2.OrderItem.get(i).Quantity);
					viewHolder.tv_earnings_attr.setText(detail2.OrderItem.get(i).Attr);
					viewHolder.tv_earnings_goods_name.setText(detail2.OrderItem.get(i).Title);
				}
				
//			}
			
			/**
			 * 会话
			 */
			tv_order_earnings_session.setOnClickListener(new OnClickListener() {
				
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
		TextView tv_order_earnings_user;//用户
		TextView tv_order_user_earnings_mobile;//订单用户手机
		TextView tv_order_earnings_remark;//订单备注
		TextView tv_order_earnings_State;
		TextView tv_order_earnings_pay_price;//支付金额
		TextView tv_order_earnings_total_price;//总金额
		TextView tv_order_earnings_commision_develop;
		TextView tv_order_earnings_send_commision;
		TextView tv_earnings_attr;
		TextView tv_earnings_goods_name;
		TextView tv_earnings_orderid;
		TextView tv_order_earnings_create_date;
		TextView tv_order_earnings_date;
		ImageView iv_earnings_goods_img;
		TextView tv_earnings_uantity;
		TextView tv_order_commision_develop;
		TextView tv_order_send_commision;
		TextView tv_order_expressType;
		
		LinearLayout ll_address;
		TextView tv_address;
	}

}
