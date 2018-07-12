package com.cesaas.android.counselor.order.activity.user;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 消息页面
 */
public class MessageActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBack;
    private LinearLayout ll_system_msg,ll_express_msg,ll_task_msg;
    private TextView mTvTitle;
    private TextView tv_system_msg,tv_express_msg,tv_task_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
    }

    private void initView() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");//记得加上这句
        mTvTitle= (TextView) findViewById(R.id.tv_base_title);
        mTvTitle.setText("消息");
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        ll_system_msg= (LinearLayout) findViewById(R.id.ll_system_msg);
        ll_express_msg= (LinearLayout) findViewById(R.id.ll_express_msg);
        ll_task_msg= (LinearLayout) findViewById(R.id.ll_task_msg);
        llBack.setOnClickListener(this);
        ll_system_msg.setOnClickListener(this);
        ll_express_msg.setOnClickListener(this);
        ll_task_msg.setOnClickListener(this);

        tv_system_msg= (TextView) findViewById(R.id.tv_system_msg);
        tv_express_msg= (TextView) findViewById(R.id.tv_express_msg);
        tv_task_msg= (TextView) findViewById(R.id.tv_task_msg);
        tv_system_msg.setTypeface(font);
        tv_express_msg.setTypeface(font);
        tv_task_msg.setTypeface(font);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.ll_system_msg:
                bundle.putInt("NotifyType",2);
                Skip.mNextFroData(mActivity,MessageDetailActivity.class,bundle);
                break;
            case R.id.ll_express_msg:
                bundle.putInt("NotifyType",3);
                Skip.mNextFroData(mActivity,MessageDetailActivity.class,bundle);
                break;
            case R.id.ll_task_msg:
                bundle.putInt("NotifyType",1);
                Skip.mNextFroData(mActivity,MessageDetailActivity.class,bundle);
                break;
        }
    }
}
