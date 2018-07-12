package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultCounselorLevelBean.CounselorLevelBean;

/**
 * 自定义CustomCunselorLevelAdapter
 * @author FGB
 *
 */
public class CustomCunselorLevelAdapter extends BaseAdapter{

	private ArrayList<CounselorLevelBean> beans;
	Context context;
	// 存布尔值的pair
	SparseBooleanArray selected;
	// 标识
	boolean isSingle = true;
	// 默认第一个选中
	int old = 0;
	
	public CustomCunselorLevelAdapter(ArrayList<CounselorLevelBean> list, Context context) {
		this.beans = list;
		this.context = context;
		selected = new SparseBooleanArray();
	}
	
	@Override
	public int getCount() {
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHloder vh;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_level_list, null);
			vh = new ViewHloder();
			vh.tv_level_name = (TextView) convertView.findViewById(R.id.tv_level_name);
			vh.tv_select=(TextView) convertView.findViewById(R.id.tv_select);
			convertView.setTag(vh);
		} else {
			vh = (ViewHloder) convertView.getTag();
		}
		
		vh.tv_level_name.setText(beans.get(position).getTitle());

		// 选中状态
		if (selected.get(position)) {
			vh.tv_level_name.setTextColor(context.getResources().getColor(
					R.color.color_title_bar));
			vh.tv_select.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selector_img_express_pay));
			
		} else {
			vh.tv_level_name.setTextColor(context.getResources().getColor(
					R.color.c1));
			vh.tv_select.setBackgroundColor(context.getResources().getColor(R.color.day_sign_content_text_white_30));
			
		}
		return convertView;
	}
	
	private class ViewHloder {
		TextView tv_level_name;
		TextView tv_select;
	}
	
	/**
	 * 切换选中项
	 * 
	 * @param selected
	 */
	public void setSelectedItem(int selected) {
		// 把上一个状态设置false
		if (isSingle == true && old != -1) {
			this.selected.put(old, false);
		}
		// 设置为选中状态
		this.selected.put(selected, true);
		// 替换
		old = selected;
	}

}
