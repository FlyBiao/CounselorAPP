package com.cesaas.android.counselor.order.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.fragment.FansOrderFragment;
import com.cesaas.android.counselor.order.fragment.SendOrderFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 所有分销订单状态页面
 * @author FGB
 *
 */
@ContentView(R.layout.activity_my_order)
public class MyAllOrderStateActivity extends BasesActivity implements OnClickListener{

	@ViewInject(R.id.radioGroup)
	private RadioGroup radioGroup;
	@ViewInject(R.id.iv_myorder_back)
	private LinearLayout iv_myorder_back;
	
	private FragmentManager fm;
	private FragmentTransaction transaction;
	
	private  SendOrderFragment merchantsOrderFragment;//发货订单
	private FansOrderFragment fansOrderFragment;//粉丝订单
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initDate();
		
	}
	
	/**
     * 初始化控件
     */
    public void initView(){

    	iv_myorder_back.setOnClickListener(this);
    	if(null==merchantsOrderFragment){
    		merchantsOrderFragment=new SendOrderFragment();
    		
    		if(!merchantsOrderFragment.isAdded()){
        		fm = getSupportFragmentManager();
        		transaction = fm.beginTransaction();
        		transaction.add(R.id.my_order_realtabcontent, merchantsOrderFragment).addToBackStack(null).commit();
        	}
    	}
    	
    }
    /**
	 * 初始化数据
	 */
	private void initDate() {
		radioGroup.check(R.id.btn_merchants_order);// //设置默认选中
		// 设置监听radioGroup的选中事件进行切换fragment
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				fm = getSupportFragmentManager();
				transaction = fm.beginTransaction();
				switch (checkedId) {
				case R.id.btn_merchants_order://
					
//					if(null==merchantsOrderFragment){
			    		merchantsOrderFragment=new SendOrderFragment();
//					}
					transaction.replace(R.id.my_order_realtabcontent, merchantsOrderFragment).commit();
					break;
					
				case R.id.btn_fans_order://

//					if(null==fansOrderFragment){
						fansOrderFragment=new FansOrderFragment();
//					}
					transaction.replace(R.id.my_order_realtabcontent, fansOrderFragment).commit();
					
					break;
				}
			}

		});
	}
	
	@Override
	public void onClick(View v) {
		Skip.mBack(mActivity);
	}
    

}
