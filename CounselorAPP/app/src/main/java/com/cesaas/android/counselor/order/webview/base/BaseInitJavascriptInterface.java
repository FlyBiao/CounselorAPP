package com.cesaas.android.counselor.order.webview.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.flybiao.materialdialog.MaterialDialog;

/**
 * Author FGB
 * Description 初始化JavascriptInterface
 * Created at 2017/5/21 14:04
 * Version 1.0
 */

public class BaseInitJavascriptInterface {

    private static BaseJavascriptInterface javascriptInterface;

    public static void initJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backTyppe,int queryType,int ii){
        javascriptInterface = new BaseJavascriptInterface(mContext,mActivity,mMaterialDialog,mWebView,bundle,prefs,mTitle,mRightTitle,backTyppe,queryType,ii);
        mWebView.addJavascriptInterface(javascriptInterface, "appHome");
    }

    public static void initJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backTyppe,int shopTypes,int shopType1,int shopType2,int shopType3){
        javascriptInterface = new BaseJavascriptInterface(mContext,mActivity,mMaterialDialog,mWebView,bundle,prefs,mTitle,mRightTitle,backTyppe,shopTypes,shopType1,shopType2,shopType3);
        mWebView.addJavascriptInterface(javascriptInterface, "appHome");
    }

    public static void initJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backTyppe){
        javascriptInterface = new BaseJavascriptInterface(mContext,mActivity,mMaterialDialog,mWebView,bundle,prefs,mTitle,mRightTitle,backTyppe);
        mWebView.addJavascriptInterface(javascriptInterface, "appHome");
    }

    public static void initJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backTyppe,int type){
        javascriptInterface = new BaseJavascriptInterface(mContext,mActivity,mMaterialDialog,mWebView,bundle,prefs,mTitle,mRightTitle,backTyppe,type);
        mWebView.addJavascriptInterface(javascriptInterface, "appHome");
    }

    public static void initJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backTyppe,int type,int queryType,int ii){
        javascriptInterface = new BaseJavascriptInterface(mContext,mActivity,mMaterialDialog,mWebView,bundle,prefs,mTitle,mRightTitle,backTyppe,type,queryType,ii);
        mWebView.addJavascriptInterface(javascriptInterface, "appHome");
    }
}
