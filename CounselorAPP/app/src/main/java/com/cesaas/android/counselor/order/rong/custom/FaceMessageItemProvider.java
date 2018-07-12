package com.cesaas.android.counselor.order.rong.custom;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.vip.MemberPortraitActivity;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

/**
 * 自定义人脸识别显示。
 * 
 * @author fgb
 * 
 */
@ProviderTag(messageContent = FaceMessage.class)
public class FaceMessageItemProvider extends IContainerItemProvider.MessageProvider<FaceMessage> {

	private Context ct;
	private Activity activity;
	public static BitmapUtils bitmapUtils;

	public FaceMessageItemProvider(Context ct, Activity activity){
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
		View view = LayoutInflater.from(context).inflate(R.layout.item_customize_face_message, null);
        ViewHolder holder = new ViewHolder();
        
        holder.tv_customize_title = (TextView) view.findViewById(R.id.tv_customize_title);
        holder.tv_customize_shopStyleId = (TextView) view.findViewById(R.id.tv_customize_shopStyleId);
        holder.tv_customize_url = (TextView) view.findViewById(R.id.tv_customize_url);
		holder.ll_message= (LinearLayout) view.findViewById(R.id.ll_message);
        view.setTag(holder);
        
        return view;
	}


	/**
	 * 将数据填充 View 上。
	 */
	@Override
	public void bindView(View v, int position, FaceMessage content,
			UIMessage message) {
		 final ViewHolder holder = (ViewHolder) v.getTag();

	        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
	            holder.ll_message.setBackgroundResource(R.mipmap.rc_ic_bubble_right);
	        } else {
	            holder.ll_message.setBackgroundResource(R.mipmap.rc_ic_bubble_left);
	        }
		// AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
		 
	        holder.tv_customize_title.setText("新的消息:"+content.Name);
	        holder.tv_customize_shopStyleId.setText(content.Sex);
	}
	
	class ViewHolder {
		TextView tv_customize_title;
		TextView tv_customize_shopStyleId;
		TextView tv_customize_url;
		LinearLayout ll_message;
	}
	
	/**
	 * 该条消息为该会话的最后一条消息时，会话列表要显示的内容，通过该方法进行定义。
	 */
	@Override
	public Spannable getContentSummary(FaceMessage arg0) {
		String title=arg0.Name;
		if(title!=null){
			return new SpannableString(arg0.Name);
		}else{
			return null;
		}
		
	}

	/**
	 * 点击该类型消息触发。
	 */
	@Override
	public void onItemClick(View v, int posion, FaceMessage content,
			UIMessage message) {
		if(message.getContent() instanceof FaceMessage){
			
			FaceMessage faceMessage=(FaceMessage) message.getContent();
			Bundle bundle = new Bundle();
			bundle.putString("VipId",faceMessage.VipId);
			bundle.putString("ImageUrl",faceMessage.ImageUrl);
			bundle.putString("FaceFrame",faceMessage.FaceFrame);
			bundle.putString("CreateTime",faceMessage.CreateTime);
			bundle.putString("Sex",faceMessage.Sex);
			Skip.mNextFroData(activity, MemberPortraitActivity.class,bundle);
		}
	}

	/**
	 * 长按该类型消息触发。
	 */
	@Override
	public void onItemLongClick(View v, int posion, FaceMessage arg2,
			UIMessage arg3) {
		// TODO Auto-generated method stub
		
	}

}
