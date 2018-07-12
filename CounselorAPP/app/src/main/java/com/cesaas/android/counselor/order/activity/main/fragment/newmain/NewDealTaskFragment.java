package com.cesaas.android.counselor.order.activity.main.fragment.newmain;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.adapter.TaskListAdapter;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.label.net.SetTagNet;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceListBean;
import com.cesaas.android.counselor.order.member.net.service.FaceBindNet;
import com.cesaas.android.counselor.order.report.bean.ResultDayTotalBean;
import com.cesaas.android.counselor.order.shopmange.ClerkManageActivity;
import com.cesaas.android.counselor.order.shopmange.SelectClerkManageActivity;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ResultTransferBean;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.TaskTransferEventBean;
import com.cesaas.android.counselor.order.shoptask.net.TaskListNet;
import com.cesaas.android.counselor.order.shoptask.net.TaskTransferNet;
import com.cesaas.android.counselor.order.shoptask.view.ShopTaskListActivity;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/24 17:32
 * Version 1.0
 */

public class NewDealTaskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data;

    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    private TaskTransferNet transferNet;
    private TaskListNet taskListNet;
    private TaskListAdapter adapter;
    private List<ShopTaskListBean> taskList=new ArrayList<>();

    private int FlowId;

    /**
     * 单例
     */
    public static NewDealTaskFragment newInstance() {
        NewDealTaskFragment fragmentCommon = new NewDealTaskFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_deal_task;
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
        taskListNet =new TaskListNet(getContext());
        taskListNet.setData(1,50,0);
    }

    public void onEventMainThread(TaskTransferEventBean msg) {
        if(msg.getType()==1){
            FlowId=msg.getFlowId();
            Intent tagIntent = new Intent(getContext(), SelectClerkManageActivity.class);
            startActivityForResult(tagIntent, Constant.H5_MEMBER_SELECT);
        }
    }

    public void onEventMainThread(ResultTransferBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(getContext(),"转交任务成功");
            taskListNet =new TaskListNet(getContext());
            taskListNet.setData(1,30,0);
        }else{
            ToastFactory.getLongToast(getContext(),"转交任务失败"+msg.Message);
        }
    }

    public void onEventMainThread(ResultShopTaskListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                taskList=new ArrayList<>();
                taskList.addAll(msg.TModel);
                adapter=new TaskListAdapter(taskList,getActivity());
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
        adapter = new TaskListAdapter(taskList,getActivity());
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                taskListNet =new TaskListNet(getContext());
                taskListNet.setData(1,30,0);
                isErr = false;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==401){//选择店员
            if(data!=null){
                TaskTransferEventBean selectList=(TaskTransferEventBean)data.getSerializableExtra("selectList");
                transferNet=new TaskTransferNet(getContext());
                transferNet.setData(FlowId,selectList.getSalesId(),selectList.getSalesName());
            }
        }
    }
}
