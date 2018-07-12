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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import android.widget.ListView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ExpressActivity;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.activity.ScanSendActivity;
import com.cesaas.android.counselor.order.adapter.MyReceiveOrderThingsAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.GetByCounselorNet;
import com.cesaas.android.counselor.order.rong.custom.ContactsOrderProvider;
import com.cesaas.android.counselor.order.rong.custom.ContactsProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.message.MyReceiveMessageListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 所有订单分销状态
 * @author FGB
 *
 */
public class AllFansOrderStateView extends BaseFragment{
	
	 private GetByCounselorNet byCounselorNet;
	 private ArrayList<GetByCounselorBean> orderLis= new ArrayList<GetByCounselorBean>();
		
		private static int pageIndex = 1;
		private LoadMoreListView mLoadMoreListView;
		private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
		private static AllFansOrderStateView fragment;
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
		
		public void onEventMainThread(ResultGetByCounselorBean msg) {
			
			if (msg != null) {
				if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
					mLoadMoreListView.setHaveMoreData(true);
				} else {
					mLoadMoreListView.setHaveMoreData(false);
					
				}
				if(msg.TModel.size()!=0){
					orderLis.addAll(msg.TModel);
					/**
					 * 按照时间排序显示
					 */
					Collections.sort(orderLis, new Comparator<GetByCounselorBean>() {
						@Override
						public int compare(GetByCounselorBean lhs, GetByCounselorBean rhs) {
							
							Date date0=null;
							Date date1=null;
							for (int i = 0; i < orderLis.size(); i++) {
								date0=AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
								date1=AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
							}
							
							// 对日期字段进行升序，如果欲降序可采用after方法  
			                if (date0.after(date1)) {  
			                	
			                    return 1;  
			                }  
			                
			                return -1;
						}
					});
				}
				mRefreshAndLoadMoreView.setRefreshing(false);
				mLoadMoreListView.onLoadComplete();
				// 当加载完成之后设置此时不在刷新状态

			}
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_all_fans_order_state_view, container, false);
			mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_allFansState_list);

			mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_allFansState_and_load_more);
			
			return view;
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			//获取Adapter 设置数据
			mLoadMoreListView.setAdapter(new CommonAdapter<GetByCounselorBean>(getContext(),R.layout.item_all_order_state,orderLis) {
				
				@Override
				public void convert(ViewHolder holder, final GetByCounselorBean bean,final int postion) {
					
					lv=holder.getView(R.id.list_receive_order_things);
					holder.setText(R.id.tv_my_receive_user, bean.NickName+"("+bean.Mobile+")");
					holder.setText(R.id.tv_order_createTime, "下单时间:" +bean.CreateDate);
					
					List<GetByCounselorBeanItemBean> list = new ArrayList<GetByCounselorBeanItemBean>();
					
					for (int i = 0; i < bean.OrderItem.size(); i++) {
						holder.setText(R.id.tv_order_id, "订单号:" + bean.TradeId);
						GetByCounselorBeanItemBean itemBean=new ResultGetByCounselorBean().new GetByCounselorBeanItemBean();
						itemBean=bean.OrderItem.get(i);
						list.add(itemBean);
					}
					
					MyReceiveOrderThingsAdapter adapter=new MyReceiveOrderThingsAdapter(mContext, list);
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
					
					if (bean.OrderStatus == 10) {// 未支付
						holder.setText(R.id.tv_orderState,"未支付");

					} 
					else if(bean.OrderStatus == 11){//待审核
						holder.setText(R.id.tv_orderState,"待审核");
					}
					else if (bean.OrderStatus == 20) {// 支付中
						holder.setText(R.id.tv_orderState,"支付中");

					} else if (bean.OrderStatus == 30) {// 未发货
						holder.setText(R.id.tv_orderState,"用户已支付");
						
						if(bean.ExpressType==0){//物流发货
							holder.getView(R.id.tv_express_send).setVisibility(View.VISIBLE);
							holder.getView(R.id.tv_scan_send).setVisibility(View.GONE);
							holder.setOnClickListener(R.id.tv_express_send, new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Bundle bundle = new Bundle();
									bundle.putString("expressOrderId", bean.TradeId);
									Skip.mNextFroData(getActivity(), ExpressActivity.class,bundle);
								}
						});
					}else if(bean.ExpressType==1){//到店自提扫描发货
						holder.getView(R.id.tv_scan_send).setVisibility(View.VISIBLE);
						holder.getView(R.id.tv_express_send).setVisibility(View.GONE);
						holder.setOnClickListener(R.id.tv_scan_send, new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Skip.mNext(getActivity(), ScanSendActivity.class);
							}
						});
					}
						
					} else if (bean.OrderStatus == 40) {// 已发货
						holder.setText(R.id.tv_orderState,"已发货物流订单");
						holder.getView(R.id.tv_express_send).setVisibility(View.GONE);

					} 
					else if (bean.OrderStatus == 80) {// 已退款
						holder.setText(R.id.tv_orderState,"已退款");
					}
					else if(bean.OrderStatus == 81){//取消订单
						holder.setText(R.id.tv_orderState,"订单已取消");
					}
					else if (bean.OrderStatus == 100) {// 已退款
						holder.setText(R.id.tv_orderState,"订单完成");
						holder.getView(R.id.tv_order_session).setVisibility(View.GONE);
					}
					
					/**
					 * 查看订单详情
					 */
					lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

							Bundle bundle = new Bundle();
							bundle.putString("TradeId", orderLis.get(postion).OrderItem.get(i).OrderId);
							Skip.mNextFroData(getActivity(), OrderDetailActivity.class, bundle);
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
				}
			});
			
			initData();
		}
		
		protected void loadData(final int page) {
			// TODO Auto-generated method stub

			if (page == 1) {
				orderLis.clear();
			}
			byCounselorNet=new GetByCounselorNet(getActivity());
			byCounselorNet.setData(0,page);

			pageIndex = page;

		}

		
	 
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
