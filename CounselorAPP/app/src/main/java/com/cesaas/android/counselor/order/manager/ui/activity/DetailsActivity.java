package com.cesaas.android.counselor.order.manager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.manager.bean.ResultTaskStatisticBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;
import com.flybiao.materialdialog.MaterialDialog;



/**
 * 任务详情
 */
public class DetailsActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvLeftTitle,tv_check_flow,mTextViewRightTitle;
    private TextView tv_task_title,tv_task_create_date,tv_title;
    private LinearLayout llBack;
    private WebView wv_task_details;

    private String leftTitle;
    private String Title;
    private String CreateTime;
    private String TaskTitle;
    private String TaskUser;
    private int TaskId;
    private int WorkId;
    private int FormId;
    private int FlowId;

    private MaterialDialog mMaterialDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_layout);
        mMaterialDialog=new MaterialDialog(mContext);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            leftTitle=bundle.getString("leftTitle");
            CreateTime=bundle.getString("CreateTime");
            TaskTitle=bundle.getString("TaskTitle");
            TaskUser=bundle.getString("TaskUser");
            Title=bundle.getString("Title");
            TaskId=bundle.getInt("TaskId");
            WorkId=bundle.getInt("WorkId");
            FormId=bundle.getInt("FormId");
            FlowId=bundle.getInt("FlowId");
        }

        initView();
        initData();
    }

    private void initData(){

        tv_task_title.setText(Title);
        tv_task_create_date.setText(CreateTime);
        tv_title.setText(TaskUser+"，"+Title);

        WebViewUtils.initWebSettings(wv_task_details,mDialog, Urls.getShopTaskDetail(WorkId,FlowId,FormId));
        BaseInitJavascriptInterface.initJavascriptInterface(mContext,mActivity,mMaterialDialog,wv_task_details,bundle,prefs,tvTitle,mTextViewRightTitle,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(mContext,mActivity,wv_task_details);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }

    private void initView() {
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setOnClickListener(this);
        wv_task_details= (WebView) findViewById(R.id.wv_task_details);

        tv_title= (TextView) findViewById(R.id.tv_title);
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tv_task_title= (TextView) findViewById(R.id.tv_task_title);
        tv_task_create_date= (TextView) findViewById(R.id.tv_task_create_date);
        tvLeftTitle.setText(leftTitle);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("任务详情");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tv_check_flow= (TextView) findViewById(R.id.tv_check_flow);
        tv_check_flow.setOnClickListener(this);
        initClickListener();
    }

    private void initClickListener(){

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_check_flow:
                bundle.putString("leftTitle",tvTitle.getText().toString());
                bundle.putInt("WorkId",WorkId);
                bundle.putString("Title",Title);
                bundle.putString("CreateTime",CreateTime);
                bundle.putString("TaskUser",TaskUser);
                Skip.mNextFroData(mActivity,TaskFlowActivity.class,bundle);
                break;
        }
    }
}
