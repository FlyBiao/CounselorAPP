package com.flybiao.adapterlibrary;

import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import android.app.Application;
import android.graphics.Bitmap;

public class App extends Application{

	private static App myapp = null;
	public BitmapDisplayConfig bitmapConfig;
	
	public static App mInstance() { // 单例实例化
		return myapp;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		myapp = this;
		
		initImage();
		
	}
	
	private void initImage() {
		bitmapConfig = new BitmapDisplayConfig();
		// bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用,
		// 图片太大时容易OOM。
		bitmapConfig.setBitmapConfig(Bitmap.Config.RGB_565);
		bitmapConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(this));
	}
}
