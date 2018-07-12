package com.cesaas.android.counselor.order.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.shopmange.AddInvitationOrderActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.BaseJavascriptInterface;
import com.flybiao.materialdialog.MaterialDialog;

/**
 * 业绩查询
 */
public class ResultsQueryActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle,mTextViewRightTitle;

    private WebView wv_member;
    protected WaitDialog mDialog;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_query);

        mMaterialDialog=new MaterialDialog(mContext);
        initView();
        initData();
    }
    private void initView() {
        wv_member= (WebView) findViewById(R.id.wv_invitation_order);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseTitle.setText("业绩查询");

        llBaseBack.setOnClickListener(this);
        mTextViewRightTitle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                bundle.putString("Url", BaseJavascriptInterface.getUrl());
                bundle.putString("vipId",prefs.getString("VipId"));
                Skip.mNextFroData(mActivity,AddInvitationOrderActivity.class,bundle);
                break;
        }
    }

    public void initData(){
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_member,mDialog, Urls.RESULTS_QUERY);
        BaseInitJavascriptInterface.initJavascriptInterface(mContext,mActivity,mMaterialDialog,wv_member,bundle,prefs,tvBaseTitle,mTextViewRightTitle,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(mContext,mActivity,wv_member);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }

}
