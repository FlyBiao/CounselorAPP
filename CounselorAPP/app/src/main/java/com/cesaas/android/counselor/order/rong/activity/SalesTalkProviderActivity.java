package com.cesaas.android.counselor.order.rong.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.fans.fragment.CustomSalesTalkFragment;
import com.cesaas.android.counselor.order.salestalk.GeneralSalesTalkViewPagerIndicatorFragment;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 话术列表页面
 * @author FGB
 *
 */
public class SalesTalkProviderActivity extends BasesActivity implements OnClickListener{

	private LinearLayout ll_words_back;
	
	private TextView tv_general_words_art,tv_custom_words_art;
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	private FragmentManager fm;
	private FragmentTransaction transaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_words_art_list);
		
		initView();
		mBack();
	}
	
	public void initView(){
		ll_words_back=(LinearLayout) findViewById(R.id.ll_words_back);
		
		tv_general_words_art=(TextView) findViewById(R.id.tv_general_words_art);
		tv_custom_words_art=(TextView) findViewById(R.id.tv_custom_words_art);
		tv_general_words_art.setOnClickListener(this);
		tv_custom_words_art.setOnClickListener(this);
		
		tvs.add(tv_general_words_art);
		tvs.add(tv_custom_words_art);
		
		if(!GeneralSalesTalkViewPagerIndicatorFragment.getInstance().isAdded()){
			fm =getSupportFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.my_sales_talk_realtabcontent, GeneralSalesTalkViewPagerIndicatorFragment.getInstance()).commit();
		}
	}
	
	/**
	 * 返回
	 */
	public void mBack(){
		
		ll_words_back.setOnClickListener(new OnClickListener() {
			
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
		case R.id.tv_general_words_art:
			index=0;
			if(tv_general_words_art.isEnabled()==true){
				transaction.replace(R.id.my_sales_talk_realtabcontent, GeneralSalesTalkViewPagerIndicatorFragment.getInstance());
			}else{
				tv_general_words_art.setEnabled(false);
			}
			
			break;
			
		case R.id.tv_custom_words_art:
			index=1;
			transaction.replace(R.id.my_sales_talk_realtabcontent, CustomSalesTalkFragment.getInstance());
			break;
		}
		setColor(index);
		transaction.commit();
	}
	
	private void setColor(int index) {
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.white));
			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.color_title_bar));
		}
		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.color_title_bar));
		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_staff_test_bg));
	}
}
