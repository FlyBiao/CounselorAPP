package com.cesaas.android.counselor.order.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.DepositBean;
import com.cesaas.android.counselor.order.bean.ResultConselorBounseBean;
import com.cesaas.android.counselor.order.net.ConselorBounseNet;
import com.cesaas.android.counselor.order.net.DepositNet;
import com.cesaas.android.counselor.order.shopmange.FilterClerkActivity;
import com.cesaas.android.counselor.order.shopmange.net.AgreeClerkNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.materialdialog.MaterialDialog;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 提现页面
 * @author FGB
 *
 */
@ContentView(R.layout.activity_deposit_layout)
public class DepositActivity extends BasesActivity implements OnClickListener{
	
	@ViewInject(R.id.et_deposit)
	private EditText et_deposit;
	@ViewInject(R.id.tv_deposit_money)
	private TextView tv_deposit_money;
	@ViewInject(R.id.il_deposit_back)
	private LinearLayout il_deposit_back;
	@ViewInject(R.id.btn_deposit_record)
	private Button btn_deposit_record;
	@ViewInject(R.id.btn_deposit)
	private Button btn_deposit;

	private MaterialDialog mMaterialDialog;
	private ConselorBounseNet bounseNet;
	private DepositNet depositNet;
	private String strValue;
	private double AllowAmount;
	private int searchResultFilterCode=1002;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		btn_deposit.setOnClickListener(this);
		btn_deposit_record.setOnClickListener(this);

		bounseNet=new ConselorBounseNet(mContext);
		bounseNet.setData();
		
		mBack();
	}

    public void onEventMainThread(ResultConselorBounseBean msg) {
        if(msg.IsSuccess==true){
			AllowAmount=msg.TModel.getAllowAmount();
			if(AllowAmount!=0){
				tv_deposit_money.setText(AllowAmount+"");
			}else{
				tv_deposit_money.setText("0");
			}
        }else{
            ToastFactory.getLongToast(mContext,"获取提现金额失败！"+ msg.Message);
        }
    }

	public void onEventMainThread(DepositBean msg) {
		if(msg.IsSuccess==true){
			//跳转提现记录列表
			ToastFactory.getLongToast(mContext,"提现成功！");
			Skip.mActivityResult(mActivity,DepositRecordActivity.class,searchResultFilterCode);
		}else{
			ToastFactory.getLongToast(mContext,"提现失败！"+msg.Message);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_deposit:
				strValue=et_deposit.getText().toString().trim();
				if(AllowAmount!=0){
					if(!TextUtils.isEmpty(strValue)){
						double depositValue=Double.parseDouble(strValue);
							if(depositValue>=5){

								showDialog(depositValue);
							}
							else if(depositValue<5){
								ToastFactory.getLongToast(mContext, "提现金额不能小于5元！");
							}
					}
					else{
						ToastFactory.getLongToast(mContext, "请输入提现金额！");
					}
				}else{
					ToastFactory.getLongToast(mContext, "当前没有金额可提现！");
				}

				break;
			case R.id.btn_deposit_record:
				Skip.mNext(mActivity, DepositRecordActivity.class);
				break;
		}
	}

	private void showDialog(final double allowAmount){
		mMaterialDialog=new MaterialDialog(mContext);
		if (mMaterialDialog != null) {
			mMaterialDialog.setTitle("温馨提示！")
					.setMessage("本次提现金额:"+allowAmount+"元，请确认是否马上提现？")
					//mMaterialDialog.setBackgroundResource(R.drawable.background);
					.setPositiveButton("确定", new View.OnClickListener() {
						@Override public void onClick(View v) {
							//执行确定操作
							mMaterialDialog.dismiss();
							depositNet=new DepositNet(mContext);
							depositNet.setData(allowAmount);
						}
					}).setNegativeButton("再想想", new View.OnClickListener() {
				@Override public void onClick(View v) {
					mMaterialDialog.dismiss();
//
				}}).setCanceledOnTouchOutside(true).show();
		}
	}

	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1002){
			if(data!=null){
				bounseNet=new ConselorBounseNet(mContext);
				bounseNet.setData();
				et_deposit.setText("");
			}
		}
	}

	/**
	 * 返回上一个页面
	 */
	public void mBack(){
		il_deposit_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
}