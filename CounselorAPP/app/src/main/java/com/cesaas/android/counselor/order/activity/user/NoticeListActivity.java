package com.cesaas.android.counselor.order.activity.user;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.adapter.NoticeAdapter;
import com.cesaas.android.counselor.order.activity.user.bean.NoticeBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultNoticeBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultNoticeListBean;
import com.cesaas.android.counselor.order.activity.user.net.NoticeNet;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shoptask.bean.ResultCounselorListBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 公告列表页面
 */
public class NoticeListActivity extends BasesActivity implements View.OnClickListener{
    private LinearLayout llBack;
    private TextView tvTitle;
    private TextView tvNotData;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private int pageIndex=1;
    private NoticeNet noticeNet;
    private NoticeAdapter noticeAdapter;
    private List<NoticeBean> noticeBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initView();
        initData();
    }

    private void initData(){
        noticeNet=new NoticeNet(mContext);
        noticeNet.setData(pageIndex);
    }

    public void onEventMainThread(final ResultNoticeListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel.size()!=0){
                noticeBeanList=new ArrayList<>();
                noticeBeanList.addAll(msg.TModel);

                noticeAdapter=new NoticeAdapter(noticeBeanList,mContext);
                noticeAdapter.setOnItemClickListener(onItemClickListener);
                mSwipeMenuRecyclerView.setAdapter(noticeAdapter);
            }else{
                tvNotData.setVisibility(View.VISIBLE);
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取公告失败！"+msg.Message);
        }
    }

    private void initView() {
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("公告列表");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvNotData= (TextView) findViewById(R.id.tv_not_data);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_notice_list_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            //跳转到公告详情页面
            bundle.putInt("noticeId",noticeBeanList.get(position).getId());
            Skip.mNextFroData(mActivity, NoticeDetailActivity.class,bundle);
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
                    noticeNet=new NoticeNet(mContext);
                    noticeNet.setData(1);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
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
