package com.cesaas.android.counselor.order.rong.custom;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

/**
 * 自定义话术消息显示。
 * 
 * @author fgb
 * 
 */
@ProviderTag(messageContent = CustomizeSalesTalkMessage.class)
public class CustomizeSalesMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeSalesTalkMessage> {

	private Context ct;
	private Activity activity;
	
	public CustomizeSalesMessageItemProvider(Context ct,Activity activity){
		this.ct=ct;
		this.activity=activity;
	}
	
	
	/**
	 * 初始化 View。
	 */
	@Override
	public View newView(Context context, ViewGroup group) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_customize_sales_talk_message, null);
        ViewHolder holder = new ViewHolder();
        
        holder.tv_sales_talk_content = (TextView) view.findViewById(R.id.tv_sales_content);
        holder.ll_message = (LinearLayout) view.findViewById(R.id.ll_message);
        view.setTag(holder);
        
        return view;
	}

	/**
	 * 将数据填充 View 上。
	 */
	@Override
	public void bindView(View arg0, int arg1, CustomizeSalesTalkMessage arg2,
			UIMessage arg3) {
		 	ViewHolder holder = (ViewHolder) arg0.getTag();
		if (arg3.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
			holder.ll_message.setBackgroundResource(R.mipmap.rc_ic_bubble_right);
//			holder.tv_sales_talk_content.getText().toString();
		} else {
			holder.ll_message.setBackgroundResource(R.mipmap.rc_ic_bubble_left);
		}
	        holder.tv_sales_talk_content.setText(arg2.Content);
	}
	
	class ViewHolder {
		TextView tv_sales_talk_content;
		LinearLayout ll_message;
	}
	
	/**
	 * 该条消息为该会话的最后一条消息时，会话列表要显示的内容，通过该方法进行定义。
	 */
	@Override
	public Spannable getContentSummary(CustomizeSalesTalkMessage arg0) {
		String title=arg0.Content;
		if(title!=null){
			return new SpannableString(arg0.Content);
		}else{
			return null;
		}
		//return new SpannableString(arg0.Content);
	}

	/**
	 * 点击该类型消息触发。
	 */
	@Override
	public void onItemClick(View v, int posion, CustomizeSalesTalkMessage content,
			UIMessage message) {
//		if(message.getContent() instanceof CustomizeMessage){
//			
//			CustomizeMessage mCustomizeMessage=(CustomizeMessage) message.getContent();
//			Bundle bundle = new Bundle();
//			bundle.putString("url",mCustomizeMessage.Url);
//			Skip.mNextFroData(activity, WebViewShopActivity.class,bundle);
//		}
	}

	/**
	 * 长按该类型消息触发。
	 */
	@Override
	public void onItemLongClick(View v, int posion, CustomizeSalesTalkMessage arg2,
			UIMessage arg3) {
		// TODO Auto-generated method stub
		
	}
}
