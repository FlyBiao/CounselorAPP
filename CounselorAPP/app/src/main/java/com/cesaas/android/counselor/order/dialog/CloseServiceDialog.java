package com.cesaas.android.counselor.order.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.custom.CustomServiceBean;
import com.cesaas.android.counselor.order.member.net.service.ColseServiceNet;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;

/**
 * Author FGB
 * Description 关闭服务dialog
 * Created at 2018/5/31 11:01
 * Version 1.0
 */

public class CloseServiceDialog extends Dialog implements View.OnClickListener {
    TextView tvCancel,tv_sure;
    MClearEditText et_service_remark;
    private int id;
    private Context mContext;

    public CloseServiceDialog(Context context, int id,Context ct) {
        this(context, R.style.dialog);
        this.id=id;
        this.mContext=ct;
    }

    public CloseServiceDialog(Context context, int dialog) {
        super(context, dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.dialog_close_service);
        et_service_remark= (MClearEditText) findViewById(R.id.et_service_remark);
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
        tvCancel= (TextView) findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(this);

    }

    public void mInitShow() {
        show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_sure:
                if(!TextUtils.isEmpty(et_service_remark.getText().toString())){
                    dismiss();
                    ColseServiceNet net=new ColseServiceNet(mContext);
                    net.setData(id,et_service_remark.getText().toString());
                }else{
                    ToastFactory.getLongToast(mContext,"请填写关闭服务理由！");
                }
                break;
        }
    }
}
