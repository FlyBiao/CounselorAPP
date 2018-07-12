package com.cesaas.android.counselor.order.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbDataPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;

import io.rong.eventbus.EventBus;

/**
 * Activity 基类
 * 
 * @author Evan
 *
 */
public abstract class MyBaseActivity extends FragmentActivity implements View.OnClickListener{

	public Context mContext;
	public Activity mActivity;
	public String TAG;
	public ImageView top_back;
	public TextView top_title;
	public App myapp;
	public AbPrefsUtil prefs;
	public AbDataPrefsUtil dataPrefs;
	public BitmapUtils bitmapUtils;
	public Gson gson;
	public Bundle bundle;
	public WaitDialog mDialog;

	/* Activity集合，便于管理 */
	public static ArrayList<Activity> activityList = new ArrayList<Activity>();
	private SparseArray<View> mViews;

	public abstract int getLayoutId();

	public abstract void initViews();

	public abstract void initListener();

	public abstract void initData();

	public abstract void processClick(View v);

	public void onClick(View v) {
		processClick(v);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mViews = new SparseArray<>();
		setContentView(getLayoutId());

		LogUtils.customTagPrefix = "xUtilsSample"; // 方便调试时过滤 adb logcat 输出
		LogUtils.allowI = false; // 关闭 LogUtils.i(...) 的 adb log 输出
		LogUtils.allowD = false;
		LogUtils.allowE = false;

		ViewUtils.inject(this);
		activityList.add(this);
		mContext = this;
		mActivity = this;
		mDialog = new WaitDialog(mContext);
		myapp = App.mInstance();
		prefs = AbPrefsUtil.getInstance();
		dataPrefs = AbDataPrefsUtil.getInstance();
		gson=new Gson();
		bundle=new Bundle();

		initViews();
		initListener();
		initData();
		
		//通过EventBus订阅事件
		EventBus.getDefault().register(this);
		
		TAG = getLocalClassName().toString();
		LogUtils.d("注册EventBus");
		
		bitmapUtils = BitmapHelp.getBitmapUtils(mContext.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		 {
		  // 透明状态栏
		  getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}

	/**
	 * 通过ID找到对应View
	 * @param viewId
	 * @param <E>
	 * @return
	 */
	public <E extends View> E findView(int viewId) {
		E view = (E) mViews.get(viewId);
		if (view == null) {
			view = (E) findViewById(viewId);
			mViews.put(viewId, view);
		}
		return view;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		EventBus.getDefault().unregister(this);//取消EventBus订阅
		finish();//销毁当前页面
		super.onDestroy();
	}


	/* 取资源值 */
	protected String getRstring(int r) {
		return App.mInstance().getResources().getString(r);
	}


	public void onEventBackgroundThread(Message msg) {
	}

	public String getAccount() {
		return prefs.getString(Constant.SPF_ACCOUNT);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			onExit();
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/* 退出Activity方法，同时清除列表 */
	protected void onExit() {
		Skip.mBack(mActivity);
		 if (activityList != null) {
		 for (int i = 0; i < activityList.size(); i++) {
		 if (activityList.equals(this)) {
		 activityList.remove(i);
		 }
		 }
		 }
		 this.finish();
	}

}
