package com.cesaas.android.counselor.order.base;


import com.cesaas.android.counselor.order.widget.MyRefreshLayout.OnLoadListener;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.LinearLayout;

public class BaseSearchView extends LinearLayout implements OnRefreshListener, OnLoadListener {

	protected Context mContext;

	public BaseSearchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	public void mRequest(String key) {
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onRefresh() {
	}

}
