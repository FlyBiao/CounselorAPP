package com.cesaas.android.counselor.order.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.fragment.newmain.NewMainSampleFragment;
import com.cesaas.android.counselor.order.activity.main.fragment.newmain.NewTaskSampleFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 任务提醒内页
 * @author FGB
 *
 */

@ContentView(R.layout.activity_task_new_layout)
public class TaskNewActivity extends BasesActivity implements OnClickListener{

	private TextView tvTitle;
	private LinearLayout llBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initClickListener();
		initData();
	}

	private void initData(){
		getSupportFragmentManager().beginTransaction().add(R.id.new_task_frame_layout, new NewTaskSampleFragment()).commit();
	}
	private void initClickListener(){
		llBack.setOnClickListener(this);
	}
	private void initView() {
		tvTitle= (TextView) findViewById(R.id.tv_base_title);
		tvTitle.setText("任务列表");
		llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ll_base_title_back:
				Skip.mBack(mActivity);
				break;
		}
	}
}
