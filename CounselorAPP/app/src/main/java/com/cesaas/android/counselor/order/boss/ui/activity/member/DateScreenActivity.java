package com.cesaas.android.counselor.order.boss.ui.activity.member;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.adapter.member.CurrentTimeAdapter;
import com.cesaas.android.counselor.order.boss.adapter.member.CurrentTimesAdapter;
import com.cesaas.android.counselor.order.boss.bean.member.TimeDataUtils;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.tag.OnTagSelectListener;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.utils.DateTools;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateScreenActivity extends BasesActivity implements View.OnClickListener ,DatePickerDialog.OnDateSetListener {

    private TextView tvTitle,mTextViewRightTitle,tv_day_sum,tv_cancel;
    private TextView tv_start_date,tv_end_date,tv_start_query_date,tv_end_query_date,tv_sure;
    private LinearLayout ll_base_title_back;
    private FlowTagLayout flow_c_layout;
    private FlowTagLayout flow_cs_layout;

    private int selectDate=0;
    private int resultCode = 901;

    private CurrentTimeAdapter currentTimeAdapter;
    private CurrentTimesAdapter currentTimesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_screen);
        initView();
        initData();
    }

    private void initView() {
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        tv_day_sum= (TextView) findViewById(R.id.tv_day_sum);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("选择时间范围");
        tv_start_query_date= (TextView) findViewById(R.id.tv_start_query_date);
        tv_start_query_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00")));
        tv_end_query_date= (TextView) findViewById(R.id.tv_end_query_date);
        tv_end_query_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59")));
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setOnClickListener(this);
        ll_base_title_back= (LinearLayout) findViewById(R.id.ll_base_title_back);
        ll_base_title_back.setOnClickListener(this);

        flow_c_layout= (FlowTagLayout) findViewById(R.id.flow_c_layout);
        flow_c_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        flow_cs_layout= (FlowTagLayout) findViewById(R.id.flow_cs_layout);
        flow_cs_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);

        tv_start_date= (TextView) findViewById(R.id.tv_start_date);
        tv_start_date.setOnClickListener(this);
        tv_end_date= (TextView) findViewById(R.id.tv_end_date);
        tv_end_date.setOnClickListener(this);
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                break;
            case R.id.tv_start_date:
                selectDate=0;
                getDateSelect(tv_start_date);
                break;
            case R.id.tv_end_date:
                selectDate=1;
                getDateSelect(tv_end_date);
                break;
            case R.id.tv_cancel:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_sure:
                if(!TextUtils.isEmpty(tv_start_query_date.getText().toString()) && !TextUtils.isEmpty(tv_end_query_date.getText().toString())){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("StartDate",tv_start_query_date.getText().toString());
                    mIntent.putExtra("EndDate",tv_end_query_date.getText().toString());
                    setResult(resultCode, mIntent);// 设置结果，并进行传送
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择开始时间和结束时间！");
                }

                break;
        }
    }

    public void initData(){
        currentTimeAdapter=new CurrentTimeAdapter(mContext);
        flow_c_layout.setAdapter(currentTimeAdapter);
        currentTimeAdapter.onlyAddAll(TimeDataUtils.getCurrentDate());
        flow_c_layout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
                tv_start_query_date.setText(AbDateUtil.getDateYMDs(TimeDataUtils.getCurrentDate().get(positoin).getStartDate()));
                tv_end_query_date.setText(AbDateUtil.getDateYMDs(TimeDataUtils.getCurrentDate().get(positoin).getEndDate()));
            }
        });

        currentTimesAdapter=new CurrentTimesAdapter(mContext);
        flow_cs_layout.setAdapter(currentTimesAdapter);
        currentTimesAdapter.onlyAddAll(TimeDataUtils.getCurrentDates());
        flow_cs_layout.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, int positoin, List<Integer> selectedList) {
//                ToastFactory.getLongToast(mContext,TimeDataUtils.getCurrentDates().get(positoin).getDateTitle());
            }
        });


        String strDate = "2018-07-03";

        Date date = DateTools.parseDate(strDate);
        System.out.println(strDate + " 是第几季度？" + DateTools.getSeason(date));
        System.out.println(strDate + " 所在季度月份？"
                + DateTools.formatDate(DateTools.getSeasonDate(date)[0], "yyyy年MM月") + "/"
                + DateTools.formatDate(DateTools.getSeasonDate(date)[1], "yyyy年MM月") + "/"
                + DateTools.formatDate(DateTools.getSeasonDate(date)[2], "yyyy年MM月"));
        System.out.println(strDate + " 所在季度最后一天日期？"
                + DateTools.formatDate(DateTools.getLastDateOfSeason(date)));

    }

    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(DateScreenActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(getResources().getColor(R.color.darkcyan));
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
        switch (selectDate){
            case 0://开始日期
                tv_start_date.setText(date);
                tv_start_query_date.setText(date);
                tv_end_query_date.setText("");
                break;
            case 1://结束日期
                tv_end_date.setText(date);
                tv_end_query_date.setText(date);
                break;
        }
    }
}
