package com.cesaas.android.counselor.order.member.service;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.member.adapter.MemberServiceSelectAdapter;
import com.cesaas.android.counselor.order.member.bean.service.MemberServiceSearchEventBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultCounselorListBean;
import com.cesaas.android.counselor.order.member.fragment.service.fest.MemberServiceFestFragment;
import com.cesaas.android.counselor.order.member.net.service.CounselorListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 会员服务信息-节日安排
 * @author FGB
 *
 */

@ContentView(R.layout.activity_member_service_info_layout)
public class MemberServiceFestInfoActivity extends BasesActivity implements OnClickListener{

	private TextView tvTitle,tv_remove,tv_sure;
	private LinearLayout llBack,ll_search;
	private EditText et_search_content;
	private DrawerLayout mDrawerLayout;
	private NavigationView mNavigationView;
	private View headerView;
	private RecyclerView mRecyclerView;

	private MemberServiceSelectAdapter adapter;
	private List<ResultCounselorListBean.CounselorListBean> mData=new ArrayList<>();
	private CounselorListNet counselorListNet;

	private int tabType=10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initClickListener();
		initData();
	}

	public void onEventMainThread(BaseTabBean msg) {
		tabType=msg.getTabType();
		switch (tabType){
			case 100:
				//展开右侧菜单筛选条件
				mDrawerLayout.openDrawer(Gravity.RIGHT);
				counselorListNet=new CounselorListNet(mContext);
				counselorListNet.setData();
				break;
			case 10://待处理
				break;
			case 20://已完成
				break;
		}
	}

	public void onEventMainThread(ResultCounselorListBean msg) {
		if(msg.IsSuccess!=false){
			if(msg.TModel!=null && msg.TModel.size()!=0){
				mData=new ArrayList<>();
				mData.addAll(msg.TModel);
				adapter=new MemberServiceSelectAdapter(mData);
				mRecyclerView.setAdapter(adapter);
			}else{

			}
		}else{
			ToastFactory.getLongToast(mContext,"获取店铺导购失败:"+msg.Message);
		}
	}

	private void initData(){
		getSupportFragmentManager().beginTransaction().add(R.id.member_service_frame_layout, new MemberServiceFestFragment()).commit();
	}
	private void initClickListener(){
		llBack.setOnClickListener(this);
	}
	private void initView() {
		ll_search= (LinearLayout) findViewById(R.id.ll_search);
		ll_search.setOnClickListener(this);
		et_search_content= (EditText) findViewById(R.id.et_search_content);
		et_search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					MemberServiceSearchEventBean bean=new MemberServiceSearchEventBean();
					bean.setSearchValue(et_search_content.getText().toString());
					bean.setType(tabType);
					EventBus.getDefault().post(bean);
				}
				return false;
			}
		});
		tvTitle= (TextView) findViewById(R.id.tv_base_title);
		tvTitle.setText("节日安排");
		llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
		mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
		mNavigationView= (NavigationView) findViewById(R.id.navigation_view);
		initDrawerLayout();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ll_base_title_back:
				Skip.mBack(mActivity);
				break;
			case R.id.ll_search:
				if(!TextUtils.isEmpty(et_search_content.getText().toString())){
					MemberServiceSearchEventBean bean=new MemberServiceSearchEventBean();
					bean.setSearchValue(et_search_content.getText().toString());
					bean.setType(tabType);
					EventBus.getDefault().post(bean);
				}else{
					ToastFactory.getLongToast(mContext,"请输入搜索内容！");
				}
				break;
		}
	}

	private void initDrawerLayout() {
		headerView = mNavigationView.getHeaderView(0);
		tv_sure= (TextView) headerView. findViewById(R.id.tv_sure);
		tv_remove= (TextView) headerView. findViewById(R.id.tv_remove);
		tv_remove.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
			}
		});
		tv_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.closeDrawers();
			}
		});
		mRecyclerView= (RecyclerView) headerView.findViewById(R.id.rv_list);
		mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
	}
}
