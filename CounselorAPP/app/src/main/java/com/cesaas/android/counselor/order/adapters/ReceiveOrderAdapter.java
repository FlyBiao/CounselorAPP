package com.cesaas.android.counselor.order.adapters;

import java.util.List;

import android.content.Context;

import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;

/**
 * 接单Adapter
 * @author FGB
 *
 */
public class ReceiveOrderAdapter<T> extends CommonAdapter<T>{

	public ReceiveOrderAdapter(Context context, int layoutId, List<T> datas) {
		super(context, layoutId, datas);
	}

	@Override
	public void convert(ViewHolder holder, T t, int postion) {
		
	}

}
