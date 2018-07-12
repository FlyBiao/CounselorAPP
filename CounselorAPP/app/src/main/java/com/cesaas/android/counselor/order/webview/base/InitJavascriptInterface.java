package com.cesaas.android.counselor.order.webview.base;

import android.content.Context;
import android.webkit.WebView;

import com.cesaas.android.counselor.order.utils.AbPrefsUtil;

/**
 * Author FGB
 * Description 初始化JavascriptInterface
 * Created at 2017/5/21 14:04
 * Version 1.0
 */

public class InitJavascriptInterface {

    private static JavascriptInterface javascriptInterface;
    public static AbPrefsUtil prefs;

    public static void initJavascriptInterface(WebView webView,Context mContext){
        prefs = AbPrefsUtil.getInstance();
        javascriptInterface = new JavascriptInterface(mContext,prefs);
        webView.addJavascriptInterface(javascriptInterface, "appHome");
    }
}
