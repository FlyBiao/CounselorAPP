package com.cesaas.android.counselor.order.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.view.AllFansOrderStateView;
import com.cesaas.android.counselor.order.view.NoPayFansOrderStateView;
import com.cesaas.android.counselor.order.view.OkOutFansOrderStateView;
import com.cesaas.android.counselor.order.view.RefundFansOrderStateView;
import com.cesaas.android.counselor.order.view.WaitInFansOrderStateView;
import com.cesaas.android.counselor.order.view.WaitOutFansOrderStateView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 粉丝订单
 * @author FGB
 *
 */
public class FansOrderFragment extends BaseFragment{
	
	private Context context;
	private FragmentManager fm;
	private FragmentTransaction transaction;
	
	private TextView tv_fans_allOrder;
	private TextView tv_fans_nopayOrder;
	private TextView tv_fans_waitoutOrder;
	private TextView tv_fans_waitinOrder;
	private TextView tv_fans_okOrder;
	
	private ArrayList<TextView> tvs=new ArrayList<TextView>();
	
	private AllFansOrderStateView allFansOrderStateView=new AllFansOrderStateView();//全部
	private NoPayFansOrderStateView noPayFansOrderStateView=new NoPayFansOrderStateView();//未付款
	private WaitInFansOrderStateView inFansOrderStateView=new WaitInFansOrderStateView();//已发货
	private WaitOutFansOrderStateView outFansOrderStateView=new WaitOutFansOrderStateView();//未发货
//	private RefundFansOrderStateView refunFansdOrderStateView=new RefundFansOrderStateView();//退款
	private OkOutFansOrderStateView okOutFansOrderStateView=new OkOutFansOrderStateView();//已完成
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		context=this.getActivity();
	}
	
	
	public void initView(){
		
//		if(!allFansOrderStateView.isAdded()){
			fm = getFragmentManager();
			transaction = fm.beginTransaction();
			transaction.add(R.id.my_fans_order_realtabcontent, allFansOrderStateView).commit();
//		}
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.activity_fans_order_layout,container, false);
		ViewUtils.inject(this, view);
		
		tv_fans_allOrder=(TextView) view.findViewById(R.id.tv_fans_allOrder);
		tv_fans_nopayOrder=(TextView) view.findViewById(R.id.tv_fans_nopayOrder);
		tv_fans_waitoutOrder=(TextView) view.findViewById(R.id.tv_fans_waitoutOrder);
		tv_fans_waitinOrder=(TextView) view.findViewById(R.id.tv_fans_waitinOrder);
		tv_fans_okOrder=(TextView) view.findViewById(R.id.tv_fans_okOrder);
		
		tvs.add(tv_fans_allOrder);
		tvs.add(tv_fans_nopayOrder);
		tvs.add(tv_fans_waitoutOrder);
		tvs.add(tv_fans_waitinOrder);
		tvs.add(tv_fans_okOrder);
		
		return view;
	}
	
	@OnClick({R.id.tv_fans_allOrder,R.id.tv_fans_nopayOrder,
		R.id.tv_fans_waitoutOrder,R.id.tv_fans_waitinOrder,
		R.id.tv_fans_okOrder})//R.id.tv_fans_waitpjOrder,
	public void onClick(View v){
		int index=-1;
		
		fm = getFragmentManager();
		transaction = fm.beginTransaction();
		
		switch (v.getId()) {
		case R.id.tv_fans_allOrder://全部
			index=0;
			transaction.replace(R.id.my_fans_order_realtabcontent, allFansOrderStateView);
			break;
			
		case R.id.tv_fans_nopayOrder://未付款
			index=1;
			transaction.replace(R.id.my_fans_order_realtabcontent, noPayFansOrderStateView);
			break;
			
		case R.id.tv_fans_waitoutOrder://已发货
			index=2;
			transaction.replace(R.id.my_fans_order_realtabcontent, inFansOrderStateView);
			break;
			
		case R.id.tv_fans_waitinOrder://未发货
			index=3;
			transaction.replace(R.id.my_fans_order_realtabcontent, outFansOrderStateView);
			break;
			
//		case R.id.tv_fans_waitpjOrder://退款
//			transaction.replace(R.id.my_fans_order_realtabcontent, refunFansdOrderStateView);
//			break;
			
		case R.id.tv_fans_okOrder://完成
			index=4;
			transaction.replace(R.id.my_fans_order_realtabcontent, okOutFansOrderStateView);
			break;
		}
		setColor(index);
		transaction.commit();
		
	}
		
	
	private void setColor(int index) {
		// TODO Auto-generated method stub
		for(int i=0;i<tvs.size();i++){
			tvs.get(i).setTextColor(context.getResources().getColor(R.color.rgb_000));
		}
		tvs.get(index).setTextColor(context.getResources().getColor(R.color.rgb_btn_red));
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
				
	}
	
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
