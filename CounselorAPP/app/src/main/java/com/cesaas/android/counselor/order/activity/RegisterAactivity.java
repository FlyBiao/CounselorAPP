package com.cesaas.android.counselor.order.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultRegistCompanyBean;
import com.cesaas.android.counselor.order.bean.ResultSendCodeBean;
import com.cesaas.android.counselor.order.custom.TimeButton;

import com.cesaas.android.counselor.order.net.RegistCompanyNet;
import com.cesaas.android.counselor.order.net.SendCodeNet;

import com.cesaas.android.counselor.order.utils.MD5;
import com.cesaas.android.counselor.order.utils.OtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;


/**
 * 注册
 * @author FGB
 *
 */
public class RegisterAactivity extends BasesActivity implements OnClickListener{
	
	private Button btn_register;
	private TimeButton btn_get_auth_code;
	private LinearLayout ll_register_back;
	
	private MClearEditText et_register_name,et_register_contact,et_register_mobile,et_register_auth_code,et_register_pwd,et_register_suer_pwd;

	private String company ,mobile, code, password, contact;
	
	private SendCodeNet codeNet;
	private RegistCompanyNet companyNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_register);
		
		initView();
		btn_get_auth_code.setTextAfter("重新获取").setTextBefore("获取验证码").setLenght(10 * 1000);
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		et_register_name=(MClearEditText) findViewById(R.id.et_register_name);
		Drawable drawable=getResources().getDrawable(R.drawable.register_shopname);  
        drawable.setBounds(10,10,10,10);  
        btn_get_auth_code=(TimeButton) findViewById(R.id.btn_get_auth_code);
        et_register_name.setCompoundDrawables(drawable,drawable,drawable,drawable);  
		et_register_contact=(MClearEditText) findViewById(R.id.et_register_contact);
		et_register_mobile=(MClearEditText) findViewById(R.id.et_register_mobile);
		et_register_auth_code=(MClearEditText) findViewById(R.id.et_register_auth_code);
		et_register_pwd=(MClearEditText) findViewById(R.id.et_register_pwd);
		et_register_suer_pwd=(MClearEditText) findViewById(R.id.et_register_suer_pwd);
		btn_register=(Button) findViewById(R.id.btn_register);
		ll_register_back=(LinearLayout) findViewById(R.id.ll_register_back);
		btn_get_auth_code.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		ll_register_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_auth_code://获取验证码
			if(!TextUtils.isEmpty(et_register_mobile.getText().toString())){
				
//				codeNet=new SendCodeNet(mContext);
//				codeNet.setData(et_register_mobile.getText().toString());
//				getSendCodeListener();
				
			}else{
				ToastFactory.getLongToast(mContext, "请输入手机号！");
			}
			
			break;
			
		case R.id.btn_register://注册
			validation();
			break;
			
		case R.id.ll_register_back://返回
			Skip.mBack(mActivity);
			break;
		}
	}
	
	/**
	 * 注册验证
	 */
	public void validation(){
		company=et_register_name.getText().toString();
		contact=et_register_contact.getText().toString();
		mobile=et_register_mobile.getText().toString(); 
		code=et_register_auth_code.getText().toString();
		password=et_register_pwd.getText().toString();
		
		if(!TextUtils.isEmpty(company)){
			
			if(!TextUtils.isEmpty(contact)){
				
				if(OtherUtil.phoneVerify(mContext, mobile)){
					
					if(!TextUtils.isEmpty(code)){
						
						if(OtherUtil.passwordVerify(mContext, password)){
							
							if(OtherUtil.passwordVerify(mContext, et_register_suer_pwd.getText().toString())){
								if(password.equals(et_register_suer_pwd.getText().toString())){
									
									companyNet=new RegistCompanyNet(mContext);
									companyNet.setData(company, mobile, code, new MD5().toMD5(password), contact);
									
								}else{
									ToastFactory.getLongToast(mContext, "两次密码不一致！");
								}
							}
						}
						
					}else{
						ToastFactory.getLongToast(mContext, "请输入验证码！");
					}
				}
				
			}else{
				ToastFactory.getLongToast(mContext, "请输入联系人！");
			}
			
		}else{
			ToastFactory.getLongToast(mContext, "请输入商家名称！");
		}
	}
	
	/**
	 * 接收验证码消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultSendCodeBean msg) {
    	if(msg.IsSuccess==true ){
    		//填写验证码
    		et_register_auth_code.setText(msg.TModel.mobile);
    		
    	}else{
    		ToastFactory.getLongToast(mContext, "验证码获取失败！"+msg.Message);
    	}
    }
	
	/**
	 * 接收商家注册消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultRegistCompanyBean msg) {
    	if(msg.IsSuccess==true ){
    		
    		ToastFactory.getLongToast(mContext, "恭喜您，商家注册成功！");
    		//跳转到登录页面
    		Skip.mNext(mActivity, LoginActivity.class);
    		
    	}else{
    		ToastFactory.getLongToast(mContext, "注册失败！"+msg.Message);
    	}
    }
	
//	public void getSendCodeListener(){
//		 Request<String> request = NoHttp.createStringRequest(Urls.GET_SEND_CODE, RequestMethod.POST);
//         request.add("mobile",et_register_mobile.getText().toString());
//         CallServer.getRequestInstance().add(this, zero, request, sendCodeListener, true, true);
//	}
//
//	 //获取验证码网络监听
//    private HttpListener<String> sendCodeListener = new HttpListener<String>()
//    {
//        @Override
//        public void onSucceed(int what, Response<String> response)
//        {
//        	Log.i(Constant.TAG, "=="+response.getHeaders()+"=="+response.request());
//        }
//        @Override
//        public void onFailed(int what, Response<String> response)
//        {
//            ToastFactory.getLongToast(mContext, response.getException().getMessage());
//        }
//    };
}
