package com.cesaas.android.counselor.order.workbench.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseActivity;

import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.workbench.adapter.ReturnVisitAdapter;
import com.cesaas.android.counselor.order.workbench.bean.ResultReturnVisitBean;
import com.cesaas.android.counselor.order.workbench.bean.ReturnVisitBean;
import com.cesaas.android.counselor.order.workbench.net.ReturnVisitNet;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户回访
 */
public class ReturnVisitActivity extends BasesActivity {

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private ReturnVisitAdapter mReturnVisitAdapter;
    private List<ReturnVisitBean> mReturnVisitBeanList;

    private ReturnVisitNet mReturnVisitNet;

    private int pageIndex=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_visit);

        initViews();
        mReturnVisitNet=new ReturnVisitNet(mContext);
        mReturnVisitNet.setData(pageIndex);
    }

    public void initViews() {

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("客户回访");

        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_return_visit_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(){
        mReturnVisitAdapter=new ReturnVisitAdapter(mReturnVisitBeanList,mContext,mActivity);
        mSwipeMenuRecyclerView.setAdapter(mReturnVisitAdapter);
        mReturnVisitAdapter.setOnItemClickListener(onItemClickListener);
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //重新加载数据
                    mReturnVisitNet.setData(pageIndex);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            bundle.putInt("RuleId",mReturnVisitBeanList.get(position).getRuleId());
            bundle.putInt("VipId",mReturnVisitBeanList.get(position).getVipId());
            Skip.mNextFroData(mActivity,ReturnVisitDetailActivity.class,bundle);
        }
    };

    /**
     *
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultReturnVisitBean msg) {
        mReturnVisitBeanList=new ArrayList<>();
        mReturnVisitBeanList.addAll(msg.TModel);
        initData();
    }

}
