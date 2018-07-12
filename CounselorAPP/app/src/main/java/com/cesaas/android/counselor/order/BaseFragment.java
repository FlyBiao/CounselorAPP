package com.cesaas.android.counselor.order;

import io.rong.eventbus.EventBus;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;

import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

public abstract class BaseFragment extends Fragment {

	protected BitmapUtils bitmapUtils;
	
	protected AbPrefsUtil abpUtil;
	/** Fragment当前状态是否可见 */
	protected boolean isVisible;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		abpUtil = AbPrefsUtil.getInstance();
		EventBus.getDefault().register(this);
		
		bitmapUtils = BitmapHelp.getBitmapUtils(getContext().getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(getContext()).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}

	/**
	 * 可见
	 */
	protected void onVisible() {
		lazyLoad();
	}

	/**
	 * 不可见
	 */
	protected void onInvisible() {

	}
	/* 取资源值 */
	protected String getRstring(int r) {
		return App.mInstance().getResources().getString(r);
	}
	/**
	 * 延迟加载 子类必须重写此方法
	 */
	protected abstract void lazyLoad();

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void onEventMainThread(Message msg) {
	}

	public void onEventBackgroundThread(Message msg) {
	}

}
