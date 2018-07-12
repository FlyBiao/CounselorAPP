package com.cesaas.android.counselor.order.earnings.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.GetCommsionListAdapter;
import com.cesaas.android.counselor.order.bean.ResultGetCommsion;
import com.cesaas.android.counselor.order.bean.ResultGetCommsion.GetCommsionBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.GetCommsionNet;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 暂时冻结收益
 * @author FGB
 *
 */
public class FreezeEarningsFragment extends BaseFragment{

	@ViewInject(R.id.lv_e_freeze_list)
	private ListView lv_e_freeze_list;
	@ViewInject(R.id.tv_not_freeze_data)
	private TextView tv_not_freeze_data;
	
	private GetCommsionListAdapter commsionAdapter;
	
	private GetCommsionNet commsionNet;
	
	private ArrayList<GetCommsionBean> getList=new ArrayList<GetCommsionBean>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		commsionNet=new GetCommsionNet(getActivity());
		commsionNet.setData(Constant.FREEZE,1,1);
	}
	
	public void initData() {
		if(getList.size()>0){
			commsionAdapter=new GetCommsionListAdapter(getActivity(), getList);
			lv_e_freeze_list.setAdapter(commsionAdapter);
		}else{
			tv_not_freeze_data.setVisibility(View.VISIBLE);
		}
	}
	
	public void onEventMainThread(ResultGetCommsion msg) {
		this.getList.addAll(msg.TModel);
//		detailAdapter.notifyDataSetChanged();
		initData();
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_freeze_earnings_layout, container, false);
        ViewUtils.inject(this, view); // 注入view和事件
        return view;
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
