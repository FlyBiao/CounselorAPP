package com.cesaas.android.counselor.order.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.VersionInfoBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.global.App;
import com.google.gson.Gson;

/**
 * 自动检查版本更新类
 *
 * @author FlyBiao
 *
 */
public class NotificationUpdate {
	public static final String TAG="NotificationUpdate";

	//正式服务器
	public static final String DOWNIP = "http://m.cesaas.com/Dowlond/Android/Counselor.apk"; // 下载地址
	public static final String DOWNJSON = "http://m.cesaas.com/Dowlond/Android/Counselor.json"; // 版本比对JSON

	//测试服务器
//	public static final String DOWNIP = "http://112.74.135.25/Dowlond/Android/Counselor.apk"; // 下载地址
//	public static final String DOWNJSON = "http://112.74.135.25/Dowlond/Android/Counselor.json"; // 版本比对JSON

	//本地tomcat测试
//	public static final String DOWNJSON = "http://192.168.1.170:8080/jsons/Counselor.json"; 
//	public static final String DOWNIP = "http://192.168.1.170:8080/jsons/Counselor.apk";

	private static Context mContext;
	private boolean mshow; // 显示没有更新
	private static boolean downing; //

	public static final int NOTIFY_ID = 20151118;
	private int progress;
	private NotificationManager mNotificationManager;
	public boolean canceled;
	/* 下载包安装路径 */
	private final String savePath;
	private final String saveFileName;

	private static NotificationUpdate ntfupdate;

	public int verCode;
	public String newInfo;

	public static NotificationUpdate mInstance(Context context) {

		mContext = context;
		if (ntfupdate == null) {
			ntfupdate = new NotificationUpdate(context);
		}
		downing = false;
		return ntfupdate;
	}

	// 构造方法
	private NotificationUpdate(Context context) {
		savePath = AbFileUtil.getCachePathManual(context);
		saveFileName = savePath + "Counselor.apk";
		mNotificationManager = (NotificationManager) context
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	// 主要检查方法，是否显示没有更新对话框
	public void mCheckVersions(boolean show) {
		if (downing) {
		} else {
			this.mshow = show;
			getJSONByVolley();
		}
	}

	/**
	 * 利用Volley获取JSON数据      
	 */
	private void getJSONByVolley() {
		App.mInstance().mExecutorService.execute(readJsonRunable);
	}

	// 提示检测到新版本下载 并启动服务后台下载
	private void showUpdateDialog(String item) {

		new MyAlertDialog(mContext).mInitShow("检测到新版本", item + "\n是否现在下载更新?", "下载更新", "暂不更新", new ConfirmListener() {
			@Override
			public void onClick(Dialog dialog) {
				App.mInstance().setDownload(true);
				downing = true;
				if (downLoadThread == null || !downLoadThread.isAlive()) {
					progress = 0;
					setUpNotification();
					new Thread() {
						public void run() {
							// 点击下载更新去下载
							startDownload();

						};
					}.start();
				}
			}
		}, null);
	}

	public interface ICallbackResult {
		public void OnBackResult(Object result);
	}

	private Handler mHandler = new Handler() {


		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					// app.setDownload(false);
					// 下载完毕
					// 取消通知
					mNotificationManager.cancel(NOTIFY_ID);
					installApk();
					break;
				case 2:
					// app.setDownload(false);
					// 这里是用户界面手动取消，所以会经过activity的onDestroy();方法
					// 取消通知
					mNotificationManager.cancel(NOTIFY_ID);
					break;
				case 1:
					int rate = msg.arg1;
					// app.setDownload(true);
					if (rate < 100) {
						RemoteViews contentview = mNotification.contentView;
						contentview.setTextViewText(R.id.tv_progress, rate + "%");
						contentview.setProgressBar(R.id.progressbar, 100, rate, false);
					} else {
						// 下载完毕后变换通知形式
						mNotification.flags = Notification.FLAG_AUTO_CANCEL;
						mNotification.contentView = null;
						Intent intent = new Intent(mContext, NotificationUpdate.class);
						// 告知已完成
						intent.putExtra("completed", "yes");
						// 更新参数,注意flags要使用FLAG_UPDATE_CURRENT
						PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent,
								PendingIntent.FLAG_UPDATE_CURRENT);
						//setLatestEventInfo需要重新处理 不然报错
//						mNotification.setLatestEventInfo(mContext, "下载完成", "文件已下载完毕", contentIntent);
					}
					mNotificationManager.notify(NOTIFY_ID, mNotification);
					break;
				case 0xD:
					if (msg.obj != null && !"".equals(msg.obj.toString()) && !"null".equals(msg.obj.toString())) {
						try {
							String strJson=msg.obj.toString();

							Gson gson=new Gson();
							VersionInfoBean bean = gson.fromJson(strJson, VersionInfoBean.class);
							if (bean.verCode> AbAppUtil.getAppVcode(mContext)) {
								if (App.mInstance().isDownload()) {
									ToastFactory.getToast(mContext, "正在下载中");
								} else {
									showUpdateDialog(bean.newInfo);//显示dialog窗口
								}
							} else {
								showNew();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							showNew();
						}
					} else {
						showNew();
					}
					break;
			}
		}
	};

	private void showNew() {
		if (mshow) {
			ToastFactory.getToast(mContext, "恭喜!您的版本已经是最新的！\n当前版本：v " + AbAppUtil.getAppVersion(mContext));
		}
	}

	private void startDownload() {
		canceled = false;
		downloadApk();
	}

	//
	Notification mNotification;

	// 通知栏
	/**
	 * 创建通知
	 */
	private void setUpNotification() {
		int icon = R.drawable.icon;
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.notification_download);
		contentView.setTextViewText(R.id.name, mContext.getString(R.string.app_name) + "APP正在更新下载...");
		contentView.setTextColor(R.id.name, 0xffffffff);
		// 指定个性化视图
		mNotification.contentView = contentView;

		Intent intent = new Intent(mContext, NotificationUpdate.class);
		// 下面两句是 在按home后，点击通知栏，返回之前activity 状态;
		// 有下面两句的话，假如service还在后台下载， 在点击程序图片重新进入程序时，直接到下载界面，相当于把程序MAIN 入口改了 - -
		// intent.setAction(Intent.ACTION_MAIN);
		// intent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// 指定内容意图
		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	//
	/**
	 * 下载apk
	 *
	 * @param url
	 */
	private Thread downLoadThread;

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 *
	 * @param
	 */
	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		try {
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
			mContext.startActivity(i);
			App.mInstance().setDownload(false);
			downing = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int lastRate = 0;
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(DOWNIP);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				if (ApkFile.exists()) {
					ApkFile.delete();
				}
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.arg1 = progress;
					if (progress >= lastRate + 1) {
						mHandler.sendMessage(msg);
						lastRate = progress;
					}
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(0);
						// 下载完了，cancelled也要设置
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				} while (!canceled);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	private Runnable readJsonRunable = new Runnable() {

		@Override
		public void run() {

			// TODO Auto-generated method stub
			StringBuffer newVerjson = new StringBuffer();
			HttpClient client = new DefaultHttpClient(); // 新建http
			// 客户端
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 3000); // 设置连接超时范围
			HttpConnectionParams.setSoTimeout(httpParams, 5000);
			// DOWNJSON是version.json的路径
			try {
				HttpResponse response = client.execute(new HttpGet(DOWNJSON));
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"),
							8192);
					String line = null;
					while ((line = reader.readLine()) != null) {
						newVerjson.append(line + "\n"); // 按行读取放入String中
					}
					reader.close();
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = mHandler.obtainMessage();

			msg.what = 0xD;
			msg.obj = newVerjson.toString();
			mHandler.sendMessage(msg);
		}
	};

}
