package com.cesaas.android.counselor.order.boss.ui.activity.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.adapter.member.MemberServiceCategoryAdapter;
import com.cesaas.android.counselor.order.boss.adapter.member.TaskByCounserlorListAdapter;
import com.cesaas.android.counselor.order.boss.bean.member.CategoryDataUtils;
import com.cesaas.android.counselor.order.boss.bean.member.ResultListByShopIdBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultShopSalesListBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultTaskByCounserlorListBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultTaskChartListBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultTaskTypeShopListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskByCounserlorListsBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskChartListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeShopDataBean;
import com.cesaas.android.counselor.order.boss.net.ListByShopIdNet;
import com.cesaas.android.counselor.order.boss.net.ShopSalesListNet;
import com.cesaas.android.counselor.order.boss.net.TaskByCounserlorListNet;
import com.cesaas.android.counselor.order.boss.net.TaskChartListNet;
import com.cesaas.android.counselor.order.boss.net.TaskTypeShopListNet;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryShopActivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 单店服务分析
 */
public class SingleShopMemberServiceAnalysisActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,mTextViewRightTitle;
    private TextView tv_screening,tv_select_category;
    private TextView tv_start_sel_date,tv_end_sel_date,tv_accounted,tv_shop_sums,tv_counserlor_sum,tv_service_sales_p,tv_not_service_sales_p;
    private TextView tv_server_sum,tv_service_finish_sum,tv_service_vaild_sum,tv_service_goshop_sum,tv_service_bill_sum,tv_go_shop,tv_shop_service_finish_sum,tv_shop_service_sum;
    private TextView tv_member_count_sum,tv_member_finish_sum,tv_member_vaild_sum,tv_member_goshop_sum,tv_bill_sum,tv_org_name,tv_shop_name;
    private LinearLayout back,ll_select_category,ll_screening;
    private LinearLayout ll_task,ll_complete,ll_go_shop,ll_price;
    private TextView tv_task,tv_complete,tv_shop,tv_price;
    private RecyclerView rv_task_counserlor_list;
    private ProgressBar pb_service;

    private TaskChartListNet chartListNet;
    private int serviceType=0;
    private int switchType=0;
    private String startDate;
    private String  endDate;
    private JSONArray shopIds=new JSONArray();
    private int shopId;
    private boolean isAccounted=false;
    private boolean isSelectDate=false;
    private TaskChartListBean chartListBean=new TaskChartListBean();

    private ListByShopIdNet listByShopIdNet;
    private TaskByCounserlorListNet byCounserlorListNet;
    private TaskTypeShopListNet taskTypeShopListNet;
    private ShopSalesListNet shopSalesListNet;

    private List<TaskTypeShopDataBean> shopSalesListBeen=new ArrayList<>();
    private List<TaskTypeShopDataBean> typeShopListBeen=new ArrayList<>();

    private List<TaskByCounserlorListsBean> byCounserlorListBeen=new ArrayList<>();
    private List<TaskByCounserlorListsBean> byCounserlorList=new ArrayList<>();
    private List<TaskByCounserlorListsBean> byCounserlorListBeens=new ArrayList<>();
    private TaskByCounserlorListAdapter taskByCounserlorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_service_analysis_single_shop);

        initView();
        initData();
    }

    /**
     * 会员服务PACD统计(按店铺查询业绩列表)
     * @param msg
     */
    public void onEventMainThread(ResultShopSalesListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                shopSalesListBeen=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    TaskTypeShopDataBean data=new TaskTypeShopDataBean();
                    data.setShopIds(msg.TModel.get(i).getShopId());
                    data.setShopTaskSumss(msg.TModel.get(i).getShopTaskSums());
                    data.setShopSumss(msg.TModel.get(i).getShopSums());
                    shopSalesListBeen.add(data);
                }
            }
            if(isSelectDate==true){
                taskTypeShopListNet=new TaskTypeShopListNet(mContext);
                taskTypeShopListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);
            }else{
                taskTypeShopListNet=new TaskTypeShopListNet(mContext);
                taskTypeShopListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);
            }
        }
    }

    /**
     * 会员服务PACD统计(服务类别按店铺列表)
     * @param msg
     */
    public void onEventMainThread(ResultTaskTypeShopListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                typeShopListBeen=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    TaskTypeShopDataBean data=new TaskTypeShopDataBean();
                    data.setShopIds(msg.TModel.get(i).getShopId());
                    data.setServerNumss(msg.TModel.get(i).getServerNums());
                    data.setServerFinishNumss(msg.TModel.get(i).getServerFinishNums());
                    data.setGoShopNumss(msg.TModel.get(i).getGoShopNums());
                    typeShopListBeen.add(data);
                }
            }
            for(int i=0;i<shopSalesListBeen.size();i++){
                for (int j=0;j<typeShopListBeen.size();j++){
                    if(shopSalesListBeen.get(i).getShopIds()==typeShopListBeen.get(j).getShopIds()){
                        tv_shop_service_finish_sum.setText(String.valueOf(typeShopListBeen.get(j).getServerFinishNumss()));
                        tv_shop_service_sum.setText(String.valueOf(typeShopListBeen.get(j).getServerNumss()));
                        tv_go_shop.setText(String.valueOf(typeShopListBeen.get(j).getGoShopNumss()));
                        tv_shop_sums.setText("￥"+String.valueOf(shopSalesListBeen.get(i).getShopSumss()));
                        if(typeShopListBeen.get(j).getServerNumss()!=0){
                            double prs=Double.parseDouble(typeShopListBeen.get(j).getServerFinishNumss()+"")/Double.parseDouble(typeShopListBeen.get(j).getServerNumss()+"")*100;
                            pb_service.setProgress((int)prs);
                        }

                        if(shopSalesListBeen.get(i).getShopSumss()!=0){
                            double serviceSales=shopSalesListBeen.get(i).getShopTaskSumss()/shopSalesListBeen.get(i).getShopSumss()*100;
                            tv_service_sales_p.setText(DecimalFormatUtils.decimalToFormat(serviceSales)+"%");
                            double notServiceSales=100-serviceSales;
                            tv_not_service_sales_p.setText(DecimalFormatUtils.decimalToFormat(notServiceSales)+"%");
                        }else{
                            tv_service_sales_p.setText(String.valueOf(0)+"%");
                            tv_not_service_sales_p.setText(String.valueOf(100)+"%");
                        }
                    }
                }
            }

        }
    }


    /**
     * 会员服务PACD统计(服务类别图形)
     * @param msg
     */
    public void onEventMainThread(ResultTaskChartListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                chartListBean=new TaskChartListBean();
                chartListBean=msg.TModel;
                tv_server_sum.setText(String.valueOf(msg.TModel.getServerNums()));
                tv_service_finish_sum.setText(String.valueOf(msg.TModel.getServerFinishNums()));
                tv_service_vaild_sum.setText(String.valueOf(msg.TModel.getVaildServerNums()));
                tv_service_goshop_sum.setText(String.valueOf(msg.TModel.getGoShopNums()));
                tv_service_bill_sum.setText(String.valueOf(msg.TModel.getBillNums()));
                if(msg.TModel.getServerNums()!=0){
                    double finish=Double.parseDouble(String.valueOf(msg.TModel.getServerFinishNums()))/Double.parseDouble(String.valueOf(msg.TModel.getServerNums()))*100;
                    tv_member_finish_sum.setText(DecimalFormatUtils.decimalToFormat(finish)+"%");
                    double vail=Double.parseDouble(String.valueOf(msg.TModel.getVaildServerNums()))/Double.parseDouble(String.valueOf(msg.TModel.getServerNums()))*100;
                    tv_member_vaild_sum.setText(DecimalFormatUtils.decimalToFormat(vail)+"%");
                    double goShop=Double.parseDouble(String.valueOf(msg.TModel.getGoShopNums()))/Double.parseDouble(String.valueOf(msg.TModel.getServerNums()))*100;
                    tv_member_goshop_sum.setText(DecimalFormatUtils.decimalToFormat(goShop)+"%");
                    double bill=Double.parseDouble(String.valueOf(msg.TModel.getBillNums()))/Double.parseDouble(String.valueOf(msg.TModel.getServerNums()))*100;
                    tv_bill_sum.setText(DecimalFormatUtils.decimalToFormat(bill)+"%");
                }
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取会员服务统计失败"+msg.Message);
        }
    }

    /**
     * 会员服务PACD统计(按顾问服务统计分析报表)
     * @param msg
     */
    public void onEventMainThread(ResultTaskByCounserlorListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                byCounserlorListBeen=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    TaskByCounserlorListsBean data=new TaskByCounserlorListsBean();
                    data.setShopId(shopId);
                    data.setBillNums(msg.TModel.get(i).getBillNums());
                    data.setCounserlorId(msg.TModel.get(i).getCounserlorId());
                    data.setGoShopNums(msg.TModel.get(i).getGoShopNums());
                    data.setServerFinishNums(msg.TModel.get(i).getServerFinishNums());
                    data.setServerNums(msg.TModel.get(i).getServerNums());
                    data.setTaskSums(msg.TModel.get(i).getTaskSums());
                    byCounserlorListBeen.add(data);
                }

                byCounserlorListBeens=new ArrayList<>();
                for (int i=0;i<byCounserlorList.size();i++){
                    for (int j=0;j<byCounserlorListBeen.size();j++){
                        if(byCounserlorList.get(i).getCounserlorId()==byCounserlorListBeen.get(j).getCounserlorId()){
                            TaskByCounserlorListsBean data=new TaskByCounserlorListsBean();
                            data.setShopId(byCounserlorListBeen.get(j).getShopId());
                            data.setTaskSums(byCounserlorListBeen.get(j).getTaskSums());
                            data.setBillNums(byCounserlorListBeen.get(j).getBillNums());
                            data.setCounserlorId(byCounserlorListBeen.get(j).getCounserlorId());
                            data.setGoShopNums(byCounserlorListBeen.get(j).getGoShopNums());
                            data.setServerFinishNums(byCounserlorListBeen.get(j).getServerFinishNums());
                            data.setServerNums(byCounserlorListBeen.get(j).getServerNums());
                            data.setFansCount(byCounserlorList.get(i).getFansCount());
                            data.setName(byCounserlorList.get(i).getName());
                            byCounserlorListBeens.add(data);
                        }
                    }
                }
                showSwitch(switchType);
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取按顾问服务统计分析报表失败"+msg.Message);
        }
    }

    /**
     * 按店铺查询店员列表
     * @param msg
     */
    public void onEventMainThread(ResultListByShopIdBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_counserlor_sum.setText(String.valueOf( msg.TModel.size()));
                byCounserlorList=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    TaskByCounserlorListsBean data=new TaskByCounserlorListsBean();
                    data.setName(msg.TModel.get(i).getCOUNSELOR_NAME());
                    data.setFansCount(msg.TModel.get(i).getFANS_NUM());
                    data.setShopId(msg.TModel.get(i).getSHOP_ID());
                    data.setCounserlorId(msg.TModel.get(i).getCOUNSELOR_ID());
                    byCounserlorList.add(data);
                }
            }
            byCounserlorListNet=new TaskByCounserlorListNet(mContext);
            byCounserlorListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopId,serviceType);
        }else{
            ToastFactory.getLongToast(mContext,"获取店员列表失败"+msg.Message);
        }
    }

    private void initView() {
        tv_service_sales_p= (TextView) findViewById(R.id.tv_service_sales_p);
        tv_not_service_sales_p= (TextView) findViewById(R.id.tv_not_service_sales_p);
        tv_shop_service_finish_sum= (TextView) findViewById(R.id.tv_shop_service_finish_sum);
        tv_shop_service_sum= (TextView) findViewById(R.id.tv_shop_service_sum);
        tv_counserlor_sum= (TextView) findViewById(R.id.tv_counserlor_sum);
        tv_shop_sums= (TextView) findViewById(R.id.tv_shop_sums);
        tv_task= (TextView) findViewById(R.id.tv_task);
        tv_task.setText(R.string.fa_caret_down);
        tv_task.setTypeface(App.font);
        tv_complete= (TextView) findViewById(R.id.tv_complete);
        tv_complete.setText(R.string.fa_caret_down);
        tv_complete.setTypeface(App.font);
        tv_shop= (TextView) findViewById(R.id.tv_shop);
        tv_shop.setText(R.string.fa_caret_down);
        tv_shop.setTypeface(App.font);
        tv_price= (TextView) findViewById(R.id.tv_price);
        tv_price.setText(R.string.fa_caret_down);
        tv_price.setTypeface(App.font);
        tv_accounted= (TextView) findViewById(R.id.tv_accounted);
        tv_start_sel_date= (TextView) findViewById(R.id.tv_start_sel_date);
        tv_start_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00")));
        tv_end_sel_date= (TextView) findViewById(R.id.tv_end_sel_date);
        tv_end_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59")));
        tv_select_category= (TextView) findViewById(R.id.tv_select_category);
        ll_screening= (LinearLayout) findViewById(R.id.ll_screening);
        ll_select_category= (LinearLayout) findViewById(R.id.ll_select_category);
        tv_screening= (TextView) findViewById(R.id.tv_screening);
        tv_screening.setText(R.string.fa_glass);
        tv_screening.setTypeface(App.font);
        back= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("单店会员服务统计分析");
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setVisibility(View.GONE);
        mTextViewRightTitle.setText(R.string.fa_th_large);
        mTextViewRightTitle.setTypeface(App.font);
        mTextViewRightTitle.setTextSize(16);
        rv_task_counserlor_list= (RecyclerView) findViewById(R.id.rv_task_counserlor_list);
        rv_task_counserlor_list.setLayoutManager(new LinearLayoutManager(this));
        tv_server_sum= (TextView) findViewById(R.id.tv_server_sum);
        tv_service_finish_sum= (TextView) findViewById(R.id.tv_service_finish_sum);
        tv_service_vaild_sum= (TextView) findViewById(R.id.tv_service_vaild_sum);
        tv_service_goshop_sum= (TextView) findViewById(R.id.tv_service_goshop_sum);
        tv_service_bill_sum= (TextView) findViewById(R.id.tv_service_bill_sum);
        tv_member_count_sum= (TextView) findViewById(R.id.tv_member_count_sum);
        tv_member_finish_sum= (TextView) findViewById(R.id.tv_member_finish_sum);
        tv_member_vaild_sum= (TextView) findViewById(R.id.tv_member_vaild_sum);
        tv_member_goshop_sum= (TextView) findViewById(R.id.tv_member_goshop_sum);
        tv_bill_sum= (TextView) findViewById(R.id.tv_bill_sum);
        tv_go_shop= (TextView) findViewById(R.id.tv_go_shop);
        tv_shop_name= (TextView) findViewById(R.id.tv_shop_name);
        tv_org_name= (TextView) findViewById(R.id.tv_org_name);
        pb_service= (ProgressBar) findViewById(R.id.pb_service);
        ll_task= (LinearLayout) findViewById(R.id.ll_task);
        ll_task.setOnClickListener(this);
        ll_complete= (LinearLayout) findViewById(R.id.ll_complete);
        ll_complete.setOnClickListener(this);
        ll_go_shop= (LinearLayout) findViewById(R.id.ll_go_shop);
        ll_go_shop.setOnClickListener(this);
        ll_price= (LinearLayout) findViewById(R.id.ll_price);
        ll_price.setOnClickListener(this);

        mTextViewRightTitle.setOnClickListener(this);
        back.setOnClickListener(this);
        ll_select_category.setOnClickListener(this);
        ll_screening.setOnClickListener(this);
        tv_accounted.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_task:
                switchType=0;
                showSwitch(switchType);
                break;
            case R.id.ll_complete:
                switchType=1;
                showSwitch(switchType);
                break;
            case R.id.ll_go_shop:
                switchType=2;
                showSwitch(switchType);
                break;
            case R.id.ll_price:
                switchType=3;
                showSwitch(switchType);
                break;
            case R.id.tv_base_title_right:
                Intent intentShop= new Intent(mContext, QueryShopActivity.class);
                intentShop.putExtra("LeftTitle","返回");
                mActivity.startActivityForResult(intentShop,201);
                break;
            case R.id.ll_screening:
                Intent tagIntent = new Intent(mContext, DateScreeningActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
                break;
            case R.id.ll_select_category:
                showPopupWindow(ll_select_category);
                break;
            case R.id.tv_accounted:
                if(isAccounted==false){
                    isAccounted=true;
                    tv_accounted.setText("按完成比");
                    tv_accounted.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_accounted.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_purple));
                    if(chartListBean.getServerNums()!=0){
                        tv_member_finish_sum.setText(100+"%");
                        double vail=Double.parseDouble(String.valueOf(chartListBean.getVaildServerNums()))/Double.parseDouble(String.valueOf(chartListBean.getServerFinishNums()))*100;
                        tv_member_vaild_sum.setText(DecimalFormatUtils.decimalToFormat(vail)+"%");
                        double goShop=Double.parseDouble(String.valueOf(chartListBean.getGoShopNums()))/Double.parseDouble(String.valueOf(chartListBean.getServerFinishNums()))*100;
                        tv_member_goshop_sum.setText(DecimalFormatUtils.decimalToFormat(goShop)+"%");
                        double bill=Double.parseDouble(String.valueOf(chartListBean.getBillNums()))/Double.parseDouble(String.valueOf(chartListBean.getServerFinishNums()))*100;
                        tv_bill_sum.setText(DecimalFormatUtils.decimalToFormat(bill)+"%");
                    }
                }else{
                    isAccounted=false;
                    tv_accounted.setText("按总占比");
                    tv_accounted.setTextColor(mContext.getResources().getColor(R.color.defalult_text_color));
                    tv_accounted.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_ellipse_grade_purle_bg));
                    if(chartListBean.getServerNums()!=0){
                        double finish=Double.parseDouble(String.valueOf(chartListBean.getServerFinishNums()))/Double.parseDouble(String.valueOf(chartListBean.getServerNums()))*100;
                        tv_member_finish_sum.setText(DecimalFormatUtils.decimalToFormat(finish)+"%");
                        double vail=Double.parseDouble(String.valueOf(chartListBean.getVaildServerNums()))/Double.parseDouble(String.valueOf(chartListBean.getServerNums()))*100;
                        tv_member_vaild_sum.setText(DecimalFormatUtils.decimalToFormat(vail)+"%");
                        double goShop=Double.parseDouble(String.valueOf(chartListBean.getGoShopNums()))/Double.parseDouble(String.valueOf(chartListBean.getServerNums()))*100;
                        tv_member_goshop_sum.setText(DecimalFormatUtils.decimalToFormat(goShop)+"%");
                        double bill=Double.parseDouble(String.valueOf(chartListBean.getBillNums()))/Double.parseDouble(String.valueOf(chartListBean.getServerNums()))*100;
                        tv_bill_sum.setText(DecimalFormatUtils.decimalToFormat(bill)+"%");
                    }
                }

                break;
        }
    }

    public void showSwitch(int switchType){
        switch (switchType){
            case 0://任务
                Collections.sort(byCounserlorListBeens, new Comparator<TaskByCounserlorListsBean>() {
                    public int compare(TaskByCounserlorListsBean user1, TaskByCounserlorListsBean user2) {
                        Integer completion1 = (int)user1.getServerNums();
                        Integer completion2 = (int)user2.getServerNums();
                        return completion2.compareTo(completion1);
                    }
                });
                taskByCounserlorListAdapter=new TaskByCounserlorListAdapter(byCounserlorListBeens,mActivity,mContext);
                rv_task_counserlor_list.setAdapter(taskByCounserlorListAdapter);
                break;
            case 1://完成
                Collections.sort(byCounserlorListBeens, new Comparator<TaskByCounserlorListsBean>() {
                    public int compare(TaskByCounserlorListsBean user1, TaskByCounserlorListsBean user2) {
                        Integer completion1 = (int)user1.getServerFinishNums();
                        Integer completion2 = (int)user2.getServerFinishNums();
                        return completion2.compareTo(completion1);
                    }
                });
                taskByCounserlorListAdapter=new TaskByCounserlorListAdapter(byCounserlorListBeens,mActivity,mContext);
                rv_task_counserlor_list.setAdapter(taskByCounserlorListAdapter);
                break;
            case 2://到店
                Collections.sort(byCounserlorListBeens, new Comparator<TaskByCounserlorListsBean>() {
                    public int compare(TaskByCounserlorListsBean user1, TaskByCounserlorListsBean user2) {
                        Integer completion1 = (int)user1.getGoShopNums();
                        Integer completion2 = (int)user2.getGoShopNums();
                        return completion2.compareTo(completion1);
                    }
                });
                taskByCounserlorListAdapter=new TaskByCounserlorListAdapter(byCounserlorListBeens,mActivity,mContext);
                rv_task_counserlor_list.setAdapter(taskByCounserlorListAdapter);
                break;
            case 3://金额
                //按金额从大到小排序
                Collections.sort(byCounserlorListBeens, new Comparator<TaskByCounserlorListsBean>() {
                    public int compare(TaskByCounserlorListsBean user1, TaskByCounserlorListsBean user2) {
                        Integer payment1 = (int)user1.getTaskSums();
                        Integer payment2 = (int)user2.getTaskSums();
                        return payment2.compareTo(payment1);
                    }
                });
                taskByCounserlorListAdapter=new TaskByCounserlorListAdapter(byCounserlorListBeens,mActivity,mContext);
                rv_task_counserlor_list.setAdapter(taskByCounserlorListAdapter);
                break;
            default:
                break;
        }
    }

    public void initData(){
        tv_org_name.setText(prefs.getString("ShopArea"));
        tv_shop_name.setText(prefs.getString("shopName"));
        shopId=Integer.parseInt(prefs.getString("ShopId"));
        shopIds=new JSONArray();
        shopIds.put(shopId);

        chartListNet=new TaskChartListNet(mContext);
        chartListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

        shopSalesListNet=new ShopSalesListNet(mContext);
        shopSalesListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

        listByShopIdNet=new ListByShopIdNet(mContext);
        listByShopIdNet.setData(shopId);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//日期筛选
            if(data!=null){
                isSelectDate=true;
                startDate=data.getStringExtra("StartDate");
                endDate=data.getStringExtra("EndDate");
                tv_start_sel_date.setText(AbDateUtil.getDateYMDs(startDate+" 00:00:00"));
                tv_end_sel_date.setText(AbDateUtil.getDateYMDs(endDate+" 23:59:59"));

                chartListNet=new TaskChartListNet(mContext);
                chartListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);

                shopSalesListNet=new ShopSalesListNet(mContext);
                shopSalesListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);

                byCounserlorListNet=new TaskByCounserlorListNet(mContext);
                byCounserlorListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopId,serviceType);
            }
        }
    }

    private View view;
    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;
    private RecyclerView rv_category_list;
    private MemberServiceCategoryAdapter categoryAdapter;

    /**
     * 显示PopupWindow弹出菜单
     */
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.popwindow_service_category_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_blue_bg));
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(true);
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        rv_category_list= (RecyclerView) view.findViewById(R.id.rv_category_list);
        rv_category_list.setLayoutManager(new LinearLayoutManager(this));
        categoryAdapter=new MemberServiceCategoryAdapter(CategoryDataUtils.getServiceCategory(),mActivity,mContext);
        rv_category_list.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                popWindow.dismiss();
                tv_select_category.setText(CategoryDataUtils.getServiceCategory().get(position).getCategoryName());
                serviceType=CategoryDataUtils.getServiceCategory().get(position).getType();

                chartListNet=new TaskChartListNet(mContext);
                chartListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);
            }
        });
    }
}
