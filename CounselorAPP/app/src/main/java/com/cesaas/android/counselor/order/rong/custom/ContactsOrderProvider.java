package com.cesaas.android.counselor.order.rong.custom;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.GetO2OOrderListActivity;

/**
 * 会话扩展功能:发送订单自定义
 * 
 * @author fgb
 * 
 */
public class ContactsOrderProvider extends InputProvider.ExtendProvider {

	private static final String TAG="ContactsOrderProvider";
	HandlerThread mWorkThread;
	Handler mUploadHandler;
	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE=101;

	public ContactsOrderProvider(RongContext context) {
		super(context);
		mWorkThread = new HandlerThread("RongDemo");
		mWorkThread.start();
		mUploadHandler = new Handler(mWorkThread.getLooper());
	}

	/***
	 * 设置展示的图标*
	 * 
	 * @param context
	 *            *
	 * @return
	 * 
	 */
	@Override
	public Drawable obtainPluginDrawable(Context context) {
		// R.drawable.de_contacts 通讯录图标
		return context.getResources().getDrawable(R.drawable.orders);
	}

	/**
	 * 设置图标下的title*
	 * 
	 * @param context
	 *            *
	 * @return
	 * 
	 */
	@Override
	public CharSequence obtainPluginTitle(Context context) {
		// 自定义功能名称
		return context.getString(R.string.custom_o2oorder);
	}

	/***
	 * click 事件*
	 * 
	 * @param view
	 */
	@Override
	public void onPluginClick(View view) {
		Intent intent = new Intent();
		intent.setClass(getContext(), GetO2OOrderListActivity.class);
		startActivityForResult(intent, REQUEST_CONTACT);
	}
	
	/**
	 * 处理Activity返回的数据
	 */
	@SuppressWarnings({ "deprecation", "deprecation" })
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_CODE) {
            int OrderStatus=data.getIntExtra("OrderStatus",-1);
            int ExpressType=data.getIntExtra("ExpressType",-1);
            String CreateDate=data.getStringExtra("CreateDate");
            String TradeId=data.getStringExtra("TradeId");
            
            if(RongIM.getInstance()!=null && RongIM.getInstance().getRongIMClient()!=null){
            	
            CustomizeOrderMessage rongRedPacketMessage = CustomizeOrderMessage.obtain(CreateDate, TradeId,OrderStatus,ExpressType);

            RongIM.getInstance().getRongIMClient().sendMessage(getCurrentConversation().getConversationType(), getCurrentConversation().getTargetId(), rongRedPacketMessage, null, null, new RongIMClient.SendMessageCallback() {
                @Override
                public void onError(Integer integer, RongIMClient.ErrorCode errorCode) {
                    Log.e("CustomizeProvider", "-----onError--" + errorCode);
                }

                @Override
                public void onSuccess(Integer integer) {
                	 Log.i(TAG, "-----onSuccess--" + integer);
                    Log.e("CustomizeProvider", "-----onSuccess--" + integer);
                }
            });
            }
        }

		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
