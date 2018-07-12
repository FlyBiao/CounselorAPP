package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * 抽象Adapter
 * @author FGB
 *
 * @param <T>
 */
public abstract class AbstractAdapter<T> extends BaseAdapter {

	protected final Context mContext;
	protected final LayoutInflater mInflater;
	protected final ArrayList<T> mData;

	public AbstractAdapter(Context context, ArrayList<? extends T> data) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		if (data == null) {
			data = new ArrayList<T>(0);
		}
		mData = new ArrayList<T>(data);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 返回列表数据
	 */
	public List<? extends T> getAllData() {
		return mData;
	}

	/**
	 * 将数据放在列表的末尾
	 */
	public void addAll(List<? extends T> list) {
		if (list != null) {
			mData.addAll(list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 将数据放在列表的前面
	 */
	public void addFirst(T t) {
		mData.add(0, t);
		notifyDataSetChanged();
	}

	/**
	 * 将数据放在列表的前面
	 * 
	 * @param list
	 */
	public void addFirstAll(ArrayList<? extends T> list) {
		if (list != null) {
			mData.addAll(0, list);
			notifyDataSetChanged();
		}
	}

	/**
	 * 删除数据
	 */
	public void remove(T t) {
		mData.remove(t);
	}

	/**
	 * 删除数据
	 */
	public void remove(int pos) {
		mData.remove(pos);
	}

	/**
	 * 清除数据
	 */
	public void clear() {
		mData.clear();
	}

}

