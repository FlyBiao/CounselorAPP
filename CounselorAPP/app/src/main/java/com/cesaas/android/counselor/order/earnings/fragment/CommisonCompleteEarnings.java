package com.cesaas.android.counselor.order.earnings.fragment;

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
import android.widget.AdapterView.OnItemClickListener;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.GetCommsionListAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetCommsion;
import com.cesaas.android.counselor.order.bean.ResultGetCommsion.GetCommsionBean;
import com.cesaas.android.counselor.order.earnings.activity.OrderEarningsDetailActivity;
import com.cesaas.android.counselor.order.net.GetCommsionNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;

/**
 * 已结算收益
 * @author FGB
 *
 */
public class CommisonCompleteEarnings extends BaseFragment{

	private GetCommsionListAdapter commsionAdapter;
	
	private GetCommsionNet commsionNet;
	
	private ArrayList<GetCommsionBean> getList=new ArrayList<GetCommsionBean>();
	
	
	private LoadMoreListView mLoadMoreListView;
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
	private static CommisonCompleteEarnings fragment;
	private static int pageIndex = 1;
	private boolean isHaveMoreData = false;
	private boolean refresh=false;
	private View view;
	
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
	
	public void initData(){
		commsionAdapter = new GetCommsionListAdapter(getActivity(), getList);
		mLoadMoreListView.setAdapter(commsionAdapter);
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
	
	protected void loadData(final int page) {
		// TODO Auto-generated method stub

		if (page == 1) {
			getList.clear();
		}
		commsionNet=new GetCommsionNet(getActivity());
		commsionNet.setData(2,1,page);

		pageIndex = page;

	}
	
	/**
	 * 初始化ListView监听
	 */
	public void initListener() {
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("TradeId", getList.get(position).TradeId);
				Skip.mNextFroData(getActivity(), OrderEarningsDetailActivity.class, bundle);
			}
		});
	}
	
	public void onEventMainThread(ResultGetCommsion msg) {
		
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
//				if(refresh){
//					Toast.makeText(mContext, "暂时没有数据哦!", Toast.LENGTH_SHORT).show();
//				}
				
			}
			if(msg.TModel.size()!=0){
				getList.addAll(msg.TModel);
				/**
				 * 按照时间排序显示
				 */
				Collections.sort(getList, new Comparator<GetCommsionBean>() {
					@Override
					public int compare(GetCommsionBean lhs, GetCommsionBean rhs) {
						
						Date date0=null;
						Date date1=null;
						for (int i = 0; i < getList.size(); i++) {
							date0=AbDateUtil.stringToDate(getList.get(i).CreateDate);
							date1=AbDateUtil.stringToDate(getList.get(i).CreateDate);
						}
						
						// 对日期字段进行升序，如果欲降序可采用after方法  
		                if (date0.after(date1)) {  
		                	
		                    return 1;  
		                }  
		                
		                return -1;
					}
				});
			}
			commsionAdapter.updateListView(getList);
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
			// 当加载完成之后设置此时不在刷新状态

		}
		
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_complete_earnings_layout, container, false);
        mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_develop_c_list);

		mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_develop_c_and_load_more);
		
		initListener();
		initData();
        return view;
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
