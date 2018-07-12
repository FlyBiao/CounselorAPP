package com.cesaas.android.counselor.order.custom.calendar;

import android.app.Activity;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.signin.SigninRecordActivity;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Author FGB
 * Description 期选择选择 类
 * Created 2017/3/21 13:56
 * Version 1.zero
 */
public class GetDateSelect {

    /**
     * 获取选择的日期
     * @param v
     */
    public static void getDateSelect(View v, Activity mActivity,DatePickerDialog dpd ){
        Calendar now = Calendar.getInstance();
//        DatePickerDialog dpd = DatePickerDialog.newInstance(mActivity,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(mActivity.getResources().getColor(R.color.base_text_color));
        }
        if (true) {// boolean,设置标题
            dpd.setTitle("日期选择");
        }
        if (false) {// boolean,只能选择某些日期
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.MONTH, i);
                dates[i + 6] = date;
            }
            dpd.setSelectableDays(dates);
        }
        if (true) {// boolean,部分高亮
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.WEEK_OF_YEAR, i);
                dates[i + 6] = date;
            }
            dpd.setHighlightedDays(dates);
        }
        if (false) {// boolean,某些日期不可选
            Calendar[] dates = new Calendar[3];
            for (int i = -1; i <= 1; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.DAY_OF_MONTH, i);
                dates[i + 1] = date;
            }
            dpd.setDisabledDays(dates);
        }
        dpd.show(mActivity.getFragmentManager(), "Datepickerdialog");
    }
}
