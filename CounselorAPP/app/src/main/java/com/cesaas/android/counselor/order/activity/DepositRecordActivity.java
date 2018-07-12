package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.DepositListBean;
import com.cesaas.android.counselor.order.bean.DepositListBean.DepositList;
import com.cesaas.android.counselor.order.net.DepositListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 提现记录页面
 * @author FGB
 *
 */

@ContentView(R.layout.activity_deposit_record_layout)
public class DepositRecordActivity extends BasesActivity{

	@ViewInject(R.id.load_more_deposit_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_deposit_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	@ViewInject(R.id.il_deposit_back)
	private LinearLayout il_deposit_back;
	
	private static int pageIndex = 1;//当前页
	private int searchResultFilterCode=1002;
	private boolean refresh=false;
	private static DepositRecordActivity fragment;
	
	private DepositListNet depositListNet;
	private ArrayList<DepositList> depositList= new ArrayList<DepositList>();
	
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			pageIndex=1;
			fragment.initLoadData(pageIndex);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		fragment=this;
		
		loadData();
		
		mBack();
	}
	
	public void onEventMainThread(DepositListBean msg) {
		
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				depositList.addAll(msg.TModel);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	public void loadData(){
		
		//获取Adapter 设置数据
		mLoadMoreListView.setAdapter(new CommonAdapter<DepositList>(mActivity,R.layout.item_deposit_record_layout,depositList) {

			@Override
			public void convert(ViewHolder holder, DepositList bean,int postion) {
				holder.setText(R.id.tv_deposit_record_money, bean.Income);
				holder.setText(R.id.tv_deposit_record_date, bean.CreateTime);
				
				//0待审核 1审核通过 2提现成功 3提现失败，4提现失败
				if(bean.Status==0){
					holder.setText(R.id.tv_deposit_status, "审核中");
					holder.setTextColor(R.id.tv_deposit_status, getResources().getColor(R.color.orange));
				}
				else if(bean.Status==1){
					holder.setText(R.id.tv_deposit_status, "审核通过");
					holder.setTextColor(R.id.tv_deposit_status, getResources().getColor(R.color.color_title_bar));
				}
				else if(bean.Status==2){
					holder.setText(R.id.tv_deposit_status, "提现成功");
					holder.setTextColor(R.id.tv_deposit_status, getResources().getColor(R.color.green));
				}
				else if(bean.Status==3){
					holder.setText(R.id.tv_deposit_status, "审核未通过");
					holder.setTextColor(R.id.tv_deposit_status, getResources().getColor(R.color.rgb_text_qian_huise));
				}
				else if(bean.Status==4){//
					holder.setText(R.id.tv_deposit_status, "提现失败");
					holder.setTextColor(R.id.tv_deposit_status, getResources().getColor(R.color.red));
				}
			}

		});
		
		initData();
	}

	private void initData() {
		initLoadData(1);
		
		mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
		mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
		// 设置下拉刷新监听
		mRefreshAndLoadMoreView
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refresh=true;
						pageIndex = 1;
						initLoadData(pageIndex);
					}
				});
		// 设置加载监听
		mLoadMoreListView
				.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
					@Override
					public void onLoadMore() {
						refresh=false;
						initLoadData(pageIndex + 1);
					}
				});
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void initLoadData(final int page) {

		if (page == 1) {
			depositList.clear();
		}
		depositListNet = new DepositListNet(mContext);
		depositListNet.setData(page);

		pageIndex = page;
	}
	

	/**
	 * 返回上一个页面
	 */
	private void mBack() {
		
		il_deposit_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.putExtra("sel","0");
				setResult(searchResultFilterCode, mIntent);// 设置结果，并进行传送
				Skip.mBack(mActivity);
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		return super.onKeyDown(keyCode, event);
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent i=new Intent();
			i.putExtra("sel","0");
			setResult(searchResultFilterCode, i);// 设置结果，并进行传送
			Skip.mBack(mActivity);
		}
		return true;
	}
}
