package com.cesaas.android.counselor.order.stafftest;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 考试列表
 * @author FGB
 *
 */
public class TestListActivity extends BasesActivity implements OnClickListener{
	
	private LinearLayout ll_staff_test_back;
	private TextView tv_not_test,tv_test_ing,tv_test_complete;
	
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	private FragmentManager fm;
	private FragmentTransaction transaction;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staff_test_list);
		
		initView ();
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		
		if(!NotTestFragment.getInstance().isAdded()){
			fm =getSupportFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.fl_test_content, NotTestFragment.getInstance()).commit();
		}
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		ll_staff_test_back=(LinearLayout) findViewById(R.id.ll_staff_test_back);
		ll_staff_test_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
		
		tv_not_test=(TextView) findViewById(R.id.tv_not_test);
		tv_test_ing=(TextView) findViewById(R.id.tv_test_ing);
		tv_test_complete=(TextView) findViewById(R.id.tv_test_complete);
		tv_not_test.setOnClickListener(this);
		tv_test_ing.setOnClickListener(this);
		tv_test_complete.setOnClickListener(this);
		tvs.add(tv_not_test);
		tvs.add(tv_test_ing);
		tvs.add(tv_test_complete);
		
	}

	@Override
	public void onClick(View v) {
		
		int index=-1;
		fm = getSupportFragmentManager();
		transaction = fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.tv_not_test://未考试
			index=0;
			if(tv_not_test.isEnabled()==true){
				transaction.replace(R.id.fl_test_content, NotTestFragment.getInstance());
			}else{
				tv_not_test.setEnabled(false);
			}
			break;
			
		case R.id.tv_test_ing://考试中
			index=1;
			transaction.replace(R.id.fl_test_content, TestIngFragment.getInstance());
			break;
			
		case R.id.tv_test_complete://已完成考试
			index=2;
			transaction.replace(R.id.fl_test_content, CompleteTestFragment.getInstance());
			break;
		}
		setColor(index);
		transaction.commit();
	}
	
	private void setColor(int index) {
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.white));
			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.color_title_bar));
		}
		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.color_title_bar));
		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_staff_test_bg));
	}
}
