package com.cesaas.android.counselor.order.activity.main.fragment.newmain;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.TaskNewActivity;
import com.cesaas.android.counselor.order.activity.user.NoticeListActivity;
import com.cesaas.android.counselor.order.activity.user.bean.ResultNoticeListBean;
import com.cesaas.android.counselor.order.activity.user.net.NoticeNet;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.member.adapter.MemberServiceAdapter;
import com.cesaas.android.counselor.order.member.bean.service.ResultProcessedServiceSumBean;
import com.cesaas.android.counselor.order.member.net.service.ServiceSumDataNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnServiceInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceBirthInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceContinueActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceCustomInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceDormancyInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceFestInfoActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceInfoActivity;
import com.cesaas.android.counselor.order.pay.Base64;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.net.TaskListNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.SortUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.ResultWaitOutOrderBean;
import com.cesaas.android.order.net.WaitOutOrderListNet;
import com.cesaas.android.order.ui.activity.NewOrderActivity;
import com.jauker.widget.BadgeView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/23 14:56
 * Version 1.0
 */

public class NewWorkbenchFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout ll_task_new,ll_member_service,ll_service_list,ll_notice,ll_order,ll_notice_msg,ll_order_msg;
    private LinearLayout ll_service_visit,ll_service_birth,ll_service_fest,ll_service_dormancy,ll_service_custom,ll_service_return,ll_return_continue;
    private TextView tv_visit_count,tv_birth_count,tv_festival_count,tv_dormancy_count,tv_custom_count,tv_return_count;
    private ImageView iv_right_arrow,iv_service,iv_notice,iv_order,iv_task;
//    private RecyclerView rv_service_list;
    private boolean isShowService=false;

    private MemberServiceAdapter adapter;
    private ServiceSumDataNet net;
    private TaskListNet taskListNet;
    private NoticeNet noticeNet;
    private WaitOutOrderListNet byCounselorNet;

    private BadgeView noticeBadgeView;
    private BadgeView serviceBadgeView;
    private BadgeView orderBadgeView;
    private BadgeView taskBadgeView;

    /**
     * 单例
     */
    public static NewWorkbenchFragment newInstance() {
        NewWorkbenchFragment fragmentCommon = new NewWorkbenchFragment();
        return fragmentCommon;
    }

    public void onEventMainThread(final ResultWaitOutOrderBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                orderBadgeView=new BadgeView(getContext());
                orderBadgeView.setTargetView(ll_order_msg);
                orderBadgeView.setBadgeCount(msg.TModel.size());
            }
        }
    }

    public void onEventMainThread(final ResultNoticeListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                noticeBadgeView=new BadgeView(getContext());
                noticeBadgeView.setTargetView(ll_notice_msg);
                noticeBadgeView.setBadgeCount(msg.TModel.size());
            }
        }
    }

    public void onEventMainThread(ResultShopTaskListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                taskBadgeView=new BadgeView(getContext());
                taskBadgeView.setTargetView(iv_task);
                taskBadgeView.setBadgeCount(msg.TModel.size());
            }
        }
    }

    public void onEventMainThread(ResultProcessedServiceSumBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel.getVisit()!=0){
                tv_visit_count.setText(msg.TModel.getVisit()+"");
                tv_visit_count.setVisibility(View.VISIBLE);
            }
            if(msg.TModel.getBirth()!=0){
                tv_birth_count.setText(msg.TModel.getBirth()+"");
                tv_birth_count.setVisibility(View.VISIBLE);
            }
            if(msg.TModel.getFestival()!=0){
                tv_festival_count.setText(msg.TModel.getFestival()+"");
                tv_festival_count.setVisibility(View.VISIBLE);
            }
            if(msg.TModel.getDormancy()!=0){
                tv_dormancy_count.setText(msg.TModel.getDormancy()+"");
                tv_dormancy_count.setVisibility(View.VISIBLE);
            }
            if(msg.TModel.getCustom()!=0){
                tv_custom_count.setText(msg.TModel.getCustom()+"");
                tv_custom_count.setVisibility(View.VISIBLE);
            }
            if(msg.TModel.getReturn()!=0){
                tv_return_count.setText(msg.TModel.getReturn()+"");
                tv_return_count.setVisibility(View.VISIBLE);
            }
            int serviceSum=msg.TModel.getVisit()+msg.TModel.getBirth()+msg.TModel.getFestival()+msg.TModel.getDormancy()+msg.TModel.getCustom()+msg.TModel.getReturn();
            serviceBadgeView=new BadgeView(getContext());
            serviceBadgeView.setTargetView(iv_service);
            serviceBadgeView.setBadgeCount(serviceSum);
        }else{
            ToastFactory.getLongToast(getContext(),"Msg:"+msg.Message);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_work_bench;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mSwipeRefreshLayout=findView(R.id.swipeLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        ll_order_msg=findView(R.id.ll_order_msg);
        ll_notice_msg=findView(R.id.ll_notice_msg);
        iv_task=findView(R.id.iv_task);
        iv_order=findView(R.id.iv_order);
        iv_notice=findView(R.id.iv_notice);
        iv_service=findView(R.id.iv_service);
        ll_order=findView(R.id.ll_order);
        ll_notice=findView(R.id.ll_notice);
        ll_task_new=findView(R.id.ll_task_new);
        ll_member_service=findView(R.id.ll_member_service);
        ll_service_list=findView(R.id.ll_service_list);
        ll_service_visit=findView(R.id.ll_service_visit);
        ll_return_continue=findView(R.id.ll_return_continue);
        ll_service_return=findView(R.id.ll_service_return);
        ll_service_birth=findView(R.id.ll_service_birth);
        ll_service_fest=findView(R.id.ll_service_fest);
        ll_service_dormancy=findView(R.id.ll_service_dormancy);
        ll_service_custom=findView(R.id.ll_service_custom);
        iv_right_arrow=findView(R.id.iv_right_arrow);
        tv_visit_count=findView(R.id.tv_visit_count);
        tv_birth_count=findView(R.id.tv_birth_count);
        tv_return_count=findView(R.id.tv_return_count);
        tv_festival_count=findView(R.id.tv_festival_count);
        tv_dormancy_count=findView(R.id.tv_dormancy_count);
        tv_custom_count=findView(R.id.tv_custom_count);
//        rv_service_list=findView(R.id.rv_service_list);
//        rv_service_list.setLayoutManager(new LinearLayoutManager(getContext()));
        net=new ServiceSumDataNet(getContext());
        net.setData();
        taskListNet=new TaskListNet(getContext());
        taskListNet.setData(1,50,0);
        noticeNet=new NoticeNet(getContext());
        noticeNet.setData(1);
        byCounselorNet=new WaitOutOrderListNet(getContext());
        byCounselorNet.setData(1,2, SortUtils.setSort());
    }

    @Override
    public void initListener() {
        ll_task_new.setOnClickListener(this);
        ll_member_service.setOnClickListener(this);
        ll_service_visit.setOnClickListener(this);
        ll_return_continue.setOnClickListener(this);
        ll_service_birth.setOnClickListener(this);
        ll_service_fest.setOnClickListener(this);
        ll_service_dormancy.setOnClickListener(this);
        ll_service_custom.setOnClickListener(this);
        ll_service_return.setOnClickListener(this);
        ll_notice.setOnClickListener(this);
        ll_order.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_order:
                Skip.mNext(getActivity(), NewOrderActivity.class);
                break;
            case R.id.ll_notice:
                Skip.mNext(getActivity(),NoticeListActivity.class);
                break;
            case R.id.ll_task_new://任务提醒
                Skip.mNext(getActivity(),TaskNewActivity.class);
                break;
            case R.id.ll_member_service:
                if(isShowService==false){
                    isShowService=true;
                    iv_right_arrow.setImageResource(R.mipmap.di_arrow);
                    ll_service_list.setVisibility(View.VISIBLE);
                }else{
                    isShowService=false;
                    iv_right_arrow.setImageResource(R.mipmap.arrow_right);
                    ll_service_list.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_service_visit://销售回访
                Skip.mNext(getActivity(),MemberServiceInfoActivity.class);
                break;
            case R.id.ll_service_birth://生日祝福
                Skip.mNext(getActivity(),MemberServiceBirthInfoActivity.class);
                break;
            case R.id.ll_service_fest://节日安排
                Skip.mNext(getActivity(),MemberServiceFestInfoActivity.class);
                break;
            case R.id.ll_service_dormancy://休眠激活
                Skip.mNext(getActivity(),MemberServiceDormancyInfoActivity.class);
                break;
            case R.id.ll_service_return://退换货
                Skip.mNext(getActivity(),MemberReturnServiceInfoActivity.class);
                break;
            case R.id.ll_service_custom://会员定制
                Skip.mNext(getActivity(),MemberServiceCustomInfoActivity.class);
                break;
            case R.id.ll_return_continue://再次服务
                Skip.mNext(getActivity(),MemberServiceContinueActivity.class);
                break;
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                net=new ServiceSumDataNet(getContext());
                net.setData();
                taskListNet=new TaskListNet(getContext());
                taskListNet.setData(1,50,0);
                noticeNet=new NoticeNet(getContext());
                noticeNet.setData(1);
                byCounselorNet=new WaitOutOrderListNet(getContext());
                byCounselorNet.setData(1,2, SortUtils.setSort());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

}
