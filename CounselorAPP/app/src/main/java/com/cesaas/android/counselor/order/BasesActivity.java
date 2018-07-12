package com.cesaas.android.counselor.order;

import io.rong.eventbus.EventBus;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbDataPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.flybiao.materialdialog.MaterialDialog;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.util.LogUtils;

/**
 * Activity 基类
 * 
 * @author Evan
 *
 */
public class BasesActivity extends FragmentActivity {

	/* Activity集合，便于管理 */
	public static ArrayList<Activity> activityList = new ArrayList<Activity>();
	protected Context mContext;
	protected Activity mActivity;
	protected String TAG;
	protected ImageView top_back;
	protected TextView top_title;
	protected App myapp;
	public AbPrefsUtil prefs;
	//缓存
	protected  static ACache mCache;
	protected AbDataPrefsUtil dataPrefs;
	protected BitmapUtils bitmapUtils;
	protected Gson gson;
	protected Bundle bundle;
	protected WaitDialog mDialog;
	protected MaterialDialog materialDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 透明状态栏
		super.onCreate(savedInstanceState);
		//输入法软键盘遮挡输入框实现（界面自动上滑并可滑动）
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		//禁止APP横屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		LogUtils.customTagPrefix = "xUtilsSample"; // 方便调试时过滤 adb logcat 输出
		LogUtils.allowI = false; // 关闭 LogUtils.i(...) 的 adb log 输出
		LogUtils.allowD = false;
		LogUtils.allowE = false;

		mCache = ACache.get(this);
		ViewUtils.inject(this);
		activityList.add(this);
		mContext = this;
		mActivity = this;
		mDialog = new WaitDialog(mContext);
		materialDialog = new MaterialDialog(this);
		myapp = App.mInstance();
		prefs = AbPrefsUtil.getInstance();
		dataPrefs = AbDataPrefsUtil.getInstance();
		gson=new Gson();
		bundle=new Bundle();
		//初始化控件
//		initData();
		
		//通过EventBus订阅事件
		EventBus.getDefault().register(this);
		
		TAG = getLocalClassName().toString();

		bitmapUtils = BitmapHelp.getBitmapUtils(mContext.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);

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

//	protected void initData() {
//		top_title = (TextView) this.findViewById(R.id.top_title);
//		top_back = (ImageView) this.findViewById(R.id.top_back);
//		top_back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				onExit();
//			}
//		});
//	}

	/* 取资源值 */
	protected String getRstring(int r) {
		return App.mInstance().getResources().getString(r);
	}

//	public void onEventMainThread(Message msg) {
//	}
//
//	public void onEventMainThread(HttpException err) { // 错误回调
//	}
//
//	public void onEventMainThread(ResultBean result) { // 成功请求结果回调
//	}

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
