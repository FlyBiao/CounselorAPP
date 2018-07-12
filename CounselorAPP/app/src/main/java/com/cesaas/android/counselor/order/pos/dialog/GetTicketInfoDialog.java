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
 * 优惠券查询dialog
 * 
 * @author FGB
 * 
 */
public class GetTicketInfoDialog extends Dialog implements View.OnClickListener {
	
	private ImageView iv_ticket_scan_code;
	private Button btn_confirm_ticket_scan;
	private EditText et_ticket_code;
	private String ticketCode;
	
	private int REQUEST_CONTACT = 20;
	private Activity activity;
	
	public interface DialogCallBackListener{//通过该接口回调Dialog需要传递的值
        public void callBack(String msg);//具体方法
    }
	
	public GetTicketInfoDialog(Context context,Activity activity) {
		this(context, R.style.dialog);
		this.activity=activity;
	}

	public GetTicketInfoDialog(Context context, int dialog) {
		super(context, dialog);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.ticket_info_dialog);
		
		initView();
	}
	
	public void initView(){
		iv_ticket_scan_code=(ImageView) findViewById(R.id.iv_ticket_scan_code);
		btn_confirm_ticket_scan=(Button) findViewById(R.id.btn_confirm_ticket_scan);
		et_ticket_code=(EditText) findViewById(R.id.et_ticket_code);
		
		iv_ticket_scan_code.setOnClickListener(this);
		btn_confirm_ticket_scan.setOnClickListener(this);
	}
	
	public void mInitShow() {
		show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ticket_scan_code://扫描优惠券二维码
			Skip.mActivityResult(activity, CaptureActivity.class, REQUEST_CONTACT);
			break;
		case R.id.btn_confirm_ticket_scan://确定查询优惠券
			if(!TextUtils.isEmpty(et_ticket_code.getText().toString())){
				ticketCode=et_ticket_code.getText().toString();
				cancel();
				
			}else{
				ToastFactory.getLongToast(activity.getApplicationContext(), "请输入优惠卷码!");
			}
			
			break;

		default:
			break;
		}
	}


}
