package com.cesaas.android.counselor.order.shoptask.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shoptask.adapter.ShopTaskListAdapter;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.net.TaskListNet;
import com.cesaas.android.counselor.order.shoptask.view.HandleTaskActivity;
import com.cesaas.android.counselor.order.shoptask.view.ShopTaskListActivity;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.lidroid.xutils.exception.HttpException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 未处理店务任务列表Fragment
 * Created at 2017/5/11 10:08
 * Version 1.0
 */

public class NotHandleShopTaskFragment extends Fragment{

    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private TextView tv_not_data;

    private int pageIndex=1;
    private int pageSize=30;

    private TaskListNet mTaskListNet;
    private ShopTaskListAdapter mShopTaskListAdapter;
    private List<ShopTaskListBean> mShopTaskListBeanList;

    /**
     * 单例
     */
    public static NotHandleShopTaskFragment newInstance() {
        NotHandleShopTaskFragment fragmentCommon = new NotHandleShopTaskFragment();
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_shop_task_list, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData(){
        mTaskListNet=new TaskListNet(getContext());
        mTaskListNet.setData(pageIndex,pageSize,0);
    }

    private void initView(){
        mSwipeRefreshLayout = (SwipeRefreshLayout)view. findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_shop_task_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        tv_not_data= (TextView) view.findViewById(R.id.tv_not_data);
    }


    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Bundle bundle=new Bundle();
            bundle.putInt("workid",mShopTaskListBeanList.get(position).getWorkId());
            bundle.putInt("flowid",mShopTaskListBeanList.get(position).getFlowId());
            bundle.putInt("formid",mShopTaskListBeanList.get(position).getFormId());
            Skip.mNextFroData(getActivity(),HandleTaskActivity.class,bundle);
        }
    };

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
                    mTaskListNet=new TaskListNet(getContext());
                    mTaskListNet.setData(pageIndex,pageSize,0);

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    /**
     * 加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!recyclerView.canScrollVertically(1)) {// 手指不能向上滑动了
                // TODO 这里有个注意的地方，如果你刚进来时没有数据，但是设置了适配器，这个时候就会触发加载更多，需要开发者判断下是否有数据，如果有数据才去加载更多。

                Toast.makeText(getContext(), "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
//                size += 20;
//                for (int i = size - 20; i < size; i++) {
//                    ReportCenterBean bean=new ReportCenterBean();
//                    bean.setTitle("测试数据"+i);
//                    bean.setId(i);
//                    reportCenterBeanList.add(bean);
//                }
//                mMenuAdapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * Author FGB
     * Description
     * Created 2017/4/20 17:53
     * Version 1.zero
     */
    public class TaskListNet extends BaseNet {
        public TaskListNet(Context context) {
            super(context, true);
            this.uri="ShopBusiness/SW/ShopTask/TaskList";
        }

        public void setData(int PageIndex,int PageSize){
            try{
                data.put("PageIndex",PageIndex);
                data.put("PageSize",PageSize);
                data.put("UserTicket",
                        AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        public void setData(int PageIndex,int PageSize,int Status){
            try{
                data.put("PageIndex",PageIndex);
                data.put("PageSize",PageSize);
                data.put("Status",Status);
                data.put("UserTicket",
                        AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultShopTaskListBean bean= JsonUtils.fromJson(rJson,ResultShopTaskListBean.class);
            if(bean.IsSuccess==true){
                mShopTaskListBeanList=new ArrayList<>();
                mShopTaskListBeanList.addAll(bean.TModel);

                if(mShopTaskListBeanList.size()!=0){
                    tv_not_data.setVisibility(View.GONE);
                    mShopTaskListAdapter=new ShopTaskListAdapter(mShopTaskListBeanList,getContext());
                    mShopTaskListAdapter.setOnItemClickListener(onItemClickListener);
                    mSwipeMenuRecyclerView.setAdapter(mShopTaskListAdapter);
                }
                else{
                    tv_not_data.setVisibility(View.VISIBLE);
                }

            }else{
                ToastFactory.getLongToast(getContext(),"获取数据失败！"+bean.Message);
            }
            EventBus.getDefault().post(bean);
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }
}
