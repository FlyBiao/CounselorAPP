package com.cesaas.android.counselor.order.salestalk.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.viewpager.ViewPagerIndicator;
import com.cesaas.android.counselor.order.custom.viewpager.VpSimpleFragment;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.salestalk.adapter.SelectVerbalTrickAdapter;
import com.cesaas.android.counselor.order.salestalk.bean.GetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultGetCategoryBean;
import com.cesaas.android.counselor.order.salestalk.bean.ResultSalesTalkCategoryListBean;
import com.cesaas.android.counselor.order.salestalk.net.GetCategoryNet;
import com.cesaas.android.counselor.order.salestalk.net.GetListNet;
import com.cesaas.android.counselor.order.signin.adapter.SigninTypeGalleryRecyclerAdapter;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 选择话术
 */
public class SelectVerbalTrickActivity extends BasesActivity implements View.OnClickListener{
    private RecyclerView rvTrickTypeMenu;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;
    private GetCategoryNet getCategoryNet;//获取话术分类
    private GetListNet getListNet;

    private int pageIndex=1;
    private int RESPONSE_CODE_OK=402;
    private String group_id;
    private String group_name;

    private List<GetCategoryBean> mTitles;
    private List<ResultSalesTalkCategoryListBean.SalesTalkCategoryListBean> categoryListBean;
    private SigninTypeGalleryRecyclerAdapter mGalleryRecyclerAdapter;
    private SelectVerbalTrickAdapter mVerbalTrickAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_verbal_trick);

        initView();
        initData();
    }

    /**
     * 接受EventBus发送消息【话术分类】
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultGetCategoryBean msg){

        if(msg.IsSuccess==true){
            mTitles=new ArrayList<>();
            mTitles.addAll(msg.TModel);

            //创建适配器
            mGalleryRecyclerAdapter=new SigninTypeGalleryRecyclerAdapter(this,mTitles);
            //绑定适配器
            rvTrickTypeMenu.setAdapter(mGalleryRecyclerAdapter);

            getListNet=new GetListNet(mContext);
            getListNet.setData(mTitles.get(0).Id,pageIndex);
            group_id=mTitles.get(0).Id+"";
            group_name=mTitles.get(0).Content;
            //temClickListener
            mGalleryRecyclerAdapter.setOnRecyclerViewItemClickListener(new SigninTypeGalleryRecyclerAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    group_id=mTitles.get(position).Id+"";
                    group_name=mTitles.get(position).Content;

                    getListNet=new GetListNet(mContext);
                    getListNet.setData(mTitles.get(position).Id,pageIndex);
                }
            });
        }
    }

    public void onEventMainThread(ResultSalesTalkCategoryListBean msg){
        if(msg.IsSuccess=true){

            categoryListBean=new ArrayList<>();
            categoryListBean.addAll(msg.TModel);

            mVerbalTrickAdapter=new SelectVerbalTrickAdapter(categoryListBean,mContext);
            mVerbalTrickAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mVerbalTrickAdapter);

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
            Intent mIntent = new Intent();
            mIntent.putExtra("id", categoryListBean.get(position).Id+"");
            mIntent.putExtra("question",categoryListBean.get(position).Question);
            mIntent.putExtra("content",categoryListBean.get(position).Content);
            mIntent.putExtra("group_id",group_id);
            mIntent.putExtra("group_name",group_name);
//            // 设置结果，并进行传送
            setResult(RESPONSE_CODE_OK, mIntent);
            finish();
        }
    };


    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_trick_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("选择话术");
        llBaseBack.setOnClickListener(this);

        rvTrickTypeMenu= (RecyclerView) findViewById(R.id.rv_trick_type_menu);
        rvTrickTypeMenu.setHasFixedSize(true);//固定高度
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);//创建布局管理器
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);//设置横向
        rvTrickTypeMenu.setLayoutManager(linearLayoutManager);//设置布局管理器
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
                    initData();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private void initData() {
        getCategoryNet=new GetCategoryNet(mContext);
        getCategoryNet.setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }

}
