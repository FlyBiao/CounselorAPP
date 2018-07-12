package com.cesaas.android.counselor.order.statistics;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.autoCompletetextview.AutoCompleteTextView;
import com.cesaas.android.counselor.order.cache.CacheManager;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.statistics.adapter.SalesThanScreenAdapter;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetAreaGainBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultSalesThanScreenBean;
import com.cesaas.android.counselor.order.statistics.bean.SalesThanScreenBean;
import com.cesaas.android.counselor.order.statistics.net.GetPowerShopNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 销量对比筛选页面
 */
public class SalesThanScreenActivity extends BasesActivity implements View.OnClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBack;
    private TextView tvBaseTitle,tvBaseRigthTitle;
    private AutoCompleteTextView complTextView;

    private SalesThanScreenAdapter mMenuAdapter;
    private List<SalesThanScreenBean> mThanBeanList;
    private List<String> complTextViewList;

    private GetPowerShopNet getPowerShopNet;

    private CacheManager mCacheManager;
    private String shopCacheJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_than_screen);

        initView();
        setOnPopupItemClickListener();
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        //读取缓存
        mCacheManager=CacheManager.getInstance(this);
        shopCacheJson=mCacheManager.readCache("ShopCacheJson");

        if(shopCacheJson!=null){//从缓存获取
            //添加店铺列表
            ResultSalesThanScreenBean bean=gson.fromJson(shopCacheJson,ResultSalesThanScreenBean.class);
            mThanBeanList = new ArrayList<>();
            mThanBeanList.addAll(bean.TModel);
            initData(mThanBeanList);

            //添加查询列表
            complTextViewList=new ArrayList<String>();
            for (int i=0; i<mThanBeanList.size(); i++){
                complTextViewList.add(mThanBeanList.get(i).getShopName());
            }
            complTextView.setDatas(complTextViewList);

        }else{//从网络获取
            getPowerShopNet=new GetPowerShopNet(mContext);
            getPowerShopNet.setData();
        }
    }

    /**
     * 设置setOnPopupItemClickListener
     */
    private void setOnPopupItemClickListener(){
        complTextView.setOnPopupItemClickListener(new AutoCompleteTextView.OnPopupItemClickListener() {
            @Override
            public void onPopupItemClick(CharSequence charSequence) {
                ToastFactory.getLongToast(mContext,charSequence.toString());
            }
        });
    }

    /**
     * 接收销量报表业务-获取店铺Json字符串
     * @param msg 消息实体类
     */
    public void onEventMainThread(CacheJsonBean msg) {
        mCacheManager.writeCache("ShopCacheJson",msg.getStrJson());
    }

    /**
     * 接收销量报表业务-获取店铺数据消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultSalesThanScreenBean msg) {
        if(msg.IsSuccess==true){
            mThanBeanList = new ArrayList<>();
            mThanBeanList.addAll(msg.TModel);
            initData(mThanBeanList);

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败!"+msg.Message);
        }
    }

    /**
     * 初始化
     * @param mThanBeanList
     */
    private void initData(List<SalesThanScreenBean> mThanBeanList){
        mMenuAdapter = new SalesThanScreenAdapter(mThanBeanList);
        mMenuAdapter.setOnItemClickListener(onItemClickListener);
        mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);
    }

    private void initView() {
        complTextView= (AutoCompleteTextView) findViewById(R.id.tvAutoCompl);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_screen_view);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvBaseRigthTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRigthTitle.setVisibility(View.VISIBLE);

        tvBaseTitle.setText("销量对比");
        tvBaseRigthTitle.setText("确定");
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
        // 添加滚动监听。
//        mSwipeMenuRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        llBack.setOnClickListener(this);
        tvBaseRigthTitle.setOnClickListener(this);
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
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Toast.makeText(mContext, mThanBeanList.get(position).getShopName(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.tv_base_title_right://确定

                break;
        }
    }

}
