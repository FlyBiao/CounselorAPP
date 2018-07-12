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
import com.cesaas.android.counselor.order.net.GetCommsionNet;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 所有收益
 * @author FGB
 *
 */
public class AllEarnings extends BaseFragment{

	private static final String TAG = "AllEarnings";

	@ViewInject(R.id.lv_e_list)
	private ListView lv_e_list;
	
	@ViewInject(R.id.tv_commsion_no_data)
	private TextView tv_commsion_no_data;
	
	private GetCommsionListAdapter commsionAdapter;
	
	private GetCommsionNet commsionNet;
	
	private ArrayList<GetCommsionBean> getCommsionList;
	
	/**
	 * 单例
	 * @return
	 */
	public static AllEarnings newInstance() {
		AllEarnings f = new AllEarnings();
		return f;
	}
//	private AllEarnings() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	public void onEventMainThread(ResultGetCommsion msg) {
		if(msg!=null){
			if(msg.TModel.size()>0){
				getCommsionList.clear();
				this.getCommsionList.addAll(msg.TModel);
//				initData();
				commsionAdapter.notifyDataSetChanged();
			}
		}
		
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_all_earnings_layout, container, false);
        ViewUtils.inject(this, view); // 注入view和事件
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commsionNet=new GetCommsionNet(getActivity());
		commsionNet.setData(-1,1,1);
        getCommsionList=new ArrayList<GetCommsionBean>();
		commsionAdapter=new GetCommsionListAdapter(getActivity(), getCommsionList);
        lv_e_list.setAdapter(commsionAdapter);
    }
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
