package com.cesaas.android.counselor.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 欢迎页面
 * @author fgb
 *
 */
public class WelcomesActivity extends FragmentActivity {

	private RelativeLayout top;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_layout);
		
		top=(RelativeLayout) findViewById(R.id.top);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		 {
		         // 透明状态栏
		         getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		
		initData();
		
	}

	private void initData() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(WelcomesActivity.this,LoginActivity.class);
				startActivity(intent);
				finish();
			}
		},2000);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
