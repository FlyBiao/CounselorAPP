package com.cesaas.android.counselor.order.staffmange;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetSubMenuPowerBean;
import com.cesaas.android.counselor.order.net.GetSubMenuPowerNet;
import com.cesaas.android.counselor.order.task.fragment.CompleteTaskFragment;
import com.cesaas.android.counselor.order.task.fragment.PrivateTaskFragment;
import com.cesaas.android.counselor.order.task.fragment.PublicTaskFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * 店员列表
 * @author FGB
 *
 */
public class ShopStaffListActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_shop_task_back;
	private TextView tv_public_task,tv_private_task,tv_complete_task;
	
	private ArrayList<Fragment> fragments;
	private ViewPager viewPager;
	private int line_width;
	private View line;
	
	private String aPPShop;
	//子菜单
//	private GetSubMenuPowerNet getSubMenuPowerNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_layout);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			aPPShop=bundle.getString("APP_Shop");
		}
		
//		getSubMenuPowerNet=new GetSubMenuPowerNet(mContext);
//		getSubMenuPowerNet.setData();
		
		initView();
		initDatas();
	}
	
	/**
	 * 接收子菜单POS消息
	 * @param msg 消息实体类
	 */
//	public void onEventMainThread(ResultGetSubMenuPowerBean msg) {
//		
//		if(msg.TModel!=null){
//			for (int i = zero; i < msg.TModel.size(); i++) {
//				if(aPPShop.equals(msg.TModel.get(i).getMENU_PARENTNO())){
//					Log.i("Menus", msg.TModel.get(i).getMENU_NAME());
//				}
//			}
//		}
//		
//	}
	
	/**
	 * 初始化视图控件
	 */
	private void initView() {
		tv_public_task=(TextView) findViewById(R.id.tv_public_task);
		tv_private_task=(TextView) findViewById(R.id.tv_private_task);
		tv_complete_task=(TextView) findViewById(R.id.tv_complete_task);
		viewPager=(ViewPager)findViewById(R.id.vp_task_viewPager);
		line=findViewById(R.id.shop_task_lines);
		ll_shop_task_back=(LinearLayout) findViewById(R.id.ll_shop_task_back);
		ll_shop_task_back.setOnClickListener(this);
		
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
			tv_complete_task.setTextColor(getResources().getColor(R.color.black));

		}else if(arg0==1){
			tv_private_task.setTextColor(getResources().getColor(R.color.color_title_bar));
			tv_public_task.setTextColor(getResources().getColor(R.color.black));
			tv_complete_task.setTextColor(getResources().getColor(R.color.black));
			
		} else {//进来
			tv_complete_task.setTextColor(getResources().getColor(R.color.color_title_bar));
			tv_private_task.setTextColor(getResources().getColor(R.color.black));
			tv_public_task.setTextColor(getResources().getColor(R.color.black));
		}
	}
	
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			
		case R.id.ll_shop_task_back://返回
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
	}
}
