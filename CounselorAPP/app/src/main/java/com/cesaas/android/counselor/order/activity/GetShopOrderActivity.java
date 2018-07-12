package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

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
import com.cesaas.android.counselor.order.bean.ResultGetShopOrderBean;
import com.cesaas.android.counselor.order.bean.ResultGetShopOrderBean.GetShopOrderBean;
import com.cesaas.android.counselor.order.net.GetShopOrderNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 店铺订单页面
 * @author fgb
 *
 */
@ContentView(R.layout.activity_get_shop_order_layout)
public class GetShopOrderActivity extends BasesActivity{

	@ViewInject(R.id.tv_shop_order_no_data)
	private TextView tv_shop_order_no_data;
	@ViewInject(R.id.iv_shop_back)
	private LinearLayout iv_shop_back;
	@ViewInject(R.id.load_more_shoporder_list)
	private LoadMoreListView mLoadMoreListView;//加载更多
	@ViewInject(R.id.refresh_shoporder_and_load_more)
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private static GetShopOrderActivity fragment;
	private GetShopOrderNet orderNet;
	private ArrayList<GetShopOrderBean> shopOrderList= new ArrayList<GetShopOrderBean>();
	
	private static int pageIndex = 1;//当前页
	private boolean refresh=false;
	
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
		
		initAdapterAndSetDate();
		initOnItemClick();
		initData();
		initBack();
	}
	
	/**
	 * 初始化Adapter和设置数据
	 */
	public void initAdapterAndSetDate(){
		
		mLoadMoreListView.setAdapter(new CommonAdapter<GetShopOrderBean>(mContext,R.layout.item_shop_order,shopOrderList) {

			@Override
			public void convert(ViewHolder holder, GetShopOrderBean bean,int postion) {
				holder.setText(R.id.tv_shop_order_number, "订单号:"+bean.TradeId);
				holder.setText(R.id.tv_shop_order_create_date, "下单时间:" + bean.CreateDate);
//				
				for (int i = 0; i < bean.OrderItem.size(); i++) {
					holder.setText(R.id.tv_shop_order_title, bean.OrderItem.get(i).Title);
					holder.setText(R.id.tv_shop_order_attr, bean.OrderItem.get(i).Attr);
					holder.setText(R.id.tv_shop_order_quantity, "x"+bean.OrderItem.get(i).Quantity);
					holder.setImageBitmapUtils(R.id.iv_shop_order_img,bitmapUtils, bean.OrderItem.get(i).ImageUrl);
				}
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
	
	public void onEventMainThread(ResultGetShopOrderBean msg) {
		if (msg != null) {
			if (msg.TModel.size() > 0&&msg.TModel.size()==20) {				
				mLoadMoreListView.setHaveMoreData(true);
			} else {
				mLoadMoreListView.setHaveMoreData(false);
			}
			if(msg.TModel.size()!=0){
				shopOrderList.addAll(msg.TModel);
				/**
				 * 按照时间排序显示
				 */
				Collections.sort(shopOrderList, new Comparator<GetShopOrderBean>() {
					@Override
					public int compare(GetShopOrderBean lhs, GetShopOrderBean rhs) {
						
						Date date0=null;
						Date date1=null;
						for (int i = 0; i < shopOrderList.size(); i++) {
							date0=AbDateUtil.stringToDate(shopOrderList.get(i).CreateDate);
							date1=AbDateUtil.stringToDate(shopOrderList.get(i).CreateDate);
						}
						
						// 对日期字段进行升序，如果欲降序可采用after方法  
		                if (date0.after(date1)) {  
		                	
		                    return 1;  
		                }  
		                
		                return -1;
					}
				});
				
			}
			mRefreshAndLoadMoreView.setRefreshing(false);
			mLoadMoreListView.onLoadComplete();
		}
	}
	
	/**
	 * ListView Item点击事件监听
	 */
	public void initOnItemClick(){
		mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("TradeId", shopOrderList.get(position).TradeId);
				Skip.mNextFroData(mActivity, OrderDetailActivity.class, bundle);
			}
		});
	}
	
	/**
	 * 加载数据
	 * @param page 当前页
	 */
	protected void loadData(final int page) {

		if (page == 1) {
			shopOrderList.clear();
		}
		orderNet = new GetShopOrderNet(mContext);
		orderNet.setData(page,30);

		pageIndex = page;

	}
	
	/**
	 * 返回上一个页面
	 */
	public void initBack(){
		iv_shop_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
