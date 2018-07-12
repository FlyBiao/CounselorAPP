package com.cesaas.android.counselor.order.stafftest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import com.cesaas.android.counselor.order.bean.ResultStaffTestIngBean;
import com.cesaas.android.counselor.order.bean.ResultStaffTestIngBean.StaffTestIngBean;
import com.cesaas.android.counselor.order.net.StaffTestNet;
import com.cesaas.android.counselor.order.stafftest.net.StaffTestIngNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 考试中
 * @author fgb
 *
 */
public class TestIngFragment extends BaseFragment{
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private View view;
	private StaffTestIngNet staffTestNet;
	private ArrayList<StaffTestIngBean> testLis= new ArrayList<StaffTestIngBean>();

	/**
	 * 单例
	 */
	public static TestIngFragment instance=null;
	public static TestIngFragment getInstance(){
		if(instance==null){
			instance=new TestIngFragment();
		}
		return instance;
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_test_ing, container,false);
		
		mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_more_test_ing_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) view.findViewById(R.id.refresh_test_ing_and_load_more);
		
		setAdapter();
		
		return view;
	}
	
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<StaffTestIngBean>(getContext(),R.layout.item_test_ing,testLis) {

			@Override
			public void convert(ViewHolder holder, StaffTestIngBean bean,int postion) {
				holder.setText(R.id.tv_test_ing_title, bean.Title);
				holder.setText(R.id.tv_test__ing_fullscore, bean.FullScore+"");
				holder.setText(R.id.tv_test_ing_count, bean.Nums+"");
				
				if(bean.IsMust==1){//必考
					holder.getView(R.id.tv_test_ing_ismust).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.button_ellipse_red_bg));
				}else{//选考
					holder.getView(R.id.tv_test_ing_ismust).setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.button_ellipse_orange_bg));
				}
				
				try {
					//日期时间转成秒
					Calendar c = Calendar.getInstance(); 
					c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bean.BegDate));
					//获取分钟
					long timeInMillis=c.getTimeInMillis();
					//计算考试剩余时间【分钟】
					long testResidueTime=bean.TimeLimit - (timeInMillis%3600/60);
					
					holder.setText(R.id.tv_test_residue_time, testResidueTime+"分钟");
					
				} catch (ParseException e) {
					e.printStackTrace();
				} 
			}

		});
		
		initData();
		initItemClickListener();
	}
	
	public void onEventMainThread(ResultStaffTestIngBean msg) {
		
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
		staffTestNet = new StaffTestIngNet(getContext());
		staffTestNet.setData(1,page);

		pageIndex = page;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
