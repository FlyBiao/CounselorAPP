package com.cesaas.android.counselor.order.activity.user;

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
 * 公告详情
 */
public class NoticeDetailActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBack;
    private TextView tvTitle,mTextViewRightTitle;

    private WebView wvNotice;
    protected WaitDialog mDialog;
    private MaterialDialog mMaterialDialog;

    private int noticeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        mMaterialDialog=new MaterialDialog(mContext);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            noticeId=bundle.getInt("noticeId");
        }
        initView();
        initData();
    }

    public void initData(){
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wvNotice,mDialog, Urls.SYS_NOTICE+noticeId);
        BaseInitJavascriptInterface.initJavascriptInterface(mContext,mActivity,mMaterialDialog,wvNotice,bundle,prefs,tvTitle,mTextViewRightTitle,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(mContext,mActivity,wvNotice);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }

    private void initView() {
        wvNotice= (WebView) findViewById(R.id.wv_notice_detail);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("公告详情");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        mTextViewRightTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                bundle.putString("Url", BaseJavascriptInterface.getUrl());
                bundle.putString("vipId",prefs.getString("VipId"));
                Skip.mNextFroData(mActivity,AddInvitationOrderActivity.class,bundle);
                break;
        }
    }
}
