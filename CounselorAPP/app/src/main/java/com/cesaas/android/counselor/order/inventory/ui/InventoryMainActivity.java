package com.cesaas.android.counselor.order.inventory.ui;

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
import com.cesaas.android.counselor.order.inventory.adapter.InventoryListAdapter;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.inventory.InventoryListBeanBean;
import com.cesaas.android.java.bean.inventory.ResultDelInventoryBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryCheckBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryConfirmDifferenceBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryCreateDifferenceReqBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetListBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryRejectBean;
import com.cesaas.android.java.bean.inventory.ResultInventorySaveBean;
import com.cesaas.android.java.bean.inventory.ResultInventorySubmitBean;
import com.cesaas.android.java.net.inventory.InventoryGetListNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 盘点主页面
 */
public class InventoryMainActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tv_all,tv_bill,tv_audit,tv_audit1;
    private TextView tv_start_sel_date,tv_end_sel_date;
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
    private int requestCode=10;

    private List<InventoryListBeanBean> inventoryListBeen;
    private InventoryListAdapter inventoryListAdapter;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();

    private InventoryGetListNet inventoryGetListNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_main);

        initView();
        initData();

    }

    private void initData(){
        inventoryGetListNet=new InventoryGetListNet(mContext);
        inventoryGetListNet.setData(pageIndex,FilterConditionDateUtils.getFilterConditionDate(
                tv_start_sel_date.getText().toString()+" 00:00:00"
                ,tv_end_sel_date.getText().toString()+" 23:59:59"));
    }



    /**
     * 接收驳回
     * @param msg
     */
    public void onEventMainThread(ResultInventoryRejectBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "已驳回");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收确认
     * @param msg
     */
    public void onEventMainThread(ResultInventoryCheckBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "确认成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收备注盘点单
     * @param msg
     */
    public void onEventMainThread(ResultInventorySaveBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "备注盘点单成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收删除盘点单
     * @param msg
     */
    public void onEventMainThread(ResultDelInventoryBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "删除盘点单成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收提交盘点单
     * @param msg
     */
    public void onEventMainThread(ResultInventorySubmitBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "提交盘点单成功");
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
    public void onEventMainThread(ResultInventoryConfirmDifferenceBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "确认盘点单差异成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收重新生成盘点单差异
     * @param msg
     */
    public void onEventMainThread(ResultInventoryCreateDifferenceReqBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "生成盘点单差异成功");
                initData();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收盘点单列表数据
     * @param msg
     */
    public void onEventMainThread(ResultInventoryGetListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_not_data.setVisibility(View.GONE);

                    tv_inentory_count.setText(String.valueOf(msg.arguments.resp.summary.getInentoryCount()));
                    tv_diff_count.setText(String.valueOf(msg.arguments.resp.summary.getDiffCount()));
                    tv_inentory_total.setText(String.valueOf(msg.arguments.resp.summary.getInentoryTotal()));

                    inventoryListBeen = new ArrayList<>();
                    inventoryListBeen.addAll(msg.arguments.resp.records.value);

                    inventoryListAdapter = new InventoryListAdapter(inventoryListBeen, mActivity, mContext);
                    recyclerView.setAdapter(inventoryListAdapter);
                    inventoryListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Bundle bundle=new Bundle();
                            bundle.putString("id",String.valueOf(inventoryListBeen.get(position).getId()));
                            bundle.putInt("type",1);
                            bundle.putSerializable("InventoryListBeanBean",inventoryListBeen.get(position));
                            Skip.mNextFroData(mActivity, InventoryDetailsActivity.class,bundle);
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

    private void initView(){
        tv_inentory_count= (TextView) findViewById(R.id.tv_inentory_count);
        tv_diff_count= (TextView) findViewById(R.id.tv_diff_count);
        tv_inentory_total= (TextView) findViewById(R.id.tv_inentory_total);
        tv_all= (TextView) findViewById(R.id.tv_all);
        tv_all.setOnClickListener(this);
        tv_bill= (TextView) findViewById(R.id.tv_bill);
        tv_bill.setOnClickListener(this);
        tv_audit= (TextView) findViewById(R.id.tv_audit);
        tv_audit.setOnClickListener(this);
        tv_audit1= (TextView) findViewById(R.id.tv_audit1);
        tv_audit1.setOnClickListener(this);
        tvs.add(tv_all);
        tvs.add(tv_bill);
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
        tvTitle.setText("盘点");
        ivScan= (ImageView) findViewById(R.id.iv_add_module);
        ivScan.setVisibility(View.VISIBLE);
        ivScan.setImageResource(R.mipmap.add_module);
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bundle.putString("leftTitle",tvTitle.getText().toString());
//                Skip.mNextFroData(mActivity,CreateInventoryActivity.class,bundle);
                Intent intent = new Intent(mContext, CreateInventoryActivity.class);
                intent.putExtra("leftTitle",tvTitle.getText().toString());
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
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    inventoryGetListNet=new InventoryGetListNet(mContext);
                    inventoryGetListNet.setData(pageIndex,FilterConditionDateUtils.getFilterConditionSelectAndDate(et_search.getText().toString(),tv_start_sel_date.getText().toString()+" 00:00:00"
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
                inventoryGetListNet=new InventoryGetListNet(mContext);
                inventoryGetListNet.setData(pageIndex,FilterConditionDateUtils.getFilterConditionDate(
                        tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                break;
            case R.id.tv_bill:
                index=1;
                inventoryGetListNet=new InventoryGetListNet(mContext);
                inventoryGetListNet.setData(pageIndex,FilterConditionDateUtils.getFilterStatusAndDate(0,tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                break;
            case R.id.tv_audit:
                index=2;
                inventoryGetListNet=new InventoryGetListNet(mContext);
                inventoryGetListNet.setData(pageIndex,FilterConditionDateUtils.getFilterStatusAndDate(30,tv_start_sel_date.getText().toString()+" 00:00:00"
                        ,tv_end_sel_date.getText().toString()+" 23:59:59"));
                break;
            case R.id.tv_audit1:
                index=3;
                inventoryGetListNet=new InventoryGetListNet(mContext);
                inventoryGetListNet.setData(pageIndex,FilterConditionDateUtils.getFilterStatusAndDate(40,tv_start_sel_date.getText().toString()+" 00:00:00"
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

                inventoryGetListNet=new InventoryGetListNet(mContext);
                inventoryGetListNet.setData(pageIndex,
                        FilterConditionDateUtils.getFilterConditionDate(
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
        inventoryListBeen=new ArrayList<>();
        inventoryListAdapter=new InventoryListAdapter(inventoryListBeen,mActivity,mContext);
        inventoryListAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                inventoryListAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
