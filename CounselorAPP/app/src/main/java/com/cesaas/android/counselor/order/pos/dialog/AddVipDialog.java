package com.cesaas.android.counselor.order.pos.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.hugo.android.scanner.CaptureActivity;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 添加会员dialog
 * 
 * @author FGB
 * 
 */
public class AddVipDialog extends Dialog implements View.OnClickListener {
	
	private ImageView iv_add_scan_vip_mobile;
	private Button btn_confirm_add_vip_mobile;
	private EditText et_vip_mobile;
	public static String mobile;
	
	private int REQUEST_CONTACT = 20;
	private Activity activity;
	
	public interface DialogCallBackListener{//通过该接口回调Dialog需要传递的值
        public void callBack(String msg);//具体方法
    }
	
	public AddVipDialog(Context context,Activity activity) {
		this(context, R.style.dialog);
		this.activity=activity;
	}

	public AddVipDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.add_vip_dialog);
		
		initView();
	}
	
	public void initView(){
		iv_add_scan_vip_mobile=(ImageView) findViewById(R.id.iv_add_scan_vip_mobile);
		btn_confirm_add_vip_mobile=(Button) findViewById(R.id.btn_confirm_add_vip_mobile);
		et_vip_mobile=(EditText) findViewById(R.id.et_vip_mobile);
		
		iv_add_scan_vip_mobile.setOnClickListener(this);
		btn_confirm_add_vip_mobile.setOnClickListener(this);
	}
	
	public void mInitShow() {
		show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_add_scan_vip_mobile://扫描会员手机号
			Skip.mActivityResult(activity, CaptureActivity.class, REQUEST_CONTACT);
			break;
		case R.id.btn_confirm_add_vip_mobile://确定添加
			if(!TextUtils.isEmpty(et_vip_mobile.getText().toString())){
				mobile=et_vip_mobile.getText().toString();
				cancel();
				
			}else{
				ToastFactory.getLongToast(activity.getApplicationContext(), "请输入手机号!");
			}
			
			break;

		default:
			break;
		}
	}


}
