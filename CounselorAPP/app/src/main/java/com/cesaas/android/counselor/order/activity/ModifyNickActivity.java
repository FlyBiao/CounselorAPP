package com.cesaas.android.counselor.order.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.SetNickNet;
import com.cesaas.android.counselor.order.utils.AbOtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;

/**
 * 修改昵称
 * @author FGB
 * 2016/3/13
 *
 */
public class ModifyNickActivity extends BasesActivity{

	private MClearEditText et_nick;
	private TextView tv_suer_update_nick;
	private LinearLayout iv_nick_back;
	
	private SetNickNet nickNet;
	private String nickName;
	
	private String nick;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){
			nickName=bundle.getString("nickName");
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_modify_nick);
		
		tv_suer_update_nick=(TextView)findViewById(R.id.tv_suer_update_nick);
		et_nick=(MClearEditText) findViewById(R.id.et_nick);
		iv_nick_back=(LinearLayout) findViewById(R.id.iv_nick_back);
		et_nick.setText(nickName);
		et_nick.setSelection(et_nick.getText().length());
		
		nickNet = new SetNickNet(mContext);
		
		sures();
		initBack();
	}
	
	public void sures(){
		tv_suer_update_nick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 nick = et_nick.getText().toString().trim();
				if (AbOtherUtil.textVerify(mContext, nick)) {
					if (verNick(nick)) {
						if (nick.length() > 1 && nick.length() < 17) {
							nickNet.setData(nick);
							
						} else {
							ToastFactory.show(mContext, "长度应为2-16位", ToastFactory.CENTER);
						}
					} else {
						ToastFactory.show(mContext, "只能以字母或汉字开头", ToastFactory.CENTER);
					}
				}
			}
		});
	}
	
	
	public void onEventMainThread(ResultBean msg) { // 成功回调
		if (msg.type.equals("nick")) {
			ToastFactory.show(mContext, "昵称修改成功", ToastFactory.CENTER);
			prefs.putString(Constant.SPF_NICK, et_nick.getText().toString().trim());
			UserCentreActivity.nickModify=true;
			UserCentreActivity.handler.obtainMessage(1, nick).sendToTarget();
			Skip.mBack(mActivity);
		}
	}
	
	private boolean verNick(String srt) {
		String regex = "^[a-zA-Z\u4E00-\u9FA5].*";
		Pattern patten = Pattern.compile(regex);
		Matcher match = patten.matcher(srt);
		return match.matches();
	}
	
	public void initBack(){
		iv_nick_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}
