package com.cesaas.android.counselor.order.pos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

/**
 * 挂单dialog
 * 
 * @author FGB
 * 
 */
public class GetOrderDialog extends Dialog implements View.OnClickListener {
	
	private TextView order_title;

	public GetOrderDialog(Context context) {
		this(context, R.style.dialog);
	}

	public GetOrderDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.get_order_list_dialog);
		initData();
	}
	private void initData() {
		
		order_title=(TextView) findViewById(R.id.order_title);
	}
	
	public void mInitShow(String title) {
		if (title == null)
			order_title.setText("取单列表");
		else
			order_title.setText(title);
		show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	

}
