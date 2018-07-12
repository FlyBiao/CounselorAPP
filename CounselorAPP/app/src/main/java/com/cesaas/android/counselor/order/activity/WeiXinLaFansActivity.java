package com.cesaas.android.counselor.order.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

public class WeiXinLaFansActivity extends BasesActivity{
	
	private ImageView iv_la_fans;
	private LinearLayout iv_back_up;
	
	public static BitmapUtils bitmapUtils;
	private String Ticket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			Ticket=bundle.getString("Ticket");
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_la_fans_layout);
		
		bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext().getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(getApplicationContext()).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		
		iv_la_fans=(ImageView) findViewById(R.id.iv_la_fans);
		iv_back_up=(LinearLayout) findViewById(R.id.iv_fans_back);
		
		bitmapUtils.display(iv_la_fans, Ticket, App.mInstance().bitmapConfig);
		
		//返回
		iv_back_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
		
	}
}
