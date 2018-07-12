package com.cesaas.android.java.ui.activity.move;

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
import com.cesaas.android.java.adapter.move.MoveDeliveryAdapter;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryConfirmBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryDeleteBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryRejectBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliverySubmitBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliverySumBean;
import com.cesaas.android.java.net.move.MoveDeliveryListNet;
import com.cesaas.android.java.net.move.MoveDeliverySumNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 调拨单列表
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class MoveDeliveryListActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tv_all,tv_audit,tv_audit1;
    private TextView tv_start_sel_date,tv_end_sel_date;
    private LinearLayout ll_search_vip,tv_ll_return,ll_move;
    private LinearLayout ll_screening;
    private EditText et_search;
    private TextView tv_screening;
    private TextView tv_originNum,tv_receiveNum,tv_transitNum,tv_count,tv_return_count,tv_return_order_sum;

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
    private int requestCode=10;
    private int index=-1;
    private int type=0;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();

    private MoveDeliveryListNet moveDeliveryListNet;
    private MoveDeliverySumNet moveDeliverySumNet;
    private MoveDeliveryAdapter moveDeliveryAdapter;
    private List<MoveDeliveryListBeanBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_deliver);
        Bundle bundle=getIntent().getExtras();
        type=bundle.getInt("netType");

        initView();
        initData();
    }

    /**
     * 接收调拨驳回
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryRejectBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode != 0) {
                if (type == 1) {
                    ToastFactory.getLongToast(mContext, "已驳回退货");
                } else {
                    ToastFactory.getLongToast(mContext, "已驳回调拨");
                }
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收确认发货
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryConfirmBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode != 0) {
                if (type == 1) {
                    ToastFactory.getLongToast(mContext, "已确认退货");
                } else {
                    ToastFactory.getLongToast(mContext, "已确认发货");
                }
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收提交
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliverySubmitBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode != 0) {
                ToastFactory.getLongToast(mContext, "提交成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }


    /**
     * 接收删除
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryDeleteBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode != 0) {
                ToastFactory.getLongToast(mContext, "删除成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收确认差异盘点单
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliverySumBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode != 0) {
                if (msg.arguments.resp.model != null && !"".equals(msg.arguments.resp.model)) {
                    tv_originNum.setText(String.valueOf(msg.arguments.resp.model.getOriginNum()));
                    tv_receiveNum.setText(String.valueOf(msg.arguments.resp.model.getReceiveNum()));
                    tv_transitNum.setText(String.valueOf(msg.arguments.resp.model.getTransitNum()));
                    tv_count.setText(String.valueOf(msg.arguments.resp.model.getCount()));

                    tv_return_count.setText(String.valueOf(msg.arguments.resp.model.getOriginNum()));
                    tv_return_order_sum.setText(String.valueOf(msg.arguments.resp.model.getCount()));
                }
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收
     * @param msg
     */
    public void onEventMainThread(ResultMoveDeliveryListBeanBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode != 0) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_not_data.setVisibility(View.GONE);
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);
                    moveDeliveryAdapter = new MoveDeliveryAdapter(mData, mActivity, mContext, type);
                    recyclerView.setAdapter(moveDeliveryAdapter);
                    moveDeliveryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            bundle.putLong("id", mData.get(position).getId());
                            bundle.putInt("netType", type);
                            bundle.putSerializable("MoveDeliveryListBeanBean",mData.get(position));
                            Skip.mNextFroData(mActivity, MoveDeliveryDetailsActivity.class, bundle);
                        }
                    });
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tv_not_data.setVisibility(View.VISIBLE);
                }
            } else {
                recyclerView.setVisibility(View.GONE);
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView() {
        tv_return_order_sum= (TextView) findViewById(R.id.tv_return_order_sum);
        tv_return_count= (TextView) findViewById(R.id.tv_return_count);
        tv_ll_return= (LinearLayout) findViewById(R.id.tv_ll_return);
        ll_move= (LinearLayout) findViewById(R.id.ll_move);
        tv_originNum= (TextView) findViewById(R.id.tv_originNum);
        tv_receiveNum= (TextView) findViewById(R.id.tv_receiveNum);
        tv_transitNum= (TextView) findViewById(R.id.tv_transitNum);
        tv_count= (TextView) findViewById(R.id.tv_count);
        tv_all= (TextView) findViewById(R.id.tv_all);
        tv_all.setOnClickListener(this);
        tv_audit= (TextView) findViewById(R.id.tv_audit);
        tv_audit.setOnClickListener(this);
        tv_audit1= (TextView) findViewById(R.id.tv_audit1);
        tv_audit1.setOnClickListener(this);
        tvs.add(tv_all);
        tvs.add(tv_audit);
        tvs.add(tv_audit1);
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
        if(type==1){
            tvTitle.setText("退货");
        }else{
            tvTitle.setText("调拨");
        }

        ivScan= (ImageView) findViewById(R.id.iv_add_module);
        ivScan.setVisibility(View.VISIBLE);
        ivScan.setImageResource(R.mipmap.add_module);
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddTransActivity.class);
                intent.putExtra("netType",String.valueOf(type));
                startActivityForResult(intent, requestCode);
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
        ll_search_vip.setOnClickListener(this);
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    moveDeliveryListNet=new MoveDeliveryListNet(mContext,type);
                    moveDeliveryListNet.setData(pageIndex,FilterConditionDateUtils.getFilterConditionSelectAndDate(et_search.getText().toString(),tv_start_sel_date.getText().toString()+" 00:00:00"
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

    public void initData() {
        if(type==1){
            tv_ll_return.setVisibility(View.VISIBLE);
            ll_move.setVisibility(View.GONE);
        }else{
            tv_ll_return.setVisibility(View.GONE);
            ll_move.setVisibility(View.VISIBLE);
        }
        moveDeliveryListNet=new MoveDeliveryListNet(mContext,type);
        moveDeliveryListNet.setData(pageIndex,FilterConditionDateUtils.getFilterConditionDate(
                tv_start_sel_date.getText().toString()+" 00:00:00"
                ,tv_end_sel_date.getText().toString()+" 23:59:59"));

        moveDeliverySumNet=new MoveDeliverySumNet(mContext,type);
        moveDeliverySumNet.setData(FilterConditionDateUtils.getFilterConditionDate(
                tv_start_sel_date.getText().toString()+" 00:00:00"
                ,tv_end_sel_date.getText().toString()+" 23:59:59"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_all:
                index=0;
                moveDeliveryListNet=new MoveDeliveryListNet(mContext,type);
                moveDeliveryListNet.setData(pageIndex,FilterConditionDateUtils.getFilterConditionDate(
                        tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                break;
            case R.id.tv_audit:
                index=1;
                moveDeliveryListNet=new MoveDeliveryListNet(mContext,type);
                moveDeliveryListNet.setData(pageIndex,FilterConditionDateUtils.getFilterStatusAndDate(30,tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                break;
            case R.id.tv_audit1:
                index=2;
                moveDeliveryListNet=new MoveDeliveryListNet(mContext,type);
                moveDeliveryListNet.setData(pageIndex,FilterConditionDateUtils.getFilterStatusAndDate(40,tv_start_sel_date.getText().toString()+" 00:00:00"
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

                moveDeliveryListNet=new MoveDeliveryListNet(mContext,type);
                moveDeliveryListNet.setData(pageIndex,
                        FilterConditionDateUtils.getFilterConditionDate(
                                tv_start_sel_date.getText().toString()+" 00:00:00"
                                ,tv_end_sel_date.getText().toString()+" 23:59:59"));

                moveDeliverySumNet=new MoveDeliverySumNet(mContext,type);
                moveDeliverySumNet.setData(FilterConditionDateUtils.getFilterConditionDate(
                        tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
            }
        }
        if(requestCode==10){
            if(data!=null){
                initData();
            }
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        moveDeliveryAdapter=new MoveDeliveryAdapter(mData,mActivity,mContext,type);
        moveDeliveryAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                moveDeliveryAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

}
