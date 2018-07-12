package com.cesaas.android.testzuofei;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.bean.LoginBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.LoginNet;
import com.cesaas.android.counselor.order.utils.AbAppUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbStrUtil;
import com.cesaas.android.counselor.order.utils.MaxLengthWatcher;
import com.cesaas.android.counselor.order.utils.OtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author FGB
 * Description
 * Created 2017/4/26 17:57
 * Version 1.0
 */
public class TestLoginActivity extends BasesActivity {


    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.et_mobile)
    EditText mEtMobile;
    @BindView(R.id.et_password)
    EditText mEtPassword;
//    @BindView(R.id.iv_show_pwd)
//    ImageView mIvShowPwd;

    private String user;
    private String pwd;

    private boolean isShowPwd=false;

    private LoginNet mLoginNet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.test_activity_login);

        initData();
        // SPF_ISLOGIN:是否登录
        if (AbPrefsUtil.getInstance().getBoolean(Constant.SPF_ISLOGIN)) {
            // 已登录 跳转到主页
//			Skip.mNext(mActivity, MainActivity.class, true);
            Skip.mNext(mActivity, HomeActivity.class,true);

//			Skip.mNext(mActivity,HomeActivity.class,true);
        }

    }


    private void initData() {
        //获取已登录的账号
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mEtMobile.setText(bundle.getString("Mobile"));
        }


        // 控制editText输入的手机号长度【注：13是因为把空格也算进去了】
        mEtMobile.addTextChangedListener(new MaxLengthWatcher(13,
                mEtMobile));

        AbStrUtil.editTextFilterChinese(mEtPassword);
        mEtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                OtherUtil.formatPhoneNum(mEtMobile, s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEtPassword.setText("");
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (OtherUtil.isShouldHideInput(v, ev)) {
                AbAppUtil.hideSoftInput(mContext);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    @OnClick({R.id.btn_login})
    public void login(View v) {

        switch (v.getId()){
            case R.id.btn_login:
                user = mEtMobile.getText().toString().trim();
                pwd = mEtPassword.getText().toString().trim();
                if (OtherUtil.phoneVerify(mContext, user)) {
                    if (OtherUtil.passwordVerify(mContext, pwd)) {

                        mLoginNet = new LoginNet(mContext, user, pwd);
                        mLoginNet.mPostNet();
                    }
                }

                break;

        }

    }


    /**
     * 接收登录结果消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(final LoginBean msg) {

        if(msg.IsSuccess==true){
            myapp.mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    String tel = mEtMobile.getText().toString().replace(" ", "");
                    prefs.putBoolean(Constant.SPF_ISLOGIN, msg.IsSuccess);

                    if (msg.TModel != null)
                        prefs.putString(Constant.SPF_TOKEN,msg.TModel.UserTicket);
                    prefs.putString(Constant.SPF_ACCOUNT, tel);
                    prefs.putString(Constant.SPF_TIME,String.valueOf(System.currentTimeMillis()));

                    // 登录成功后跳转到MainActivity
                    Skip.mNext(TestLoginActivity.this, HomeActivity.class);

                    TestLoginActivity.this.finish();

                    try {

                        BasesActivity.activityList.remove(TestLoginActivity.this);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    overridePendingTransition(R.anim.activity_slide_in_right,
                            R.anim.activity_no_anim);
                }
            });

        }
    }
}
