package com.cesaas.android.counselor.order.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.fragment.HomeSampleFragment;
import com.cesaas.android.counselor.order.activity.user.MessageActivity;
import com.cesaas.android.counselor.order.activity.user.NoticeListActivity;
import com.cesaas.android.counselor.order.bean.ResultGetTokenBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.rong.activity.ConversationListActivity;
import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomReportPushMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeSalesMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeSalesTalkMessage;
import com.cesaas.android.counselor.order.rong.custom.FaceMessage;
import com.cesaas.android.counselor.order.rong.custom.FaceMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.SalesTalkProvider;
import com.cesaas.android.counselor.order.rong.message.MyConversationBehaviorListener;
import com.cesaas.android.counselor.order.runtimepermissions.PermissionUtils;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.NotificationUpdate;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class HomeActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvBaseTitle;
    private TextView tv_message,tv_session;
    private LinearLayout llBaseBack,ll_base_scan;

    private long exitTime = 0; // 退出点击间隔时间

    private GetTokenNet getTokenNet;
    private ResultGetTokenBean.GetTokenBean tokenBean;

    private BadgeView sessionBadgeView,msgBadgeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        getTokenNet=new GetTokenNet(mContext);
        getTokenNet.setData();

        checkAndRequestPermission();
        initNotificationUpdate();//

        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, new HomeSampleFragment()).commit();

    }

    private void initView() {
        sessionBadgeView=new BadgeView(this);
        msgBadgeView=new BadgeView(this);

        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("超级零售");
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setVisibility(View.GONE);
        ll_base_scan= (LinearLayout) findViewById(R.id.ll_base_scan);
        ll_base_scan.setVisibility(View.GONE);

        tv_message= (TextView) findViewById(R.id.tv_message);
        tv_message.setTypeface(App.font);
        tv_message.setText(R.string.system_msg);
        tv_message.setVisibility(View.VISIBLE);
        tv_message.setOnClickListener(this);
        msgBadgeView.setTargetView(tv_message);
//        msgBadgeView.setBadgeCount(2);
//        msgBadgeView.setBadgeGravity(Gravity.CENTER_VERTICAL);

        tv_session= (TextView) findViewById(R.id.tv_session);
        tv_session.setTypeface(App.font);
        tv_session.setText(R.string.msg);
        tv_session.setVisibility(View.VISIBLE);
        tv_session.setOnClickListener(this);
        sessionBadgeView.setTargetView(tv_session);

    }

    /**
     * 版本自动更新
     */
    public void initNotificationUpdate() {
        NotificationUpdate.mInstance(this).mCheckVersions(false);
    }


    /**
     * 检查权限【Android6.0动态申请运行时权限】
     */
    private void checkAndRequestPermission(){
        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
    }

    /**
     *  处理授权请求回调
     使用onRequestPermissionsResult(int ,String , int[])方法处理回调，
     上面说到了根据requestPermissions()方法中的requestCode，
     就可以在回调方法中区分授权请求
     */
    @SuppressLint("Override")
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    /**
     * 一次申请多个权限Android6.0
     */
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO://录制音频
                    Log.d(Constant.TAG, "Result Permission Grant CODE_RECORD_AUDIO");
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Log.d(Constant.TAG, "Result Permission Grant CODE_GET_ACCOUNTS");
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Log.d(Constant.TAG, "Result Permission Grant CODE_READ_PHONE_STATE");
                    break;
                case PermissionUtils.CODE_CALL_PHONE://电话
                    Log.d(Constant.TAG, "Result Permission Grant CODE_CALL_PHONE");
                    break;
                case PermissionUtils.CODE_CAMERA://相机
                    Log.d(Constant.TAG, "Result Permission Grant CODE_CAMERA");
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Log.d(Constant.TAG, "Result Permission Grant CODE_ACCESS_FINE_LOCATION");
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION://获取模拟定位信息
                    Log.d(Constant.TAG, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION");
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE://读
                    Log.d(Constant.TAG, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE");
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE://写
                    Log.d(Constant.TAG, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE");
                    break;
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    Log.d(Constant.TAG, "Result All Permission Grant");
                    break;
                default:
                    break;
            }
        }
    };


//
    public void onEventMainThread(ResultGetTokenBean msg) {
        if (msg.IsSuccess==true && msg.IsSuccess==true) {
            tokenBean = msg.TModel;
            prefs.putString("RongToken", tokenBean.token);
            connectRongCloud(tokenBean.token);
        }

    }

    /**
     * 链接融云
     */
    public void connectRongCloud(String token){
        if (mActivity.getApplicationInfo().packageName.equals(App.getCurProcessName(mContext))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.i("test", "Token已经过期！");
//                    Toast.makeText(mContext, "Token已经过期！", Toast.LENGTH_SHORT).show();
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @SuppressWarnings("static-access")
                @Override
                public void onSuccess(String userid) {
                    Log.i("test", "连接融云成功！"+userid);

                    //扩展功能自定义
                    InputProvider.ExtendProvider[] provider = {
                            new SalesTalkProvider(RongContext.getInstance(),mActivity),//自定义会话话术
                            new ContactsProvider(RongContext.getInstance()),//自定义推荐商品
                            new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
//		            	  new ImageInputProvider(RongContext.getInstance()),//图片
                            new CameraInputProvider(RongContext.getInstance()),//相机
//		            	  new LocationInputProvider(RongContext.getInstance()),//地理位置

                    };
                    RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);

                    //注册自定义消息
                    RongIM.registerMessageType(CustomizeMessage.class);
                    RongIM.registerMessageType(CustomizeOrderMessage.class);
                    RongIM.registerMessageType(CustomizeSalesTalkMessage.class);
                    RongIM.registerMessageType(FaceMessage.class);

                    //注册自定义人脸识别消息模板
                    RongIM.getInstance().registerMessageTemplate(new FaceMessageItemProvider(mContext,mActivity));
                    //注册自定义商品消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(mContext,mActivity));
                    //注册自定义订单消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(mContext,mActivity));
                    //注册自定义话术消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomizeSalesMessageItemProvider(mContext,mActivity));
                    //注册自定义报表消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomReportPushMessageItemProvider(mActivity));
//

                    //设置接收消息的监听器。
                    RongIM.setOnReceiveMessageListener(new HomeActivity.MyReceiveMessageListener());
                    //设置会话界面操作的监听器。
                    RongIM.setConversationBehaviorListener(new MyConversationBehaviorListener());
                    //接收所有未读消息消息的监听器。
                    RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new HomeActivity.MyReceiveUnreadCountChangedListener());
                    // 接收指定会话类型的未读消息数。
                    Conversation.ConversationType conversationType[]={
                            Conversation.ConversationType.PRIVATE,
                            Conversation.ConversationType.GROUP,
                            Conversation.ConversationType.DISCUSSION,
                            Conversation.ConversationType.SYSTEM,
                            Conversation.ConversationType.PUSH_SERVICE,
                            Conversation.ConversationType.NONE,
                            Conversation.ConversationType.CUSTOMER_SERVICE,
                            Conversation.ConversationType.APP_PUBLIC_SERVICE,
                            Conversation.ConversationType.CHATROOM
                    };

                    RongIM.getInstance().setOnReceiveUnreadCountChangedListener(
                            new HomeActivity.MyReceiveUnreadCountChangedListener(), conversationType);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.i("test","融云连接失败----"+errorCode);
                }
            });
        }
    }

    /**
     * 接收未读消息的监听器。
     * @author FGB
     */
    public class MyReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

        /**
         *  @param count 未读消息数。
         */
        @Override
        public void onMessageIncreased(int count) {
            Log.i("test", "未读消息数"+count);
            if(count!=0){
                //设置未读消息数
                sessionBadgeView.setBadgeCount(count);
                sessionBadgeView.setBadgeGravity(Gravity.HORIZONTAL_GRAVITY_MASK);
            }
        }
    }

    /**
     * 接受消息监听
     * @author fgb
     *
     */
    public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param i    剩余未拉取消息数目。
         * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
         */
        @Override
        public boolean onReceived(Message message, int i) {
            if(message!=null){
                try {
                    Log.i("test", "收到消息"+message.getContent()
                            +"发送人Id：="+message.getTargetId()
                            +"ObjectName=="+message.getObjectName()
                            +"=="+message.getMessageDirection()
//                            +"=="+message.getContent().getJSONUserInfo().getString("Name")
//                            +"=="+message.getContent().getJSONUserInfo().get("Name")
//                            +"=="+message.getContent().getJSONUserInfo().getJSONObject("Name")
//                            +"=="+message.getContent().getJSONUserInfo().getString("VipId")
//                            +"=="+message.getContent().getJSONUserInfo().get("VipId")
//                            +"=="+message.getContent().getJSONUserInfo().getJSONObject("VipId")
//                            +"=="+message.getContent().getJSONUserInfo().getString("Sex")
//                            +"=="+message.getContent().getJSONUserInfo().get("Sex")
//                            +"=="+message.getContent().getJSONUserInfo().getJSONObject("Sex")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
//						ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
            }
            return false;
        }
    }

    /**
     * 退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                    //断开融云服务
                    RongIM.getInstance().disconnect();
                } else {
                    for (int i = 0; i < HomeActivity.activityList.size(); i++) {
                        if (null != HomeActivity.activityList.get(i)) {
                            Skip.mBack(HomeActivity.activityList.get(i));
                        }
                    }
                    Skip.mBack(this);
                }
                return true;
            } catch (Exception e) {
                Skip.mBack(this);
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_message://消息页面
                Skip.mNext(mActivity,MessageActivity.class);
                break;
            case R.id.tv_session://跳转到会话列表
//                Skip.mNext(mActivity,VipActivity.class);
                Skip.mNext(mActivity,ConversationListActivity.class);
                break;
        }
    }


    /**
     * 获取融云Token
     * @author FGB
     *
     */
    public class GetTokenNet extends BaseNet {

        public GetTokenNet(Context context) {
            super(context, true);
            this.uri="User/Sw/User/GetToken";
        }

        public void setData() {
            try {
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Gson gson = new Gson();
            Log.i("test","rong:"+rJson);
            ResultGetTokenBean msg = gson.fromJson(rJson, ResultGetTokenBean.class);
            if(msg.IsSuccess==true){
                connectRongCloud(msg.TModel.token);
                prefs.putString("RongToken", msg.TModel.token+"");

            }else{
                ToastFactory.getLongToast(mContext,"获取融云ToKen失败！");
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
            Log.i(Constant.TAG, "GetToken"+e+"==err==="+err);
        }

    }
}
