package com.cesaas.android.counselor.order.statistics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean;
import com.cesaas.android.counselor.order.custom.progress.CustomIndicateProgressView;
import com.cesaas.android.counselor.order.earnings.activity.EarningsActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.statistics.adapter.GalleryMonthAdapter;
import com.cesaas.android.counselor.order.statistics.adapter.StatisticsMangerGridAdapter;
import com.cesaas.android.counselor.order.statistics.adapter.StoreStatisticsMangerGridAdapter;
import com.cesaas.android.counselor.order.statistics.bean.GalleryMonthBean;
import com.cesaas.android.counselor.order.statistics.bean.GetchievementReportBean;
import com.cesaas.android.counselor.order.statistics.bean.InfactGainBean;
import com.cesaas.android.counselor.order.statistics.bean.ResultGetchievementReportBean;
import com.cesaas.android.counselor.order.statistics.net.GetPowerShopNet;
import com.cesaas.android.counselor.order.statistics.net.GetchievementReportNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.gridview.CostomMangerGridView;
import com.cesaas.android.counselor.order.widget.gridview.MyGridView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * 统计总报表页面
 */
public class ReportStatisticsActivity extends BasesActivity {

    private LinearLayout llBack;
    private TextView tvBaseTitle,tvCurrentDate;
    private TextView tvTodaySalesVolume,tvTodaySalesSum,tvTodayOrderSum;
    private TextView tvMonthSalesVolume,tvMonthSalesSum,tvMonthSalesOrderSum;
    private TextView tvYearSalesVolume,tvYearSalesSum,tvYearSalesOrderSum;

    private CostomMangerGridView costomMangerGridView;
    private MyGridView gridViewMenu;//GridView菜单
    private CustomIndicateProgressView indicateProgressView;

    //菜单名称
    private List<String> menuNames;
    //菜单图片
    private List<Integer> menuImages;

    //菜单名称
    private List<String> menus;
    //菜单图片
    private List<String> titles;

    private GetchievementReportNet getchievementReportNet;
    private List<InfactGainBean> getchievementReportBeanList=new ArrayList<>();

    private RecyclerView mRecyclerView;
    private GalleryMonthAdapter mAdapter;
    private List<GalleryMonthBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_statistics);

        getchievementReportNet=new GetchievementReportNet(mContext);
        getchievementReportNet.setData();

        initView();
        initData();
    }

    private void initDatas()
    {
        mDatas = new ArrayList<>();
        for (int i=0;i<getchievementReportBeanList.size();i++){
            GalleryMonthBean monthBean=new GalleryMonthBean();
            monthBean.setMonth(getchievementReportBeanList.get(i).Month);
            monthBean.setVolume(getchievementReportBeanList.get(i).Values);
            mDatas.add(monthBean);
        }
        //设置适配器
        mAdapter = new GalleryMonthAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 接收报表业务设计图获取当天业务报表数据消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultGetchievementReportBean msg) {
        if(msg.IsSuccess==true){
            tvTodaySalesVolume.setText(msg.TModel.TodaySalesCount+"");
            tvTodaySalesSum.setText(msg.TModel.TodayItemsCount+"");
            tvTodayOrderSum.setText(msg.TModel.TodayOrderCount+"");

            tvMonthSalesVolume.setText(msg.TModel.MonthSalesCount+"");
            tvMonthSalesSum.setText(msg.TModel.MonthItemsCount+"");
            tvMonthSalesOrderSum.setText(msg.TModel.MonthOrderCount+"");

            tvYearSalesVolume.setText(msg.TModel.YearSalesCount+"");
            tvYearSalesSum.setText(msg.TModel.YearItemsCount+"");
            tvYearSalesOrderSum.setText(msg.TModel.YearOrderCount+"");

            menus=new ArrayList<String>();
            titles=new ArrayList<String>();

            titles.add("会员消费");
            menus.add(msg.TModel.TodayMemebersConsumption+"");
            titles.add("非会员消费");
            menus.add(msg.TModel.TodayUnMemberConsumption+"");
            titles.add("订单总数");
            menus.add(msg.TModel.CountAllOrder+"");
            titles.add("总会员数");
            menus.add(msg.TModel.CountAllMember+"");

            //设置店铺数据九宫格
            costomMangerGridView.setAdapter(new StoreStatisticsMangerGridAdapter(mContext,menus,titles));
            getchievementReportBeanList.addAll(msg.TModel.InfactGain);

            initDatas();
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败："+msg.Message);
        }

    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        tvTodaySalesVolume= (TextView) findViewById(R.id.tv_today_sales_volume);
        tvTodaySalesSum= (TextView) findViewById(R.id.tv_today_sales_sum);
        tvTodayOrderSum= (TextView) findViewById(R.id.tv_today_order_sum);
        tvMonthSalesVolume= (TextView) findViewById(R.id.tv_month_sales_volume);
        tvMonthSalesSum= (TextView) findViewById(R.id.tv_month_sales_sum);
        tvMonthSalesOrderSum= (TextView) findViewById(R.id.tv_month_sales_order_sum);
        tvYearSalesVolume= (TextView) findViewById(R.id.tv_year_sales_volume);
        tvYearSalesSum= (TextView) findViewById(R.id.tv_year_sales_sum);
        tvYearSalesOrderSum= (TextView) findViewById(R.id.tv_year_sales_order_sum);
        tvCurrentDate= (TextView) findViewById(R.id.tv_current_date);
        tvCurrentDate.setText(AbDateUtil.getToDayDate());

        //得到控件
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview_horizontal);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        indicateProgressView= (CustomIndicateProgressView) findViewById(R.id.indicate_progress);
        costomMangerGridView= (CostomMangerGridView) findViewById(R.id.gv_store_data);
        gridViewMenu= (MyGridView) findViewById(R.id.gv_menu);
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        setBack();
    }

    /**
     * 设置返回上一个页面
     */
    public void setBack(){
        llBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    /**
     * 初始化数据
     */
    public void initData(){
        tvBaseTitle.setText("业绩报表");

        indicateProgressView.setMax(100);
        indicateProgressView.setProgress(80);

        //判断店员店长的权限查看内容
        //经理级别以上
            setStoreManagerData();
        //店员，店长
//            setSummaryStoreManagerData();
//          setStoreManagerData();
//         setStoreStaticsData();//暂时不这么实现
        //设置店铺数据九宫格
//        costomMangerGridView.setAdapter(new StoreStatisticsMangerGridAdapter(mContext,menus,titles));
    }

    /**
     * 设置汇总数据
     */
    public void setSummaryStoreManagerData(){
        menuNames=new ArrayList<String>();
        menuImages=new ArrayList<Integer>();

        menuNames.add("完成对比");
        menuImages.add(R.mipmap.complete_contrast);
        menuNames.add("销量总览");
        menuImages.add(R.mipmap.sales_overview);
        menuNames.add("销量对比");
        menuImages.add(R.mipmap.sales_contrast);
        menuNames.add("门店日报");
        menuImages.add(R.mipmap.store_daypaper);
        menuNames.add("直营Top");
        menuImages.add(R.mipmap.direct_selling_top);
        menuNames.add("加盟Top");
        menuImages.add(R.mipmap.direct_selling_top);

        //设置菜单九宫格数据
        gridViewMenu.setAdapter(new StatisticsMangerGridAdapter(mContext,menuNames,menuImages));
        //设置点击事件
        gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://完成率对比
                        Skip.mNext(mActivity,CompleteContrastActivity.class);
                        break;
                    case 1://销量总览
                        Skip.mNext(mActivity,SalesOverviewActivity.class);
                        break;
                    case 2://销售对比
                        Skip.mNext(mActivity,SalesContrastActivity.class);
                        break;
                    case 3://门店日报
                        break;
                    case 4://直营TOP10
                        Skip.mNext(mActivity,DirectSellingTopActivity.class);
                        break;
                    case 5://加盟TOP10
                        Skip.mNext(mActivity,AffiliateTopActivity.class);
                        break;
                }
            }
        });
    }

    /**
     * 设置店长数据
     */
    public void setStoreManagerData(){
        menuNames=new ArrayList<String>();
        menuImages=new ArrayList<Integer>();

        menuNames.add("销售对比");
        menuImages.add(R.mipmap.sales_contrast);
        menuNames.add("直营Top");
        menuImages.add(R.mipmap.direct_selling_top);

        //设置菜单九宫格数据
        gridViewMenu.setAdapter(new StatisticsMangerGridAdapter(mContext,menuNames,menuImages));
        //设置点击事件
        gridViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://销售对比
                        Skip.mNext(mActivity,SalesContrastActivity.class);
                        break;
                    case 1://直营Top10
                        Skip.mNext(mActivity,AffiliateTopActivity.class);
                        break;
                }
            }
        });
    }

    /**
     * 设置门店统计数据
     */
    public void setStoreStaticsData(){
        menus=new ArrayList<String>();
        titles=new ArrayList<String>();

        titles.add("会员消费");
        menus.add("333");
        titles.add("非会员消费");
        menus.add("333");
        titles.add("订单总数");
        menus.add("333");
        titles.add("总会员数");
        menus.add("333");
    }

}
