package com.cesaas.android.counselor.order.fans.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultSetRemarkBean;
import com.cesaas.android.counselor.order.net.SetRemarkNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;

/**
 * 设置粉丝备注
 * @author FGB
 *
 */
public class SetFansRemarkActivity extends BasesActivity implements OnClickListener{

	private LinearLayout ll_fans_remark_back;
	private TextView tv_sure_add_remark;
	private MClearEditText mcet_fans_remark_name;
	
	private String name;
	private String id;
	
	private SetRemarkNet remarkNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_fans_remark_activity);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			id=bundle.getString("fansId");
			name=bundle.getString("fansNickName");
		}
		
		initView();
		mcet_fans_remark_name.setText(name);
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		ll_fans_remark_back=(LinearLayout) findViewById(R.id.ll_fans_remark_back);
		tv_sure_add_remark=(TextView) findViewById(R.id.tv_sure_add_remark);
		mcet_fans_remark_name=(MClearEditText) findViewById(R.id.mcet_fans_remark_name);
		
		ll_fans_remark_back.setOnClickListener(this);
		tv_sure_add_remark.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_fans_remark_back://返回
			Skip.mBack(mActivity);
			break;
			
		case R.id.tv_sure_add_remark://添加备注
			if(TextUtils.isEmpty(mcet_fans_remark_name.getText().toString())){
				ToastFactory.getLongToast(mContext, "请输入备注名称!");
			}else{
				name=mcet_fans_remark_name.getText().toString();
				remarkNet=new SetRemarkNet(mContext);
				remarkNet.setData(name, id);
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 接受添加备注消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultSetRemarkBean msg) {
		if(msg.IsSuccess==true){
			ToastFactory.getLongToast(mContext, "添加粉丝备注成功！");
			Skip.mNext(mActivity, VipDetailActivity.class);
			
			
		}else{
			ToastFactory.getLongToast(mContext, "添加备注失败!"+msg.Message);
		}
	}
}
