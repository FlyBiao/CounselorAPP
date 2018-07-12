package com.cesaas.android.counselor.order.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.express.view.ExpressListActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 意见反馈
 * @author FGB
 *
 */

@ContentView(R.layout.activity_feedback_layout)
public class FeedBackActivity extends BasesActivity{
	
	@ViewInject(R.id.tv_feedback_submit)
	private TextView tv_feedback_submit;
	@ViewInject(R.id.tv_user_feedback_back)
	private TextView tv_user_feedback_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		tv_feedback_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new MyAlertDialog(mContext).mSingleShow("反馈成功", "感谢您的宝贵意见", "返回", new ConfirmListener() {

					@Override
					public void onClick(Dialog dialog) {
						finish();
					}
				});
			}
		});
		
		tv_user_feedback_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}

}
