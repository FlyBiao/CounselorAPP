package com.cesaas.android.counselor.order.label.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.label.bean.ResultGetCategoryListBean.GetCategoryListBean;

public class AddCreateLabelAdapter extends BaseAdapter {

	private ArrayList<GetCategoryListBean> categoryListBeans;
	
	Context context;
	// 存布尔值的pair
	SparseBooleanArray selected;
	// 标识
	boolean isSingle = true;
	// 默认第一个选中
	int old = 0;
	
	public AddCreateLabelAdapter(ArrayList<GetCategoryListBean> list, Context context) {
		this.categoryListBeans = list;
		this.context = context;
		selected = new SparseBooleanArray();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categoryListBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categoryListBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHloder vh;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_create_label, null);
			vh = new ViewHloder();
//			vh.iv_opreation = (ImageView) convertView
//					.findViewById(R.id.iv_opreation);
			vh.tv_label_category_names = (TextView) convertView.findViewById(R.id.tv_label_category_names);
			vh.tv_xuanzhong=(TextView) convertView.findViewById(R.id.tv_xuanzhong);
			convertView.setTag(vh);
		} else {
			vh = (ViewHloder) convertView.getTag();
		}
		
		vh.tv_label_category_names.setText(categoryListBeans.get(position).CategoryName);
		// 选中状态
		if (selected.get(position)) {
			vh.tv_label_category_names.setTextColor(context.getResources().getColor(
					R.color.color_title_bar));
			vh.tv_xuanzhong.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selector_img_express_pay));
			
		} else {
			vh.tv_label_category_names.setTextColor(context.getResources().getColor(
					R.color.c1));
			vh.tv_xuanzhong.setBackgroundColor(context.getResources().getColor(R.color.day_sign_content_text_white_30));
			
		}
		return convertView;
	}
	
	private class ViewHloder {
		TextView tv_label_category_names;
		TextView tv_xuanzhong;
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
