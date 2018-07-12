package com.cesaas.android.counselor.order.workbench.fragment;

import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.DepositActivity;
import com.cesaas.android.counselor.order.activity.user.NoticeListActivity;
import com.cesaas.android.counselor.order.activity.user.net.BindPublicNoCodeNet;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.bean.weather.net.ChinaWeatherNet;
import com.cesaas.android.counselor.order.earnings.activity.EarningsActivity;
import com.cesaas.android.counselor.order.fragment.HomeFragment;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shoptask.view.ShopTaskListActivity;
import com.cesaas.android.counselor.order.store.StoreDisplayActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.cesaas.android.counselor.order.workbench.activity.DisplayActivity;
import com.cesaas.android.counselor.order.workbench.activity.ReturnVisitActivity;
import com.cesaas.android.counselor.order.workbench.activity.ShopVisitActivity;
import com.cesaas.android.counselor.order.workbench.activity.WorkListActivity;
import com.cesaas.android.counselor.order.workbench.adapter.WorkbenchAdapter;
import com.cesaas.android.counselor.order.workbench.bean.WorkbenchBean;
import com.cesaas.android.counselor.order.workbench.net.ReturnVisitNet;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 工作台Fragment
 * Created 2017/4/6 18:30
 * Version 1.zero
 */
public class WorkbenchFragment extends BaseFragment implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private TextView tv_task_msg,tv_my_earnings,tv_notice_list;
    private LinearLayout ll_task_msg,ll_my_earnings,ll_notice_list;

    private WorkbenchAdapter mWorkbenchAdapter;
    private List<WorkbenchBean> mWorkbenchBeanList;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_workbench;
    }

    @Override
    public void initViews() {
        mSwipeRefreshLayout=findView(R.id.swipe_layout);
        mSwipeMenuRecyclerView=findView(R.id.recycler_workbench_view);
        ll_my_earnings=findView(R.id.ll_my_earnings);
        ll_notice_list=findView(R.id.ll_notice_list);
        tv_notice_list=findView(R.id.tv_notice_list);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        ll_task_msg=findView(R.id.ll_task_msg);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fontawesome-webfont.ttf");//记得加上这句
        tv_task_msg= (TextView) findView(R.id.tv_task_msg);
        tv_my_earnings=findView(R.id.tv_my_earnings);
        tv_my_earnings.setText(R.string.money);
        tv_notice_list.setText(R.string.system_msg);
        tv_notice_list.setTypeface(font);
        tv_task_msg.setTypeface(font);
        tv_my_earnings.setTypeface(font);
    }

    /**
     * 单例
     */
    public static WorkbenchFragment newInstance() {
        WorkbenchFragment fragmentCommon = new WorkbenchFragment();
        return fragmentCommon;
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


                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            switch (mWorkbenchBeanList.get(position).getStatus()){
                case 1://巡店
                    Skip.mNext(getActivity(),ShopVisitActivity.class);
                    break;
                case 2://回访
                    Skip.mNext(getActivity(),ReturnVisitActivity.class);
                    break;
                case 3://陈列
//                    Skip.mNext(getActivity(),DisplayActivity.class);
                    Skip.mNext(getActivity(), StoreDisplayActivity.class);
                    break;
                case 4://工作
                    Skip.mNext(getActivity(),WorkListActivity.class);
                    break;
            }
        }
    };

    @Override
    public void initListener() {
        ll_task_msg.setOnClickListener(this);
        ll_my_earnings.setOnClickListener(this);
        ll_notice_list.setOnClickListener(this);
    }

    @Override
    public void initData() {

        mWorkbenchBeanList=new ArrayList<>();
            WorkbenchBean shopBean=new WorkbenchBean();
            shopBean.setStatus(1);
            shopBean.setTitle("巡店任务");
            shopBean.setContent("督导对店铺进行例行检查");
            mWorkbenchBeanList.add(shopBean);

        WorkbenchBean returnVisitBean=new WorkbenchBean();
        returnVisitBean.setStatus(2);
        returnVisitBean.setTitle("客户回访");
        returnVisitBean.setContent("对归属会员进行购买后回访");
        mWorkbenchBeanList.add(returnVisitBean);

        WorkbenchBean displayBean=new WorkbenchBean();
        displayBean.setStatus(3);
        displayBean.setTitle("门店成列");
        displayBean.setContent("对店铺内的商品陈列改动");
        mWorkbenchBeanList.add(displayBean);

        WorkbenchBean workBean=new WorkbenchBean();
        workBean.setStatus(4);
        workBean.setTitle("工作列表");
        workBean.setContent("本日进行的任务");
        mWorkbenchBeanList.add(workBean);

        mWorkbenchAdapter=new WorkbenchAdapter(mWorkbenchBeanList);
        mWorkbenchAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mWorkbenchAdapter);
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_task_msg:
                Skip.mNext(getActivity(),ShopTaskListActivity.class);
                break;
            case R.id.ll_my_earnings:
//                Skip.mNext(getActivity(),DepositActivity.class);
                Skip.mNext(getActivity(),EarningsActivity.class);

                break;
            case R.id.ll_notice_list:
                Skip.mNext(getActivity(), NoticeListActivity.class);
                break;
        }
    }

    @Override
    public void fetchData() {

    }
}
