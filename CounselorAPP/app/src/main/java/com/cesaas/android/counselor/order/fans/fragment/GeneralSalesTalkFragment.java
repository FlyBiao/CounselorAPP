package com.cesaas.android.counselor.order.fans.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultSalesTalkBean;
import com.cesaas.android.counselor.order.bean.ResultSalesTalkBean.SalesTalkBean;
import com.cesaas.android.counselor.order.net.SalesTalkNet;
import com.cesaas.android.counselor.order.view.NoPayOrderStateView;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 通用销售话术
 * @author FGB
 *
 */
public class GeneralSalesTalkFragment extends BaseFragment{
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	public int RESULT_CODE=101;
	
	private View view;
	
	private SalesTalkNet salesTalkNet;
	private ArrayList<SalesTalkBean> talkLis= new ArrayList<SalesTalkBean>();
	
	/**
	 * 单例
	 */
	public static GeneralSalesTalkFragment instance=null;
	public static GeneralSalesTalkFragment getInstance(){
		if(instance==null){
			instance=new GeneralSalesTalkFragment();
		}
		return instance;
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_general_sales_talk, container,false);
		
		initView();
		setAdapter();
		initOnItemClick();
		
		return view;
	}
	

	private void initView() {
		mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_more_talk_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView)view. findViewById(R.id.refresh_talk_and_load_more);		
	}
	
	public void initData(){
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
	
	public void onEventMainThread(ResultSalesTalkBean msg) {
		
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			talkLis.clear();
			if(msg.TModel.size()!=0){
				talkLis.addAll(msg.TModel);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<SalesTalkBean>(getContext(),R.layout.item_sales_talk,talkLis) {

			@Override
			public void convert(ViewHolder holder, SalesTalkBean bean,int postion) {
				holder.setText(R.id.tv_sales_talk_content, bean.Content);
			}

		});
		
		initData();
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			talkLis.clear();
		}
		salesTalkNet = new SalesTalkNet(getContext());
		salesTalkNet.setData(page);
		pageIndex = page;
	}
	
	public void initOnItemClick(){
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent();
		        intent.putExtra("Content", talkLis.get(position).Content);
		        
		        getActivity().setResult(RESULT_CODE, intent);
		        getActivity().finish();
			}
		});
	}


	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
