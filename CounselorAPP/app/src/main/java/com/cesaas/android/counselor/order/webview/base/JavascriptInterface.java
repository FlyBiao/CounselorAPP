package com.cesaas.android.counselor.order.webview.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.base.BaseTestBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;
import com.flybiao.materialdialog.MaterialDialog;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 定义JS交互接口
 * Created at 2017/5/21 9:10
 * Version 1.0
 */

public class JavascriptInterface {

    private Context mContext;
    private AbPrefsUtil prefs;
    private Activity mActivity;
    private MaterialDialog mMaterialDialog;
    private Bundle bundle;

    private WebView mWebView;
    private TextView mTitle;
    private TextView mRightTitle;

    public JavascriptInterface(Context c,AbPrefsUtil prefs) {
        mContext = c;
        this.prefs=prefs;
    }

    public JavascriptInterface( Context mContext, Activity mActivity,MaterialDialog mMaterialDialog,WebView mWebView,Bundle bundle,AbPrefsUtil prefs,TextView mTitle,TextView mRightTitle){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        this.prefs=prefs;
        this.mTitle=mTitle;
        this.mRightTitle=mRightTitle;
    }

    /**
     * 返回UserToken
     * @return UserToken
     */
    @android.webkit.JavascriptInterface
    public String getUserInfo() {
        return prefs.getString("token");
    }

    /**
     * WebView posMessag
     * @param postData
     */
    @android.webkit.JavascriptInterface
    public void postMessage(String postData) {
        Log.i(Constant.TAG,"postData"+postData);
        BaseTestBean bean= JsonUtils.fromJson(postData,BaseTestBean.class);

        WebViewRequestJsonBean viewRequestJsonBean=new WebViewRequestJsonBean();
        viewRequestJsonBean.setWebviewParamJson(postData);
        viewRequestJsonBean.setRequestType(bean.getType());
        EventBus.getDefault().post(viewRequestJsonBean);

    }

    /**
     * 返回用户信息
     * @return
     */
    @android.webkit.JavascriptInterface
    public String appUserInfo(){
        return JsonUtils.toJson(BaseUserInfo.getUserInfo(prefs));
    }
}
