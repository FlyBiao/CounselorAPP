package com.cesaas.android.counselor.order.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工厂，专门做显示提示用
 * @author FlyBiao
 *
 */
public class ToastFactory {

	private static Context context = null;
	private static Toast toast = null;
	public static final int TOP = Gravity.TOP;
	public static final int CENTER = Gravity.CENTER;
	public static final int BOTTOM = Gravity.BOTTOM;

	public static void getToast(Context context, String text) {
		if (ToastFactory.context == context) {
			// toast.cancel();
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);
		} else {
			ToastFactory.context = context;
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public static void getLongToast(Context context, String text) {
		if (ToastFactory.context == context) {
			// toast.cancel();
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_LONG);
		} else {

			ToastFactory.context = context;
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		}
		toast.show();
	}

	public static void cancelToast() {
		if (toast != null) {
			toast.cancel();
		}
	}

	/**
	 * 位置自由显示
	 * 
	 * @param context
	 * @param text
	 * @param position
	 */
	public static void show(Context context, String text, int position) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		if (position == TOP) {
			toast.setGravity(position, 0, 160);
		} else {
			toast.setGravity(position, 0, 0);
		}
		toast.show();
	}

}
