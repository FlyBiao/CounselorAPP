package com.cesaas.android.counselor.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

/**
 * Author FGB
 * Description 自定义文本编辑查询对话框
 * Created 2017/3/3 14:31
 * Version 1.zero
 */
public class CustomTextQueryDialog extends Dialog implements View.OnClickListener{

    private TextView title_tv;
    private Button dialog_cancel_btn,dialog_sure_btn;

    private ConfirmListener sureInface;
    private CancelListener canaelInface;

    public CustomTextQueryDialog(Context context) {
        this(context, R.style.dialog);
    }

    public CustomTextQueryDialog(Context context, int theme) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.simple_tips_text_query_dialog);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        dialog_cancel_btn = (Button) findViewById(R.id.dialog_cancel_btn);
        dialog_sure_btn = (Button) findViewById(R.id.dialog_sure_btn);
        dialog_cancel_btn.setOnClickListener(this);
        dialog_sure_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancel_btn:
                if (canaelInface != null)
                    canaelInface.onClick(this);
                else
                    mDismiss();
                break;
            case R.id.dialog_sure_btn:
                if (sureInface != null) {
                    sureInface.onClick(this);
                }
                mDismiss();
                break;
        }
    }

    /**
     * 显示Dialog对话框
     * @param title
     * @param ok
     * @param cbtn
     * @param sure
     * @param cancel
     */
    public void mInitShow(String title,String ok, String cbtn, CustomTextQueryDialog.ConfirmListener sure,
                          CustomTextQueryDialog.CancelListener cancel) {
        if (title == null)
            title_tv.setText(getContext().getResources().getString(R.string.tips_titles));
        else
            title_tv.setText(title);
        dialog_sure_btn.setText(ok);
        dialog_cancel_btn.setText(cbtn);
        this.sureInface = sure;
        this.canaelInface = cancel;
        show();
    }

    public interface ConfirmListener {
        public void onClick(Dialog dialog);
    }

    public interface CancelListener {
        public void onClick(Dialog dialog);
    }

    public void mDismiss() {
        cancel();
    }
}
