package com.cesaas.android.counselor.order.rong.message;

import com.cesaas.android.counselor.order.fragment.ReceiveOrderFragment;

import android.util.Log;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * 接受消息监听
 * @author fgb
 *
 */
public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

	private static final String TAG = "MyReceiveMessageListener";

	/**
     * 收到消息的处理。
     * 
     * @param message 收到的消息实体。
     * @param i    剩余未拉取消息数目。
     * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
     */
	@Override
	public boolean onReceived(Message message, int i) {
		//
		Log.i(TAG, "收到消息"+message+"发送人：="+message.getTargetId());
		return false;
	}

}
