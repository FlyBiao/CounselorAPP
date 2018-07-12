package com.cesaas.android.counselor.order.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetTokenBean;
import com.cesaas.android.counselor.order.bean.ResultGetTokenBean.GetTokenBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.net.GetTokenNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.lidroid.xutils.ViewUtils;

/**
 * Created by FGB on 2016/3/7.
 */
public class SalesFragment extends BaseFragment{
	 private static final String TAG="SalesFragment";
	 
    private GetTokenNet getTokenNet;
    private GetTokenBean tokenBean;
    
    private UserInfoNet userInfoNet;
	private UserBean userBean;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getTokenNet=new GetTokenNet(getActivity());
        getTokenNet.setData();
        
        userInfoNet=new UserInfoNet(getContext());
		userInfoNet.setData();
        
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.activity_my_sales_layout, container, false);
    	View view=inflater.inflate(R.layout.activity_my_sales_layout, container, false);
    	ViewUtils.inject(this, view);
    	return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    
    public void onEventMainThread(ResultGetTokenBean msg) {
    	
    	if(msg!=null){
    		tokenBean=msg.TModel;
    		abpUtil.putString("RongToken", tokenBean.token);
    	}
    }
    
    public void onEventMainThread(ResultUserBean msg) {
    	
    	if(msg!=null){
    		userBean=msg.TModel;
    		abpUtil.putString("nick", userBean.NickName);
    	}
    }
    
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
