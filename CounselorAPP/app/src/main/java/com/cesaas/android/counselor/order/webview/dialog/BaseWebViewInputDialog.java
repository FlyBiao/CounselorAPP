package com.cesaas.android.counselor.order.webview.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.webview.resultbean.PromptInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptValueBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultPromptInfoBean;

import org.json.JSONArray;

/**
 * Author FGB
 * Description
 * Created at 2017/5/27 11:34
 * Version 1.0
 */

public class BaseWebViewInputDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Button btn_confirm_add_return_visit,btn_cancel;
    private EditText et_return_visit_content;
    private TextView tv_dialog_title;
    private String dialogTitle;
    private WebView webView;

    public BaseWebViewInputDialog(Context context, Activity activity,WebView webView, String dialogTitle) {
        this(context, R.style.dialog);
        this.activity=activity;
        this.dialogTitle=dialogTitle;
        this.webView=webView;
        initView();
    }

    public BaseWebViewInputDialog(Context context, int dialog) {
        super(context, dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.add_return_visit_dialog);

        initView();
    }

    public void initView(){
        btn_confirm_add_return_visit=(Button) findViewById(R.id.btn_confirm_add_return_visit);
        et_return_visit_content=(EditText) findViewById(R.id.et_return_visit_content);
        tv_dialog_title= (TextView) findViewById(R.id.tv_dialog_title);
        btn_cancel= (Button) findViewById(R.id.btn_cancel);
        tv_dialog_title.setText(dialogTitle);
        btn_confirm_add_return_visit.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    public void mInitShow() {
        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_add_return_visit://确定添加

                PromptValueBean promptValueBean=new PromptValueBean();
                promptValueBean.setValue(et_return_visit_content.getText().toString());

                JSONArray jsonArray=new JSONArray();
                jsonArray.put(promptValueBean.getPromptValue());

                PromptInfoBean promptInfoBean=new PromptInfoBean();
                promptInfoBean.setOk(1);
                promptInfoBean.setItems(jsonArray);

                final ResultPromptInfoBean resultPromptInfoBean=new ResultPromptInfoBean();
                resultPromptInfoBean.setType(103);
                resultPromptInfoBean.setParam(promptInfoBean.getPromptInfo());
                Log.i(Constant.TAG,"getPromptInfo:"+resultPromptInfoBean.getPromptInfoResult());

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 调用js中的方法实现
                         */
                        webView.loadUrl("javascript:appCallback('"+resultPromptInfoBean.getPromptInfoResult()+"')");
                    }
                });

                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;

        }
    }

}
