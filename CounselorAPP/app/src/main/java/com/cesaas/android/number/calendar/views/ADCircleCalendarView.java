package com.cesaas.android.number.calendar.views;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.number.calendar.component.ADCircleMonthView;
import com.cesaas.android.number.calendar.component.MonthView;
import com.cesaas.android.number.calendar.component.WeekView;
import com.cesaas.android.number.calendar.entity.CalendarInfo;
import com.cesaas.android.number.calendar.theme.IDayTheme;
import com.cesaas.android.number.calendar.theme.IWeekTheme;

/**
 * Created by Administrator on 2016/8/7.
 */
public class ADCircleCalendarView extends LinearLayout implements View.OnClickListener {
    private WeekView weekView;
    private ADCircleMonthView circleMonthView;
    private TextView textViewYear,textViewMonth,left1,right1;
    private View view ;
    
    public ADCircleCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutParams llParams =new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        view = LayoutInflater.from(context).inflate(R.layout.display_grid_date,null);
        
        initView();
        
        weekView = new WeekView(context,null);
        circleMonthView = new ADCircleMonthView(context,null);
        
        addView(view,llParams);
        addView(weekView,llParams);
        addView(circleMonthView,llParams);
       
    }
    
    /**
     * 初始化视图控件
     */
    public void initView(){
    	
    	 left1=(TextView) view.findViewById(R.id.left1);
         right1=(TextView) view.findViewById(R.id.right1);
         left1.setOnClickListener(this);
         right1.setOnClickListener(this);
         
         textViewYear = (TextView) view.findViewById(R.id.year1);
         textViewMonth = (TextView) view.findViewById(R.id.month1);
         
         textViewYear.setText(circleMonthView.getSelYear()+"年");
         textViewMonth.setText((circleMonthView.getSelMonth() + 1)+"月");
         
         circleMonthView.setMonthLisener(new MonthView.IMonthLisener() {
             @Override
             public void setTextMonth() {
                 textViewYear.setText(circleMonthView.getSelYear()+"年");
                 textViewMonth.setText((circleMonthView.getSelMonth() + 1)+"月");
             }
         });
    }

    /**
     * 设置日历点击事件
     * @param dateClick
     */
    public void setDateClick(MonthView.IDateClick dateClick){
        circleMonthView.setDateClick(dateClick);
    }

    /**
     * 设置星期的形式
     * @param weekString
     * 默认值	"日","一","二","三","四","五","六"
     */
    public void setWeekString(String[] weekString){
        weekView.setWeekString(weekString);
    }

    public void setCalendarInfos(List<CalendarInfo> calendarInfos){
        circleMonthView.setCalendarInfos(calendarInfos);
    }

    public void setDayTheme(IDayTheme theme){
        circleMonthView.setTheme(theme);
    }

    public void setWeekTheme(IWeekTheme weekTheme){
        weekView.setWeekTheme(weekTheme);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.left1){
            circleMonthView.onLeftClick();
        }else{
            circleMonthView.onRightClick();
        }
    }
}
