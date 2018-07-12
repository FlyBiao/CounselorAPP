package com.cesaas.android.counselor.order.view;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation.ConversationType;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.activity.ScanSendActivity;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.adapter.ShopOrderThingsAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetShopOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetShopOrderBean.GetShopOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetShopOrderBean.GetShopOrderItemBean;
import com.cesaas.android.counselor.order.express.view.ExpressListActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.GetShopOrderNet;
import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.haozhang.lib.SlantedTextView;

/**
 * 店铺订单=未发货
 * @author FGB
 *
 */
public class WaitOutShopOrderFragment  extends BaseFragment{
	private SlantedTextView stv;
	
	private GetShopOrderNet orderNet;
	 private ArrayList<GetShopOrderBean> shopOrderLis= new ArrayList<GetShopOrderBean>();
	 private CommonAdapter<GetShopOrderBean> adapter;
	 private List<GetShopOrderItemBean> list;
		
		private static int pageIndex = 1;
		private boolean isHaveMoreData = false;
		private LoadMoreListView mLoadMoreListView;
		private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
		private static WaitOutShopOrderFragment fragment;
		private boolean refresh=false;
		
		private ListView lv;

		public static Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				
				pageIndex=1;
				fragment.loadData(pageIndex);
			};
		};
		
	 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			fragment=this;
		}
		
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
							shopOrderLis.clear();
							adapter.notifyDataSetChanged();
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
		
		public void onEventMainThread(ResultGetShopOrderBean msg) {
			if (msg != null) {
				if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
					mLoadMoreListView.setHaveMoreData(true);
				} else {
					mLoadMoreListView.setHaveMoreData(false);
				}
				if(msg.TModel.size()!=0){
					shopOrderLis.addAll(msg.TModel);
					adapter.notifyDataSetChanged();
					/**
					 * 按照时间排序显示
					 */
//					Collections.sort(shopOrderLis, new Comparator<GetShopOrderBean>() {
//						@Override
//						public int compare(GetShopOrderBean lhs, GetShopOrderBean rhs) {
//							
//							Date date0=null;
//							Date date1=null;
//							for (int i = zero; i < shopOrderLis.size(); i++) {
//								date0=AbDateUtil.stringToDate(shopOrderLis.get(i).CreateDate);
//								date1=AbDateUtil.stringToDate(shopOrderLis.get(i).CreateDate);
//							}
//							
//							// 对日期字段进行升序，如果欲降序可采用after方法  
//			                if (date0.after(date1)) {  
//			                	
//			                    return 1;  
//			                }  
//			                
//			                return -1;
//						}
//					});
				}
				mRefreshAndLoadMoreView.setRefreshing(false);
				mLoadMoreListView.onLoadComplete();
				// 当加载完成之后设置此时不在刷新状态
			}
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_all_order_state_view, container, false);
			mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_allOrderState_list);
			mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_allOrderState_and_load_more);


			initListener();
			return view;
		}
		
		/**
		 * 订单详情
		 */
		public void initListener() {
			mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Bundle bundle = new Bundle();
					bundle.putString("TradeId", shopOrderLis.get(position).TradeId);
					Skip.mNextFroData(getActivity(), OrderDetailActivity.class, bundle);
				}
			});
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			//获取Adapter 设置数据
			adapter=new CommonAdapter<GetShopOrderBean>(getContext(),R.layout.item_all_order_state,shopOrderLis) {
				
				@Override
				public void convert(ViewHolder holder, final GetShopOrderBean bean,final int postion) {
					
					lv=holder.getView(R.id.list_receive_order_things);
					holder.setText(R.id.tv_order_createTime, "下单时间:" +bean.CreateDate);
					
					list = new ArrayList<GetShopOrderItemBean>();
					
					for (int i = 0; i < bean.OrderItem.size(); i++) {
						holder.setText(R.id.tv_order_id, "订单号:" + bean.TradeId);
						GetShopOrderItemBean itemBean=new ResultGetShopOrderBean().new GetShopOrderItemBean();
						itemBean=bean.OrderItem.get(i);
						list.add(itemBean);
					}
					
					ShopOrderThingsAdapter adapter=new ShopOrderThingsAdapter(mContext, list);
					int totalHeight = 0;
					for (int i = 0; i < adapter.getCount(); i++) {
						View listItem = adapter.getView(i, null, lv);
						listItem.measure(0, 0);
						totalHeight += listItem.getMeasuredHeight();
					}

					ViewGroup.LayoutParams params = lv.getLayoutParams();
					params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
					((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
					lv.setLayoutParams(params);
					
					if(bean.ExpressType==0){//物流发货
						holder.setText(R.id.tv_my_receive_user, bean.NickName+"("+bean.Mobile+")");
						holder.getView(R.id.tv_order_session1).setVisibility(View.VISIBLE);
						holder.getView(R.id.tv_order_session).setVisibility(View.GONE);
						holder.getView(R.id.slv_right_tri_express).setVisibility(View.VISIBLE);

						
						//物流订单发货点击事件监听
						holder.getView(R.id.tv_express_send).setVisibility(View.VISIBLE);
						holder.getView(R.id.tv_scan_send).setVisibility(View.GONE);
						holder.setOnClickListener(R.id.tv_express_send, new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Bundle bundle = new Bundle();
								bundle.putString("TradeId", bean.TradeId);
								Skip.mNextFroData(getActivity(), ExpressListActivity.class, bundle);
							}
						});
						
					}else if(bean.ExpressType==1){//到店自提
						holder.setText(R.id.tv_my_receive_user, bean.NickName);
						holder.getView(R.id.tv_order_session1).setVisibility(View.GONE);
						holder.getView(R.id.tv_order_session).setVisibility(View.VISIBLE);
						holder.getView(R.id.slv_right_tri_self_lift).setVisibility(View.VISIBLE);

						//扫描发货点击事件监听
						holder.getView(R.id.tv_express_send).setVisibility(View.GONE);
						holder.getView(R.id.tv_scan_send).setVisibility(View.VISIBLE);
						holder.setOnClickListener(R.id.tv_scan_send, new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Skip.mNext(getActivity(), ScanSendActivity.class);
							}
						});
					}
					
					/**
					 * 查看订单详情
					 */
					lv.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

							Bundle bundle = new Bundle();
							bundle.putString("Title", list.get(i).Title);
							bundle.putString("ImageUrl", list.get(i).ImageUrl);
							bundle.putString("StyleCode", list.get(i).StyleCode);
							bundle.putString("BarcodeCode", list.get(i).BarcodeCode);
							bundle.putDouble("Price", list.get(i).Price);
							bundle.putString("Attr", list.get(i).Attr);
							
							Skip.mNextFroData(getActivity(), ShopDetailActivity.class, bundle);
						}
					});
					
					lv.setAdapter(adapter);
					
					//会话
					holder.setOnClickListener(R.id.tv_order_session, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if (getContext().getApplicationInfo().packageName.equals(App.getCurProcessName(getContext()))) {abpUtil = AbPrefsUtil.getInstance();
							/**
							 * IMKit SDK调用第二步,建立与服务器的连接
							 */
							RongIM.connect(abpUtil.getString("RongToken"),new RongIMClient.ConnectCallback() {

										/**
										 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App
										 * Server 重新请求一个新的 Token
										 */
										@Override
										public void onTokenIncorrect() {
											Toast.makeText(getContext(), "onTokenIncorrect", 0).show();
										}

										/**
										 * 连接融云成功
										 * 
										 * @param userid
										 *            当前 token
										 */
										@SuppressWarnings("static-access")
										@Override
										public void onSuccess(String userid) {

											// 启动单聊会话界面
											if (RongIM.getInstance() != null)
												RongIM.getInstance().startPrivateChat(getContext(), bean.VipId, bean.CounselorName);

											// 设置自己发出的消息监听器
											if (RongIM.getInstance() != null) {
												RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
											}

											// 设置接收消息的监听器。
											RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());

											// 扩展功能自定义
											InputProvider.ExtendProvider[] provider = {
													new ContactsProvider(RongContext.getInstance()),// 自定义推荐订单
													new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
													new ImageInputProvider(RongContext.getInstance()),// 图片
													new CameraInputProvider(RongContext.getInstance()),// 相机
													new LocationInputProvider(RongContext.getInstance()),// 地理位置

											};
											RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE,provider);

											// 注册自定义消息
											RongIM.registerMessageType(CustomizeMessage.class);
											RongIM.registerMessageType(CustomizeOrderMessage.class);
											
											// 注册消息模板
											RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(getContext(), getActivity()));
											//注册自定义订单消息模板
							            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(getContext(), getActivity()));
							            	
										}

										/**
										 * 连接融云失败
										 * 
										 * @param errorCode
										 *            错误码，可到官网 查看错误码对应的注释
										 */
										@Override
										public void onError(RongIMClient.ErrorCode errorCode) {
											Toast.makeText(getContext(), "连接失败", 0).show();
										}
									});
						}
							
						}
					});
					
					holder.setOnClickListener(R.id.tv_order_session1, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							if (getContext().getApplicationInfo().packageName.equals(App.getCurProcessName(getContext()))) {abpUtil = AbPrefsUtil.getInstance();
							/**
							 * IMKit SDK调用第二步,建立与服务器的连接
							 */
							RongIM.connect(abpUtil.getString("RongToken"),new RongIMClient.ConnectCallback() {

										/**
										 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App
										 * Server 重新请求一个新的 Token
										 */
										@Override
										public void onTokenIncorrect() {
											Toast.makeText(getContext(), "onTokenIncorrect", 0).show();
										}

										/**
										 * 连接融云成功
										 * 
										 * @param userid
										 *            当前 token
										 */
										@SuppressWarnings("static-access")
										@Override
										public void onSuccess(String userid) {

											// 启动单聊会话界面
											if (RongIM.getInstance() != null)
												RongIM.getInstance().startPrivateChat(getContext(), bean.VipId, bean.CounselorName);

											// 设置自己发出的消息监听器
											if (RongIM.getInstance() != null) {
												RongIM.getInstance().setSendMessageListener(new MySendMessageListener());
											}

											// 设置接收消息的监听器。
											RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());

											// 扩展功能自定义
											InputProvider.ExtendProvider[] provider = {
													new ContactsProvider(RongContext.getInstance()),// 自定义推荐订单
													new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
													new ImageInputProvider(RongContext.getInstance()),// 图片
													new CameraInputProvider(RongContext.getInstance()),// 相机
													new LocationInputProvider(RongContext.getInstance()),// 地理位置

											};
											RongIM.getInstance().resetInputExtensionProvider(ConversationType.PRIVATE,provider);

											// 注册自定义消息
											RongIM.registerMessageType(CustomizeMessage.class);
											RongIM.registerMessageType(CustomizeOrderMessage.class);
											
											// 注册消息模板
											RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(getContext(), getActivity()));
											//注册自定义订单消息模板
							            	RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(getContext(), getActivity()));
							            	
										}

										/**
										 * 连接融云失败
										 * 
										 * @param errorCode
										 *            错误码，可到官网 查看错误码对应的注释
										 */
										@Override
										public void onError(RongIMClient.ErrorCode errorCode) {
											Toast.makeText(getContext(), "连接失败", 0).show();
										}
									});
						}
							
						}
					});
				}
			};
			mLoadMoreListView.setAdapter(adapter);
			
			initData();
		}
		
		protected void loadData(final int page) {
			// TODO Auto-generated method stub
			if (page == 1) {
				shopOrderLis.clear();
			}
			
			orderNet=new GetShopOrderNet(getActivity());
			orderNet.setData(page,30);

			pageIndex = page;
		}

	 
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
