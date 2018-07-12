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
import com.cesaas.android.counselor.order.task.adapter.MyTaskAdapter;
import com.cesaas.android.counselor.order.task.bean.MyTaskBean;
import com.cesaas.android.counselor.order.task.bean.TaskCenterBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyTaskActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBack;
    private TextView tvBaseTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private List<MyTaskBean> myTaskBeanList;
    private MyTaskAdapter adapter;
    private int size=20;

    private int formId;//表单Id
    private int flowId;//流程编号
    private int taskId;//任务编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        initView();
        initData();
    }

    private void initData() {
        myTaskBeanList=new ArrayList<>();
        for (int i=0;i<size;i++){
            MyTaskBean bean=new MyTaskBean();
            bean.setTitle("我的任务"+i);
            bean.setDate(""+i);
            myTaskBeanList.add(bean);
        }
        adapter=new MyTaskAdapter(myTaskBeanList);
        adapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_mytask_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);


        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);

        tvBaseTitle.setText("任务中心");
        llBack.setOnClickListener(this);
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Skip.mNext(mActivity,MyTaskDetailActivity.class);
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

                Toast.makeText(MyTaskActivity.this, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
                size += 20;
                for (int i = size - 20; i < size; i++) {
                    MyTaskBean bean=new MyTaskBean();
                    bean.setTitle("我的任务"+i);
                    bean.setDate(""+i);
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

        }
    }
}
