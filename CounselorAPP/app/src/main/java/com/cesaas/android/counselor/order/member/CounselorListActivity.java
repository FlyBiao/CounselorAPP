package com.cesaas.android.counselor.order.member;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.adapter.MemberDistributionAdapter;
import com.cesaas.android.counselor.order.member.bean.BackBean;
import com.cesaas.android.counselor.order.member.bean.BindVipIdBean;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionBean;
import com.cesaas.android.counselor.order.member.bean.ResultBinVipBean;
import com.cesaas.android.counselor.order.member.bean.ResultMemberDistributionBean;
import com.cesaas.android.counselor.order.member.fragment.AlreadyDistributionMemberFragment;
import com.cesaas.android.counselor.order.member.net.AlreadyDistributionMemberNet;
import com.cesaas.android.counselor.order.member.net.BindVipNet;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 顾问列表
 */
public class CounselorListActivity extends BaseTemplateActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_counselor_list;
    }

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private int pageIndex=1;
    private int status=1;
    private AlreadyDistributionMemberNet mDistributionMemberNet;
    private MemberDistributionAdapter mAdapter;
    private List<MemberDistributionBean> mMemberDistributionBeanList;

    private JSONArray vipArray=new JSONArray();
    private BindVipIdBean mBindVipIdBean;
    private List<BindVipIdBean> selectList;

    public void onEventMainThread(ResultBinVipBean msg){
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"分配会员成功！");
            mDistributionMemberNet.setData(pageIndex,status);
            vipArray=new JSONArray();
            Intent mIntent = new Intent();
            setResult(Constant.H5_FACE_BIND, mIntent);// 设置结果，并进行传送
            finish();
        }else{
            ToastFactory.getLongToast(mContext,"分配会员失败！"+msg.Message);
        }
    }

    public void onEventMainThread(ResultMemberDistributionBean msg){
        if(msg.IsSuccess==true){
            if(msg.TModel!=null){
                mMemberDistributionBeanList=new ArrayList<>();
                mMemberDistributionBeanList.addAll(msg.TModel);

                mAdapter=new MemberDistributionAdapter(mMemberDistributionBeanList,mContext);
                mAdapter.setOnItemClickListener(onItemClickListener);
                mSwipeMenuRecyclerView.setAdapter(mAdapter);

            }
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            if(vipArray.length()!=0){
                //执行分配会员操作
                BindVipNet mBindVipNet=new BindVipNet(mContext);
                mBindVipNet.setData(mMemberDistributionBeanList.get(position).getCOUNSELOR_ID(),vipArray);
            }
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
                    mDistributionMemberNet.setData(pageIndex,status);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };


    @Override
    public void initViews() {
        mDistributionMemberNet=new AlreadyDistributionMemberNet(mContext);
        mDistributionMemberNet.setData(pageIndex,status);

        llBaseBack=findView(R.id.ll_base_title_back);
        tvBaseTitle=findView(R.id.tv_base_title);
        tvBaseTitle.setText("店员列表");

        mSwipeRefreshLayout=findView(R.id.swipe_layout);
        mSwipeMenuRecyclerView=findView(R.id.recycler_counselor_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
//        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    @Override
    public void initListener() {
        llBaseBack.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent data=getIntent();
        selectList=(ArrayList<BindVipIdBean>)data.getSerializableExtra("selectList");
        for (int i=0;i<selectList.size();i++){
            mBindVipIdBean=new BindVipIdBean();
            mBindVipIdBean.setVipId(selectList.get(i).getVipId());
            vipArray.put(mBindVipIdBean.getVips());
        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                BackBean backBean=new BackBean();
                backBean.setBack(true);
                EventBus.getDefault().post(backBean);
                Skip.mBack(mActivity);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        BackBean backBean=new BackBean();
        backBean.setBack(true);
        EventBus.getDefault().post(backBean);
        return super.onKeyDown(keyCode, event);
    }
}
