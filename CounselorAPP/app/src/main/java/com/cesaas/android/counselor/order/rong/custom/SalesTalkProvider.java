package com.cesaas.android.counselor.order.rong.custom;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.net.service.SendSalesTalkMsgNet;
import com.cesaas.android.counselor.order.member.net.service.SendWxMsgNet;
import com.cesaas.android.counselor.order.member.salestalk.SalesTalkListActivity;
import com.cesaas.android.counselor.order.rong.activity.SalesTalkProviderActivity;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 话术提供者
 * @author FGB
 *
 */
public class SalesTalkProvider extends InputProvider.ExtendProvider{

	HandlerThread mWorkThread;
	Handler mUploadHandler;
	final int RESULT_CODE=10;
	private Activity mActivity;
	private String content;
	private String vipId;
	private SendSalesTalkMsgNet wxMsgNet;

	public SalesTalkProvider(RongContext context,Activity mActivity) {
		super(context);
		this.mActivity=mActivity;
		EventBus.getDefault().register(this);
		mWorkThread = new HandlerThread("RongDemo");
		mWorkThread.start();
		mUploadHandler = new Handler(mWorkThread.getLooper());
	}

	/**
	 * 设置显示标题icon
	 */
	@Override
	public Drawable obtainPluginDrawable(Context context) {
		return context.getResources().getDrawable(R.drawable.words);
	}

	/**
	 * 设置标题下的名称
	 */
	@Override
	public CharSequence obtainPluginTitle(Context context) {
		// 自定义功能名称
		return context.getString(R.string.custom_words_art);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onPluginClick(View view) {
		Intent intent = new Intent();
		intent.putExtra("Type",String.valueOf(1));
		intent.setClass(getContext(), SalesTalkListActivity.class);
		startActivityForResult(intent, RESULT_CODE);
	}
	
	/**
	 * 处理Activity返回的数据
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_CODE) {
			if(data!=null){
				content=data.getStringExtra("result");
				if(RongIM.getInstance()!=null && RongIM.getInstance().getRongIMClient()!=null){
					CustomizeSalesTalkMessage rongRedPacketMessage = CustomizeSalesTalkMessage.obtain(content);
					RongIM.getInstance().getRongIMClient().sendMessage(getCurrentConversation().getConversationType(), getCurrentConversation().getTargetId(), rongRedPacketMessage, null, null, new RongIMClient.SendMessageCallback() {
						@Override
						public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
						}

						@Override
						public void onSuccess(Integer integer) {

						}
					});
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onEventMainThread(String msg) {
		vipId=msg;
		if(vipId!=null && content!=null){
			wxMsgNet=new SendSalesTalkMsgNet(getContext());
			wxMsgNet.setData(vipId,1,content,"");

			vipId=null;
			content=null;
		}
	}

}
