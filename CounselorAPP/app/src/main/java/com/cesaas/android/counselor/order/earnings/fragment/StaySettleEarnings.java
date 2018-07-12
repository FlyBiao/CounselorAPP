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
 * 待结算
 * @author fgb
 *
 */
public class StaySettleEarnings extends BaseFragment{

	private static final String TAG = "StaySettleEarnings";
	@ViewInject(R.id.tv_stay_list)
	private ListView tv_stay_list;
	@ViewInject(R.id.tv_not_datas)
	private TextView tv_not_datas;
	
	private GetCommsionListAdapter commsionAdapter;//
	
	private GetCommsionNet commsionNet;//
	
	private ArrayList<GetCommsionBean> getCList;
	
	/**
	 * 单例
	 * @return
	 */
	public static StaySettleEarnings newInstance() {
		StaySettleEarnings f = new StaySettleEarnings();
		return f;
	}
//	private StaySettleEarnings() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		if(getCList.size()>0){
			commsionAdapter=new GetCommsionListAdapter(getActivity(), getCList);
			tv_stay_list.setAdapter(commsionAdapter);
		}else{
			tv_not_datas.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 接受EventBus 发送的事件
	 * @param msg
	 */
	public void onEventMainThread(ResultGetCommsion msg) {
		if(msg!=null){
			if(msg.TModel.size()>0){
				getCList.clear();
				this.getCList.addAll(msg.TModel);
//				initData();
				commsionAdapter.notifyDataSetChanged();
			}
		}
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_stay_earnings_layout, container, false);
        ViewUtils.inject(this, view); // 注入view和事件
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        commsionNet=new GetCommsionNet(getActivity());
		commsionNet.setData(Constant.SETTLEMENT,1,1);
        getCList=new ArrayList<GetCommsionBean>();
		commsionAdapter=new GetCommsionListAdapter(getActivity(), getCList);
        tv_stay_list.setAdapter(commsionAdapter);
    }
	
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
