package com.cesaas.android.counselor.order.custom.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.salestalk.net.GetListNet;
import com.cesaas.android.counselor.order.shoptask.fragment.NotHandleShopTaskFragment;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;



public class VpSimpleFragment extends Fragment {
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

	private String categoryId;//categoryId
	private String categoryName;
	private GetListNet getListNet;
	private List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> categoryListBean;

	private View view;

	//fragment一般使用newInstance方法new出实例
	public static VpSimpleFragment newInstance(String id,String categoryName) {
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		bundle.putString("categoryName",categoryName);

		VpSimpleFragment fragment = new VpSimpleFragment();
		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.select_verbaltrick_layout, container, false);

		mSwipeRefreshLayout = (SwipeRefreshLayout)view. findViewById(R.id.swipe_layout);
		mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_shop_task_view);
		mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
		mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
		mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
		mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
		// 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

		mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);



        Bundle bundle = getArguments();
		if(bundle != null){
			categoryId="";
			categoryName="";
			categoryId = bundle.getString("id");
			categoryName=bundle.getString("categoryName");

			getListNet=new GetListNet(getContext());
			getListNet.setData(Integer.parseInt(bundle.getString("id")),1);
			Log.i(Constant.TAG,"============================="+bundle.getString("id")+"===="+bundle.getString("categoryName"));

		}

		return view;
	}

	/**
	 * 刷新监听。
	 */
	private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh() {
			mSwipeMenuRecyclerView.postDelayed(new Runnable() {
				@Override
				public void run() {
					//重新加载数据
					getListNet=new GetListNet(getContext());
					getListNet.setData(Integer.parseInt(categoryId),1);

					mSwipeRefreshLayout.setRefreshing(false);
				}
			}, 2000);
		}
	};


	/**
	 * 获取分类下的话术列表
	 * @author fgb
	 *
	 */
	public class GetListNet extends BaseNet {

		public GetListNet(Context context) {
			super(context, true);
			this.uri="User/Sw/SalesTalk/GetList";
		}

		public void setData(int categoryId,int page){
			try {
				data.put("CategoryId", categoryId);
				data.put("PageIndex", page);
				data.put("PageSize", 20);
				data.put("UserTicket",
						AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mPostNet();
		}

		@Override
		protected void mSuccess(String rJson) {
			super.mSuccess(rJson);
			Gson gson=new Gson();
			ResultSalesTalkCategoryListBean msg=gson.fromJson(rJson, ResultSalesTalkCategoryListBean.class);
			if(msg.IsSuccess==true){
				categoryListBean=new ArrayList<>();
				categoryListBean.addAll(msg.TModel);
				for (int i=0;i<categoryListBean.size();i++){
					Log.i(Constant.TAG,"test:"+categoryListBean.get(i).Content);
				}
			}
			else{
				ToastFactory.getLongToast(getContext(), msg.Message);
			}
		}

		@Override
		protected void mFail(HttpException e, String err) {
			super.mFail(e, err);
			Log.i(Constant.TAG, "GetListNet=error:"+err);
		}

	}
}
