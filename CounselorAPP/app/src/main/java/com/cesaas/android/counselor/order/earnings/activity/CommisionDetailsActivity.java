package com.cesaas.android.counselor.order.earnings.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.lidroid.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_commision_details_layout)
public class CommisionDetailsActivity extends BasesActivity implements
		OnClickListener {

//	@ViewInject(R.id.rg_commision)
//	private RadioGroup rg_commision;
//	@ViewInject(R.id.iv_back_main)
//	private ImageView iv_back_main;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		initData();
//
//		// 返回
//		iv_back_main.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Skip.mBack(mActivity);
//			}
//		});

	}
	
	/*public void initData(){
		rg_commision.check(R.id.rbtn_commision1);// //设置默认选中推荐佣金
		
		// 设置监听radioGroup的选中事件
		rg_commision.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rbtn_commision1://
					Skip.mNext(mActivity, CommisionDevelopActivity.class);
					break;
				case R.id.rbtn_commision2://
					Skip.mNext(mActivity, CommisionSendActivity.class);
					break;

				default:
					break;
				}
			}

		});
	}*/

	@Override
	public void onClick(View v) {

	}
}
