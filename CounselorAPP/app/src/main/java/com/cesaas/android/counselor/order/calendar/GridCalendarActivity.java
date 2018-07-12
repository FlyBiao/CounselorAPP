package com.cesaas.android.counselor.order.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultSalesInfoBean;
import com.cesaas.android.counselor.order.net.GetOneMouthSaleNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ProgressbarWidget;
import com.cesaas.android.counselor.order.widget.ProgressbarWidget1;
import com.cesaas.android.number.calendar.component.MonthView;
import com.cesaas.android.number.calendar.entity.CalendarInfo;
import com.cesaas.android.number.calendar.entity.DateInfoBean;
import com.cesaas.android.number.calendar.views.GridCalendarView;

public class GridCalendarActivity extends BasesActivity {
    private GridCalendarView gridCalendarView;
    private LinearLayout shop_count_off_back;
    private TextView tv_sales_target,tv_complete_target;
    
    private ProgressbarWidget mProgress;
    private ProgressbarWidget1 tv_progressbar1;
	private int max = 100;
	private int current = 0;
	private int max1 = 100;
	private int current1 = 0;
	private int currYear;
	private int currMonth;
	private int currDay;
	private String lastDayOfMonth;
	
	private int currentDD;
	private String saleValue;//销售额
	private int completedTask;//完成任务
	private double completedTime;//完成时间
	private int taskTarget;//任务目标
	private double completedSaleProportion;//完成销售比例
	
	private String CountOffDate;
	private GetOneMouthSaleNet getOneMouthSaleNet;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_calendar_view);
        
        initView();
        initData();
    }
    
    /**
     * 初始化数据
     */
    private void initData() {
    	
    	//获取日历年月
		Calendar calendar = Calendar.getInstance();
	  	currYear= calendar.get(Calendar.YEAR);
	  	currMonth = calendar.get(Calendar.MONTH)+ 1;
	  	currDay=calendar.get(Calendar.DAY_OF_MONTH);
	  	lastDayOfMonth=AbDateUtil.getLastDayOfMonth("dd");
	  	
	  	getOneMouthSaleNet=new GetOneMouthSaleNet(mContext);
        getOneMouthSaleNet.setData(currYear, currMonth);
	  	
	  	completedTime=Double.parseDouble(currDay+"")/Double.parseDouble(lastDayOfMonth)*100;
        
    	//点击当前日期跳转
        gridCalendarView.setDateClick(new MonthView.IDateClick(){
            @Override
            public void onClickOnDate(int year, int month, int day) {
            	
            	CountOffDate=year + "-" + month + "-" + day;
            	Bundle bundle=new Bundle();
            	bundle.putString("CountOffDate", CountOffDate);
            	Skip.mNextFroData(mActivity, CountOffDetailActivity.class,bundle);
            }
        });
        
	}

    /**
     * 初始化视图控件
     */
	private void initView() {
		
		tv_progressbar1=(ProgressbarWidget1) findViewById(R.id.tv_progressbar1);
		mProgress=(ProgressbarWidget)findViewById(R.id.tv_progressbar);
		
		//点击获取当前日期
        gridCalendarView = (GridCalendarView) findViewById(R.id.gridMonthView);
		//任务目标
		tv_sales_target=(TextView) findViewById(R.id.tv_sales_target);
		//完成目标
		tv_complete_target=(TextView) findViewById(R.id.tv_complete_target);
		
		//点击返回
        shop_count_off_back=(LinearLayout) findViewById(R.id.shop_count_off_back);
        shop_count_off_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
	public void onEventMainThread(DateInfoBean msg) {
//		if(){
//			
//		}
//		getOneMouthSaleNet=new GetOneMouthSaleNet(mContext);
//        getOneMouthSaleNet.setData(msg.getYear(), msg.getMonth());
	}

	/**
	 * 获取EventBus订阅事件
	 * @param msg
	 */
	public void onEventMainThread(ResultSalesInfoBean msg) {
    	if(msg.IsSuccess==true){
    		List<CalendarInfo> list=new ArrayList<CalendarInfo>();
    		
    		for (int i = 0; i < msg.TModel.SaleInfo.size(); i++) {
    			//本月某一天
    			currentDD=Integer.parseInt(AbDateUtil.toDateD(msg.TModel.SaleInfo.get(i).date));
    			//销售额
    			saleValue=Integer.toString(msg.TModel.SaleInfo.get(i).SaleValue);
    			//设置日期事务
    	        list.add(new CalendarInfo(currYear,currMonth,currentDD,"￥"+saleValue));
    	        gridCalendarView.setCalendarInfos(list);
    	        
    	        //已完成任务
    	        completedTask+=msg.TModel.SaleInfo.get(i).SaleValue;
    			
    			for (int j = 0; j < msg.TModel.SaleInfo.get(i).Competitor.size(); j++) {
    				Log.i("TModel", msg.TModel.SaleInfo.get(i).Competitor.get(j).Name+"=");
    			}
    		}
    		
    		taskTarget=msg.TModel.Target;
    		//任务目标值
    		tv_sales_target.setText(""+taskTarget);
    		//已完成任务值
    		tv_complete_target.setText(completedTask+"");
    		//销售百分比值
    		completedSaleProportion=Double.parseDouble(completedTask+"")/Double.parseDouble(taskTarget+"")*100;
    		
    		//设置报数百分比进度条
            //时间
    		if (current <= max) {
    			mProgress.setMaxValue(max);
    			mProgress.setCurrentValue(Float.parseFloat(DecimalFormatUtils.decimalToFormat(completedTime)));
    		}
    		
    		//销售
    		if (current1 <= max1) {
    			tv_progressbar1.setMaxValue(max1);
    			tv_progressbar1.setCurrentValue(Float.parseFloat(DecimalFormatUtils.decimalToFormat(completedSaleProportion)));
    		}
    		
    	}else{
    		ToastFactory.getLongToast(mContext, "获取数据失败!"+msg.Message);
    	}
    }
}
