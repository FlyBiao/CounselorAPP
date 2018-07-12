package com.cesaas.android.counselor.order.view;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.activity.order.bean.ResultNotPayCounselorOrderBean;
import com.cesaas.android.counselor.order.activity.order.net.NotPayCounselorOrderNet;
import com.cesaas.android.counselor.order.adapter.MyReceiveOrderThingsAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.net.GetByCounselorNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

import static com.cesaas.android.counselor.order.R.mipmap.zero;

/**
 * 未付款订单分销状态
 * @author FGB
 *
 */
public class NoPayOrderStateView  extends BaseFragment{
	
//	 private GetByCounselorNet byCounselorNet;
	private NotPayCounselorOrderNet byCounselorNet;
	 private ArrayList<GetByCounselorBean> orderLis= new ArrayList<GetByCounselorBean>();
	 private CommonAdapter<GetByCounselorBean> adapter;
	 private List<GetByCounselorBeanItemBean> list;
		
		private static int pageIndex = 1;
		private boolean isHaveMoreData = false;
		private LoadMoreListView mLoadMoreListView;
		private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
		private static NoPayOrderStateView fragment;
		private boolean refresh=false;
		
		private ListView lv;
		
		/**
		 * 单例
		 */
		public static NoPayOrderStateView instance=null;
		public static NoPayOrderStateView getInstance(){
			if(instance==null){
				instance=new NoPayOrderStateView();
			}
			return instance;
		}

		public static Handler handler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				
				pageIndex=1;
				fragment.loadData(pageIndex);
			}
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
							orderLis.clear();
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
		
		public void onEventMainThread(ResultNotPayCounselorOrderBean msg) {
			if (msg != null) {
				if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
					mLoadMoreListView.setHaveMoreData(true);
				} else {
					mLoadMoreListView.setHaveMoreData(false);
				}
				if(msg.TModel.size()!=0){
					orderLis.addAll(msg.TModel);
					adapter.notifyDataSetChanged();
					/**
					 * 按照时间排序显示
					 */
					Collections.sort(orderLis, new Comparator<GetByCounselorBean>() {
						@Override
						public int compare(GetByCounselorBean lhs, GetByCounselorBean rhs) {

							Date date0=null;
							Date date1=null;
							for (int i = 0; i < orderLis.size(); i++) {
								date0= AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
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
			View view = inflater.inflate(R.layout.activity_all_order_state_view, container, false);
			mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_allOrderState_list);
			mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_allOrderState_and_load_more);

			initListener();
			return view;
		}

	private void initAdaper() {

		//获取Adapter 设置数据
		adapter=new CommonAdapter<GetByCounselorBean>(getContext(),R.layout.item_all_order_state,orderLis) {

			@Override
			public void convert(ViewHolder holder, final GetByCounselorBean bean,final int postion) {

				lv=holder.getView(R.id.list_receive_order_things);
				holder.setText(R.id.tv_order_createTime,bean.CreateDate);
				holder.setText(R.id.tv_shop_name, bean.CounselorName);

//					//判断是否显示订单所属店员
//					if(abpUtil.getString("TypeId").equals("1")){//店长
//						holder.getView(R.id.view_shop_assistant).setVisibility(View.VISIBLE);
//						holder.getView(R.id.ll_shop_assistant).setVisibility(View.VISIBLE);
//					}else{//店员
//						holder.getView(R.id.view_shop_assistant).setVisibility(View.GONE);
//						holder.getView(R.id.ll_shop_assistant).setVisibility(View.GONE);
//					}

				list = new ArrayList<GetByCounselorBeanItemBean>();

				for (int i = 0; i < bean.OrderItem.size(); i++) {
					holder.setText(R.id.tv_order_id, bean.TradeId);
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

				if(bean.ExpressType==0){//物流发货
					holder.setText(R.id.tv_my_receive_user, bean.NickName+"("+bean.Mobile+")");
					holder.getView(R.id.tv_order_session1).setVisibility(View.VISIBLE);
					holder.getView(R.id.tv_order_session).setVisibility(View.GONE);
					holder.getView(R.id.slv_right_tri_express).setVisibility(View.VISIBLE);

				}else if(bean.ExpressType==1){//到店自提扫描发货
					holder.setText(R.id.tv_my_receive_user, bean.NickName);
					holder.getView(R.id.tv_order_session1).setVisibility(View.GONE);
					holder.getView(R.id.tv_order_session).setVisibility(View.VISIBLE);
					holder.getView(R.id.slv_right_tri_self_lift).setVisibility(View.VISIBLE);

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

						// 启动单聊会话界面
						if (RongIM.getInstance() != null)
							RongIM.getInstance().startPrivateChat(getContext(), bean.VipId, bean.CounselorName);
					}
				});

				holder.setOnClickListener(R.id.tv_order_session1, new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 启动单聊会话界面
						if (RongIM.getInstance() != null)
							RongIM.getInstance().startPrivateChat(getContext(), bean.VipId, bean.CounselorName);
					}
				});
			}
		};
		mLoadMoreListView.setAdapter(adapter);

		initData();
		Log.i("TestLog","微支付");
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
					bundle.putString("TradeId", orderLis.get(position).TradeId);
					Skip.mNextFroData(getActivity(), OrderDetailActivity.class, bundle);
				}
			});
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			initAdaper();
		}
		
		protected void loadData(final int page) {
			// TODO Auto-generated method stub
			if (page == 1) {
				orderLis.clear();
			}
			
			byCounselorNet=new NotPayCounselorOrderNet(getActivity());
			byCounselorNet.setData(1,10,page);

			pageIndex = page;
		}

	 
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
