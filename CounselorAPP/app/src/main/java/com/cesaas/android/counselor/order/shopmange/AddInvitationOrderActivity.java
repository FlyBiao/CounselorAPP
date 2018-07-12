package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebView;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;
import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.InitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.WebViewEventBus;
import com.flybiao.materialdialog.MaterialDialog;


/**
 * 新增邀单计划页面
 */
public class AddInvitationOrderActivity extends FragmentActivity implements View.OnClickListener{
    private LinearLayout llBaseBack;
    private TextView tvBaseTitle,mTextViewRightTitle;
    private WebView wv_member;
    protected WaitDialog mDialog;

    private String id;
    private String url;
    private MaterialDialog mMaterialDialog;

    public AbPrefsUtil prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invitation_order);
        //输入法软键盘遮挡输入框实现（界面自动上滑并可滑动）
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //禁止APP横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            id=bundle.getString("vipId");
            url=bundle.getString("Url");
        }
        mMaterialDialog=new MaterialDialog(this);

        prefs = AbPrefsUtil.getInstance();

        initView();
        initData();
    }

    public void initData(){
        Bundle bundle=new Bundle();
        AbPrefsUtil prefs=AbPrefsUtil.getInstance();
        mDialog = new WaitDialog(this);
        WebViewUtils.initWebSettings(wv_member,mDialog, url+id);
        BaseInitJavascriptInterface.initJavascriptInterface(this,AddInvitationOrderActivity.this,mMaterialDialog,wv_member,bundle,prefs,tvBaseTitle,mTextViewRightTitle,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(getApplicationContext(),AddInvitationOrderActivity.this,wv_member);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }


    private void initView() {
        wv_member= (WebView) findViewById(R.id.wv_add_invitation_order);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseTitle.setText("邀单计划");

        llBaseBack.setOnClickListener(this);
        mTextViewRightTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(this);
                break;
        }
    }
}
