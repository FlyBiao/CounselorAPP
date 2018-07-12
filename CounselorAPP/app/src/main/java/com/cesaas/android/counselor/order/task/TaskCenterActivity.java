package com.cesaas.android.counselor.order.task;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.report.ReportCenterActivity;
import com.cesaas.android.counselor.order.report.ReportDetailActivity;
import com.cesaas.android.counselor.order.report.bean.ReportCenterBean;
import com.cesaas.android.counselor.order.report.net.GetReportListNet;
import com.cesaas.android.counselor.order.store.bean.ResultStoreDisplayDetailBean;
import com.cesaas.android.counselor.order.store.bean.ResultTaskListBean;
import com.cesaas.android.counselor.order.task.adapter.TaskCenterAdapter;
import com.cesaas.android.counselor.order.task.bean.TaskCenterBean;
import com.cesaas.android.counselor.order.task.net.GetTaskList;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务中心
 */
public class TaskCenterActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBack;
    private TextView tvBaseTitle,tvBaseRigthTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private List<TaskCenterBean> taskCenterBeanList;
    private TaskCenterAdapter adapter;
    private int size=20;
    private int pageIndex=1;

    private GetTaskList getTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_center);

        getTaskList=new GetTaskList(mContext);
        getTaskList.setData(pageIndex);

        initView();
    }

    /**
     * 接受EventBus发送消息
     * @param msg 陈列详情消息实体类
     */
    public void onEventMainThread(ResultTaskListBean msg) {
        if(msg.IsSuccess==true){
            taskCenterBeanList=new ArrayList<>();
            taskCenterBeanList.addAll(msg.TModel);
            initData(taskCenterBeanList);
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    private void initData(List<TaskCenterBean> taskCenterBeanList) {

        adapter=new TaskCenterAdapter(taskCenterBeanList);
        adapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_task_center_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvBaseRigthTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRigthTitle.setVisibility(View.VISIBLE);

        tvBaseTitle.setText("任务中心");
        tvBaseRigthTitle.setText("任务记录");
        llBack.setOnClickListener(this);
        tvBaseRigthTitle.setOnClickListener(this);
        llBack.setOnClickListener(this);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            bundle.putString("formId",taskCenterBeanList.get(position).getFormId());
            bundle.putString("flowId",taskCenterBeanList.get(position).getFlowId());
            bundle.putString("taskId",taskCenterBeanList.get(position).getTaskId());
            Skip.mNextFroData(mActivity,TaskCenterDetailActivity.class,bundle);
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
                    getTaskList=new GetTaskList(mContext);
                    getTaskList.setData(pageIndex);

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

                Toast.makeText(TaskCenterActivity.this, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
                size += 20;
                for (int i = size - 20; i < size; i++) {
                    TaskCenterBean bean=new TaskCenterBean();
                    bean.setTitle("任务"+i);
                    bean.setCreteTime(""+i);
                    taskCenterBeanList.add(bean);
                }
                adapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right://我的任务记录
                Skip.mNext(mActivity,MyTaskActivity.class);
                break;
        }
    }
}
