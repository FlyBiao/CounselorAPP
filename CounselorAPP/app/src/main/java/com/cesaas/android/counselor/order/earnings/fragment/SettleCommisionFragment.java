package com.cesaas.android.counselor.order.earnings.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 待结算Fragment
 * @author fgb
 *
 */
public class SettleCommisionFragment extends BaseFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View view=inflater.inflate(R.layout.settle_commision_view, container, false);
    	ViewUtils.inject(this, view);
    	return view;
    }
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
