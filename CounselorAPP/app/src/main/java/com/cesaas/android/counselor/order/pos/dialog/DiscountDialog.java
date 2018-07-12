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
 * 折扣dialog
 * 
 * @author FGB
 * 
 */
public class DiscountDialog extends Dialog implements View.OnClickListener {
	
	public DiscountDialog(Context context) {
		this(context, R.style.dialog);
	}

	public DiscountDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.discount_dialog);
	}
	
	public void mInitShow() {
		show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
