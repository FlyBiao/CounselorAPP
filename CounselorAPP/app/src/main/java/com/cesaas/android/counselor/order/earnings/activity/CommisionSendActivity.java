package com.cesaas.android.counselor.order.earnings.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.earnings.fragment.AllEarnings;
import com.cesaas.android.counselor.order.earnings.fragment.CompleteEarnings;
import com.cesaas.android.counselor.order.earnings.fragment.StaySettleEarnings;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;

/**
 * 发货佣金
 * @author FGB
 *
 */
@ContentView(R.layout.activity_commision_send_layout)
public class CommisionSendActivity extends BasesActivity implements OnClickListener{

	private RelativeLayout rl_commision_send_all, rl_commision_send_settlement, rl_commision_send_receive;
	private TextView tv_commision_all, tv_commision_send_settlement, tv_commision_send_receive;
	private ImageView iv_back_send;
	
	private ViewPager viewPager;
	private int[] selectList;
	private TextView[] textViewList;
	private int selectID = 0;
	
	private List<Fragment> fragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initViews();
		initDatas();
	}
	
	/**
	 * 初始化控件
	 */
	public void initViews(){
		rl_commision_send_all=(RelativeLayout) findViewById(R.id.rl_commision_send_all);
		rl_commision_send_settlement=(RelativeLayout) findViewById(R.id.rl_commision_send_settlement);
		rl_commision_send_receive=(RelativeLayout) findViewById(R.id.rl_commision_send_receive);
		
		tv_commision_all=(TextView)findViewById(R.id.tv_commision_all);
		tv_commision_send_settlement=(TextView)findViewById(R.id.tv_commision_send_settlement);
		tv_commision_send_receive=(TextView)findViewById(R.id.tv_commision_send_receive);
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		
		iv_back_send=(ImageView) findViewById(R.id.iv_back_send);
		iv_back_send.setOnClickListener(this);
		
		//初始化Fragment
		fragments = new ArrayList<Fragment>();
		fragments.add(AllEarnings.newInstance());
		fragments.add(StaySettleEarnings.newInstance());
		fragments.add(CompleteEarnings.newInstance());
		
	}
	
	/**
	 * 初始化数据
	 */
	private void initDatas(){
		selectList = new int[] { 0, 1, 1 };// 0表示选中，1表示未选中(默认第一个选中)
		textViewList = new TextView[] { tv_commision_all, tv_commision_send_settlement, tv_commision_send_receive };
	
		rl_commision_send_all.setOnClickListener(listener);
		rl_commision_send_settlement.setOnClickListener(listener);
		rl_commision_send_receive.setOnClickListener(listener);
		
		viewPager.setAdapter(adapter);
		viewPager.setOnPageChangeListener(changeListener);
	}
	
	/**
	 * 监听点击事件
	 */
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
	
	
	/**
	 * 滑动切换页面
	 */
	private FragmentPagerAdapter adapter = new FragmentPagerAdapter(
			getSupportFragmentManager()) {

		public int getCount() {
			return selectList.length;
		}

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
	
	/**
	 * 设置选中的Tab Title
	 * @param position
	 */
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

	/**
	 * 返回上一个页面
	 */
	@Override
	public void onClick(View v) {
		Skip.mBack(mActivity);
	}
}
