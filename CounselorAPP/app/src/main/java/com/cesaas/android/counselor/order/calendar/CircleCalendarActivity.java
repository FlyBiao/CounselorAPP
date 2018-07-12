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
import com.cesaas.android.number.calendar.views.CircleCalendarView;

public class CircleCalendarActivity extends Activity {
    private CircleCalendarView circleCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_calendar_view);
        Calendar calendar = Calendar.getInstance();
        int currYear = calendar.get(Calendar.YEAR);
        int currMonth = calendar.get(Calendar.MONTH) + 1;
        List<CalendarInfo> list = new ArrayList<CalendarInfo>();
        list.add(new CalendarInfo(currYear,currMonth,4,"￥20","k"));
        list.add(new CalendarInfo(currYear,currMonth,6,"￥100","k"));
        list.add(new CalendarInfo(currYear,currMonth,12,"￥200","k"));
        list.add(new CalendarInfo(currYear,currMonth,16,"￥1200","k"));
        list.add(new CalendarInfo(currYear,currMonth,28,"￥1200","k"));
        list.add(new CalendarInfo(currYear,currMonth,1,"￥1200",1));
        list.add(new CalendarInfo(currYear,currMonth,11,"￥100",1));
        list.add(new CalendarInfo(currYear,currMonth,19,"￥1200",2));
        list.add(new CalendarInfo(currYear,currMonth,21,"￥100",1));
        circleCalendarView = (CircleCalendarView) findViewById(R.id.circleMonthView);
        circleCalendarView.setCalendarInfos(list);
        circleCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(CircleCalendarActivity.this,"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
