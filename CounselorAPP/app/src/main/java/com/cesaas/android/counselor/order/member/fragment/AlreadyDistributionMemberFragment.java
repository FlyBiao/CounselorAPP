package com.cesaas.android.counselor.order.member.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.MemberDistributionDetailActivity;
import com.cesaas.android.counselor.order.member.adapter.MemberDistributionAdapter;
import com.cesaas.android.counselor.order.member.bean.MBaseBack;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionBean;
import com.cesaas.android.counselor.order.member.bean.ResultMemberDistributionBean;
import com.cesaas.android.counselor.order.member.bean.ResultMemberDistributionDetailBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 已分配会员
 * Created 2017/4/12 14:44
 * Version 1.zero
 */
public class AlreadyDistributionMemberFragment extends BaseFragment{
    @Override
    public int getLayoutId() {
        return  R.layout.activity_alread_distributionmember;
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private int pageIndex=1;
    private int status=1;

    private MemberDistributionAdapter mAdapter;
    private List<MemberDistributionBean> mMemberDistributionBeanList;
    private AlreadyDistributionMemberNet mDistributionMemberNet;

    @Override
    public void initViews() {
        mDistributionMemberNet=new AlreadyDistributionMemberNet(getContext());
        mDistributionMemberNet.setData(pageIndex,status);

        mSwipeRefreshLayout=findView(R.id.swipe_layout);
        mSwipeMenuRecyclerView=findView(R.id.recycler_distribution_member_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
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
                    mDistributionMemberNet.setData(pageIndex,status);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if(mMemberDistributionBeanList.get(position).getFANS_NUM()!=0){
                bundle.putInt("CounselorID",mMemberDistributionBeanList.get(position).getCOUNSELOR_ID());
                Skip.mNextFroData(getActivity(),MemberDistributionDetailActivity.class,bundle);
            }else{
                ToastFactory.getLongToast(getContext(),"该导购没有可维护会员！");
            }
        }
    };

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }

    public void onEventMainThread(MBaseBack back){
        if(back.isBack()==true){
            mDistributionMemberNet=new AlreadyDistributionMemberNet(getContext());
        }
    }


    /**
     * Author FGB
     * Description 获取已分配导购列表Net
     * Created 2017/4/21 16:26
     * Version 1.zero
     */
    public class AlreadyDistributionMemberNet extends BaseNet {
        public AlreadyDistributionMemberNet(Context context) {
            super(context, true);
            this.uri="User/SW/Counselor/getList";
        }

        public void setData(int PageIndex,int Status){

            try{
                data.put("PageIndex",PageIndex);
                data.put("PageSize",30);
                data.put("Status",Status);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Log.i(Constant.TAG,"已分配:"+rJson);
            ResultMemberDistributionBean bean= JsonUtils.fromJson(rJson,ResultMemberDistributionBean.class);
            if(bean.IsSuccess==true){
                if(bean.TModel!=null){
                    mMemberDistributionBeanList=new ArrayList<>();
                    mMemberDistributionBeanList.addAll(bean.TModel);

                    mAdapter=new MemberDistributionAdapter(mMemberDistributionBeanList,getContext());
                    mAdapter.setOnItemClickListener(onItemClickListener);
                    mSwipeMenuRecyclerView.setAdapter(mAdapter);

                }

            }else{
                ToastFactory.getLongToast(getContext(),"获取数据失败！"+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }
}
