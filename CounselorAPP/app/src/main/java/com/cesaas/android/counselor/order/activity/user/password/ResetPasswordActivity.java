package com.cesaas.android.counselor.order.activity.user.password;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.user.bean.ResetPasswordBusBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultResetPwdBean;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDataPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.MD5;
import com.cesaas.android.counselor.order.utils.NetworkUtil;
import com.cesaas.android.counselor.order.utils.OtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import io.rong.eventbus.EventBus;

/**
 * 重置密码
 */
public class ResetPasswordActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout mBaseBack;
    private TextView mBaseTitle;
    private MClearEditText et_new_pwd,et_again_check_new_pwd;
    private ImageView iv_check_new_pwd,iv_again_check_new_pwd;
    private Button btn_submit_reset;

    private ResetPasswordNet resetPasswordNet;

    private String mobile;
    private String code;
    private String registerAuthorization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mobile=bundle.getString("mobile");
            code=bundle.getString("code");
            registerAuthorization=bundle.getString("registerAuthorization");
        }

        initView();
        initData();
    }

    private void initData(){

    }

    private void initView() {

        mBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        mBaseTitle.setText("重置账号密码");
        et_new_pwd= (MClearEditText) findViewById(R.id.et_new_pwd);
        et_again_check_new_pwd= (MClearEditText) findViewById(R.id.et_again_check_new_pwd);
        iv_check_new_pwd= (ImageView) findViewById(R.id.iv_check_new_pwd);
        iv_again_check_new_pwd= (ImageView) findViewById(R.id.iv_again_check_new_pwd);
        btn_submit_reset= (Button) findViewById(R.id.btn_submit_reset);

        mBaseBack.setOnClickListener(this);
        iv_check_new_pwd.setOnClickListener(this);
        iv_again_check_new_pwd.setOnClickListener(this);
        btn_submit_reset.setOnClickListener(this);
    }

    public void onEventMainThread(ResultResetPwdBean msg) {
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"重置密码成功！");
            bundle.putString("Mobile",mobile);
            //返回登陆页面
            Skip.mNextFroData(mActivity, LoginActivity.class,bundle);
        }else{
            ToastFactory.getLongToast(mContext,"重置密码失败！"+msg.Message);
        }
    }

    /**
     * 接收Header消息
     * @param msg
     */
    public void onEventMainThread(ResetPasswordBusBean msg) {
        if(msg.isCheck()==true && msg.getIsNew()==0){
        }else{

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.iv_check_new_pwd://查看密码明文
                ResetPasswordBusBean passwordBusBean=new ResetPasswordBusBean();
                passwordBusBean.setCheck(true);
                passwordBusBean.setIsNew(0);
                EventBus.getDefault().post(passwordBusBean);
                break;

            case R.id.iv_again_check_new_pwd://再一次查看密码明文
                ResetPasswordBusBean resetPasswordBusBean=new ResetPasswordBusBean();
                resetPasswordBusBean.setCheck(true);
                resetPasswordBusBean.setIsNew(1);
                EventBus.getDefault().post(resetPasswordBusBean);
                break;

            case R.id.btn_submit_reset://提交
                if (OtherUtil.passwordVerify(mContext, et_new_pwd.getText().toString())) {
                    if(OtherUtil.passwordVerify(mContext, et_again_check_new_pwd.getText().toString())){
                        if(et_new_pwd.getText().toString().equals(et_again_check_new_pwd.getText().toString())){
                            //执行重置密码操作
                            resetPasswordNet=new ResetPasswordNet(mContext);
                            resetPasswordNet.setData(code,et_again_check_new_pwd.getText().toString(),mobile);
                        }else{
                            ToastFactory.getLongToast(mContext,"两次密码不一致！");
                        }
                    }
                }
                break;
        }

    }


    /**
     * Author FGB
     * Description 重置密码
     * Created at 2017/5/14 16:35
     * Version 1.0
     */

    public class ResetPasswordNet extends BaseNet {
        public ResetPasswordNet(Context context) {
            super(context, true);
            this.uri="User/Sw/Regist/Reset";
        }

        public void setData(String Code,String PassWord,String Mobile){
            try{
                data.put("Code",Code);
                data.put("PassWord",new MD5().toMD5(PassWord));
                data.put("Mobile",Mobile);
                data.put("UserTicket",
                        AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }

            mPostNet();
        }


        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultResetPwdBean bean= JsonUtils.fromJson(rJson,ResultResetPwdBean.class);
            EventBus.getDefault().post(bean);
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }



    /**
     * 网络请求基类
     *
     * @author Evan
     *
     */
    public class BaseNet {

        public Context mContext;
        protected String uri;
        protected JSONObject data;
        private boolean ishow;
        private WaitDialog dialog;
        protected AbDataPrefsUtil abData;
        private StringBuffer auth;

        public BaseNet(Context context, boolean show) {
            this.mContext = context;
            data = new JSONObject();
            abData = AbDataPrefsUtil.getInstance();
            this.ishow = show;
            if (show)
                dialog = new WaitDialog(context);
        }

        /**
         * 获取授权
         * @return
         */
        public String getToken() {
            StringBuffer sbuf = new StringBuffer();
            sbuf.append(AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN))
                    .append("SW-AppAuthorizationToken")
                    .append(AbPrefsUtil.getInstance().getString(Constant.SPF_TIME, ""));
            return new MD5().toMD5(sbuf.toString());
        }

        /**
         * 开始请求网络
         */
        public void mPostNet() {
            if (App.mInstance().getNetType() != NetworkUtil.NO_NETWORK) {
                RequestParams params = new RequestParams();
                try {
                    params.setBodyEntity(new StringEntity(data.toString(), "UTF-8"));
                    params.addHeader("Content-Type", "application/json");
                    auth= new StringBuffer();
                    auth.append(registerAuthorization);
                    params.addHeader("Authorization",auth.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("onSuccess","请求Url："+ Constant.IP + uri + "\n" + data+ "\n" +auth.toString());

                HttpUtils http = new HttpUtils();
                http.send(HttpRequest.HttpMethod.POST, Constant.IP + uri, params, new RequestCallBack<HttpUtils>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        if (isUploading) {
                        } else {
                        }
                    }

                    //加载刷新
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (ishow && dialog != null)
                            dialog.show();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        try {
                            if (ishow && dialog != null)
                                dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mFail(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<HttpUtils> responseInfo) {
                        Log.i("onSuccess",
                                responseInfo.result
                                        +"\n"+"statusCode:"+responseInfo.statusCode
                                        +"\n"+"contentType:"+responseInfo.contentType
                                        +"\n"+"contentEncoding:"+responseInfo.contentEncoding
                                        +"\n"+"contentLength:"+responseInfo.contentLength
                                        +"\n"+"reasonPhrase:"+responseInfo.reasonPhrase
                                        +"\n"+"hashCode:"+responseInfo.hashCode()
                                        +"\n"+"locale:"+responseInfo.locale
                                        +"\n"+"protocolVersion:"+responseInfo.protocolVersion
                                        +"\n"+"getAllHeaders:"+responseInfo.getAllHeaders()
                                        +"\n"+"responseInfo:"+responseInfo
                                        +"\n"+"responseInfo:"+responseInfo);

                        try {
                            if (ishow && dialog != null)
                                dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mSuccess(responseInfo.result + "");
                    }
                });
            } else {
                ToastFactory.show(App.mInstance(), "未Intent网络，请检查后重试！", ToastFactory.CENTER);
            }
        }


        protected void mSuccess(String rJson) {
        }

        protected void mFail(HttpException e, String err) {
            LogUtils.d(Constant.TAG+ err+"=e"+e);
            Log.i(Constant.TAG,"\n\n" +e+"err："+err);
            ToastFactory.show(mContext, "服务器连接或返回错误！", ToastFactory.CENTER);
        }

    }


}
