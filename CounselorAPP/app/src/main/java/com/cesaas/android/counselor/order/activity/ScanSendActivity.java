package com.cesaas.android.counselor.order.activity;

import android.content.Intent;
import android.os.Bundle;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.zbar.lib.CaptureActivity;

/**
 * 扫描发货页面
 * 
 * @author fgb
 * 
 */
public class ScanSendActivity extends BasesActivity {

	public static final String TAGS = "ScanSendActivity";

	private String PushscanOrder;
	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE = 101;
	public static ScanSendActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity=this;
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			PushscanOrder=bundle.getString("PushscanOrder");
		}
		// 调用扫描二维码
		Skip.mActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
		
//		Skip.mActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
	}

	/**
	 * 获取处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 获取扫描发货返回的condeID
		if (resultCode == RESULT_CODE) {
			
			String codeId = data.getStringExtra("resultCode");
			Bundle bundle=new Bundle();
			bundle.putString("CodeId", codeId);
			bundle.putString("PushscanOrder", PushscanOrder);
			ScanSendActivity.this.finish();
			Skip.mNextFroData(mActivity, ScanSendDetailActivity.class, bundle);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

}
