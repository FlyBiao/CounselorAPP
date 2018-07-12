package com.cesaas.android.counselor.order.fans.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.group.fragment.GroupImageFragment;
import com.cesaas.android.counselor.order.group.fragment.GroupImageTextFragment;
import com.cesaas.android.counselor.order.group.fragment.GroupTextFragment;
import com.cesaas.android.counselor.order.stafftest.NotTestFragment;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 群发消息
 * @author FGB
 *
 */
public class GroupSendMessageAactivity extends BasesActivity implements OnClickListener{

	private LinearLayout ll_group_send_back;
	
	private TextView tv_test1,tv_test3,tv_test6,tv_group_checkNum;
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	private FragmentManager fm;
	private FragmentTransaction transaction;
	
	public JSONArray fansArray;
	private int checkNum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_send_layout);
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			checkNum=bundle.getInt("checkNum");
		}
		
		initView();
		initData();
	}
	
	private void initData() {
		tv_group_checkNum.setText(checkNum+"人");
		
		if(!NotTestFragment.getInstance().isAdded()){
			fm =getSupportFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.fl_group_message_content, GroupTextFragment.getInstance()).commit();
		}
	}

	/**
	 * 初始化视图控件
	 */
	private void initView() {
		tv_group_checkNum=(TextView) findViewById(R.id.tv_group_checkNum);
		ll_group_send_back=(LinearLayout) findViewById(R.id.ll_group_send_back);
		ll_group_send_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
		
		tv_test1=(TextView) findViewById(R.id.tv_test1);
		tv_test3=(TextView) findViewById(R.id.tv_test3);
		tv_test6=(TextView) findViewById(R.id.tv_test6);
		
		tv_test1.setOnClickListener(this);
		tv_test3.setOnClickListener(this);
		tv_test6.setOnClickListener(this);
		
		tvs.add(tv_test1);
		tvs.add(tv_test3);
		tvs.add(tv_test6);
	}

	@Override
	public void onClick(View v) {
		
		int index=-1;
		fm =getSupportFragmentManager();
		transaction = fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.tv_test1://文本
			index=0;
			if(tv_test1.isEnabled()==true){
				transaction.replace(R.id.fl_group_message_content, GroupTextFragment.getInstance());
			}else{
				tv_test1.setEnabled(false);
			}
			break;
			
		case R.id.tv_test3://Image
			index=1;
			transaction.replace(R.id.fl_group_message_content, GroupImageFragment.getInstance());
			break;
			
		case R.id.tv_test6://图文
			index=2;
			transaction.replace(R.id.fl_group_message_content, GroupImageTextFragment.getInstance());
			break;

		default:
			break;
		}
		setColor(index);
		transaction.commit();
	}
	
	private void setColor(int index) {
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.text_press));
			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.color_title_bar));
		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_group_send_bg));
	}
	
}
