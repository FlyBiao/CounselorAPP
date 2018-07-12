package com.cesaas.android.counselor.order.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lidroid.xutils.util.LogUtils;

public class ThreadPool {
	static ExecutorService threadPool;

	public static void initThreadPool(int max) { // 可以在Application中进行配置
		if (max > 0) {
			max = max < 3 ? 3 : max;
			threadPool = Executors.newFixedThreadPool(max);
		} else {
			threadPool = Executors.newCachedThreadPool();
		}

		LogUtils.d("[ThreadPool]ThreadPool init success...max thread: " + max);

	}

	public static ExecutorService getInstances() {
		return threadPool;
	}

	public synchronized static <U, R> void go(Runtask<U, R> runtask) {
		if (null == threadPool) {
			LogUtils.e("ThreadPool没有被初始化，请在Application中进行初始化操作...");
			return;
		}
		// runtask.onBefore();
		threadPool.execute(runtask);
	}

	public synchronized static void go(Runnable runnable) {
		if (null == threadPool) {
			LogUtils.e("ThreadPool没有被初始化，请在Application中进行初始化操作...");
			return;
		}
		threadPool.execute(runnable);
	}

}
