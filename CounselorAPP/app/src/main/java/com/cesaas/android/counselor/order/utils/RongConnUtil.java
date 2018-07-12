package com.cesaas.android.counselor.order.utils;

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

import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;

public class RongConnUtil {

	Activity mActivity;
	Context mContext;
	String fansID;
	String fansNick;
	String fansIcon;
	String vipId;
	String userIcon;
	String rongToken;
	
	public RongConnUtil(Activity mActivity,Context mContext,String fansID,String fansNick,String fansIcon,String vipId,String userIcon,String rongToken){
		this.mActivity=mActivity;
		this.mContext=mContext;
		this.fansID=fansID;
		this.fansNick=fansNick;
		this.fansIcon=fansIcon;
		this.vipId=vipId;
		this.userIcon=fansIcon;
		this.rongToken=rongToken;
	}
	
	/**
	 * 聊天
	 */
	public void FansConverSation(){
				if (mActivity.getApplicationInfo().packageName.equals(App.getCurProcessName(mContext))) {

			        /**
			         * IMKit SDK调用第二步,建立与服务器的连接
			         */
			        RongIM.connect(rongToken, new RongIMClient.ConnectCallback() {

						/**
			             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
			             */
			            @Override
			            public void onTokenIncorrect() {
			            	Toast.makeText(mContext, "获取Token错误！", 0).show();
			            }

			            /**
			             * 连接融云成功
			             * @param userid 当前 token
			             */
			            @Override
			            public void onSuccess(String userid) {
			            	//启动单聊会话界面
			            	if (RongIM.getInstance() != null)
			            	   RongIM.getInstance().startPrivateChat(mActivity, 
			            			   fansID, fansNick);
			            	
			            	//设置自己发出的消息监听器
			            	if (RongIM.getInstance() != null) {
			            		  RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
			            		}
			            	
			            	//设置接收消息的监听器。
			            	RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
			            	/**
			            	* 设置接收 push 消息的监听器。
			            	*/
			            	//RongIM.setOnReceivePushMessageListener(new MyReceivePushMessageListener());
			            	
			            	//扩展功能自定义
			            	InputProvider.ExtendProvider[] provider = {
			            	  new ContactsProvider(RongContext.getInstance()),//自定义推荐商品
			            	  new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
			            	  new ImageInputProvider(RongContext.getInstance()),//图片
			            	  new CameraInputProvider(RongContext.getInstance()),//相机
			            	  new LocationInputProvider(RongContext.getInstance()),//地理位置
			            	  
			            	};
			            	RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE, provider);
			            	
			            	//注册自定义消息
			            	RongIM.registerMessageType(CustomizeMessage.class);
			            	RongIM.registerMessageType(CustomizeOrderMessage.class);
			            	
			            	//注册自定义商品消息模板
			            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(mContext,mActivity));
			            	//注册自定义订单消息模板
			            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(mContext,mActivity));
			            	
			            	RongIM.getInstance().refreshUserInfoCache(new UserInfo(fansID,"",Uri.parse(fansIcon)));
			            	RongIM.getInstance().refreshUserInfoCache(new UserInfo(vipId,"",Uri.parse(userIcon)));
			            }

			            /**
			             * 连接融云失败
			             * @param errorCode 错误码，可到官网 查看错误码对应的注释
			             */
			            @Override
			            public void onError(RongIMClient.ErrorCode errorCode) {
			            	Toast.makeText(mContext, "连接失败！", 0).show();
			            }
			        });
			    }
	}
}
