package com.cesaas.android.counselor.order.bean;

import java.io.Serializable;

/**
 * 优惠券列表Bean
 * @author FGB
 *
 */
public class GetUserTicketListBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String DateActive;
    public String Id;
    public String Title;
    public String UniqueCode;
    public String UseRule;
    public double Money;
    public int IsUsed;// zero 未使用 1 已使用
}
