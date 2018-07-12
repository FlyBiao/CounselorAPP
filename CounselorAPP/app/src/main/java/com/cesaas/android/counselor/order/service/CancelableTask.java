package com.cesaas.android.counselor.order.service;

/**
 * @version V1.zero
 * @Description 可撤销任务接口
 * @Createdate 14-9-3 15:53
 */
public interface CancelableTask {

	public boolean cancel(boolean mayInterruptIfRunning);
}
