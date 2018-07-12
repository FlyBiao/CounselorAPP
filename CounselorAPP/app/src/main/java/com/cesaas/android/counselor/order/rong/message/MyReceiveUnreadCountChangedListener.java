package com.cesaas.android.counselor.order.rong.message;

import io.rong.imkit.RongIM.OnReceiveUnreadCountChangedListener;
import android.util.Log;


/**
 * 接收未读消息的监听器。
 * @author FGB	
 */
public class MyReceiveUnreadCountChangedListener implements OnReceiveUnreadCountChangedListener{

	
	public static final String TAG = "MyReceiveUnreadCountChangedListener";

	/**
	 * 
	 *  @param count 未读消息数。
	 */
	@Override
	public void onMessageIncreased(int count) {
		Log.i("MessageListener", "未读消息=："+count);
	}

}
