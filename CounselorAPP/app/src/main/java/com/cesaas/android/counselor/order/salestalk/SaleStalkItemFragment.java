package com.cesaas.android.counselor.order.salestalk;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.salestalk.net.GetListNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 销售话术Item MemberFragment
 * @author FGB
 *
 */
public class SaleStalkItemFragment extends BaseFragment{

	private View view;
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private int categoryId;
	
	private GetListNet getListNet;
	private List<SalesTalkCategoryListBean> categoryListBean=new ArrayList<SalesTalkCategoryListBean>();

	public static Fragment getInstance() {
		return new SaleStalkItemFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			view = inflater.inflate(R.layout.layout_selastlk_item_fragment,container, false);
			
			mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_more_selastlk_list);
			mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) view.findViewById(R.id.refresh_selastlk_and_load_more);
			
			categoryId= getArguments().getInt("index");
			
			setAdapter();
			
		return view;
	}
	
	private void setAdapter() {
		mLoadMoreListView.setAdapter(new CommonAdapter<SalesTalkCategoryListBean>(getContext(),R.layout.item_selastlk,categoryListBean) {

			@Override
			public void convert(ViewHolder holder, SalesTalkCategoryListBean bean,int postion) {
				holder.setText(R.id.tv_selastlk_title, bean.Content);
			}

		});
		
		initData();
		initItemClickListener();
	}
	
	/**
	 * 初始化ListView Item监听
	 */
	private void initItemClickListener() {
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
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

	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			categoryListBean.clear();
		}
		getListNet=new GetListNet(getContext());
		getListNet.setData(categoryId,page);

		pageIndex = page;
	}

	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultSalesTalkCategoryListBean msg){
		if(msg.IsSuccess==true){
			categoryListBean.clear();
			if (msg.TModel.size() > 0&&msg.TModel.size()==20) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				categoryListBean.addAll(msg.TModel);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
		else{
			ToastFactory.getLongToast(getContext(), msg.Message);
		}
	}
	
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
