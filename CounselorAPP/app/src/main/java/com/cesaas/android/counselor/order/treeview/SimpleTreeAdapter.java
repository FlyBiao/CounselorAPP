package com.cesaas.android.counselor.order.treeview;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

	public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(Node node , int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_tree_list, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
			viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
			viewHolder.rl_tree_item= (RelativeLayout) convertView.findViewById(R.id.rl_tree_item);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (node.getIcon() == -1) {
			viewHolder.icon.setVisibility(View.INVISIBLE);
			viewHolder.rl_tree_item.setBackgroundResource(R.color.common_gray_6);
		} else {
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());
			viewHolder.rl_tree_item.setBackgroundResource(R.color.transparent);
		}

		viewHolder.label.setText(node.getName());
		
		return convertView;
	}

	private final class ViewHolder {
		ImageView icon;
		TextView label;
		RelativeLayout rl_tree_item;
	}

}
