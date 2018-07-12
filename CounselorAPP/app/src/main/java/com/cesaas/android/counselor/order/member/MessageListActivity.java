package com.cesaas.android.counselor.order.member;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.MessageGroupListAdapter;
import com.cesaas.android.counselor.order.member.bean.MessageListBean;
import com.cesaas.android.counselor.order.member.bean.ResultGetMsgPageListBean;
import com.cesaas.android.counselor.order.member.net.GetMsgPageByVipIdNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.StringSplitUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信列表
 */
public class MessageListActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack,ll_create_msg;
    private TextView tvBaseTitle;

    private int pageIndex=1;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private MessageGroupListAdapter mMessageGroupListAdapter;
    private List<MessageListBean> mMessageListBeen;

    private GetMsgPageByVipIdNet mGetMsgPageByVipIdNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group);

        mGetMsgPageByVipIdNet=new GetMsgPageByVipIdNet(mContext);
        mGetMsgPageByVipIdNet.setData(pageIndex);

        initView();
    }

    private void initView() {
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_msg_group_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        ll_create_msg= (LinearLayout) findViewById(R.id.ll_create_msg);
        ll_create_msg.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("短信列表");

    }

    public void onEventMainThread(ResultGetMsgPageListBean msg) {
        if(msg.IsSuccess==true){
            mMessageListBeen=new ArrayList<>();
            mMessageListBeen.addAll(msg.TModel);

            mMessageGroupListAdapter=new MessageGroupListAdapter(mMessageListBeen,mActivity);
            mMessageGroupListAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mMessageGroupListAdapter);

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
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
                    mGetMsgPageByVipIdNet.setData(pageIndex);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            ToastFactory.getLongToast(mContext,mMessageListBeen.get(position).getCreateTime());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_create_msg:
                Skip.mNext(mActivity,MessageGroupActivity.class);
                break;
        }
    }
}
