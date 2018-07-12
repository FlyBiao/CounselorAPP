package com.cesaas.android.order.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.adapter.retail.RetailOrderAdapter;
import com.cesaas.android.order.bean.retail.ResultRetailOrderBean;
import com.cesaas.android.order.bean.retail.RetailOrderBean;
import com.cesaas.android.order.net.retail.PosRetailOrderListNet;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 零售单查询
 */
public class RetailOrderActivity extends BasesActivity implements View.OnClickListener ,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tvTitle,mTextViewRightTitle;
    private TextView tv_screening,tv_not_data;
    private TextView tv_all,tv_bill,tv_audit;
    private TextView tv_start_sel_date,tv_end_sel_date;
    private LinearLayout ll_search_vip;
    private EditText et_search;
    private LinearLayout back;
    private LinearLayout ll_screening;

    private int index=-1;
    private int indexPage=1;
    private String startDate;
    private String  endDate;
    private boolean isSelectDate=false;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private List<RetailOrderBean> mData=new ArrayList<>();
    private RetailOrderAdapter adapter;
    private PosRetailOrderListNet net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_order);

        initView();
        initData();
    }

    public void initData() {
        net=new PosRetailOrderListNet(mContext);
        net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59",indexPage);
    }

    public void onEventMainThread(ResultRetailOrderBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_data.setVisibility(View.GONE);
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);

                adapter=new RetailOrderAdapter(mData,mActivity,mContext);
                mRecyclerView.setAdapter(adapter);
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(mContext,"获取零售单数据失败:"+msg.Message);
        }
    }

    /**
     * 初始化视图控件
     */
    public void initView(){
        tv_start_sel_date= (TextView) findViewById(R.id.tv_start_sel_date);
        tv_start_sel_date.setText(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"));
        tv_end_sel_date= (TextView) findViewById(R.id.tv_end_sel_date);
        tv_end_sel_date.setText(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        ll_screening= (LinearLayout) findViewById(R.id.ll_screening);
        ll_screening.setOnClickListener(this);
        tv_screening= (TextView) findViewById(R.id.tv_screening);
        tv_screening.setText(R.string.fa_glass);
        tv_screening.setTypeface(App.font);
        back= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("零售单");
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        ll_screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tagIntent = new Intent(mContext, DateScreenActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_all:
                index=0;
                net=new PosRetailOrderListNet(mContext);
                net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59",indexPage);
                break;
            case R.id.tv_bill:
                index=1;
                net=new PosRetailOrderListNet(mContext);
                net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59",indexPage,1,1);
                break;
            case R.id.tv_audit:
                index=2;
                net=new PosRetailOrderListNet(mContext);
                net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59",indexPage,0,1);
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

    }

    @Override
    public void onLoadMoreRequested() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//日期筛选
            if(data!=null){
                isSelectDate=true;
                startDate=data.getStringExtra("StartDate");
                endDate=data.getStringExtra("EndDate");
                tv_start_sel_date.setText(AbDateUtil.getDateYMDs(startDate+" 00:00:00"));
                tv_end_sel_date.setText(AbDateUtil.getDateYMDs(endDate+" 23:59:59"));

                switch (index){
                    case 0:
                        net=new PosRetailOrderListNet(mContext);
                        net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59",indexPage);
                        break;
                    case 1:
                        net=new PosRetailOrderListNet(mContext);
                        net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59",indexPage,1,1);
                        break;
                    case 2:
                        net=new PosRetailOrderListNet(mContext);
                        net.setData(tv_start_sel_date.getText().toString()+" 00:00:00",tv_end_sel_date.getText().toString()+" 23:59:59",indexPage,0,1);
                        break;
                }

            }
        }
    }
}
