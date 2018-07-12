package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetSubMenuPowerBean;
import com.cesaas.android.counselor.order.fans.fragment.FansServiceFragment;
import com.cesaas.android.counselor.order.fans.fragment.ShopFansFragment;
import com.cesaas.android.counselor.order.net.GetSubMenuPowerNet;
import com.cesaas.android.counselor.order.rong.activity.FansConversationListFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.view.NoPayOrderStateView;

/**
 * 会员页面
 * @author fgb
 *
 */
public class VipActivity extends BasesActivity implements OnClickListener{

	private LinearLayout llBack;
	private TextView tvTitle;
	private TextView tv_vip_session,tv_my_fans,tv_shop_fans;
	
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	private FragmentManager fm;
	private FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip_layout);

		initView();
	}

	/**
	 * 初始化视图标签
	 */
	public void initView(){
		tv_vip_session=(TextView) findViewById(R.id.tv_vip_session);
		tv_my_fans=(TextView) findViewById(R.id.tv_my_fans);
		tv_shop_fans=(TextView) findViewById(R.id.tv_shop_fans);
		tvTitle= (TextView) findViewById(R.id.tv_base_title);
		tvTitle.setText("会话列表");
		llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
		
		tv_vip_session.setOnClickListener(this);
		tv_my_fans.setOnClickListener(this);
		tv_shop_fans.setOnClickListener(this);
		
		tvs.add(tv_vip_session);
		tvs.add(tv_my_fans);
		tvs.add(tv_shop_fans);
		
		if(!NoPayOrderStateView.getInstance().isAdded()){
			fm = getSupportFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.vip_realtabcontent, new FansConversationListFragment()).commit();
		}

		llBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		int index=-1;
		fm = getSupportFragmentManager();
		transaction = fm.beginTransaction();
		
		switch (v.getId()) {
		
		case R.id.tv_vip_session:
			index=0;
			if(tv_my_fans.isEnabled()==true){
				transaction.replace(R.id.vip_realtabcontent, new FansConversationListFragment());
			}else{
				tv_my_fans.setEnabled(false);
			}
			break;
		
		case R.id.tv_my_fans://
			index=1;
			transaction.replace(R.id.vip_realtabcontent, new FansServiceFragment());
			break;
			
		case R.id.tv_shop_fans://
			index=2;
			transaction.replace(R.id.vip_realtabcontent, new ShopFansFragment());
			break;
		}
		
		setColor(index);
		transaction.commit();
		
	}
	private void setColor(int index) {
		// TODO Auto-generated method stub
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.c1));
			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_ellipse_blue_bg));
	}

	
}
