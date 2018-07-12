package com.cesaas.android.counselor.order.store;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.FansNet;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayBean;
import com.cesaas.android.counselor.order.store.bean.StoreDisplayBean;
import com.cesaas.android.counselor.order.store.net.StoreDisplayNet;
import com.cesaas.android.counselor.order.task.net.PublicTaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 店铺陈列页面
 * @author FGB
 *
 */
public class StoreDisplayActivity extends BasesActivity implements OnClickListener{
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private StoreDisplayNet storeDisplayNet;
	private ArrayList<StoreDisplayBean> storeDisplayList;
	
	private LinearLayout llBack;
	private TextView tvBaseTitle,tvBaseRightTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.store_display_activity);

		storeDisplayList= new ArrayList<StoreDisplayBean>();

		initView();
		setAdapter();
		initData();
	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplayBean msg) {

		if (msg.IsSuccess==true ) {

			if (msg.TModel.size() > 0 && msg.TModel.size()==20) {
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				storeDisplayList.addAll(msg.TModel);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();

		}else{

			ToastFactory.getLongToast(mContext, "获取列表数据失败！"+msg.Message);
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
			storeDisplayList.clear();
		}
		storeDisplayNet = new StoreDisplayNet(mContext);
		storeDisplayNet.setData(page);
		pageIndex = page;
	}
	

	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<StoreDisplayBean>(mContext,R.layout.item_store_display,storeDisplayList) {

			@Override
			public void convert(ViewHolder holder, StoreDisplayBean bean,int postion) {
					holder.setText(R.id.tv_store_display_title, bean.getTitle());
					holder.setText(R.id.tv_store_display_date, bean.getCreteTime());
					if(bean.getEvenType()==1){
						holder.setText(R.id.tv_store_display_status, "待提交");
						holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.red));

					}else if(bean.getEvenType()==2){
						holder.setText(R.id.tv_store_display_status, "待审核");
						holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.base_text_color));

					}else {
						holder.setText(R.id.tv_store_display_status, "待确定");
						holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.rgb_text_qian_huise));
					}
			}
		});
		
		initItemClickListener();
		
	}
	
	/**
	 * List Item Click
	 */
	private void initItemClickListener() {
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//根据状态跳转到相应陈列任务详情页
				if(storeDisplayList.get(position).getEvenType()==1){//待提交
					bundle.putString("SheetId",storeDisplayList.get(position).getSheetId());
					bundle.putString("FlowId",storeDisplayList.get(position).getFlowId());
					bundle.putString("TaskId",storeDisplayList.get(position).getTaskId());
					Skip.mNextFroData(mActivity, StoreDisplayDetailActivity.class,bundle);

				}else if(storeDisplayList.get(position).getEvenType()==2){//待审核
					bundle.putString("SheetId",storeDisplayList.get(position).getSheetId());
					bundle.putString("FlowId",storeDisplayList.get(position).getFlowId());
					bundle.putString("TaskId",storeDisplayList.get(position).getTaskId());
					Skip.mNextFroData(mActivity, DisplayApprovalActivity.class,bundle);

				}else{//待确定
					bundle.putString("SheetId",storeDisplayList.get(position).getSheetId());
					bundle.putString("FlowId",storeDisplayList.get(position).getFlowId());
					bundle.putString("TaskId",storeDisplayList.get(position).getTaskId());
					Skip.mNextFroData(mActivity, DisplayOverdueActivity.class,bundle);
				}
//
	}
});
		
	}

	/**
	 * 初始化视图控件
	 */
	public void initView(){
		mLoadMoreListView=(LoadMoreListView) findViewById(R.id.load_more_store_diaplay_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) findViewById(R.id.refresh_store_diaplay_and_load_more);
		
		llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
		tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
		tvBaseTitle.setText("门店陈列");
		tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
		tvBaseRightTitle.setText("审批记录");
//		tvBaseRightTitle.setVisibility(View.VISIBLE);

		llBack.setOnClickListener(this);
		tvBaseRightTitle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ll_base_title_back://返回
				Skip.mBack(mActivity);
				break;
			case R.id.tv_base_title_right://审批记录
				Skip.mNext(mActivity,DisplayApprovalListActivity.class);
				break;
		}
	}
}
