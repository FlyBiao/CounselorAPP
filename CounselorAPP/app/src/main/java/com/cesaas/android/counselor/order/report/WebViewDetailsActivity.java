package com.cesaas.android.counselor.order.report;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.AbDateUtil;

import java.io.FileOutputStream;


/**
 * 业绩分配
 */
public class WebViewDetailsActivity extends FragmentActivity {
    private Button btn_capture;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_capture);
        btn_capture = (Button) findViewById(R.id.btn_capture);
        webView = (WebView) findViewById(R.id.wv_webview);
        initWebview();
        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSnapshot();
            }
        });
    }

    private void initWebview() {

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true); //支持缩放
        webView.requestFocusFromTouch();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://sw.cesaas.com:84/merchant/submit/reportDetail?id=16178&d=2017/10/19&name=undefined");
    }

    private void getSnapshot() {
        Picture picture = webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        if (width > 0 && height > 0) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            picture.draw(canvas);
            try {
                String fileName = Environment.getExternalStorageDirectory().getPath()+"/"+ AbDateUtil.getStringDateShort()+".jpg";
                FileOutputStream fos = new FileOutputStream(fileName);
                //压缩bitmap到输出流中
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                Toast.makeText(WebViewDetailsActivity.this, "截屏成功"+fileName, Toast.LENGTH_LONG).show();
                bitmap.recycle();
            } catch (Exception e) {
//                Log.e(TAG, e.getMessage());
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK)&&webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
