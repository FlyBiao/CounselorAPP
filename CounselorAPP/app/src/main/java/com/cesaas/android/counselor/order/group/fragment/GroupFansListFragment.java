package com.cesaas.android.counselor.order.group.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;

/**
 * 群发粉丝List
 * @author FGB
 *
 */
public class GroupFansListFragment extends BaseFragment{
	
	private View view;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_fans_service_layout, container,false);
		initView();
		return view;
	}
	
	/**
	 * 初始化视图控件
	 */
	private void initView() {
		
	}


	/**
	 * 懒加载
	 */
	@Override
	protected void lazyLoad() {
	}

}
