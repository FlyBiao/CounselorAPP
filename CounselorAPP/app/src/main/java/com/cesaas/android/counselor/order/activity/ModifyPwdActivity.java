package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.LoginBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.ModifyPwdNet;
import com.cesaas.android.counselor.order.utils.AbOtherUtil;
import com.cesaas.android.counselor.order.utils.AbStrUtil;
import com.cesaas.android.counselor.order.utils.MD5;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 修改密码
 * @author FGB
 *
 */
public class ModifyPwdActivity extends BasesActivity{

	private MClearEditText ump_opwd;
	private MClearEditText ump_npwd;
	private MClearEditText ump_renpwd;
	private Button bt_sures;
	private LinearLayout iv_pwds_back;
	
	private ModifyPwdNet pwdNet;
	
	
	private ArrayList<EditText> ets=new ArrayList<EditText>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_modify_pwd);
		
		ump_opwd=(MClearEditText) findViewById(R.id.ump_opwd);
		ump_npwd=(MClearEditText) findViewById(R.id.ump_npwd);
		ump_renpwd=(MClearEditText) findViewById(R.id.ump_renpwd);
		bt_sures=(Button) findViewById(R.id.bt_set_pws_sures);
		iv_pwds_back=(LinearLayout) findViewById(R.id.iv_pwds_back);
		
		pwdNet = new ModifyPwdNet(mContext);
		AbStrUtil.editTextFilterChinese(ump_npwd);
		AbStrUtil.editTextFilterChinese(ump_opwd);
		AbStrUtil.editTextFilterChinese(ump_renpwd);
		
		ets.add(ump_npwd);
		ets.add(ump_opwd);
		ets.add(ump_renpwd);
		
		setPwd();
		initBack();
	}
	
	public void setPwd(){
		bt_sures.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String op = ump_opwd.getText().toString().trim();
				String np = ump_npwd.getText().toString().trim();
				String rnp = ump_renpwd.getText().toString().trim();
				if (AbOtherUtil.passwordVerify(mContext, op)) {
					if (AbOtherUtil.passwordVerify(mContext, np)) {
						if (rnp.equals(np)) {
							pwdNet.setData(new MD5().toMD5(op), new MD5().toMD5(rnp));
						} else {
							ToastFactory.show(mContext, "密码不一致", ToastFactory.CENTER);
						}
					}
				}
			}
		});
	}
	
	
	public void onEventMainThread(LoginBean msg) { // 登录成功回调
		if (msg != null) {
			if (msg.TModel != null)
				prefs.putString(Constant.SPF_TOKEN, msg.TModel.UserTicket);
//			Skip.mNext(mActivity, UserCentreActivity.class, true);
			Skip.mBack(mActivity);
		}
	}
	
	
	@OnClick({R.id.ump_opwd,R.id.ump_npwd,R.id.ump_renpwd,})
	public void onClick(View v){
		
		int index=-1;
		
		switch (v.getId()) {
		case R.id.ump_opwd:
			index=0;
			if(ump_opwd.isEnabled()==true){
				ets.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_session_bg));
			}else{
				ump_opwd.setEnabled(false);
			}
			
			break;
			
		case R.id.ump_npwd:
			index=1;
			
			break;
			
		case R.id.ump_renpwd:
			index=2;
			break;
		}
		setColor(index);
	}
	
	private void setColor(int index) {
		// TODO Auto-generated method stub
		for(int i=0;i<ets.size();i++){
			ets.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_session_bg));
		}
		ets.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_pwd_bg));
	}
	
	public void initBack(){
		iv_pwds_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
}
