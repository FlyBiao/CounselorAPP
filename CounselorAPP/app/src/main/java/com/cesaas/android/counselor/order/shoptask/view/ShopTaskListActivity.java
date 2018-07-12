package com.cesaas.android.counselor.order.shoptask.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.TabLayoutViewPagerAdapter;
import com.cesaas.android.counselor.order.shoptask.adapter.ShopTaskListAdapter;
import com.cesaas.android.counselor.order.shoptask.adapter.TablayoutViewPagerTaskAdapter;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.net.TaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShopTaskListActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_task_list);

        initView();
        initData();

    }

    private void initData() {
        PagerAdapter adapter= new TablayoutViewPagerTaskAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initView() {
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("我的任务");

        llBaseBack.setOnClickListener(this);
        tvBaseTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
        }
    }
}
