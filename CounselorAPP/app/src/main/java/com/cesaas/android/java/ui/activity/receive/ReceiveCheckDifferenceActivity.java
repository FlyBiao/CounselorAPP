package com.cesaas.android.java.ui.activity.receive;

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
import com.cesaas.android.java.adapter.receive.ReceiveDifferenceListAdapter;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.receive.ReceiveGetDiffListBean;
import com.cesaas.android.java.bean.receive.ResultReceiveGetDiffListBean;
import com.cesaas.android.java.net.receive.ReceiveGetDiffListNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 查看收货差异
 * Created at 2018/6/4 11:57
 * Version 1.0
 */

public class ReceiveCheckDifferenceActivity extends BasesActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{

    private TextView tvTitle;
    private TextView tv_no,tv_originOrganizationTitle,tv_originShopName,tv_receiveOrganizationTitle,tv_receiveShopName,tv_createTime,tv_sureTime;
    private TextView tv_shipmentsNum,tv_num,tv_differenceNum,tv_shop_icon,tv_org_icon,tv_createTime_icon,tv_sureTime_icon,tv_not_data;
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

    private long id;
    private MoveDeliveryListBeanBean moveDeliveryListBeanBean=new MoveDeliveryListBeanBean();
    private List<ReceiveGetDiffListBean> mData=new ArrayList<>();
    private ReceiveDifferenceListAdapter receiveDifferenceListAdapter;

    private ReceiveGetDiffListNet receiveGetDiffListNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_check_difference);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getLong("id");
        moveDeliveryListBeanBean= (MoveDeliveryListBeanBean) bundle.getSerializable("moveDeliveryListBeanBean");

        initView();
        initData();
    }

    /**
     * 接收收货差异
     * @param msg
     */
    public void onEventMainThread(ResultReceiveGetDiffListBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tv_not_data.setVisibility(View.GONE);
                    tv_shipmentsNum.setText(String.valueOf(msg.arguments.resp.shipmentsNum));
                    tv_num.setText(String.valueOf(msg.arguments.resp.num));
                    tv_differenceNum.setText(String.valueOf(msg.arguments.resp.differenceNum));
                    mData = new ArrayList<>();
                    mData.addAll(msg.arguments.resp.records.value);

                    receiveDifferenceListAdapter = new ReceiveDifferenceListAdapter(mData, mActivity, mContext);
                    recyclerView.setAdapter(receiveDifferenceListAdapter);
                } else {
                    tv_not_data.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            } else {
                tv_not_data.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    private void initData() {
        tv_no.setText(moveDeliveryListBeanBean.getNo());
        tv_originOrganizationTitle.setText(moveDeliveryListBeanBean.getOriginOrganizationTitle());
        tv_originShopName.setText(moveDeliveryListBeanBean.getOriginShopName());
        tv_receiveOrganizationTitle.setText(moveDeliveryListBeanBean.getReceiveOrganizationTitle());
        tv_receiveShopName.setText(moveDeliveryListBeanBean.getReceiveShopName());
        if(moveDeliveryListBeanBean.getCreateTime()!=null && !"".equals(moveDeliveryListBeanBean.getCreateTime())){
            tv_createTime.setText(AbDateUtil.getDateYMDs(moveDeliveryListBeanBean.getCreateTime()));
        }
        if(moveDeliveryListBeanBean.getSureTime()!=null && !"".equals(moveDeliveryListBeanBean.getSureTime())){
            tv_sureTime.setText(AbDateUtil.getDateYMDs(moveDeliveryListBeanBean.getSureTime()));
        }

        receiveGetDiffListNet=new ReceiveGetDiffListNet(mContext);
        receiveGetDiffListNet.setData(pageIndex, FilterConditionDateUtils.getPId(id));
    }

    private void initView() {
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("收货差异");
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        swipeLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        swipeLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_originOrganizationTitle= (TextView) findViewById(R.id.tv_originOrganizationTitle);
        tv_originShopName= (TextView) findViewById(R.id.tv_originShopName);
        tv_receiveOrganizationTitle= (TextView) findViewById(R.id.tv_receiveOrganizationTitle);
        tv_receiveShopName= (TextView) findViewById(R.id.tv_receiveShopName);
        tv_createTime= (TextView) findViewById(R.id.tv_createTime);
        tv_sureTime= (TextView) findViewById(R.id.tv_sureTime);
        tv_shipmentsNum= (TextView) findViewById(R.id.tv_shipmentsNum);
        tv_num= (TextView) findViewById(R.id.tv_num);
        tv_differenceNum= (TextView) findViewById(R.id.tv_differenceNum);
        tv_shop_icon= (TextView) findViewById(R.id.tv_shop_icon);
        tv_shop_icon.setText(R.string.business_school);
        tv_shop_icon.setTypeface(App.font);
        tv_org_icon= (TextView) findViewById(R.id.tv_org_icon);
        tv_org_icon.setText(R.string.fa_long_arrow_right);
        tv_org_icon.setTypeface(App.font);
        tv_createTime_icon= (TextView) findViewById(R.id.tv_createTime_icon);
        tv_createTime_icon.setText(R.string.fa_clock);
        tv_createTime_icon.setTypeface(App.font);
        tv_sureTime_icon= (TextView) findViewById(R.id.tv_sureTime_icon);
        tv_sureTime_icon.setText(R.string.fa_clock);
        tv_sureTime_icon.setTypeface(App.font);
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
        receiveDifferenceListAdapter=new ReceiveDifferenceListAdapter(mData,mActivity,mContext);
        receiveDifferenceListAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                swipeLayout.setRefreshing(false);
                receiveDifferenceListAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
