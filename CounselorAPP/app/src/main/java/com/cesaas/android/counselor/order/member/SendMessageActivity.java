package com.cesaas.android.counselor.order.member;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgServiceBean;
import com.cesaas.android.counselor.order.member.net.SendSmsMsgNet;
import com.cesaas.android.counselor.order.member.net.service.SendMsgNet;
import com.cesaas.android.counselor.order.member.salestalk.SalesTalkListActivity;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivitys;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendMessageActivity extends BasesActivity {

    @BindView(R.id.etContent)
    EditText mEtTextNum;
    @BindView(R.id.ll_send_message)
    LinearLayout mLlSendMessage;
    @BindView(R.id.ll_base_title_back)
    LinearLayout mLlBaseTitleBack;
    @BindView(R.id.tv_base_title)
    TextView mTvBaseTitle;
    @BindView(R.id.tv_select_talk)
    TextView tv_select_talk;
    @BindView(R.id.tv_base_title_right)
    TextView tvRight;

    private String Tel;
    private String VipId;
    private String VipName;
    private int requestCode=10;

    private SendSmsMsgNet mSendSmsMsgNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);
        ButterKnife.bind(this);

        mTvBaseTitle.setText("短信发送");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("选择话术");

        Bundle bundle = getIntent().getExtras();
        Tel = bundle.getString("Tel");
        VipId=bundle.getString("VipId");
        VipName=bundle.getString("VipName");

    }

    @OnClick({R.id.ll_send_message,R.id.ll_base_title_back,R.id.tv_select_talk,R.id.tv_base_title_right})
    public void sendMessage(View v) {
        switch (v.getId()){
            case R.id.ll_send_message:
                if (!TextUtils.isEmpty(mEtTextNum.getText().toString())) {
                    SendMsgNet net=new SendMsgNet(mContext);
                    net.setData(Integer.parseInt(VipId),Tel,mEtTextNum.getText().toString());

                } else {
                    ToastFactory.getLongToast(mContext, "请输入短信内容！");
                }
                break;
            case R.id. ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                //Skip.mNext(mActivity, SalesTalkProviderActivity.class);
                Intent intent = new Intent(mContext, SalesTalkListActivity.class);
                intent.putExtra("Type",String.valueOf(0));
                startActivityForResult(intent, requestCode);
                break;
            case R.id.tv_select_talk:
                if(!TextUtils.isEmpty(mEtTextNum.getText().toString())){
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(mEtTextNum.getText().toString());
                    ToastFactory.getLongToast(mContext,"复制成功");
                }else{
                    ToastFactory.getLongToast(mContext,"找不到可复制的内容");
                }
                break;
        }
    }


    /**
     * 接收短信发送消息[暂时不用]
     *
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultSendMsgBean msg) {
        if (msg.IsSuccess == true) {
            Bundle bundle=new Bundle();
            bundle.putInt("Id",Integer.parseInt(VipId));
            bundle.putString("Name",VipName);
            bundle.putString("Phone",Tel);
            bundle.putString("sex","1");
            Skip.mNextFroData(mActivity, MemberServiceResultActivitys.class,bundle);

        } else {
            ToastFactory.getLongToast(mContext, "发送失败！" + msg.Message);
        }
    }

    /**
     * 接收短信发送消息
     *
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultSendMsgServiceBean msg) {
        if (msg.IsSuccess == true) {
            ToastFactory.getLongToast(mContext, "发送成功！");
           finish();
        } else {
            ToastFactory.getLongToast(mContext, "发送失败！" + msg.Message);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data!=null){
                String result=data.getStringExtra("result");
                mEtTextNum.setText(result);
            }
        }
    }

}
