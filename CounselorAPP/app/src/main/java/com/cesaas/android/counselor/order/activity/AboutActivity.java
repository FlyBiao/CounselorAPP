package com.cesaas.android.counselor.order.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 关于页面
 * @author FGB
 *
 */

@ContentView(R.layout.activity_about_layout)
public class AboutActivity extends BasesActivity{

	@ViewInject(R.id.iv_about_back)
	private LinearLayout iv_about_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		iv_about_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});

	}
}
