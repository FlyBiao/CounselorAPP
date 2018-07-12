package com.cesaas.android.counselor.order.shoptask.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.BaseJavascriptInterface;
import com.flybiao.materialdialog.MaterialDialog;

/**
 * 处理任务
 */
public class HandleTaskActivity extends BasesActivity implements View.OnClickListener{
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,getmTextViewRightTitle;
    private WebView wv_member;
    protected WaitDialog mDialog;

    private int formid;
    private int flowid;
    private int workid;

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_task);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            formid=bundle.getInt("formid");
            flowid=bundle.getInt("flowid");
            workid=bundle.getInt("workid");
        }
        mMaterialDialog=new MaterialDialog(mContext);

        initView();
        initData();
    }

    public void initView(){
        wv_member= (WebView) findViewById(R.id.wv_task_handle_activity);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText("任务详情");
        getmTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        getmTextViewRightTitle.setVisibility(View.VISIBLE);

        getmTextViewRightTitle.setOnClickListener(this);
        llBaseBack.setOnClickListener(this);
    }

    public void initData(){
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_member,mDialog, Urls.getShopTaskDetail(workid,flowid,formid));
        BaseInitJavascriptInterface.initJavascriptInterface(mContext,mActivity,mMaterialDialog,wv_member,bundle,prefs,mTextViewTitle,getmTextViewRightTitle,1);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseJavascriptInterface.getResult(requestCode, resultCode, data,mContext,mActivity,wv_member);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                bundle.putString("Url", BaseJavascriptInterface.getUrl());
                Skip.mNextFroData(mActivity,TaskFlowActivity.class,bundle);
                break;
        }
    }

}
