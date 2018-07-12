package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.shopmange.bean.FilterClerkTypeBean;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.db.ClerkFilterSQLiteDatabaseUtils;
import com.cesaas.android.counselor.order.utils.db.DBConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤店员设置
 */
public class FilterClerkActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView mTextViewTitle;
    private Switch sc_hide_audit_clerk,sc_hide_dimission_clerk,sc_hide_job_clerk;

    private int searchResultFilterCode=1001;
    private FilterClerkTypeBean clerkTypeBean;
    private ClerkFilterSQLiteDatabaseUtils databaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_clerk_aactivity);

        initView();
        initData();
        getSwitchClickListener();
    }

    public void onEventMainThread(FilterClerkTypeBean msg) {
        clerkTypeBean=new FilterClerkTypeBean();
        clerkTypeBean=msg;

        if(clerkTypeBean.getFilterAuditType()!=-1){
            sc_hide_audit_clerk.setChecked(false);
        }else{
            sc_hide_audit_clerk.setChecked(true);
        }
//        if(clerkTypeBean.getFilterDimissionType()==1){
//            sc_hide_dimission_clerk.setChecked(true);
//        }else{
//            sc_hide_dimission_clerk.setChecked(false);
//        }
//        if(clerkTypeBean.getFilterJobType()==1){
//            sc_hide_job_clerk.setChecked(true);
//        }else{
//            sc_hide_job_clerk.setChecked(false);
//        }
    }

    private void initData() {
//        //查询数据
        databaseUtils=new ClerkFilterSQLiteDatabaseUtils(mContext,null,null, DBConstant.VERSION);
        databaseUtils.createDB(mContext,DBConstant.DB,DBConstant.VERSION);
        databaseUtils.selectData(mContext);
    }

    private void initView() {
        sc_hide_job_clerk= (Switch) findViewById(R.id.sc_hide_job_clerk);
        sc_hide_dimission_clerk= (Switch) findViewById(R.id.sc_hide_dimission_clerk);
        sc_hide_audit_clerk= (Switch) findViewById(R.id.sc_hide_audit_clerk);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText("设置");

        llBaseBack.setOnClickListener(this);
    }

    private void getSwitchClickListener(){
        //待审核
        sc_hide_audit_clerk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    databaseUtils.updateData(mContext,-1,clerkTypeBean.getFilterDimissionType(),clerkTypeBean.getFilterJobType(),clerkTypeBean.getId());
                    Intent mIntent = new Intent();
                    mIntent.putExtra("FilterType","-1");
                    setResult(searchResultFilterCode, mIntent);// 设置结果，并进行传送
                }else{
                    databaseUtils.updateData(mContext,0,clerkTypeBean.getFilterDimissionType(),clerkTypeBean.getFilterJobType(),clerkTypeBean.getId());
                    Intent mIntent = new Intent();
                    mIntent.putExtra("FilterType","0");
                    setResult(searchResultFilterCode, mIntent);// 设置结果，并进行传送
                }
            }
        });
        //已离职
        sc_hide_dimission_clerk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    databaseUtils.updateData(mContext,clerkTypeBean.getFilterAuditType(),1,clerkTypeBean.getFilterJobType(),clerkTypeBean.getId());
                }else{
                    databaseUtils.updateData(mContext,clerkTypeBean.getFilterAuditType(),0,clerkTypeBean.getFilterJobType(),clerkTypeBean.getId());
                }
            }
        });
        //在职
        sc_hide_job_clerk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    databaseUtils.updateData(mContext,clerkTypeBean.getFilterAuditType(),clerkTypeBean.getFilterDimissionType(),1,clerkTypeBean.getId());
                }else{
                    databaseUtils.updateData(mContext,clerkTypeBean.getFilterAuditType(),clerkTypeBean.getFilterDimissionType(),0,clerkTypeBean.getId());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }
}
