package com.cesaas.android.counselor.order.ordermange;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerAdapter;
import com.cesaas.android.counselor.order.ordermange.adapter.ShopOrderTabLayoutAdapter;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.view.FinshShopOrderFragment;
import com.cesaas.android.counselor.order.view.NoPayShopOrderFragment;
import com.cesaas.android.counselor.order.view.WaitInShopOrderFragment;
import com.cesaas.android.counselor.order.view.WaitOutShopOrderFragment;

/**
 * 门店订单
 * @author FGB
 *
 */
public class ShopOrderActivity extends BasesActivity{
	
	private LinearLayout ll_shop_order_back;

	TabLayout mTabLayout;
	ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_order);
		
		initView();
		mBack();
		initData();
	}

	private void initData(){
		PagerAdapter adapter= new ShopOrderTabLayoutAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		ll_shop_order_back=(LinearLayout) findViewById(R.id.ll_shop_order_back);
		mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
		mViewPager= (ViewPager) findViewById(R.id.view_pager);

	}
	
	/**
	 * 返回
	 */
	public void mBack(){
		ll_shop_order_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}


	
}
