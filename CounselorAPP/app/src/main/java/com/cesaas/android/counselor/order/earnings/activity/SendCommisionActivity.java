package com.cesaas.android.counselor.order.earnings.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.earnings.fragment.AllCommisonEarning;
import com.cesaas.android.counselor.order.earnings.fragment.CommisonCompleteEarnings;
import com.cesaas.android.counselor.order.earnings.fragment.CommisonStaySettleEarnings;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 发货佣金详情页面
 * @author fgb
 *
 */
@ContentView(R.layout.activity_commision_earnings_layout)
public class SendCommisionActivity extends BasesActivity{
	
	@ViewInject(R.id.rgp_commision)
	private RadioGroup rgp_commision;
	
	@ViewInject(R.id.ll_back)
	private LinearLayout ll_back;
	
	private FragmentManager fm;
	private FragmentTransaction transaction;
	
	private AllCommisonEarning allCommisonEarning=new AllCommisonEarning();//全部佣金收益
	private CommisonCompleteEarnings commisonCompleteEarnings=new CommisonCompleteEarnings();//已结算
	private CommisonStaySettleEarnings commisonStaySettleEarnings=new CommisonStaySettleEarnings();//可结算

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		onClickBack();
		initViews();
		initData();
	}
	
	/**
	 * 初始化控件
	 */
	public void initViews(){
		
		if(!allCommisonEarning.isAdded()){
			fm = getSupportFragmentManager();
	        transaction = fm.beginTransaction();
	        transaction.add(R.id.my_commision_realtabcontent, allCommisonEarning).addToBackStack(null).commit();
		}
		
	}
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		rgp_commision.check(R.id.rbtn_commision01);// //设置默认选中首页

		// 设置监听radioGroup的选中事件
		rgp_commision.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				fm = getSupportFragmentManager();
				transaction = fm.beginTransaction();
				
				switch (checkedId) {
				case R.id.rbtn_commision01://全部
					transaction.replace(R.id.my_commision_realtabcontent, allCommisonEarning);
					break;
				case R.id.rbtn_commision02://已结算
					transaction.replace(R.id.my_commision_realtabcontent, commisonCompleteEarnings);
					break;
				case R.id.rbtn_commision03://可结算
						transaction.replace(R.id.my_commision_realtabcontent, commisonStaySettleEarnings);
					break;

				default:
					break;
				}
				transaction.commit();
			}

		});
	}
	
	/**
	 * 点击返回上一个页面
	 */
	public void onClickBack() {
		ll_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);				
			}
		});
		
		
	}
	
}
