package com.cesaas.android.counselor.order.global;

/**
 * 所有接口URL
 * @author FGB
 *
 */
public class Urls {

	//生成环境 m.cesaas.com 测试环境 a.cesaas.com
	public static String SERVICE = "http://a.cesaas.com/EpApi/";//测试
	public static String H5_BASE_URL="http://sw.cesaas.com:84/";//正式
	public static String H5_BASE_TEST_URL="http://a.cesaas.com:84/";//测试

	//登录
	public static String LOGIN=SERVICE+"User/Sw/Account/Login";
	//修改密码
	public static String MOBIFY_PWD=SERVICE+"User/Sw/Password/Reset";
	//用户详情
	public static String USER_INFO=SERVICE+"User/Sw/User/Detail";
	//修改昵称
	public static String MOBIFY_NAME=SERVICE+"User/Sw/User/ModifyName";
	//检查粉丝在线状态
	public static String CHECK_ON_LINE=SERVICE+"User/Sw/Fans/CheckOnline";
	//添加店员
	public static String ADD_SHOP_STAFF=SERVICE+"User/SW/Counselor/Add";
	//修改店员
	public static String SAVE_SHOP_STAFF=SERVICE+"User/SW/Counselor/Save";
	
	//粉丝详情
	public static String FANS_DETAILS=SERVICE+"User/Sw/Fans/Detail";
	//按粉丝查询列表
	public static String FANS_LIST=SERVICE+"User/Sw/Fans/GetList";
	//按店铺查询粉丝
	public static String GET_LIST_BY_SHOP_ID=SERVICE+"User/Sw/Fans/GetListByShopId";
	
	
	//退单
	public static String ORDERE_STORE_BACK=SERVICE+"Order/Sw/Express/OrderStoreBack";
	//发货
	public static String OFF_LINE_CONFRIM=SERVICE+"Order/Sw/Express/OfflineConfrim";
	//在线抢单发货
	public static String ON_LINE_CONFRIM=SERVICE+"Order/Sw/Express/OnlineConfrim";
	//无货
	public static String ORDER_BACK=SERVICE+"Order/Sw/Order/OrderBack";
	public static String ORDER_OUT_STOCK=SERVICE+"Order/Sw/Order/OrderOutStock";
	//有货
	public static String CONFRIM_ORDER="Order/Sw/Order/ConfirmOrder";
	public static String RECEIVEING_FONFIRM_ORDER="Order/Sw/Order/ReceivingFonfirmOrder";
	//我的订单【发货订单/粉丝订单】ByDevelop：粉丝订单 : zero   发货订单 :1
	public static String GET_BY_COUNSELOR=SERVICE+"Order/Sw/Order/GetByCounselor";
	//收银
	public static String CREATE_FROM_STORE=SERVICE+"Order/Sw/StoreCashi/CreateFromStore";
	//POS在线下单
	public static String CREATE_ORDER=SERVICE+"Order/Sw/Express/CreateOrder";
	//确认发货订单详情
	public static String GET_CODE_ORDER=SERVICE+"Order/Sw/Order/GetCodeOrder";
	//订单详情
	public static String GET_DISTRIBUTION_ORDER=SERVICE+"Order/Sw/Order/GetDistributionOrder";
	public static String GET_ORDER=SERVICE+"Order/Sw/Order/GetOrder";
	//店铺订单
	public static String GET_SHOP_ORDER=SERVICE+"Order/Sw/Order/GetShopOrder";
	//待接单
	public static String GET_UN_RECEIVE_ORDER="Order/Sw/Order/GetUnReceiveOrder";
	//排行
	public static String dd="Order/Sw/Report/OrderRankingByCounselor";
	//到店发货
	public static String SEND_ORDER=SERVICE+"Order/Sw/Express/SendOrder";
	
	//获取条码商品
	public static String GET_BY_BARCODE_CODE=SERVICE+"Marketing/Sw/Style/GetByBarcodeCode";
	//根据商品款号获取商品图片
	public static String GET_STYLE_PICTURE="Marketing/Sw/Style/GetStylePicture";
	
	//店铺报数
	public static String ADD_SALEGOAL="Marketing/Sw/SaleGoal/Add";
	//获取店铺报数数据
	public static String GET_ONE_MOUTH_SALE="Marketing/Sw/SaleGoal/GetOneMouthSale";
	//获取当天报数
	public static String GET_ONE_SALE=SERVICE+"Marketing/Sw/SaleGoal/GetOneSale";
	
	//优惠券列表
    public static String GET_USER_TICKET=SERVICE+"Marketing/Sw/Ticket/GetUseTicket";
	//优惠券Info查询
	public static String GET_TICKET_INFO=SERVICE+"Marketing/Sw/Ticket/GetTicketInfo";
	//验证优惠券是否可用
    public static String GET_USER_TICKET_Available=SERVICE+"Marketing/Sw/Ticket/TicketIsAvailable";
    
	//话术
	public static String SALES_TALK=SERVICE+"User/Sw/SalesTalk/GetList";
	//给离线的用户发送微信消息
	public static String SEND_WEIXIN_MSG=SERVICE+"User/Sw/Msg/SendWxMsgByVipId";
	//获取容云TOKEN
	public static String GET_TOKEN=SERVICE+"User/Sw/User/GetToken";
	//刷新容云token
	public static String REFERSH_TOKEN=SERVICE+"User/Sw/User/RefershToken";
	
	
	//删除标签
	public static String DEL_TAG=SERVICE+"User/Sw/Tag/DeleteTag";
	//标签列表
	public static String LEBEL_LIST=SERVICE+"User/Sw/Tag/GetList";
	//获取短信验证码
	public static String GET_SEND_CODE=SERVICE+"User/Sw/Regist/SendCode";
	
	
	//物流公司
	public static String EXPRESS=SERVICE+"Express/Sw/Express";
	//在线发货物流列表
	public static String EXPRES_LIST=SERVICE+"Order/Sw/Express/GetList";
	//物流订单查询
	public static String ORDER_QUERY=SERVICE+"Order/Sw/Express/OrderQuery";


	/**************************************H5 Address****************************************/
	//我的收益
	public static String MYINCOME = H5_BASE_URL+"merchant/myincome/ower";
	//店铺业绩
	public static String SHOPINCOME = H5_BASE_URL+"merchant/myincome/shop";
	//排榜
	public static String RANKING = H5_BASE_URL+"merchant/ranking";
	//拉粉
	public static String WEIXIN_LA_FANS=H5_BASE_URL+"merchant/counselor";
	//店员考试
	public static String STAFF_TEST=H5_BASE_URL+"merchant/test?id=";
	//销售达成率报表
	public static String SELA_PERFORMANCE=H5_BASE_URL+"merchant/performance";
	
	//店员任务详情
	public static String SHOP_TASK_DETAIL="merchant/task/edit?id=";


	//获取店务任务详情
	public static String getShopTaskDetail(int workid,int flowid,int formid){
		return H5_BASE_URL+"merchant/form?workid="+workid+"&flowid="+flowid+"&formid="+formid;
	}

	//店员考试
	public static String TEST_STAFF_TEST=H5_BASE_URL+"merchant/test?id=";
	public static String TEST_REPOER_DETAIL=H5_BASE_URL+"merchant/report?no=";
	//任务表单的地址链接，
	//参数说明：formid表单的编号，flowid流程编号，taskid任务编号
	public static String TEST_TASK_DETAILS=H5_BASE_URL+"merchant/form?";
	//会员【http://192.168.1.174:84/merchant/crm/list?tagids=】
	public static String MEMBER=H5_BASE_URL+"merchant/crm/list";
	//商品
	public static String PRODUCT=H5_BASE_URL+"merchant/product";
	//分配业绩
	public static String PERFORMANCE_DISTRIBUTION=H5_BASE_URL+"merchant/achieve";
	//报数
	public static String COUNT_OFF=H5_BASE_URL+"merchant/submit";
	//会员画像
	public static String MEMBER_PORTRAIT=H5_BASE_URL+"merchant/crm/fans?id=";
	//试穿报表
	public static String TRY_REPORT=H5_BASE_URL+"merchant/tryon";
	//进店报表
	public static String INTO_SHOP_REPORT=H5_BASE_URL+"merchant/entershop";
	//商品搭配列表
	public static String SHOP_COLLOCATION=H5_BASE_URL+"merchant/collocate/list";
	//商品销量
	public static String SHOP_SALES=H5_BASE_URL+"merchant/product/ranking";
	//导购排名
	public static String SHOPPING_GUIDE=H5_BASE_URL+"merchant/achieve/myself";
	//服务忠诚度分析
	public static String SERVICE_LOYALTY=H5_BASE_URL+"merchant/crm/service";
	//技术支持
	public static String SERVICE_ABOUT=H5_BASE_URL+"merchant/about";
	//店铺业绩排行
	public static String SHOP_GUIDE=H5_BASE_URL+"merchant/achieve/shop";
	//营销活动列表
	public static String SHOP_ACTIVITY=H5_BASE_URL+"merchant/shopactive";
	//商学院
	public static String BUSINESS=H5_BASE_URL+"merchant/business";
	//邀单计划
	public static String INVITATION_ORDER=H5_BASE_URL+"merchant/orderplan/list";
	//邀单计划详情
	public static String INVITATION_ORDER_DETAIL=H5_BASE_URL+"merchant/orderplan/detail?";
	///merchant/orderplan/detail?id=*&vipid=*&act=*邀单计划详情(编辑act=edit,查看act=view)
	//http://a.cesaas.com:84/merchant/orderplan/list邀单计划列表
	//系统公告
	public static String SYS_NOTICE=H5_BASE_URL+"merchant/notice?id=";
	//业绩查询
	public static String RESULTS_QUERY=H5_BASE_URL+"merchant/submit/reportList";
	//店铺
	public static String RESULTS_SHOP=H5_BASE_URL+"merchant/boss/shop?shop_type=";
	public static String RESULTS_SHOP_ALL=H5_BASE_URL+"merchant/boss/shop";
	//商品
	public static String RESULTS_PRODUCT=H5_BASE_URL+"merchant/boss/product?shop_type=";
	public static String RESULTS_PRODUCT_ALL=H5_BASE_URL+"merchant/boss/product";
	//会员
	public static String RESULTS_VIP_TARGET=H5_BASE_URL+"merchant/boss/crm";
	//日报
	public static String RESULTS_DAILY=H5_BASE_URL+"merchant/boss/daily";
	//商品报表
	public static String RESULTS_MERCHANT_REPORT=H5_BASE_URL+"merchant/report?type=";
	//扫描商品
	public static String RESULTS_MSCAN_SHOP=H5_BASE_URL+"merchant/product/detail?id=";
	//扫描会员卡
	public static String RESULTS_MSCAN_VIP=H5_BASE_URL+"merchant/crm/fans?id=";

}
