package com.cesaas.android.counselor.order.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.adapter.AllOrderStateAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.net.GetByCounselorNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

/**
 * 退款订单分销状态
 * @author FGB
 *
 */
public class RefundOrderStateView  extends BaseFragment{
	
	 private GetByCounselorNet byCounselorNet;
	 private AllOrderStateAdapter allOrderStateAdapter;
	 private ArrayList<GetByCounselorBean> orderLis= new ArrayList<GetByCounselorBean>();
		
	 private View view;
		private static int pageIndex = 1;
		private boolean isHaveMoreData = false;
		private LoadMoreListView mLoadMoreListView;
		private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
		private static RefundOrderStateView fragment;
		private boolean refresh=false;

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
			allOrderStateAdapter=new AllOrderStateAdapter(getActivity(),orderLis);
			mLoadMoreListView.setAdapter(allOrderStateAdapter);
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
		
		/**
		 * 初始化ListView监听
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
		
		public void onEventMainThread(ResultGetByCounselorBean msg) {
			if (msg != null) {
				if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
					mLoadMoreListView.setHaveMoreData(true);
				} else {
					mLoadMoreListView.setHaveMoreData(false);
//					if(refresh){
//						Toast.makeText(mContext, "暂时没有数据哦!", Toast.LENGTH_SHORT).show();
//					}
					
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
				allOrderStateAdapter.updateListView(orderLis);
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
			initData();
			return view;
		}

		protected void loadData(final int page) {
			// TODO Auto-generated method stub

			if (page == 1) {
				orderLis.clear();
			}
			byCounselorNet=new GetByCounselorNet(getActivity());
			byCounselorNet.setData(1,80,page);

			pageIndex = page;

		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}
	 
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
