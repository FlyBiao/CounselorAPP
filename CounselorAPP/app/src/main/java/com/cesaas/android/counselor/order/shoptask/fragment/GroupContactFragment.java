package com.cesaas.android.counselor.order.shoptask.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shoptask.adapter.ContactAdapter;
import com.cesaas.android.counselor.order.shoptask.bean.CounselorListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ResultCounselorListBean;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.shoptask.net.CounselorListNet;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.lidroid.xutils.exception.HttpException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 组联系人
 * Created at 2017/5/10 13:35
 * Version 1.0
 */

public class GroupContactFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private TextView tv_not_data;
    private View view;

    private int pageIndex=1;
    private int type=2;	//查询类型： 0=全部，1=最近联系人，2=组联系人，3=部门联系人
    private int resultCode=701;
    private String roleIds;
    private String multi;

    private CounselorListNet mCounselorListNet;
    private ContactAdapter mContactAdapter;
    private List<CounselorListBean> mCounselorListBeanList;

    List<SelectCounselorListBean> selectCounselorList=new ArrayList<>();

    /**
     * 单例
     */
    public static GroupContactFragment newInstance() {
        GroupContactFragment fragmentCommon = new GroupContactFragment();
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_group_contact_layout, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent=getActivity().getIntent();
        roleIds= intent.getStringExtra("RoleIds");
        multi= intent.getStringExtra("Multi");

        if(roleIds!=null){
            initData(Integer.parseInt(roleIds));
        }else{
            initData(0);
        }

    }

    private void initData(int roleIds){
        mCounselorListNet=new CounselorListNet(getContext());
        mCounselorListNet.setData(pageIndex,type,"",roleIds);
    }

    private void initView() {
        //通过EventBus订阅事件
        EventBus.getDefault().register(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_group_contact_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        tv_not_data= (TextView) view.findViewById(R.id.tv_not_data);
    }


    public void onEventMainThread(final ResultCounselorListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null){
                mCounselorListBeanList=new ArrayList<>();
                mCounselorListBeanList.addAll(msg.TModel);
                mContactAdapter=new ContactAdapter(mCounselorListBeanList,getContext());
                mContactAdapter.setOnItemClickListener(onItemClickListener);
                mSwipeMenuRecyclerView.setAdapter(mContactAdapter);
            }
        }else{
            ToastFactory.getLongToast(getContext(),"null:"+msg.Message);
        }
    }

    /**
     * OnItemClickListener
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            selectCounselorList=new ArrayList<>();
            SelectCounselorListBean bean=new SelectCounselorListBean();
            bean.setCOUNSELOR_ICON(mCounselorListBeanList.get(position).getCOUNSELOR_ICON());
            bean.setCOUNSELOR_NICKNAME(mCounselorListBeanList.get(position).getCOUNSELOR_NAME());
            bean.setCOUNSELOR_LEVELNAME(mCounselorListBeanList.get(position).getCOUNSELOR_LEVELNAME());
            bean.setCOUNSELOR_ID(mCounselorListBeanList.get(position).getCOUNSELOR_ID());
            selectCounselorList.add(bean);
            Intent mIntent = new Intent();
            // 设置结果，并进行传送
            mIntent.putExtra("selectList",(Serializable)selectCounselorList);
            getActivity().setResult(resultCode, mIntent);
            getActivity().finish();
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
                    if(roleIds!=null){
                        initData(Integer.parseInt(roleIds));
                    }else{
                        initData(0);
                    }
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);//取消EventBus订阅
        super.onDestroy();
    }
}
