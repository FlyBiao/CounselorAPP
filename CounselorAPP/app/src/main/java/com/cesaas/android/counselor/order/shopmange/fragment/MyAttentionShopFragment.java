package com.cesaas.android.counselor.order.shopmange.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shopmange.adapter.AllShopAdapter;
import com.cesaas.android.counselor.order.shopmange.adapter.MyAttentionShopAdapter;
import com.cesaas.android.counselor.order.shopmange.bean.AllShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.RecentPurchaseBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultAttentionBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultGetAllShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultRecentPurchaseBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.workbench.adapter.ReturnVisitGalleryRecyclerAdapter;
import com.lidroid.xutils.exception.HttpException;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 我的关注商品
 * Created 2017/4/27 17:18
 * Version 1.0
 */
public class MyAttentionShopFragment extends Fragment {

    private View view;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private RecyclerView rv_shop_product;
    private TextView tv_recent_purchase_count;

    private int pageIndex=1;
    private int Type=0;
    private int vipId;
    protected AbPrefsUtil prefs;

    private MyAttentionNet mMyAttentionNet;
    private MyAttentionShopAdapter mAllShopAdapter;
    private List<AllShopBean> mAllShopBeanList;

    private ReturnVisitGalleryRecyclerAdapter mRecyclerAdapter;
    private List<RecentPurchaseBean> urls;
    private RecentPurchaseNet mRecentPurchaseNet;

    /**
     * 单例
     */
    public static MyAttentionShopFragment newInstance() {
        MyAttentionShopFragment fragmentCommon = new MyAttentionShopFragment();
        return fragmentCommon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my_attention_shop_layout, container, false);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prefs = AbPrefsUtil.getInstance();
        vipId=Integer.parseInt(prefs.getString("VipId"));

        mRecentPurchaseNet=new RecentPurchaseNet(getContext());
        mRecentPurchaseNet.setData(pageIndex,vipId);

        mMyAttentionNet=new MyAttentionNet(getContext());
        mMyAttentionNet.setData(pageIndex,Type);

        initData();
    }

    private void initData(){

    }

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {

        }
    };

    private void initView() {
        tv_recent_purchase_count= (TextView) view.findViewById(R.id.tv_recent_purchase_count);
        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView= (SwipeMenuRecyclerView) view.findViewById(R.id.recycler_attention_shop_view);

        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

        rv_shop_product= (RecyclerView)view. findViewById(R.id.rv_shop_product);//固定高度
        rv_shop_product.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());//创建布局管理器
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);//设置横向
        rv_shop_product.setLayoutManager(linearLayoutManager);//设置布局管理器
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
                    mMyAttentionNet=new MyAttentionNet(getContext());
                    mMyAttentionNet.setData(pageIndex,Type);

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void fetchData() {

    }

    /**
     * Author FGB
     * Description 获取所有商品Net
     * Created 2017/4/27 20:28
     * Version 1.0
     */
    public class MyAttentionNet extends BaseNet {
        public MyAttentionNet(Context context) {
            super(context, true);
            this.uri="Marketing/Sw/Style/GetAppList";
        }

        public void setData(int PageIndex,int Type){
            try{

                data.put("Type",Type);//0所有，1最新，2预售，3关注
                data.put("PageIndex",PageIndex);
                data.put("PageSize",30);
                data.put("UserTicket",
                        AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultAttentionBean bean= JsonUtils.fromJson(rJson,ResultAttentionBean.class);
            if(bean.IsSuccess==true){
                if(bean.TModel!=null){
                    mAllShopBeanList=new ArrayList<>();
                    mAllShopBeanList.addAll(bean.TModel);

                    mAllShopAdapter=new MyAttentionShopAdapter(mAllShopBeanList,getContext());
                    mAllShopAdapter.setOnItemClickListener(onItemClickListener);
                    mSwipeMenuRecyclerView.setAdapter(mAllShopAdapter);
                }else{
                    ToastFactory.getLongToast(getContext(),"没有获取商品数据！"+bean.Message);
                }

            }else{
                ToastFactory.getLongToast(getContext(),"获取商品信息失败！"+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }


    /**
     * Author FGB
     * Description 最近购买商品Net
     * Created 2017/4/28 9:46
     * Version 1.0
     */
    public class RecentPurchaseNet extends BaseNet{
        public RecentPurchaseNet(Context context) {
            super(context, true);
            this.uri="Order/Sw/Order/RecentPurchase";
        }

        public void setData(int PageIndex,int VipId){
            try{
                data.put("VipId",VipId);
                data.put("PageIndex",PageIndex);
                data.put("PageSize",30);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultRecentPurchaseBean bean= JsonUtils.fromJson(rJson,ResultRecentPurchaseBean.class);
            if(bean.IsSuccess){
                if(bean.TModel!=null){
                    urls=new ArrayList<>();
                    urls.addAll(bean.TModel);
                    tv_recent_purchase_count.setText(urls.size()+"");
                    mRecyclerAdapter=new ReturnVisitGalleryRecyclerAdapter(getContext(),urls);
                    rv_shop_product.setAdapter(mRecyclerAdapter);
                }else{
                    ToastFactory.getLongToast(mContext,"没有最近购买商品！");
                }
            }else{
                ToastFactory.getLongToast(mContext,"获取最近购买数据失败！");
            }
        }
        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }


}
