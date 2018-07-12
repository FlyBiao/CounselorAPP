package com.cesaas.android.counselor.order.dialog;

import android.content.Context;

import com.cesaas.android.counselor.order.R;

public class WaitDialog extends BaseDialog {

	public WaitDialog(Context context) {
		super(context, R.style.dialog);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.wait_dialog);
		setCanceledOnTouchOutside(false);
	}

	public void mStart() {
		show();
	}

	public void mStop() {
		cancel();
	}

}
