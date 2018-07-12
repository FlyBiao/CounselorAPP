package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.adapter.CounselorListAadapter;
import com.cesaas.android.counselor.order.bean.ResultGetCounselorListBean;
import com.cesaas.android.counselor.order.bean.ResultGetCounselorListBean.CounselorListBean;
import com.cesaas.android.counselor.order.net.GetCounselorListNet;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 顾问列表
 * @author FGB
 *
 */
@ContentView(R.layout.activity_counselor_list_layout)
public class CounselorListActivity extends BasesActivity{

	@ViewInject(R.id.lv_counselor_list)
	private ListView lv_counselor_list;
	
	private GetCounselorListNet counselorListNet;
	private CounselorListAadapter counselorListAadapter;
	
	private ArrayList<CounselorListBean> counselorList=new ArrayList<CounselorListBean>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		counselorListNet=new GetCounselorListNet(mContext);
		counselorListNet.setData();
	}
	
	public void initData() {
		if (counselorList.size() >0) {
			counselorListAadapter = new CounselorListAadapter(mContext,counselorList);
			lv_counselor_list.setAdapter(counselorListAadapter);
		}
	}
	public void onEventMainThread(ResultGetCounselorListBean msg) {
		this.counselorList.addAll(msg.TModel);
		initData();
	}
}
