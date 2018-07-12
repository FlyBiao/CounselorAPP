package com.cesaas.android.java.ui.activity;

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
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.adapter.SendReceiveShopAdapter;
import com.cesaas.android.java.bean.ResultSendShopBean;
import com.cesaas.android.java.bean.ShopListBean;
import com.cesaas.android.java.bean.receive.ResultReceiveShopBean;
import com.cesaas.android.java.net.move.GetPowerShopListNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jauker.widget.BadgeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 选择店铺列表
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class SelectShopActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle,tv_not_data;
    private LinearLayout llBack;
    private LinearLayout ll_search_vip;
    private EditText et_search;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private int type,BillType;

    private GetPowerShopListNet getPowerShopListNet;
    private SendReceiveShopAdapter adapter;
    private List<ShopListBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Intent intent=getIntent();
        String strType = intent.getStringExtra("Type");
        type=Integer.valueOf(strType);
        String billType = intent.getStringExtra("BillType");
        BillType=Integer.valueOf(billType);

        initView();
        initData();
    }

    /**
     * 接收店铺列表
     * @param msg
     */
    public void onEventMainThread(ResultSendShopBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);
                    adapter = new SendReceiveShopAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            Intent mIntent = new Intent();
                            mIntent.putExtra("result", mData.get(position));
                            setResult(10, mIntent);// 设置结果，并进行传送
                            finish();
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

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("店铺列表");
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ll_search_vip= (LinearLayout) findViewById(R.id.ll_search_vip);
        ll_search_vip.setOnClickListener(this);
        et_search= (EditText) findViewById(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getPowerShopListNet=new GetPowerShopListNet(mContext);
                    getPowerShopListNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionSelectShopName(et_search.getText().toString()),BillType, type);
                }
                return false;
            }
        });
    }

    public void initData() {
        getPowerShopListNet=new GetPowerShopListNet(mContext);
        getPowerShopListNet.setData(pageIndex,BillType, type);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_search_vip:
                getPowerShopListNet=new GetPowerShopListNet(mContext);
                getPowerShopListNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionSelectShopName(et_search.getText().toString()),BillType, type);
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter=new SendReceiveShopAdapter(mData,mActivity,mContext);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

}
