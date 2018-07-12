package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean.FansListByShopIdBean;
import com.cesaas.android.counselor.order.dialog.SearchDialog;
import com.cesaas.android.counselor.order.net.GetListByShopIdNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 按店铺查询粉丝列表
 * @author FGB
 *
 */
@ContentView(R.layout.activity_get_fans_list_by_shop_id_layout)
public class GetListByShopIdActivity extends BasesActivity{
//
	@ViewInject(R.id.tv_shop_fans_size)
	private TextView tv_shop_fans_size;
	@ViewInject(R.id.il_fans_back)
	private LinearLayout il_fans_back;
	@ViewInject(R.id.iv_fans_search)
	private ImageView iv_fans_search;
	@ViewInject(R.id.iv_vip_search)
	private ImageView iv_vip_search;
	@ViewInject(R.id.et_search)
	private EditText et_search;
	@ViewInject(R.id.load_more_shop_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_shop_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private ArrayList<FansListByShopIdBean> fansListByShopId=new ArrayList<FansListByShopIdBean>();
	private GetListByShopIdNet byShopIdNet;
	private static GetListByShopIdActivity fragment;
	
	private static int pageIndex = 1;//当前页
	private boolean refresh=false;
	
	private String fansId;//粉丝ID
	
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
		
		initItemClickListener();
		initAdapterAndSetData();
		initData();
	}
	
	/**
	 * 初始化Adapter和设置数据
	 */
	public void initAdapterAndSetData(){
		mLoadMoreListView.setAdapter(new CommonAdapter<FansListByShopIdBean>(mContext,R.layout.item_fans_list_by_shop_id,fansListByShopId) {

			@Override
			public void convert(ViewHolder holder, FansListByShopIdBean fans,int postion) {
				if(fans.FANS_GRADE!=null){
					holder.setText(R.id.tv_vip_grid, "会员等级:"+fans.FANS_GRADE);
				}else{
					holder.setText(R.id.tv_vip_grid, "会员等级:"+"暂无");
				}
				holder.setText(R.id.tv_vip_point, "积分:  "+fans.FANS_POINT);
				holder.setText(R.id.fans_nick, fans.FANS_NICKNAME);
				holder.setText(R.id.tv_fans_counselor, "所属顾问:"+fans.COUNSELOR_NAME);
				holder.setCircleImageViewBitmap(R.id.iv_fans_icon, bitmapUtils, fans.FANS_ICON);
			}
		});
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
	 * 监听查看粉丝详情
	 */
	public void initItemClickListener() {
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				if (fansListByShopId.get(position).FANS_ID!=null) {
					bundle.putString("fansId", fansListByShopId.get(position).FANS_ID);				
					Skip.mNextFroData(mActivity, GetShopFansDetailActivity.class,
							bundle);
				}
			}
		});
		
		
		//会员查询
		iv_vip_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(!"".equals(et_search.getText().toString())){
					Bundle bundle=new Bundle();
					bundle.putString("fansMobile", et_search.getText().toString());
					Skip.mNextFroData(mActivity, SerachVipActivity.class,bundle);
				}else{
					ToastFactory.getLongToast(mContext, "请输入会员手机号！");
				}
			}
		});
	}
	
	public void onEventMainThread(ResultGetListByShopIdBean msg) {
		
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				fansListByShopId.addAll(msg.TModel);
				tv_shop_fans_size.setText("("+fansListByShopId.size()+")");
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			fansListByShopId.clear();
		}
		byShopIdNet = new GetListByShopIdNet(mContext);
		byShopIdNet.setData(page,null);

		pageIndex = page;

	}
	
	@OnClick({ R.id.il_fans_back,R.id.iv_fans_search })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.il_fans_back:
			Skip.mBack(mActivity);
			break;
		case R.id.iv_fans_search:
			Skip.mNext(mActivity, SearchDialog.class);
			break;
		}
	}	
}
