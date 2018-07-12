package com.cesaas.android.counselor.order.member;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultMemberApplyAgreeBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultSendMsgBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.net.SendSmsMsgNet;
import com.cesaas.android.counselor.order.member.net.service.SendMsgNet;
import com.cesaas.android.counselor.order.member.net.service.SendSmsmsgsNet;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivitys;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupSendMessageActivity extends BasesActivity {

    @BindView(R.id.etContent)
    EditText mEtTextNum;
    @BindView(R.id.ll_send_message)
    LinearLayout mLlSendMessage;
    @BindView(R.id.ll_base_title_back)
    LinearLayout mLlBaseTitleBack;
    @BindView(R.id.tv_base_title)
    TextView mTvBaseTitle;

    private SendSmsmsgsNet sendSmsmsgsNet;
    private List<MemberServiceListBean> memberServiceList;
    private String mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message2);
        ButterKnife.bind(this);

        mTvBaseTitle.setText("短信发送");

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            memberServiceList= (List<MemberServiceListBean>) bundle.getSerializable("MemberList");
        }
    }

    @OnClick({R.id.ll_send_message,R.id.ll_base_title_back})
    public void sendMessage(View v) {
        switch (v.getId()){
            case R.id.ll_send_message:
                if (!TextUtils.isEmpty(mEtTextNum.getText().toString())) {
                    StringBuffer buf = new StringBuffer();
                    for (int i=0;i<memberServiceList.size();i++){
                        mobiles = memberServiceList.get(i).getMobile();
                        if(mobiles!=null){
                            buf.append(mobiles).append(",");
                        }
                    }
                    String str=buf.substring(0,buf.length()-1);
                    sendSmsmsgsNet=new SendSmsmsgsNet(mContext);
                    sendSmsmsgsNet.setData("群发短信发送",mEtTextNum.getText().toString(),str);

                } else {
                    ToastFactory.getLongToast(mContext, "请输入短信内容！");
                }
                break;

            case R.id. ll_base_title_back:
                Skip.mBack(mActivity);
                break;
        }
    }


    /**
     * 接收短信发送消息
     *
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultMemberApplyAgreeBean msg) {
        if (msg.IsSuccess == true) {
            finish();
            ToastFactory.getLongToast(mContext, "发送成功！");

        } else {
            ToastFactory.getLongToast(mContext, "发送失败！" + msg.Message);
        }
    }

}
