package com.cesaas.android.counselor.order.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.view.AllOrderStateView;
import com.cesaas.android.counselor.order.view.NoPayOrderStateView;
import com.cesaas.android.counselor.order.view.OkOutOrderStateView;
import com.cesaas.android.counselor.order.view.WaitInOrderStateView;
import com.cesaas.android.counselor.order.view.WaitOutOrderStateView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 发货订单管理
 * @author FGB
 *
 */
public class SendOrderMangerFragment extends BaseFragment{
	
	private Context context;
	
	private TextView tv_send_all_order;
	private TextView tv_send_nopayOrder;
	private TextView tv_send_waitoutOrder;
	private TextView tv_send_waitinOrder;
	private TextView tv_send_okOrder;

	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	AllOrderStateView allOrderStateView=new AllOrderStateView();
	NoPayOrderStateView noPayOrderStateView=new NoPayOrderStateView();
	WaitOutOrderStateView outOrderStateView=new WaitOutOrderStateView();
	WaitInOrderStateView inOrderStateView=new WaitInOrderStateView();
	OkOutOrderStateView okOutOrderStateView=new OkOutOrderStateView();
	
	
	private FragmentManager fm;
	private FragmentTransaction transaction;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		
		context=this.getActivity();
	}
	
   public void initView(){
		
		if(!NoPayOrderStateView.getInstance().isAdded()){
			fm = getFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.my_send_order_realtabcontent, allOrderStateView).commit();
		}
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.activity_merchants_order_layout,container, false);
		ViewUtils.inject(this, view);
		
		tv_send_all_order=(TextView) view.findViewById(R.id.tv_send_all_order);
		tv_send_nopayOrder=(TextView) view.findViewById(R.id.tv_send_nopayOrder);
		tv_send_waitinOrder=(TextView) view.findViewById(R.id.tv_send_waitinOrder);
		tv_send_waitoutOrder=(TextView) view.findViewById(R.id.tv_send_waitoutOrder);
		tv_send_okOrder=(TextView) view.findViewById(R.id.tv_send_okOrder);
		
		tvs.add(tv_send_all_order);
		tvs.add(tv_send_nopayOrder);
		tvs.add(tv_send_waitinOrder);
		tvs.add(tv_send_waitoutOrder);
		tvs.add(tv_send_okOrder);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	
	
	@OnClick({R.id.tv_send_all_order,R.id.tv_send_nopayOrder,
		R.id.tv_send_waitoutOrder,R.id.tv_send_waitinOrder,
		R.id.tv_send_okOrder})
	public void onClick(View v){
		
		int index=-1;
		
		fm = getFragmentManager();
		transaction = fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.tv_send_all_order://所有
			index=0;
			if(tv_send_all_order.isEnabled()==true){
				transaction.replace(R.id.my_send_order_realtabcontent, allOrderStateView);
			}else{
				tv_send_all_order.setEnabled(false);
			}
			
			break;
			
		case R.id.tv_send_nopayOrder://未付款
			index=1;
			transaction.replace(R.id.my_send_order_realtabcontent,noPayOrderStateView);
			
			break;
			
		case R.id.tv_send_waitinOrder://未发货
			index=2;
			transaction.replace(R.id.my_send_order_realtabcontent,outOrderStateView);
			
			break;
			
		case R.id.tv_send_waitoutOrder://已发货
			index=3;
			transaction.replace(R.id.my_send_order_realtabcontent, inOrderStateView);
			break;
			
		case R.id.tv_send_okOrder://完成
			index=4;
			transaction.replace(R.id.my_send_order_realtabcontent, okOutOrderStateView);
			break;
		}
		setColor(index);
		transaction.commit();
	}
	
	private void setColor(int index) {
		// TODO Auto-generated method stub
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
