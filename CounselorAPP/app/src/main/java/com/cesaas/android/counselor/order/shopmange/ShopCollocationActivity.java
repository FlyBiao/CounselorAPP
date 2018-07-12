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
import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;

import com.flybiao.materialdialog.MaterialDialog;



/**
 * 商品搭配[H5]
 */
public class ShopCollocationActivity extends BasesActivity {
    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitle;

    private WebView wv_member;
    protected WaitDialog mDialog;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_collocation);
        mMaterialDialog=new MaterialDialog(mContext);

        initView();
        initData();
    }

    public void initView(){
        wv_member= (WebView) findViewById(R.id.wv_into_shop_collocation);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewTitle.setText("商品搭配");

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(){
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_member,mDialog, Urls.SHOP_COLLOCATION);

        BaseInitJavascriptInterface.initJavascriptInterface(mContext,mActivity,mMaterialDialog,wv_member,bundle,prefs,mTextViewTitle,mTextViewRightTitle,0);
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(mContext,mActivity,wv_member);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }

}
