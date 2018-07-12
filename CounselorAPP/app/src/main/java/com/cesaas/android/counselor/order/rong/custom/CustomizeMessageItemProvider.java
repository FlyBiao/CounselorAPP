package com.cesaas.android.counselor.order.rong.custom;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utils.AndroidEmoji;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.rong.activity.WebViewShopActivity;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

/**
 * 自定义商品消息显示。
 * 
 * @author fgb
 * 
 */
@ProviderTag(messageContent = CustomizeMessage.class)
public class CustomizeMessageItemProvider extends IContainerItemProvider.MessageProvider<CustomizeMessage> {

	private static final String TAG = "CustomizeMessageItemProvider";
	private Context ct;
	private Activity activity;
	public static BitmapUtils bitmapUtils;
	
	public CustomizeMessageItemProvider(Context ct,Activity activity){
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
		View view = LayoutInflater.from(context).inflate(R.layout.item_customize_message, null);
        ViewHolder holder = new ViewHolder();
        
        holder.tv_customize_title = (TextView) view.findViewById(R.id.tv_customize_title);
        holder.tv_customize_shopStyleId = (TextView) view.findViewById(R.id.tv_customize_shopStyleId);
        holder.tv_customize_url = (TextView) view.findViewById(R.id.tv_customize_url);
        holder.iv_customize_imgUrl = (ImageView) view.findViewById(R.id.iv_customize_imgUrl);
        holder.ll_msg = (RelativeLayout) view.findViewById(R.id.ll_msg);
        view.setTag(holder);
        
        return view;
	}
	

	/**
	 * 将数据填充 View 上。
	 */
	@Override
	public void bindView(View v, int position, CustomizeMessage content,
			UIMessage message) {
		 ViewHolder holder = (ViewHolder) v.getTag();

	        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
	            holder.ll_msg.setBackgroundResource(R.mipmap.rc_ic_bubble_right);
	        } else {
	            holder.ll_msg.setBackgroundResource(R.mipmap.rc_ic_bubble_left);
	        }
//		AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
		 
	        holder.tv_customize_title.setText(content.Title);
	        holder.tv_customize_shopStyleId.setText(content.ShopStyleId);
	        holder.tv_customize_url.setText(content.Url);
	        bitmapUtils.display(holder.iv_customize_imgUrl, content.ImageUrl, App.mInstance().bitmapConfig);
	}
	
	class ViewHolder {
		TextView tv_customize_title;
		TextView tv_customize_shopStyleId;
		TextView tv_customize_url;
		ImageView iv_customize_imgUrl;
		RelativeLayout ll_msg;
	}
	
	/**
	 * 该条消息为该会话的最后一条消息时，会话列表要显示的内容，通过该方法进行定义。
	 */
	@Override
	public Spannable getContentSummary(CustomizeMessage arg0) {
		String title=arg0.Title;
		if(title!=null){
			return new SpannableString(arg0.Title);
		}else{
			return null;
		}
		
	}

	/**
	 * 点击该类型消息触发。
	 */
	@Override
	public void onItemClick(View v, int posion, CustomizeMessage content,
			UIMessage message) {
		if(message.getContent() instanceof CustomizeMessage){
			
			CustomizeMessage mCustomizeMessage=(CustomizeMessage) message.getContent();
			Bundle bundle = new Bundle();
			bundle.putString("url",mCustomizeMessage.Url);
			Skip.mNextFroData(activity, WebViewShopActivity.class,bundle);
		}
	}

	/**
	 * 长按该类型消息触发。
	 */
	@Override
	public void onItemLongClick(View v, int posion, CustomizeMessage arg2,
			UIMessage arg3) {
		// TODO Auto-generated method stub
		
	}

}
