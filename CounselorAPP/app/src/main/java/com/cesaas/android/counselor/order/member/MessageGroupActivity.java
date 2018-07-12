package com.cesaas.android.counselor.order.member;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseActivity;
import com.cesaas.android.counselor.order.bean.ResultFans;
import com.cesaas.android.counselor.order.bean.SelectFans;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultSendMessageBean;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.member.net.SendSmsMsgNet;
import com.cesaas.android.counselor.order.salestalk.activity.SelectSalesTalkActivity;
import com.cesaas.android.counselor.order.salestalk.activity.SelectVerbalTrickActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.StringSplitUtils;
import com.cesaas.android.counselor.order.utils.StringToArrayUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信群发
 */
public class MessageGroupActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack,ll_add_group,ll_send_message;
    private TextView tvBaseTitle,tvBaseRightTitle,tv_sel_name,tv_group_number,tv_select_template;
    private EditText etContent;

    private int requestCode=200;
    private int requestVipCode=201;

    private SendSmsMsgNet mSendSmsMsgNet;

    private String title;
    private String messageContent;
    private String messageMobiles;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group2);

        initView();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            content=bundle.getString("content");
            etContent.setText(content);
        }
    }

    private void initView() {
        tv_sel_name= (TextView) findViewById(R.id.tv_sel_name);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("短信群发");
        tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRightTitle.setVisibility(View.GONE);
        tvBaseRightTitle.setText("短信记录");
        tv_group_number= (TextView) findViewById(R.id.tv_group_number);
        ll_add_group= (LinearLayout) findViewById(R.id.ll_add_group);
        ll_add_group.setOnClickListener(this);
        ll_send_message= (LinearLayout) findViewById(R.id.ll_send_message);
        ll_send_message.setOnClickListener(this);
        etContent= (EditText) findViewById(R.id.etContent);
        tv_select_template= (TextView) findViewById(R.id.tv_select_template);
        tv_select_template.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_add_group:
//                Skip.mNext(mActivity,MemberListActivity.class);
//                Skip.mActivityResult(mActivity,MemberListActivity.class,requestVipCode);
                Skip.mActivityResult(mActivity,VipListActivity.class,requestVipCode);
                break;
            case R.id.ll_send_message:
                messageContent=etContent.getText().toString();
                if(messageContent!=null){
                    mSendSmsMsgNet=new SendSmsMsgNet(mContext);
                    mSendSmsMsgNet.setData(title,messageContent,messageMobiles);
                }else{
                    ToastFactory.getLongToast(mContext,"请输入短信内容!");
                }
                break;
            case R.id.tv_select_template:
//                Skip.mActivityResult(mActivity,SelectSalesTalkActivity.class,requestCode);
                Skip.mActivityResult(mActivity,SelectVerbalTrickActivity.class,requestCode);
                break;
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

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==200){
            if(data!=null){
                String result = data.getStringExtra("content");
                etContent.setText(result);
            }
        }
        if(requestCode==201){
            if(data!=null){
                List<SelectVipListBean> selectFansList=(ArrayList<SelectVipListBean>)data.getSerializableExtra("selectList");
                tv_group_number.setText(""+selectFansList.size());
                List<String> mobiles=new ArrayList<>();
                List<String> names=new ArrayList<>();
                for (int i=0;i<selectFansList.size();i++){
                    mobiles.add(selectFansList.get(i).getMobile());
                    names.add(selectFansList.get(i).getNickName());
                }
                messageMobiles=StringSplitUtils.stringSplite(mobiles);
                title=StringSplitUtils.stringSplite(names);
                tv_sel_name.setText(title);
            }
        }
    }
}
