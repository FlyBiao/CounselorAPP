package com.cesaas.android.counselor.order.activity.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.cesaas.android.counselor.order.activity.main.fragment.newmain.NewMainSampleFragment;
import com.cesaas.android.counselor.order.activity.user.ITSupportActivity;
import com.cesaas.android.counselor.order.activity.user.bean.AppSettingEventBusBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultCompanyBean;
import com.cesaas.android.counselor.order.activity.user.net.GetCompanyNet;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.bean.ResultActionPowerBean;
import com.cesaas.android.counselor.order.bean.ResultChangeShop;
import com.cesaas.android.counselor.order.bean.ResultGetTokenBean;
import com.cesaas.android.counselor.order.bean.ResultShopPowerBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.ShopPowerBean;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.custom.tag.FlowTagLayout;
import com.cesaas.android.counselor.order.custom.toprightmenu.MenuItemMenu;
import com.cesaas.android.counselor.order.custom.toprightmenu.TopRightMenu;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.adapter.MemberQueryTagAdapter;
import com.cesaas.android.counselor.order.member.adapter.service.custom.MemberGradeAdapter;
import com.cesaas.android.counselor.order.member.bean.service.check.ResultQueryVipBean;
import com.cesaas.android.counselor.order.member.bean.service.custom.ResultVipGradeBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.ResultMemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.query.Grades;
import com.cesaas.android.counselor.order.member.net.service.FaceListNet;
import com.cesaas.android.counselor.order.member.net.service.GradeListNet;
import com.cesaas.android.counselor.order.member.net.service.MemberServiceListNet;
import com.cesaas.android.counselor.order.member.service.MemberSwitchShopActivity;
import com.cesaas.android.counselor.order.net.ActionPowerNet;
import com.cesaas.android.counselor.order.net.ChangeShopNet;
import com.cesaas.android.counselor.order.net.ShopPowerNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.power.adapter.MenuActionPowerAdapter;
import com.cesaas.android.counselor.order.power.adapter.ShopMenuActionPowerAdapter;
import com.cesaas.android.counselor.order.power.adapter.ShopTaskMenuActionPowerAdapter;
import com.cesaas.android.counselor.order.power.utils.MenuUtils;
import com.cesaas.android.counselor.order.power.utils.SkipMenuUtils;
import com.cesaas.android.counselor.order.rong.activity.ConversationListActivity;
import com.cesaas.android.counselor.order.rong.custom.CustomReportPushMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeMessageOrderItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeOrderMessage;
import com.cesaas.android.counselor.order.rong.custom.CustomizeSalesMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.CustomizeSalesTalkMessage;
import com.cesaas.android.counselor.order.rong.custom.FaceMessage;
import com.cesaas.android.counselor.order.rong.custom.FaceMessageItemProvider;
import com.cesaas.android.counselor.order.rong.custom.SalesTalkProvider;
import com.cesaas.android.counselor.order.rong.message.MyConversationBehaviorListener;
import com.cesaas.android.counselor.order.rong.message.MySendMessageListener;
import com.cesaas.android.counselor.order.runtimepermissions.PermissionUtils;
import com.cesaas.android.counselor.order.scan.AppNewScanActivity;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.AppUpdateUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.ui.activity.OrderInventoryActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.exception.HttpException;
import com.sing.datetimepicker.date.DatePickerDialog;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.CameraInputProvider;
import io.rong.imkit.widget.provider.ImageInputProvider;
import io.rong.imkit.widget.provider.InputProvider;
import io.rong.imkit.widget.provider.LocationInputProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class NewMainActivity extends BasesActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener ,RongIM.UserInfoProvider {

    private FlowTagLayout member_lable_flow_layout;
    private ImageView iv_add_tags;
    private LinearLayout ll_select_day,ll_select_vip;
    private TextView tv_sure_query,tv_cancel_query;
    private TextView tv_up,tv_bottom,tv_stored_value_arrow,tv_point_scope_arrow,tv_purchase_date_arrow,tv_birthday_scope_arrow,tv_registr_date_arrow;
    private EditText et_MoneyAreaMin,et_MoneyAreaMax,et_PointsAreaMin,et_PointsAreaMax,et_tag_count;
    private CheckBox cb_StoredValue,cb_buy_date,cb_birth_date,cb_points,cb_reg_date,cb_grade,cb_day,cb_member,cb_fans;
    private TextView tv_grade,tv_day,tv_buy_start_date,tv_buy_end_date,tv_birthday_start_date,tv_birthday_end_date,tv_reg_start_date,tv_reg_end_date,tv_PointsAreaMin,tv_PointsAreaMax;

    private int selectDate=0;//2开始购买日期，3结束购买日期，4开始生日日期，5结束生日日期,6开始注册日期，7结束注册日期
    private int day=0;
    private int gradeId;
    private int tagsCounts=0;
    private boolean isStoredValue=false;
    private boolean isBuyDate=false;
    private boolean isBirthDate=false;
    private boolean isPoints=false;
    private boolean isRegDate=false;
    private boolean isGrade=false;
    private boolean isDay=false;
    private boolean isMember=false;
    private boolean isFans=false;

    private TextView tvTitle,tvLeftTitle,tv_user_level;
    private ImageView tvRightTitle,iv_scan_s;
    private TextView tv_head_user_name,tv_sys_setting,tv_switch_shop,tv_update_pwd,tv_exit_login,tv_sys_update,tv_brand_name,tv_version,tv_phone,tv_gift,tv_authorization_code;
    private CircleImageView ivw_head_user_icon;
    private LinearLayout llBack,ll_base_title_left;
    private LinearLayout ll_update_pwd,ll_switch_shop,ll_sys_setting,ll_exit_login,ll_technology,ll_advice,ll_sys_update;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView,navigation_right_view;
    private FloatingActionButton fab_add;
    private long exitTime = 0; // 退出点击间隔时间
    private String mobile;
    private String icon;
    private String nickName;
    private View headerView;
    private RecyclerView mRecyclerView;
    private RecyclerView mMenuRecyclerView;
    private RecyclerView mShopMenuRecyclerView;
    private boolean isShowMenu=false;

    private TopRightMenu mTopRightMenu;
    private UserShopAdapter adapter;
    private List<ShopPowerBean> list=new ArrayList<>();

    private GradeListNet net;
    private ShopPowerNet powerNet;
    private UserInfoNet userInfoNet;
    private ChangeShopNet changeShopNet;
    private ActionPowerNet  actionPowerNet;
    private GetCompanyNet companyNet;
    private FaceListNet faceListNet;

    public MemberQueryTagAdapter<GetTagListBean> tagAdapter;
    private MemberGradeAdapter gradeAdapter;
    private MenuActionPowerAdapter powerAdapter;
    private ShopMenuActionPowerAdapter shopMenuActionPowerAdapter;
    private List<String> actionList=new ArrayList<>();
    private List<ResultVipGradeBean.VipGradeBean> mData=new ArrayList<>();
    private List<GetTagListBean> tags=new ArrayList<>();
    private List<Grades> grades=new ArrayList<>();

    private List<MemberServiceListBean> userIdList=new ArrayList<>();
    private String mUserId;

    private int MemberType=10;
    private int resultNo=0;

    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;
    private boolean isshow=false;

    private ActionBarDrawerToggle toggle;
    private BadgeView sessionBadgeView;
    private int msgCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        initView();
        initDrawerLayout();
        initData();
        GetTokenNet tokenNet=new GetTokenNet(mContext);
        tokenNet.setData();

        AppUpdateUtil.checkUpdate(mContext);
        checkAndRequestPermission();

        checkPwd();
    }

    public void onEventMainThread(ResultVipGradeBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                mData=new ArrayList<>();
                mData.addAll(msg.TModel);
            }else{
                ToastFactory.getLongToast(mContext,"暂无会员等级数据！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    public void onEventMainThread(ResultMemberServiceListBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                userIdList=new ArrayList<>();
                userIdList.addAll(msg.TModel);
            }
        }
    }

    private void initData(){
        RongIM.setUserInfoProvider(this, true);

        MemberServiceListNet net=new MemberServiceListNet(mContext,1);
        net.setData(1,0);

        userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();

        if(mCache.getAsString("ActionPower")!=null &&!"".equals(mCache.getAsString("ActionPower"))){
            ResultActionPowerBean msg = JsonUtils.fromJson(mCache.getAsString("ActionPower"), ResultActionPowerBean.class);
            actionList=new ArrayList<>();
            for (int i=0;i<msg.TModel.size();i++){
                actionList.add(msg.TModel.get(i).getACTION_NO());
            }
        }else{
            actionPowerNet=new ActionPowerNet(mContext,1,mCache);
            actionPowerNet.setData();
        }

        if(mCache.getAsString("ShopPower")!=null && !"".equals(mCache.getAsString("ShopPower"))){
            ResultShopPowerBean msg = JsonUtils.fromJson(mCache.getAsString("ShopPower"), ResultShopPowerBean.class);
            if(msg.TModel!=null && msg.TModel.size()!=0){
                list=new ArrayList<>();
                list.addAll(msg.TModel);
                adapter=new UserShopAdapter(list);
                mRecyclerView.setAdapter(adapter);
            }
        }else{
            powerNet=new ShopPowerNet(mActivity,mCache);
            powerNet.setData();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.new_frame_layout, new NewMainSampleFragment()).commit();
        adapter=new UserShopAdapter(list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                mDrawerLayout.closeDrawers();
                changeShopNet=new ChangeShopNet(mContext);
                changeShopNet.setData(list.get(position).getShopId());
            }
        });
    }

    /**
     * 接收底部菜单类型
     * @param msg
     */
    public void onEventMainThread(BaseTabBean msg) {
        switch (msg.getTabType()){
            case 1:
                tvTitle.setText("首页");
                iv_scan_s.setVisibility(View.GONE);
                tvRightTitle.setVisibility(View.VISIBLE);
                break;
            case 2:
                tvTitle.setText("工作台");
                iv_scan_s.setVisibility(View.GONE);
                tvRightTitle.setVisibility(View.VISIBLE);
                break;
            case 3:
                tvTitle.setText("商品");
                iv_scan_s.setVisibility(View.GONE);
                tvRightTitle.setVisibility(View.VISIBLE);
                break;
            case 4:
                tvTitle.setText("会员");
                iv_scan_s.setVisibility(View.VISIBLE);
                tvRightTitle.setVisibility(View.GONE);
                break;
            case 10:
                MemberType=10;
                break;
            case 20:
                MemberType=20;
                break;
            case 30:
                MemberType=30;
                break;
            case 40:
                MemberType=40;
                break;
            default:
                break;
        }
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
            ToastFactory.getLongToast(mContext,"获取企业信息失败："+msg.Message);
        }
    }

    /**
     * 接收切换店铺结果
     * @param msg
     */
    public void onEventMainThread(ResultChangeShop msg) {
        if (msg.IsSuccess!=false) {
            ToastFactory.getLongToast(mContext,"切换店铺成功！");
            userInfoNet=new UserInfoNet(mContext);
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
                mRecyclerView.setAdapter(adapter);
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取店铺列表失败："+msg.Message);
        }
    }

    /**
     * 检查用户密码强度============暂时去掉
     */
    private void checkPwd() {
//        Bundle bundle=getIntent().getExtras();
//        resultNo=bundle.getInt("resultNo");
//        if(resultNo==3){//提示用户修改密码
//            if (materialDialog != null) {
//                materialDialog.setTitle("温馨提示！")
//                        .setMessage("当前用户密码较弱，是否马上修改密码？")
//                        .setPositiveButton("马上修改", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                materialDialog.dismiss();
//                                Skip.mNext(mActivity, ModifyPwdActivity.class);
//                            }
//                        }).setNegativeButton("返回", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        materialDialog.dismiss();
//                    }
//                }).setCanceledOnTouchOutside(true).show();
//            }
//        }
    }


    /**
     * 接收用户信息数据
     * @param lbean
     */
    public void onEventMainThread(ResultUserBean lbean) {
        if(lbean.IsSuccess!=false && lbean.TModel!=null){
            mobile=lbean.TModel.Mobile;
            icon=lbean.TModel.Icon;
            nickName=lbean.TModel.NickName;
            tvTitle.setText("首页");
            tv_head_user_name.setText(nickName);
            prefs.putString("TypeId", lbean.TModel.TypeId);
            prefs.putString("shopName", lbean.TModel.ShopName);
            prefs.putString("ShopId",lbean.TModel.ShopId);
            prefs.putString("OrgId",lbean.TModel.OrganizationId);
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
            tv_user_level.setText(lbean.TModel.TypeName);
            if(lbean.TModel.Icon!=null && !"".equals(lbean.TModel.Icon)){
                Glide.with(mContext).load(lbean.TModel.Icon).into(ivw_head_user_icon);
            }else{
                ivw_head_user_icon.setImageResource(R.mipmap.not_user_icon);
            }
        }else{
            ToastFactory.getLongToast(mContext,"获取用户信息失败："+lbean.Message);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_left:
                if(isShowMenu==true){
                    popWindow.dismiss();
                    isShowMenu=false;
                    fab_add.setImageResource(R.mipmap.menu);
                }
                mDrawerLayout.openDrawer(Gravity.START);
                companyNet=new GetCompanyNet(mContext,mCache);
                companyNet.setData();
                break;
            case R.id.iv_scan_s:
                mDrawerLayout.openDrawer(Gravity.RIGHT);
                if(mCache.getAsString("GetGradeList")!=null && !"".equals(mCache.getAsString("GetGradeList"))){
                    ResultVipGradeBean msg= JsonUtils.fromJson(mCache.getAsString("GetGradeList"),ResultVipGradeBean.class);
                    if(msg.IsSuccess!=false){
                        if(msg.TModel!=null && msg.TModel.size()!=0){
                            mData=new ArrayList<>();
                            mData.addAll(msg.TModel);
                        }else{
                            ToastFactory.getLongToast(mContext,"暂无会员等级数据！");
                        }
                    }else{
                        ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
                    }
                }else{
                    net=new GradeListNet(mContext,mCache);
                    net.setData();
                }
                break;
            case R.id.iv_add_module:
                showMore();
                break;
            case R.id.fab_add:
                if(isShowMenu==false){
                    isShowMenu=true;
                    fab_add.setImageResource(R.mipmap.cancel);
                    showPopupWindow(fab_add);
                }else{
                    isShowMenu=false;
                    popWindow.dismiss();
                    fab_add.setImageResource(R.mipmap.menu);
                }
                break;
            case R.id.ll_update_pwd:
                mDrawerLayout.closeDrawers();
                Skip.mNext(mActivity, ModifyPwdActivity.class);
                break ;
            case R.id.ll_switch_shop:
                mDrawerLayout.closeDrawers();
                Skip.mNext(mActivity,MemberSwitchShopActivity.class);
                break ;
            case R.id.ll_sys_setting:
                mDrawerLayout.closeDrawers();
                AppSettingEventBusBean b=new AppSettingEventBusBean();
                b.setId(1);
                EventBus.getDefault().post(b);
//                Skip.mNext(mActivity, AppSettingActivity.class);
                break ;
            case R.id.ll_exit_login:
                mDrawerLayout.closeDrawers();
                exit();
                break ;
            case R.id.ll_sys_update:
                mDrawerLayout.closeDrawers();
                AppUpdateUtil.checkUpdate(mContext);
                break;
            case R.id.ll_technology://技术支持
                mDrawerLayout.closeDrawers();
                Skip.mNext(mActivity,ITSupportActivity.class);
                break;
            case R.id.ll_advice://建议
                mDrawerLayout.closeDrawers();
                break;
            case R.id.tv_sure_query:
                mDrawerLayout.closeDrawers();
                ResultQueryVipBean vipBean=new ResultQueryVipBean();
                if(isStoredValue==true){
                    if(!TextUtils.isEmpty(et_MoneyAreaMin.getText().toString()) && !TextUtils.isEmpty(et_MoneyAreaMax.getText().toString())){
                        vipBean.setMoneyAreaMin(Double.parseDouble(et_MoneyAreaMin.getText().toString()));
                        vipBean.setMoneyAreaMax(Double.parseDouble(et_MoneyAreaMax.getText().toString()));
                    }
                }

                if(isPoints==true){
                    if(!TextUtils.isEmpty(et_PointsAreaMin.getText().toString()) && !TextUtils.isEmpty(et_PointsAreaMax.getText().toString())){
                       vipBean.setPointsAreaMin(Integer.parseInt(et_PointsAreaMin.getText().toString()));
                        vipBean.setPointsAreaMax(Integer.parseInt(et_PointsAreaMax.getText().toString()));
                    }
                }

                if(isGrade==true){
                    grades=new ArrayList<>();
                    Grades g=new Grades();
                    g.setTitle(tv_grade.getText().toString());
                    g.setId(gradeId);
                    grades.add(g);
                    vipBean.setGrade(grades);
                }

                if(isBuyDate==true){
                    vipBean.setBuyDateAreaMin(tv_buy_start_date.getText().toString());
                    vipBean.setBuyDateAreaMax(tv_buy_end_date.getText().toString());
                }
                if(isBirthDate==true){
                    vipBean.setBirthdayAreaMin(tv_birthday_start_date.getText().toString());
                    vipBean.setBirthdayAreaMax(tv_birthday_end_date.getText().toString());
                }
                if (isRegDate==true){
                    vipBean.setCreateAreaMin(tv_reg_start_date.getText().toString());
                    vipBean.setCreateAreaMax(tv_reg_end_date.getText().toString());
                }
                if(!TextUtils.isEmpty(et_tag_count.getText().toString()) && !"".equals(et_tag_count.getText().toString())){
                    vipBean.setTagsCount(Integer.parseInt(et_tag_count.getText().toString()));
                }
                if (isDay=true){
                    vipBean.setNoBuyCount(day);
                }
                if(isFans==true){
                    vipBean.setIsVip(0);
                }
                if(isMember==true){
                    vipBean.setIsVip(1);
                }
                vipBean.setTag(tags);
                vipBean.setMemberType(MemberType);
                EventBus.getDefault().post(vipBean);
                break;
            case R.id.tv_cancel_query:
//                mDrawerLayout.closeDrawers();
                resetQuery();
                break;
            case R.id.tv_buy_start_date:
                if(isBuyDate==true){
                    selectDate=2;
                    getDateSelect(tv_buy_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_buy_end_date:
                if(isBuyDate==true){
                    selectDate=3;
                    getDateSelect(tv_buy_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_birthday_start_date:
                if(isBirthDate==true){
                    selectDate=4;
                    getDateSelect(tv_birthday_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_birthday_end_date:
                if(isBirthDate==true){
                    selectDate=5;
                    getDateSelect(tv_birthday_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_reg_start_date:
                if(isRegDate==true){
                    selectDate=6;
                    getDateSelect(tv_reg_start_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.tv_reg_end_date:
                if(isRegDate==true){
                    selectDate=7;
                    getDateSelect(tv_reg_end_date);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.ll_select_day:
                if(isDay==true){
                    setDayDialog();
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.ll_select_vip:
                if(isGrade==true){
                    setVipDialog(mData);
                }else{
                    ToastFactory.getLongToast(mContext,"请先勾选该选项！");
                }
                break;
            case R.id.iv_add_tags:
                Intent tagIntent = new Intent(mContext, ShopTagListActivity.class);
                startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
                break;
            case R.id.tv_up:
                tagsCounts=tagsCounts+1;
                et_tag_count.setText(String.valueOf(tagsCounts));
                break;
            case R.id.tv_bottom:
                if(tagsCounts!=0){
                    tagsCounts=tagsCounts-1;
                }
                et_tag_count.setText(String.valueOf(tagsCounts));
                break;
        }
    }

    private void initView() {
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView= (NavigationView) findViewById(R.id.navigation_view);
        navigation_right_view= (NavigationView) findViewById(R.id.navigation_right_view);
        ll_base_title_left= (LinearLayout) findViewById(R.id.ll_base_title_left);
        ll_base_title_left.setVisibility(View.VISIBLE);
        actionBarDrawerToggle();

        fab_add= (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

        tvLeftTitle= (TextView) findViewById(R.id.tv_title_left);
        tvLeftTitle.setVisibility(View.VISIBLE);
        tvLeftTitle.setTypeface(App.font);
        tvLeftTitle.setText(R.string.fa_align_justify);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setVisibility(View.GONE);
        tvRightTitle= (ImageView) findViewById(R.id.iv_add_module);
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setImageResource(R.mipmap.add_module);
        iv_scan_s= (ImageView) findViewById(R.id.iv_scan_s);
        iv_scan_s.setImageResource(R.mipmap.query_member);

        iv_scan_s.setOnClickListener(this);
        tvRightTitle.setOnClickListener(this);
        tvLeftTitle.setOnClickListener(this);
        fab_add.setOnClickListener(this);
    }

    private void actionBarDrawerToggle(){
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout,0,0){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //关闭
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //打开
                companyNet=new GetCompanyNet(mContext,mCache);
                companyNet.setData();
            }
        };
        mDrawerLayout.setDrawerListener(toggle);
    }

    private void initDrawerLayout() {
        headerView=navigation_right_view.getHeaderView(0);
        et_tag_count= (EditText) headerView.findViewById(R.id.et_tag_count);
        tv_up= (TextView) headerView.findViewById(R.id.tv_up);
        tv_up.setOnClickListener(this);
        tv_up.setText(R.string.fa_sort_up);
        tv_up.setTypeface(App.font);
        tv_bottom= (TextView) headerView.findViewById(R.id.tv_bottom);
        tv_bottom.setOnClickListener(this);
        tv_bottom.setText(R.string.fa_sort_desc);
        tv_bottom.setTypeface(App.font);
        tv_stored_value_arrow= (TextView) headerView.findViewById(R.id.tv_stored_value_arrow);
        tv_stored_value_arrow.setText(R.string.fa_long_arrow_right);
        tv_stored_value_arrow.setTypeface(App.font);
        tv_point_scope_arrow= (TextView) headerView.findViewById(R.id.tv_point_scope_arrow);
        tv_point_scope_arrow.setText(R.string.fa_long_arrow_right);
        tv_point_scope_arrow.setTypeface(App.font);
        tv_purchase_date_arrow= (TextView) headerView.findViewById(R.id.tv_purchase_date_arrow);
        tv_purchase_date_arrow.setText(R.string.fa_long_arrow_right);
        tv_purchase_date_arrow.setTypeface(App.font);
        tv_birthday_scope_arrow= (TextView) headerView.findViewById(R.id.tv_birthday_scope_arrow);
        tv_birthday_scope_arrow.setText(R.string.fa_long_arrow_right);
        tv_birthday_scope_arrow.setTypeface(App.font);
        tv_registr_date_arrow= (TextView) headerView.findViewById(R.id.tv_registr_date_arrow);
        tv_registr_date_arrow.setText(R.string.fa_long_arrow_right);
        tv_registr_date_arrow.setTypeface(App.font);
        member_lable_flow_layout= (FlowTagLayout) headerView.findViewById(R.id.member_lable_flow_layout);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        iv_add_tags= (ImageView) headerView.findViewById(R.id.iv_add_tags);
        iv_add_tags.setOnClickListener(this);
        tv_grade= (TextView) headerView.findViewById(R.id.tv_grade);
        ll_select_vip= (LinearLayout) headerView.findViewById(R.id.ll_select_vip);
        ll_select_vip.setOnClickListener(this);
        ll_select_day= (LinearLayout) headerView.findViewById(R.id.ll_select_day);
        ll_select_day.setOnClickListener(this);
        tv_PointsAreaMin= (TextView) headerView.findViewById(R.id.tv_PointsAreaMin);
        tv_PointsAreaMax= (TextView) headerView.findViewById(R.id.tv_PointsAreaMax);
        tv_day= (TextView) headerView.findViewById(R.id.tv_day);
        tv_reg_start_date = (TextView)headerView. findViewById(R.id.tv_reg_start_date);
        tv_reg_start_date.setOnClickListener(this);
        tv_reg_end_date = (TextView)headerView. findViewById(R.id.tv_reg_end_date);
        tv_reg_end_date.setOnClickListener(this);
        tv_birthday_start_date= (TextView) headerView.findViewById(R.id.tv_birthday_start_date);
        tv_birthday_start_date.setOnClickListener(this);
        tv_birthday_end_date= (TextView) headerView.findViewById(R.id.tv_birthday_end_date);
        tv_birthday_end_date.setOnClickListener(this);
        tv_buy_end_date= (TextView) headerView.findViewById(R.id.tv_buy_end_date);
        tv_buy_end_date.setOnClickListener(this);
        tv_buy_start_date= (TextView) headerView.findViewById(R.id.tv_buy_start_date);
        tv_buy_start_date.setOnClickListener(this);
        tv_sure_query= (TextView) headerView.findViewById(R.id.tv_sure_query);
        tv_sure_query.setOnClickListener(this);
        tv_cancel_query= (TextView) headerView.findViewById(R.id.tv_cancel_query);
        tv_cancel_query.setOnClickListener(this);
        et_MoneyAreaMin= (EditText) headerView.findViewById(R.id.et_MoneyAreaMin);
        et_MoneyAreaMax= (EditText) headerView.findViewById(R.id.et_MoneyAreaMax);
        et_PointsAreaMin= (EditText) headerView.findViewById(R.id.et_PointsAreaMin);
        et_PointsAreaMax= (EditText) headerView.findViewById(R.id.et_PointsAreaMax);
        cb_StoredValue= (CheckBox) headerView.findViewById(R.id.cb_StoredValue);
        cb_StoredValue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isStoredValue=true;
                    et_MoneyAreaMin.setEnabled(true);
                    et_MoneyAreaMax.setEnabled(true);
                    et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }else{
                    isStoredValue=false;
                    et_MoneyAreaMin.setEnabled(false);
                    et_MoneyAreaMax.setEnabled(false);
                    et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }
            }
        });
        cb_buy_date= (CheckBox) headerView.findViewById(R.id.cb_buy_date);
        cb_buy_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isBuyDate=true;
                }else{
                    isBuyDate=false;
                }
            }
        });
        cb_birth_date= (CheckBox) headerView.findViewById(R.id.cb_birth_date);
        cb_birth_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isBirthDate=true;
                }else{
                    isBirthDate=false;
                }
            }
        });
        cb_points= (CheckBox) headerView.findViewById(R.id.cb_points);
        cb_points.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isPoints=true;
                    tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMin.setEnabled(true);
                    et_PointsAreaMax.setEnabled(true);
                    et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }else{
                    isPoints=false;
                    tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.white));
                    tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMin.setEnabled(false);
                    et_PointsAreaMax.setEnabled(false);
                    et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.white));
                    et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.white));
                }
            }
        });
        cb_reg_date= (CheckBox) headerView.findViewById(R.id.cb_reg_date);
        cb_reg_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isRegDate=true;
                }else{
                    isRegDate=false;
                }
            }
        });
        cb_grade= (CheckBox) headerView.findViewById(R.id.cb_grade);
        cb_grade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isGrade=true;
                }else{
                    isGrade=false;
                }
            }
        });
        cb_day= (CheckBox) headerView.findViewById(R.id.cb_day);
        cb_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    isDay=true;
                }else{
                    isDay=false;
                }
            }
        });
        cb_member= (CheckBox) headerView.findViewById(R.id.cb_member);
        cb_member.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    isMember=true;
                }else{
                    isMember=false;
                }
            }
        });
        cb_fans= (CheckBox) headerView.findViewById(R.id.cb_fans);
        cb_fans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    isFans=true;
                    cb_member.setChecked(false);
                }else{
                    isFans=false;
                    cb_member.setChecked(true);
                }
            }
        });

        headerView = mNavigationView.getHeaderView(0);
        tv_user_level= (TextView) headerView.findViewById(R.id.tv_user_level);
        tv_sys_update= (TextView) headerView.findViewById(R.id.tv_sys_update);
        tv_sys_update.setText(R.string.fa_sys_update);
        tv_sys_update.setTypeface(App.font);
        tv_sys_setting= (TextView) headerView. findViewById(R.id.tv_sys_setting);
        tv_sys_setting.setText(R.string.fa_cogs);
        tv_sys_setting.setTypeface(App.font);
        tv_switch_shop= (TextView) headerView. findViewById(R.id.tv_switch_shop);
        tv_switch_shop.setText(R.string.fa_mail_forward);
        tv_switch_shop.setTypeface(App.font);
        tv_update_pwd= (TextView) headerView. findViewById(R.id.tv_update_pwd);
        tv_update_pwd.setText(R.string.fa_key);
        tv_update_pwd.setTypeface(App.font);
        tv_exit_login= (TextView) headerView. findViewById(R.id.tv_exit_login);
        tv_exit_login.setText(R.string.fa_arrow_right);
        tv_exit_login.setTypeface(App.font);
        tv_version= (TextView) headerView. findViewById(R.id.tv_version);
        tv_version.setText(getVersion());
        tv_phone= (TextView) headerView. findViewById(R.id.tv_phone);
        tv_phone.setText(R.string.fa_volume_control_phone);
        tv_phone.setTypeface(App.font);
        tv_gift= (TextView) headerView. findViewById(R.id.tv_gift);
        tv_gift.setText(R.string.fa_gift);
        tv_gift.setTypeface(App.font);
        tv_brand_name= (TextView) headerView. findViewById(R.id.tv_brand_name);
        tv_authorization_code= (TextView) headerView. findViewById(R.id.tv_authorization_code);
        tv_head_user_name= (TextView) headerView. findViewById(R.id.tv_head_user_name);
        ivw_head_user_icon= (CircleImageView) headerView.findViewById(R.id.ivw_head_user_icon);
        ll_sys_update= (LinearLayout) headerView.findViewById(R.id.ll_sys_update);
        ll_sys_update.setOnClickListener(this);
        ll_advice= (LinearLayout) headerView.findViewById(R.id.ll_advice);
        ll_advice.setOnClickListener(this);
        ll_technology= (LinearLayout) headerView.findViewById(R.id.ll_technology);
        ll_technology.setOnClickListener(this);
        ll_update_pwd= (LinearLayout) headerView.findViewById(R.id.ll_update_pwd);
        ll_update_pwd.setOnClickListener(this);
        ll_switch_shop= (LinearLayout) headerView.findViewById(R.id.ll_switch_shop);
        ll_switch_shop.setOnClickListener(this);
        ll_sys_setting= (LinearLayout) headerView.findViewById(R.id.ll_sys_setting);
        ll_sys_setting.setOnClickListener(this);
        ll_exit_login= (LinearLayout) headerView.findViewById(R.id.ll_exit_login);
        ll_exit_login.setOnClickListener(this);
        mRecyclerView= (RecyclerView) headerView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Skip.mNext(mActivity,UserCentreActivity.class);
            }
        });
    }

    /**
     * 重置会员筛选条件
     */
    public void resetQuery(){
        cb_StoredValue.setChecked(false);
        et_MoneyAreaMin.setText("");
        et_MoneyAreaMin.setHint("最小储值");
        et_MoneyAreaMax.setText("");
        et_MoneyAreaMax.setHint("最大储值");
        isStoredValue=false;
        et_MoneyAreaMin.setEnabled(false);
        et_MoneyAreaMax.setEnabled(false);
        et_MoneyAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        et_MoneyAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));

        cb_buy_date.setChecked(false);
        isBuyDate=false;
        tv_buy_start_date.setText("");
        tv_buy_start_date.setText("开始日期");
        tv_buy_end_date.setText("");
        tv_buy_end_date.setText("结束日期");

        cb_birth_date.setChecked(false);
        isBirthDate=false;
        tv_birthday_start_date.setText("");
        tv_birthday_start_date.setText("开始范围");
        tv_birthday_end_date.setText("");
        tv_birthday_end_date.setText("结束范围");

        cb_day.setChecked(false);
        isDay=false;
        day=30;
        tv_day.setText("最近一个月");

        cb_grade.setChecked(false);
        isGrade=false;
        tv_grade.setText("");
        tv_grade.setText("选择会员等级");

        cb_points.setChecked(false);
        isPoints=false;
        tv_PointsAreaMin.setText("");
        tv_PointsAreaMin.setText("填写积分范围");
        tv_PointsAreaMax.setText("");
        tv_PointsAreaMax.setText("填写积分范围");
        tv_PointsAreaMin.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        tv_PointsAreaMax.setTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        et_PointsAreaMin.setEnabled(false);
        et_PointsAreaMax.setEnabled(false);
        et_PointsAreaMin.setText("最低积分");
        et_PointsAreaMax.setText("最高积分");
        et_PointsAreaMin.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));
        et_PointsAreaMax.setHintTextColor(mContext.getResources().getColor(R.color.cinema_list_switch_normal));


        cb_reg_date.setChecked(false);
        isRegDate=false;
        tv_reg_start_date.setText("");
        tv_reg_start_date.setText("开始日期");
        tv_reg_end_date.setText("");
        tv_reg_end_date.setText("结束日期");

        tags=new ArrayList<>();
        tagAdapter = new MemberQueryTagAdapter<>(mContext);
        member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        member_lable_flow_layout.setAdapter(tagAdapter);
        tagAdapter.onlyAddAll(tags);
    }

    private void showMore(){
        mTopRightMenu = new TopRightMenu(mActivity,msgCount);
        List<MenuItemMenu> menuItems = new ArrayList<>();
//        menuItems.add(new MenuItemMenu(R.mipmap.menu,"扫描库存"));
        menuItems.add(new MenuItemMenu( R.mipmap.scan_main,"扫描二维码"));
        menuItems.add(new MenuItemMenu( R.mipmap.inventory,"查询库存"));
        menuItems.add(new MenuItemMenu( R.mipmap.msg_main,"消息"));
//        menuItems.add(new MenuItemMenu( R.mipmap.msg_main,"盘点"));
        mTopRightMenu
                .setHeight(500)     //默认高度480
                .setWidth(420)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position){
                            case 0://扫描二维码
                                Skip.mNext(mActivity,AppNewScanActivity.class);
                                break;
                            case 1://查询库存
                                Skip.mNext(mActivity, OrderInventoryActivity.class);
                                break;
                            case 2://消息
                                Skip.mNext(mActivity,ConversationListActivity.class);
                                break;
                            default:
                                break;
                        }
                    }
                })
                .showAsDropDown(tvRightTitle, -245, 0);
//                        .showAsDropDown(moreBtn);
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
                                mCache.remove(Constant.IS_SWITCH_SERVER);
                                mCache.clear();
                                NewMainActivity.this.finish();
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
                    //断开融云服务
                    RongIM.getInstance().disconnect();
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

    private View view;
    private LinearLayout ll_shop_task,ll_pos_task,ll_shop_task_menu,ll_pos_task_menu;
    private TextView tv_shop_task,tv_pos_task;
    private TextView tv_head_phones,tv_first_order;
    private RecyclerView rv_shop_task_menu_list,rv_pos_task_menu_list;
    private ShopTaskMenuActionPowerAdapter shopTaskMenuActionPowerAdapter;
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
        ll_pos_task_menu= (LinearLayout) view.findViewById(R.id.ll_pos_task_menu);
        ll_shop_task_menu= (LinearLayout) view.findViewById(R.id.ll_shop_task_menu);
        ll_shop_task= (LinearLayout) view.findViewById(R.id.ll_shop_task);
        ll_pos_task= (LinearLayout) view.findViewById(R.id.ll_pos_task);
        tv_shop_task= (TextView) view.findViewById(R.id.tv_shop_task);
        tv_pos_task= (TextView) view.findViewById(R.id.tv_pos_task);
        tv_head_phones= (TextView) view.findViewById(R.id.tv_head_phones);
        tv_head_phones.setText(R.string.fa_head_phones);
        tv_head_phones.setTypeface(App.font);
        tv_first_order= (TextView) view.findViewById(R.id.tv_first_order);
        tv_first_order.setText(R.string.fa_first_order);
        tv_first_order.setTypeface(App.font);

        mMenuRecyclerView= (RecyclerView) view.findViewById(R.id.rv_menu_list);
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mShopMenuRecyclerView= (RecyclerView) view.findViewById(R.id.rv_shop_menu_list);
        mShopMenuRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        rv_shop_task_menu_list= (RecyclerView) view.findViewById(R.id.rv_shop_task_menu_list);
        rv_shop_task_menu_list.setLayoutManager(new GridLayoutManager(this,4));
        rv_pos_task_menu_list= (RecyclerView) view.findViewById(R.id.rv_pos_task_menu_list);
        rv_pos_task_menu_list.setLayoutManager(new GridLayoutManager(this,4));

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
                }                popWindow.dismiss();
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
        ll_shop_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_shop_task_menu.setVisibility(View.VISIBLE);
                ll_pos_task_menu.setVisibility(View.GONE);
                ll_shop_task.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                ll_pos_task.setBackgroundColor(mContext.getResources().getColor(R.color.new_base_bg));
                tv_shop_task.setTextColor(mContext.getResources().getColor(R.color.purple_pressed));
                tv_head_phones.setTextColor(mContext.getResources().getColor(R.color.purple_pressed));
                tv_pos_task.setTextColor(mContext.getResources().getColor(R.color.purple_normal));
                tv_first_order.setTextColor(mContext.getResources().getColor(R.color.purple_normal));

            }
        });
        ll_pos_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopTaskMenuActionPowerAdapter=new ShopTaskMenuActionPowerAdapter(MenuUtils.posTaskMenuList(actionList));
                rv_shop_task_menu_list.setAdapter(shopTaskMenuActionPowerAdapter);

                ll_shop_task_menu.setVisibility(View.GONE);
                ll_pos_task_menu.setVisibility(View.VISIBLE);
                ll_shop_task.setBackgroundColor(mContext.getResources().getColor(R.color.new_base_bg));
                ll_pos_task.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                tv_shop_task.setTextColor(mContext.getResources().getColor(R.color.purple_normal));
                tv_head_phones.setTextColor(mContext.getResources().getColor(R.color.purple_normal));
                tv_pos_task.setTextColor(mContext.getResources().getColor(R.color.purple_pressed));
                tv_first_order.setTextColor(mContext.getResources().getColor(R.color.purple_pressed));
            }
        });

        rv_shop_task_menu_list.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                SkipMenuUtils.skipMenu(MenuUtils.posTaskMenuList(actionList).get(position).getMenuNo(),prefs,mActivity);
                if(isShowMenu==false){
                    isShowMenu=true;
                    fab_add.setImageResource(R.mipmap.cancel);
                    showPopupWindow(fab_add);
                }else{
                    isShowMenu=false;
                    popWindow.dismiss();
                    fab_add.setImageResource(R.mipmap.menu);
                }                popWindow.dismiss();
            }
        });
    }

    /**io
     *
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
     * 链接融云
     */
    public void connectRongCloud(String token){
        if (mActivity.getApplicationInfo().packageName.equals(App.getCurProcessName(mContext))) {
            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
//                    Log.i("test", "Token已经过期！");
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "融云Token已经过期！", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @SuppressWarnings("static-access")
                @Override
                public void onSuccess(String userid) {
                    Log.i("test", "连接融云成功！"+userid);
                    mUserId=userid;
                    //扩展功能自定义
                    InputProvider.ExtendProvider[] provider = {
//                            new ContactsProvider(RongContext.getInstance()),//自定义推荐商品
//                            new ContactsOrderProvider(RongContext.getInstance()),//自定义发送订单
		            	    new ImageInputProvider(RongContext.getInstance()),//图片
                            new CameraInputProvider(RongContext.getInstance()),//相机
//		            	    new LocationInputProvider(RongContext.getInstance()),//地理位置
                            new SalesTalkProvider(RongContext.getInstance(),mActivity),//自定义会话话术

                    };
                    RongIM.getInstance().resetInputExtensionProvider(Conversation.ConversationType.PRIVATE, provider);

                    //注册自定义消息
                    RongIM.registerMessageType(CustomizeMessage.class);
                    RongIM.registerMessageType(CustomizeOrderMessage.class);
                    RongIM.registerMessageType(CustomizeSalesTalkMessage.class);
                    RongIM.registerMessageType(FaceMessage.class);

                    //注册自定义人脸识别消息模板
                    RongIM.getInstance().registerMessageTemplate(new FaceMessageItemProvider(mContext,mActivity));
                    //注册自定义商品消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider(mContext,mActivity));
                    //注册自定义订单消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomizeMessageOrderItemProvider(mContext,mActivity));
                    //注册自定义话术消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomizeSalesMessageItemProvider(mContext,mActivity));
                    //注册自定义报表消息模板
                    RongIM.getInstance().registerMessageTemplate(new CustomReportPushMessageItemProvider(mActivity));

                    //设置接收消息的监听器。
                    RongIM.setOnReceiveMessageListener(new NewMainActivity.MyReceiveMessageListener());
                    //设置会话界面操作的监听器。
                    RongIM.setConversationBehaviorListener(new MyConversationBehaviorListener());
                    //监听已发送的消息
//                    RongIM.getInstance().setSendMessageListener(new OnSendMessageListener(mContext,mActivity,prefs));
                    RongIM.getInstance().setSendMessageListener(new MySendMessageListener(mContext,mActivity,prefs));
                    //接收所有未读消息消息的监听器。
                    RongIM.getInstance().setOnReceiveUnreadCountChangedListener(new NewMainActivity.MyReceiveUnreadCountChangedListener());
                    // 接收指定会话类型的未读消息数。
                    Conversation.ConversationType conversationType[]={
                            Conversation.ConversationType.PRIVATE,
                            Conversation.ConversationType.GROUP,
                            Conversation.ConversationType.DISCUSSION,
                            Conversation.ConversationType.SYSTEM,
                            Conversation.ConversationType.PUSH_SERVICE,
                            Conversation.ConversationType.NONE,
                            Conversation.ConversationType.CUSTOMER_SERVICE,
                            Conversation.ConversationType.APP_PUBLIC_SERVICE,
                            Conversation.ConversationType.CHATROOM
                    };

                    RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
                    RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目

                    RongIM.getInstance().setOnReceiveUnreadCountChangedListener(
                            new NewMainActivity.MyReceiveUnreadCountChangedListener(), conversationType);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(final RongIMClient.ErrorCode errorCode) {
//                    Log.i("test","融云连接失败----"+errorCode);
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "融云连接失败:"+errorCode, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public UserInfo getUserInfo(String s) {
        if(userIdList.size()!=0){
            for (MemberServiceListBean i : userIdList) {
                String vipId=i.getVipId()+"";
                if (vipId.equals(s)) {
                    return new UserInfo(i.getVipId()+"",i.getName(), Uri.parse(i.getImage()));
                }
            }
        }
        return null;
    }

    /**
     * 接收未读消息的监听器。
     * @author FGB
     */
    public class MyReceiveUnreadCountChangedListener implements RongIM.OnReceiveUnreadCountChangedListener {

        /**
         *  @param count 未读消息数。
         */
        @Override
        public void onMessageIncreased(int count) {
            msgCount=count;
//            mActivity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    faceListNet=new FaceListNet(mContext);
//                    faceListNet.setData(1, AbDateUtil.getCurrentDate("yyyy-MM-dd 00:00:00"),AbDateUtil.getCurrentDate("yyyy-MM-dd 23:59:59"));
//                }
//            });
        }
    }

    /**
     * 接受消息监听
     * @author fgb
     *
     */
    public class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param i    剩余未拉取消息数目。
         * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
         */
        @Override
        public boolean onReceived(Message message, int i) {
            if(message!=null){
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
//						ReceiveOrderFragment.handler.obtainMessage().sendToTarget();
            }
            return false;
        }
    }

    /**
     * 获取融云Token
     * @author FGB
     *
     */
    public class GetTokenNet extends BaseNet {

        public GetTokenNet(Context context) {
            super(context, true);
            this.uri="User/Sw/User/GetToken";
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
            Gson gson = new Gson();
            ResultGetTokenBean msg = gson.fromJson(rJson, ResultGetTokenBean.class);
            if(msg.IsSuccess==true){
                connectRongCloud(msg.TModel.token);
                prefs.putString("RongToken", msg.TModel.token+"");

            }else{
                ToastFactory.getLongToast(mContext,"获取融云ToKen失败！");
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
            Log.i(Constant.TAG, "GetToken"+e+"==err==="+err);
        }

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

    private Dialog dayDialog;
    private Dialog vipDialog;
    private Dialog baseDialog;
    private View dialogContentView;
    private EditText et_set_value;
    private TextView tv_three_day,tv_week,tv_half_month,tv_month,tv_service_content,tv_sure_set;
    private RecyclerView rv_member_list;
    public void setDayDialog(){
        dayDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_day, null);
        dayDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_three_day= (TextView) dayDialog.findViewById(R.id.tv_three_day);
        tv_three_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=3;
                dayDialog.dismiss();
                tv_day.setText("最近三天");
            }
        });
        tv_week= (TextView) dayDialog.findViewById(R.id.tv_week);
        tv_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=7;
                dayDialog.dismiss();
                tv_day.setText("一个星期");
            }
        });
        tv_half_month= (TextView) dayDialog.findViewById(R.id.tv_half_month);
        tv_half_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=15;
                dayDialog.dismiss();
                tv_day.setText("半个月");

            }
        });
        tv_month= (TextView) dayDialog.findViewById(R.id.tv_month);
        tv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day=30;
                dayDialog.dismiss();
                tv_day.setText("一个月");
            }
        });

        dayDialog.getWindow().setGravity(Gravity.BOTTOM);
        dayDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        dayDialog.setCanceledOnTouchOutside(true);
        dayDialog.show();
    }

    public void setVipDialog(final List<ResultVipGradeBean.VipGradeBean> mData){
        vipDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_vip, null);
        vipDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        rv_member_list= (RecyclerView) vipDialog.findViewById(R.id.rv_member_list);
        rv_member_list.setLayoutManager(new LinearLayoutManager(this));
        rv_member_list.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                vipDialog.dismiss();
                gradeId=mData.get(position).getId();
                tv_grade.setText(mData.get(position).getTitle());
            }
        });

        gradeAdapter=new MemberGradeAdapter(mData,mActivity,mContext);
        rv_member_list.setAdapter(gradeAdapter);

        vipDialog.getWindow().setGravity(Gravity.BOTTOM);
        vipDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        vipDialog.setCanceledOnTouchOutside(true);
        vipDialog.show();
    }


    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(NewMainActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(getResources().getColor(R.color.new_base_bg));
        }
        if (true) {// boolean,设置标题
            dpd.setTitle("日期选择");
        }
        if (false) {// boolean,只能选择某些日期
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.MONTH, i);
                dates[i + 6] = date;
            }
            dpd.setSelectableDays(dates);
        }
        if (true) {// boolean,部分高亮
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.WEEK_OF_YEAR, i);
                dates[i + 6] = date;
            }
            dpd.setHighlightedDays(dates);
        }
        if (false) {// boolean,某些日期不可选
            Calendar[] dates = new Calendar[3];
            for (int i = -1; i <= 1; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.DAY_OF_MONTH, i);
                dates[i + 1] = date;
            }
            dpd.setDisabledDays(dates);
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        switch (selectDate){
            case 2://开始购买日期
                tv_buy_start_date.setText(date);
                break;
            case 3://结束购买日期
                tv_buy_end_date.setText(date);
                break;
            case 4://开始生日日期
                tv_birthday_start_date.setText(date);
                break;
            case 5://结束生日日期
                tv_birthday_end_date.setText(date);
                break;
            case 6://开始注册日期
                tv_reg_start_date.setText(date);
                break;
            case 7://结束注册日期
                tv_reg_end_date.setText(date);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==901){//标签筛选
            if(data!=null){
                tags=new ArrayList<>();
                tags=(ArrayList<GetTagListBean>)data.getSerializableExtra("selectList");
                tagAdapter = new MemberQueryTagAdapter<>(mContext);
                member_lable_flow_layout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
                member_lable_flow_layout.setAdapter(tagAdapter);
                tagAdapter.onlyAddAll(tags);
            }
        }
    }

}
