package com.cesaas.android.counselor.order.activity.main.fragment.newmain;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.UserCentreActivity;
import com.cesaas.android.counselor.order.activity.main.adapter.MainOrderAdapter;
import com.cesaas.android.counselor.order.activity.main.adapter.MainTaskListAdapter;
import com.cesaas.android.counselor.order.activity.user.AppSettingActivity;
import com.cesaas.android.counselor.order.activity.user.bean.AppSettingEventBusBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultSalerSaleCompareBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultShopSaleCompareBean;
import com.cesaas.android.counselor.order.activity.user.net.GetDayTotalNet;
import com.cesaas.android.counselor.order.activity.user.net.SalerSaleCompareNet;
import com.cesaas.android.counselor.order.activity.user.net.ShopSaleCompareNet;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.bean.ResultGetVipAddNumBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.custom.progress.CustomCircleProgressBar;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.adapter.member.FaceGalleryRecyclerAdapter;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultFaceListBean;
import com.cesaas.android.counselor.order.member.net.service.FaceListNet;
import com.cesaas.android.counselor.order.member.service.FaceListActivity;
import com.cesaas.android.counselor.order.net.GetVipAddNumNet;
import com.cesaas.android.counselor.order.net.NewGetDayTotalNet;
import com.cesaas.android.counselor.order.report.bean.ResultCounselorSaleBean;
import com.cesaas.android.counselor.order.report.bean.ResultDayTotalBean;
import com.cesaas.android.counselor.order.report.net.CounselorSaleNet;
import com.cesaas.android.counselor.order.report.net.ResultGetDayTotalBean;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.net.TaskListNet;
import com.cesaas.android.counselor.order.shoptask.view.HandleTaskActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.SortUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.review.ResultGetRatecontentAppBean;
import com.cesaas.android.java.net.review.GetRatecontentAppNet;
import com.cesaas.android.java.ui.activity.review.ReviewActivity;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.cesaas.android.order.bean.ResultUnReceiveOrderBean;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.cesaas.android.order.net.UnReceiveOrderNet;
import com.cesaas.android.order.ui.activity.ReceiveOrderDetailActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;


/**
 * Author FGB
 * Description
 * Created at 2018/1/23 14:56
 * Version 1.0
 */

public class NewHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    TextView tv_before_day,tv_week,tv_month,tv_get_week,tv_month_day;
    TextView tv_user_name,tv_shop_name,tv_user_level,tv_not_visiting_data;
    TextView tv_not_task_data,tv_not_order_data,tv_task_left,tv_task_right,tv_order_left,tv_order_right,tv_order_refresh,tv_user_refresh;
    TextView tv_vip_compared,tv_vip_sequential,tv_current_data,tv_current_shop_sales,tv_current_sales;
    TextView tv_relative,tv_shop_compared_progress,tv_shop_sequential_progress,tv_saler_compared_progress,tv_salersequential_progress;
    TextView tv_score,tv_score2,tv_score3,tv_score4,tv_score5,tv_start_value;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout ll_sales_data,ll_task_data,ll_visit_data,ll_order_data,ll_face_list,ll_review;
    private TextView tv_shop_goal,tv_counselor_goal,tv_TodayCounselorGoal,tv_CounselorGoal,tv_vip_cart_number,tv_vip_number;
    private RecyclerView rv_task_list;
    private RecyclerView rv_order_list;
    private RecyclerView rv_visiting_list;

    private CircleImageView ivw_user_icon;
    private CustomCircleProgressBar am_progressbar_shop;
    private CustomCircleProgressBar am_progressbar_personal;
    private CustomCircleProgressBar am_progressbar_members;

    private int pageIndex=1;
    private int pageSize=50;

    private double shopProgress=0;
    private double counselorProgress=0;
    private double vipProgress=0;
    private int comparedProgress;
    private int sequentialProgress;
    private boolean isRelative=false;

    private double shopSalesAmount;//店铺销售金额
    private double counselorSalesAmount;//个人销售金额
    private int newVipCount;//新增会员

    private double ShopComparedProgress;//店铺同比
    private double ShopSequentialProgress;//店铺环比

    private double SalerComparedProgress;//个人同比
    private double SalerSequentialProgress;//个人环比

    //绝对达成目标
    private int Card;
    private double CounselorGoal;
    private double ShopGoal;
    //相对达成目标
    private int TodayCard;
    private double TodayCounselorGoal;
    private double TodayShopGoal;

    private ArrayList<TextView> tvs=new ArrayList<TextView>();
    private TaskListNet taskListNet;
    private NewGetDayTotalNet dayTotalNet;
    private GetVipAddNumNet getVipAddNumNet;
    private UnReceiveOrderNet unReceiveOrderNet;
    private ShopSaleCompareNet shopSaleCompareNet;
    private SalerSaleCompareNet salerSaleCompareNet;
    private CounselorSaleNet counselorSaleNet;
    private GetDayTotalNet getDayTotalNet;
    private FaceListNet net;
    private GetRatecontentAppNet getRatecontentAppNet;

    private FaceGalleryRecyclerAdapter faceGalleryRecyclerAdapter;
    private List<FaceListBean> faceListBeen;
    private MainOrderAdapter orderAdapter;
    private List<UnReceiveOrderBean> orderList;
    private MainTaskListAdapter taskListAdapter;
    private List<ShopTaskListBean> taskList=new ArrayList<>();
    private UserBean userBean=new UserBean();

    /**
     * 单例
     */
    public static NewHomeFragment newInstance() {
        NewHomeFragment fragmentCommon = new NewHomeFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_home;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        tv_not_visiting_data=findView(R.id.tv_not_visiting_data);
        ll_review=findView(R.id.ll_review);
        ll_face_list=findView(R.id.ll_face_list);
        ll_sales_data=findView(R.id.ll_sales_data);
        ll_task_data=findView(R.id.ll_task_data);
        ll_visit_data=findView(R.id.ll_visit_data);
        ll_order_data=findView(R.id.ll_order_data);
        tv_current_sales=findView(R.id.tv_current_sales);
        tv_current_shop_sales=findView(R.id.tv_current_shop_sales);
        tv_current_data=findView(R.id.tv_current_data);
        tv_salersequential_progress=findView(R.id.tv_salersequential_progress);
        tv_saler_compared_progress=findView(R.id.tv_saler_compared_progress);
        tv_shop_sequential_progress=findView(R.id.tv_shop_sequential_progress);
        tv_shop_compared_progress=findView(R.id.tv_shop_compared_progress);
        tv_CounselorGoal=findView(R.id.tv_CounselorGoal);
        tv_TodayCounselorGoal=findView(R.id.tv_TodayCounselorGoal);
        tv_task_left=findView(R.id.tv_task_left);
        tv_task_left.setText(R.string.fa_caret_left);
        tv_task_left.setTypeface(App.font);
        tv_task_right=findView(R.id.tv_task_right);
        tv_task_right.setText(R.string.fa_caret_right);
        tv_task_right.setTypeface(App.font);
        tv_order_left=findView(R.id.tv_order_left);
        tv_order_left.setText(R.string.fa_caret_left);
        tv_order_left.setTypeface(App.font);
        tv_order_right=findView(R.id.tv_order_right);
        tv_order_right.setText(R.string.fa_caret_right);
        tv_order_right.setTypeface(App.font);
        tv_order_refresh=findView(R.id.tv_order_refresh);
        tv_order_refresh.setText(R.string.fa_refresh);
        tv_order_refresh.setTypeface(App.font);
        tv_user_refresh=findView(R.id.tv_user_refresh);
        tv_user_refresh.setText(R.string.fa_refresh);
        tv_user_refresh.setTypeface(App.font);

        tv_start_value=findView(R.id.tv_start_value);
        tv_score=findView(R.id.tv_score);
        tv_score.setText(R.string.fa_star_o);
        tv_score.setTypeface(App.font);
        tv_score2=findView(R.id.tv_score2);
        tv_score2.setText(R.string.fa_star_o);
        tv_score2.setTypeface(App.font);
        tv_score3=findView(R.id.tv_score3);
        tv_score3.setText(R.string.fa_star_o);
        tv_score3.setTypeface(App.font);
        tv_score4=findView(R.id.tv_score4);
        tv_score4.setText(R.string.fa_star_o);
        tv_score4.setTypeface(App.font);
        tv_score5=findView(R.id.tv_score5);
        tv_score5.setText(R.string.fa_star_o);
        tv_score5.setTypeface(App.font);

        tv_vip_compared=findView(R.id.tv_vip_compared);
        tv_vip_sequential=findView(R.id.tv_vip_sequential);
        tv_relative=findView(R.id.tv_relative);

        tv_not_task_data=findView(R.id.tv_not_task_data);
        tv_not_order_data=findView(R.id.tv_not_order_data);
        tv_before_day=findView(R.id.tv_before_day);
        tv_week=findView(R.id.tv_week);
        tv_get_week=findView(R.id.tv_get_week);
        tv_month=findView(R.id.tv_month);
        tv_month_day=findView(R.id.tv_month_day);
        tvs.add(tv_before_day);
        tvs.add(tv_week);
        tvs.add(tv_month);
        tv_user_name=findView(R.id.tv_user_name);
        tv_shop_name=findView(R.id.tv_shop_name);
        tv_user_level=findView(R.id.tv_user_level);
        ivw_user_icon=findView(R.id.ivw_user_icon);
        am_progressbar_shop=findView(R.id.am_progressbar_shop);
        am_progressbar_personal=findView(R.id.am_progressbar_personal);
        am_progressbar_members=findView(R.id.am_progressbar_members);
        tv_shop_goal=findView(R.id.tv_shop_goal);
        tv_counselor_goal=findView(R.id.tv_counselor_goal);
        tv_vip_cart_number=findView(R.id.tv_vip_cart_number);
        tv_vip_number=findView(R.id.tv_vip_number);
        rv_task_list=findView(R.id.rv_task_list);
        rv_task_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order_list=findView(R.id.rv_order_list);
        rv_order_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_visiting_list=findView(R.id.rv_visiting_list);
        //固定高度
        rv_visiting_list.setHasFixedSize(true);
        //创建布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        //设置横向
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置布局管理器
        rv_visiting_list.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initListener() {
        tv_before_day.setOnClickListener(this);
        tv_week.setOnClickListener(this);
        tv_month.setOnClickListener(this);
        //相对达成
        tv_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRelative();
            }
        });

        //刷新订单
        tv_order_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unReceiveOrderNet=new UnReceiveOrderNet(getContext());
                unReceiveOrderNet.setData(pageIndex, SortUtils.setSort());
            }
        });
        ll_face_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mNext(getActivity(), FaceListActivity.class);
            }
        });
        ivw_user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mNext(getActivity(),UserCentreActivity.class);
            }
        });
        ll_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putSerializable("userBean",userBean);
                Skip.mNextFroData(getActivity(), ReviewActivity.class,bundle);
            }
        });
    }

    private void isRelative(){
        if(isRelative==false){
            isRelative=true;
            tv_relative.setText("绝对达成");
            tv_relative.setTextColor(getResources().getColor(R.color.base_colors));
            //店铺相对百分比
            shopProgress=shopSalesAmount/TodayShopGoal*100;
            if(shopProgress!=0 && !Double.isNaN(shopProgress)){
                am_progressbar_shop.setProgress((int)shopProgress);
            }else{
                am_progressbar_shop.setProgress(100);
            }
            //个人相对百分比
            counselorProgress=counselorSalesAmount/TodayCounselorGoal*100;
            if(counselorProgress!=0 && !Double.isNaN(counselorProgress)){
                am_progressbar_personal.setProgress((int)counselorProgress);
            }else{
                am_progressbar_personal.setProgress(100);
            }
            //会员相对百分比
            vipProgress=(double)newVipCount/(double)TodayCard*100;
            if(vipProgress!=0 && !Double.isNaN(vipProgress)){
                am_progressbar_members.setProgress((int)vipProgress);
            }else{
                am_progressbar_members.setProgress(100);
            }
        }else{
            isRelative=false;
            tv_relative.setText("相对达成");
            tv_relative.setTextColor(getResources().getColor(R.color.new_base_bg));
            //店铺绝对百分比
            shopProgress=shopSalesAmount/ShopGoal*100;
            if(shopProgress!=0 && !Double.isNaN(shopProgress)){
                am_progressbar_shop.setProgress((int)shopProgress);
            }else{
                am_progressbar_shop.setProgress(100);
            }
            //个人绝对百分比
            counselorProgress=counselorSalesAmount/CounselorGoal*100;
            if(counselorProgress!=0 && !Double.isNaN(counselorProgress)){
                am_progressbar_personal.setProgress((int)counselorProgress);
            }else{
                am_progressbar_personal.setProgress(100);
            }
            //会员绝对百分比
            vipProgress=(double)newVipCount/(double)Card*100;
            if(vipProgress!=0 && !Double.isNaN(vipProgress)){
                am_progressbar_members.setProgress((int)vipProgress);
            }else{
                am_progressbar_members.setProgress(100);
            }
        }
    }

    /**
     * 接收人脸抓拍列表结果
     * @param msg
     */
    public void onEventMainThread(ResultFaceListBean msg) {
        if (msg.IsSuccess!=false) {
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_visiting_data.setVisibility(View.GONE);
                faceListBeen=new ArrayList<>();
                faceListBeen.addAll(msg.TModel);
                faceGalleryRecyclerAdapter=new FaceGalleryRecyclerAdapter(getContext(),faceListBeen,getContext());
                rv_visiting_list.setAdapter(faceGalleryRecyclerAdapter);
                faceGalleryRecyclerAdapter.setOnRecyclerViewItemClickListener(new FaceGalleryRecyclerAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                });
            }else{
                tv_not_visiting_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_visiting_data.setVisibility(View.VISIBLE);
        }
    }

    public void onEventMainThread(AppSettingEventBusBean lbean) {
        Intent tagIntent = new Intent(getContext(), AppSettingActivity.class);
        startActivityForResult(tagIntent, 1022);
    }

    public void onEventMainThread(ResultUserBean lbean) {
        if(lbean.IsSuccess!=false && lbean.TModel!=null){
            userBean=lbean.TModel;
            tv_user_name.setText(lbean.TModel.NickName);
            tv_shop_name.setText(lbean.TModel.ShopName);
            tv_user_level.setText(lbean.TModel.TypeName);
            if(lbean.TModel.Icon!=null && !"".equals(lbean.TModel.Icon)){
                Glide.with(getContext()).load(lbean.TModel.Icon).into(ivw_user_icon);
            }else{
                ivw_user_icon.setImageResource(R.mipmap.not_user_icon);
            }
            getRatecontentAppNet=new GetRatecontentAppNet(getContext());
            getRatecontentAppNet.setData(pageIndex, FilterConditionDateUtils.getFilterConditionDateAndId(AbDateUtil.getBeginDayOfYear(),AbDateUtil.getEndDayOfYear(),Integer.parseInt(userBean.CounselorId)));
        }
    }

    /**
     * 接收查看导购评价
     * @param
     */
    public void onEventMainThread(ResultGetRatecontentAppBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(getContext(),msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                if (msg.arguments.resp != null && !"".equals(msg.arguments.resp)) {
                    tv_start_value.setText(String.valueOf(Double.parseDouble(msg.arguments.resp.getAvgTotalValue() + "")));
                    switch (msg.arguments.resp.getAvgTotalValue()) {
                        case 1:
                            tv_score.setText(R.string.fa_star);
                            break;
                        case 2:
                            tv_score.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            break;
                        case 3:
                            tv_score.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            tv_score3.setText(R.string.fa_star);
                            break;
                        case 4:
                            tv_score.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            tv_score3.setText(R.string.fa_star);
                            tv_score4.setText(R.string.fa_star);
                            break;
                        case 5:
                            tv_score.setText(R.string.fa_star);
                            tv_score2.setText(R.string.fa_star);
                            tv_score3.setText(R.string.fa_star);
                            tv_score4.setText(R.string.fa_star);
                            tv_score5.setText(R.string.fa_star);
                            break;
                    }
                }
            } else {
                ToastFactory.getLongToast(getContext(), msg.arguments.resp.errorMessage);
            }
        }
    }

    @Override
    public void initData() {
        tv_month_day.setText(AbDateUtil.toDateMDs(AbDateUtil.getCurrentTime()));
        tv_get_week.setText(AbDateUtil.getWeek());

        am_progressbar_personal.setProgress(100);

        getDayTotalNet=new GetDayTotalNet(getContext());
        getDayTotalNet.setData();
        counselorSaleNet=new CounselorSaleNet(getContext());
        counselorSaleNet.setData();

        dayTotalNet=new NewGetDayTotalNet(getContext());
        dayTotalNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        shopSaleCompareNet=new ShopSaleCompareNet(getContext());
        shopSaleCompareNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        salerSaleCompareNet=new SalerSaleCompareNet(getContext());
        salerSaleCompareNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
        getVipAddNumNet=new GetVipAddNumNet(getContext());
        getVipAddNumNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));

        taskListNet =new TaskListNet(getContext());
        taskListNet.setData(pageIndex,3,0);

        unReceiveOrderNet=new UnReceiveOrderNet(getContext());
        unReceiveOrderNet.setData(pageIndex, SortUtils.setSort());

        net=new FaceListNet(getContext());
        net.setData(pageIndex, AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));

    }

    /**
     * 接收待接单数据
     * @param msg
     */
    public void onEventMainThread(ResultUnReceiveOrderBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_order_data.setVisibility(View.GONE);
                orderList=new ArrayList<>();
                orderList.addAll(msg.TModel);
                orderAdapter=new MainOrderAdapter(orderList);
                rv_order_list.setAdapter(orderAdapter);
                rv_order_list.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("OrderType",100);
                        bundle.putInt("TradeId", orderList.get(position).getOrderId());
                        Skip.mNextFroData(getActivity(), ReceiveOrderDetailActivity.class, bundle);
                    }
                });
            }else{
                tv_not_order_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_order_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    /**
     * 接收会员新增数数据
     * @param msg
     */
    public void onEventMainThread(ResultGetVipAddNumBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                //新增会员数
                newVipCount=msg.TModel.getVipNum();
                tv_vip_number.setText(newVipCount+"");
                //会员绝对百分比
                vipProgress=(double)msg.TModel.getVipNum()/(double)Card*100;
                if(vipProgress!=0 && !Double.isNaN(vipProgress)){
                    am_progressbar_members.setProgress((int)vipProgress);
                }else{
                    am_progressbar_members.setProgress(100);
                }
                //同比，环比
                comparedProgress=newVipCount-msg.TModel.getLastYearVip();
                sequentialProgress=newVipCount-msg.TModel.getLastMonthVip();
                if(comparedProgress!=0 && !Double.isNaN(comparedProgress)){
                    tv_vip_compared.setText(DecimalFormatUtils.decimalToFormats((double)comparedProgress/(double)msg.TModel.getLastYearVip())+"%");
                }else{
                    tv_vip_compared.setText(100+"%");
                }
                if(sequentialProgress!=0 && !Double.isNaN(sequentialProgress)){
                    tv_vip_sequential.setText(DecimalFormatUtils.decimalToFormats((double)sequentialProgress/(double)msg.TModel.getLastMonthVip())+"%");
                }else{
                    tv_vip_sequential.setText(100+"%");
                }

            }else{
                am_progressbar_members.setProgress(100);
            }
        }else{
            am_progressbar_members.setProgress(100);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    /**
     * 接收获取店铺个人实时销售数据
     * @param msg
     */
    public void onEventMainThread(ResultCounselorSaleBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                counselorSalesAmount=msg.TModel.getSalesAmount();
                tv_current_sales.setText("￥"+counselorSalesAmount);
            }
        }else{
            ToastFactory.getLongToast(getContext(),"接收获取店铺个人实时销售数据:"+msg.Message);
        }
    }

    /**
     * 接收获取店铺个销售目标数据
     * @param msg
     */
    public void onEventMainThread(ResultGetDayTotalBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                tv_current_shop_sales.setText("￥"+msg.TModel.getCounselorGoal());
            }
        }else{
            ToastFactory.getLongToast(getContext(),"接收获取店铺个销售目标数据:"+msg.Message);
        }
    }

    /**
     * 接收获取店铺销售数据
     * @param msg
     */
    public void onEventMainThread(ResultShopSaleCompareBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                shopSalesAmount=msg.TModel.getSalesAmount();
                tv_counselor_goal.setText("￥"+shopSalesAmount);
                //店铺绝对百分比
                shopProgress=shopSalesAmount/ShopGoal*100;
                if(shopProgress!=0 && !Double.isNaN(shopProgress)){
                    am_progressbar_shop.setProgress((int)shopProgress);
                }else{
                    am_progressbar_shop.setProgress(100);
                }
                //店铺同比
                ShopComparedProgress=(shopSalesAmount-msg.TModel.getLastYearAmount())/msg.TModel.getLastYearAmount();
                if(ShopComparedProgress!=0){
                    tv_shop_compared_progress.setText("100%");
//                    tv_shop_compared_progress.setText(DecimalFormatUtils.decimalToFormats(ShopComparedProgress)+"%");

                }else{
                    tv_shop_compared_progress.setText(100+"%");
                }

                //店铺环比
                ShopSequentialProgress=(shopSalesAmount-msg.TModel.getLastAmount())/msg.TModel.getLastAmount();
                if(ShopSequentialProgress!=0){
                    tv_shop_sequential_progress.setText("100%");
//                    tv_shop_sequential_progress.setText(DecimalFormatUtils.decimalToFormats(ShopSequentialProgress)+"%");
                }else{
                    tv_shop_sequential_progress.setText(100+"%");
                }
            }else{
                am_progressbar_shop.setProgress(100);
            }

        }else{
            am_progressbar_shop.setProgress(100);
            ToastFactory.getLongToast(getContext(),"获取店铺销售数据失败:"+msg.Message);
        }
    }

    /**
     * 接收获取个人销售数据
     * @param msg
     */
    public void onEventMainThread(ResultSalerSaleCompareBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                counselorSalesAmount=msg.TModel.getSalesAmount();
                tv_CounselorGoal.setText("￥"+counselorSalesAmount);
                //个人绝对百分比
                counselorProgress=counselorSalesAmount/CounselorGoal*100;
                if(counselorProgress!=0 && !Double.isNaN(counselorProgress)){
                    am_progressbar_personal.setProgress((int)counselorProgress);
                }else{
                    am_progressbar_personal.setProgress(100);
                }

                //个人同比
                SalerComparedProgress=(counselorSalesAmount-msg.TModel.getLastYearAmount())/msg.TModel.getLastYearAmount();
                if(SalerComparedProgress!=0){
                    tv_saler_compared_progress.setText("100%");
//                    tv_saler_compared_progress.setText(DecimalFormatUtils.decimalToFormats(SalerComparedProgress)+"%");
                }else{
                    tv_saler_compared_progress.setText(100+"%");
                }
                //个人环比
                SalerSequentialProgress=(counselorSalesAmount-msg.TModel.getLastAmount())/msg.TModel.getLastAmount();
                if(SalerSequentialProgress!=0){
                    tv_salersequential_progress.setText("100%");
//                    tv_salersequential_progress.setText(DecimalFormatUtils.decimalToFormats(SalerSequentialProgress)+"%");
                }else{
                    tv_salersequential_progress.setText(100+"%");
                }

            }else{
                am_progressbar_personal.setProgress(100);
            }

        }else{
            am_progressbar_personal.setProgress(100);
            ToastFactory.getLongToast(getContext(),"获取店铺销售数据失败:"+msg.Message);
        }
    }

    /**
     * 接收店铺任务数据
     * @param msg
     */
    public void onEventMainThread(ResultShopTaskListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                tv_not_task_data.setVisibility(View.GONE);
                taskList=new ArrayList<>();
                taskList.addAll(msg.TModel);
                taskListAdapter=new MainTaskListAdapter(taskList);
                rv_task_list.setAdapter(taskListAdapter);
                rv_task_list.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        Bundle bundle=new Bundle();
                        bundle.putInt("workid",taskList.get(position).getWorkId());
                        bundle.putInt("flowid",taskList.get(position).getFlowId());
                        bundle.putInt("formid",taskList.get(position).getFormId());
                        Skip.mNextFroData(getActivity(),HandleTaskActivity.class,bundle);
                    }
                });
            }else{
                tv_not_task_data.setVisibility(View.VISIBLE);
            }
        }else{
            tv_not_task_data.setVisibility(View.VISIBLE);
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    /**
     * 获取店铺，店员日目标，店员开卡目标和新增粉丝数
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultDayTotalBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null){
                //绝对达成目标
                Card=msg.TModel.getCard();
                CounselorGoal=msg.TModel.getCounselorGoal();
                ShopGoal=msg.TModel.getShopGoal();
                //相对达成目标
                TodayCard=msg.TModel.getTodayCard();
                TodayCounselorGoal=msg.TModel.getTodayCounselorGoal();
                TodayShopGoal=msg.TModel.getTodayShopGoal();

                //店铺绝对目标
                tv_shop_goal.setText("￥"+ShopGoal);
                //个人绝对目标
                tv_TodayCounselorGoal.setText("￥"+CounselorGoal);
                //会员绝对目标
                tv_vip_cart_number.setText(Card+"");

            }else{
                am_progressbar_shop.setProgress(100);
                am_progressbar_personal.setProgress(100);
                am_progressbar_members.setProgress(100);
            }
        }else{
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    @Override
    public void processClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_before_day:
                index=0;
                tv_current_data.setText("销售数据");
                dayTotalNet=new NewGetDayTotalNet(getContext());
                dayTotalNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
                shopSaleCompareNet=new ShopSaleCompareNet(getContext());
                shopSaleCompareNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
                salerSaleCompareNet=new SalerSaleCompareNet(getContext());
                salerSaleCompareNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
                getVipAddNumNet=new GetVipAddNumNet(getContext());
                getVipAddNumNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
            break;
            case R.id.tv_week:
                tv_current_data.setText("销售数据");
                index=1;
                dayTotalNet=new NewGetDayTotalNet(getContext());
                dayTotalNet.setData(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek());
                shopSaleCompareNet=new ShopSaleCompareNet(getContext());
                shopSaleCompareNet.setData(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek());
                salerSaleCompareNet=new SalerSaleCompareNet(getContext());
                salerSaleCompareNet.setData(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek());
                getVipAddNumNet=new GetVipAddNumNet(getContext());
                getVipAddNumNet.setData(AbDateUtil.getTopWeek(),AbDateUtil.getCurrentWeek());
                break;
            case R.id.tv_month:
                tv_current_data.setText("销售数据");
                index=2;
                dayTotalNet=new NewGetDayTotalNet(getContext());
                dayTotalNet.setData(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
                shopSaleCompareNet=new ShopSaleCompareNet(getContext());
                shopSaleCompareNet.setData(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
                salerSaleCompareNet=new SalerSaleCompareNet(getContext());
                salerSaleCompareNet.setData(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
                getVipAddNumNet=new GetVipAddNumNet(getContext());
                getVipAddNumNet.setData(AbDateUtil.getFirstDayOfMonth("yyyy-MM-dd 00:00:00"),AbDateUtil.getLastDayOfMonth("yyyy-MM-dd 23:59:59"));
                break;
        }
        setColor(index);
    }

    @Override
    public void fetchData() {

    }

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(getContext().getResources().getColor(R.color.new_menu_text_color));
            tvs.get(i).setBackgroundColor(getContext().getResources().getColor(R.color.day_sign_content_text_white_30));
        }
        tvs.get(index).setTextColor(getContext().getResources().getColor(R.color.white));
        tvs.get(index).setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.button_purple_bg));
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1022){//
            if(data!=null){
                if(mCache.getAsString("cb_sales_data")!=null && !"".equals(mCache.getAsString("cb_sales_data"))){
                    ll_sales_data.setVisibility(View.GONE);
                }else{
                    ll_sales_data.setVisibility(View.VISIBLE);
                }
                if(mCache.getAsString("cb_task_data")!=null && !"".equals(mCache.getAsString("cb_task_data"))){
                    ll_task_data.setVisibility(View.GONE);
                }else{
                    ll_task_data.setVisibility(View.VISIBLE);
                }
                if(mCache.getAsString("cb_order_data")!=null && !"".equals(mCache.getAsString("cb_order_data"))){
                    ll_order_data.setVisibility(View.GONE);
                }else{
                    ll_order_data.setVisibility(View.VISIBLE);
                }
                if(mCache.getAsString("cb_visit_data")!=null && !"".equals(mCache.getAsString("cb_visit_data"))){
                    ll_visit_data.setVisibility(View.GONE);
                }else{
                    ll_visit_data.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                am_progressbar_personal.setProgress(100);

                getDayTotalNet=new GetDayTotalNet(getContext());
                getDayTotalNet.setData();
                counselorSaleNet=new CounselorSaleNet(getContext());
                counselorSaleNet.setData();

                dayTotalNet=new NewGetDayTotalNet(getContext());
                dayTotalNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
                shopSaleCompareNet=new ShopSaleCompareNet(getContext());
                shopSaleCompareNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
                salerSaleCompareNet=new SalerSaleCompareNet(getContext());
                salerSaleCompareNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));
                getVipAddNumNet=new GetVipAddNumNet(getContext());
                getVipAddNumNet.setData(AbDateUtil.YesTerDay("yyyy-MM-dd 00:00:00"),AbDateUtil.YesTerDay("yyyy-MM-dd 23:59:59"));

                taskListNet =new TaskListNet(getContext());
                taskListNet.setData(pageIndex,3,0);

                unReceiveOrderNet=new UnReceiveOrderNet(getContext());
                unReceiveOrderNet.setData(pageIndex, SortUtils.setSort());

                net=new FaceListNet(getContext());
                net.setData(pageIndex, AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
