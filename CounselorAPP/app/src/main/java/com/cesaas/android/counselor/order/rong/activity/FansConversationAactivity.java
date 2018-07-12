package com.cesaas.android.counselor.order.rong.activity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.global.App;

/**
 * Fans回话页面
 * @author cebsaas
 *
 */
public class FansConversationAactivity extends BasesActivity{

	private String RongToken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RongToken=prefs.getString("RongToken");
		
		connect(RongToken);
	}
	
	/**
	 * 建立与融云服务器的连接
	 *
	 * @param token
	 */
	private void connect(String token) {

	    if (this.getApplicationInfo().packageName.equals(App.getCurProcessName(getApplicationContext()))) {

	        /**
	         * IMKit SDK调用第二步,建立与服务器的连接
	         */
	        RongIM.connect(token, new RongIMClient.ConnectCallback() {

				/**
	             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
	             */
	            @Override
	            public void onTokenIncorrect() {
	            	Toast.makeText(getApplicationContext(), "onTokenIncorrect", 0).show();
	            }

	            /**
	             * 连接融云成功
	             * @param userid 当前 token
	             */
	            @Override
	            public void onSuccess(String userid) {

	            	//启动会话界面
	            	if (RongIM.getInstance() != null)
	            	                    RongIM.getInstance().startPrivateChat(getApplicationContext(), "26594", "title");

	            	//启动会话列表界面
//	            	if (RongIM.getInstance() != null)
//	            	                   RongIM.getInstance().startConversationList(getApplicationContext());

	            	//启动聚合会话列表界面
//	            	if (RongIM.getInstance() != null)
//	            	                   RongIM.getInstance().startSubConversationList(getActivity(), Conversation.ConversationType.GROUP);
	            }

	            /**
	             * 连接融云失败
	             * @param errorCode 错误码，可到官网 查看错误码对应的注释
	             */
	            @Override
	            public void onError(RongIMClient.ErrorCode errorCode) {
	            	Toast.makeText(getApplicationContext(), "ErrorCode"+errorCode.getValue(), 0).show();
	                Log.d("LoginActivity", "--onError" + errorCode);
	            }
	        });
	    }
	}
}
