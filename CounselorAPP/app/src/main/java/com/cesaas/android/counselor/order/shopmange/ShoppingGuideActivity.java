package com.cesaas.android.counselor.order.shopmange;

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
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;
import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.InitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.WebViewEventBus;

import com.flybiao.materialdialog.MaterialDialog;


/**
 * 导购排名
 */
public class ShoppingGuideActivity extends BasesActivity {

    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle;
    private WebView wv_member;

    protected WaitDialog mDialog;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_guide);
        mMaterialDialog=new MaterialDialog(mContext);
        initView();
        initData();
    }

    public void initView(){
        wv_member= (WebView) findViewById(R.id.wv_shopping_guide);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewTitle.setText("导购排名");

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(){
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_member,mDialog, Urls.SHOPPING_GUIDE);
//        InitJavascriptInterface.initJavascriptInterface(wv_member,mContext);
        BaseInitJavascriptInterface.initJavascriptInterface(mContext,mActivity,mMaterialDialog,wv_member,bundle,prefs,mTextViewTitle,mTextViewRightTitle,0);
    }

//    public void onEventMainThread(final WebViewRequestJsonBean mJson) {
//        WebViewEventBus viewEventBus=new WebViewEventBus(mContext,mActivity,mMaterialDialog,wv_member,bundle,mTextViewTitle,mTextViewRightTitle);
//        viewEventBus.getSwitch(mJson.getRequestType(),mJson.getWebviewParamJson());
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(mContext,mActivity,wv_member);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }
}
