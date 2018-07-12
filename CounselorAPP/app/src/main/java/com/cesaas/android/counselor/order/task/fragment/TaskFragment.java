package com.cesaas.android.counselor.order.task.fragment;

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
 * 任务
 * @author FGB
 *
 */
public class TaskFragment extends BaseFragment implements OnClickListener{
	
	private View view;
	private TextView tv_public_task,tv_private_task,tv_complete_task;
	
	private ArrayList<Fragment> fragments;
	private ViewPager viewPager;
	private int line_width;
	private View line;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_task_layout, container,false);
		
		initView();
		initDatas();
		
		return view;
	}
	
	/**
	 * 初始化视图控件
	 */
	private void initView() {
		tv_public_task=(TextView) view.findViewById(R.id.tv_public_task);
		tv_private_task=(TextView) view.findViewById(R.id.tv_private_task);
		tv_complete_task=(TextView) view.findViewById(R.id.tv_complete_task);
		viewPager=(ViewPager) view.findViewById(R.id.vp_task_viewPager);
		line=view.findViewById(R.id.shop_task_lines);
		
		initTextViewAnimator();
	}
	
	/**
	 * 初始化TextView动画
	 */
	private void initTextViewAnimator() {
		ViewPropertyAnimator.animate(tv_public_task).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_public_task).scaleY(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_private_task).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_private_task).scaleY(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_complete_task).scaleX(1.1f).setDuration(0);
		ViewPropertyAnimator.animate(tv_complete_task).scaleY(1.1f).setDuration(0);
		
	}
	
	@SuppressWarnings("deprecation")
	public void initDatas(){
		fragments = new ArrayList<Fragment>();
		fragments.add(new PublicTaskFragment());
		fragments.add(new PrivateTaskFragment());
		fragments.add(new CompleteTaskFragment());
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

		tv_public_task.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(0);
			}
		});
		
		tv_private_task.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				viewPager.setCurrentItem(1);
				//initData();
			}
		});
		
		tv_complete_task.setOnClickListener(new OnClickListener() {

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
			tv_public_task.setTextColor(getResources().getColor(R.color.color_title_bar));
			tv_private_task.setTextColor(getResources().getColor(R.color.black));

		} else {//进来
			tv_private_task.setTextColor(getResources().getColor(R.color.color_title_bar));
			tv_public_task.setTextColor(getResources().getColor(R.color.black));
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
