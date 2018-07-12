package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetCounselorListBean.CounselorListBean;

/**
 * 顾问列表Adapter
 * @author FGB
 *
 */
public class CounselorListAadapter extends BaseAdapter{

	private static final String TAG = "CounselorListAadapter";
	private Context ct;
	private List<CounselorListBean> data=new ArrayList<CounselorListBean>();
	
	public CounselorListAadapter(Context ct,List<CounselorListBean> data){
		this.ct=ct;
		this.data=data;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<CounselorListBean> list) {
		this.data = list;
		notifyDataSetChanged();
	}
	

	public void remove(CounselorListBean user) {
		this.data.remove(user);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_counselor_list, null);
			viewHolder.tv_counselor_id=(TextView) convertView.findViewById(R.id.tv_counselor_id);
			viewHolder.tv_counselor_name=(TextView) convertView.findViewById(R.id.tv_counselor_name);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		CounselorListBean bean=data.get(position);
		viewHolder.tv_counselor_id.setText("ID:"+bean.COUNSELOR_ID);
		viewHolder.tv_counselor_name.setText(bean.COUNSELOR_NAME);
		Log.i(TAG, bean.COUNSELOR_NAME+"ID"+bean.COUNSELOR_ID);
		return convertView;
	}
	
	static class ViewHolder {
		TextView tv_counselor_id;
		TextView tv_counselor_name;
		
	}

}
