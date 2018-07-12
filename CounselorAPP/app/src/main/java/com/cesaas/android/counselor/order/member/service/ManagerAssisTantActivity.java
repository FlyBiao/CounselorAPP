package com.cesaas.android.counselor.order.member.service;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.adapter.manager.ManagerAssisTantAdapter;
import com.cesaas.android.counselor.order.member.adapter.manager.ManagerVipGradeAdapter;
import com.cesaas.android.counselor.order.member.adapter.member.AllMemberAdapter;
import com.cesaas.android.counselor.order.member.bean.manager.ResultVipFansNumsListByShopIdBean;
import com.cesaas.android.counselor.order.member.bean.manager.ResultVipGradeNumsListBean;
import com.cesaas.android.counselor.order.member.bean.manager.VipFansNumsListByShopIdBean;
import com.cesaas.android.counselor.order.member.bean.manager.VipGradeNumsListBean;
import com.cesaas.android.counselor.order.member.net.service.MemberServiceListNet;
import com.cesaas.android.counselor.order.member.net.service.VipFansNumsListByShopIdNet;
import com.cesaas.android.counselor.order.member.net.service.VipGradeNumsListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.chart.CakeSurfaceView;
import com.cesaas.android.counselor.order.utils.chart.PieChartView;
import com.cesaas.android.counselor.order.utils.chart.bean.CakeValue;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.don.pieviewlibrary.PercentPieView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 店长管理店员
 */
public class ManagerAssisTantActivity extends BasesActivity implements View.OnClickListener,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private RecyclerView rv_vip_grade_list;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView tv_not_data,tv_shop_name,tv_shop_fans_sum,tv_shop_vip_sum;
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle;
    private CakeSurfaceView pieChart;

    private int totalSize=0;
    private int delayMillis = 2000;
    private int pageIndex=1;
    private int mCurrentCounter = 0;
    private boolean isErr=true;
    private boolean mLoadMoreEndGone = false;
    private boolean isLoadMore=false;

    private int fansSum;
    private int vipSum;

    private VipGradeNumsListNet vipGradeNumsListNet;
    private VipFansNumsListByShopIdNet vipFansNumsListByShopIdNet;
    private ManagerVipGradeAdapter vipGradeAdapter;
    private List<VipGradeNumsListBean> gradeNumsListBeen=new ArrayList<>();
    private ManagerAssisTantAdapter adapter;
    private List<VipFansNumsListByShopIdBean> mData=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_assistant);
        initView();
        initData();
    }

    /**
     * 查询会员等级数分组
     * @param msg
     */
    public void onEventMainThread(ResultVipGradeNumsListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                gradeNumsListBeen=new ArrayList<>();
                gradeNumsListBeen.addAll(msg.TModel);

                List<CakeValue> itemBeanList = new ArrayList<>();
                List<String> chartNames=new ArrayList<>();
                List<Integer> chartDatas=new ArrayList<>();
                List<Integer> chartColors=new ArrayList<>();

                for (int i=0;i<gradeNumsListBeen.size();i++){
                    if(gradeNumsListBeen.get(i).getFontColor()!=null && !"".equals(gradeNumsListBeen.get(i).getFontColor())){
                        chartColors.add(Color.parseColor(gradeNumsListBeen.get(i).getFontColor()));
                        itemBeanList.add(new CakeValue(gradeNumsListBeen.get(i).getVipGradeName(),gradeNumsListBeen.get(i).getNums(),gradeNumsListBeen.get(i).getFontColor()));
                    }else{
                        chartColors.add(Color.parseColor("#4D5A9E"));
                        itemBeanList.add(new CakeValue(gradeNumsListBeen.get(i).getVipGradeName(),gradeNumsListBeen.get(i).getNums(),"#4D5A9E"));
                    }
                    chartNames.add(gradeNumsListBeen.get(i).getNums()+"名");
                    chartDatas.add(gradeNumsListBeen.get(i).getNums());
                }
                PercentPieView percentPieView = (PercentPieView) findViewById(R.id.percentPieView);
                String[] names = new String[chartNames.size()];

                Integer[] datasInteger=new Integer[chartDatas.size()];
                Integer[] datasIntegers=chartDatas.toArray(datasInteger);
                int[] intArray = new int[datasIntegers.length];
                for(int i=0; i < datasIntegers.length; i ++)
                {
                    intArray[i] = datasIntegers[i].intValue();
                }

                Integer[] colorInteger=new Integer[chartColors.size()];
                Integer[] colorIntegers=chartColors.toArray(colorInteger);
                int[] colorArray = new int[colorIntegers.length];
                for(int i=0; i < colorIntegers.length; i ++)
                {
                    colorArray[i] = colorIntegers[i].intValue();
                }

                percentPieView.setData(intArray, chartNames.toArray(names),colorArray);

                pieChart.setData(itemBeanList);
                vipGradeAdapter=new ManagerVipGradeAdapter(gradeNumsListBeen,mActivity,mContext);
                rv_vip_grade_list.setAdapter(vipGradeAdapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员等级失败："+msg.Message);
        }
    }

    /**
     * 查询会员粉丝数分组
     * @param msg
     */
    public void onEventMainThread(ResultVipFansNumsListByShopIdBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                for (int i=0;i<mData.size();i++){
                    fansSum+=mData.get(i).getFansNums();
                    vipSum+=mData.get(i).getVipNums();
                }
                tv_shop_fans_sum.setText(String.valueOf(fansSum));
                tv_shop_vip_sum.setText(String.valueOf(vipSum));

                adapter=new ManagerAssisTantAdapter(mData,mActivity,mContext);
                mRecyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        bundle.putString("Title",mData.get(position).getCounselorName());
                        Skip.mNextFroData(mActivity,ManagerAssisTantDatailsActivity.class,bundle);
                    }
                });
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员粉丝数分组失败："+msg.Message);
        }
    }

    public void initView(){
        pieChart = (CakeSurfaceView)findViewById(R.id.assets_pie_chart);
        tv_shop_name= (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_name.setText(prefs.getString("shopName"));
        tv_shop_fans_sum= (TextView) findViewById(R.id.tv_shop_fans_sum);
        tv_shop_vip_sum= (TextView) findViewById(R.id.tv_shop_vip_sum);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText("会员报表");
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        tv_not_data= (TextView) findViewById(R.id.tv_not_data);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView= (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        rv_vip_grade_list= (RecyclerView) findViewById(R.id.rv_vip_grade_list);
        rv_vip_grade_list.setLayoutManager(new LinearLayoutManager(mContext));

    }

    public void initData(){
        vipGradeNumsListNet=new VipGradeNumsListNet(mContext);
        vipGradeNumsListNet.setData();

        vipFansNumsListByShopIdNet=new VipFansNumsListByShopIdNet(mContext);
        vipFansNumsListByShopIdNet.setData();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }

    @Override
    public void onRefresh() {
        isLoadMore=false;
        mData=new ArrayList<>();
        vipGradeAdapter=new ManagerVipGradeAdapter(gradeNumsListBeen,mActivity,mContext);
        adapter=new ManagerAssisTantAdapter(mData,mActivity,mContext);
        adapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageIndex=1;
                initData();
                isErr = false;
                mCurrentCounter = Constant.PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                adapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
