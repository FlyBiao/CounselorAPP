package com.cesaas.android.counselor.order.projecttest;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.net.FansNet;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_fans_service_layout)
public class FansActivity extends BasesActivity{

	@ViewInject(R.id.load_more_fans_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_fans_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	
	private static int pageIndex = 1;//当前页
	private static FansActivity fragment;
	
	private FansNet fansNet;
	private List<Fans> fansLis= new ArrayList<Fans>();
	
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			pageIndex=1;
			fragment.loadData(pageIndex);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		fragment=this;
		
		mLoadMoreListView.setAdapter(new CommonAdapter<Fans>(mContext,R.layout.item_user_fans,fansLis) {

			@Override
			public void convert(ViewHolder holder, Fans fans,int postion) {
				holder.setText(R.id.tv_fans_nick, fans.FANS_NICKNAME);
				holder.setText(R.id.tv_fans_mobile, fans.FANS_MOBILE);
				holder.setCircleImageViewBitmap(R.id.iv_fans_img, bitmapUtils, fans.FANS_ICON);
			}
		});
		
		initData();
	}
	
	
	public void onEventMainThread(ResultFans msg) {
		
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				fansLis.addAll(msg.TModel);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
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
			fansLis.clear();
		}
		fansNet = new FansNet(mActivity);
		fansNet.setData(page);

		pageIndex = page;

	}
}
