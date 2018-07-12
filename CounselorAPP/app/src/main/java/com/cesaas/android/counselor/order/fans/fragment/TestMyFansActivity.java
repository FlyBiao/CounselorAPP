package com.cesaas.android.counselor.order.fans.fragment;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.FansNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeSalesMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeSalesTalkMessage;
import com.cesaas.android.counselor.order.rong.custom.SalesTalkProvider;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的粉丝
 * @author FGB
 *
 */
@ContentView(R.layout.activity_fans_service_layout)
public class TestMyFansActivity extends BasesActivity implements RongIM.UserInfoProvider{
	
	@ViewInject(R.id.load_more_fans_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_fans_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	@ViewInject(R.id.ll_session_list_back)
	private LinearLayout ll_session_list_back;
	
	public String getMessageTargetId;
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private static TestMyFansActivity fragment;
	
	private FansNet fansNet;
	private UserInfoNet userInfoNet;
	private UserBean userBean;
	private ArrayList<Fans> fansLis= new ArrayList<Fans>();
	
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			pageIndex=1;
			fragment.loadData(pageIndex);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		fragment=this;
		userInfoNet=new UserInfoNet(mContext);
		userInfoNet.setData();
		
		setAdapter();
		mBack();
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		loadData(1);
		
		mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
		mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
		// 设置下拉刷新监听
		mRefreshAndLoadMoreView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refresh=true;
						pageIndex = 1;
						loadData(pageIndex);
					}
				});
		// 设置加载监听
		mLoadMoreListView
				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
					@Override
					public void onLoadMore() {
						refresh=false;
						loadData(pageIndex + 1);
					}
				});
	}
	
	public void onEventMainThread(ResultFans msg) {
		
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				fansLis.addAll(msg.TModel);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	public void onEventMainThread(ResultUserBean msg) {
    		userBean=msg.TModel;
    }
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<Fans>(mContext,R.layout.item_user_fans,fansLis) {

			@Override
			public void convert(ViewHolder holder, Fans fans,int postion) {
				holder.setText(R.id.tv_fans_nick, fans.FANS_NICKNAME);
				holder.setText(R.id.tv_fans_mobile, fans.FANS_MOBILE);
				holder.setCircleImageViewBitmap(R.id.iv_fans_img, bitmapUtils, fans.FANS_ICON);
			}

		});
		
		initData();
		initItemClickListener();
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			fansLis.clear();
		}
		fansNet = new FansNet(mContext);
		fansNet.setData(page);

		pageIndex = page;
	}
	

	/**
	 * 初始化ListView Item监听
	 */
	public void initItemClickListener(){
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				if (mActivity.getApplicationInfo().packageName.equals(App.getCurProcessName(mContext))) {

			        /**
			         * IMKit SDK调用第二步,建立与服务器的连接
			         */
			        RongIM.connect(prefs.getString("RongToken"), new RongIMClient.ConnectCallback() {

						/**
			             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
			             */
			            @Override
			            public void onTokenIncorrect() {
			            	Toast.makeText(mContext, "Token已经过期！", 0).show();
			            }

			            /**
			             * 连接融云成功
			             * @param userid 当前 token
			             */
			            @SuppressWarnings("static-access")
						@Override
			            public void onSuccess(String userid) {
			            	
			            	//启动单聊会话界面
			            	if (RongIM.getInstance() != null)
			            	   RongIM.getInstance().startPrivateChat(mActivity, 
			            			   fansLis.get(position).FANS_ID, fansLis.get(position).FANS_NICKNAME);
			            	
			            	//设置自己发出的消息监听器
			            	if (RongIM.getInstance() != null) {
			            		  RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
			            		}
			            	
			            	//设置接收消息的监听器。
			            	RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());
			            	//扩展功能自定义
			            	InputProvider.ExtendProvider[] provider = {
			            	  new SalesTalkProvider(RongContext.getInstance(),mActivity),//自定义会话话术
			            	  new ContactsProvider(RongContext.getInstance()),//自定义推荐商品
			            	  new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
			            	  new ImageInputProvider(RongContext.getInstance()),//图片
			            	  new CameraInputProvider(RongContext.getInstance()),//相机
			            	  new LocationInputProvider(RongContext.getInstance()),//地理位置
			            	  
			            	};
			            	RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE, provider);
			            	
//			            	//注册自定义消息
			            	RongIM.registerMessageType(CustomizeMessage.class);
			            	RongIM.registerMessageType(CustomizeOrderMessage.class);
			            	RongIM.registerMessageType(CustomizeSalesTalkMessage.class);
			            	
			            	//注册自定义商品消息模板
			            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(mContext,mActivity));
			            	//注册自定义订单消息模板
			            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(mContext,mActivity));
			            	//注册自定义话术消息模板
			            	RongIM.getInstance().registerMessageTemplate(new CustomizeSalesMessageItemProvider(mContext,mActivity));
//			            	
			            	RongIM.getInstance().refreshUserInfoCache(new UserInfo(userBean.VipId+"","",Uri.parse(userBean.Icon)));
			            	RongIM.getInstance().setMessageAttachedUserInfo(true);
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
		});
	}
	
	public void mBack(){
		ll_session_list_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}

	@Override
	public UserInfo getUserInfo(String s) {
		for(Fans f:fansLis){
			if(f.equals(s)){
				return new UserInfo(f.FANS_ID,f.FANS_NICKNAME, Uri.parse(f.FANS_ICON));
			}
		}
		return null;
	}
}
