package com.cesaas.android.counselor.order.activity.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.VipDataScreenActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Author FGB
 * Description 订单数据统计筛选页面
 * Created 2017/3/2 23:29
 * Version 1.zero
 */
public class OrderDataScreenActivity extends BasesActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private LinearLayout llBack;
    private TextView tvBaseTitle;
    private EditText etStartDate,etEndDate;
    private int selectDate=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_data_screen);

        initView();
        setData();
    }

    private void setData() {
        tvBaseTitle.setText("订单统计报表");
    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        etStartDate= (EditText) findViewById(R.id.et_start_date);
        etEndDate= (EditText) findViewById(R.id.et_end_date);
        llBack = (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle = (TextView) findViewById(R.id.tv_base_title);


        //设置视图控件监听
        llBack.setOnClickListener(this);
        etStartDate.setOnClickListener(this);
        etEndDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.et_start_date://开始日期
                selectDate=0;
                getDateSelect(etStartDate);
                break;
            case R.id.et_end_date://结束日期
                selectDate=1;
                getDateSelect(etEndDate);
                break;
        }
    }

    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(OrderDataScreenActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(getResources().getColor(R.color.base_text_color));
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
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        if(selectDate==1){//结束日期
            etEndDate.setText(date);
        }else{//开始日期
            etStartDate.setText(date);
        }
    }
}
