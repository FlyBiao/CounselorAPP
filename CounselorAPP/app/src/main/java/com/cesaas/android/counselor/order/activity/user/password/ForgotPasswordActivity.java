package com.cesaas.android.counselor.order.activity.user.password;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.bean.RegisterAuthorizationBean;
import com.cesaas.android.counselor.order.activity.user.net.SendResetCodeNet;
import com.cesaas.android.counselor.order.activity.user.register.RegisterActivity;
import com.cesaas.android.counselor.order.net.SendCodeNet;
import com.cesaas.android.counselor.order.utils.OtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;


/**
 * 忘记密码页面
 */
public class ForgotPasswordActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout mBaseBack;
    private TextView mBaseTitle;
    private Button btn_next_step,btn_get_auth_code;
    private MClearEditText et_user_mobile,et_user_auth_code;

    private TimeCount time;
    private SendResetCodeNet mSendResetCodeNet;
    private String registerAuthorization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initView();
        initData();
    }
    private void initData(){
    }


    /**
     * 接收Header消息
     * @param msg
     */
    public void onEventMainThread(RegisterAuthorizationBean msg) {
        String authorizationValue=msg.getAuthorization();
        registerAuthorization=authorizationValue.replace("Authorization: ","");
    }



    private void initView() {

        mBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        mBaseTitle.setText("忘记密码");
        btn_next_step= (Button) findViewById(R.id.btn_next_step);
        btn_get_auth_code= (Button) findViewById(R.id.btn_get_auth_code);
        et_user_mobile= (MClearEditText) findViewById(R.id.et_user_mobile);
        et_user_auth_code= (MClearEditText) findViewById(R.id.et_user_auth_code);

        mBaseBack.setOnClickListener(this);
        btn_next_step.setOnClickListener(this);
        btn_get_auth_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.btn_get_auth_code://获取短信验证码
                if(OtherUtil.phoneVerify(mContext, et_user_mobile.getText().toString())){
                    time = new TimeCount(60000, 1000);
                    time.start();
                    //执行获取验证码操作
                    mSendResetCodeNet=new SendResetCodeNet(mContext);
                    mSendResetCodeNet.setData(et_user_mobile.getText().toString());
                }
                break;

            case R.id.btn_next_step://下一步
                if(OtherUtil.phoneVerify(mContext, et_user_mobile.getText().toString())){
                    if(!TextUtils.isEmpty(et_user_auth_code.getText().toString())){
                        bundle.putString("mobile",et_user_mobile.getText().toString());
                        bundle.putString("code",et_user_auth_code.getText().toString());
                        bundle.putString("registerAuthorization",registerAuthorization);
                        Skip.mNextFroData(mActivity,ResetPasswordActivity.class,bundle);
                    }else{
                        ToastFactory.getLongToast(mContext,"请输入短信验证码");
                    }
                }

                break;
        }

    }

    /**
     * 定时发送
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_get_auth_code.setBackgroundResource(R.drawable.button_ellipse_huise_bg);
            btn_get_auth_code.setClickable(false);
            btn_get_auth_code.setText("("+millisUntilFinished / 1000 +") 秒后重新发送");
        }

        @Override
        public void onFinish() {
            btn_get_auth_code.setText("重新获取验证码");
            btn_get_auth_code.setClickable(true);
            btn_get_auth_code.setBackgroundResource(R.drawable.button_ellipse_blue_bg);

        }
    }
}
