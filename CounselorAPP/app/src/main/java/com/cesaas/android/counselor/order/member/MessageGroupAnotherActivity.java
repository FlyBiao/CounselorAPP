package com.cesaas.android.counselor.order.member;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.ResultGetSendDetailBean;
import com.cesaas.android.counselor.order.member.bean.ResultSendMessageBean;
import com.cesaas.android.counselor.order.member.net.GetMsgSendDetailNet;
import com.cesaas.android.counselor.order.member.net.SendSmsMsgNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.StringSplitUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 再发一次短信群发
 */
public class MessageGroupAnotherActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack,ll_add_group,ll_send_message;
    private TextView tvBaseTitle,tvBaseRightTitle,tv_group_number;
    private EditText etContent;

    private SendSmsMsgNet mSendSmsMsgNet;
    private GetMsgSendDetailNet mGetMsgSendDetailNet;
    private int MsgId;

    private String messageContent;
    private String messageMobiles;
    private List<String> mobiles=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group_another);

        Bundle bundle=getIntent().getExtras();
        MsgId=bundle.getInt("MsgId");

        mGetMsgSendDetailNet=new GetMsgSendDetailNet(mContext);
        mGetMsgSendDetailNet.setData(MsgId);

        initView();
    }

    private void initView() {
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("短信群发");
        tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRightTitle.setVisibility(View.VISIBLE);
        tvBaseRightTitle.setText("短信记录");
        tv_group_number= (TextView) findViewById(R.id.tv_group_number);
        ll_add_group= (LinearLayout) findViewById(R.id.ll_add_group);
        ll_add_group.setOnClickListener(this);
        ll_send_message= (LinearLayout) findViewById(R.id.ll_send_message);
        ll_send_message.setOnClickListener(this);
        etContent= (EditText) findViewById(R.id.etContent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_add_group:
                Skip.mNext(mActivity,MemberListActivity.class);
                break;
            case R.id.ll_send_message:
                messageContent=etContent.getText().toString();
                if(messageContent!=null){
                    mSendSmsMsgNet=new SendSmsMsgNet(mContext);
                    mSendSmsMsgNet.setData("",messageContent,messageMobiles);
                }else{
                    ToastFactory.getLongToast(mContext,"请输入短信内容!");
                }

                break;
        }
    }

    public void onEventMainThread(ResultGetSendDetailBean msg){
        if(msg.IsSuccess==true){
            etContent.setText(msg.TModel.getTitle());
            tv_group_number.setText(msg.TModel.Fans.size()+"");

            for (int i=0;i<msg.TModel.Fans.size();i++){
                mobiles.add("15889583639");
            }
            messageMobiles=StringSplitUtils.stringSplite(mobiles);

        }else{
            ToastFactory.getLongToast(mContext,"获取短信详情失败！"+msg.Message);
        }

    }

    /**
     * 接受Fans List发送消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultSendMessageBean msg){
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"群发短信成功!");
            //返回短信列表页面
            Skip.mNext(mActivity,MessageListActivity.class);
        }else{
            ToastFactory.getLongToast(mContext,"群发短信失败!");
        }

    }
}
