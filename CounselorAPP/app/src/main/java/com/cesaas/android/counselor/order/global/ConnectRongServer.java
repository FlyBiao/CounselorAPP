package com.cesaas.android.counselor.order.global;/*package com.cesaas.android.counselor.order.global;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import io.rong.imlib.model.Conversation.ConversationType;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.ToastFactory;

*//**
 * 连接融云服务器
 * @author fgb
 *
 *//*
public class ConnectRongServer {

	private String mUserId;
	private Activity activity;
	private Context context;
	private String token;
	
	public void connectRongServer(Activity activity,Context context, String token){
		this.activity=activity;
		this.context=context;
		this.token=token;
		if (activity.getApplicationInfo().packageName.equals(App.getCurProcessName(context))) {

	        *//**
	         * IMKit SDK调用第二步,建立与服务器的连接
	         *//*
	        RongIM.connect(abpUtil.getString("RongToken"), new RongIMClient.ConnectCallback() {

				*//**
	             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
	             *//*
	            @Override
	            public void onTokenIncorrect() {
	            	ToastFactory.show(context, "获取Token错误", ToastFactory.CENTER);
	            }

	            *//**
	             * 连接融云成功
	             * @param userid 当前 token
	             *//*
	            @Override
	            public void onSuccess(String userid) {
	            	mUserId=userid;
	            	//启动单聊会话界面
	            	if (RongIM.getInstance() != null)
	            	   RongIM.getInstance().startPrivateChat(getActivity(), fansLis.get(position).FANS_ID, "title");
	            	
	            	//设置自己发出的消息监听器
	            	if (RongIM.getInstance() != null) {
	            		  RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
	            		}
	            	
	            	//设置接收消息的监听器。
	            	RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
	            	
	            	//扩展功能自定义
	            	InputProvider.ExtendProvider[] provider = {
	            	  new ContactsProvider(RongContext.getInstance()),//自定义推荐商品
	            	  new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
	            	  new ImageInputProvider(RongContext.getInstance()),//图片
	            	  new CameraInputProvider(RongContext.getInstance()),//相机
	            	  new LocationInputProvider(RongContext.getInstance()),//地理位置
//	            	  new VoIPInputProvider(RongContext.getInstance())// 语音通话
	            	  
	            	};
	            	RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE, provider);
	            	
	            	//注册自定义消息
	            	RongIM.registerMessageType(CustomizeMessage.class);
	            	RongIM.registerMessageType(CustomizeOrderMessage.class);
	            	
	            	//注册自定义商品消息模板
	            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(getContext(),getActivity()));
	            	//注册自定义订单消息模板
	            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(getContext(),getActivity()));
	            	
	            	//重新注册该消息模板
//	            	RongIM.getInstance().registerMessageTemplate(new MyTextMessageItemProvider());
	            	//启动会话列表界面
//	            	if (RongIM.getInstance() != null)
//	            	                   RongIM.getInstance().startConversationList(getActivity());

	            	//启动聚合会话列表界面
//	            	if (RongIM.getInstance() != null)
//	            	                   RongIM.getInstance().startSubConversationList(getActivity(), ConversationType.GROUP);
	           
	            	// 1
	                if(RongIM.getInstance() != null)
	                RongIM.getInstance().setCurrentUserInfo(new UserInfo(mUserId.equals("10010") ? "10086" : "10010",mUserId.equals("10010") ? "我曾经是移动" : "我曾经是联通",
	                                mUserId.equals("10010") ? Uri.parse("http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg") : Uri.parse("http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"))
	                );
	                // 2
	                RongIM.getInstance().setMessageAttachedUserInfo(true);


	                //A、B互通消息时， 各端接收到消息后，SDK自动会从消息中取出用户信息放入用户信息缓存中，并刷新UI显示。
	            
	            }

	            *//**
	             * 连接融云失败
	             * @param errorCode 错误码，可到官网 查看错误码对应的注释
	             *//*
	            @Override
	            public void onError(RongIMClient.ErrorCode errorCode) {
	            	Toast.makeText(getContext(), "连接失败！", lin).show();
	            }
	        });
	    }
	}
});
	}
}
*/