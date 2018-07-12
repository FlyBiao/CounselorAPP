package com.cesaas.android.counselor.order.member.volume;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.ui.activity.member.DateScreenActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.member.DateScreeningActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.adapter.volume.TicketReportAdapter;
import com.cesaas.android.counselor.order.member.bean.service.volume.ResultSumTickeUsetReportBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.ResultTickeUseInfotReportBean;
import com.cesaas.android.counselor.order.member.bean.service.volume.TickeUseInfotReportBean;
import com.cesaas.android.counselor.order.member.net.service.SumTickeUsetReportNet;
import com.cesaas.android.counselor.order.member.net.service.TickeUseInfotReportNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 券报表查询
 */
public class TicketReportActivity extends BasesActivity implements View.OnClickListener ,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tvTitle,mTextViewRightTitle;
    private TextView tv_screening,tv_not_data;
    private TextView tv_all,tv_bill,tv_audit;
    private TextView tv_start_sel_date,tv_end_sel_date;
    private LinearLayout ll_search_vip,ll_screening;
    private EditText et_search;
    private LinearLayout back;
    private TextView tv_send_sum,tv_get_sum,tv_use_num,tv_sales,tv_get_percen,tv_use_percen,tv_ticket_sums;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private int pageIndex=1;
    private int type=0;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private List<TickeUseInfotReportBean> mData=new ArrayList<>();
    private TicketReportAdapter adapter;

    private TickeUseInfotReportNet reportNet;
    private SumTickeUsetReportNet sumTickeUsetReportNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_report);

        initView();
        initData();
    }

    /**
     * 券分析追踪-列表
     * @param msg
     */
    public void onEventMainThread(ResultTickeUseInfotReportBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);

                mData=new ArrayList<>();
                mData.addAll(msg.TModel);

                if(isLoadMore==true){
                    adapter.addData(mData);
                    adapter.loadMoreComplete();
                    adapter.notifyDataSetChanged();
                }else{
                    adapter=new TicketReportAdapter(mData,mActivity,mContext);
                    mRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                adapter.setOnLoadMoreListener(this, mRecyclerView);
                mCurrentCounter = adapter.getData().size();

            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"获取券分析追踪-列表失败:"+msg.Message);
        }
    }

    /**
     * 券分析追踪-总计
     * @param msg
     */
    public void onEventMainThread(ResultSumTickeUsetReportBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                tv_ticket_sums.setText(String.valueOf(msg.RecordCount));
                tv_send_sum.setText(String.valueOf(msg.TModel.getCreateNum()));

                tv_get_sum.setText(String.valueOf(msg.TModel.getGetNum()));
                if(msg.TModel.getGetNum()!=0){
                    double getPercen=Double.parseDouble(String.valueOf(msg.TModel.getGetNum()))/Double.parseDouble(String.valueOf(msg.TModel.getCreateNum()))*100;
                    tv_get_percen.setText(DecimalFormatUtils.decimalToFormats(getPercen));
                }else{
                    tv_get_percen.setText(String.valueOf(0));
                }

                tv_use_num.setText(String.valueOf(msg.TModel.getUseNum()));
                if(msg.TModel.getUseNum()!=0){
                    double usePercen=Double.parseDouble(String.valueOf(msg.TModel.getUseNum()))/Double.parseDouble(String.valueOf(msg.TModel.getCreateNum()))*100;
                    tv_use_percen.setText(DecimalFormatUtils.decimalToFormats(usePercen));
                }else{
                    tv_use_percen.setText(String.valueOf(0));
                }

                tv_sales.setText("￥"+String.valueOf(msg.TModel.getPayMent()));
            }else{
                ToastFactory.getLongToast(mContext,"TModel=null");
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取券分析追踪总计失败:"+msg.Message);
        }
    }

    public void initData() {
        reportNet=new TickeUseInfotReportNet(mContext);
        reportNet.setData(tv_start_sel_date.getText().toString(),tv_end_sel_date.getText().toString(),"",pageIndex);

        sumTickeUsetReportNet=new SumTickeUsetReportNet(mContext);
        sumTickeUsetReportNet.setData(tv_start_sel_date.getText().toString(),tv_end_sel_date.getText().toString());
    }

    /**
     * 初始化视图控件
     */
    public void initView(){
        tv_ticket_sums= (TextView) findViewById(R.id.tv_ticket_sums);
        tv_use_percen= (TextView) findViewById(R.id.tv_use_percen);
        tv_get_percen= (TextView) findViewById(R.id.tv_get_percen);
        tv_sales= (TextView) findViewById(R.id.tv_sales);
        tv_use_num= (TextView) findViewById(R.id.tv_use_num);
        tv_get_sum= (TextView) findViewById(R.id.tv_get_sum);
        tv_send_sum= (TextView) findViewById(R.id.tv_send_sum);
        tv_start_sel_date= (TextView) findViewById(R.id.tv_start_sel_date);
        tv_start_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00")));
        tv_end_sel_date= (TextView) findViewById(R.id.tv_end_sel_date);
        tv_end_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59")));
        ll_screening= (LinearLayout) findViewById(R.id.ll_screening);
        tv_screening= (TextView) findViewById(R.id.tv_screening);
        tv_screening.setText(R.string.fa_glass);
        tv_screening.setTypeface(App.font);
        back= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("券分析追踪");
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setVisibility(View.GONE);
        mTextViewRightTitle.setText(R.string.fa_th_large);
        mTextViewRightTitle.setTypeface(App.font);
        mTextViewRightTitle.setTextSize(16);
        tv_all= (TextView) findViewById(R.id.tv_all);
        tv_all.setOnClickListener(this);
        tv_bill= (TextView) findViewById(R.id.tv_bill);
        tv_bill.setOnClickListener(this);
        tv_audit= (TextView) findViewById(R.id.tv_audit);
        tv_audit.setOnClickListener(this);
        tvs.add(tv_all);
        tvs.add(tv_bill);
        tvs.add(tv_audit);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_retail_order_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        ll_search_vip= (LinearLayout) findViewById(R.id.ll_search_vip);
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                }
                return false;
            }
        });
        ll_search_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tagIntent = new Intent(mContext, DateScreenActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_all:
                index=0;
                type=0;
                reportNet=new TickeUseInfotReportNet(mContext);
                reportNet.setData(tv_start_sel_date.getText().toString(),tv_end_sel_date.getText().toString(),"",pageIndex);
                break;
            case R.id.tv_bill:
                index=1;
                type=1;
                reportNet=new TickeUseInfotReportNet(mContext);
                reportNet.setData(tv_start_sel_date.getText().toString(),tv_end_sel_date.getText().toString(),"",pageIndex,1);
                break;
            case R.id.tv_audit:
                index=2;
                type=2;
                reportNet=new TickeUseInfotReportNet(mContext);
                reportNet.setData(tv_start_sel_date.getText().toString(),tv_end_sel_date.getText().toString(),"",pageIndex,0);
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

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter=new TicketReportAdapter(mData,mActivity,mContext);
        mRecyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (adapter.getData().size() < Constant.PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= totalSize) {
                //数据全部加载完毕
                adapter.loadMoreEnd(mLoadMoreEndGone);
            } else {
                if (isErr) {
                    //成功获取更多数据
                    isLoadMore=true;
                    pageIndex+=1;
                    reportNet=new TickeUseInfotReportNet(mContext);
                    reportNet.setData(tv_start_sel_date.getText().toString(),tv_end_sel_date.getText().toString(),"",pageIndex);
                } else {
                    //获取更多数据失败
                    isErr = true;
                    adapter.loadMoreFail();
                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//标签筛选
            if(data!=null){
                String startDate=data.getStringExtra("StartDate");
                String endDate=data.getStringExtra("EndDate");
                tv_start_sel_date.setText(startDate);
                tv_end_sel_date.setText(endDate);

                if(type==1){
                    reportNet=new TickeUseInfotReportNet(mContext);
                    reportNet.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59","",pageIndex,1);
                }else if(type==2){
                    reportNet=new TickeUseInfotReportNet(mContext);
                    reportNet.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59","",pageIndex,0);
                }else{
                    reportNet=new TickeUseInfotReportNet(mContext);
                    reportNet.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59","",pageIndex);
                }
            }
        }
    }
}
