package com.cesaas.android.counselor.order.activity;

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
import com.cesaas.android.counselor.order.fans.fragment.ShopFansFragment;
import com.cesaas.android.counselor.order.fragment.ShopOrderFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * 店铺管理
 * 
 * @author fgb
 * 
 */
@ContentView(R.layout.vp_shop_indicator)
public class ShopManageActvity extends BasesActivity  {

	private View view;
	private ArrayList<Fragment> fragments;

	private ViewPager viewPager;

	private TextView tab_game;

	private TextView tab_app;
	private LinearLayout il_send_back;

	private int line_width;

	private View line;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tab_game = (TextView) findViewById(R.id.shop_tab_game);
		tab_app = (TextView) findViewById(R.id.shop_tab_app);
		il_send_back=(LinearLayout) findViewById(R.id.il_send_back);
		line = findViewById(R.id.shop_line);
		//initData();
		mBack();

		// 初始化TextView动画
		ViewPropertyAnimator.animate(tab_app).scaleX(1.2f).setDuration(0);
		ViewPropertyAnimator.animate(tab_app).scaleY(1.2f).setDuration(0);
		ViewPropertyAnimator.animate(tab_game).scaleX(1.2f).setDuration(0);
		ViewPropertyAnimator.animate(tab_game).scaleY(1.2f).setDuration(0);

		fragments = new ArrayList<Fragment>();
		fragments.add(new ShopFansFragment());
		fragments.add(new ShopOrderFragment());
		line_width = getWindowManager().getDefaultDisplay().getWidth()
				/ fragments.size();
		line.getLayoutParams().width = line_width;
		line.requestLayout();

		viewPager = (ViewPager) findViewById(R.id.shop_viewPager);
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
	
	
	public void mBack(){
		il_send_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}
	
	
}
