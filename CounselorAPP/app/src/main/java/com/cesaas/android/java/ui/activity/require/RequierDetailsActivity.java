package com.cesaas.android.java.ui.activity.require;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.scan.ZxingScanRequierActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.adapter.require.RequierListAdapter;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliverySubmitBean;
import com.cesaas.android.java.bean.require.RequireListBean;
import com.cesaas.android.java.bean.require.ResultRequireGoodsDetailBean;
import com.cesaas.android.java.net.require.RequireGoodsDetailNet;
import com.cesaas.android.java.net.require.RequireSubmitNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.cesaas.android.java.utils.MoveDeliveryStatusUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 补货详情详情
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class RequierDetailsActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tvTitle;
    private TextView tv_shop_icon,tv_user_icon;
    private TextView tv_status_icon,tv_mover_status,tv_category;
    private TextView tv_back,tv_submit,tv_no,tv_date,tv_shop,tv_user,tv_requier_num,tv_remark,tv_not_data;
    private LinearLayout llBack,ll_scan_add_box,ll_search,lll;
    private MClearEditText et_search_content;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private long id;
    private long boxId;
    private int boxNo;

    private RequierListAdapter adapter;
    private List<MoveDeliveryGoodsDetailBean> mData=new ArrayList<>();
    private RequireListBean requireListBean;
    private RequireGoodsDetailNet requireGoodsDetailNet;
    private RequireSubmitNet requireSubmitNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requier_details);
        Bundle bundle=getIntent().getExtras();
        requireListBean= (RequireListBean) bundle.getSerializable("RequireBean");

        initView();
        initData();

        if(requireListBean.getStatus()==30 || requireListBean.getStatus()==40){
            lll.setVisibility(View.GONE);
        }else{
            lll.setVisibility(View.VISIBLE);
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
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "提交成功");
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收调货条码列表
     * @param msg
     */
    public void onEventMainThread(ResultRequireGoodsDetailBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);

                    adapter = new RequierListAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(adapter);
                } else {
                    tv_not_data.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                MoveDeliveryStatusUtils.getDetailStatus(tv_status_icon,tv_mover_status,requireListBean.getStatus(),mContext);

            } else {
                tv_not_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    public void initData() {
        requireGoodsDetailNet=new RequireGoodsDetailNet(mContext);
        requireGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPId(requireListBean.getId()));

        tv_no.setText(requireListBean.getNo());
        tv_shop.setText(requireListBean.getOriginShopName());
        tv_user.setText(requireListBean.getCreateName());
        tv_date.setText(AbDateUtil.getDateYMDs(requireListBean.getCreateTime()));
        tv_requier_num.setText(String.valueOf(requireListBean.getNum()));
        tv_remark.setText(requireListBean.getRemark());
    }

    private void initView() {
        tv_category= (TextView) findViewById(R.id.tv_category);
        tv_mover_status= (TextView) findViewById(R.id.tv_mover_status);
        tv_status_icon= (TextView) findViewById(R.id.tv_status_icon);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_shop_icon= (TextView) findViewById(R.id.tv_shop_icon);
        tv_shop_icon.setText(R.string.business_school);
        tv_shop_icon.setTypeface(App.font);
        tv_user_icon= (TextView) findViewById(R.id.tv_user_icon);
        tv_user_icon.setText(R.string.user);
        tv_user_icon.setTypeface(App.font);

        lll= (LinearLayout) findViewById(R.id.lll);
        ll_scan_add_box= (LinearLayout) findViewById(R.id.ll_scan_add_box);
        ll_scan_add_box.setOnClickListener(this);

        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("补货详情");
        tv_back= (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(this);
        tv_submit= (TextView) findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_date= (TextView) findViewById(R.id.tv_date);
        tv_shop= (TextView) findViewById(R.id.tv_shop);
        tv_user= (TextView) findViewById(R.id.tv_user);
        tv_requier_num= (TextView) findViewById(R.id.tv_requier_num);
        tv_remark= (TextView) findViewById(R.id.tv_remark);

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ll_search= (LinearLayout) findViewById(R.id.ll_search);
        ll_search.setOnClickListener(this);
        et_search_content= (MClearEditText) findViewById(R.id.et_search_content);
        et_search_content.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    requireGoodsDetailNet=new RequireGoodsDetailNet(mContext);
                    requireGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndNo(requireListBean.getId(),et_search_content.getText().toString()));
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_scan_add_box:
                bundle.putSerializable("RequireListBean",requireListBean);
                Skip.mNextFroData(mActivity, ZxingScanRequierActivity.class,bundle);
                break;
            case R.id.tv_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_submit:
                requireSubmitNet=new RequireSubmitNet(mContext);
                requireSubmitNet.setData(requireListBean.getId());
                break;
            case R.id.ll_search:
                requireGoodsDetailNet=new RequireGoodsDetailNet(mContext);
                requireGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndNo(requireListBean.getId(),et_search_content.getText().toString()));
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        adapter=new RequierListAdapter(mData,mActivity,mContext);
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
