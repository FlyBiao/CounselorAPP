package com.cesaas.android.counselor.order.shopmange;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 商品管理
 * @author FGB
 *
 */
public class ShopMangeActivity extends BasesActivity implements OnClickListener{
	
	private TextView tv_new_shop,tv_selling_price_shop,tv_sales_shop,tv_all_shop,tv_shop_type_label;
	private LinearLayout ll_shop_mange_back,ll_shop_counts,ll_shop_type_label;
	
	private ArrayList<TextView> tvs=new ArrayList<TextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_maage);
		
		initView();
		mBack();
		mSearch();
		hid();
		
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			ll_shop_type_label.setVisibility(View.VISIBLE);
			ll_shop_counts.setVisibility(View.GONE);
			tv_shop_type_label.setText(bundle.getString("searchContent"));
		}
	}
	
	public void initView(){
		tv_new_shop=(TextView) findViewById(R.id.tv_new_shop);
		tv_selling_price_shop=(TextView) findViewById(R.id.tv_selling_price_shop);
		tv_sales_shop=(TextView) findViewById(R.id.tv_sales_shop);
		tv_all_shop=(TextView) findViewById(R.id.tv_all_shop);
		tv_shop_type_label=(TextView) findViewById(R.id.tv_shop_type_label);
		
		ll_shop_type_label=(LinearLayout) findViewById(R.id.ll_shop_type_label);
		ll_shop_counts=(LinearLayout) findViewById(R.id.ll_shop_counts);
		ll_shop_mange_back=(LinearLayout) findViewById(R.id.ll_shop_mange_back);
		
		tv_new_shop.setOnClickListener(this);
		tv_selling_price_shop.setOnClickListener(this);
		tv_sales_shop.setOnClickListener(this);
		tv_all_shop.setOnClickListener(this);
		
		tvs.add(tv_all_shop);
		tvs.add(tv_sales_shop);
		tvs.add(tv_selling_price_shop);
		tvs.add(tv_new_shop);
		
	}
	
	public void mBack(){
		ll_shop_mange_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}
	
	public void mSearch(){
//		tv_search_shop.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Skip.mNext(mActivity, ShopMangerSearchActivity.class);
//			}
//		});
	}
	
	public void hid(){
		//设置3秒后隐藏商品数量
		new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                	ll_shop_counts.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 3000);
	}
	
	private void setColor(int index) {
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.color_title_bar));
			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_shop_mange_bg));
	}

	@Override
	public void onClick(View v) {
		int index=-1;
		
		switch (v.getId()) {
		
		case R.id.tv_all_shop://全部
			index=0;
			
			break;
			
		case R.id.tv_sales_shop://销量
			index=1;
			
			break;
			
		case R.id.tv_selling_price_shop://售价
			index=2;
			break;
			
		case R.id.tv_new_shop://新品
			index=3;
			break;
		}
		setColor(index);
		
	}
}
