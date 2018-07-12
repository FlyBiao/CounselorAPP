package com.cesaas.android.counselor.order.staffmange;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetSubMenuPowerBean;
import com.cesaas.android.counselor.order.bean.ResultShopStaffBean;
import com.cesaas.android.counselor.order.bean.ResultShopStaffBean.ShopStaffBean;
import com.cesaas.android.counselor.order.net.GetSubMenuPowerNet;
import com.cesaas.android.counselor.order.net.ShopStaffListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 店员管理
 * @author FGB
 *
 */
public class ShopStaffMangerActivity extends BasesActivity implements OnClickListener{
	
	private Button btn_add_staff;
	private LinearLayout ll_staff_list_back;
	private LoadMoreListView mLoadMoreListView;//加载更多
	private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
	
	private ArrayList<ShopStaffBean> shopStaffList=new ArrayList<ShopStaffBean>();
	private ShopStaffListNet listNet;
	
	private static int pageIndex = 1;//当前页
	private boolean refresh=false;
	
	private String aPPShopStaffManger;
	//子菜单
//	private GetSubMenuPowerNet getSubMenuPowerNet;
	
	private static ShopStaffMangerActivity fragment;
	
	public static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			pageIndex=1;
			fragment.loadData(pageIndex);
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_staff);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			aPPShopStaffManger=bundle.getString("APP_Shop_staff");
		}
		
//		getSubMenuPowerNet=new GetSubMenuPowerNet(mContext);
//		getSubMenuPowerNet.setData();
		
		fragment=this;
		
		initView();
		
		initAdapterAndSetData();
		initData();
		initItemClickListener();
	}
	
	/**
	 * 接收子菜单POS消息
	 * @param msg 消息实体类
	 */
//	public void onEventMainThread(ResultGetSubMenuPowerBean msg) {
//		
//		if(msg.TModel!=null){
//			for (int i = zero; i < msg.TModel.size(); i++) {
//				if(aPPShopStaffManger.equals(msg.TModel.get(i).getMENU_PARENTNO())){
//					Log.i("Menus", msg.TModel.get(i).getMENU_NAME());
//				}
//			}
//		}
//		
//	}
	
	private void initView(){
		
		mLoadMoreListView=(LoadMoreListView) findViewById(R.id.load_shop_staff_list);
		mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) findViewById(R.id.refresh_shop_staff_list);
		
		btn_add_staff=(Button) findViewById(R.id.btn_add_staff);
		ll_staff_list_back=(LinearLayout) findViewById(R.id.ll_staff_list_back);
		
		btn_add_staff.setOnClickListener(this);
		ll_staff_list_back.setOnClickListener(this);
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
				Skip.mNextFroData(mActivity, ShopStaffDetailActivity.class,bundle);
			}
		});
	}
	
	/**
	 * 初始化Adapter和设置数据
	 */
	public void initAdapterAndSetData(){
		mLoadMoreListView.setAdapter(new CommonAdapter<ShopStaffBean>(mContext,R.layout.item_shop_staff,shopStaffList) {

			@Override
			public void convert(ViewHolder holder, ShopStaffBean bean,int postion) {
				holder.setText(R.id.tv_shop_staff_nick, bean.COUNSELOR_NAME);
				holder.setText(R.id.tv_shop_staff_mobile,bean.COUNSELOR_MOBILE);
				holder.setCircleImageViewBitmap(R.id.iv_shop_staff_img, bitmapUtils, bean.COUNSELOR_ICON);
//				if(bean.COUNSELOR_INUSE==zero){//未生效
//					holder.getView(R.id.tv_shopstaff_status).setVisibility(View.VISIBLE);
//				}else{
//					holder.getView(R.id.tv_shopstaff_status).setVisibility(View.GONE);
//				}
			}
		});
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
		listNet=new ShopStaffListNet(mContext);
		listNet.setData(page);

		pageIndex = page;

	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_staff://新增店员
			Skip.mNext(mActivity, AddShopStaffActivity.class);
			break;
			
		case R.id.ll_staff_list_back://返回
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
	}
}