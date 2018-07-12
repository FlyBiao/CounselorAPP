package com.cesaas.android.counselor.order.store;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 陈列任务审批批注页面
 * @author FGB
 *
 */
public class DisplayTaskApprovalActivity extends BasesActivity implements OnClickListener{

	private LinearLayout llBack;
	private LinearLayout llEditDisplay;
	private TextView tvBaseTitle;
	private TextView tvBaseTitleRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_task_approval_activity);
		initView();
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {

		llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
		llEditDisplay=(LinearLayout) findViewById(R.id.ll_et_display);
		llEditDisplay.getBackground().setAlpha(180);//zero~255透明度值
		tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
		tvBaseTitle.setText("陈列任务审批");
		tvBaseTitleRight=(TextView) findViewById(R.id.tv_base_title_right);
		tvBaseTitleRight.setVisibility(View.VISIBLE);
		tvBaseTitleRight.setText("保存");
		
		//设置视图控件监听
		llBack.setOnClickListener(this);
		tvBaseTitleRight.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_base_title_back://返回
			Skip.mBack(mActivity);
			break;
			
		case R.id.tv_base_title_right://保存
			ToastFactory.getLongToast(mContext, "审批保存成功!");
			break;
		}
	}
}
