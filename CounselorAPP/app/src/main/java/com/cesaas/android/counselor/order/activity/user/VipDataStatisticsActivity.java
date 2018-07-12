package com.cesaas.android.counselor.order.activity.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapters.VipDataStatisticsGridAdapter;
import com.cesaas.android.counselor.order.bean.VipDataStatisticsBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.store.bean.StoreDisplayBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.cesaas.android.counselor.order.widget.gridview.CostomMangerGridView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 会员数据统计页面
 * Created 2017/3/2 10:33
 * Version 1.zero
 */
public class VipDataStatisticsActivity extends BasesActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvBaseTitle,tvBaseRightTitle;

    private LoadMoreListView mLoadMoreListView;//加载更多
    private RefreshAndLoadMoreView mRefreshAndLoadMoreView;//下拉刷新
    private boolean refresh=false;
    private static int pageIndex = 1;//当前页

    private ArrayList<VipDataStatisticsBean> vipDataStatisticsBeen= new ArrayList<VipDataStatisticsBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_data_statistics);

        initView();
        setData();

    }

    private void setData() {
        tvBaseTitle.setText("会员统计报表");
        tvBaseRightTitle.setVisibility(View.VISIBLE);
        tvBaseRightTitle.setText("筛选");

        for (int i = 0; i < 5; i++) {
            VipDataStatisticsBean bean=new VipDataStatisticsBean();
            bean.setShopName("门店店铺"+i);
            bean.setTodayVip(12+i);
            bean.setMonthVip(34+i);
            bean.setTotalVip(56+i);
            vipDataStatisticsBeen.add(bean);
        }
        setAdapter();
    }

    public void setAdapter(){
        mLoadMoreListView.setAdapter(new CommonAdapter<VipDataStatisticsBean>(mContext,R.layout.item_vip_data,vipDataStatisticsBeen) {

            @Override
            public void convert(ViewHolder holder, VipDataStatisticsBean bean, int postion) {
                Log.i(Constant.TAG,"==="+bean.getShopName());
                holder.setText(R.id.tv_shop_name,bean.getShopName());
                holder.setText(R.id.tv_today_vip,bean.getTodayVip()+"");
                holder.setText(R.id.tv_month_vip,bean.getMonthVip()+"");
                holder.setText(R.id.tv_total_vip,bean.getTotalVip()+"");
                mLoadMoreListView.setHaveMoreData(false);
            }
        });
    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        mLoadMoreListView=(LoadMoreListView) findViewById(R.id.load_more_vip_data_list);
        mRefreshAndLoadMoreView=(RefreshAndLoadMoreView) findViewById(R.id.refresh_vip_data_and_load_more);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);

        //设置视图控件监听
        llBack.setOnClickListener(this);
        tvBaseRightTitle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right://筛选
                Skip.mNext(mActivity,VipDataScreenActivity.class);
                break;
        }

    }
}
