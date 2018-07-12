package com.cesaas.android.counselor.order.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.nineoldandroids.view.ViewPropertyAnimator;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;

public class OrderFragment extends BaseFragment{
	
	private View view;
	private ArrayList<Fragment> fragments;

	private ViewPager viewPager;

	private TextView tab_game;

	private TextView tab_app;

	private int line_width;

	private View line;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.viewpager_layout, container,false);
		tab_game = (TextView)view. findViewById(R.id.tab_game);
		tab_app = (TextView) view.findViewById(R.id.tab_app);
		line = view.findViewById(R.id.line);
		
		initTextViewAnimator();
		initDatas();
		
		return view;
	}
	
	/**
	 * 初始化TextView动画
	 */
	public void initTextViewAnimator(){
		ViewPropertyAnimator.animate(tab_app).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_app).scaleY(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_game).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tab_game).scaleY(1.1f).setDuration(0);
	}
	
	@SuppressWarnings("deprecation")
	public void initDatas(){
		fragments = new ArrayList<Fragment>();
		fragments.add(new ReceiveOrderFragment());
		fragments.add(new SendOrderFragment());
		line_width = getActivity().getWindowManager().getDefaultDisplay().getWidth()
				/ fragments.size();
		line.getLayoutParams().width = line_width;
		line.requestLayout();

		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
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

		tab_game.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
				//initData();
			}
		});

		tab_app.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});
	}
	
	/* 根据传入的值来改变状态 */
	private void changeState(int arg0) {
		if (arg0 == 0) {
			tab_app.setTextColor(getResources().getColor(R.color.white));
			tab_game.setTextColor(getResources().getColor(R.color.white));
//			ViewPropertyAnimator.animate(tab_app).scaleX(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_app).scaleY(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleX(1.2f)
//					.setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleY(1.2f)
//					.setDuration(200);

		} else {
			tab_game.setTextColor(getResources().getColor(R.color.white));
			tab_app.setTextColor(getResources().getColor(R.color.white));
//			ViewPropertyAnimator.animate(tab_app).scaleX(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_app).scaleY(1.2f).setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleX(1.2f)
//					.setDuration(200);
//			ViewPropertyAnimator.animate(tab_game).scaleY(1.2f)
//					.setDuration(200);
		}
	}
	
	

	@Override
	protected void lazyLoad() {
		
	}

}
