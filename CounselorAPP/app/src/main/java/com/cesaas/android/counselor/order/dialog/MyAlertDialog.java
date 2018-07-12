package com.cesaas.android.counselor.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

/**
 * 公共警告dialog
 * 
 * @author Evan
 * 
 */
public class MyAlertDialog extends Dialog implements View.OnClickListener {

	private TextView title_tv;
	private TextView content_tv;
	private LinearLayout two_button_layout;
	private LinearLayout single_button_layout;
	private Button dialog_cancel_btn;
	private Button dialog_sure_btn;
	private Button single_sure_bt;
	private ConfirmListener sureInface;
	private CancelListener canaelInface;

	public MyAlertDialog(Context context) {
		this(context, R.style.dialog);
	}

	public MyAlertDialog(Context context, int theme) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.simple_tips_dialog);
		initData();
	}

	private void initData() {
		title_tv = (TextView) findViewById(R.id.title_tv);
		content_tv = (TextView) findViewById(R.id.content_tv);
		two_button_layout = (LinearLayout) findViewById(R.id.two_button_layout);
		single_button_layout = (LinearLayout) findViewById(R.id.single_button_layout);
		dialog_cancel_btn = (Button) findViewById(R.id.dialog_cancel_btn);
		dialog_sure_btn = (Button) findViewById(R.id.dialog_sure_btn);
		single_sure_bt = (Button) findViewById(R.id.single_sure_bt);
		dialog_cancel_btn.setOnClickListener(this);
		dialog_sure_btn.setOnClickListener(this);
		single_sure_bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialog_cancel_btn:
			if (canaelInface != null)
				canaelInface.onClick(this);
			else
				mDismiss();
			break;
		case R.id.dialog_sure_btn:
			if (sureInface != null) {
				sureInface.onClick(this);
			}
			mDismiss();
			break;
		case R.id.single_sure_bt:
			if (sureInface != null) {
				sureInface.onClick(this);
			}
			mDismiss();
			break;

		default:
			break;
		}

	}

	public void mInitShow(String title, String message, ConfirmListener sure, CancelListener cancel) {
		if (title == null)
			title_tv.setText(getContext().getResources().getString(R.string.tips_titles));
		else
			title_tv.setText(title);
		content_tv.setText(message);
		dialog_sure_btn.setText("确定");
		dialog_cancel_btn.setText("取消");
		this.sureInface = sure;
		this.canaelInface = cancel;
		show();
	}
	
	public void mInitShows(String title, String message) {
		if (title == null)
			title_tv.setText(getContext().getResources().getString(R.string.tips_titles));
		else
			title_tv.setText(title);
			content_tv.setText(message);
			dialog_sure_btn.setText("已关注");
			dialog_cancel_btn.setText("取消关注");
			show();
	}
	/**
	 * 显示Dialog对话框
	 * @param title
	 * @param message
	 * @param ok
	 * @param cbtn
	 * @param sure
	 * @param cancel
	 */
	public void mInitShow(String title, String message, String ok, String cbtn, ConfirmListener sure,
			CancelListener cancel) {
		if (title == null)
			title_tv.setText(getContext().getResources().getString(R.string.tips_titles));
		else
			title_tv.setText(title);
		content_tv.setText(message);
		dialog_sure_btn.setText(ok);
		dialog_cancel_btn.setText(cbtn);
		this.sureInface = sure;
		this.canaelInface = cancel;
		show();
	}

	public void mSingleShow(String title, String message, ConfirmListener sure) {
		if (title == null)
			title_tv.setText(getContext().getResources().getString(R.string.tips_titles));
		else
			title_tv.setText(title);
		content_tv.setText(message);
		single_sure_bt.setText("确定");
		two_button_layout.setVisibility(View.GONE);
		single_button_layout.setVisibility(View.VISIBLE);
		this.sureInface = sure;
		show();
	}

	public void mSingleShow(String title, String message, String ok, ConfirmListener sure) {
		if (title == null)
			title_tv.setText(getContext().getResources().getString(R.string.tips_titles));
		else
			title_tv.setText(title);
		content_tv.setText(message);
		single_sure_bt.setText(ok);
		two_button_layout.setVisibility(View.GONE);
		single_button_layout.setVisibility(View.VISIBLE);
		this.sureInface = sure;
		show();
	}

	public interface ConfirmListener {
		public void onClick(Dialog dialog);
	}

	public interface CancelListener {
		public void onClick(Dialog dialog);
	}

	public void mDismiss() {
		cancel();
	}

}
