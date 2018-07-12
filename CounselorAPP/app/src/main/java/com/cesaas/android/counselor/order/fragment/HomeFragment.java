package com.cesaas.android.counselor.order.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.OrderActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ResultsQueryActivity;
import com.cesaas.android.counselor.order.activity.UserCentreActivity;
import com.cesaas.android.counselor.order.activity.WXLaFansActivity;
import com.cesaas.android.counselor.order.activity.adapter.MemberMangerGridviewAdapter;
import com.cesaas.android.counselor.order.activity.adapter.SchoolMangerGridviewAdapter;
import com.cesaas.android.counselor.order.activity.main.adapter.MyWorkAdapter;
import com.cesaas.android.counselor.order.activity.user.NoticeDetailActivity;
import com.cesaas.android.counselor.order.activity.user.NoticeListActivity;
import com.cesaas.android.counselor.order.activity.user.bean.NoticeBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultNoticeBean;
import com.cesaas.android.counselor.order.activity.user.vip.MemberPortraitActivity;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserBean;
import com.cesaas.android.counselor.order.bean.weather.DailyBean;
import com.cesaas.android.counselor.order.bean.weather.ResultsBean;
import com.cesaas.android.counselor.order.bean.weather.adapter.WeatherAdapter;
import com.cesaas.android.counselor.order.bean.weather.bean.ResultChinaWeatherBean;
import com.cesaas.android.counselor.order.boss.ui.activity.BossMainActivity;
import com.cesaas.android.counselor.order.custom.NoticeView;
import com.cesaas.android.counselor.order.custom.chartview.ChartData;
import com.cesaas.android.counselor.order.custom.chartview.JerryChartView;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.AppMenuPower;
import com.cesaas.android.counselor.order.global.BaseChinaWeatherNet;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.BaseWeatherNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.inventory.ui.InventoryMainActivity;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.BusinessActivity;
import com.cesaas.android.counselor.order.member.MemberDistributionActivity;
import com.cesaas.android.counselor.order.member.MessageListActivity;
import com.cesaas.android.counselor.order.net.GetMenuPowerNet;
import com.cesaas.android.counselor.order.pos.CashierMainActivity;
import com.cesaas.android.counselor.order.report.CountOffActivity;
import com.cesaas.android.counselor.order.report.IntoManagerActivity;
import com.cesaas.android.counselor.order.report.IntoReportActivity;
import com.cesaas.android.counselor.order.report.PerformanceDistributionActivity;
import com.cesaas.android.counselor.order.report.TryReportActivity;
import com.cesaas.android.counselor.order.report.TryWearDataActivity;
import com.cesaas.android.counselor.order.report.bean.ResultCounselorSaleBean;
import com.cesaas.android.counselor.order.report.bean.ResultShopSaleBean;
import com.cesaas.android.counselor.order.report.net.ResultGetDayTotalBean;
import com.cesaas.android.counselor.order.shopmange.ClerkManageActivity;
import com.cesaas.android.counselor.order.shopmange.InvitationOrderListActivity;
import com.cesaas.android.counselor.order.shopmange.RecommendShopListActivity;
import com.cesaas.android.counselor.order.shopmange.ShopActivityListActivity;
import com.cesaas.android.counselor.order.shopmange.ShopCollocationActivity;
import com.cesaas.android.counselor.order.shopmange.ShopPerformanceActivity;
import com.cesaas.android.counselor.order.shopmange.ShopSalesActivity;
import com.cesaas.android.counselor.order.shopmange.ShopSelectActivity;
import com.cesaas.android.counselor.order.shopmange.ShoppingGuideActivity;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.view.HandleTaskActivity;
import com.cesaas.android.counselor.order.shoptask.view.ShopTaskListActivity;
import com.cesaas.android.counselor.order.signin.SigninActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.DecimalFormatUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.cesaas.android.counselor.order.widget.gridview.BaseViewHolder;
import com.cesaas.android.counselor.order.widget.gridview.MyGridView;
import com.cesaas.android.order.ui.activity.NewOrderActivity;
import com.google.gson.Gson;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.exception.HttpException;
import com.nostra13.universalimageloader.utils.L;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 *
 * @author FGB
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTvCurrentDay,mTvWeek,tvUserName,tv_more_shoptask,tv_member_card,my_work_task,tv_user_shop_name,tv_user_grade;
    private TextView tv_member_number,tv_counselorSale_sale,tv_shop_sale,tv_new_member_number,tv_counselorSale_target,tv_shop_sale_target;
    private TextView tv_deg,tv_description,tv_high_low,tv_mingt;
    private LinearLayout ll_notice_view;
    private ImageView iv_curr_icon;
    private ImageView iv_mingt_icon;
    private CircleImageView ivwUserIcon;
    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    private MyGridView gridview,gridview_member_manger,gridview_school;
    private ListView lv_weather;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private GetMenuPowerNet menuPowerNet;

    private List<String> menuNames;//菜单名称
    private List<Integer> menuImages;//菜单图片

    //会员管理
    private List<String> memberMenuNames;//菜单名称
    private List<Integer> memberMenuImages;//菜单图片

    //商学院管理
    private List<String> schoolMenuNames;//菜单名称
    private List<Integer> schoolMenuImages;//菜单图片

    private List<ShopTaskListBean> mMyWorkBeanList;
    private List<DailyBean> mWeatherResultsBeanList;

    //用户信息
    private UserInfoNet userInfoNet;
    private UserBean userInfo;

//    private NoticeNet noticeNet;

    private MyWorkAdapter mMyWorkAdapter;

    private TaskListNet mTaskListNet;

    private WeatherNet mWeatherNet;
    private WeatherAdapter mWeatherAdapter;

    private ShopSaleNet mShopSaleNet;
    private GetDayTotalNet mGetDayTotalNet;
    private CounselorSaleNet mCounselorSaleNet;

    private double shopSaleMoney;
    private double shopTargetSaleMoney;
    private double shopSalesScale;
    private double CounselorSaleMoney;
    private double CounselorTargetSaleMoney;
    private double CounselorSalesScale;
    private float vipSunCount;
    private float vipNewCount;
    private float vipCountScale;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    private double longtitude;
    private double latitude;
    private double temp;//摄氏度
    private double temp_min;
    private double temp_max;

    public BadgeView badgeView;
    private JerryChartView jcv,jcv2,jcv3;

    /**
     * 单例
     */
    public static HomeFragment newInstance() {
        HomeFragment fragmentCommon = new HomeFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    public void initViews() {
        ll_notice_view=findView(R.id.ll_notice_view);

        lv_weather=findView(R.id.lv_weather);
        mSwipeRefreshLayout =findView(R.id.swipe_layout);

        tv_mingt=findView(R.id.tv_mingt);
        tv_high_low=findView(R.id.tv_high_low);
        tv_description=findView(R.id.tv_description);
        tv_deg=findView(R.id.tv_deg);
        gridview=findView(R.id.gridview);
        gridview_member_manger= findView(R.id.gridview_member_manger);
        gridview_school=findView(R.id.gridview_school);
        tv_more_shoptask=findView(R.id.tv_more_shoptask);
        tv_user_shop_name=findView(R.id.tv_user_shop_name);
        tv_member_number=findView(R.id.tv_member_number);
        tv_counselorSale_sale=findView(R.id.tv_counselorSale_sale);
        tv_shop_sale=findView(R.id.tv_shop_sale);
        tv_new_member_number=findView(R.id.tv_new_member_number);
        tv_counselorSale_target=findView(R.id.tv_counselorSale_target);
        tv_shop_sale_target=findView(R.id.tv_shop_sale_target);

        iv_curr_icon=findView(R.id.iv_curr_icon);
        iv_mingt_icon=findView(R.id.iv_mingt_icon);

        my_work_task=findView(R.id.my_work_task);
        badgeView=new BadgeView(getContext());
        badgeView.setTargetView(my_work_task);

        mTvCurrentDay=findView(R.id.tv_current_day);
        mTvWeek=findView(R.id.tv_week);
        ivwUserIcon=findView(R.id.ivw_user_icon);
        tvUserName=findView(R.id.tv_user_name);
        tv_member_card=findView(R.id.tv_member_card);
        tv_user_grade=findView(R.id.tv_user_grade);

        mSwipeMenuRecyclerView=findView(R.id.recycler_my_work_view);
        mSwipeMenuRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器。
        mSwipeMenuRecyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
        mSwipeMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
        mSwipeMenuRecyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。

        //更多任务
        tv_more_shoptask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mNext(getActivity(),ShopTaskListActivity.class);
            }
        });
        ivwUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userInfo!=null){
                    bundle.putString("icon", userInfo.Icon);
                    bundle.putString("nickName", userInfo.Name);
                    bundle.putString("mobile", userInfo.Mobile);
                }
                Skip.mNextFroData(getActivity(), UserCentreActivity.class,bundle);
            }
        });
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSwipeMenuRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //重新加载数据
                    mTvCurrentDay.setText(AbDateUtil.getCurrentDay());
                    mTvWeek.setText(AbDateUtil.getWeek());

//                    noticeNet=new NoticeNet(getContext());
//                    noticeNet.setData(1);
                    //用户信息
                    userInfoNet=new UserInfoNet(getContext());
                    userInfoNet.setData();

                    mTaskListNet=new TaskListNet(getContext());
                    mTaskListNet.setData(1,5);

                    //销售统计
                    mShopSaleNet=new ShopSaleNet(getContext());
                    mShopSaleNet.setData();

                    mGetDayTotalNet=new GetDayTotalNet(getContext());
                    mGetDayTotalNet.setData();

                    mCounselorSaleNet=new CounselorSaleNet(getContext());
                    mCounselorSaleNet.setData();

                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2000);
        }
    };

    @Override
    public void initListener() {}

    @Override
    public void initData() {
        initAMapLocation();

        mTvCurrentDay.setText(AbDateUtil.getCurrentDay());
        mTvWeek.setText(AbDateUtil.getWeek());

//        noticeNet=new NoticeNet(getContext());
//        noticeNet.setData(1);

        //用户信息
        userInfoNet=new UserInfoNet(getContext());
        userInfoNet.setData();

        mTaskListNet=new TaskListNet(getContext());
        mTaskListNet.setData(1,5);

        //销售统计
        mShopSaleNet=new ShopSaleNet(getContext());
        mShopSaleNet.setData();

        mGetDayTotalNet=new GetDayTotalNet(getContext());
        mGetDayTotalNet.setData();

        mCounselorSaleNet=new CounselorSaleNet(getContext());
        mCounselorSaleNet.setData();

        jcv = (JerryChartView) findView(R.id.jcv);
        jcv2 = (JerryChartView) findView(R.id.jcv2);
        jcv3 = (JerryChartView) findView(R.id.jcv3);

        initPowerMenu();
        initMemberMangerMenu();
        initSchoolMangerMenu();

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);

//        menuPowerNet=new GetMenuPowerNet(getContext());
//        menuPowerNet.setData();
    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void fetchData() {

    }

    public void initSchoolMangerMenu(){
        schoolMenuNames=new ArrayList<String>();
        schoolMenuImages=new ArrayList<Integer>();

        schoolMenuNames.add("导购(店员)排名");
        schoolMenuImages.add(R.string.counselor_ranking);
        schoolMenuNames.add("店铺业绩报表");
        schoolMenuImages.add(R.string.store_performance_report);
        schoolMenuNames.add("商品销量列表");
        schoolMenuImages.add(R.string.sales_list);
        schoolMenuNames.add("进店管理报表");
        schoolMenuImages.add(R.string.into_store_manger_report);
        schoolMenuNames.add("试穿报表");
        schoolMenuImages.add(R.string.try_report);

        gridview_school.setAdapter(new SchoolMangerGridviewAdapter(getContext(),schoolMenuNames,schoolMenuImages));
        gridview_school.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 if(schoolMenuNames.get(position).equals("导购(店员)排名")){
                    Skip.mNext(getActivity(), ShoppingGuideActivity.class);

                }else if(schoolMenuNames.get(position).equals("商品销量列表")){
                    Skip.mNext(getActivity(), ShopSalesActivity.class);

                }else if(schoolMenuNames.get(position).equals("店铺业绩报表")){
                    Skip.mNext(getActivity(), ShopPerformanceActivity.class);

                }else if(schoolMenuNames.get(position).equals("试穿报表")){
                    Skip.mNext(getActivity(), TryReportActivity.class);

                }else if(schoolMenuNames.get(position).equals("进店管理报表")){
                    Skip.mNext(getActivity(),IntoReportActivity.class);
//                     Skip.mNext(getActivity(),ReportCenterActivity.class);
                }
                else{
                    ToastFactory.getLongToast(getContext(),"没有该菜单选项！");
                }
            }
        });
    }

    public void initMemberMangerMenu(){
        memberMenuNames=new ArrayList<String>();
        memberMenuImages=new ArrayList<Integer>();

        memberMenuNames.add("会员分配");
        memberMenuImages.add(R.string.member_distribution);

        memberMenuNames.add("店员管理");
        memberMenuImages.add(R.string.users);

        memberMenuNames.add("短信群发");
        memberMenuImages.add(R.string.group_messaging);

        memberMenuNames.add("微信拉粉");
        memberMenuImages.add(R.string.weChat_fans);

        gridview_member_manger.setAdapter(new MemberMangerGridviewAdapter(getContext(),memberMenuNames,memberMenuImages));
        gridview_member_manger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(memberMenuNames.get(position).equals("会员分配")){
//                    Skip.mActivityResult(getActivity(), MemberDistributionActivity.class,requestCode);
                    Skip.mNext(getActivity(), MemberDistributionActivity.class);

                }else if(memberMenuNames.get(position).equals("店员管理")){
                    Skip.mNext(getActivity(),ClerkManageActivity.class);
                }
                else if(memberMenuNames.get(position).equals("短信群发")){
                    Skip.mNext(getActivity(),MessageListActivity.class);

                }else if(memberMenuNames.get(position).equals("微信拉粉")){
                    Bundle b=new Bundle();
                    b.putString("Ticket", prefs.getString("ticket"));
                    Skip.mNextFroData(getActivity(), WXLaFansActivity.class,b);

                }else{
                    ToastFactory.getLongToast(getContext(),"没有该菜单选项！");
                }
            }
        });
    }


    /**
     * 初始化高德地图定位
     */
    private void initAMapLocation(){

        mLocationListener= new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode()==0){
                        //定位成功回调信息，设置相关消息
                        longtitude=aMapLocation.getLongitude();//获取经度
                        latitude=aMapLocation.getLatitude();//获取纬度
                        //执行获取天气接口
                        ChinaWeatherNet chinaWeatherNet=new ChinaWeatherNet(getContext(),latitude,longtitude);
                        chinaWeatherNet.setData();
                    }
                }
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);
        //其他信息配置
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    /**
     * 初始化权限菜单
     */
    public void initPowerMenu(){
        menuNames=new ArrayList<String>();
        menuImages=new ArrayList<Integer>();

        menuNames.add("任务");
        menuImages.add(R.string.shop_task);
        menuNames.add("报数");
        menuImages.add(R.string.count_off);
        menuNames.add("订单");
        menuImages.add(R.string.shop_order);
////
//        menuNames.add("订单路由");
//        menuImages.add(R.string.shop_order);
        menuNames.add("业绩查询");
        menuImages.add(R.string.count_off);

        menuNames.add("分配业绩");
        menuImages.add(R.string.performance_distribution);
        menuNames.add("试穿记录");
        menuImages.add(R.string.try_record);
        menuNames.add("营销活动列表");
        menuImages.add(R.string.marketing_activity);

        menuNames.add("进店登记");
        menuImages.add(R.string.into_store_register);
        menuNames.add("邀单计划");
        menuImages.add(R.string.invitation_order);
//        menuNames.add("商学院");
//        menuImages.add(R.string.business_school);

        menuNames.add("商品搭配");
        menuImages.add(R.string.goods_collocation);

//        menuNames.add("会员画像");
//        menuImages.add(R.string.goods_collocation);

//        menuNames.add("盘点");
//        menuImages.add(R.string.count_off);

//        menuNames.add("签到记录");
//        menuImages.add(R.string.business_school);
//        menuNames.add("POS收银");
//        menuImages.add(R.string.count_off);
//        menuNames.add("公告");
//        menuImages.add(R.mipmap.announcement);
//
//        menuNames.add("商品搭配");
//        menuImages.add(R.mipmap.shop_collocation);
//
//        menuNames.add("推荐商品");
//        menuImages.add(R.mipmap.try_jilu);
//
//        menuNames.add("商品选择");
//        menuImages.add(R.mipmap.into_shop);


        //设置九宫格数据
        gridview.setAdapter(new MangerGridAdapter(getContext(),menuNames,menuImages));
        //设置点击事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(menuNames.get(position).equals("订单")){
                    bundle.putString("APP_Order", AppMenuPower.APP_ORDER);
                    Skip.mNextFroData(getActivity(), OrderActivity.class,bundle);
                }
                else if(menuNames.get(position).equals("订单路由")){
                    Skip.mNext(getActivity(), NewOrderActivity.class);
                }
                else if(menuNames.get(position).equals("业绩查询")){
                    Skip.mNext(getActivity(), ResultsQueryActivity.class);
                }
                else if(menuNames.get(position).equals("任务")){
                    bundle.putString("APP_Shop", AppMenuPower.APP_SHOP);
//                    Skip.mNextFroData(getActivity(), TaskCenterActivity.class,bundle);
                    Skip.mNext(getActivity(),ShopTaskListActivity.class);

                }else if(menuNames.get(position).equals("报数")){
//                    bundle.putString("APP_Number", AppMenuPower.APP_NUMBER);
//                    Skip.mNextFroData(getActivity(), AddStoresCountOffActivity.class,bundle);
                    Skip.mNext(getActivity(),CountOffActivity.class);

                }else if(menuNames.get(position).equals("POS收银")){
                    bundle.putString("APP_POSCashier", AppMenuPower.APP_POS_CASHIER);
                    Skip.mNextFroData(getActivity(), CashierMainActivity.class,bundle);

                }else if(menuNames.get(position).equals("签到记录")){
                    Skip.mNext(getActivity(),SigninActivity.class);

                }else if(menuNames.get(position).equals("分配业绩")){
                    Skip.mNext(getActivity(),PerformanceDistributionActivity.class);

                }else if(menuNames.get(position).equals("商学院")){
//                    bundle.putString("APP_College", AppMenuPower.APP_COLLEGE);
//                    Skip.mNextFroData(getActivity(), StaffTestListActivity.class,bundle);
                    Skip.mNext(getActivity(),BusinessActivity.class);

                }else if(menuNames.get(position).equals("商品搭配")){
                    Skip.mNext(getActivity(),ShopCollocationActivity.class);

                }else if(menuNames.get(position).equals("试穿记录")){
                    Skip.mNext(getActivity(),TryWearDataActivity.class);

                }else if(menuNames.get(position).equals("进店登记")){
                    Skip.mNext(getActivity(),IntoManagerActivity.class);
//                    Skip.mNext(getActivity(),SigninActivity.class);

                }else if(menuNames.get(position).equals("营销活动列表")){
                    Skip.mNext(getActivity(),ShopActivityListActivity.class);

                }else if(menuNames.get(position).equals("推荐商品")){
                    Skip.mNext(getActivity(),RecommendShopListActivity.class);

                }else if(menuNames.get(position).equals("邀单计划")){
                    Skip.mNext(getActivity(),InvitationOrderListActivity.class);

                }else if(menuNames.get(position).equals("商品选择")){
                    Skip.mNext(getActivity(),ShopSelectActivity.class);

                }else if(menuNames.get(position).equals("盘点")){
                    Skip.mNext(getActivity(),InventoryMainActivity.class);
                }else if(menuNames.get(position).equals("会员画像")){
                    Skip.mNext(getActivity(),MemberPortraitActivity.class);
                }
                else {//没有该菜单功能额
                    ToastFactory.getLongToast(getContext(), "没有该菜单功能额");
                }
            }
        });
    }

    /**
     * 九宫格GridAdapter
     * @author FGB
     *
     */
    public class MangerGridAdapter extends BaseAdapter {
        private Context mContext;

        private TextView tv;
        private TextView iv;
        private LinearLayout ll_grids;

        private List<String> menu;
        private List<Integer> imgs;

        public MangerGridAdapter(Context mContext,List<String> menuName,List<Integer> imgs) {
            super();
            this.mContext = mContext;
            this.menu=menuName;
            this.imgs=imgs;
        }

        @Override
        public int getCount() {
            return menu.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.grid_item_shop, parent, false);
            }
            tv = BaseViewHolder.get(convertView, R.id.tv_item);
            iv = BaseViewHolder.get(convertView, R.id.iv_item);
            ll_grids=BaseViewHolder.get(convertView, R.id.ll_grids);

            ll_grids.setVisibility(View.VISIBLE);
            iv.setText(imgs.get(position));
            iv.setTypeface(App.font);
            tv.setText(menu.get(position));

            return convertView;
        }
    }


    /**
     * Author FGB
     * Description 公告
     * Created at 2017/6/26 15:51
     * Version 1.0
     */

    public class NoticeNet extends BaseNet {

        public NoticeNet(Context context) {
            super(context, true);
            this.uri="ShopBusiness/SW/Notice/GetList";
        }

        public void setData(int PageIndex){
            try{
                data.put("PageSize",30);
                data.put("PageIndex",PageIndex);
                data.put("UserTicket",
                        AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultNoticeBean bean= JsonUtils.fromJson(rJson,ResultNoticeBean.class);
           final List<NoticeBean> notices = new ArrayList<>();
            if(bean.IsSuccess==true){
                if(bean.TModel.size()!=0){
                    TextView moreNotice=(TextView)findView(R.id.tv_more_notice);
                    NoticeView noticeView = (NoticeView) findView(R.id.notice_view);

                    for (int i=0;i<bean.TModel.size();i++){
                        if(bean.TModel.get(i).getStatus()==0){//未读
                            NoticeBean noticeBean=new NoticeBean();
                            noticeBean.setId(bean.TModel.get(i).getId());
                            noticeBean.setTitle(bean.TModel.get(i).getTitle());
                            notices.add(noticeBean);
                        }
                    }

                    if(notices.size()!=0){
                        ll_notice_view.setVisibility(View.VISIBLE);
                    }else{
                        ll_notice_view.setVisibility(View.GONE);
                    }

                    noticeView.addNotice(notices);
                    noticeView.startFlipping();

                    //点击查看更多公告
                    moreNotice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Skip.mNext(getActivity(), NoticeListActivity.class);
                        }
                    });

                    //点击获取相应公告详情
                    noticeView.setOnNoticeClickListener(new NoticeView.OnNoticeClickListener() {
                        @Override
                        public void onNotieClick(int position, String notice) {
                            bundle.putInt("noticeId",notices.get(position).getId());
                            Skip.mNextFroData(getActivity(), NoticeDetailActivity.class,bundle);
                        }
                    });
                }

            }else{
                ToastFactory.getLongToast(getContext(),"获取公告消息失败！"+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
            Log.i(Constant.TAG,"公告err:"+err);
        }
    }

    /**
     * Author FGB
     * Description 天气Net
     * Created 2017/4/20 18:58
     * Version 1.zero
     */
    public class WeatherNet extends BaseWeatherNet {
        public WeatherNet(Context context) {
            super(context, true);
            this.uri= "daily.json?key=ud9km8mr0dczmojp&location=ip&days=2";
        }

        public void setData(){
            try{
                mPostNet();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultsBean bean= JsonUtils.fromJson(rJson,ResultsBean.class);

            mWeatherResultsBeanList=new ArrayList<>();
            mWeatherResultsBeanList.addAll(bean.getResults().get(0).getDaily());
            mWeatherAdapter=new WeatherAdapter(mWeatherResultsBeanList,getContext());
            lv_weather.setAdapter(mWeatherAdapter);
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }

    /**
     * 用户详情信息
     * @author FGB
     *
     */
    public class UserInfoNet extends BaseNet {

        public UserInfoNet(Context context) {
            super(context,true);
            this.uri="User/Sw/User/Detail";
        }

        public void setData() {
            try {
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Log.i("test",rJson);
            Gson gson = new Gson();
            ResultUserBean lbean = gson.fromJson(rJson, ResultUserBean.class);
            if(lbean.IsSuccess==true && lbean.TModel!=null){
                userInfo=lbean.TModel;
                prefs.putString("TypeId", lbean.TModel.TypeId);
                prefs.putString("shopName", lbean.TModel.ShopName);
                prefs.putString("ShopId",lbean.TModel.ShopId);
                prefs.putString("VipId",lbean.TModel.VipId+"");
                prefs.putString("Mobile", lbean.TModel.Mobile);
                prefs.putString("Icon", lbean.TModel.Icon);
                prefs.putString("Name",lbean.TModel.Name);
                prefs.putString("NickName",lbean.TModel.NickName);
                prefs.putString("Sex", lbean.TModel.Sex);
                prefs.putString("shopMobile", lbean.TModel.ShopMobile);
                prefs.putString("TypeName",lbean.TModel.TypeName);
                prefs.putString("Ticket",lbean.TModel.Ticket);
                prefs.putString("CounselorId",lbean.TModel.CounselorId);
                prefs.putString("GzNo",lbean.TModel.GzNo);
                prefs.putString("TId",lbean.TModel.tId+"");
                prefs.putString("ShopPostCode",lbean.TModel.ShopPostCode);
                prefs.putString("ShopProvince",lbean.TModel.ShopProvince);
                prefs.putString("ShopAddress",lbean.TModel.ShopAddress);
                prefs.putString("ShopArea",lbean.TModel.ShopArea);
                prefs.putString("ShopCity",lbean.TModel.ShopCity);

                tvUserName.setText(lbean.TModel.NickName);
                tv_user_shop_name.setText(lbean.TModel.ShopName);
                tv_user_grade.setText(lbean.TModel.TypeName);

                tvUserName.setText(lbean.TModel.NickName);
                tv_member_card.setText(lbean.TModel.NickName);
                if(lbean.TModel.Icon!=null && !"".equals(lbean.TModel.Icon)){
                    Glide.with(getContext()).load(lbean.TModel.Icon).into(ivwUserIcon);
                }else{
                    ivwUserIcon.setImageResource(R.mipmap.not_user_icon);
                }
            }else{
                ToastFactory.getLongToast(getContext(), "获取用户信息失败！"+lbean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }

    }

    /**
     * Author FGB
     * Description 店铺任务
     * Created 2017/4/20 17:53
     * Version 1.zero
     */
    public class TaskListNet extends BaseNet {
        public TaskListNet(Context context) {
            super(context, true);
            this.uri="ShopBusiness/SW/ShopTask/TaskList";
        }

        public void setData(int PageIndex,int PageSize){
            try{
                data.put("PageIndex",PageIndex);
                data.put("PageSize",PageSize);
                data.put("Status",0);
                data.put("UserTicket",
                        AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultShopTaskListBean bean= JsonUtils.fromJson(rJson,ResultShopTaskListBean.class);
            if(bean.IsSuccess==true){
                mMyWorkBeanList=new ArrayList<>();
                mMyWorkBeanList.addAll(bean.TModel);

                badgeView.setBadgeCount(mMyWorkBeanList.size());

                mMyWorkAdapter=new MyWorkAdapter(mMyWorkBeanList,mContext);
                mSwipeMenuRecyclerView.setAdapter(mMyWorkAdapter);
                mMyWorkAdapter.setOnItemClickListener(onItemClickListener);
            }else{
                ToastFactory.getLongToast(getContext(),bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }
    //任务详情
    public OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            bundle.putInt("workid",mMyWorkBeanList.get(position).getWorkId());
            bundle.putInt("flowid",mMyWorkBeanList.get(position).getFlowId());
            bundle.putInt("formid",mMyWorkBeanList.get(position).getFormId());
            Skip.mNextFroData(getActivity(),HandleTaskActivity.class,bundle);
        }
    };


    /**
     * Author FGB
     * Description 店铺销售业绩Net
     * Created at 2017/5/8 13:40
     * Version 1.0
     */
    public class ShopSaleNet extends BaseNet{
        public ShopSaleNet(Context context) {
            super(context, true);
            this.uri="Order/Sw/Report/ShopSale";
        }

        public void setData(){
            try{
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultShopSaleBean bean= JsonUtils.fromJson(rJson,ResultShopSaleBean.class);
            if(bean.IsSuccess==true && bean.TModel!=null){
                shopSaleMoney=bean.TModel.getSalesAmount();
                tv_shop_sale.setText(shopSaleMoney+"");
            }else{
                tv_shop_sale.setText("0.0");
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }

    /**
     * Author FGB
     * Description 个人销售业绩Net
     * Created at 2017/5/8 13:41
     * Version 1.0
     */

    public class CounselorSaleNet extends BaseNet{
        public CounselorSaleNet(Context context) {
            super(context, true);
            this.uri="Order/Sw/Report/CounselorSale";
        }

        public void setData(){
            try{
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultCounselorSaleBean bean= JsonUtils.fromJson(rJson,ResultCounselorSaleBean.class);
            if(bean.IsSuccess==true && bean.TModel!=null){
                CounselorSaleMoney=bean.TModel.getSalesAmount();
                tv_counselorSale_sale.setText(CounselorSaleMoney+"");
            }else{
                tv_counselorSale_sale.setText("0.0");
            }
        }


        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }

    /**
     * Author FGB
     * Description 获取会员日目标Net
     * Created at 2017/5/8 13:41
     * Version 1.0
     */

    public class GetDayTotalNet extends BaseNet{
        public GetDayTotalNet(Context context) {
            super(context, true);
            this.uri="Marketing/Sw/SaleGoal/GetDayTotal";
        }

        public void setData(){
            try{
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultGetDayTotalBean bean= JsonUtils.fromJson(rJson,ResultGetDayTotalBean.class);
            if(bean.IsSuccess==true && bean.TModel!=null){
                vipSunCount= Integer.parseInt(bean.TModel.getCard());
                vipNewCount=Integer.parseInt(bean.TModel.getVipNum());
                shopTargetSaleMoney=Double.parseDouble(bean.TModel.getShopGoal());
                CounselorTargetSaleMoney=Double.parseDouble(bean.TModel.getCounselorGoal());

                tv_member_number.setText(vipSunCount+"");
                tv_new_member_number.setText(vipNewCount+"");
                tv_shop_sale_target.setText(shopTargetSaleMoney+"");
                tv_counselorSale_target.setText(CounselorTargetSaleMoney+"");

                List<ChartData> datas = new ArrayList<>();
                shopSalesScale=shopSaleMoney/shopTargetSaleMoney/100*100;
                ChartData cd1 = new ChartData(0x7f808080, 1.00f, 0xffffffff, false);
                ChartData cd2 = new ChartData(0xFFFF5500, (float)shopSalesScale, 0xff898988, true);
                datas.add(cd1);
                datas.add(cd2);

                List<ChartData> datas2 = new ArrayList<>();
                CounselorSalesScale=CounselorSaleMoney/CounselorTargetSaleMoney/100*100;
                ChartData cd11 = new ChartData(0x7f808080, 1.00f, 0xffffffff, false);
                ChartData cd22 = new ChartData(0xFFFF5500, (float)CounselorSalesScale, 0xff898988, true);
                datas2.add(cd11);
                datas2.add(cd22);

                List<ChartData> datas3 = new ArrayList<>();
                vipCountScale=vipSunCount/vipNewCount/100*100;
                ChartData cd13 = new ChartData(0x7f808080, 1.00f, 0xffffffff, false);
                ChartData cd23 = new ChartData(0xFFFF5500, vipCountScale, 0xff898988, true);
                datas3.add(cd13);
                datas3.add(cd23);

                jcv.setData(datas);
                jcv2.setData(datas2);
                jcv3.setData(datas3);

            }
        }
    }

    /**
     * Author FGB
     * Description 中国天气Net
     * Created 2017/4/20 18:58
     * Version 1.zero
     */
    public class ChinaWeatherNet extends BaseChinaWeatherNet {
        public ChinaWeatherNet(Context context,double lat,double lon) {
            super(context, true);
            this.uri= "lat="+lat+"&lon="+lon+"&appid=bea4ff6dad416cefcbd1f0480c179f1c&lang=zh_cn";
        }

        public void setData(){
            try{
                mPostNet();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultChinaWeatherBean bean=JsonUtils.fromJson(rJson,ResultChinaWeatherBean.class);
            //当前温度
            temp=bean.list.get(3).getMain().getTemp()-273.15;
            tv_deg.setText(DecimalFormatUtils.decimalToFormats(temp)+"℃");
            tv_description.setText(bean.list.get(3).getWeather().get(0).getDescription());
            switch (bean.list.get(3).getWeather().get(0).getId()){
                case 800://晴
                    iv_curr_icon.setImageResource(R.mipmap.sanshiba);
                    break;
                case 801://晴，多云
                    iv_curr_icon.setImageResource(R.mipmap.seven);
                    break;
                case 802://多云
                    iv_curr_icon.setImageResource(R.mipmap.four);
                    break;
                case 803://多云
                    iv_curr_icon.setImageResource(R.mipmap.four);
                    break;
                case 804://阴，多云
                    iv_curr_icon.setImageResource(R.mipmap.nine);
                    break;
                case 500://小雨
                    iv_curr_icon.setImageResource(R.mipmap.thirteen);
                    break;
                case 501://中雨
                    iv_curr_icon.setImageResource(R.mipmap.twelve);
                    break;
                case 502://大雨
                    iv_curr_icon.setImageResource(R.mipmap.shiwu);
                    break;
                case 503://大雨
                    iv_curr_icon.setImageResource(R.mipmap.sanshiqi);
                    break;
            }


            //明天温度
            temp_min=bean.list.get(10).getMain().getTemp_min()-273.15;
            temp_max=bean.list.get(10).getMain().getTemp_max()-273.15;
            tv_high_low.setText(DecimalFormatUtils.decimalToFormats(temp_min)+"/"+DecimalFormatUtils.decimalToFormats(temp_max)+"℃");
            tv_mingt.setText(bean.list.get(10).getWeather().get(0).getDescription());

            switch (bean.list.get(10).getWeather().get(0).getId()){
                case 800://晴
                    iv_mingt_icon.setImageResource(R.mipmap.sanshiba);
                    break;
                case 801://晴，多云
                    iv_mingt_icon.setImageResource(R.mipmap.seven);
                    break;
                case 802://多云
                    iv_mingt_icon.setImageResource(R.mipmap.four);
                    break;
                case 803://多云
                    iv_mingt_icon.setImageResource(R.mipmap.four);
                    break;
                case 804://阴，多云
                    iv_mingt_icon.setImageResource(R.mipmap.nine);
                    break;
                case 500://小雨
                    iv_mingt_icon.setImageResource(R.mipmap.thirteen);
                    break;
                case 501://中雨
                    iv_mingt_icon.setImageResource(R.mipmap.twelve);
                    break;
                case 502://大雨
                    iv_mingt_icon.setImageResource(R.mipmap.shiwu);
                    break;
                case 503://大雨
                    iv_mingt_icon.setImageResource(R.mipmap.sanshiqi);
                    break;
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }

}
