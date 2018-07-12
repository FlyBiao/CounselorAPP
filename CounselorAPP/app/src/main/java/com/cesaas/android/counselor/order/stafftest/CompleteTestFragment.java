package com.cesaas.android.counselor.order.stafftest;

import java.util.ArrayList;

import android.annotation.SuppressLint;
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
import com.cesaas.android.counselor.order.bean.ResultStaffTestCompleteBean;
import com.cesaas.android.counselor.order.bean.ResultStaffTestCompleteBean.StaffTestCompleteBean;
import com.cesaas.android.counselor.order.stafftest.net.StaffCompleteTestNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 完成考试
 * @author fgb
 *
 */
public class CompleteTestFragment extends BaseFragment{

	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private View view;
	private StaffCompleteTestNet staffTestNet;
	private ArrayList<StaffTestCompleteBean> testLis= new ArrayList<StaffTestCompleteBean>();
	
	/**
	 * 单例
	 */
	public static CompleteTestFragment instance=null;
	public static CompleteTestFragment getInstance(){
		if(instance==null){
			instance=new CompleteTestFragment();
		}
		return instance;
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_complete_test, container,false);
		mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_more_complete_test_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) view.findViewById(R.id.refresh_complete_test_and_load_more);
		
		setAdapter();
		
		return view;
	}
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<StaffTestCompleteBean>(getContext(),R.layout.item_complete_test,testLis) {

			@SuppressLint("ResourceAsColor")
			@Override
			public void convert(ViewHolder holder, StaffTestCompleteBean bean,int postion) {
				holder.setText(R.id.tv_complete_test_title, bean.Title);
				holder.setText(R.id.tv_complete_test_fullscore, bean.FullScore+"");
				holder.setText(R.id.tv_test_complete_count, bean.Nums+"");
				
				if(bean.IsMust==1){//必考
					holder.getView(R.id.tv_test_complete_ismust).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.button_ellipse_red_bg));
				}else{
					holder.getView(R.id.tv_test_complete_ismust).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.button_ellipse_orange_bg));
				}
				
				if(bean.Score>=bean.PassScore){//通过考试
					holder.setText(R.id.tv_complete_passscore, bean.Score+"  已通过");
					holder.setTextColor(R.id.tv_passscore, getContext().getResources().getColor(R.color.test_color));
					holder.setTextColor(R.id.tv_complete_passscore, getContext().getResources().getColor(R.color.test_color));
					
				}else{//未通过考试
					holder.setText(R.id.tv_complete_passscore, bean.Score+"  未通过");
					holder.setTextColor(R.id.tv_passscore, getContext().getResources().getColor(R.color.red));
					holder.setTextColor(R.id.tv_complete_passscore, getContext().getResources().getColor(R.color.red));
				}
				
			}

		});
		
		initData();
		initItemClickListener();
	}
	
	public void onEventMainThread(ResultStaffTestCompleteBean msg) {
		
		if (msg.IsSuccess==true && msg != null) {
			testLis.addAll(msg.TModel);
			if (testLis.size() > 0&& testLis.size()==20) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
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
				ToastFactory.getLongToast(getContext(), "position:"+position);
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
		staffTestNet = new StaffCompleteTestNet(getContext());
		staffTestNet.setData(2,page);

		pageIndex = page;
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
