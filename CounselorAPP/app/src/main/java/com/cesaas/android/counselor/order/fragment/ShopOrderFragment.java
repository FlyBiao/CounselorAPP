package com.cesaas.android.counselor.order.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.view.FinshShopOrderFragment;
import com.cesaas.android.counselor.order.view.NoPayShopOrderFragment;
import com.cesaas.android.counselor.order.view.WaitInShopOrderFragment;
import com.cesaas.android.counselor.order.view.WaitOutShopOrderFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 店铺订单
 * @author FGB
 *
 */
public class ShopOrderFragment extends BaseFragment{
	
	private TextView tv_shop_send_nopayOrder;
	private TextView tv_shop_send_waitoutOrder;
	private TextView tv_shop_send_waitinOrder;
	private TextView tv_shop_send_okOrder;
	
	private Context context;
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	private NoPayShopOrderFragment noPayShopOrderFragment=new NoPayShopOrderFragment();//未支付
	private WaitOutShopOrderFragment waitOutShopOrderFragment=new WaitOutShopOrderFragment();//未发货
	private WaitInShopOrderFragment waitInShopOrderFragment=new WaitInShopOrderFragment();//已发货
	private FinshShopOrderFragment finshShopOrderFragment=new FinshShopOrderFragment();//完成
	
	private FragmentManager fm;
	private FragmentTransaction transaction;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		
		context=this.getActivity();
	}
	
	public void initView(){
		
		if(!noPayShopOrderFragment.isAdded()){
			fm = getFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.shop_send_order_realtabcontent, noPayShopOrderFragment).commit();
		}
    }
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.activity_shop_order_layout,container, false);
		ViewUtils.inject(this, view);
		
		tv_shop_send_nopayOrder=(TextView) view.findViewById(R.id.tv_shop_send_nopayOrder);
		tv_shop_send_waitinOrder=(TextView) view.findViewById(R.id.tv_shop_send_waitinOrder);
		tv_shop_send_waitoutOrder=(TextView) view.findViewById(R.id.tv_shop_send_waitoutOrder);
		tv_shop_send_okOrder=(TextView) view.findViewById(R.id.tv_shop_send_okOrder);
		
		tvs.add(tv_shop_send_nopayOrder);
		tvs.add(tv_shop_send_waitinOrder);
		tvs.add(tv_shop_send_waitoutOrder);
		tvs.add(tv_shop_send_okOrder);
		
		return view;
	}
	
	@OnClick({R.id.tv_shop_send_nopayOrder,R.id.tv_shop_send_waitinOrder,
		R.id.tv_shop_send_waitoutOrder,R.id.tv_shop_send_okOrder})
	public void onClick(View v){
		int index=-1;
		fm = getFragmentManager();
		transaction = fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.tv_shop_send_nopayOrder:
			
			index=0;
			if(tv_shop_send_nopayOrder.isEnabled()==true){
				transaction.replace(R.id.shop_send_order_realtabcontent, noPayShopOrderFragment);
			}else{
				tv_shop_send_nopayOrder.setEnabled(false);
			}
			
			break;
			
		case R.id.tv_shop_send_waitinOrder:
			index=1;
			transaction.replace(R.id.shop_send_order_realtabcontent, waitOutShopOrderFragment);
			break;
			
		case R.id.tv_shop_send_waitoutOrder:
			index=2;
			transaction.replace(R.id.shop_send_order_realtabcontent, waitInShopOrderFragment);
			break;
			
		case R.id.tv_shop_send_okOrder:
			index=3;
			transaction.replace(R.id.shop_send_order_realtabcontent, finshShopOrderFragment);
			break;
		}
		
		setColor(index);
		transaction.commit();
	}
	
	
	private void setColor(int index) {
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(context.getResources().getColor(R.color.color_title_bar));
			tvs.get(i).setBackgroundColor(context.getResources().getColor(R.color.day_sign_content_text_white_30));
		}
		tvs.get(index).setTextColor(context.getResources().getColor(R.color.white));
		tvs.get(index).setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_login_bg));
	}
	

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
