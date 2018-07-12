package com.cesaas.android.counselor.order.training;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 培训资料
 * @author FGB
 *
 */
public class TrainingRelatedFragment extends BaseFragment implements OnClickListener{
	
	private LinearLayout ll_tests;
	
	private View view;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_training_related, container,false);
		
		initView();
		
		return view;
	}

	public void initView(){
		ll_tests=(LinearLayout) view.findViewById(R.id.ll_tests);
		
		ll_tests.setOnClickListener(this);
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			
		case R.id.ll_tests://测试
			Skip.mNext(getActivity(), TrainingRelatedDetailActivity.class);
			break;

		default:
			break;
		}
		
	}

}
