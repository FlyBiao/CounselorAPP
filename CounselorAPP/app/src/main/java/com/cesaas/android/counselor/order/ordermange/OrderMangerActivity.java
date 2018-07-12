package com.cesaas.android.counselor.order.ordermange;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.fragment.ReceiveOrderFragment;
import com.cesaas.android.counselor.order.fragment.SendOrderFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * 订单管理页面
 * @author FGB
 *
 */
public class OrderMangerActivity extends BasesActivity{
	
	private ArrayList<Fragment> fragments;

	private ViewPager viewPager;
	
	private LinearLayout ll_order_manger_back;

	private TextView tab_receive_order;
	private TextView tab_ongoing_order;
	private TextView tv_order_manger_title;

	private int line_width;
	private View line;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_manger_layout);
		
		initView();
		initDatas();
	}
	
	public void initView(){
		ll_order_manger_back=(LinearLayout) findViewById(R.id.ll_order_manger_back);
		tab_receive_order=(TextView) findViewById(R.id.tab_receive_order);
		tab_ongoing_order=(TextView) findViewById(R.id.tab_ongoing_order);
		tv_order_manger_title=(TextView) findViewById(R.id.tv_order_manger_title);
		viewPager=(ViewPager) findViewById(R.id.order_manger_viewPager);
		line=findViewById(R.id.order_manger_line);
		
		initTextViewAnimator();
		//返回店铺页面
		ll_order_manger_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}
	
	/**
	 * 初始化TextView动画
	 */
	public void initTextViewAnimator(){
		ViewPropertyAnimator.animate(tab_receive_order).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_receive_order).scaleY(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_ongoing_order).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_ongoing_order).scaleY(1.1f).setDuration(0);
	}
	
	
	@SuppressWarnings("deprecation")
	public void initDatas(){
		fragments = new ArrayList<Fragment>();
		fragments.add(new ReceiveOrderFragment());
		fragments.add(new SendOrderFragment());
		line_width = mActivity.getWindowManager().getDefaultDisplay().getWidth()
				/ fragments.size();
		line.getLayoutParams().width = line_width;
		line.requestLayout();

		viewPager.setAdapter(new FragmentStatePagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				changeState(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				float tagerX = arg0 * line_width + arg2 / fragments.size();
				ViewPropertyAnimator.animate(line).translationX(tagerX)
						.setDuration(0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		tab_ongoing_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
				//initData();
			}
		});

		tab_receive_order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});
	}
	
	/* 根据传入的值来改变状态 */
	private void changeState(int arg0) {
		if (arg0 == 0) {//出去
			tab_receive_order.setTextColor(getResources().getColor(R.color.color_title_bar));
			tab_ongoing_order.setTextColor(getResources().getColor(R.color.black));

		} else {//进来
			tab_ongoing_order.setTextColor(getResources().getColor(R.color.color_title_bar));
			tab_receive_order.setTextColor(getResources().getColor(R.color.black));
		}
	}
}
