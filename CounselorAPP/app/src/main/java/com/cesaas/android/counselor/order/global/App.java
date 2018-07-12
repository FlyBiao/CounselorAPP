package com.cesaas.android.counselor.order.global;

import io.rong.imkit.RongIM;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.imagepicker.imageloader.GlideImageLoader;
import com.cesaas.android.counselor.order.service.NetworkConnectivityListener;
import com.cesaas.android.counselor.order.service.NetworkConnectivityListener.NetworkCallBack;
import com.cesaas.android.counselor.order.utils.ACache;
import com.cesaas.android.counselor.order.utils.AbDataPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.CrashHandler;
import com.cesaas.android.counselor.order.utils.NetworkUtil;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by FGB on 2016/3/7.
 */
public class App extends Application {

	// 用于存放倒计时时间
		public static Map<String, Long> map;
	
	private static App myapp = null;
	public ExecutorService mExecutorService = null; // 线程池
	public BitmapDisplayConfig bitmapConfig;
	private boolean isDownload = false; // 标示正在下载
	// private Mapbean mapBean = null; // 经纬地址信息
	private int maxImgCount = 9;               //允许选择图片最大数

	private int netType; // 网络类型

	public static Typeface font;
	public  static ACache mCache;

	private NetworkConnectivityListener mNetChangeReceiver;
	private NetworkCallBack mNetworkCallBack = new NetworkCallBack() {
		public void getSelfNetworkType(int type) {
			if (netType != type) {
				// setAvailable_http_host("");
			}
			setNetType(type);
		}
	};

	public static App mInstance() { // 单例实例化
		return myapp;
	}

	public static DisplayImageOptions imageLoaderOptions = new DisplayImageOptions.Builder()//
			.showImageOnLoading(R.mipmap.default_image)         //设置图片在下载期间显示的图片
			.showImageForEmptyUri(R.mipmap.default_image)       //设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.mipmap.default_image)            //设置图片加载/解码过程中错误时候显示的图片
			.cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
			.cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
			.build();                                           //构建完成

	@Override
	public void onCreate() {
		super.onCreate();
		mCache = ACache.get(this);
		if (getApplicationInfo().packageName
				.equals(getCurProcessName(getApplicationContext()))
				|| "io.rong.push"
						.equals(getCurProcessName(getApplicationContext()))) {

		/**
		 * IMKit SDK调用第一步 初始化
		 */
		RongIM.init(this);
		}

		ImageLoaderConfiguration config = ImageLoaderConfiguration.createDefault(this);

		ImageLoader.getInstance().init(config);     //UniversalImageLoader初始化

		/**
		 * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
		 * io.rong.push 为融云 push 进程名称，不可修改。
		 */

		initImagePicker();

		initCrashHandler();
		myapp = this;
		mExecutorService = Executors.newFixedThreadPool(8);
		initPrefs();
		// initExp();
		initNet();
		initImage();

		font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");//记得加上这句

	}

	/**
	 * 获得当前进程的名字
	 * 
	 * @param context
	 * @return 进程号
	 */
	public static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	private void initNet() {
		netType = NetworkUtil.getSelfNetworkType(this);
		mNetChangeReceiver = new NetworkConnectivityListener();
		mNetChangeReceiver.startListening(this);
		addNetworkCallBack(mNetworkCallBack);
	}

	/*
	 * private void initExp() {
	 * SDKInitializer.initialize(getApplicationContext()); // 初始化地图
	 * JPushInterface.init(this); // 初始化 JPush
	 * JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志 }
	 */

	/**
	 * 初始化SharedPreference
	 */
	private void initPrefs() {
		AbPrefsUtil.init(this, getPackageName() + "_preference",
				Context.MODE_PRIVATE);
		AbDataPrefsUtil.init(this, getPackageName() + "_net_data",
				Context.MODE_PRIVATE);
	}

	/**
	 * 初始化程序崩溃捕捉处理
	 */
	private void initCrashHandler() {
		CrashHandler.getInstance().init(this);
	}

	private void initImage() {
		bitmapConfig = new BitmapDisplayConfig();
		// bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用,
		// 图片太大时容易OOM。
		bitmapConfig.setBitmapConfig(Bitmap.Config.RGB_565);
		bitmapConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(this));
	}

	public int getNetType() {
		return netType;
	}

	public void setNetType(int netType) {
		this.netType = netType;
	}

	public void addNetworkCallBack(NetworkCallBack mNetworkCallBack) {
		mNetChangeReceiver.registerNetworkCallBack(mNetworkCallBack);
	}

	public void removeNetworkCallBack(NetworkCallBack mNetworkCallBack) {
		mNetChangeReceiver.unregisterNetworkCallBack(mNetworkCallBack);
	}

	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	// 地图
	/*
	 * public void setMapBean(Mapbean mapBean) { this.mapBean = mapBean; }
	 * 
	 * public Mapbean getMapBean() { return mapBean; }
	 */

	/**
	 * 配置方法数处理超过 64K 的应用
	 * @param base
     */
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	private void initImagePicker() {
		ImagePicker imagePicker = ImagePicker.getInstance();
		imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
		imagePicker.setShowCamera(true);                      //显示拍照按钮
		imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
		imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
		imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
		imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
		imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
		imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
		imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
		imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
	}
}
