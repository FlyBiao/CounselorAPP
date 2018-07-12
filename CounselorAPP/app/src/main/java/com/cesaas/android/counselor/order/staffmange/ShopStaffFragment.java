package com.cesaas.android.counselor.order.staffmange;

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
import com.cesaas.android.counselor.order.bean.ResultShopStaffBean;
import com.cesaas.android.counselor.order.bean.ResultShopStaffBean.ShopStaffBean;
import com.cesaas.android.counselor.order.net.ShopStaffListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 店员管理FrAGMENT
 * @author fgb
 *
 */
public class ShopStaffFragment extends BaseFragment {
	
	private View view;
	
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private ArrayList<ShopStaffBean> shopStaffList=new ArrayList<ShopStaffBean>();
	private ShopStaffListNet listNet;
	
	private static int pageIndex = 1;//当前页
	private boolean refresh=false;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_shop_staff_list, container,false);
		initView();
		initAdapterAndSetData();
		return view;
	}
	

	private void initView() {
		mLoadMoreListView=(LoadMoreListView) view.findViewById(R.id.load_shop_staff_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) view.findViewById(R.id.refresh_shop_staff_list);
		
	}

	/**
	 * ListView Item监听
	 */
	public void initItemClickListener(){
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putString("Name", shopStaffList.get(position).COUNSELOR_NAME);
				bundle.putString("Nick", shopStaffList.get(position).COUNSELOR_NICKNAME);
				bundle.putString("ShopName", shopStaffList.get(position).SHOP_NAME);
				bundle.putString("Mobile", shopStaffList.get(position).COUNSELOR_MOBILE);
				bundle.putString("Sex", shopStaffList.get(position).COUNSELOR_SEX);
				bundle.putInt("ShopId", shopStaffList.get(position).SHOP_ID);
				bundle.putString("Icon", shopStaffList.get(position).COUNSELOR_ICON);
				bundle.putInt("Type", shopStaffList.get(position).COUNSELOR_TYPE);
				bundle.putInt("counselorId", shopStaffList.get(position).COUNSELOR_ID);
				Skip.mNextFroData(getActivity(), ShopStaffDetailActivity.class,bundle);
			}
		});
	}
	
	/**
	 * 初始化Adapter和设置数据
	 */
	public void initAdapterAndSetData(){
		mLoadMoreListView.setAdapter(new CommonAdapter<ShopStaffBean>(getContext(),R.layout.item_shop_staff,shopStaffList) {

			@Override
			public void convert(ViewHolder holder, ShopStaffBean bean,int postion) {
				holder.setText(R.id.tv_shop_staff_nick, bean.COUNSELOR_NAME);
				holder.setText(R.id.tv_shop_staff_mobile,bean.COUNSELOR_MOBILE);
				holder.setCircleImageViewBitmap(R.id.iv_shop_staff_img, bitmapUtils, bean.COUNSELOR_ICON);
				if(bean.COUNSELOR_INUSE==0){//未生效
					holder.getView(R.id.tv_shopstaff_status).setVisibility(View.VISIBLE);
				}else{
					holder.getView(R.id.tv_shopstaff_status).setVisibility(View.GONE);
				}
			}
		});
		
		initData();
		initItemClickListener();
	}
	
	public void onEventMainThread(ResultShopStaffBean msg) {
		
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				shopStaffList.addAll(msg.TModel);
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
			shopStaffList.clear();
		}
		listNet=new ShopStaffListNet(getContext());
		listNet.setData(page);

		pageIndex = page;

	}

	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
