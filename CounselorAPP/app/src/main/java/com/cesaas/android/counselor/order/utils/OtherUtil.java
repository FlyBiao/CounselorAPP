package com.cesaas.android.counselor.order.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;

public class OtherUtil {
	
	
	
	/**
	 * 跳转到qq
	 * @param context 上下文
	 * @param qqNum qq号码
	 * @return 是否成功
	 */
	public static boolean toTheQQ(Context context,String qqNum){
		if(null==qqNum||qqNum.length()<5){
			return false;
		}
		String url11 = "mqqwpa://im/chat?chat_type=wpa&uin="+qqNum+"&version=1";
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
		return true;
	}

	
	
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
			ToastFactory.show(context, context.getResources().getString(R.string.login_account_not_null), ToastFactory.CENTER);
		}
		return false;
	}

	/**
	 * 密码验证
	 * @param context
	 * @param str
	 * @return
	 */
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
	 * 隐藏软键盘
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void hideSoftInput(Context context) {
		View view = ((Activity) context).getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
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


}
