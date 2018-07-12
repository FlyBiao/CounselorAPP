package com.cesaas.android.counselor.order.boss.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
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

public class StoreSalesActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvLeftTitle,mTextViewRightTitle;
    private ImageView ivShare,ivSelect;
    private LinearLayout llBack;
    private WebView wvOnLineShop;

    protected WaitDialog mDialog;
    private MaterialDialog mMaterialDialog;

    private String leftTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_sales);
        mMaterialDialog=new MaterialDialog(mContext);
        if(bundle!=null){
            leftTitle=bundle.getString("LeftTitle");
        }
        initView();
        initData();
    }
    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("平均店销");
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);
        ivShare= (ImageView) findViewById(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.share);
        ivShare.setOnClickListener(this);
        ivSelect= (ImageView) findViewById(R.id.iv_scan_s);
        ivSelect.setVisibility(View.VISIBLE);
        ivSelect.setImageResource(R.mipmap.select);
        ivSelect.setOnClickListener(this);

        mTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitle.setOnClickListener(this);
        wvOnLineShop= (WebView) findViewById(R.id.wv_store_sales);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.iv_add_module:
                break;
            case R.id.iv_scan_s:
                Intent intent= new Intent(mContext, SelectShopActivity.class);
                intent.putExtra("LeftTitle",tvTitle.getText().toString());
                Skip.mSelActivityResult(mActivity, 201,intent);
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
        WebViewUtils.initWebSettings(wvOnLineShop,mDialog, Urls.RESULTS_QUERY);
        BaseInitJavascriptInterface.initJavascriptInterface(mContext,mActivity,mMaterialDialog,wvOnLineShop,bundle,prefs,tvTitle,mTextViewRightTitle,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        BaseActivityResult result=new BaseActivityResult(mContext,mActivity,wvOnLineShop);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
    }
}
