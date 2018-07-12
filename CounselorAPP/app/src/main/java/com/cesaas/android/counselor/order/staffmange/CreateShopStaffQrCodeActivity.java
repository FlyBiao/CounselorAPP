package com.cesaas.android.counselor.order.staffmange;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.pos.createQRImageUtils;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 创建绑定会员二维码和绑定会员
 * @author FGB
 *
 */
public class CreateShopStaffQrCodeActivity extends BasesActivity{

	private LinearLayout ll_add_shop_staff;
	private ImageView iv_shop_staff_code;
	
	private String mobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_shop_staff_qr_code);
		
		Bundle bundle=getIntent().getExtras();
		mobile=bundle.getString("mobile");
		
		iv_shop_staff_code=(ImageView) findViewById(R.id.iv_shop_staff_code);
		iv_shop_staff_code.setImageBitmap(createQRImageUtils.createQRImage(mobile, 750, 750));
		
		ll_add_shop_staff=(LinearLayout) findViewById(R.id.ll_add_shop_staff);
		ll_add_shop_staff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
		
	}
}
;