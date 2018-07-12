package com.cesaas.android.counselor.order.bean;

/**
 * 前一天门店报数消息类
 * @author FGB
 *
 */
public class DayBeforeMsg {

	public boolean IsSuccess;
	public int isDayBefore=0;//是否昨天销售信息[1:昨天，zero：当天]
	public String DayBefore;
}
