package com.cesaas.android.counselor.order.boss.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.ModifyPwdActivity;
import com.cesaas.android.counselor.order.activity.UserCentreActivity;
import com.cesaas.android.counselor.order.activity.main.adapter.UserShopAdapter;
import com.cesaas.android.counselor.order.activity.user.bean.ResultCompanyBean;
import com.cesaas.android.counselor.order.activity.user.net.GetCompanyNet;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.bean.ResultActionPowerBean;
import com.cesaas.android.counselor.order.bean.ResultBossUserBean;
import com.cesaas.android.counselor.order.bean.ResultChangeShop;
import com.cesaas.android.counselor.order.bean.ResultShopPowerBean;
import com.cesaas.android.counselor.order.bean.ShopPowerBean;
import com.cesaas.android.counselor.order.boss.ui.fragment.main.NewCEOMainSampleFragment;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.ActionPowerNet;
import com.cesaas.android.counselor.order.net.BossUserInfoNet;
import com.cesaas.android.counselor.order.net.ChangeShopNet;
import com.cesaas.android.counselor.order.power.adapter.MenuActionPowerAdapter;
import com.cesaas.android.counselor.order.power.adapter.ShopMenuActionPowerAdapter;
import com.cesaas.android.counselor.order.power.utils.MenuUtils;
import com.cesaas.android.counselor.order.power.utils.SkipMenuUtils;
import com.cesaas.android.counselor.order.runtimepermissions.PermissionUtils;
import com.cesaas.android.counselor.order.utils.AppUpdateUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewBossMainActivity extends BasesActivity{

    private TextView tvTitle,tvLeftTitle;
    private TextView tv_head_user_name,tv_user_info,tv_exit,tv_version,tv_sys_update,tv_grade,tv_brand_name,tv_authorization_code;
    private FloatingActionButton fab_add;
    private ImageView ivShare;
    private CircleImageView ivw_head_user_icon;
    private LinearLayout llBack,ll_base_title_left,ll_base_title,ll_sys_update;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View headerView;
    private RecyclerView mRecyclerView;
    private RecyclerView mList;
    private RecyclerView mMenuRecyclerView;
    private RecyclerView mShopMenuRecyclerView;

    private int resultNo;
    private long exitTime = 0; // 退出点击间隔时间
    private boolean isShowMenu=false;
    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;
    private boolean isshow=false;

    private ActionBarDrawerToggle toggle;
    private MenuActionPowerAdapter powerAdapter;
    private ShopMenuActionPowerAdapter shopMenuActionPowerAdapter;
    private List<String> actionList=new ArrayList<>();
    private ActionPowerNet  actionPowerNet;

    private String mobile;
    private String icon;
    private String nickName;
    //用户信息

    private UserShopAdapter adapter;
    private List<ShopPowerBean> list=new ArrayList<>();

    private BossUserInfoNet userInfoNet;
    private ChangeShopNet changeShopNet;
    private GetCompanyNet companyNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_new_main);

        initView();
        initDrawerLayout();
        initData();
        AppUpdateUtil.checkUpdate(mContext);
        checkAndRequestPermission();

        checkPwd();
    }

    private void initData(){
        if(mCache.getAsString("ActionPower")!=null &&!"".equals(mCache.getAsString("ActionPower"))){
            ResultActionPowerBean msg = JsonUtils.fromJson(mCache.getAsString("ActionPower"), ResultActionPowerBean.class);
            actionList=new ArrayList<>();
            if(msg.TModel!=null && msg.TModel.size()!=0){
                actionList=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    actionList.add(msg.TModel.get(i).getACTION_NO());
                }
            }
        }else{
            actionPowerNet=new ActionPowerNet(mContext,1,mCache);
            actionPowerNet.setData();
        }

        userInfoNet=new BossUserInfoNet(mContext);
        userInfoNet.setData();
        companyNet=new GetCompanyNet(mContext,mCache);
        companyNet.setData();

        getSupportFragmentManager().beginTransaction().add(R.id.new_boss_frame_layout, new NewCEOMainSampleFragment()).commit();
    }

    public void onEventMainThread(BaseTabBean msg) {
       mDrawerLayout.openDrawer(Gravity.START);
    }

    /**
     * 接收菜单权限数据
     * @param msg
     */
    public void onEventMainThread(ResultActionPowerBean msg) {
        if (msg.IsSuccess!=false) {
            if(msg.TModel!=null && msg.TModel.size()!=0){
                actionList=new ArrayList<>();
                for (int i=0;i<msg.TModel.size();i++){
                    actionList.add(msg.TModel.get(i).getACTION_NO());
                }
            }
        }
    }

    /**
     * 接收菜单权限数据
     * @param msg
     */
    public void onEventMainThread(ResultCompanyBean msg) {
        if (msg.IsSuccess!=false && msg.TModel!=null) {
            tv_brand_name.setText(msg.TModel.getCompanyName());
            tv_authorization_code.setText(msg.TModel.getCompanyAuthCode());
        }else{
            ToastFactory.getLongToast(mContext,"获取企业授权码失败："+msg.Message);
        }
    }

    /**
     * 接收切换店铺结果
     * @param msg
     */
    public void onEventMainThread(ResultChangeShop msg) {
        if (msg.IsSuccess!=false) {
            ToastFactory.getLongToast(mContext,"切换店铺成功！");
            userInfoNet=new BossUserInfoNet(mContext);
            userInfoNet.setData();
        }else{
            ToastFactory.getLongToast(mContext,"切换店铺失败："+msg.Message);
        }
    }

    /**
     * 接收当前用户店铺列表
     * @param msg
     */
    public void onEventMainThread(ResultShopPowerBean msg) {
        if (msg.IsSuccess!=false) {
            if(msg.TModel!=null && msg.TModel.size()!=0){
                list=new ArrayList<>();
                list.addAll(msg.TModel);
                adapter=new UserShopAdapter(list);
                mList.setAdapter(adapter);
                mList.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                        mDrawerLayout.closeDrawers();
                        changeShopNet=new ChangeShopNet(mContext);
                        changeShopNet.setData(list.get(position).getShopId());
                    }
                });
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取店铺列表失败："+msg.Message);
        }
    }

    /**
     * 接收用户信息数据
     * @param msg
     */
    public void onEventMainThread(ResultBossUserBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            if(msg.TModel.BrandName!=null){
                mobile=msg.TModel.Mobile;
                icon=msg.TModel.Icon;
                nickName=msg.TModel.NickName;
                prefs.putString("nickName",nickName);
                tv_brand_name.setText(msg.TModel.BrandName);
                tvTitle.setText(msg.TModel.BrandName);
                tv_head_user_name.setText(nickName);
                tv_grade.setText(msg.TModel.TypeName);
            }else{
                tvTitle.setText("报表");
            }
        }else{
            tvTitle.setText("报表");
        }

        if(msg.TModel.Icon!=null && !"".equals(msg.TModel.Icon)){
            Glide.with(mContext).load(msg.TModel.Icon).into(ivw_head_user_icon);
        }else{
            ivw_head_user_icon.setImageResource(R.mipmap.not_user_icon);
        }

        prefs.putString("TypeId", msg.TModel.TypeId);
        prefs.putString("shopName", msg.TModel.ShopName);
        prefs.putString("BrandName", msg.TModel.BrandName);
        prefs.putString("ShopId",msg.TModel.ShopId);
        prefs.putString("VipId",msg.TModel.VipId+"");
        prefs.putString("Mobile", msg.TModel.Mobile);
        prefs.putString("Icon", msg.TModel.Icon);
        prefs.putString("Name",msg.TModel.Name);
        prefs.putString("NickName",msg.TModel.NickName);
        prefs.putString("Sex", msg.TModel.Sex);
        prefs.putString("shopMobile", msg.TModel.ShopMobile);
        prefs.putString("TypeName",msg.TModel.TypeName);
        prefs.putString("Ticket",msg.TModel.Ticket);
        prefs.putString("CounselorId",msg.TModel.CounselorId);
        prefs.putString("GzNo",msg.TModel.GzNo);
        prefs.putString("TId",msg.TModel.tId+"");
        prefs.putString("ShopPostCode",msg.TModel.ShopPostCode);
        prefs.putString("ShopProvince",msg.TModel.ShopProvince);
        prefs.putString("ShopAddress",msg.TModel.ShopAddress);
        prefs.putString("ShopArea",msg.TModel.ShopArea);
        prefs.putString("ShopCity",msg.TModel.ShopCity);
    }


    private void setOnClick(){

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowMenu==false){
                    isShowMenu=true;
                    fab_add.setImageResource(R.mipmap.cancel);
                    showPopupWindow(fab_add);
                }else{
                    isShowMenu=false;
                    popWindow.dismiss();
                    fab_add.setImageResource(R.mipmap.menu);
                }
            }
        });
    }

    /**
     * 检查用户密码强度
     */
    private void checkPwd() {
        Bundle bundle=getIntent().getExtras();
        resultNo=bundle.getInt("resultNo");
        if(resultNo==3){//提示用户修改密码
            if (materialDialog != null) {
                materialDialog.setTitle("温馨提示！")
                        .setMessage("当前用户密码较弱，是否马上修改密码？")
                        .setPositiveButton("马上修改", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                                Skip.mNext(mActivity, ModifyPwdActivity.class);
                            }
                        }).setNegativeButton("返回", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                    }
                }).setCanceledOnTouchOutside(true).show();
            }
        }
    }

    private void initView() {
        fab_add= (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
        ll_base_title= (LinearLayout) findViewById(R.id.ll_base_title);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView= (NavigationView) findViewById(R.id.navigation_view);
        ll_base_title_left= (LinearLayout) findViewById(R.id.ll_base_title_left);
        ll_base_title_left.setVisibility(View.VISIBLE);
        tvLeftTitle= (TextView) findViewById(R.id.tv_title_left);
        tvLeftTitle.setVisibility(View.VISIBLE);
        tvLeftTitle.setTypeface(App.font);
        tvLeftTitle.setText(R.string.fa_align_justify);

        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        ivShare= (ImageView) findViewById(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.share);

        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setVisibility(View.GONE);

        setOnClick();
    }

    private void initDrawerLayout() {
        headerView = mNavigationView.getHeaderView(0);
        tv_version= (TextView) headerView. findViewById(R.id.tv_version);
        tv_version.setText(getVersion());
        tv_exit= (TextView) headerView. findViewById(R.id.tv_exit);
        tv_grade= (TextView) headerView. findViewById(R.id.tv_grade);
        tv_user_info= (TextView) headerView. findViewById(R.id.tv_user_info);
        tv_brand_name= (TextView) headerView. findViewById(R.id.tv_brand_name);
        tv_authorization_code= (TextView) headerView. findViewById(R.id.tv_authorization_code);
        tv_head_user_name= (TextView) headerView. findViewById(R.id.tv_head_user_name);
        ivw_head_user_icon= (CircleImageView) headerView.findViewById(R.id.ivw_head_user_icon);
        mList= (RecyclerView) headerView.findViewById(R.id.rv_list);
        mList.setLayoutManager(new LinearLayoutManager(mContext));
        ll_sys_update= (LinearLayout) headerView.findViewById(R.id.ll_sys_update);
        ll_sys_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                AppUpdateUtil.checkUpdate(mContext);
            }
        });
        tv_sys_update= (TextView) headerView.findViewById(R.id.tv_sys_update);
        tv_sys_update.setText(R.string.fa_sys_update);
        tv_sys_update.setTypeface(App.font);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Skip.mNext(mActivity,UserCentreActivity.class);
            }
        });
        tv_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Skip.mNext(mActivity, ModifyPwdActivity.class);
            }
        });
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                exit();
            }
        });
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        return version;
    }

    /**
     * 退出当前用户
     */
    public void exit() {
        new MyAlertDialog(mContext).mInitShow("退出登录", "是否退出登录，退出后将不能接收最新消息",
                "我知道", "点错了", new MyAlertDialog.ConfirmListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        myapp.mExecutorService.execute(new Runnable() {
                            @Override
                            public void run() {
                                prefs.cleanAll();
                                prefs.removeKey(Constant.SPF_TOKEN);
                                bundle.putString("Mobile",mobile);
                                bundle.putString("userIcon",icon);
                                bundle.putString("nickName",nickName);
                                mCache.remove("ActionPower");
                                mCache.remove("GetCompany");
                                mCache.remove(Constant.IS_SWITCH_SERVER);
                                mCache.clear();
                                NewBossMainActivity.this.finish();
                                setResult(0xe); // 用于退出时关闭本页
                                onExit();
                                Skip.mNextFroData(mActivity, LoginActivity.class, bundle,true);
                            }
                        });
                    }
                }, null);
    }

    /**
     * 退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    for (int i = 0; i < BasesActivity.activityList.size(); i++) {
                        if (null != BasesActivity.activityList.get(i)) {
                            Skip.mBack(BasesActivity.activityList.get(i));
                        }
                    }
                    Skip.mBack(this);
                }
                return true;
            } catch (Exception e) {
                Skip.mBack(this);
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 检查权限【Android6.0动态申请运行时权限】
     */
    private void checkAndRequestPermission(){
        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
    }

    /**
     *  处理授权请求回调
     使用onRequestPermissionsResult(int ,String , int[])方法处理回调，
     上面说到了根据requestPermissions()方法中的requestCode，
     就可以在回调方法中区分授权请求
     */
    @SuppressLint("Override")
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    /**
     * 一次申请多个权限Android6.0
     */
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO://录制音频
                    Log.d(Constant.TAG, "Result Permission Grant CODE_RECORD_AUDIO");
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Log.d(Constant.TAG, "Result Permission Grant CODE_GET_ACCOUNTS");
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Log.d(Constant.TAG, "Result Permission Grant CODE_READ_PHONE_STATE");
                    break;
                case PermissionUtils.CODE_CALL_PHONE://电话
                    Log.d(Constant.TAG, "Result Permission Grant CODE_CALL_PHONE");
                    break;
                case PermissionUtils.CODE_CAMERA://相机
                    Log.d(Constant.TAG, "Result Permission Grant CODE_CAMERA");
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Log.d(Constant.TAG, "Result Permission Grant CODE_ACCESS_FINE_LOCATION");
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION://获取模拟定位信息
                    Log.d(Constant.TAG, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION");
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE://读
                    Log.d(Constant.TAG, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE");
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE://写
                    Log.d(Constant.TAG, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE");
                    break;
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    Log.d(Constant.TAG, "Result All Permission Grant");
                    break;
                default:
                    break;
            }
        }
    };

    private View view;
    /**
     * 显示PopupWindow弹出菜单
     */
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= layoutInflater.inflate(R.layout.popwindow_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_blue_bg));
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(true);
        // PopupWindow的显示及位置设置
        if(Build.VERSION.SDK_INT >= 24){
            popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }else if(Build.VERSION.SDK_INT <= 20){
            popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        }else{
            popWindow.setOutsideTouchable(false);
            showAsDropDown(popWindow,parent,0,0);
        }

        if(Build.VERSION.SDK_INT >= 24){
            popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    isShowMenu=false;
                    popWindow.dismiss();
                    fab_add.setImageResource(R.mipmap.menu);
                }
            });
        }
        if(Build.VERSION.SDK_INT <= 20){
            popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    isShowMenu=false;
                    popWindow.dismiss();
                    fab_add.setImageResource(R.mipmap.menu);
                }
            });
        }
        mMenuRecyclerView= (RecyclerView) view.findViewById(R.id.rv_menu_list);
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mShopMenuRecyclerView= (RecyclerView) view.findViewById(R.id.rv_shop_menu_list);
        mShopMenuRecyclerView.setLayoutManager(new GridLayoutManager(this,4));

        powerAdapter=new MenuActionPowerAdapter(MenuUtils.menuList(actionList));
        mMenuRecyclerView.setAdapter(powerAdapter);
        shopMenuActionPowerAdapter=new ShopMenuActionPowerAdapter(MenuUtils.shopMenuList(actionList));
        mShopMenuRecyclerView.setAdapter(shopMenuActionPowerAdapter);

        mMenuRecyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                SkipMenuUtils.skipMenu(MenuUtils.menuList(actionList).get(position).getMenuNo(),prefs,mActivity);
                if(isShowMenu==false){
                    isShowMenu=true;
                    fab_add.setImageResource(R.mipmap.cancel);
                    showPopupWindow(fab_add);
                }else{
                    isShowMenu=false;
                    popWindow.dismiss();
                    fab_add.setImageResource(R.mipmap.menu);
                }
                popWindow.dismiss();
            }
        });
        mShopMenuRecyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                SkipMenuUtils.skipMenu(MenuUtils.shopMenuList(actionList).get(position).getMenuNo(),prefs,mActivity);
                if(isShowMenu==false){
                    isShowMenu=true;
                    fab_add.setImageResource(R.mipmap.cancel);
                    showPopupWindow(fab_add);
                }else{
                    isShowMenu=false;
                    popWindow.dismiss();
                    fab_add.setImageResource(R.mipmap.menu);
                }
                popWindow.dismiss();
            }
        });
    }

    /**
     *
     * @param pw     popupWindow
     * @param anchor v
     * @param xoff   x轴偏移
     * @param yoff   y轴偏移
     */
    public static void showAsDropDown(final PopupWindow pw, final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            pw.setHeight(height);
            pw.showAsDropDown(anchor, xoff, yoff);
        } else {
            pw.showAsDropDown(anchor, xoff, yoff);
        }
    }
}
