package com.cesaas.android.counselor.order.rong.custom;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.PushOrderDetailActivity;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 自定义订单消息显示。
 * 
 * @author fgb
 * 
 */
@ProviderTag(messageContent = CustomizeOrderMessage.class)
public class CustomizeMessageOrderItemProvider extends IContainerItemProvider.MessageProvider<CustomizeOrderMessage> {

	private static final String TAG = "CustomizeMessageOrderItemProvider";
	private Context ct;
	private Activity activity;
	public static BitmapUtils bitmapUtils;
	
	public CustomizeMessageOrderItemProvider(Context ct,Activity activity){
		this.ct=ct;
		this.activity=activity;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}
	
	
	/**
	 * 初始化 View。
	 */
	@Override
	public View newView(Context context, ViewGroup group) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_customize_order_message, null);
        ViewHolder holder = new ViewHolder();
        
        holder.tv_customize_orderid = (TextView) view.findViewById(R.id.tv_customize_orderid);
        holder.tv_custom_order_message_prompt=(TextView) view.findViewById(R.id.tv_custom_order_message_prompt);
        //holder.tv_customize_order_cratedate = (TextView) view.findViewById(R.id.tv_customize_order_cratedate);
        view.setTag(holder);
        
        return view;
	}
	

	/**
	 * 将数据填充 View 上。
	 */
	@Override
	public void bindView(View v, int position, CustomizeOrderMessage content,
			UIMessage message) {
		
			ViewHolder holder = (ViewHolder) v.getTag();
		
			if(message.getMessageDirection()== UIMessage.MessageDirection.SEND){//自己发送的消息
				holder.tv_custom_order_message_prompt.setText("订单消息：");
			}else{
				holder.tv_custom_order_message_prompt.setText("您收到新的订单！可以抢单进行发货！");
			}
		 	
	        holder.tv_customize_orderid.setText(content.OrderNo);
//	        holder.tv_customize_order_cratedate.setText(content.CreateDate);
//	        holder.tv_customize_url.setText(content.Url);
//	        bitmapUtils.display(holder.iv_customize_imgUrl, content.ImageUrl, App.mInstance().bitmapConfig);
	}
	
	class ViewHolder {
		TextView tv_customize_orderid;
		TextView tv_customize_order_cratedate;
		TextView tv_custom_order_message_prompt;
	}
	
	/**
	 * 该条消息为该会话的最后一条消息时，会话列表要显示的内容，通过该方法进行定义。
	 */
	@Override
	public Spannable getContentSummary(CustomizeOrderMessage arg0) {
		String TradeId=arg0.OrderNo;
		if(TradeId!=null){
			return new SpannableString(arg0.OrderNo);
		}else{
			return null;
		}
		
	}

	/**
	 * 点击该类型消息触发。
	 */
	@Override
	public void onItemClick(View v, int posion, CustomizeOrderMessage content,
			UIMessage message) {
		if(message.getContent() instanceof CustomizeOrderMessage){
			
			CustomizeOrderMessage mCustomizeMessage=(CustomizeOrderMessage) message.getContent();
			Bundle bundle = new Bundle();
			bundle.putString("TradeId", mCustomizeMessage.OrderNo);
			Skip.mNextFroData(activity, PushOrderDetailActivity.class, bundle);
			
		}
	}

	/**
	 * 长按该类型消息触发。
	 */
	@Override
	public void onItemLongClick(View v, int posion, CustomizeOrderMessage arg2,
			UIMessage arg3) {
		// TODO Auto-generated method stub
		
	}

}
