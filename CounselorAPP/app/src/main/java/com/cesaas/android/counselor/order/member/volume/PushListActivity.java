package com.cesaas.android.counselor.order.member.volume;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.fans.activity.GroupSendAactivity;
import com.cesaas.android.counselor.order.member.adapter.volume.PushListAdapter;
import com.cesaas.android.counselor.order.member.bean.service.volume.PushBean;
import com.cesaas.android.counselor.order.utils.Skip;

import java.util.ArrayList;
import java.util.List;


/**
 * 推送卷列表
 */
public class PushListActivity extends BasesActivity implements View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle;
    private TextView tv_not_data,tv_s_sum,tv_e_sum;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr;
    private boolean mLoadMoreEndGone = false;


    private List<PushBean> pushList=new ArrayList<>();
    private PushListAdapter adapter;

    private int sSum=0;
    private int eSum=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_list);
        initView();
        initData();
    }

    public void initView(){
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        tv_s_sum= (TextView) findViewById(R.id.tv_s_sum);
        tv_e_sum= (TextView) findViewById(R.id.tv_e_sum);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewTitle.setText("券发送状态");
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(){
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            pushList= (List<PushBean>) bundle.getSerializable("PushList");
        }

        for (int i=0;i<pushList.size();i++){
            if(pushList.get(i).getStatus()==0){
                eSum+=1;
            }else{
                sSum+=1;
            }
        }

        tv_s_sum.setText(String.valueOf(sSum));
        tv_e_sum.setText(String.valueOf(eSum));

        adapter=new PushListAdapter(pushList,mActivity,mContext);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

    @Override
    public void onRefresh() {
        adapter = new PushListAdapter(pushList,mActivity,mContext);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
