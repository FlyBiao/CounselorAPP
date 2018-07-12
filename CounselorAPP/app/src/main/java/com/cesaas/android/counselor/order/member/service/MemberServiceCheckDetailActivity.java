package com.cesaas.android.counselor.order.member.service;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.member.adapter.MemberServiceSelectAdapter;
import com.cesaas.android.counselor.order.member.bean.service.CheckServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultCloseBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultCounselorListBean;
import com.cesaas.android.counselor.order.member.fragment.MemberServiceCheckFragment;
import com.cesaas.android.counselor.order.member.net.service.CloseNet;
import com.cesaas.android.counselor.order.member.net.service.CounselorListNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务检查
 */
public class MemberServiceCheckDetailActivity extends BaseTemplateActivity{

    private TextView tvTitle,tv_Finish,tv_Quantity;
    private TextView tv_service_status,tv_service_title,tv_service_end_date,tv_service_create_date,tv_service_remark;
    private TextView tv_remove,tv_sure,tv_close_service;
    private LinearLayout llBack;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View headerView;
    private RecyclerView mRecyclerView;

    private int Id;
    public static int ids;
    private int Status;
    private int Finish;
    private int Quantity;
    private String Title;
    private static String Titles;
    private String CreateDate;
    private String EndDate;
    private String Remark;

    private MemberServiceSelectAdapter adapter;
    private List<ResultCounselorListBean.CounselorListBean> mData=new ArrayList<>();
    private CloseNet closeNet;
    private CounselorListNet counselorListNet;

    @Override
    public int getLayoutId() {
        return R.layout.activity_member_service_check_detail;
    }

    public void onEventMainThread(BaseTabBean msg) {
        //展开右侧菜单筛选条件
        mDrawerLayout.openDrawer(Gravity.RIGHT);
//        counselorListNet=new CounselorListNet(mContext);
//        counselorListNet.setData();
    }

    public void onEventMainThread(ResultCloseBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"关闭服务成功！");
            Skip.mNext(mActivity, MemberServiceCheckActivity.class,true);
        }else{
            ToastFactory.getLongToast(mContext,"关闭服务失败:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultCounselorListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
                adapter=new MemberServiceSelectAdapter(mData);
                mRecyclerView.setAdapter(adapter);
            }else{

            }
        }else{
            ToastFactory.getLongToast(mContext,"获取店铺导购失败:"+msg.Message);
        }
    }

    @Override
    public void initViews() {
        tv_Finish=findView(R.id.tv_Finish);
        tv_Quantity=findView(R.id.tv_Quantity);
        tv_service_status=findView(R.id.tv_service_status);
        tv_service_title=findView(R.id.tv_service_title);
        tv_service_end_date=findView(R.id.tv_service_end_date);
        tv_service_create_date=findView(R.id.tv_service_create_date);
        tv_service_remark=findView(R.id.tv_service_remark);

        tv_close_service=findView(R.id.tv_close_service);
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("会员服务检查-详情");
        llBack=findView(R.id.ll_base_title_back);
        mDrawerLayout=findView(R.id.drawer_layout);
        mNavigationView= (NavigationView) findViewById(R.id.navigation_view);

        initDrawerLayout();
    }

    @Override
    public void initListener() {
        llBack.setOnClickListener(this);
        tv_remove.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        tv_close_service.setOnClickListener(this);
    }

    @Override
    public void initData() {

        try{
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null){
                Id=bundle.getInt("Id");
                ids=Id;
                Status=bundle.getInt("Status");
                Finish=bundle.getInt("Finish");
                Quantity=bundle.getInt("Quantity");
                Title=bundle.getString("Title");
                Titles=Title;
                CreateDate=bundle.getString("CreateDate");
                EndDate=bundle.getString("EndDate");
                Remark=bundle.getString("Remark");
                tv_Quantity.setText(Quantity+"");
                tv_Finish.setText(Finish+"");
                tv_service_title.setText(Title);
                tv_service_end_date.setText(EndDate);
                tv_service_create_date.setText(CreateDate);
                if(CreateDate!=null && !"".equals(CreateDate)){
                    tv_service_create_date.setText(AbDateUtil.getDateYMDs(CreateDate));
                }else{
                    tv_service_create_date.setText("暂无发布日期");
                }
                if(EndDate!=null && !"".equals(EndDate)){
                    tv_service_end_date.setText(AbDateUtil.getDateYMDs(EndDate));
                }else{
                    tv_service_end_date.setText("暂无截至日期");
                }
                if(Remark!=null){
                    tv_service_remark.setText(Remark);
                }else{
                    tv_service_remark.setText("暂无服务备注！");
                }
                switch (Status){
                    case 10:
                        tv_service_status.setText("进行中");
                        tv_service_status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_red_bg));
                        break;
                    case 20:
                        tv_service_status.setText("已完成");
                        tv_service_status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_ellipse_huise_bg));
                        break;
                    case 30:
                        tv_service_status.setText("已关闭");
                        tv_service_status.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_ellipse_huise_bg));
                        break;
                    default:
                        break;
                }
            }
            getId();
            getSupportFragmentManager().beginTransaction().add(R.id.member_service_check_detail_frame_layout, new MemberServiceCheckFragment()).commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int getId(){
        int id=ids;
        return id;
    }

    public static String getTitles(){
        String title=Titles;
        return title;
    }

    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_remove:
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_sure:
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_close_service:
                new MyAlertDialog(mContext).mInitShow("温馨提示", "是否确定关闭该会员服务？",
                        "确定", "点错了", new MyAlertDialog.ConfirmListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                closeNet=new CloseNet(mContext);
                                closeNet.setData(Id);
                            }
                        }, null);
                break;
            default:
                break;
        }
    }

    private void initDrawerLayout() {
        headerView = mNavigationView.getHeaderView(0);
        tv_remove= (TextView) headerView. findViewById(R.id.tv_remove);
        tv_sure= (TextView) headerView. findViewById(R.id.tv_sure);
        mRecyclerView= (RecyclerView) headerView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
    }
}
