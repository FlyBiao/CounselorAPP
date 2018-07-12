package com.cesaas.android.counselor.order.boss.ui.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.UserCentreActivity;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.base.MyBaseActivity;
import com.cesaas.android.counselor.order.bean.ResultBossUserBean;
import com.cesaas.android.counselor.order.boss.bean.ResultCrmDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopMonthDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopSalesBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopYestDaySaleStatisticBean;
import com.cesaas.android.counselor.order.boss.net.ShopSaleMonthTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopVipTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopSaleTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopSalesNet;
import com.cesaas.android.counselor.order.boss.net.ShopYearSaleTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopYestDaySaleStatisticNet;
import com.cesaas.android.counselor.order.boss.ui.activity.shop.MerchantReportActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.shop.ShopMainActivity;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.manager.ui.activity.ManagerMainActivity;
import com.cesaas.android.counselor.order.net.BossUserInfoNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AppUpdateUtil;
import com.cesaas.android.counselor.order.utils.DateFirstLast;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.imkit.RongIM;

public class BossMainActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvLeftTitle,tvFaCaretDown,tvFaCaretUp;
    private TextView tvAllData,tvDirectSalesData,tvJoinData,tvPoolData,tvAgencyData;
    private TextView tvShopQuantity,tvBarcodeQuantityr,tvSaleAmount;
    private TextView tv_month_sales_amount,tv_month_sales_target_amount,tv_month_progress;
    private TextView tv_year_sales_amount,tv_year_target_sales_amount,tv_year_progress;
    private TextView tv_vip_quantity,tv_vip_target_quantity,tv_vip_progress;
    private TextView tv_check_shop_quantity,tv_check_check_sale_amount,tv_check_barcode_quantity;
    private TextView tv_check_vip,tv_check_month,tv_check_year;
    private TextView tv_day,tv_month,tv_year;
    private TextView tv_month_target,tv_year_target,tv_retail_total_amount;
    private TextView tv_before_day,tv_before_progress,tv_last_week,tv_last_week_progress;
    private TextView tv_head_user_name;
    private ImageView ivShare;
    private CircleImageView ivw_head_user_icon;
    private LinearLayout llBack,ll_base_title_left;
    private LinearLayout ll_vip_target,ll_month_shop,ll_year_shop;
    private LinearLayout llOnlineShop,llShopSellNumber,llStoreSales;
    private ProgressBar pbActualSalesMonth,pbActualSalesYear,pbVip;
    private ScrollView sv_shop_img;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View headerView;

    private List<TextView> textViewList=new ArrayList<>();
    private long exitTime = 0; // 退出点击间隔时间

    private static String ossImageUrl;//
    private static OSS oss;
    private File oldFile;
    private File newFile;

    private Typeface font;
    private String mobile;
    private String icon;
    private String nickName;

    //用户信息
    private BossUserInfoNet userInfoNet;
    private ShopSalesNet getSaleDataNet;
    private ShopVipTargetNet crmDataNet;
//    private ShopSaleTargetNet shopSaleDataNet;
    private ShopSaleMonthTargetNet shopSaleMonthTargetNet;
    private ShopYestDaySaleStatisticNet shopYestDaySaleStatisticNet;
    private ShopYearSaleTargetNet shopYearSaleTargetNet;

    private WaitDialog dialog;
    private double retailTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_main);
        dialog = new WaitDialog(mContext);

        initView();
        initDrawerLayout();
        initData();
        AppUpdateUtil.checkUpdate(mContext);
    }

    private void initData(){
        if(AbDateUtil.YesTerDay().equals("1")){//本月第一天
            tv_day.setText(DateFirstLast.getDateDay(DateFirstLast. a_day_last(AbDateUtil.getCurrentDate("yyyy-MM-dd")))+"");
            tv_month.setText(DateFirstLast.getDateMonth(DateFirstLast. a_day_last(AbDateUtil.getCurrentDate("yyyy-MM-dd"))));
        }else{
            tv_day.setText(Integer.parseInt(AbDateUtil.YesTerDay())-1+"");
            tv_month.setText(AbDateUtil.getMonth());
        }
        tv_year.setText(AbDateUtil.getYear());

        tv_month_target.setText(AbDateUtil.getMonth()+"月");
        tv_year_target.setText(AbDateUtil.getYear()+"年");

        getSaleDataNet=new ShopSalesNet(mContext);
        getSaleDataNet.setData();

        shopSaleMonthTargetNet=new ShopSaleMonthTargetNet(mContext);
        shopSaleMonthTargetNet.setData();

        crmDataNet=new ShopVipTargetNet(mContext);
        crmDataNet.setData();

        shopYestDaySaleStatisticNet=new ShopYestDaySaleStatisticNet(mContext);
        shopYestDaySaleStatisticNet.setData();

        userInfoNet=new BossUserInfoNet(mContext);
        userInfoNet.setData();
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

                tvTitle.setText(msg.TModel.BrandName);
                tv_head_user_name.setText(nickName);
            }else{
                tvTitle.setText("报表");
            }
        }else{
            tvTitle.setText("报表");
        }

        if(msg.TModel.Icon!=null && !"".equals(msg.TModel.Icon)){
            Glide.with(mContext).load(msg.TModel.Icon).into(ivw_head_user_icon);
//            Glide.with(mContext).load(msg.TModel.Icon).placeholder(R.mipmap.not_user_icon).into(ivw_head_user_icon);
        }else{
            ivw_head_user_icon.setImageResource(R.mipmap.not_user_icon);
        }

        prefs.putString("TypeId", msg.TModel.TypeId);
        prefs.putString("shopName", msg.TModel.ShopName);
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

    /**
     * 接收店铺销售数据
     * @param msg
     */
    public void onEventMainThread(ResultShopSalesBean msg) {
        if(msg.TModel!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);// 设置精确到小数点后2位

            tvShopQuantity.setText(msg.TModel.getShopQuantity()+"");
            tvBarcodeQuantityr.setText(msg.TModel.getStyleQuantity()+"");
            tv_retail_total_amount.setText("￥"+numberFormat.format(msg.TModel.getPayment()));
            retailTotal=msg.TModel.getPayment();
            double shopQuantity=msg.TModel.getShopQuantity();
            double saleAmount=retailTotal/shopQuantity;
            if(retailTotal!=0){
                tvSaleAmount.setText(numberFormat.format(saleAmount)+"");
            }else{
                tvSaleAmount.setText("0");
            }
        }
    }

    /**
     * 接收店铺及业绩-店铺销售/目标数据【年】
     * @param msg
     */
    public void onEventMainThread(ResultShopDataBean msg) {
        if(msg.TModel!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);// 设置精确到小数点后2位
            tv_year_sales_amount.setText("￥"+numberFormat.format(msg.TModel.getPayment()));
            tv_year_target_sales_amount.setText("￥"+numberFormat.format(msg.TModel.getTargetAmount()));

            double tet=msg.TModel.getPayment()/msg.TModel.getTargetAmount()* 100;
            if(msg.TModel.getTargetAmount()!=0){
                if(tet>100){
                    tv_year_progress.setText(100+"%");
                }else{
                    tv_year_progress.setText(numberFormat.format(tet)+"%");
                }
            }else{
                if(msg.TModel.getPayment()!=0){
                    tv_year_progress.setText(100+"%");
                }else{
                    tv_year_progress.setText(0+"%");
                }
            }
            pbActualSalesYear.setProgress((int)tet);
        }
    }

    /**
     * 接收店铺及业绩-店铺销售/目标数据【月】
     * @param msg
     */
    public void onEventMainThread(ResultShopMonthDataBean msg) {
        if(msg.TModel!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);// 设置精确到小数点后2位

            tv_month_sales_amount.setText("￥"+numberFormat.format(msg.TModel.getPayment()));
            tv_month_sales_target_amount.setText("￥"+numberFormat.format(msg.TModel.getTargetAmount()));
            double tet= msg.TModel.getPayment() / msg.TModel.getTargetAmount() * 100;

            if(msg.TModel.getTargetAmount()!=0){
                if(tet>100){
                    tv_month_progress.setText(100+"%");
                }else{
                    tv_month_progress.setText(numberFormat.format(tet)+"%");
                }

            }else{
                if(msg.TModel.getPayment()!=0){
                    tv_month_progress.setText(100+"%");
                }else{
                    tv_month_progress.setText(0+"%");
                }
            }

            pbActualSalesMonth.setProgress((int)tet);

            shopYearSaleTargetNet=new ShopYearSaleTargetNet(mContext);
            shopYearSaleTargetNet.setData();
        }
    }

    /**
     * 店铺及业绩-拉粉数量/目标
     * @param msg
     */
    public void onEventMainThread(ResultCrmDataBean msg) {
        if(msg.TModel!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);// 设置精确到小数点后2位
            tv_vip_quantity.setText(msg.TModel.getQuantity()+"");
            tv_vip_target_quantity.setText(msg.TModel.getTargetQuantity()+"");
            double Quantity=msg.TModel.getQuantity();
            double TargetQuantity=msg.TModel.getTargetQuantity();
            if(msg.TModel.getTargetQuantity()!=0){
                double vip= Quantity /  TargetQuantity * 100;
                tv_vip_progress.setText(numberFormat.format(vip)+"%");
                pbVip.setProgress((int)vip);
            }else{
                tv_vip_progress.setText(100+"%");
                pbVip.setProgress(100);
            }
        }
    }
    /**
     * 销售数据
     * @param msg
     */
    public void onEventMainThread(ResultShopYestDaySaleStatisticBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);// 设置精确到小数点后2位

            tv_before_day.setText("￥"+numberFormat.format(msg.TModel.getPayment()));
            tv_last_week.setText("￥"+numberFormat.format(msg.TModel.getLastPayment()));

            if(msg.TModel.getPayment()>0){
                double testDay=retailTotal-msg.TModel.getPayment();
                double day=testDay / msg.TModel.getPayment() *100;
                tv_before_progress.setText(numberFormat.format(day)+"%");
                if(day>=0){//上升
                    tvFaCaretUp.setText(R.string.fa_caret_up);
                    tvFaCaretUp.setTextColor(mContext.getResources().getColor(R.color.red));
                    tvFaCaretUp.setTextColor(mContext.getResources().getColor(R.color.red));
                    tv_before_progress.setTextColor(mContext.getResources().getColor(R.color.red));
                }else{//下降
                    tvFaCaretUp.setText(R.string.fa_caret_down);
                    tvFaCaretUp.setTextColor(mContext.getResources().getColor(R.color.springgreen));
                    tvFaCaretUp.setTextColor(mContext.getResources().getColor(R.color.springgreen));
                    tv_before_progress.setTextColor(mContext.getResources().getColor(R.color.springgreen));
                }
            }else{
                tv_before_progress.setText(0+"%");
            }

            if(msg.TModel.getLastPayment()>0){
                double testWeek=retailTotal-msg.TModel.getLastPayment();
                double week=testWeek / msg.TModel.getLastPayment() * 100;
                tv_last_week_progress.setText(numberFormat.format(week)+"%");
                if(week>=0){//上升
                    tv_last_week_progress.setTextColor(mContext.getResources().getColor(R.color.red));
                    tvFaCaretDown.setText(R.string.fa_caret_up);
                    tvFaCaretDown.setTextColor(mContext.getResources().getColor(R.color.red));
                }else{//下降
                    tvFaCaretDown.setText(R.string.fa_caret_down);
                    tv_last_week_progress.setTextColor(mContext.getResources().getColor(R.color.springgreen));
                    tvFaCaretDown.setTextColor(mContext.getResources().getColor(R.color.springgreen));
                }
            }else{
                tv_last_week_progress.setText(0+"%");
            }
        }
    }

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_all_data://全部
                index=0;
                getSaleDataNet=new ShopSalesNet(mContext);
                getSaleDataNet.setData();
                break;
            case R.id.tv_join_data://直营
                index=1;
                getSaleDataNet=new ShopSalesNet(mContext);
                getSaleDataNet.setData(1);
                break;
            case R.id.tv_pool_data://加盟
                index=2;
                getSaleDataNet=new ShopSalesNet(mContext);
                getSaleDataNet.setData(2);
                break;
            case R.id.tv_agency_data://代理
                index=3;
                getSaleDataNet=new ShopSalesNet(mContext);
                getSaleDataNet.setData(3);
                break;
            case R.id.tv_direct_sales_data://联营
                index=4;
                getSaleDataNet=new ShopSalesNet(mContext);
                getSaleDataNet.setData(4);
                break;
        }
        setColor(index);
    }

    private void setOnClick(){
        llOnlineShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                Skip.mNextFroData(mActivity,OnLineShopActivity.class,bundle);
            }
        });
        llShopSellNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                Skip.mNextFroData(mActivity,ShopSellNumberActivity.class,bundle);
            }
        });
        llStoreSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
//                Skip.mNextFroData(mActivity,StoreSalesActivity.class,bundle);
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBitmapByView(sv_shop_img);
            }
        });

        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });

        ll_month_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                Skip.mNextFroData(mActivity,ShopSellNumberActivity.class,bundle);
            }
        });
        ll_year_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                Skip.mNextFroData(mActivity,ShopSellNumberActivity.class,bundle);
            }
        });
        ll_vip_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                Skip.mNextFroData(mActivity,VipTargetActivity.class,bundle);
            }
        });
    }

    private void setColor(int index) {
        for(int i=0;i<textViewList.size();i++){
            textViewList.get(i).setTextColor(mContext.getResources().getColor(R.color.white));
        }
        textViewList.get(index).setTextColor(mContext.getResources().getColor(R.color.tv_green));
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

    private void initView() {
        font= Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView= (NavigationView) findViewById(R.id.navigation_view);

        ll_base_title_left= (LinearLayout) findViewById(R.id.ll_base_title_left);
        ll_base_title_left.setVisibility(View.VISIBLE);

//        tv_user_nick_name= (TextView) findViewById(R.id.tv_user_nick_name);

        tvLeftTitle= (TextView) findViewById(R.id.tv_title_left);
        tvLeftTitle.setVisibility(View.VISIBLE);
        tvLeftTitle.setTypeface(font);
        tvLeftTitle.setText(R.string.fa_align_justify);

        tv_check_shop_quantity= (TextView) findViewById(R.id.tv_check_shop_quantity);
        tv_check_shop_quantity.setTypeface(font);
        tv_check_shop_quantity.setText(R.string.fa_external_link);
        tv_check_barcode_quantity= (TextView) findViewById(R.id.tv_check_barcode_quantity);
        tv_check_barcode_quantity.setTypeface(font);
        tv_check_barcode_quantity.setText(R.string.fa_external_link);
        tv_check_check_sale_amount= (TextView) findViewById(R.id.tv_check_check_sale_amount);
        tv_check_check_sale_amount.setTypeface(font);
        tv_check_check_sale_amount.setText(R.string.fa_external_link);

        tv_check_vip= (TextView) findViewById(R.id.tv_check_vip);
        tv_check_vip.setTypeface(font);
        tv_check_vip.setText(R.string.fa_external_link);
        tv_check_month= (TextView) findViewById(R.id.tv_check_month);
        tv_check_month.setTypeface(font);
        tv_check_month.setText(R.string.fa_external_link);
        tv_check_year= (TextView) findViewById(R.id.tv_check_year);
        tv_check_year.setTypeface(font);
        tv_check_year.setText(R.string.fa_external_link);

        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        ivShare= (ImageView) findViewById(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.share);

        ll_vip_target= (LinearLayout) findViewById(R.id.ll_vip_target);
        ll_month_shop= (LinearLayout) findViewById(R.id.ll_month_shop);
        ll_year_shop= (LinearLayout) findViewById(R.id.ll_year_shop);

        tvShopQuantity= (TextView) findViewById(R.id.tv_shop_quantity);
        tvBarcodeQuantityr= (TextView) findViewById(R.id.tv_barcode_quantity);
        tvSaleAmount= (TextView) findViewById(R.id.tv_sale_amount);

        tv_month_sales_amount= (TextView) findViewById(R.id.tv_month_sales_amount);
        tv_month_sales_target_amount= (TextView) findViewById(R.id.tv_month_sales_target_amount);
        tv_month_progress= (TextView) findViewById(R.id.tv_month_progress);

        tv_year_sales_amount= (TextView) findViewById(R.id.tv_year_sales_amount);
        tv_year_target_sales_amount= (TextView) findViewById(R.id.tv_year_target_sales_amount);
        tv_year_progress= (TextView) findViewById(R.id.tv_year_progress);

        tv_vip_quantity= (TextView) findViewById(R.id.tv_vip_quantity);
        tv_vip_target_quantity= (TextView) findViewById(R.id.tv_vip_target_quantity);
        tv_vip_progress= (TextView) findViewById(R.id.tv_vip_progress);

        tv_day= (TextView) findViewById(R.id.tv_day);
        tv_month= (TextView) findViewById(R.id.tv_month);
        tv_year= (TextView) findViewById(R.id.tv_year);
        tv_retail_total_amount= (TextView) findViewById(R.id.tv_retail_total_amount);

        tv_month_target= (TextView) findViewById(R.id.tv_month_target);
        tv_year_target= (TextView) findViewById(R.id.tv_year_target);

        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setVisibility(View.GONE);

        pbActualSalesMonth= (ProgressBar) findViewById(R.id.pb_actual_sales_month);
        pbActualSalesYear= (ProgressBar) findViewById(R.id.pb_actual_sales_year);
        pbVip= (ProgressBar) findViewById(R.id.pb_vip);

//        tv_before_day,tv_before_progress,tv_last_week,tv_last_week_progress;
        tv_before_day= (TextView) findViewById(R.id.tv_before_day);
        tv_before_progress= (TextView) findViewById(R.id.tv_before_progress);
        tv_last_week= (TextView) findViewById(R.id.tv_last_week);
        tv_last_week_progress= (TextView) findViewById(R.id.tv_last_week_progress);

        sv_shop_img= (ScrollView) findViewById(R.id.sv_shop_img);

        tvAllData= (TextView) findViewById(R.id.tv_all_data);
        tvAllData.setOnClickListener(this);
        tvDirectSalesData= (TextView) findViewById(R.id.tv_direct_sales_data);
        tvDirectSalesData.setOnClickListener(this);
        tvJoinData= (TextView) findViewById(R.id.tv_join_data);
        tvJoinData.setOnClickListener(this);
        tvPoolData= (TextView) findViewById(R.id.tv_pool_data);
        tvPoolData.setOnClickListener(this);
        tvAgencyData= (TextView) findViewById(R.id.tv_agency_data);
        tvAgencyData.setOnClickListener(this);

        textViewList.add(tvAllData);
        textViewList.add(tvJoinData);
        textViewList.add(tvPoolData);
        textViewList.add(tvAgencyData);
        textViewList.add(tvDirectSalesData);

        llOnlineShop=(LinearLayout) findViewById(R.id.ll_online_shop);
        llShopSellNumber=(LinearLayout) findViewById(R.id.ll_shop_sell_number);
        llStoreSales=(LinearLayout) findViewById(R.id.ll_store_sales);

        tvFaCaretUp= (TextView) findViewById(R.id.tv_fa_caret_up);
        tvFaCaretUp.setTypeface(font);

        tvFaCaretDown= (TextView) findViewById(R.id.tv_fa_caret_down);
        tvFaCaretDown.setTypeface(font);

        setOnClick();
    }

    private void initDrawerLayout() {
        headerView = mNavigationView.getHeaderView(0);
        tv_head_user_name= (TextView) headerView. findViewById(R.id.tv_head_user_name);
        ivw_head_user_icon= (CircleImageView) headerView.findViewById(R.id.ivw_head_user_icon);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                Skip.mNext(mActivity,UserCentreActivity.class);
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.menu_app_update:
//                        mDrawerLayout.closeDrawers();
//                        ToastFactory.getLongToast(mContext,"已是最新版本！");
//                        break;
//                    case R.id.menu_app_setting:
//                        mDrawerLayout.closeDrawers();
//                        break;
                    case R.id.menu_app_exit:
                        mDrawerLayout.closeDrawers();
                        exit();
                        break;
                    case R.id.menu_app_user_info:
                        mDrawerLayout.closeDrawers();
                        Skip.mNext(mActivity,BossUserInfoActivity.class);
                        break;
//                    case R.id.menu_app_task:
//                        bundle.putString("leftTitle",tvTitle.getText().toString());
//                        Skip.mNextFroData(mActivity, ManagerMainActivity.class,bundle);
//                        break;
//                    case R.id.menu_app_daily:
//                        mDrawerLayout.closeDrawers();
//                        bundle.putString("leftTitle",tvTitle.getText().toString());
//                        Skip.mNextFroData(mActivity,DailyActivity.class,bundle);
//                        break;
//                    case R.id.menu_app_report:
//                        mDrawerLayout.closeDrawers();
////                        Skip.mNext(mActivity, ShopMainActivity.class);
////                        Skip.mNext(mActivity,QueryReportActivity.class);
//                        bundle.putString("leftTitle",tvTitle.getText().toString());
//                        bundle.putInt("type",1);
//                        Skip.mNextFroData(mActivity,MerchantReportActivity.class,bundle);
//                        break;
                }
                return false;
            }
        });

    }


    private Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(
                    Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        saveBitmap(bitmap);
        return bitmap;
    }

    public void saveBitmap(Bitmap bitmap) {
        // 保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"images");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = AbDateUtil.getStringDateShort();
        appDir = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(appDir);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 把文件插入到系统图库
        try {
            if(appDir.getAbsolutePath()!=null){
                MediaStore.Images.Media.insertImage(this.getContentResolver(), appDir.getAbsolutePath(), fileName, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(appDir.getAbsolutePath()!=null){
            // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
            oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);
            //压缩图片
            oldFile=new File(appDir.getAbsolutePath());
            newFile = CompressHelper.getDefault(mContext).compressToFile(oldFile);
            //调用AliOss上传图片
            setUploadAliOss(newFile.getName(),newFile.getAbsolutePath());
        }else{
            ToastFactory.getLongToast(mContext,"获取图片失败，请重试！");
        }
    }

    private void showShare(String shareTitle,String imageUrl,String shreUrl) {
        ShareSDK.initSDK(mContext);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(shareTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(shreUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shreUrl);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath(imageUrl);//确保SDcard下面存在此张图片
        oks.setImageUrl(imageUrl);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shreUrl);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(shreUrl);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(R.string.app_name+"");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(shreUrl);

        // 启动分享GUI
        oks.show(mContext);
    }

    public void setUploadAliOss(String imageName,String imageUrl){
        /**
         * 构造上传请求
         * bucketName - 上传到Bucket的名字
         * objectKey - 上传到OSS后的ObjectKey
         * uploadFilePath - 上传文件的本地路径
         */
        PutObjectRequest put = new PutObjectRequest(OSSKey.BUCKET_NAME, imageName, imageUrl);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//				Log.i(Constant.TAG, "Uploading..."+"=totalSize=="+totalSize+"==currentSize:"+currentSize);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                });

            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            /**
             * 上传失败
             * @param arg0
             * @param arg0
             *
             */
            @Override
            public void onFailure(PutObjectRequest arg0,
                                  com.alibaba.sdk.android.oss.ClientException clientExcepion,
                                  com.alibaba.sdk.android.oss.ServiceException serviceException) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    Log.i(Constant.TAG,"onFailure：本地异常如网络异常等:"+clientExcepion);
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.i(Constant.TAG,"ErrorCode:"+serviceException.getErrorCode());
                    Log.i(Constant.TAG,"RequestId:"+serviceException.getRequestId());
                    Log.i(Constant.TAG,"HostId:"+serviceException.getHostId());
                    Log.i(Constant.TAG,"RawMessage:"+serviceException.getRawMessage());
                }
            }

            /**
             * 上传成功
             * @param request
             * @param result
             */
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                //获取上传阿里云Image
                ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //执行分享操作
                        showShare(tvTitle.getText().toString(),ossImageUrl,ossImageUrl);
                    }
                });
            }
        });
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
                                BossMainActivity.this.finish();
                                setResult(0xe); // 用于退出时关闭本页
                                onExit();
                                Skip.mNextFroData(mActivity, LoginActivity.class, bundle,true);
                            }
                        });
                    }
                }, null);
    }

}
