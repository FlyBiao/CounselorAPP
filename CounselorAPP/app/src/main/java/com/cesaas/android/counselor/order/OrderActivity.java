package com.cesaas.android.counselor.order;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cesaas.android.counselor.order.ordermange.ShopOrderActivity;
import com.cesaas.android.counselor.order.ordermange.adapter.OrderTabLayoutAdapter;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 订单Activity
 * @author FGB
 *
 */
public class OrderActivity extends BasesActivity {
	
	private TextView tv_shop_order;
	private LinearLayout ll_order_back;
	TabLayout mTabLayout;
	ViewPager mViewPager;
	
	private String aPPOrder;
	//子菜单
//	private GetSubMenuPowerNet getSubMenuPowerNet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			aPPOrder=bundle.getString("APP_Order");
		}
		initView();
		ShopOrder();
		mBack();
		initData();
	}

	public void initData(){
		PagerAdapter adapter= new OrderTabLayoutAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		mTabLayout.setupWithViewPager(mViewPager);
	}

	/**
	 * 门店订单
	 */
	private void ShopOrder() {
		tv_shop_order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mNext(mActivity, ShopOrderActivity.class);
			}
		});
	}
	
	/**
	 * 返回
	 */
	public void mBack(){
		ll_order_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}

	/**
	 * 初始化视图控件
	 */
	public void initView(){
		tv_shop_order=(TextView) findViewById(R.id.tv_shop_order);
		ll_order_back=(LinearLayout) findViewById(R.id.ll_order_back);
		mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
		mViewPager= (ViewPager) findViewById(R.id.view_pager);
    }
	
//	public void onClick(View v){
//
//		int index=-1;
//
//		fm = getSupportFragmentManager();
//		transaction = fm.beginTransaction();
//
//		switch (v.getId()) {
//		case R.id.tv_send_all_order://所有
//			index=zero;
//			if(tv_send_all_order.isEnabled()==true){
//				transaction.replace(R.id.my_send_order_realtabcontent, new ReceiveOrderFragment());
//			}else{
//				tv_send_all_order.setEnabled(false);
//			}
//
//			break;
//
//		case R.id.tv_send_nopayOrder://未付款
//			index=1;
//			transaction.replace(R.id.my_send_order_realtabcontent,new NoPayOrderStateView());
//
//			break;
//
//		case R.id.tv_send_waitinOrder://未发货
//			index=2;
//			transaction.replace(R.id.my_send_order_realtabcontent,new WaitOutOrderStateView());
//
//			break;
//
//		case R.id.tv_send_waitoutOrder://已发货
//			index=3;
//			transaction.replace(R.id.my_send_order_realtabcontent, new WaitInOrderStateView());
//			break;
//
//		case R.id.tv_send_okOrder://完成
//			index=4;
//			transaction.replace(R.id.my_send_order_realtabcontent, new OkOutOrderStateView());
//			break;
//		}
//		setColor(index);
//		transaction.commit();
//	}
//
//	private void setColor(int index) {
//		// TODO Auto-generated method stub
//		for(int i=zero;i<tvs.size();i++){
//			tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.c1));
//			tvs.get(i).setBackgroundColor(mContext.getResources().getColor(R.color.day_sign_content_text_white_30));
//		}
//		tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.white));
//		tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_ellipse_blue_bg));
//	}
	

}
