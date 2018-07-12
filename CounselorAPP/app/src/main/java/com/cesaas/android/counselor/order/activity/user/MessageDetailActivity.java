package com.cesaas.android.counselor.order.activity.user;

import android.os.Bundle;
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
import com.cesaas.android.counselor.order.activity.adapter.MessageDetailAdapter;
import com.cesaas.android.counselor.order.activity.user.bean.MessageBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultMessageBean;
import com.cesaas.android.counselor.order.activity.user.net.MessageNet;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessageDetailActivity extends BasesActivity implements View.OnClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private LinearLayout llBack;
    private TextView mTvTitle;

    private int notifyType;
    private MessageNet messageNet;
    private MessageDetailAdapter adapter;
    private List<MessageBean> messageBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            notifyType=bundle.getInt("NotifyType");
            messageNet=new MessageNet(mContext);
            switch (notifyType){
                case 1:
                    messageNet.setData(1);
                    break;
                case 2:
                    messageNet.setData(2);
                    break;
                case 3:
                    messageNet.setData(3);
                    break;
            }
        }

        initView();
    }

    public void onEventMainThread(ResultMessageBean msg) {
        if(msg.IsSuccess==true){
            messageBeanList=new ArrayList<>();
            messageBeanList.addAll(msg.TModel);

            adapter=new MessageDetailAdapter(messageBeanList,mContext);
            adapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(adapter);
        }else{
            ToastFactory.getLongToast(mContext,"null:"+msg.Message);
        }
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    private void initView() {
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvTitle.setText("消息详情");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);

        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_message_view);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
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
                    messageNet=new MessageNet(mContext);
                    messageNet.setData(notifyType);
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
                Toast.makeText(mContext, "滑到最底部了，加载更多数据！", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }
}
