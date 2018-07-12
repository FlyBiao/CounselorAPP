package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultExpressBean.ExpressBean;

/**
 * 物流公司数据适配器[此处写成了内部类]
 * @author fgb
 *
 */
public class ExpressAdapter extends BaseAdapter{

	private Context ct;
	private int index = -1;//// 标记用户当前选择的那一快递
	private List<ExpressBean> data=new ArrayList<ExpressBean>();
	
	public ExpressAdapter(Context ct){
		this.ct=ct;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<ExpressBean> list) {
		this.data = list;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_express_list, null);
			viewHolder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			viewHolder.selectBtn=(RadioButton) convertView.findViewById(R.id.radio);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final ExpressBean bean=data.get(position);
		viewHolder.tv_name.setText(bean.Name);
		
		viewHolder.selectBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					Toast.makeText(ct, "" + bean.Id,Toast.LENGTH_LONG).show();
					index = position;
					notifyDataSetChanged();
				}
			}

		});
		
		if (index == position) {//判断选中的条目和当前的条目是否相等
			viewHolder.selectBtn.setChecked(true);
		} else {
			viewHolder.selectBtn.setChecked(false);
		}
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView tv_name;
		RadioButton selectBtn;
	}

}
