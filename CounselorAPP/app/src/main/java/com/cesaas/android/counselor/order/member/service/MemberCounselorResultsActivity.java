package com.cesaas.android.counselor.order.member.service;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultAddCounselorMonthGoalBean;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultGetListCounselorDayGoalBean;
import com.cesaas.android.counselor.order.member.bean.service.results.ResultGetShopGoalBean;
import com.cesaas.android.counselor.order.member.net.results.AddCounselorDayGoalNet;
import com.cesaas.android.counselor.order.member.net.results.GetCounselorMonthGoalNet;
import com.cesaas.android.counselor.order.member.net.results.GetListCounselorDayGoalNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.jmavarez.materialcalendar.CalendarView;
import com.jmavarez.materialcalendar.Interface.OnDateChangedListener;
import com.jmavarez.materialcalendar.Interface.OnMonthChangedListener;
import com.jmavarez.materialcalendar.Util.CalendarDay;
import com.jmavarez.materialcalendar.Util.CalendarUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;


/**
 * 个人业绩分配
 */
public class MemberCounselorResultsActivity extends BasesActivity implements View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener {
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle,tv_sure;
    private TextView tv_sales_icon,tv_surpass_icon,tv_card_icon;
    private TextView tv_year,tv_month,tv_select_day,tv_select_month,tv_set_month,tv_set_day;
    private EditText et_sales,et_surpass,et_card;
    private TextView tv_month_sales_target,tv_month_complete_sales_target,tv_month_surpass_target,tv_month_complete_surpass_target,tv_month_card_target,tv_month_complete_card_target;

    CalendarView calendarView;
    HashSet<CalendarDay> calendarDays;

    private double salesTargetSum;
    private double suroassTargetSum;
    private int cardSum;
    private int CounselorId;
    private String ymd;

    private GetListCounselorDayGoalNet getListCounselorDayGoalNet;
    private AddCounselorDayGoalNet addCounselorDayGoalNet;
    private GetCounselorMonthGoalNet getCounselorMonthGoalNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_counselor_results);
        initView();
        initData(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"));
    }

    public void onEventMainThread(ResultAddCounselorMonthGoalBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"设置目标成功！");
            if(ymd!=null){
                initData(ymd);
            }else{
                initData(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"));
            }
        }else{
            ToastFactory.getLongToast(mContext,"设置目标失败:"+msg.Message);
        }
    }

    /**
     * 接收查询店员每日目标分配情况
     * @param msg
     */
    public void onEventMainThread(ResultGetListCounselorDayGoalBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                calendarDays = new HashSet<>();
                //Calendar indicators
                for (int i = 0; i < msg.TModel.size(); i++) {
                    if (msg.TModel.get(i).getIsSet()==1) {
                        CalendarDay day = CalendarDay.from(i+1,Integer.parseInt(AbDateUtil.toMarch(AbDateUtil.getCurrentDate(msg.TModel.get(i).getDate()))), Integer.parseInt(AbDateUtil.toyear(AbDateUtil.getCurrentDate(msg.TModel.get(i).getDate()))));
                        calendarDays.add(day);
                    }
                    if(msg.TModel.get(i).getDate().equals(ymd)){
                        et_card.setText(msg.TModel.get(i).getCardTarget()+"");
                        et_sales.setText(msg.TModel.get(i).getSalesTarget()+"");
                        et_surpass.setText(msg.TModel.get(i).getSalesSurpass()+"");

                        salesTargetSum=msg.TModel.get(i).getSalesTarget();
                        suroassTargetSum=msg.TModel.get(i).getSalesSurpass();
                        cardSum=msg.TModel.get(i).getCardTarget();
                    }

                }
                calendarView.addEvents(calendarDays);
                calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
                    @Override
                    public void onMonthChanged(Date date) {
                        String month = new SimpleDateFormat("MMMM").format(date);
                        tv_select_month.setText(month);
                        tv_set_month.setText(month);
                    }
                });

                calendarView.setOnDateChangedListener(new OnDateChangedListener() {
                    @Override
                    public void onDateChanged(Date date) {
                        String d = new SimpleDateFormat("dd").format(date);
                        tv_select_day.setText(d+"日");
                        tv_set_day.setText(d+"");

                        ymd= new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(date);
                        initData(ymd);
                    }
                });
            }
        }else{
            ToastFactory.getLongToast(mContext,"查询店员每日目标分配情况失败:"+msg.Message);
        }
    }


    /**
     * 接收查询店铺月度目标
     * @param msg
     */
    public void onEventMainThread(ResultGetShopGoalBean msg) {
        if (msg.IsSuccess!=false) {
            if(msg.TModel!=null){
                tv_month_sales_target.setText("￥"+msg.TModel.getSalesTarget());
                tv_month_surpass_target.setText("￥"+msg.TModel.getSalesSurpass());
                tv_month_card_target.setText(msg.TModel.getCardTarget()+"");
                if(salesTargetSum!=0){
                    if(msg.TModel.getSalesTarget()>salesTargetSum){
                        tv_month_complete_sales_target.setText("-"+(msg.TModel.getSalesTarget()-salesTargetSum));
                    }else{
                        tv_month_complete_sales_target.setText("+"+(salesTargetSum-msg.TModel.getSalesTarget()));
                    }
                }else{
                    tv_month_complete_sales_target.setText("- ￥"+msg.TModel.getSalesTarget());
                }
                if(suroassTargetSum!=0){
                    if(msg.TModel.getSalesSurpass()>suroassTargetSum){
                        tv_month_complete_surpass_target.setText("- ￥"+(msg.TModel.getSalesSurpass()-suroassTargetSum));
                    }else{
                        tv_month_complete_surpass_target.setText("+ ￥"+(suroassTargetSum-msg.TModel.getSalesSurpass()));
                    }
                }else{
                    tv_month_complete_surpass_target.setText("- ￥"+msg.TModel.getSalesSurpass());
                }
                if(cardSum!=0){
                    if(msg.TModel.getCardTarget()>cardSum){
                        tv_month_complete_card_target.setText("-"+(msg.TModel.getCardTarget()-cardSum));
                    }else{
                        tv_month_complete_card_target.setText("+"+(cardSum-msg.TModel.getCardTarget()));
                    }
                }else{
                    tv_month_complete_card_target.setText("-"+msg.TModel.getCardTarget());
                }
            }
        }else{
            ToastFactory.getLongToast(mContext,"接收查询店铺月度目标失败:"+msg.Message);
        }
    }

    public void initView(){
        tv_sales_icon= (TextView) findViewById(R.id.tv_sales_icon);
        tv_sales_icon.setText(R.string.fa_dot_circle);
        tv_sales_icon.setTypeface(App.font);
        tv_surpass_icon= (TextView) findViewById(R.id.tv_surpass_icon);
        tv_surpass_icon.setText(R.string.fa_copyright);
        tv_surpass_icon.setTypeface(App.font);
        tv_card_icon= (TextView) findViewById(R.id.tv_card_icon);
        tv_card_icon.setText(R.string.user);
        tv_card_icon.setTypeface(App.font);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setIndicatorsVisibility(true);
        tv_set_month= (TextView) findViewById(R.id.tv_set_month);
        tv_set_month.setText(Integer.parseInt(AbDateUtil.toMarch(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))+"月");
        tv_set_day= (TextView) findViewById(R.id.tv_set_day);
        tv_set_day.setText(Integer.parseInt(AbDateUtil.toDay(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))+"日");
        tv_select_month= (TextView) findViewById(R.id.tv_select_month);
        tv_select_month.setText(Integer.parseInt(AbDateUtil.toMarch(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))+"月");
        tv_select_day= (TextView) findViewById(R.id.tv_select_day);
        tv_select_day.setText(Integer.parseInt(AbDateUtil.toDay(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))+"日");
        et_sales= (EditText) findViewById(R.id.et_sales);
        et_surpass= (EditText) findViewById(R.id.et_surpass);
        et_card= (EditText) findViewById(R.id.et_card);
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
        tv_month= (TextView) findViewById(R.id.tv_month);
        tv_month.setText(Integer.parseInt(AbDateUtil.toMarch(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss")))+"月");
        tv_year= (TextView) findViewById(R.id.tv_year);
        tv_year.setText(AbDateUtil.toyear(AbDateUtil.getCurrentDate("yyyy/MM/dd HH:mm:ss"))+"年");
        tv_month_sales_target= (TextView) findViewById(R.id.tv_month_sales_target);
        tv_month_complete_sales_target= (TextView) findViewById(R.id.tv_month_complete_sales_target);
        tv_month_surpass_target= (TextView) findViewById(R.id.tv_month_surpass_target);
        tv_month_complete_surpass_target= (TextView) findViewById(R.id.tv_month_complete_surpass_target);
        tv_month_card_target= (TextView) findViewById(R.id.tv_month_card_target);
        tv_month_complete_card_target= (TextView) findViewById(R.id.tv_month_complete_card_target);

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewTitle.setText("设置我的天目标 "+prefs.getString("Name"));

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(String date){
        getListCounselorDayGoalNet=new GetListCounselorDayGoalNet(mContext);
        getListCounselorDayGoalNet.setData(date,Integer.parseInt(prefs.getString("CounselorId")));

        getCounselorMonthGoalNet=new GetCounselorMonthGoalNet(mContext);
        getCounselorMonthGoalNet.setData(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_sure:
                addCounselorDayGoalNet=new AddCounselorDayGoalNet(mContext);
                if(ymd!=null){
                    addCounselorDayGoalNet.setData(ymd,et_sales.getText().toString(),et_surpass.getText().toString(),et_card.getText().toString());
                }else{
                    addCounselorDayGoalNet.setData(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),et_sales.getText().toString(),et_surpass.getText().toString(),et_card.getText().toString());
                }
                break;
        }
    }

    @Override
    public void onRefresh() {

    }
}
