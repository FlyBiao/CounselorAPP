package com.cesaas.android.counselor.order.utils;

import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

public class AbOtherUtil {

	public static int times; // 倒计时
	private static TextView sendText; // 发送按钮

	/**
	 * 手机号号码及为空判断
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public static boolean phoneVerify(Context context, String str) {
		if (!AbStrUtil.isEmpty(str)) {
			if (AbRegUtil.isMobileNO(str)) { // 是否为手机号
				return true;
			} else {
				ToastFactory.show(context, context.getResources().getString(R.string.login_tel_sucs),
						ToastFactory.CENTER);
			}
		} else {
			ToastFactory.show(context, context.getResources().getString(R.string.login_pwd_not_null), ToastFactory.CENTER);
		}
		return false;
	}

	public static boolean passwordVerify(Context context, String str) {
		int size = str.length();
		if (!AbStrUtil.isEmpty(str)) {
			if (size > 3 && size < 13) { // 密码长度4-12位
				return true;
			} else {
				ToastFactory.show(context, context.getResources().getString(R.string.login_pwd_sucs),
						ToastFactory.CENTER);
			}
		} else {
			ToastFactory.show(context, context.getResources().getString(R.string.login_pwd_not_null), ToastFactory.CENTER);
		}
		return false;
	}

	public static boolean textVerify(Context context, String str) {
		if (!AbStrUtil.isEmpty(str)) {
			return true;
		} else {
			ToastFactory.show(context, context.getResources().getString(R.string.main_empty), ToastFactory.CENTER);
		}
		return false;
	}

	public static boolean lengthVerify(Context context, String str, int min, int max) {
		if (!AbStrUtil.isEmpty(str)) {
			if (str.length() >= min && str.length() <= max) {
				return true;
			} else {
				ToastFactory.show(context,
						String.format(context.getResources().getString(R.string.text_length), min, max),
						ToastFactory.CENTER);
			}
		} else {
			ToastFactory.show(context, context.getResources().getString(R.string.main_empty), ToastFactory.CENTER);
		}
		return false;
	}

	/**
	 * 手机号码按3－4－4格式化显示
	 * 
	 * @author Evan 2015-8-28
	 * @param num
	 * @return
	 */
	public static void formatPhoneNum(EditText edt, CharSequence num) {
		String contents = num.toString();
		int length = contents.length();
		if (length == 4) {
			if (contents.substring(3).equals(new String(" "))) { // -
				contents = contents.substring(0, 3);
				edt.setText(contents);
				edt.setSelection(contents.length());
			} else { // +
				contents = contents.substring(0, 3) + " " + contents.substring(3);
				edt.setText(contents);
				edt.setSelection(contents.length());
			}
		} else if (length == 9) {
			if (contents.substring(8).equals(new String(" "))) { // -
				contents = contents.substring(0, 8);
				edt.setText(contents);
				edt.setSelection(contents.length());
			} else {// +
				contents = contents.substring(0, 8) + " " + contents.substring(8);
				edt.setText(contents);
				edt.setSelection(contents.length());
			}
		}
	}

	public static void formatPhoneShow(EditText edt, String num) {
		String contents = num.toString();
		if (contents.length() > 8) {
			contents = contents.substring(0, 3) + " " + contents.substring(3, 7) + " " + contents.substring(7);
			edt.setText(contents);
			edt.setSelection(contents.length());
		}
	}

	public static void formatPhoneShow(TextView edt, String num) {
		String contents = num.toString();
		if (contents.length() > 8) {
			contents = contents.substring(0, 3) + " " + contents.substring(3, 7) + " " + contents.substring(7);
			edt.setText(contents);
		}
	}

	/**
	 * 是否隐藏键盘
	 * 
	 * @param v
	 * @param event
	 * @return
	 */
	public static boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			// 获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证码倒计时
	 * 
	 * @param send
	 */
	public static void sendCode(TextView send) {
		times = 90;
		sendText = send;
		send.setText(times + " s后重发");
		// send.setTextColor(0xffffffff);
		send.setEnabled(false);
		handler.postDelayed(runnable, 1000);// 每一秒执行一次runnable.
	}

	public static Handler handler = new Handler();
	public static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			--times;
			if (times < 0) {
				sendText.setText("重新发送");
				// sendText.setTextColor(0xffffffff);
				sendText.setEnabled(true);
				return;
			}
			sendText.setText(times + " s后重发");
			handler.postDelayed(this, 1000);
		}
	};

	public static void remoHandle() {
		handler.removeCallbacks(runnable);
	}

}
