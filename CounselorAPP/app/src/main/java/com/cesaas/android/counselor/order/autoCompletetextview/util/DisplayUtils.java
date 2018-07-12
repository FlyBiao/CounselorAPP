package com.cesaas.android.counselor.order.autoCompletetextview.util;

import android.content.Context;

/**
 * Author FGB
 * Description 显示工具类
 * Created 2017/3/17 9:44
 * Version 1.zero
 */
public class DisplayUtils {

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
