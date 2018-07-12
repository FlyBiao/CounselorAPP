package com.cesaas.android.counselor.order.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cesaas.android.counselor.order.dialog.WaitDialog;

/**
 * Author FGB
 * Description webview工具类
 * Created 2017/3/27 16:45
 * Version 1.zero
 */
public class WebViewUtils {

    /**
     * 初始化webview基础设置
     * @param wv
     * @param dialog
     * @param url
     */
    public static void initWebSettings(WebView wv ,final WaitDialog dialog,String url){
        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);// 代表设置支持JS
        settings.setBuiltInZoomControls(true);// 在网页中显示放大缩小按钮
        settings.setUseWideViewPort(true);// 开启支持双击网页进行缩放
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv.requestFocusFromTouch();
        settings.setAllowFileAccess(true);// 设置允许访问文件数据
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setSupportZoom(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
//        mWebView.getSettings().setPluginsEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        //这一句是设置js 对话框
        wv.setWebChromeClient(new WebChromeClient());
        //不加上，会显示白边
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        wv.setWebViewClient(new WebViewClient(){
            /**
             * 所有跳转的链接都会在此方法中调用
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            /**
             * 网页开始加载
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.mStart();
            }

            /**
             * 网页加载结束
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.mStop();
            }

        });

        wv.loadUrl(url);// 加载网页
    }

    /**
     * 下拉刷新
     */
    public static void initSwipeRefreshLayout(final SwipeRefreshLayout swipeRefreshLayout,final WebView view){
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        //设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
//          	  dialog.mStop();
                //下拉重新加载
                view.reload();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
