package com.cesaas.android.counselor.order.pos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.cesaas.android.counselor.order.R;

/**
 * 设置商品数量dialog
 * 
 * @author FGB
 * 
 */
public class SetNumberDialog extends Dialog implements View.OnClickListener {
	
	private Button btn_confirm_set_shop_number;
	private EditText et_barcode_shop_number;
	private Activity activity;
	public static int number=0;
	
	public SetNumberDialog(Context context,Activity activity) {
		this(context, R.style.dialog);
		this.activity=activity;
	}

	public SetNumberDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.set_shop_number_dialog);
		
		initView();
	}
	
	public void initView(){
		btn_confirm_set_shop_number=(Button) findViewById(R.id.btn_confirm_set_shop_number);
		et_barcode_shop_number=(EditText) findViewById(R.id.et_barcode_shop_number);
		btn_confirm_set_shop_number.setOnClickListener(this);
	}
	
	public void mInitShow() {
		show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm_set_shop_number://确定添加
			number=Integer.parseInt(et_barcode_shop_number.getText().toString());
			
	        cancel();
			break;

		default:
			break;
		}
	}


}
