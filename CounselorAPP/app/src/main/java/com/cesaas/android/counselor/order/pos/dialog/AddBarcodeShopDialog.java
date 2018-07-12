package com.cesaas.android.counselor.order.pos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.cesaas.android.counselor.order.R;

/**
 * 添加条码商品dialog【暂时在收银主页面写了成了内部类】
 * 
 * @author FGB
 * 
 */
public class AddBarcodeShopDialog extends Dialog implements View.OnClickListener {
	
	private ImageView iv_add_scan_barcode;
	private Button btn_confirm_add_barcode_shop;
	
	public AddBarcodeShopDialog(Context context) {
		
		this(context, R.style.dialog);
	}

	public AddBarcodeShopDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.add_barcode_shop_dialog);
		initView();
	}
	
	public void initView(){
		iv_add_scan_barcode=(ImageView) findViewById(R.id.iv_add_scan_barcode);
		btn_confirm_add_barcode_shop=(Button) findViewById(R.id.btn_confirm_add_barcode_shop);
		
		iv_add_scan_barcode.setOnClickListener(this);
		btn_confirm_add_barcode_shop.setOnClickListener(this);
	}
	

	public void mInitShow() {
		show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add_scan_barcode://扫描添加商品条码
			// 调用扫描二维码
			break;
		case R.id.btn_confirm_add_barcode_shop://确定添加
			
			break;

		default:
			break;
		}
		
	}

	

}
