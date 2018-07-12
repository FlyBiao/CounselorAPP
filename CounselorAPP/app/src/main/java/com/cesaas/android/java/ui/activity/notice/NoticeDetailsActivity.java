package com.cesaas.android.java.ui.activity.notice;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.adapter.notice.NoticeDetailsAdapter;
import com.cesaas.android.java.bean.notice.NoticeGoodsDetailBean;
import com.cesaas.android.java.bean.notice.NoticeListListBean;
import com.cesaas.android.java.bean.notice.ResultNoticeDetailsBean;
import com.cesaas.android.java.bean.notice.ResultNoticeGoodsDetailBean;
import com.cesaas.android.java.net.notice.NoticeDetailNet;
import com.cesaas.android.java.net.notice.NoticeGoodsDetailNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.cesaas.android.java.utils.MoveDeliveryStatusUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 通知单详情
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class NoticeDetailsActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private TextView tvTitle;
    private TextView tv_shop_icon,tv_org_icon,tv_date_icon,tv_not_data,tv_num;
    private TextView tv_status_icon,tv_mover_status,tv_category;
    private TextView tv_no,tv_originOrganizationTitle,tv_originShopName,tv_receiveOrganizationTitle,tv_receiveShopName,tv_date,tv_remark;
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

    private NoticeListListBean noticeListListBean;
    private NoticeGoodsDetailNet noticeGoodsDetailNet;
    private NoticeDetailNet noticeDetailNet;
    private NoticeDetailsAdapter noticeDetailsAdapter;
    private List<NoticeGoodsDetailBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_details);
        Bundle bundle=getIntent().getExtras();
        noticeListListBean= (NoticeListListBean) bundle.getSerializable("NoticeListListBean");

        initView();
        initData();
    }

    /**
     * 接收调货条码列表
     * @param msg
     */
    public void onEventMainThread(ResultNoticeGoodsDetailBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    tv_not_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);
                    noticeDetailsAdapter = new NoticeDetailsAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(noticeDetailsAdapter);
                } else {
                    tv_not_data.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                MoveDeliveryStatusUtils.getDetailStatus(tv_status_icon,tv_mover_status,noticeListListBean.getStatus(),mContext);
            } else {
                tv_not_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收通知单详情
     * @param msg
     */
    public void onEventMainThread(ResultNoticeDetailsBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.model != null && !"".equals(msg.arguments.resp.model)) {
                    tv_no.setText(msg.arguments.resp.model.getNo());
                    tv_originOrganizationTitle.setText(msg.arguments.resp.model.getOriginOrganizationTitle());
                    tv_originShopName.setText(msg.arguments.resp.model.getOriginShopName());
                    tv_receiveOrganizationTitle.setText(msg.arguments.resp.model.getReceiveOrganizationTitle());
                    tv_receiveShopName.setText(msg.arguments.resp.model.getReceiveShopName());
                    tv_num.setText(String.valueOf(msg.arguments.resp.model.getNum()));

                    if (msg.arguments.resp.model.getRemark() != null && !"".equals(msg.arguments.resp.model.getRemark())) {
                        tv_remark.setText(msg.arguments.resp.model.getRemark());
                    }

                    if (msg.arguments.resp.model.getCreateTime() != null && !"".equals(msg.arguments.resp.model.getCreateTime())) {
                        tv_date.setText(AbDateUtil.getDateYMDs(msg.arguments.resp.model.getCreateTime()));
                    }
                }
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }



    public void initData() {
        noticeDetailNet=new NoticeDetailNet(mContext);
        noticeDetailNet.setData(noticeListListBean.getId());

        noticeGoodsDetailNet=new NoticeGoodsDetailNet(mContext);
        noticeGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPId(noticeListListBean.getId()));
    }

    private void initView() {
        tv_category= (TextView) findViewById(R.id.tv_category);
        tv_mover_status= (TextView) findViewById(R.id.tv_mover_status);
        tv_status_icon= (TextView) findViewById(R.id.tv_status_icon);
        tv_num= (TextView) findViewById(R.id.tv_num);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_remark= (TextView) findViewById(R.id.tv_remark);
        tv_date= (TextView) findViewById(R.id.tv_date);
        tv_receiveShopName= (TextView) findViewById(R.id.tv_receiveShopName);
        tv_receiveOrganizationTitle= (TextView) findViewById(R.id.tv_receiveOrganizationTitle);
        tv_originShopName= (TextView) findViewById(R.id.tv_originShopName);
        tv_originOrganizationTitle= (TextView) findViewById(R.id.tv_originOrganizationTitle);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_shop_icon= (TextView) findViewById(R.id.tv_shop_icon);
        tv_shop_icon.setText(R.string.business_school);
        tv_shop_icon.setTypeface(App.font);
        tv_org_icon= (TextView) findViewById(R.id.tv_org_icon);
        tv_org_icon.setText(R.string.fa_long_arrow_right);
        tv_org_icon.setTypeface(App.font);
        tv_date_icon= (TextView) findViewById(R.id.tv_date_icon);
        tv_date_icon.setText(R.string.fa_clock);
        tv_date_icon.setTypeface(App.font);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("通知单详情");

        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        noticeDetailsAdapter=new NoticeDetailsAdapter(mData,mActivity,mContext);
        noticeDetailsAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                noticeDetailsAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
