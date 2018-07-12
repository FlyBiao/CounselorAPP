package com.cesaas.android.counselor.order.global;


/**
 * 描述：常量.
 * 
 * @author FGB
 * @date 2015-9-19
 * 
 */
public class Constant {
	

	public static final String TAG = "onSuccess";
	public static final String JAVA_TAG = "javaLog";
	/** 日志开关 */
	public static boolean DEBUG = false;

	/** UI设计的基准宽度 */
	public static int UI_WIDTH = 720;

	/** UI设计的基准高度 */
	public static int UI_HEIGHT = 1280;

	/** UI设计的密度 */
	public static int UI_DENSITY = 2;
	/** 分页参数 */
	public static int PAGE_SIZE = 30;
	/** 列表总数 */
	public static int TOTAL_COUNTER =0;


	// 下拉上拉的显示时间
	public static int TOPSHOWTIME = 2000;
	public static int BOTTOMSHOWTIME = 500;

	public static String Express_QUERY="http://m.kuaidi100.com/index_all.html?type=&postid=";//订单物流查询接口
	public static String COUNSELOR_MESSAGE="http://a.cesaas.com:88/mob/adviser/chat?adviserid=2&gzno=";//顾问发送微信消息

	//知心天气URL
	public static String WEATHER="https://api.seniverse.com/v3/weather/";
	//
	public static String CHINA_WEATHER="http://api.openweathermap.org/data/2.5/forecast?";

	//生成环境 m.cesaas.com 测试环境 a.cesaas.com
	public static String IP="http://m.cesaas.com/EpApi/";//
	public static String Express_IP="http://m.cesaas.com/OpenApi/";//快递物流

	//java
	public static String JAVA_IP="http://120.79.128.56:40000/tarsApi/";
	public static String JAVA_TEST_IP="http://120.77.158.83:40000/tarsApi/";

	
	/**
	 * 测试环境
	 */
	public static String Test_IP = "http://a.cesaas.com/EpApi/";//
	public static String Pos_IP = "http://pos.cesaas.com/EpApi/";//


	/** 列表一次请求的数量 */
	public static String MAXROW = "30";

	/** 图片处理：裁剪 */
	public static final int CUTIMG = 0;
	/** 图片处理：缩放 */
	public static final int SCALEIMG = 1;
	

	/** 标示是否登录 */
	public static String SPF_ISLOGIN = "isLogin";
	/** 帐号（手机号） */
	public static String SPF_ACCOUNT = "account";
	/** 登陆服务器切换 [默认正式服务器]*/
	public static String IS_SWITCH_SERVER ="isSwitchServer";
	// 昵称
	public static String SPF_NICK = "nick";
	// 用户头像
	public static String SPF_UIMG = "img";
	// 积分
	public static String SPF_POINT = "point";
	public static String VIP_ID = "vipId";
	// 支付密码
	public static String SPF_ISPAYPWD = "ispay_pwd";
	/** 时间参数 */
	public static String SPF_TIME = "t_time";
	/** 请求的TOKEN */
	public static String SPF_TOKEN= "token";
	public static String SPF_AUTHKEY= "AuthKey";
	/** 区域编码 */
	public static String SPF_ACODE = "area_code";
	/** 是否推送 */
	public static String SPF_MSG_PUSH = "msg_push";
	// 城市区域
	public static String SPF_CITY = "l_city";
	// 地址
	public static String SPF_ADDR = "l_addr";
	// public static String SPF_LNG = "l_lng";
	// public static String SPF_LAT = "l_lat";
	
	//在线发货
	public static String ONLIN_SEND="zero";
	//物流发货
	public static String EXPRESS_SEND="1";
	//扫描发货
	public static String SCAN_SEND="2";

	// 全部,(已取消订单)
	public static String XFOS_ALL = "zero";
	// 新增订单(未支付订单)
	public static String XFOS_ADDED = "10";
	// 支付中(第三方支付已返回支付结果，服务器还未获取到第三方支付回调，此时订单状态还不是支付成功)
	public static String XFOS_PAYING = "20";
	// 已支付
	public static String XFOS_PAYED = "30";
	// 已发货
	public static String XFOS_SHIPMENTS = "40";
	// 交易退款后关闭
	public static String XFOS_CLOSE = "80";
	// 买家或卖家主动取消
	public static String XFOS_CANCEL = "60";
	// 完成
	public static String XFOS_FINISHED = "100";

	// 支付宝回调
	// public static String ALIPAYRESULT = IP +
	// "payapi/Notifypay/AliNotifyUrl/Index";
	// // 微信支付回调
	// public static String WXRESULT = IP +
	// "payapi/Notifypay/WxNotifyUrl/Index";

	// 商家版API
	public static class API {

		public static final String BASE_URL = "http://112.74.135.25/EpApi/User/";
		// public static final String
		// BASE_URL="http://192.168.1.113/EpApi/User/";
		// 用户登录
		public static final String USER_LOGIN = BASE_URL + "Sw/Account/Login";
		// 个人信息
		public static final String USER_DETAIL = BASE_URL + "Sw/User/Detail";
		// 上传用户头像
		public static final String USER_UPLOAD_HEAD_ICON = BASE_URL
				+ "Sw/User/UploadHeadIcon";
		// 用户修改昵称
		public static final String USER_SET_NICK = BASE_URL
				+ "Sw/User/ModifyName";
		// 修改用户密码
		public static final String USER_SET_PWD = BASE_URL
				+ "Sw/Password/Reset";

		// 粉丝List
		public static final String USER_FANS = BASE_URL + "Sw/Fans/GetList";

	}

	//================OrderDistStatus接单订单状态====================//
	
	/**
	 * 等待顾问接单
	 */
	public static int WAIT_CONSULTANT_RECEIVING = 0;
	/**
	 * 顾问已接单
	 */
	public static int CONSULTANT_RECEIVED = 10;

	/**
	 * 顾问已确认，待卖家发货
	 */
	public static int StringCONSULTANT_CONFIRM = 20;
	/**
	 * 顾问确认无货，待卖家确认
	 */
	public static int StringOUT_OF_STOCK = 30;
	
	//================分销订单状态OrderStatus====================//
	/**
	 * 10:订单已创建，待买家付款 
	 */
	public static int WAIT_BUYER_PAY=10; 
	/**
	 * 20:订单支付中 
	 */
	public static int TRADE_PAYING = 20;
	/**
	 * 30:买家已付款，待卖家发货 
	 */
	public static int WAIT_SELLER_SEND = 30;
	/**
	 * 40:卖家已发货，待卖家确认
	 */
	public static int WAIT_BUYER_CONFIRM =40 ; 
	/**
	 * 80:交易退款后关闭 
	 */
	public static int TRADE_CLOSED = 80; 
	/**
	 *  90:交易取消，买家卖家主动取消  
	 */
	public static int TRADE_CANCEL = 90; 
	/**
	 * 100:交易完成 
	 */
	public static int TRADE_FINISHED =100 ;
	
	//================订单快递方式====================//
	/**
	 * zero:快递配送
	 */
	public static int Express = 0;
	/**
	 * 1:到店取货
	 */
	public static int StoreExtract=1;
	
	//================我的收益佣金状态====================//
	/**
	 * zero:暂时冻结
	 */
	public static int FREEZE=0;
	/**
	 * 1:可以结算
	 */
	public static int SETTLEMENT=1;
	/**
	 * 2:已经收
	 */
	public static int RECEIVE=2;
	/**
	 * 3:不能结算
	 */
	public static int NO_SETTLEMENT=3;
	
	
	//================web 页面加载Type====================//
	//web页面加载 1
    public static int LOAD_WEB_URL=1; 
    //收益详情列表 2
    public static int LOAD_APP_EARNINGS_DETAIL=2;
     //提现页面 5
    public static int LOAD_APP_DEPOSIT=5;
    //首页_店铺 6
    public static int LOAD_APP_SHOP=6;
    //首页_接单 7
    public static int LOAD_APP_RECEIVE_ORDER=7;
    //首页_任务 8
    public static int LOAD_APP_TASK=8;
    //首页_粉丝 9
    public static int LOAD_APP_FANS=9;
    //首页_我的 10
    public static int LOAD_APP_MY=10;
    //顾问微信二维码页面 11
    public static int LOAD_APP_WX_QR=11;
    //post收银 12
    public static int LOAD_APP_POS_ORDER=12;
    //分享商品 13
    public static int LOAD_APP_SHARE_SHOP_GOODS=13;
    //培训资料 14
    public static int LOAD_APP_TRAIN_DATA=14;
    //考试列表 15
    public static int LOAD_APP_TEST_LIST=15;
    //店铺报数 16
    public static int LOAD_APP_SHOP_COUNT=16;
    //店员管理 17
    public static int LOAD_APP_SHOP_STAFF_MANGER=17;
    //店铺订单 18
    public static int LOAD_APP_SHOP_ORDER=18;
    //店铺业绩 19
    public static int LOAD_APP_SHOP_RESULTS=19;
    //店铺粉丝 20
    public static int LOAD_APP_SHOP_FANS=20;
    //订单详情 21
    public static int LOAD_APP_ORDER_DETAIL=21;
    //粉丝详情 22
    public static int LOAD_APP_FANS_DETAIL=22;
    //标签会员列表 23
    public static int LOAD_APP_VIP_LABEL_LIST=23;
    //添加标签 24
    public static int LOAD_APP_ADD_LABEL=24;
    //话术列表 25
    public static int LOAD_APP_WORDS_LIST=25;
    //app设置页 26
    public static int LOAD_APP_SETTINGS=26;
    //会员聊天页 27
    public static int LOAD_APP_VIP_SESSION=27;
    //登陆 28
    public static int LOAD_APP_LOGING=28;
    //上传文件29 
    public static int UP_LOAD_FILE=29;
	//打电话
	public static int CALL=30;
	//发短信
	public static int MESSAGE=31;
	//标签筛选
	public static int TAG_SCREEN=32;
	//分享
	public static int SHARE=34;

	//35  提示文字接口  参数 = ["要提示的文字","success"]
	//36  确认弹窗  参数 =  ["标题","提示消息"]  回调参数  确定 = [1]  取消 = [0]
	//37  弹窗输入框  参数 =  ["标题","提示消息","输入框1提示文字","输入框2提示文字"…..]



    
    
//    AliOss:{
//        region:'oss-cn-shenzhen',
//        accessKeyId:'9IlE3kJs4FdQfq9c',
//        accessKeySecret:'mfjWa1a7L2OC2vf78PZrPmOhy6f41U ',
//        bucket:'shenzhentesting'
//    }
//AliOss:{
//        region:'oss-cn-shenzhen',
//        accessKeyId:'9IlE3kJs4FdQfq9c',
//        accessKeySecret:'mfjWa1a7L2OC2vf78PZrPmOhy6f41U ',
//        bucket:'shenzhenluohu'
//    }


	public static int H5_PICTURE_UPLOAD=111;////图片上传
	public int  H5_PICTURE_PREVIEW=112;//图片预览

	public static int H5_PRODUCT_SELECT=301;//选择商品
	public static int H5_COLLOCATE_SELECT=302;//选择搭配
	public static int H5_PRODUCT_SHARE=301;//分享商品

	public static int H5_MEMBER_SELECT=401;//选择会员
	public static int H5_VERBALTRICK_SELECT=402;//选择话术
	public static int H5_FACE_BIND=403;//人脸绑定

	public static int H5_CLERK_SELECT=701;//选择店员

	public static int H5_TAG_SELECT=901;//选择标签


	public static int ORDER_BACK=1000;//退单
	public static int ORDER_REFUSED=1001;//拒绝订单
}
