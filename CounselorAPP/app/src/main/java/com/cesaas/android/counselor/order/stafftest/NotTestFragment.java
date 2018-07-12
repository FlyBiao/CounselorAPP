package com.cesaas.android.counselor.order.stafftest;

import java.util.ArrayList;

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
import com.cesaas.android.counselor.order.bean.ResultStaffNotTestBean;
import com.cesaas.android.counselor.order.bean.ResultStaffNotTestBean.StaffNotTestBean;
import com.cesaas.android.counselor.order.stafftest.net.NotStaffTestNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 未考试
 * @author fgb
 *
 */
public class NotTestFragment extends BaseFragment{
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private View view;
	private NotStaffTestNet staffTestNet;
	private ArrayList<StaffNotTestBean> testLis= new ArrayList<StaffNotTestBean>();
	
	/**
	 * 单例
	 */
	public static NotTestFragment instance=null;
	public static NotTestFragment getInstance(){
		if(instance==null){
			instance=new NotTestFragment();
		}
		return instance;
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_not_test, container,false);
		
		mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_more_not_test_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) view.findViewById(R.id.refresh_not_test_and_load_more);
		
		setAdapter();
		initItemClickListener();
		
		return view;
	}
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<StaffNotTestBean>(getContext(),R.layout.item_not_test,testLis) {

			@Override
			public void convert(ViewHolder holder, StaffNotTestBean bean,int postion) {
				holder.setText(R.id.tv_test_title, bean.Title);
				holder.setText(R.id.tv_test_count, bean.Nums+"");
				holder.setText(R.id.tv_test_fullscore, bean.FullScore+"");
				
				if(bean.IsMust==1){//必考
					holder.getView(R.id.tv_test_ismust).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.button_ellipse_red_bg));
					
				}else{//选考
					holder.getView(R.id.tv_test_ismust).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.button_ellipse_orange_bg));
				}
			}

		});
		
		initData();
	}
	
	public void onEventMainThread(ResultStaffNotTestBean msg) {
		
		if (msg.IsSuccess==true && msg != null) {
			
			if (msg.TModel.size() > 0&& msg.TModel.size()==20) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			
			if(msg.TModel.size()!=0){
				testLis.addAll(msg.TModel);
			}
			
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}

	/**
	 * 初始化ItemClick点击事件
	 */
	private void initItemClickListener() {
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				//跳转考试页面【H5】
				Bundle bundle=new Bundle();
				bundle.putInt("PaperId", testLis.get(position).PaperId);
				Skip.mNextFroData(getActivity(), StaffTestActivity.class,bundle);
			}
		});
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
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
			testLis.clear();
		}
		staffTestNet = new NotStaffTestNet(getContext());
		staffTestNet.setData(0,page);

		pageIndex = page;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
