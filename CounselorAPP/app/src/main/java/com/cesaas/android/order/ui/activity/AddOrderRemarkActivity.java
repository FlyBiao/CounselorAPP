package com.cesaas.android.order.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultOrderBackBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgBean;
import com.cesaas.android.counselor.order.member.net.SendSmsMsgNet;
import com.cesaas.android.counselor.order.member.net.service.SendMsgNet;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivitys;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.net.OrderBackNetTest;
import com.cesaas.android.order.ui.fragment.WaitOutNewOrderStateView;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加订单备注
 */
public class AddOrderRemarkActivity extends BasesActivity {

    @BindView(R.id.tv_order_title)
    TextView tv_order_title;
    @BindView(R.id.tv_sure_add)
    TextView tv_sure_add;
    @BindView(R.id.etContent)
    EditText mEtTextNum;
    @BindView(R.id.ll_send_message)
    LinearLayout mLlSendMessage;
    @BindView(R.id.ll_base_title_back)
    LinearLayout mLlBaseTitleBack;
    @BindView(R.id.tv_base_title)
    TextView mTvBaseTitle;

    private String OrderId;
    private String OrderType;

    private OrderBackNetTest orderBackNetTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        OrderId = intent.getStringExtra("OrderId");
        OrderType = intent.getStringExtra("OrderType");

        if(OrderType.equals("1000")){
            mTvBaseTitle.setText("退单备注");
            tv_order_title.setText("填写备注");
            mEtTextNum.setHint("请输入退单备注说明！");
            tv_sure_add.setText("确认退单");
        }
        else if(OrderType.equals("1002")){
            mTvBaseTitle.setText("拒绝接单备注");
            tv_order_title.setText("填写备注");
            mEtTextNum.setHint("请输入拒绝接单备注说明！");
            tv_sure_add.setText("拒绝接单");
        }

    }

    @OnClick({R.id.ll_send_message,R.id.ll_base_title_back})
    public void sendMessage(View v) {
        switch (v.getId()){
            case R.id.ll_send_message:
                if (!TextUtils.isEmpty(mEtTextNum.getText().toString())) {
                    if(OrderType.equals("1000")){
                        orderBackNetTest=new OrderBackNetTest(mContext);
                        orderBackNetTest.setData(Integer.parseInt(OrderId),mEtTextNum.getText().toString());
                    }else if(OrderType.equals("1002")){

                    }
                } else {
                    ToastFactory.getLongToast(mContext, "请输入备注说明！");
                }
                break;
            case R.id. ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }

    public void onEventMainThread(ResultOrderBackBean msg) {
        if(msg.IsSuccess!=false){
            ToastFactory.getLongToast(mContext,"退单成功！");
            Intent mIntent = new Intent();
            mIntent.putExtra("OrderId",OrderId);
            setResult(Constant.ORDER_BACK, mIntent);
            finish();
        }else{
            ToastFactory.getLongToast(mContext,"退单失败！"+ msg.Message);
        }
    }

}
