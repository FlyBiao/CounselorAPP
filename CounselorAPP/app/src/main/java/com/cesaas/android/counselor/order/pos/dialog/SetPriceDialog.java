package com.cesaas.android.counselor.order.pos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cesaas.android.counselor.order.R;

/**
 * 设置商品价格dialog
 * 
 * @author FGB
 * 
 */
public class SetPriceDialog extends Dialog implements View.OnClickListener {
	
	private Button btn_confirm_set_shop_price;
	private EditText et_barcode_shop_price;
	public static double price=0.0;
	
	public SetPriceDialog(Context context) {
		this(context, R.style.dialog);
	}

	public SetPriceDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.set_price_dialog);
		
		initView();
	}
	
	public void initView(){
		btn_confirm_set_shop_price=(Button) findViewById(R.id.btn_confirm_set_shop_price);
		et_barcode_shop_price=(EditText) findViewById(R.id.et_barcode_shop_price);
		btn_confirm_set_shop_price.setOnClickListener(this);
	}
	
	public void mInitShow() {
		show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm_set_shop_price://确定添加
				price=Double.parseDouble(et_barcode_shop_price.getText().toString());
				cancel();
			break;

		default:
			break;
		}
	}


}
