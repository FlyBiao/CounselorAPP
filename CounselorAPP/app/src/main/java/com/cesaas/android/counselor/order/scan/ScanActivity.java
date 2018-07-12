package com.cesaas.android.counselor.order.scan;

import android.content.Intent;
import android.os.Bundle;

import cn.hugo.android.scanner.CaptureActivity;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.activity.ScanSendDetailActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 扫描发货页面
 * @author cebsaas
 *
 */
public class ScanActivity extends BasesActivity{

	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE = 101;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Skip.mActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
	}
	
	/**
	 * 处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(resultCode==RESULT_CODE) {
			String CodeId = data.getStringExtra("resultCode");
			Bundle bundle=new Bundle();
			bundle.putString("CodeId", CodeId);
			bundle.putString("SendType", Constant.EXPRESS_SEND);
			Skip.mNextFroData(mActivity, ScanSendDetailActivity.class, bundle);
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}
