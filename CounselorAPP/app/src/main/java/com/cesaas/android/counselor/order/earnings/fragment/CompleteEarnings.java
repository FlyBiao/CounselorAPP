package com.cesaas.android.counselor.order.earnings.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
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
 * 已完成收益
 * @author FGB
 *
 */
public class CompleteEarnings extends BaseFragment{

	private static final String TAG = "CompleteEarnings";
	@ViewInject(R.id.lv_e_complete_list)
	private ListView lv_e_complete_list;
	@ViewInject(R.id.tv_not_data)
	private TextView tv_not_data;
	
	private GetCommsionListAdapter commsionAdapter;
	
	private GetCommsionNet commsionNet;
	
	private ArrayList<GetCommsionBean> getList;
	
	/**
	 * 单例
	 * @return
	 */
	public static CompleteEarnings newInstance() {
		CompleteEarnings f = new CompleteEarnings();
		return f;
	}
//	private CompleteEarnings() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	public void onEventMainThread(ResultGetCommsion msg) {
		
		if(msg!=null){
			if(msg.TModel.size()>0){
				getList.clear();
				this.getList.addAll(msg.TModel);
//				initData();
				commsionAdapter.notifyDataSetChanged();
		}
		}
		
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_complete_earnings_layout, container, false);
        ViewUtils.inject(this, view); // 注入view和事件
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        commsionNet=new GetCommsionNet(getActivity());
		commsionNet.setData(Constant.RECEIVE,1,1);
        getList=new ArrayList<GetCommsionBean>();
		commsionAdapter=new GetCommsionListAdapter(getActivity(), getList);
        lv_e_complete_list.setAdapter(commsionAdapter);
    }
	
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
