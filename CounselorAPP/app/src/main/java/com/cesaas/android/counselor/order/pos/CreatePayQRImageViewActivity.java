package com.cesaas.android.counselor.order.pos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 创建支付二维码页面
 * @author FGB
 *
 */
public class CreatePayQRImageViewActivity extends BasesActivity{

	private ImageView mCreateView;
	private LinearLayout ll_back_pos_pay;
	private String orderNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_qr);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			orderNo=bundle.getString("orderNo");
		}
		
		mCreateView=(ImageView) findViewById(R.id.iv_qr_result);
		ll_back_pos_pay=(LinearLayout) findViewById(R.id.ll_back_pos_pay);
		mCreateView.setImageBitmap(createQRImageUtils.createQRImage(orderNo, 750, 750));
		
		ll_back_pos_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}
}
