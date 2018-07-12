package com.cesaas.android.counselor.order.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.adapter.OrderAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.GetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * 待接单 Created by FGB on 2016/3/7.
 */
public class ReceiveOrderFragment extends Fragment {

	private Context mContext;
	private GetUnReceiveOrderNet net;
	private OrderAdapter adapter;



	private View view;
	private static int pageIndex = 1;
	private LoadMoreListView mLoadMoreListView;
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
	public static ReceiveOrderFragment fragment;
	
	private LayoutInflater mInflater;

	private ArrayList<GetUnReceiveOrderBean> orderLis = new ArrayList<GetUnReceiveOrderBean>();
	private boolean refresh=false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mInflater = LayoutInflater.from(getActivity());
//		inflater();
		
		mContext = this.getActivity();
		fragment=this;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_order_layout, container, false);
		initViews();
		
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initData();
		initListener();
	}

	@Override
	public void fetchData() {

	}

	/**
	 * 初始化控件
	 */
	public void initViews() {
		mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_list);
		mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_and_load_more);
		adapter = new OrderAdapter(mContext, getActivity());
		mLoadMoreListView.setAdapter(adapter);
	}

	/**
	 * 初始化ListView监听
	 */
	public void initListener() {
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String TradeId=orderLis.get(position).TradeId;
				Bundle bundle = new Bundle();
				bundle.putString("TradeId", TradeId);
				Skip.mNextFroData(getActivity(), OrderDetailActivity.class, bundle);
			}
		});
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

	public void loadData(final int page) {
		if (page == 1) {
			orderLis.clear();
		}
		net = new GetUnReceiveOrderNet(getActivity());
		net.setData(page);
		pageIndex = page;

	}
	
	public void onEventMainThread(ResultGetUnReceiveOrderBean msg) {

		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==30) {
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				orderLis.addAll(msg.TModel);
				/**
				 * 按照时间排序显示
				 */
				Collections.sort(orderLis, new Comparator<GetUnReceiveOrderBean>() {
					@Override
					public int compare(GetUnReceiveOrderBean lhs, GetUnReceiveOrderBean rhs) {
						
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
			adapter.updateListView(orderLis);
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
			// 当加载完成之后设置此时不在刷新状态

		}

	}


	
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
				fragment.loadData(pageIndex);
		};
	};


	/**
	 * 待接单接口
	 * @author fgb
	 *
	 */
	public class GetUnReceiveOrderNet extends BaseNet {

		public GetUnReceiveOrderNet(Context context) {
			super(context, true);
			this.uri="Order/Sw/Order/GetUnReceiveOrder";
		}

		public void setData(int page){
			try {
				data.put("PageIndex", page);
				data.put("PageSize", 30);
				data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mPostNet(); // 开始请求网络
		}

		public void setData(){
			try {
				data.put("PageIndex", "");
				data.put("PageSize", "");
				data.put("UserTicket",AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mPostNet(); // 开始请求网络
		}

		@Override
		protected void mSuccess(String rJson) {
			super.mSuccess(rJson);
			Gson gson = new Gson();
			ResultGetUnReceiveOrderBean lbean = gson.fromJson(rJson, ResultGetUnReceiveOrderBean.class);
			if (lbean != null) {
				if (lbean.TModel.size() > 0&&lbean.TModel.size()==30) {
					mLoadMoreListView.setHaveMoreData(true);
				} else {
					mLoadMoreListView.setHaveMoreData(false);

				}
				if(lbean.TModel.size()!=0){
					orderLis.addAll(lbean.TModel);
					/**
					 * 按照时间排序显示
					 */
					Collections.sort(orderLis, new Comparator<GetUnReceiveOrderBean>() {
						@Override
						public int compare(GetUnReceiveOrderBean lhs, GetUnReceiveOrderBean rhs) {

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
				adapter.updateListView(orderLis);
				mRefreshAndLoadMoreView.setRefreshing(false);
				mLoadMoreListView.onLoadComplete();
				// 当加载完成之后设置此时不在刷新状态

			}
		}

		@Override
		protected void mFail(HttpException e, String err) {
			super.mFail(e, err);
			Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
		}

	}


}
