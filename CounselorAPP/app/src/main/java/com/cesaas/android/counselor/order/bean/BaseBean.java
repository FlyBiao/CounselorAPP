package com.cesaas.android.counselor.order.bean;


/**
 * 接口公共响应参数
 */
public class BaseBean {
	protected long id;
	public boolean IsSuccess;
	public boolean IsNotSuccess;
	public String Message;
	public String ResultNo;
	public int RecordCount;
	public int PageCount;

	public int errorCode;
	public int totalCount;
	public String errorMessage;
	public String _classname;
	public int numCount;
	public int submitNum;
	public int countBoxNum;   // 总箱数
    public int countNum;      // 总件数
    public int shipmentsNum;      // 发货总数
    public int num;      // 总收货件总数
    public int differenceNum;      // 差异总数
    public int sendNum;      // 收货箱总发货数
    public int nums;      // 收货箱总收货数

}
