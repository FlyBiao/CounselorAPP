package com.cesaas.android.counselor.order.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.number.calendar.component.MonthView;
import com.cesaas.android.number.calendar.entity.CalendarInfo;
import com.cesaas.android.number.calendar.views.ADCircleCalendarView;

public class ADCircleCalendarActivity extends Activity {
    private ADCircleCalendarView circleCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adcircle_calendar_view);
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH) + 1;
        List<CalendarInfo> list = new ArrayList<CalendarInfo>();
        list.add(new CalendarInfo(currYear,currMonth,4,"￥1200","k"));
        list.add(new CalendarInfo(currYear,currMonth,6,"￥1200","k"));
        list.add(new CalendarInfo(currYear,currMonth,12,"￥1200","k"));
        list.add(new CalendarInfo(currYear,currMonth,16,"￥1200","k"));
        list.add(new CalendarInfo(currYear,currMonth,28,"￥1200","k"));
        list.add(new CalendarInfo(currYear,currMonth,1,"￥1200",1));
        list.add(new CalendarInfo(currYear,currMonth,11,"￥1200",1));
        list.add(new CalendarInfo(currYear,currMonth,19,"￥1200",2));
        list.add(new CalendarInfo(currYear,currMonth,21,"￥1200",1));
        circleCalendarView = (ADCircleCalendarView) findViewById(R.id.circleMonthView);
        circleCalendarView.setCalendarInfos(list);
        circleCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(ADCircleCalendarActivity.this,"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });
    }
}