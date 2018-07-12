package com.cesaas.android.counselor.order.stores;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 业绩管理
 * @author FGB
 *
 */
public class PerformanceMangerActivity extends BasesActivity implements OnClickListener{

	private LinearLayout ll_performance_manger_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.performance_manger_activity);
		
		initView();
		initClickListener();
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		ll_performance_manger_back=(LinearLayout) findViewById(R.id.ll_performance_manger_back);
	}
	
	/**
	 * 初始化点击监听
	 */
	private void initClickListener() {
		ll_performance_manger_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_performance_manger_back://返回
			Skip.mBack(mActivity);
			break;

		default:
			break;
		}
	}
}
