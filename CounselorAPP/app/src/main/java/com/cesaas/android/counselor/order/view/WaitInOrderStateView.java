package com.cesaas.android.counselor.order.view;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Context;
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
import com.cesaas.android.counselor.order.activity.order.bean.ResultWaitInCounselorOrderBean;
import com.cesaas.android.counselor.order.activity.order.net.WaitInCounselorOrderNet;
import com.cesaas.android.counselor.order.adapter.AllOrderStateAdapter;
import com.cesaas.android.counselor.order.adapter.MyReceiveOrderThingsAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.express.view.ExpressQueryActivity;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.GetByCounselorNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.view.adapter.OrderStateViewAdapter;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import static com.cesaas.android.counselor.order.R.mipmap.zero;

/**
 * 已发货订单分销状态
 * @author FGB
 *
 */
public class WaitInOrderStateView extends BaseFragment{
	
	 private WaitInCounselorOrderNet byCounselorNet;
	 private ArrayList<GetByCounselorBean> orderLis= new ArrayList<GetByCounselorBean>();
	 private OrderStateViewAdapter adapter;

		private static int pageIndex = 1;
		private boolean isHaveMoreData = false;
		private LoadMoreListView mLoadMoreListView;
		private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
		private static WaitInOrderStateView fragment;
		private boolean refresh=false;
		
		/**
		 * 单例
		 */
		public static WaitInOrderStateView instance=null;
		public static WaitInOrderStateView getInstance(){
			if(instance==null){
				instance=new WaitInOrderStateView();
			}
			return instance;
		}

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
							orderLis.clear();
//							adapter.notifyDataSetChanged();
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
		
		public void onEventMainThread(ResultWaitInCounselorOrderBean msg) {
			if (msg != null) {
				if (msg.TModel.size() > 0&&msg.TModel.size()==10) {
					mLoadMoreListView.setHaveMoreData(true);
				} else {
					mLoadMoreListView.setHaveMoreData(false);

				}
				if(msg.TModel.size()!=0){
					orderLis.addAll(msg.TModel);
					adapter=new OrderStateViewAdapter(getContext(),getActivity(),orderLis);
					mLoadMoreListView.setAdapter(adapter);
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
			initData();
			Log.i("TestLog","已发货");
		}

		protected void loadData(final int page) {
			if (page == 1) {
				orderLis.clear();
			}
			
			byCounselorNet=new WaitInCounselorOrderNet(getActivity());
			byCounselorNet.setData(1,40,page);

			pageIndex = page;

		}
		
	 
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
