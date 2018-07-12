package com.cesaas.android.counselor.order.fans.fragment;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

import java.util.ArrayList;

import android.net.Uri;
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
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean.FansListByShopIdBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.net.GetListByShopIdNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 店铺粉丝
 * @author FGB
 *
 */
public class ShopFansFragment extends BaseFragment implements RongIM.UserInfoProvider{
	
	@ViewInject(R.id.load_more_shop_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_shop_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private ArrayList<FansListByShopIdBean> fansListByShopId=new ArrayList<FansListByShopIdBean>();
	private GetListByShopIdNet byShopIdNet;
	
	private UserInfoNet userInfoNet;
	private UserBean userBean;
	
	private static int pageIndex = 1;//当前页
	private boolean refresh=false;
	
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.activity_shop_fans_layout,container, false);
		ViewUtils.inject(this, view);
		
		return view;
	}
	
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		userInfoNet=new UserInfoNet(getContext());
		userInfoNet.setData();
		
		initAdapterAndSetData();
		initData();
		initSession();
	}
	
	public void onEventMainThread(ResultUserBean msg) {
		userBean=msg.TModel;
	}
	
	/**
	 * 初始化Adapter和设置数据
	 */
	public void initAdapterAndSetData(){
		mLoadMoreListView.setAdapter(new CommonAdapter<FansListByShopIdBean>(getContext(),R.layout.item_manage_fans,fansListByShopId) {
			@Override
			public void convert(ViewHolder holder, FansListByShopIdBean fans,int postion) {
				holder.setText(R.id.tv_manage_fans_nick, fans.FANS_NICKNAME);
				holder.setText(R.id.tv_manage_fans_mobile,fans.FANS_MOBILE);
				holder.setCircleImageViewBitmap(R.id.iv_manage_fans_img, bitmapUtils, fans.FANS_ICON);
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
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			fansListByShopId.clear();
		}
		byShopIdNet = new GetListByShopIdNet(getContext());
		byShopIdNet.setData(page,null);

		pageIndex = page;

	}
	
	public void initSession(){
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				
				//启动单聊会话界面
            	if (RongIM.getInstance() != null)
            	   RongIM.getInstance().startPrivateChat(getActivity(), 
            			   fansListByShopId.get(position).FANS_ID, fansListByShopId.get(position).FANS_NICKNAME);
            	//刷新用户信息头像
            	RongIM.getInstance().refreshUserInfoCache(new UserInfo(userBean.VipId+"","",Uri.parse(userBean.Icon)));
            	RongIM.getInstance().setMessageAttachedUserInfo(true);
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
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public UserInfo getUserInfo(String s) {
		for(FansListByShopIdBean f:fansListByShopId){
			if(f.equals(s)){
				return new UserInfo(f.FANS_ID,f.FANS_NICKNAME, Uri.parse(f.FANS_ICON));
			}
		}
		return null;
	}

}
