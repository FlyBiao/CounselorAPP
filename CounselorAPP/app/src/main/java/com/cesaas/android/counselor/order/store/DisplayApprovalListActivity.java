package com.cesaas.android.counselor.order.store;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.CustomTextDialog;
import com.cesaas.android.counselor.order.dialog.CustomTextQueryDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.adapter.ReferenceDisplayAdapter;
import com.cesaas.android.counselor.order.store.bean.DisplayApprovalBean;
import com.cesaas.android.counselor.order.store.bean.GraffitiImagePathBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayApprovaBean;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayBean;
import com.cesaas.android.counselor.order.store.bean.StoreDisplayBean;
import com.cesaas.android.counselor.order.store.net.DisplayApprovalListNet;
import com.cesaas.android.counselor.order.store.net.StoreDisplayNet;
import com.cesaas.android.counselor.order.task.net.PublicTaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.hzw.graffiti.GraffitiActivity;

/**
 * 陈列审批列表页面
 * @author FGB
 *
 */
public class DisplayApprovalListActivity extends BasesActivity implements OnClickListener{

	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新

	private boolean refresh=false;
	private static int pageIndex = 1;//当前页

	private DisplayApprovalListNet displayApprovalListNet;
	private ArrayList<DisplayApprovalBean> storeDisplayList= new ArrayList<DisplayApprovalBean>();

	private LinearLayout llBack;
	private TextView tvBaseTitle,tvBaseRightTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_approval_list_activity);

		initView();
		initData();
		setAdapter();
	}

	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultStoreDisplayApprovaBean msg) {

		if (msg.IsSuccess==true) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==20) {
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
			ToastFactory.getLongToast(mContext, msg.Message);
//			mLoadMoreListView.setHaveMoreData(false);
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
		displayApprovalListNet = new DisplayApprovalListNet(mContext);
		displayApprovalListNet.setData(page);

		pageIndex = page;
	}


	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<DisplayApprovalBean>(mContext,R.layout.item_store_display,storeDisplayList) {

			@Override
			public void convert(ViewHolder holder, DisplayApprovalBean bean, int postion) {
				holder.setText(R.id.tv_store_display_title, bean.getTitle());
				holder.setText(R.id.tv_store_display_date, bean.getCreteTime());
				if(bean.getStatus()==1){
					holder.setText(R.id.tv_store_display_status, "待审批");
					holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.orange));

				}else if(bean.getStatus()==2){
					holder.setText(R.id.tv_store_display_status, "已过期");
					holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.rgb_text_qian_huise));

				}else if(bean.getStatus()==3){
					holder.setText(R.id.tv_store_display_status, "已通过");
					holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.green));

				}else if(bean.getStatus()==4){
					holder.setText(R.id.tv_store_display_status, "未通过");
					holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.red));
				}else {
					holder.setText(R.id.tv_store_display_status, "未开始");
					holder.setTextColor(R.id.tv_store_display_status, getResources().getColor(R.color.rgb_text_qian_huise));
				}

				mLoadMoreListView.setHaveMoreData(false);
			}

		});

		initItemClickListener();

	}

	/**
	 * List Item Click
	 */
	private void initItemClickListener() {
		mLoadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				//根据状态跳转到相应陈列任务详情页
				if(storeDisplayList.get(position).getStatus()==1){//待审批
					Skip.mNext(mActivity, DisplayApprovalActivity.class);

				}else if(storeDisplayList.get(position).getStatus()==2){//已过期
					Skip.mNext(mActivity, DisplayOverdueActivity.class);

				}else if(storeDisplayList.get(position).getStatus()==3){//已通过
					Skip.mNext(mActivity, DisplayApprovalCompleteActivity.class);

				}else if(storeDisplayList.get(position).getStatus()==4){//未通过
					Skip.mNext(mActivity, DisplayApprovalPassActivity.class);

				}else{//未完成
					Skip.mNext(mActivity, StoreDisplayDetailActivity.class);

				}
			}
		});

	}

	/**
	 * 初始化视图控件
	 */
	public void initView(){
		mLoadMoreListView=(LoadMoreListView) findViewById(R.id.load_more_store_diaplay_list_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) findViewById(R.id.refresh_store_diaplay_list_and_load_more);

		llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
		tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
		tvBaseRightTitle.setVisibility(View.VISIBLE);
		tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);

		llBack.setOnClickListener(this);
		tvBaseRightTitle.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ll_base_title_back://返回
				Skip.mBack(mActivity);
				break;
			case R.id.tv_base_title_right://搜索
				showDialog();
				break;
		}
	}

	public void showDialog(){
		new CustomTextQueryDialog(mContext).mInitShow("搜索","搜索", "返回", new CustomTextQueryDialog.ConfirmListener() {
			@Override
			public void onClick(Dialog dialog) {
				ToastFactory.getLongToast(mContext,"搜索成功！");
			}
		},null);
	}
}
