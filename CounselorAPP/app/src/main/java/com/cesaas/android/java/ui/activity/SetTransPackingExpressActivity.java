package com.cesaas.android.java.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.bean.inventory.ResultDeliverySetExpressBean;
import com.cesaas.android.java.net.DeliverySetExpressNet;

/**
 * Author FGB
 * Description 设置调拨装箱单物流
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class SetTransPackingExpressActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tv_set_complete,tv_no,tv_box_no;
    private MClearEditText et_expressName,et_expressNo,et_expressRemark;
    private LinearLayout llBack;

    private long boxId;
    private int boxNo;
    private String no;
    private int type;

    private DeliverySetExpressNet deliverySetExpressNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_packing_express_set);
        Bundle bundle=getIntent().getExtras();
        boxId=bundle.getLong("boxId");
        no=bundle.getString("no");
        boxNo=bundle.getInt("boxNo");
        type=bundle.getInt("netType");

        initView();
        initData();
    }


    /**
     * 接收 设置物流
     * @param msg
     */
    public void onEventMainThread(ResultDeliverySetExpressBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "设置物流成功");
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    private void initView() {
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("调拨装箱单快递设置");
        tv_set_complete= (TextView) findViewById(R.id.tv_set_complete);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_box_no= (TextView) findViewById(R.id.tv_box_no);
        tv_set_complete.setOnClickListener(this);
        et_expressName= (MClearEditText) findViewById(R.id.et_expressName);
        et_expressNo= (MClearEditText) findViewById(R.id.et_expressNo);
        et_expressRemark= (MClearEditText) findViewById(R.id.et_expressRemark);
    }

    public void initData() {
        tv_no.setText(no);
        tv_box_no.setText(String.valueOf(boxNo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_set_complete:
                if(!TextUtils.isEmpty(et_expressName.getText().toString())){
                    if(!TextUtils.isEmpty(et_expressNo.getText().toString())){
                        deliverySetExpressNet=new DeliverySetExpressNet(mContext,type);
                        deliverySetExpressNet.setData(boxId,et_expressName.getText().toString(),et_expressNo.getText().toString(),et_expressRemark.getText().toString());
                    }else{
                        ToastFactory.getLongToast(mContext,"请输入快递单号");
                    }
                }else{
                    ToastFactory.getLongToast(mContext,"请输入快递公司名称");
                }
                break;
        }
    }

}
