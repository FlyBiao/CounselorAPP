package com.cesaas.android.java.ui.activity.review;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.adapter.review.ReViewAdapter;
import com.cesaas.android.java.bean.review.GetEvaluationModelBean;
import com.cesaas.android.java.bean.review.ResultGetEvaluationModelBean;
import com.cesaas.android.java.bean.review.ResultGetRatecontentAppBean;
import com.cesaas.android.java.net.review.GetEvaluationModelNet;
import com.cesaas.android.java.net.review.GetRatecontentAppNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Author FGB
 * Description 评价
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class ReviewActivity extends BasesActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle,tv_user_name,tv_user_level,tv_shop_name,tv_not_data;
    private TextView tv_custom_review,tv_today_review,tv_week_review,tv_month_review;
    private TextView tv_score1,tv_score2,tv_score3,tv_score4,tv_score5,tv_points_icon,tv_amount_icon,tv_review_sum,tv_user_points,tv_user_amount,tv_all_review;
    private TextView tv_review_score1,tv_review_score2,tv_review_score3,tv_review_score4,tv_review_score5;
    private TextView tv_score_count1,tv_score_count2,tv_score_count3,tv_score_count4,tv_score_count5;
    private ProgressBar pb_score1,pb_score2,pb_score3,pb_score4,pb_score5;
    private LinearLayout ll_score1,ll_score2,ll_score3,ll_score4,ll_score5;
    private LinearLayout llBack;
    private CircleImageView userIcon;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private int index=2;
    private int indexStart=-1;
    private int selectType=2;
    private int CounselorId;

    private int scoreValue1,scoreValue2,scoreValue3,scoreValue4,scoreValue5;
    private String startDate;
    private String endDate;

    private ArrayList<TextView> tvs=new ArrayList<>();
    private ArrayList<TextView> tvStart=new ArrayList<>();


    private GetEvaluationModelNet getEvaluationModelNet;
    private GetRatecontentAppNet getRatecontentAppNet;

    private UserBean userBean;
    private List<GetEvaluationModelBean> mData=new ArrayList<>();
    private ReViewAdapter reViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Bundle bundle=getIntent().getExtras();
        userBean= (UserBean) bundle.getSerializable("userBean");
        CounselorId=Integer.parseInt(userBean.CounselorId);

        initView();
        initData();
    }

    /**
     * 接收查看导购评价
     * @param msg
     */
    public void onEventMainThread(ResultGetRatecontentAppBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp != null && !"".equals(msg.arguments.resp)) {
                    tv_review_sum.setText(String.valueOf(msg.arguments.resp.getAvgTotalValue()));
                    tv_user_points.setText(String.valueOf(msg.arguments.resp.getTotalCount()));
                    tv_user_amount.setText(String.valueOf(msg.arguments.resp.getTotalScore()));
                    switch (msg.arguments.resp.getAvgTotalValue()) {
                        case 1:
                            tv_score1.setText(R.string.fa_star);
                            break;
                        case 2:
                            tv_score1.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            break;
                        case 3:
                            tv_score1.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            tv_score3.setText(R.string.fa_star);
                            break;
                        case 4:
                            tv_score1.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            tv_score3.setText(R.string.fa_star);
                            tv_score4.setText(R.string.fa_star);
                            break;
                        case 5:
                            tv_score1.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            tv_score3.setText(R.string.fa_star);
                            tv_score4.setText(R.string.fa_star);
                            tv_score5.setText(R.string.fa_star);
                            break;
                    }

                    for (int i = 0; i < msg.arguments.resp.records.value.size(); i++) {
                        switch (msg.arguments.resp.records.value.get(i).getTotalValue()) {
                            case 1:
                                scoreValue1 = msg.arguments.resp.records.value.get(i).getTotalValue();
                                tv_score_count1.setText(String.valueOf(msg.arguments.resp.records.value.get(i).getCount()));
                                double pb1 = Double.parseDouble(tv_score_count1.getText().toString()) / Double.parseDouble(tv_user_amount.getText().toString()) * 100;
                                pb_score1.setProgress((int) DecimalFormatUtils.decimalFormatRound(pb1));
                                break;
                            case 2:
                                scoreValue2 = msg.arguments.resp.records.value.get(i).getTotalValue();
                                tv_score_count2.setText(String.valueOf(msg.arguments.resp.records.value.get(i).getCount()));
                                double pb2 = Double.parseDouble(tv_score_count2.getText().toString()) / Double.parseDouble(tv_user_amount.getText().toString()) * 100;
                                pb_score2.setProgress((int) DecimalFormatUtils.decimalFormatRound(pb2));
                                break;
                            case 3:
                                scoreValue3 = msg.arguments.resp.records.value.get(i).getTotalValue();
                                tv_score_count3.setText(String.valueOf(msg.arguments.resp.records.value.get(i).getCount()));
                                double pb3 = Double.parseDouble(tv_score_count3.getText().toString()) / Double.parseDouble(tv_user_amount.getText().toString()) * 100;
                                pb_score3.setProgress((int) DecimalFormatUtils.decimalFormatRound(pb3));
                                break;
                            case 4:
                                scoreValue4 = msg.arguments.resp.records.value.get(i).getTotalValue();
                                tv_score_count4.setText(String.valueOf(msg.arguments.resp.records.value.get(i).getCount()));
                                double pb4 = Double.parseDouble(tv_score_count4.getText().toString()) / Double.parseDouble(tv_user_amount.getText().toString()) * 100;
                                pb_score4.setProgress((int) DecimalFormatUtils.decimalFormatRound(pb4));
                                break;
                            case 5:
                                scoreValue5 = msg.arguments.resp.records.value.get(i).getTotalValue();
                                tv_score_count5.setText(String.valueOf(msg.arguments.resp.records.value.get(i).getCount()));
                                double pb5 = Double.parseDouble(tv_score_count5.getText().toString()) / Double.parseDouble(tv_user_amount.getText().toString()) * 100;
                                pb_score5.setProgress((int) DecimalFormatUtils.decimalFormatRound(pb5));
                                break;
                        }
                    }
                }
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收查看多个评价
     * @param msg
     */
    public void onEventMainThread(ResultGetEvaluationModelBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
            recyclerView.setVisibility(View.GONE);
            tv_not_data.setVisibility(View.VISIBLE);
        }else{
            if(msg.arguments!=null && msg.arguments.resp.errorCode==1){
                if(msg.arguments.resp.records!=null && msg.arguments.resp.records.value.size()!=0){
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mData=new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);

                    reViewAdapter=new ReViewAdapter(mData,mContext,mActivity);
                    recyclerView.setAdapter(reViewAdapter);

                }else{
                    recyclerView.setVisibility(View.GONE);
                    tv_not_data.setVisibility(View.VISIBLE);
                }
            }else{
                recyclerView.setVisibility(View.GONE);
                tv_not_data.setVisibility(View.VISIBLE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    private void initView() {
        tv_score_count1= (TextView) findViewById(R.id.tv_score_count1);
        tv_score_count2= (TextView) findViewById(R.id.tv_score_count2);
        tv_score_count3= (TextView) findViewById(R.id.tv_score_count3);
        tv_score_count4= (TextView) findViewById(R.id.tv_score_count4);
        tv_score_count5= (TextView) findViewById(R.id.tv_score_count5);

        ll_score1= (LinearLayout) findViewById(R.id.ll_score1);
        ll_score2= (LinearLayout) findViewById(R.id.ll_score2);
        ll_score3= (LinearLayout) findViewById(R.id.ll_score3);
        ll_score4= (LinearLayout) findViewById(R.id.ll_score4);
        ll_score5= (LinearLayout) findViewById(R.id.ll_score5);

        tv_all_review= (TextView) findViewById(R.id.tv_all_review);
        tv_user_amount= (TextView) findViewById(R.id.tv_user_amount);
        tv_user_points= (TextView) findViewById(R.id.tv_user_points);
        tv_review_sum= (TextView) findViewById(R.id.tv_review_sum);
        tv_points_icon= (TextView) findViewById(R.id.tv_points_icon);
        tv_points_icon.setText(R.string.fa_file_text_o);
        tv_points_icon.setTypeface(App.font);

        tv_amount_icon= (TextView) findViewById(R.id.tv_amount_icon);
        tv_amount_icon.setText(R.string.fa_database);
        tv_amount_icon.setTypeface(App.font);

        tv_score1= (TextView) findViewById(R.id.tv_score1);
        tv_score1.setText(R.string.fa_star_o);
        tv_score1.setTypeface(App.font);
        tv_score2= (TextView) findViewById(R.id.tv_score2);
        tv_score2.setText(R.string.fa_star_o);
        tv_score2.setTypeface(App.font);
        tv_score3= (TextView) findViewById(R.id.tv_score3);
        tv_score3.setText(R.string.fa_star_o);
        tv_score3.setTypeface(App.font);
        tv_score4= (TextView) findViewById(R.id.tv_score4);
        tv_score4.setText(R.string.fa_star_o);
        tv_score4.setTypeface(App.font);
        tv_score5= (TextView) findViewById(R.id.tv_score5);
        tv_score5.setText(R.string.fa_star_o);
        tv_score5.setTypeface(App.font);

        tv_review_score1= (TextView) findViewById(R.id.tv_review_score1);
        tv_review_score1.setText(R.string.fa_star);
        tv_review_score1.setTypeface(App.font);
        tv_review_score2= (TextView) findViewById(R.id.tv_review_score2);
        tv_review_score2.setText(R.string.fa_star);
        tv_review_score2.setTypeface(App.font);
        tv_review_score3= (TextView) findViewById(R.id.tv_review_score3);
        tv_review_score3.setText(R.string.fa_star);
        tv_review_score3.setTypeface(App.font);
        tv_review_score4= (TextView) findViewById(R.id.tv_review_score4);
        tv_review_score4.setText(R.string.fa_star);
        tv_review_score4.setTypeface(App.font);
        tv_review_score5= (TextView) findViewById(R.id.tv_review_score5);
        tv_review_score5.setText(R.string.fa_star);
        tv_review_score5.setTypeface(App.font);
        tvStart.add(tv_review_score1);
        tvStart.add(tv_review_score2);
        tvStart.add(tv_review_score3);
        tvStart.add(tv_review_score4);
        tvStart.add(tv_review_score5);

        tv_month_review= (TextView) findViewById(R.id.tv_month_review);
        tv_month_review.setOnClickListener(this);
        tv_today_review= (TextView) findViewById(R.id.tv_today_review);
        tv_today_review.setOnClickListener(this);
        tv_custom_review= (TextView) findViewById(R.id.tv_custom_review);
        tv_custom_review.setOnClickListener(this);
        tv_week_review= (TextView) findViewById(R.id.tv_week_review);
        tv_week_review.setOnClickListener(this);
        tvs.add(tv_custom_review);
        tvs.add(tv_today_review);
        tvs.add(tv_week_review);
        tvs.add(tv_month_review);

        pb_score1= (ProgressBar) findViewById(R.id.pb_score1);
        pb_score2= (ProgressBar) findViewById(R.id.pb_score2);
        pb_score3= (ProgressBar) findViewById(R.id.pb_score3);
        pb_score4= (ProgressBar) findViewById(R.id.pb_score4);
        pb_score5= (ProgressBar) findViewById(R.id.pb_score5);

        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("对"+userBean.Name+"的评价");
        tv_user_name= (TextView) findViewById(R.id.tv_user_name);
        tv_user_level= (TextView) findViewById(R.id.tv_user_level);
        tv_shop_name= (TextView) findViewById(R.id.tv_shop_name);
        userIcon= (CircleImageView) findViewById(R.id.ivw_user_icon);
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });

        initClick();
    }

    public void initData() {
        tv_user_name.setText(userBean.Name);
        tv_user_level.setText(userBean.TypeName);
        tv_shop_name.setText(userBean.ShopName);
        if(userBean.Icon!=null && !"".equals(userBean.Icon)){
            Glide.with(mContext).load(userBean.Icon).into(userIcon);
        }else{
            userIcon.setImageResource(R.mipmap.not_user_icon);
        }

        getRatecontentAppNet=new GetRatecontentAppNet(mContext);
        getRatecontentAppNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek(),CounselorId));

        getEvaluationModelNet=new GetEvaluationModelNet(mContext);
        getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek(),CounselorId));

    }

    public void initClick(){
        tv_all_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<tvStart.size();i++){
                    tvStart.get(i).setTextColor(mContext.getResources().getColor(R.color.hui));
                }
                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getId(CounselorId));
            }
        });
        ll_score1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexStart=0;
                setStartColor(indexStart);
                setSelectType(indexStart);
            }
        });
        ll_score2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexStart=1;
                setStartColor(indexStart);
                setSelectType(indexStart);
            }
        });
        ll_score3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexStart=2;
                setStartColor(indexStart);
                setSelectType(indexStart);
            }
        });
        ll_score4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexStart=3;
                setStartColor(indexStart);
                setSelectType(indexStart);
            }
        });
        ll_score5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexStart=4;
                setStartColor(indexStart);
                setSelectType(indexStart);
            }
        });
    }

    private void setStartColor(int indexStart) {
        for(int i=0;i<tvStart.size();i++){
            tvStart.get(i).setTextColor(mContext.getResources().getColor(R.color.hui));
        }
        tvStart.get(indexStart).setTextColor(mContext.getResources().getColor(R.color.red));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_custom_review:
                index=0;
                selectType=0;
                BaseDialog d=new BaseDialog(mContext);
                d.setCancelable(false);
                d.show();
                break;
            case R.id.tv_today_review:
                index=1;
                selectType=1;
                getRatecontentAppNet=new GetRatecontentAppNet(mContext);
                getRatecontentAppNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"),CounselorId));

                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"),CounselorId));
                break;
            case R.id.tv_week_review:
                index=2;
                selectType=2;
                getRatecontentAppNet=new GetRatecontentAppNet(mContext);
                getRatecontentAppNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek(),CounselorId));

                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek(),CounselorId));
                break;
            case R.id.tv_month_review:
                index=3;
                selectType=3;
                getRatecontentAppNet=new GetRatecontentAppNet(mContext);
                getRatecontentAppNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"),CounselorId));

                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"),CounselorId));
                break;
        }
        setColor(index);
    }

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.new_menu_text_color));
            tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_purple_bg));
    }


    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(ReviewActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(getResources().getColor(R.color.new_base_bg));
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
        if(dateType==1){
            startDate= year + "-" + (++monthOfYear) + "-" + dayOfMonth+" 00:00:00";
            tv_start_date.setText(AbDateUtil.getDateYMDs(startDate));
        }else{
            endDate= year + "-" + (++monthOfYear) + "-" + dayOfMonth+" 23:59:59";
            tv_end_date.setText(AbDateUtil.getDateYMDs(endDate));
        }
    }

    public void setSelectType(int index){
        switch (selectType){
            case 0:
                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndIdTotalValue(startDate,endDate,CounselorId,index+1));
                break;
            case 1:
                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndIdTotalValue(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"),CounselorId,index+1));
                break;
            case 2:
                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndIdTotalValue(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek(),CounselorId,index+1));
                break;
            case 3:
                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndIdTotalValue(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"),CounselorId,index+1));
                break;
            default:
                getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndIdTotalValue(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek(),CounselorId,index+1));
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        reViewAdapter=new ReViewAdapter(mData,mContext,mActivity);
        reViewAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                reViewAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }




    TextView tvCancel,tvSure;
    TextView tv_start_date,tv_end_date;
    private int dateType;
    public class BaseDialog extends Dialog implements View.OnClickListener {

        public BaseDialog(Context context) {
            this(context, R.style.dialog);
        }

        public BaseDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_date);

            tv_end_date = (TextView) findViewById(R.id.tv_end_date);
            tv_end_date.setOnClickListener(this);
            tv_start_date = (TextView) findViewById(R.id.tv_start_date);
            tv_start_date.setOnClickListener(this);
            tvSure = (TextView) findViewById(R.id.tv_sure);
            tvSure.setOnClickListener(this);
            tvCancel = (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);
        }

        public void mInitShow() {
            show();
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_start_date:
                    dateType=1;
                    getDateSelect(tv_start_date);
                    break;
                case R.id.tv_end_date:
                    dateType=2;
                    getDateSelect(tv_end_date);
                    break;
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    dismiss();
                    getRatecontentAppNet=new GetRatecontentAppNet(mContext);
                    getRatecontentAppNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(startDate,endDate,CounselorId));

                    getEvaluationModelNet=new GetEvaluationModelNet(mContext);
                    getEvaluationModelNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(startDate,endDate,CounselorId));
                    break;
            }
        }
    }
}
