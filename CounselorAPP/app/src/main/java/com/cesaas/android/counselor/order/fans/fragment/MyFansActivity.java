package com.cesaas.android.counselor.order.fans.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.MyFansAdapter;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.fans.activity.VipDetailActivity;
import com.cesaas.android.counselor.order.net.FansNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 我的粉丝
 * @author FGB
 *
 */
@ContentView(R.layout.activity_fans_list_layout)
public class MyFansActivity extends BasesActivity{
	
	@ViewInject(R.id.load_more_fans_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_fans_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	@ViewInject(R.id.ll_session_list_back)
	private LinearLayout ll_session_list_back;
	
	@ViewInject(R.id.tv_selectall)
	private TextView tv_selectall;
	
	public String getMessageTargetId;
	
	private boolean refresh=false;
	private static int pageIndex = 1;//当前页
	
	private static MyFansActivity fragment;
	
	private FansNet fansNet;
	private ArrayList<Fans> fansLis= new ArrayList<Fans>();
	
	
	private MyFansAdapter adapter;
	
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
		
		setAdapter();
		mBack();
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
//			adapter.updateListView(fansLis);
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	/**
	 * 设置Adapter数据适配器
	 */
	public void setAdapter(){
//		mLoadMoreListView.setAdapter(new CommonAdapter<Fans>(mContext,R.layout.item_my_fans,fansLis) {
//
//			@Override
//			public void convert(ViewHolder holder, Fans fans,int postion) {
//				holder.setText(R.id.tv_fans_nick, fans.FANS_NICKNAME);
//				holder.setText(R.id.tv_fans_mobile, fans.FANS_MOBILE);
//				holder.setCircleImageViewBitmap(R.id.iv_fans_img, bitmapUtils, fans.FANS_ICON);
//			}
//
//		});
		adapter=new MyFansAdapter(mContext, fansLis);
		mLoadMoreListView.setAdapter(adapter);
		
		initData();
		initItemClickListener();
		setAllSelect();
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			fansLis.clear();
		}
		fansNet = new FansNet(mContext);
		fansNet.setData(page);

		pageIndex = page;
	}
	
	/**
	 * 初始化ListView Item监听
	 */
	public void initItemClickListener(){
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putString("fansId", fansLis.get(position).FANS_ID);
				bundle.putString("fansNickName", fansLis.get(position).FANS_NICKNAME);
				Skip.mNextFroData(mActivity, VipDetailActivity.class,bundle);
				
				//启动单聊会话界面
//            	if (RongIM.getInstance() != null)
//            	   RongIM.getInstance().startPrivateChat(mActivity, 
//            			   fansLis.get(position).FANS_ID, fansLis.get(position).FANS_NICKNAME);
			}
		});
	}
	
	public void mBack(){
		ll_session_list_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}
	
	/**
	 * 设置全选
	 */
	public void setAllSelect(){
		// 全选按钮的回调接口 
		tv_selectall.setOnClickListener(new OnClickListener() { 
	        @Override 
	        public void onClick(View v) { 
	            // 遍历list的长度，将MyAdapter中的map值全部设为true 
	            for (int i = 0; i < fansLis.size(); i++) { 
	                MyFansAdapter.getIsSelected().put(i, true); 
	            } 
//	            // 数量设为list的长度 
//	            checkNum = list.size(); 
//	            // 刷新listview和TextView的显示 
//	            dataChanged(); 
	        } 
	    });
	}
}
