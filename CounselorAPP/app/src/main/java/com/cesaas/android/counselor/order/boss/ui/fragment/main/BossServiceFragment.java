package com.cesaas.android.counselor.order.boss.ui.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.boss.adapter.member.MemberServiceCategoryAdapter;
import com.cesaas.android.counselor.order.boss.adapter.member.TaskTypeListAdapter;
import com.cesaas.android.counselor.order.boss.adapter.member.TaskTypeShopListAdapter;
import com.cesaas.android.counselor.order.boss.bean.ResultShopListBean;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.bean.member.CategoryDataUtils;
import com.cesaas.android.counselor.order.boss.bean.member.ResultShopSalesListBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultTaskChartListBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultTaskTypeListBean;
import com.cesaas.android.counselor.order.boss.bean.member.ResultTaskTypeShopListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskChartListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeListBean;
import com.cesaas.android.counselor.order.boss.bean.member.TaskTypeShopDataBean;
import com.cesaas.android.counselor.order.boss.net.ShopListNet;
import com.cesaas.android.counselor.order.boss.net.ShopSalesListNet;
import com.cesaas.android.counselor.order.boss.net.TaskChartListNet;
import com.cesaas.android.counselor.order.boss.net.TaskTypeListNet;
import com.cesaas.android.counselor.order.boss.net.TaskTypeShopListNet;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryShopActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.member.DateScreeningActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.member.ServiceAnalysisDetailsActivity;
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

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/4/13 16:04
 * Version 1.0
 */

public class BossServiceFragment extends BaseFragment{
    private LinearLayout ll_base_title_back;
    private TextView tvTitle,mTextViewRightTitle;
    private TextView tv_screening,tv_select_category;
    private TextView tv_start_sel_date,tv_end_sel_date,tv_accounted;
    private TextView tv_server_sum,tv_service_finish_sum,tv_service_vaild_sum,tv_service_goshop_sum,tv_service_bill_sum,tv_sales;
    private TextView tv_member_count_sum,tv_member_finish_sum,tv_member_vaild_sum,tv_member_goshop_sum,tv_bill_sum;
    private LinearLayout back,ll_select_category,ll_screening;
    private RecyclerView rv_shop_list,rv_task_category_list;

    private TaskTypeShopListAdapter taskTypeShopListAdapter;
    private TaskTypeListAdapter typeListAdapter;
    private List<TaskTypeListBean> taskTypeListBeen=new ArrayList<>();

    private List<TaskTypeShopDataBean> shopSalesListBeen=new ArrayList<>();
    private List<TaskTypeShopDataBean> typeShopListBeen=new ArrayList<>();
    private List<TaskTypeShopDataBean> shopListBeen=new ArrayList<>();
    private List<TaskTypeShopDataBean> taskTypeShopDataBeen=new ArrayList<>();

    private TaskChartListNet chartListNet;
    private TaskTypeShopListNet taskTypeShopListNet;
    private TaskTypeListNet taskTypeListNet;
    private ShopSalesListNet shopSalesListNet;
    private ShopListNet shopListNet;

    private boolean isSales=false;
    private boolean isAccounted=false;
    private int serviceType=0;
    private JSONArray shopIds=new JSONArray();
    private TaskChartListBean chartListBean=new TaskChartListBean();

    /**
     * 单例
     */
    public static BossServiceFragment newInstance() {
        BossServiceFragment fragmentCommon = new BossServiceFragment();
        return fragmentCommon;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_boss_service;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        ll_base_title_back=findView(R.id.ll_base_title_back);
        ll_base_title_back.setVisibility(View.GONE);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员服务");
        tv_accounted=findView(R.id.tv_accounted);
        tv_sales=findView(R.id.tv_sales);
        tv_start_sel_date=findView(R.id.tv_start_sel_date);
        tv_start_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00")));
        tv_end_sel_date=findView(R.id.tv_end_sel_date);
        tv_end_sel_date.setText(AbDateUtil.getDateYMDs(AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59")));
        tv_select_category=findView(R.id.tv_select_category);
        ll_screening=findView(R.id.ll_screening);
        ll_select_category=findView(R.id.ll_select_category);
        tv_screening=findView(R.id.tv_screening);
        tv_screening.setText(R.string.fa_glass);
        tv_screening.setTypeface(App.font);
        back=findView(R.id.ll_base_title_back);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员服务统计分析");
        mTextViewRightTitle=findView(R.id.tv_base_title_right);
        mTextViewRightTitle.setVisibility(View.VISIBLE);
        mTextViewRightTitle.setText(R.string.fa_th_large);
        mTextViewRightTitle.setTypeface(App.font);
        mTextViewRightTitle.setTextSize(16);
        rv_shop_list=findView(R.id.rv_shop_list);
        rv_task_category_list=findView(R.id.rv_task_category_list);
        rv_shop_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_task_category_list.setLayoutManager(new LinearLayoutManager(getContext()));
        tv_server_sum=findView(R.id.tv_server_sum);
        tv_service_finish_sum=findView(R.id.tv_service_finish_sum);
        tv_service_vaild_sum=findView(R.id.tv_service_vaild_sum);
        tv_service_goshop_sum=findView(R.id.tv_service_goshop_sum);
        tv_service_bill_sum=findView(R.id.tv_service_bill_sum);
        tv_member_count_sum=findView(R.id.tv_member_count_sum);
        tv_member_finish_sum=findView(R.id.tv_member_finish_sum);
        tv_member_vaild_sum=findView(R.id.tv_member_vaild_sum);
        tv_member_goshop_sum= findView(R.id.tv_member_goshop_sum);
        tv_bill_sum=findView(R.id.tv_bill_sum);

    }

    @Override
    public void initListener() {
        mTextViewRightTitle.setOnClickListener(this);
        back.setOnClickListener(this);
        ll_select_category.setOnClickListener(this);
        ll_screening.setOnClickListener(this);
        tv_sales.setOnClickListener(this);
        tv_accounted.setOnClickListener(this);
    }

    @Override
    public void initData() {
        shopListNet=new ShopListNet(getContext());
        shopListNet.setData();
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
            ToastFactory.getLongToast(getContext(),"获取会员服务统计失败"+msg.Message);
        }
    }

    /**
     * 接收店铺列表数据
     * @param msg
     */
    public void onEventMainThread(ResultShopListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                shopIds=new JSONArray();
                shopListBeen=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    TaskTypeShopDataBean data=new TaskTypeShopDataBean();
                    shopIds.put(msg.TModel.get(i).getShopId());

                    data.setShopIds(msg.TModel.get(i).getShopId());
                    data.setShopNames(msg.TModel.get(i).getShopName());
                    data.setOrgNames(msg.TModel.get(i).getOrganizationName());
                    shopListBeen.add(data);
                }
            }
            chartListNet=new TaskChartListNet(getContext());
            chartListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

            taskTypeListNet=new TaskTypeListNet(getContext());
            taskTypeListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

            taskTypeShopListNet=new TaskTypeShopListNet(getContext());
            taskTypeShopListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

            shopSalesListNet=new ShopSalesListNet(getContext());
            shopSalesListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);
        }else{
            ToastFactory.getLongToast(getContext(),"获取店铺数据失败:"+msg.Message);
        }
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

                taskTypeShopDataBeen=new ArrayList<>();
                for (int i=0;i<shopListBeen.size();i++){
                    if(shopSalesListBeen.size() >= typeShopListBeen.size()){
                        for (int j=0;j<shopSalesListBeen.size();j++){
                            for (int k=0;k<typeShopListBeen.size();k++){
                                if(shopListBeen.get(i).getShopIds()==shopSalesListBeen.get(j).getShopIds() && shopListBeen.get(i).getShopIds()==typeShopListBeen.get(k).getShopIds()){
                                    TaskTypeShopDataBean shopDataBean=new TaskTypeShopDataBean();
                                    shopDataBean.setShopIds(shopListBeen.get(i).getShopIds());
                                    shopDataBean.setShopNames(shopListBeen.get(i).getShopNames());
                                    shopDataBean.setOrgNames(shopListBeen.get(i).getOrgNames());
                                    shopDataBean.setServerNumss(typeShopListBeen.get(k).getServerNumss());
                                    shopDataBean.setServerFinishNumss(typeShopListBeen.get(k).getServerFinishNumss());
                                    shopDataBean.setGoShopNumss(typeShopListBeen.get(k).getGoShopNumss());
                                    shopDataBean.setShopTaskSumss(shopSalesListBeen.get(j).getShopTaskSumss());
                                    shopDataBean.setShopSumss(shopSalesListBeen.get(j).getShopSumss());
                                    taskTypeShopDataBeen.add(shopDataBean);
                                }
                            }
                        }
                    }else if(shopSalesListBeen.size() <= typeShopListBeen.size()){
                        for (int j=0;j<typeShopListBeen.size();j++){
                            for (int k=0;k<shopSalesListBeen.size();k++){
                                if(shopListBeen.get(i).getShopIds()==shopSalesListBeen.get(j).getShopIds() && shopListBeen.get(i).getShopIds()==typeShopListBeen.get(k).getShopIds()){
                                    TaskTypeShopDataBean shopDataBean=new TaskTypeShopDataBean();
                                    shopDataBean.setShopIds(shopListBeen.get(i).getShopIds());
                                    shopDataBean.setShopNames(shopListBeen.get(i).getShopNames());
                                    shopDataBean.setOrgNames(shopListBeen.get(i).getOrgNames());
                                    shopDataBean.setServerNumss(typeShopListBeen.get(j).getServerNumss());
                                    shopDataBean.setServerFinishNumss(typeShopListBeen.get(j).getServerFinishNumss());
                                    shopDataBean.setGoShopNumss(typeShopListBeen.get(j).getGoShopNumss());
                                    shopDataBean.setShopTaskSumss(shopSalesListBeen.get(k).getShopTaskSumss());
                                    shopDataBean.setShopSumss(shopSalesListBeen.get(k).getShopSumss());
                                    taskTypeShopDataBeen.add(shopDataBean);
                                }
                            }
                        }
                    }else{
                        taskTypeShopDataBeen.addAll(shopListBeen);
                    }
                }
                Collections.sort(taskTypeShopDataBeen, new Comparator<TaskTypeShopDataBean>() {
                    public int compare(TaskTypeShopDataBean user1, TaskTypeShopDataBean user2) {
                        Integer completion1 = (int)user1.getServerFinishNumss();
                        Integer completion2 = (int)user2.getServerFinishNumss();
                        return completion2.compareTo(completion1);
                    }
                });
                taskTypeShopListAdapter=new TaskTypeShopListAdapter(taskTypeShopDataBeen,shopSalesListBeen,getActivity(),getContext());
                rv_shop_list.setAdapter(taskTypeShopListAdapter);

                taskTypeShopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        bundle.putInt("shopId",taskTypeShopDataBeen.get(position).getShopIds());
                        bundle.putInt("ServerNums",taskTypeShopDataBeen.get(position).getServerNumss());
                        bundle.putInt("ServerFinishNums",taskTypeShopDataBeen.get(position).getServerFinishNumss());
                        bundle.putInt("GoShopNums",taskTypeShopDataBeen.get(position).getGoShopNumss());
                        bundle.putString("ShopName",taskTypeShopDataBeen.get(position).getShopNames());
                        bundle.putString("OrgName",taskTypeShopDataBeen.get(position).getOrgNames());
                        bundle.putDouble("ShopTaskSums",taskTypeShopDataBeen.get(position).getShopTaskSumss());
                        bundle.putDouble("ShopSums",taskTypeShopDataBeen.get(position).getShopSumss());
                        Skip.mNextFroData(getActivity(),ServiceAnalysisDetailsActivity.class,bundle);
                    }
                });
            }
        }else{
            ToastFactory.getLongToast(getContext(),"获取按店铺查询业绩列表失败"+msg.Message);
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
        }else{
            ToastFactory.getLongToast(getContext(),"获取服务类别按店铺列表失败"+msg.Message);
        }
    }

    /**
     * 会员服务PACD统计(服务类别列表)
     * @param msg
     */
    public void onEventMainThread(ResultTaskTypeListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                taskTypeListBeen=new ArrayList<>();
                taskTypeListBeen.addAll(msg.TModel);
                typeListAdapter=new TaskTypeListAdapter(taskTypeListBeen,getActivity(),getContext());
                rv_task_category_list.setAdapter(typeListAdapter);
            }
        }else{
            ToastFactory.getLongToast(getContext(),"获取服务类别列表失败"+msg.Message);
        }
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.tv_base_title_right:
                Intent intentShop= new Intent(getContext(), QueryShopActivity.class);
                intentShop.putExtra("LeftTitle",tvTitle.getText().toString());
                startActivityForResult(intentShop,201);
                break;
            case R.id.ll_screening:
                Intent tagIntent = new Intent(getContext(), DateScreeningActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
                break;
            case R.id.ll_select_category:
                showPopupWindow(ll_select_category);
                break;
            case R.id.tv_sales:
                if(isSales==false){
                    isSales=true;
                    tv_sales.setTextColor(getContext().getResources().getColor(R.color.white));
                    tv_sales.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_purple));

                    Collections.sort(taskTypeShopDataBeen, new Comparator<TaskTypeShopDataBean>() {
                        public int compare(TaskTypeShopDataBean user1, TaskTypeShopDataBean user2) {
                            Integer payment1 = (int)user1.getShopSumss();
                            Integer payment2 = (int)user2.getShopSumss();
                            return payment2.compareTo(payment1);
                        }
                    });
                    taskTypeShopListAdapter=new TaskTypeShopListAdapter(taskTypeShopDataBeen,shopSalesListBeen,getActivity(),getContext());
                    rv_shop_list.setAdapter(taskTypeShopListAdapter);
                    taskTypeShopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            bundle.putInt("shopId",taskTypeShopDataBeen.get(position).getShopIds());
                            bundle.putInt("ServerNums",taskTypeShopDataBeen.get(position).getServerNumss());
                            bundle.putInt("ServerFinishNums",taskTypeShopDataBeen.get(position).getServerFinishNumss());
                            bundle.putInt("GoShopNums",taskTypeShopDataBeen.get(position).getGoShopNumss());
                            bundle.putString("ShopName",taskTypeShopDataBeen.get(position).getShopNames());
                            bundle.putString("OrgName",taskTypeShopDataBeen.get(position).getOrgNames());
                            bundle.putDouble("ShopTaskSums",taskTypeShopDataBeen.get(position).getShopTaskSumss());
                            bundle.putDouble("ShopSums",taskTypeShopDataBeen.get(position).getShopSumss());
                            Skip.mNextFroData(getActivity(),ServiceAnalysisDetailsActivity.class,bundle);
                        }
                    });
                }else{
                    isSales=false;
                    tv_sales.setTextColor(getContext().getResources().getColor(R.color.defalult_text_color));
                    tv_sales.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_ellipse_grade_purle_bg));

                    Collections.sort(taskTypeShopDataBeen, new Comparator<TaskTypeShopDataBean>() {
                        public int compare(TaskTypeShopDataBean user1, TaskTypeShopDataBean user2) {
                            Integer completion1 = (int)user1.getServerFinishNumss();
                            Integer completion2 = (int)user2.getServerFinishNumss();
                            return completion2.compareTo(completion1);
                        }
                    });
                    taskTypeShopListAdapter=new TaskTypeShopListAdapter(taskTypeShopDataBeen,shopSalesListBeen,getActivity(),getContext());
                    rv_shop_list.setAdapter(taskTypeShopListAdapter);
                    taskTypeShopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            bundle.putInt("shopId",taskTypeShopDataBeen.get(position).getShopIds());
                            bundle.putInt("ServerNums",taskTypeShopDataBeen.get(position).getServerNumss());
                            bundle.putInt("ServerFinishNums",taskTypeShopDataBeen.get(position).getServerFinishNumss());
                            bundle.putInt("GoShopNums",taskTypeShopDataBeen.get(position).getGoShopNumss());
                            bundle.putString("ShopName",taskTypeShopDataBeen.get(position).getShopNames());
                            bundle.putString("OrgName",taskTypeShopDataBeen.get(position).getOrgNames());
                            bundle.putDouble("ShopTaskSums",taskTypeShopDataBeen.get(position).getShopTaskSumss());
                            bundle.putDouble("ShopSums",taskTypeShopDataBeen.get(position).getShopSumss());
                            Skip.mNextFroData(getActivity(),ServiceAnalysisDetailsActivity.class,bundle);
                        }
                    });
                }
                break;
            case R.id.tv_accounted:
                if(isAccounted==false){
                    isAccounted=true;
                    tv_accounted.setText("按完成比");
                    tv_accounted.setTextColor(getContext().getResources().getColor(R.color.white));
                    tv_accounted.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_purple));
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
                    tv_accounted.setTextColor(getContext().getResources().getColor(R.color.defalult_text_color));
                    tv_accounted.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_ellipse_grade_purle_bg));
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

    @Override
    public void fetchData() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//标签筛选
            if(data!=null){
                String startDate=data.getStringExtra("StartDate");
                String endDate=data.getStringExtra("EndDate");
                tv_start_sel_date.setText(AbDateUtil.getDateYMDs(startDate+" 00:00:00"));
                tv_end_sel_date.setText(AbDateUtil.getDateYMDs(endDate+" 23:59:59"));

                chartListNet=new TaskChartListNet(getContext());
                chartListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);

                taskTypeShopListNet=new TaskTypeShopListNet(getContext());
                taskTypeShopListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);

                shopSalesListNet=new ShopSalesListNet(getContext());
                shopSalesListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);

                taskTypeShopListNet=new TaskTypeShopListNet(getContext());
                taskTypeShopListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);

                taskTypeListNet=new TaskTypeListNet(getContext());
                taskTypeListNet.setData(startDate+" 00:00:00",endDate+" 23:59:59",shopIds,serviceType);
            }
        }
        if(requestCode==201){
            if(data!=null){
                List<SelectShopListBean> shopListBeanList=(ArrayList<SelectShopListBean>)data.getSerializableExtra("selectList");
                shopIds=new JSONArray();
                for (int i=0;i<shopListBeanList.size();i++){
                    shopIds.put(shopListBeanList.get(i).getShopId());
                }
                chartListNet=new TaskChartListNet(getContext());
                chartListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

                shopSalesListNet=new ShopSalesListNet(getContext());
                shopSalesListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

                taskTypeShopListNet=new TaskTypeShopListNet(getContext());
                taskTypeShopListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

                taskTypeListNet=new TaskTypeListNet(getContext());
                taskTypeListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);
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
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.popwindow_service_category_layout, null);
            dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
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
        rv_category_list.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter=new MemberServiceCategoryAdapter(CategoryDataUtils.getServiceCategory(),getActivity(),getContext());
        rv_category_list.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                popWindow.dismiss();
                tv_select_category.setText(CategoryDataUtils.getServiceCategory().get(position).getCategoryName());
                serviceType=CategoryDataUtils.getServiceCategory().get(position).getType();

                chartListNet=new TaskChartListNet(getContext());
                chartListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

                shopSalesListNet=new ShopSalesListNet(getContext());
                shopSalesListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

                taskTypeShopListNet=new TaskTypeShopListNet(getContext());
                taskTypeShopListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);

                taskTypeListNet=new TaskTypeListNet(getContext());
                taskTypeListNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"),shopIds,serviceType);
            }
        });
    }
}
