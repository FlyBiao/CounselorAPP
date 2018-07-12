package com.cesaas.android.counselor.order.activity.main.fragment.newmain;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.adapter.CompleteTaskListAdapter;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskCompleteListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.net.TaskCompleteListNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/24 17:32
 * Version 1.0
 */

public class NewCompleteTaskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private TaskCompleteListNet taskListNet;
    private CompleteTaskListAdapter adapter;
    private List<ShopTaskListBean> taskList=new ArrayList<>();

    /**
     * 单例
     */
    public static NewCompleteTaskFragment newInstance() {
        NewCompleteTaskFragment fragmentCommon = new NewCompleteTaskFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_complete_task;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mRecyclerView=findView(R.id.rv_list);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        tv_not_data=findView(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        taskListNet =new TaskCompleteListNet(getContext());
        taskListNet.setData(1,30,1);
    }

    public void onEventMainThread(ResultShopTaskCompleteListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                taskList=new ArrayList<>();
                taskList.addAll(msg.TModel);
                adapter=new CompleteTaskListAdapter(taskList,getActivity());
                mRecyclerView.setAdapter(adapter);
            }else{
                tv_not_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onRefresh() {
        adapter = new CompleteTaskListAdapter(taskList,getActivity());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                taskListNet =new TaskCompleteListNet(getContext());
                taskListNet.setData(1,30,1);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }
}
