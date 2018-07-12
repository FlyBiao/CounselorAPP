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
import com.cesaas.android.counselor.order.task.bean.ResultPrivateTaskBean;
import com.cesaas.android.counselor.order.task.bean.ResultPrivateTaskBean.PrivateTaskBean;
import com.cesaas.android.counselor.order.task.net.PrivateTaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 私有任务
 * @author FGB
 *
 */
public class PrivateTaskFragment extends BaseFragment{

	private View view;
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private PrivateTaskListNet privateTaskListNet;
	private ArrayList<PrivateTaskBean> privateTaskBeans= new ArrayList<PrivateTaskBean>();
	
	/**
	 * 单例
	 */
	public static PrivateTaskFragment instance=null;
	public static PrivateTaskFragment getInstance(){
		if(instance==null){
			instance=new PrivateTaskFragment();
		}
		return instance;
	}
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_private_task, container,false);
		
		initView();
		
		setAdapter();
		
		return view;
	}
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
		mLoadMoreListView.setAdapter(new CommonAdapter<PrivateTaskBean>(getContext(),R.layout.item_private_task,privateTaskBeans) {

			@Override
			public void convert(ViewHolder holder, PrivateTaskBean bean,int postion) {
				holder.setText(R.id.tv_private_task_title, bean.Title);
				holder.setText(R.id.tv_private_task_time, bean.CreateTime);
				
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
					holder.setText(R.id.tv_private_task_pattern, "个人");
					
				}else{//团队完成
					holder.setText(R.id.tv_private_task_pattern, "团队");
					holder.getView(R.id.iv_task_pattern).setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.team_task));
				}
			}

		});
		
		initData();
		initItemClickListener();
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
				bundle.putInt("TaskId", privateTaskBeans.get(position).TaskId);
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
			privateTaskBeans.clear();
		}
		privateTaskListNet=new PrivateTaskListNet(getContext());
		privateTaskListNet.setData(page,0);

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
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultPrivateTaskBean msg){
		if(msg.IsSuccess==true){
			if (msg.TModel.size() > 0&&msg.TModel.size()==20) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				privateTaskBeans.addAll(msg.TModel);
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
			
		}else{
			ToastFactory.getLongToast(getContext(), msg.Message);
		}
	}
	
	/**
	 * 初始化视图控件
	 */
	public void initView(){
		mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_more_private_task_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) view.findViewById(R.id.refresh_private_task_and_load_more);
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
