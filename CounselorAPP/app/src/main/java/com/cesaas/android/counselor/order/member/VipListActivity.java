package com.cesaas.android.counselor.order.member;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.cesaas.android.counselor.order.member.adapter.VipListAdapter;
import com.cesaas.android.counselor.order.member.bean.ResultVipListBean;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.member.bean.VipListBean;
import com.cesaas.android.counselor.order.member.net.VipListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员列表
 */
public class VipListActivity extends BasesActivity implements View.OnClickListener{
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout mBack;
    private TextView mTvTitle,mTvRightTitle,tvSearchName;
    private MClearEditText etSearchValue;

    private int resultCode=401;
    private int searchResultCode=1000;
    private int pageIndex=1;
    private VipListNet mVipListNet;
    private List<VipListBean> mVipListBeen;
    private VipListAdapter mVipListAdapter;

    private List<SelectVipListBean> selectVipListBeen=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_list);
        mVipListNet=new VipListNet(mContext);
        initView();
        initData(1);
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

            SelectVipListBean bean=new SelectVipListBean();
            bean.setVipId(mVipListBeen.get(position).getVipId());
            bean.setNickName(mVipListBeen.get(position).getNickName());
            bean.setImage(mVipListBeen.get(position).getImage());
            bean.setCardName(mVipListBeen.get(position).getCardName());
            bean.setMobile(mVipListBeen.get(position).getMobile());
            bean.setPosition(position);
            selectVipListBeen.add(bean);

            Intent mIntent = new Intent();
            mIntent.putExtra("selectList",(Serializable)selectVipListBeen);
            setResult(resultCode, mIntent);// 设置结果，并进行传送
            finish();
        }
    };

    private void initView() {
        etSearchValue= (MClearEditText) findViewById(R.id.et_search_value);
        etSearchValue.setOnClickListener(this);
        tvSearchName= (TextView) findViewById(R.id.tv_search_name);
        tvSearchName.setOnClickListener(this);
        mBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mBack.setOnClickListener(this);
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTvRightTitle.setOnClickListener(this);
        mTvRightTitle.setVisibility(View.VISIBLE);
        mTvRightTitle.setText("确定");
        mTvTitle.setText("会员列表");

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_vip_list_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    public void onEventMainThread(List<SelectVipListBean> selectVipList) {
        selectVipListBeen=new ArrayList<>();
        selectVipListBeen=selectVipList;
    }

    public void onEventMainThread(ResultVipListBean msg) {
        if(msg.IsSuccess==true){
            mVipListBeen=new ArrayList<>();
            mVipListBeen.addAll(msg.TModel);

            mVipListAdapter=new VipListAdapter(mVipListBeen,mContext);
            mVipListAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mVipListAdapter);
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
                    initData(1);
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
                Toast.makeText(VipListActivity.this, "滑到最底部了，去加载更多吧！", Toast.LENGTH_SHORT).show();
//                size += 50;
//                for (int i = size - 50; i < size; i++) {
//                    mDataList.add("我是第" + i + "个。");
////                }
//                mVipListNet.setData(pageIndex+1);
//                mVipListAdapter.notifyDataSetChanged();
            }
        }
    };


    private void initData(int page) {
        mVipListNet.setData(page);
        pageIndex=page;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;

            case R.id.tv_search_name:
                mVipListNet.setData(pageIndex,etSearchValue.getText().toString());
                break;

            case R.id.et_search_value:
                Skip.mActivityResult(mActivity,VipSearchActivity.class,searchResultCode);
                break;

            case R.id.tv_base_title_right:
                if(selectVipListBeen.size()!=0){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("selectList",(Serializable)selectVipListBeen);
                    // 设置结果，并进行传送
                    setResult(resultCode, mIntent);
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择会员！");
                }
                break;
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(data!=null){
                String searchValue = data.getStringExtra("searchValue");
                etSearchValue.setText(searchValue);
                mVipListNet.setData(pageIndex,searchValue);
            }
        }
    }
}
