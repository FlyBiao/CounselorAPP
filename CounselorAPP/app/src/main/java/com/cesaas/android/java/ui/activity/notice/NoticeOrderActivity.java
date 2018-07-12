package com.cesaas.android.java.ui.activity.notice;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.ui.activity.member.DateScreenActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.adapter.notice.NoticeListAdapter;
import com.cesaas.android.java.bean.notice.NoticeListListBean;
import com.cesaas.android.java.bean.notice.ResultNoticeListListBean;
import com.cesaas.android.java.net.notice.NoticeListListNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 通知单
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class NoticeOrderActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_all,tv_bill,tv_audit;
    private TextView tv_start_sel_date,tv_end_sel_date,tv_notice_count,tv_order_count;
    private LinearLayout ll_search_vip;
    private LinearLayout ll_screening;
    private EditText et_search;
    private TextView tv_screening;
    private TextView tv_inentory_count,tv_diff_count,tv_inentory_total;

    private TextView tvTitle,tv_not_data;
    private ImageView ivScan;
    private LinearLayout llBack;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;
    private int index=-1;
    private int status=0;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private List<NoticeListListBean> mData=new ArrayList<>();
    private NoticeListAdapter noticeListAdapter;

    private NoticeListListNet noticeListListNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_order);

        initView();
        initData();
    }

    /**
     * 接收通知单
     * @param msg
     */
    public void onEventMainThread(ResultNoticeListListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_not_data.setVisibility(View.GONE);

                    tv_notice_count.setText(String.valueOf(msg.arguments.resp.numCount));
                    tv_order_count.setText(String.valueOf(msg.arguments.resp.totalCount));

                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);
                    noticeListAdapter = new NoticeListAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(noticeListAdapter);
                    noticeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            bundle.putSerializable("NoticeListListBean", mData.get(position));
                            Skip.mNextFroData(mActivity, NoticeDetailsActivity.class, bundle);
                        }
                    });
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tv_not_data.setVisibility(View.VISIBLE);
                }
            } else {
                recyclerView.setVisibility(View.GONE);
                tv_not_data.setVisibility(View.VISIBLE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    public void initData() {
        noticeListListNet=new NoticeListListNet(mContext);
        noticeListListNet.setData(pageIndex,FilterConditionDateUtils.getFilterCategory(status,tv_start_sel_date.getText().toString()+" 00:00:00"
                ,tv_end_sel_date.getText().toString()+" 23:59:59"));
    }

    private void initView() {
        tv_notice_count= (TextView) findViewById(R.id.tv_notice_count);
        tv_order_count= (TextView) findViewById(R.id.tv_order_count);
        tv_inentory_count= (TextView) findViewById(R.id.tv_inentory_count);
        tv_diff_count= (TextView) findViewById(R.id.tv_diff_count);
        tv_inentory_total= (TextView) findViewById(R.id.tv_inentory_total);
        tv_all= (TextView) findViewById(R.id.tv_all);
        tv_all.setOnClickListener(this);
        tv_bill= (TextView) findViewById(R.id.tv_bill);
        tv_bill.setOnClickListener(this);
        tv_audit= (TextView) findViewById(R.id.tv_audit);
        tv_audit.setOnClickListener(this);
        tvs.add(tv_all);
        tvs.add(tv_bill);
        tvs.add(tv_audit);
        tv_start_sel_date= (TextView) findViewById(R.id.tv_start_sel_date);
        tv_start_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00")));
        tv_end_sel_date= (TextView) findViewById(R.id.tv_end_sel_date);
        tv_end_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59")));
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("通知单");
        ivScan= (ImageView) findViewById(R.id.iv_add_module);
        ivScan.setVisibility(View.GONE);
        ivScan.setImageResource(R.mipmap.add_module);
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ll_screening= (LinearLayout) findViewById(R.id.ll_screening);
        ll_screening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tagIntent = new Intent(mContext, DateScreenActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
            }
        });
        tv_screening= (TextView) findViewById(R.id.tv_screening);
        tv_screening.setText(R.string.fa_glass);
        tv_screening.setTypeface(App.font);
        ll_search_vip= (LinearLayout) findViewById(R.id.ll_search_vip);
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    noticeListListNet=new NoticeListListNet(mContext);
                    noticeListListNet.setData(pageIndex, FilterConditionDateUtils.getFilterCategoryAndSel(status,et_search.getText().toString(),tv_start_sel_date.getText().toString()+" 00:00:00"
                            ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                }
                return false;
            }
        });
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_all:
                index=0;
                status=0;
                noticeListListNet=new NoticeListListNet(mContext);
                noticeListListNet.setData(pageIndex,FilterConditionDateUtils.getFilterCategory(0,tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                break;
            case R.id.tv_bill:
                index=1;
                status=2;
                noticeListListNet=new NoticeListListNet(mContext);
                noticeListListNet.setData(pageIndex,FilterConditionDateUtils.getFilterCategory(2,tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                break;
            case R.id.tv_audit:
                index=2;
                status=3;
                noticeListListNet=new NoticeListListNet(mContext);
                noticeListListNet.setData(pageIndex,FilterConditionDateUtils.getFilterCategory(3,tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
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

    private String startDate;
    private String  endDate;
    private boolean isSelectDate=false;

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

                noticeListListNet=new NoticeListListNet(mContext);
                noticeListListNet.setData(pageIndex,FilterConditionDateUtils.getFilterCategory(0,tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));

            }
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        noticeListAdapter=new NoticeListAdapter(mData,mActivity,mContext);
        noticeListAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                noticeListAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

}
