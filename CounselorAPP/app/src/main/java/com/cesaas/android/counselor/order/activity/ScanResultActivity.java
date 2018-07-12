package com.cesaas.android.counselor.order.activity;

import android.content.Intent;
import android.os.Bundle;
import cn.hugo.android.scanner.CaptureActivity;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 扫描结果Activity
 * @author FGB
 *
 */
public class ScanResultActivity extends BasesActivity {
	private static final String TAG="ScanResultActivity";
	
	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE = 101;
	private String ScanBarcodeCode;// 扫描验货条形码
	private String barcodeCode;//订单条码
	public static ScanResultActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			barcodeCode=bundle.getString("barcodeCode");
			
		}
		
		super.onCreate(savedInstanceState);
		activity=this;
		// 调用扫描二维码
		Skip.mScanCheckActivityResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
	}

	/**
	 * 获取处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		// 获取扫描发货返回的codeID
		if (resultCode == RESULT_CODE) {
			if(data.getStringExtra("mScanCheckActivityResult")!=null){
				
				if(data.getStringExtra("mScanCheckActivityResult").equals("009")){
					ScanBarcodeCode = data.getStringExtra("resultCode");
					if(ScanBarcodeCode!=null){
						if(ScanBarcodeCode.contains(barcodeCode)){
							
							ToastFactory.show(mContext, "验货成功", ToastFactory.CENTER);
							ScanResultActivity.this.finish();
						}else{
							
							ToastFactory.show(mContext, "验货失败,商品条码有误！", ToastFactory.CENTER);
							ScanResultActivity.this.finish();
						}
					}else{
						ToastFactory.show(mContext, "获取验货条码为空!", ToastFactory.CENTER);
						ScanResultActivity.this.finish();
					}
				}
			}
			
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
