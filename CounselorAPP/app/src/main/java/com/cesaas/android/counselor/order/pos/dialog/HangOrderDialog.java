package com.cesaas.android.counselor.order.pos.dialog;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.CancelListener;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 挂单dialog
 * 
 * @author FGB
 * 
 */
public class HangOrderDialog extends Dialog implements View.OnClickListener {
	
	private TextView hang_title;

	public HangOrderDialog(Context context) {
		this(context, R.style.dialog);
	}

	public HangOrderDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.hang_order_list_dialog);
		initData();
	}
	private void initData() {
		
		hang_title=(TextView) findViewById(R.id.hang_title);
	}
	
	public void mInitShow(String title) {
		if (title == null)
			hang_title.setText("挂单列表");
		else
			hang_title.setText(title);
		show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	

}
