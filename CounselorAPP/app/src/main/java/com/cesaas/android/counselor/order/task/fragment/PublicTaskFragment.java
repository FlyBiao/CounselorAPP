package com.cesaas.android.counselor.order.task.fragment;

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
import com.cesaas.android.counselor.order.task.bean.ResultPublicTaskBean;
import com.cesaas.android.counselor.order.task.bean.ResultPublicTaskBean.PublicTaskBean;
import com.cesaas.android.counselor.order.task.net.PublicTaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 共有任务
 * @author FGB
 *
 */
public class PublicTaskFragment extends BaseFragment{

	private View view;
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private PublicTaskListNet publicTaskListNet;
	private ArrayList<PublicTaskBean> publicTaskBeans= new ArrayList<PublicTaskBean>();
	
	/**
	 * 单例
	 */
	public static PublicTaskFragment instance=null;
	public static PublicTaskFragment getInstance(){
		if(instance==null){
			instance=new PublicTaskFragment();
		}
		return instance;
	}
	
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_public_task, container,false);
		
		mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_more_public_task_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) view.findViewById(R.id.refresh_public_task_and_load_more);
		
		setAdapter();
		
		return view;
	}
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<PublicTaskBean>(getContext(),R.layout.item_public_task,publicTaskBeans) {

			@Override
			public void convert(ViewHolder holder, PublicTaskBean bean,int postion) {
				holder.setText(R.id.tv_task_title, bean.Title);
				holder.setText(R.id.tv_task_time, bean.CreateTime);
				
				//优先级
				if(bean.Leve==1){
					holder.setText(R.id.tv_shop_clie, bean.Leve+"");
					holder.getView(R.id.tv_shop_clie).setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_ellipse_red_bg));
				}else if(bean.Leve==2){
					holder.setText(R.id.tv_shop_clie, bean.Leve+"");
					holder.getView(R.id.tv_shop_clie).setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_ellipse_orange_bg));
				}else{//优先级 3
					holder.setText(R.id.tv_shop_clie, "3");
					holder.getView(R.id.tv_shop_clie).setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_ellipse_green_bg));
				}
				
				if(bean.Pattern==0){//个人完成
					holder.setText(R.id.tv_task_pattern, "个人");
					
				}else{//团队完成
					holder.setText(R.id.tv_task_pattern, "团队");
					holder.getView(R.id.iv_task_pattern).setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.team_task));
					
				}
			}

		});
		
		initData();
		initItemClickListener();
	}
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultPublicTaskBean msg){
		if(msg.IsSuccess==true && msg.TModel!=null){
			publicTaskBeans.addAll(msg.TModel);
			if (publicTaskBeans.size() > 0&&publicTaskBeans.size()==20) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	/**
	 * ListView Item Click
	 */
	private void initItemClickListener() {
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putInt("TaskId", publicTaskBeans.get(position).TaskId);
				Skip.mNextFroData(getActivity(), ShopTaskDetailActivity.class, bundle);
			}
		});
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	public void loadData(int page) {

		if (page == 1) {
			publicTaskBeans.clear();
		}
		publicTaskListNet=new PublicTaskListNet(getContext());
		publicTaskListNet.setData(page);;

		pageIndex = page;
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
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
