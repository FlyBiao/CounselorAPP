package com.cesaas.android.counselor.order.stafftest;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * 店员考试列表页面
 * @author fgb
 *
 */
public class StaffTestListFragment extends BaseFragment implements OnClickListener{
	
	private View view;
	
	private TextView tv_not_test,tv_test_ing,tv_test_complete;
	
	private ArrayList<Fragment> fragments;
	private ViewPager viewPager;
	private int line_width;
	private View line;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_staff_test_list, container,false);
		
		initView ();
		initDatas();
		
		return view;
	}

	private void initView() {
		tv_not_test=(TextView) view.findViewById(R.id.tv_not_test);
		tv_test_ing=(TextView) view.findViewById(R.id.tv_test_ing);
		tv_test_complete=(TextView) view.findViewById(R.id.tv_test_complete);
		viewPager=(ViewPager)view.findViewById(R.id.vp_test_viewPager);
		line=view.findViewById(R.id.shop_test_lines);
		
		tv_not_test.setOnClickListener(this);
		tv_test_ing.setOnClickListener(this);
		tv_test_complete.setOnClickListener(this);
		
		initTextViewAnimator();
	}
	
	/**
	 * 初始化TextView动画
	 */
	private void initTextViewAnimator() {
		ViewPropertyAnimator.animate(tv_not_test).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_not_test).scaleY(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_test_ing).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_test_ing).scaleY(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_test_complete).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_test_complete).scaleY(1.1f).setDuration(0);
		
	}
	

	@SuppressWarnings("deprecation")
	public void initDatas(){
		fragments = new ArrayList<Fragment>();
		fragments.add(new NotTestFragment());
		fragments.add(new TestIngFragment());
		fragments.add(new CompleteTestFragment());
		line_width = getActivity().getWindowManager().getDefaultDisplay().getWidth()
				/ fragments.size();
		line.getLayoutParams().width = line_width;
		line.requestLayout();

		viewPager.setAdapter(new FragmentStatePagerAdapter(
				getFragmentManager()) {

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

		tv_not_test.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});
		
		tv_test_ing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
				//initData();
			}
		});
		
		tv_test_complete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(2);
				//initData();
			}
		});
	}
	
	/* 根据传入的值来改变状态 */
	private void changeState(int arg0) {
		if (arg0 == 0) {//出去
			tv_not_test.setTextColor(getResources().getColor(R.color.color_title_bar));
			tv_test_ing.setTextColor(getResources().getColor(R.color.black));
			tv_test_complete.setTextColor(getResources().getColor(R.color.black));

		}else if(arg0==1){
			tv_test_ing.setTextColor(getResources().getColor(R.color.color_title_bar));
			tv_not_test.setTextColor(getResources().getColor(R.color.black));
			tv_test_complete.setTextColor(getResources().getColor(R.color.black));
			
		} else {//进来
			tv_test_complete.setTextColor(getResources().getColor(R.color.color_title_bar));
			tv_test_ing.setTextColor(getResources().getColor(R.color.black));
			tv_not_test.setTextColor(getResources().getColor(R.color.black));
		}
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		}
	}
	
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
	
}
