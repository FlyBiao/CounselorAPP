package com.cesaas.android.counselor.order.view.search;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.GetShopFansDetailActivity;
import com.cesaas.android.counselor.order.adapter.SearchListByShopIdAdapter;
import com.cesaas.android.counselor.order.base.BaseSearchView;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean;
import com.cesaas.android.counselor.order.bean.ResultGetListByShopIdBean.FansListByShopIdBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.GetListByShopIdNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.MyRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

/**
 * 按店铺粉丝查询
 * @author fgb
 *
 */
public class SearchShopFansView extends BaseSearchView {

	private GetListByShopIdNet byShopIdNet;
	private SearchListByShopIdAdapter byShopIdAdapter;
	private ArrayList<FansListByShopIdBean> fansListByShopId=new ArrayList<FansListByShopIdBean>();
	
	@ViewInject(R.id.myrefresh_shopfans)
	private MyRefreshLayout myrefresh;
	@ViewInject(R.id.ll_shopfans_listview)
	private ListView ll_listview;
	@ViewInject(R.id.tv_shopfans_nodata)
	private TextView nodata;
	
	private String keyword;//查询关键字
	private static int pageIndex = 1;//当前页
	
	public SearchShopFansView(Context context) {
		super(context);
		View view = LayoutInflater.from(context).inflate(R.layout.listview_refresh_layout, this);
		ViewUtils.inject(this, view);
		byShopIdNet=new GetListByShopIdNet(mContext);
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		byShopIdAdapter = new SearchListByShopIdAdapter(mContext,fansListByShopId,ll_listview);
		ll_listview.setAdapter(byShopIdAdapter);
		ll_listview.setEmptyView(nodata);
		myrefresh.setOnRefreshListener(this);
		myrefresh.setOnLoadListener(this);
	}

	/**
	 * 点击ListView Item事件
	 * @param parent
	 * @param view
	 * @param position
	 * @param id
	 */
	@OnItemClick(R.id.ll_shopfans_listview)
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Bundle bundle = new Bundle();
		bundle.putString("fansId", fansListByShopId.get(position).FANS_ID);
		Skip.mNextFroData((Activity)mContext, GetShopFansDetailActivity.class,
				bundle);
	}
	
	/**
	 *查询请求
	 */
	@Override
	public void mRequest(String key) {
		super.mRequest(key);
		this.keyword = key;
		byShopIdNet.setData(pageIndex,key);
	}
	
	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		super.onRefresh();
		myrefresh.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pageIndex = 1;
				byShopIdNet.setData(pageIndex,keyword);
				myrefresh.setRefreshing(false);
			}
		}, Constant.TOPSHOWTIME);
	}

	/**
	 * 上拉加载更多
	 */
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		super.onLoad();
		myrefresh.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				++pageIndex;
				byShopIdNet.setData(pageIndex,keyword);
				myrefresh.setLoading(false);
			}
		}, Constant.BOTTOMSHOWTIME);
	}
	
	public void onEventMainThread(ResultGetListByShopIdBean msg) {
		if (msg.TModel.size() > 0) {
			fansListByShopId.addAll(msg.TModel);
			byShopIdAdapter.clear();
			byShopIdAdapter.addAll(fansListByShopId);
		}
	}
}
