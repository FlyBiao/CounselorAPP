package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.earnings.fragment.AllEarnings;
import com.cesaas.android.counselor.order.earnings.fragment.CompleteEarnings;
import com.cesaas.android.counselor.order.earnings.fragment.StaySettleEarnings;
import com.lidroid.xutils.view.annotation.ContentView;

/**
 * 我的收益详情页面
 * @author FGB
 *
 */
@ContentView(R.layout.item_earnings_view)
public class EarningsDetailActivity extends BasesActivity{

	private RelativeLayout RelativeLayout1, RelativeLayout2, RelativeLayout3;
	private TextView textView1, textView2, textView3;
	private ViewPager viewPager;
	private int[] selectList;
	private TextView[] textViewList;
	private int selectID = 0;
	
	private List<Fragment> fragments;
	
	private AllEarnings allEarnings;
	private StaySettleEarnings staySettleEarnings;
	private CompleteEarnings completeEarnings;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
		initDatas();
	}
	
	private void initLayout() {
		RelativeLayout1 = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		RelativeLayout2 = (RelativeLayout) findViewById(R.id.RelativeLayout2);
		RelativeLayout3 = (RelativeLayout) findViewById(R.id.RelativeLayout3);

		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		
		fragments = new ArrayList<Fragment>();
		fragments.add(AllEarnings.newInstance());
		fragments.add(StaySettleEarnings.newInstance());
		fragments.add(CompleteEarnings.newInstance());
	}
	
	private void initDatas() {
		selectList = new int[] { 0, 1, 1 };// 0表示选中，1表示未选中(默认第一个选中)
		textViewList = new TextView[] { textView1, textView2, textView3 };

		RelativeLayout1.setOnClickListener(listener);
		RelativeLayout2.setOnClickListener(listener);
		RelativeLayout3.setOnClickListener(listener);
		
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(changeListener);
	}
	
	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.RelativeLayout1:
				if (selectID == 0) {
					return;
				} else {
					setSelectedTitle(0);
					viewPager.setCurrentItem(0);
				}
				break;
			case R.id.RelativeLayout2:
				if (selectID == 1) {
					return;
				} else {
					setSelectedTitle(1);
					viewPager.setCurrentItem(1);
				}
				break;
			case R.id.RelativeLayout3:
				if (selectID == 2) {
					return;
				} else {
					setSelectedTitle(2);
					viewPager.setCurrentItem(2);
				}
				break;
			}
		}
	};
	
	private FragmentPagerAdapter adapter = new FragmentPagerAdapter(
			getSupportFragmentManager()) {

		public int getCount() {
			return selectList.length;
		}
		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		/*@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(selectList[position]);
		}

		
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(selectList[position], zero);
			return selectList[position];
		}*/

		public Fragment getItem(int position) {
			return fragments.get(position);
		}
	};
	
	/**
	 * 当前UI改变时，修改TITLE选中项
	 * 
	 * @param position
	 *            zero 1 2
	 */
	private SimpleOnPageChangeListener changeListener=new SimpleOnPageChangeListener(){
		public void onPageSelected(int position) {
			setSelectedTitle(position);
		}
	};
	
	private void setSelectedTitle(int position) {
		for (int i = 0; i < selectList.length; i++) {
			if (selectList[i] == 0) {
				selectList[i] = 1;
				textViewList[i].setVisibility(View.INVISIBLE);
			}
		}
		selectList[position] = 0;
		textViewList[position].setVisibility(View.VISIBLE);
		selectID = position;
	}
}
