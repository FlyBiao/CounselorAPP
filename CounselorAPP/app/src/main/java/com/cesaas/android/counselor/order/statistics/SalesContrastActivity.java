package com.cesaas.android.counselor.order.statistics;

import android.graphics.drawable.Drawable;
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
import com.cesaas.android.counselor.order.cache.CacheManager;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.statistics.adapter.SalesThanAdapter;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetAreaGainBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetchievementReportBean;
import com.cesaas.android.counselor.order.statistics.bean.SalesThanBean;
import com.cesaas.android.counselor.order.statistics.bean.ThanShopIdArrayBean;
import com.cesaas.android.counselor.order.statistics.net.GetAreaGainNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 总销量对比
 */
public class SalesContrastActivity extends BasesActivity implements View.OnClickListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private LinearLayout llBack;
    private TextView tvBaseTitle,tvBaseRightTitle;

    private SalesThanAdapter mMenuAdapter;
    private List<SalesThanBean> mThanBeanList;

    private GetAreaGainNet getAreaGainNet;
    private List<String> shopIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_contrast);
        initView();

        shopIdList=new ArrayList<>();
        shopIdList.add(prefs.getString("ShopId"));

        getAreaGainNet=new GetAreaGainNet(mContext);
        getAreaGainNet.setData(ThanShopIdArrayBean.getArrayItem(shopIdList));
    }

    /**
     * 接收销量对比业务报表数据消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultGetAreaGainBean msg) {
        if(msg.IsSuccess==true){
            mThanBeanList = new ArrayList<>();
            mThanBeanList.addAll(msg.TModel);

            mMenuAdapter = new SalesThanAdapter(mThanBeanList);
            mMenuAdapter.setOnItemClickListener(onItemClickListener);
            mSwipeMenuRecyclerView.setAdapter(mMenuAdapter);

        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败!"+msg.Message);
        }
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRightTitle.setVisibility(View.VISIBLE);

        tvBaseTitle.setText("销量对比");
        tvBaseRightTitle.setText("");
        Drawable drawable= getResources().getDrawable(R.mipmap.screen);
        drawable.setBounds( 0 ,  0 , drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvBaseRightTitle.setCompoundDrawables(drawable, null , null , null );
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，加也行，不加也行。

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        llBack.setOnClickListener(this);
        tvBaseRightTitle.setOnClickListener(this);
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
            //处理点击 RecyclerView Item Click Listener
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.tv_base_title_right://筛选
                Skip.mNext(mActivity,SalesThanScreenActivity.class);
                break;
        }
    }
}
