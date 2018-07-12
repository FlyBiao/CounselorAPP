package com.cesaas.android.counselor.order.boss.ui.fragment.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.bean.ResultBossUserBean;
import com.cesaas.android.counselor.order.boss.bean.ResultCrmDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopMonthDataBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopSalesBean;
import com.cesaas.android.counselor.order.boss.bean.ResultShopYestDaySaleStatisticBean;
import com.cesaas.android.counselor.order.boss.net.ShopSaleMonthTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopSalesNet;
import com.cesaas.android.counselor.order.boss.net.ShopVipTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopYearSaleTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopYestDaySaleStatisticNet;
import com.cesaas.android.counselor.order.boss.ui.activity.OnLineShopActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.ShopSellNumberActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.StoreSalesActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.VipTargetActivity;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultProcessedServiceSumBean;
import com.cesaas.android.counselor.order.net.BossUserInfoNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DateFirstLast;
import com.cesaas.android.counselor.order.utils.GetFileNameUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/23 14:56
 * Version 1.0
 */

public class BossHomeFragment extends BaseFragment {

    private TextView tvFaCaretDown,tvFaCaretUp;
    private TextView tvTitle,tvLeftTitle;
    private TextView tvAllData,tvDirectSalesData,tvJoinData,tvPoolData,tvAgencyData,tv_line_sales_data;
    private TextView tvShopQuantity,tvBarcodeQuantityr,tvSaleAmount;
    private TextView tv_month_sales_amount,tv_month_sales_target_amount,tv_month_progress;
    private TextView tv_year_sales_amount,tv_year_target_sales_amount,tv_year_progress;
    private TextView tv_vip_quantity,tv_vip_target_quantity,tv_vip_progress;
    private TextView tv_check_shop_quantity,tv_check_check_sale_amount,tv_check_barcode_quantity;
    private TextView tv_check_vip,tv_check_month,tv_check_year;
    private TextView tv_day,tv_month,tv_year;
    private TextView tv_month_target,tv_year_target,tv_retail_total_amount,tv_retail_total_amounts;
    private TextView tv_before_day,tv_before_progress,tv_last_week,tv_last_week_progress;
    private LinearLayout ll_vip_target,ll_month_shop,ll_year_shop;
    private LinearLayout llOnlineShop,llShopSellNumber,llStoreSales,ll_base_title_left,llBack;
    private ProgressBar pbActualSalesMonth,pbActualSalesYear,pbVip;
    private ScrollView sv_shop_img;
    private ImageView ivShare;

    private List<TextView> textViewList=new ArrayList<>();

    private static String ossImageUrl;//
    private static OSS oss;
    private File oldFile;
    private File newFile;

    private int shopType=0;


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

    /**
     * 单例
     */
    public static BossHomeFragment newInstance() {
        BossHomeFragment fragmentCommon = new BossHomeFragment();
        return fragmentCommon;
    }

    public void onEventMainThread(ResultProcessedServiceSumBean msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_boss_home;
    }


    /**
     * 接收用户信息数据
     * @param msg
     */
    public void onEventMainThread(ResultBossUserBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            if(msg.TModel.BrandName!=null){
                tvTitle.setText("首页");
            }else{
                tvTitle.setText("首页");
            }
        }else{
            tvTitle.setText("首页");
        }
    }

    /**
     * 接收店铺销售数据
     * @param msg
     */
    public void onEventMainThread(ResultShopSalesBean msg) {
        if(msg.TModel!=null){
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);// 设置精确到小数点后2位

            retailTotal=msg.TModel.getPayment();

            tvShopQuantity.setText(msg.TModel.getShopQuantity()+"");
            tvBarcodeQuantityr.setText(msg.TModel.getStyleQuantity()+"");
            tv_retail_total_amount.setText("￥"+numberFormat.format(msg.TModel.getPayment()));
            tv_retail_total_amounts.setText(String.valueOf(numberFormat.format(msg.TModel.getPayment())));

            double shopQuantity=msg.TModel.getShopQuantity();
            double saleAmount=retailTotal/shopQuantity;
            if(retailTotal!=0){
                tvSaleAmount.setText(numberFormat.format(saleAmount)+"");
            }else{
                tvSaleAmount.setText("0");
            }

            if(shopType!=0){
                shopYestDaySaleStatisticNet=new ShopYestDaySaleStatisticNet(getContext());
                shopYestDaySaleStatisticNet.setData(shopType);

            }else{
                shopYestDaySaleStatisticNet=new ShopYestDaySaleStatisticNet(getContext());
                shopYestDaySaleStatisticNet.setData();
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
                    tvFaCaretUp.setTextColor(getContext().getResources().getColor(R.color.red));
                    tvFaCaretUp.setTextColor(getContext().getResources().getColor(R.color.red));
                    tv_before_progress.setTextColor(getContext().getResources().getColor(R.color.red));
                }else{//下降
                    tvFaCaretUp.setText(R.string.fa_caret_down);
                    tvFaCaretUp.setTextColor(getContext().getResources().getColor(R.color.springgreen));
                    tvFaCaretUp.setTextColor(getContext().getResources().getColor(R.color.springgreen));
                    tv_before_progress.setTextColor(getContext().getResources().getColor(R.color.springgreen));
                }
            }else{
                tv_before_progress.setText(0+"%");
            }

            if(msg.TModel.getLastPayment()>0){
                double testWeek=retailTotal-msg.TModel.getLastPayment();
                double week=testWeek / msg.TModel.getLastPayment() * 100;
                tv_last_week_progress.setText(numberFormat.format(week)+"%");
                if(week>=0){//上升
                    tv_last_week_progress.setTextColor(getContext().getResources().getColor(R.color.red));
                    tvFaCaretDown.setText(R.string.fa_caret_up);
                    tvFaCaretDown.setTextColor(getContext().getResources().getColor(R.color.red));
                }else{//下降
                    tvFaCaretDown.setText(R.string.fa_caret_down);
                    tv_last_week_progress.setTextColor(getContext().getResources().getColor(R.color.springgreen));
                    tvFaCaretDown.setTextColor(getContext().getResources().getColor(R.color.springgreen));
                }
            }else{
                tv_last_week_progress.setText(0+"%");
            }
        }
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        tv_check_shop_quantity=findView(R.id.tv_check_shop_quantity);
        tv_check_shop_quantity.setTypeface(App.font);
        tv_check_shop_quantity.setText(R.string.fa_external_link);
        tv_check_barcode_quantity=findView(R.id.tv_check_barcode_quantity);
        tv_check_barcode_quantity.setTypeface(App.font);
        tv_check_barcode_quantity.setText(R.string.fa_external_link);
        tv_check_check_sale_amount=findView(R.id.tv_check_check_sale_amount);
        tv_check_check_sale_amount.setTypeface(App.font);
        tv_check_check_sale_amount.setText(R.string.fa_external_link);

        tv_retail_total_amounts=findView(R.id.tv_retail_total_amounts);
        tv_check_vip=findView(R.id.tv_check_vip);
        tv_check_vip.setTypeface(App.font);
        tv_check_vip.setText(R.string.fa_external_link);
        tv_check_month=findView(R.id.tv_check_month);
        tv_check_month.setTypeface(App.font);
        tv_check_month.setText(R.string.fa_external_link);
        tv_check_year=findView(R.id.tv_check_year);
        tv_check_year.setTypeface(App.font);
        tv_check_year.setText(R.string.fa_external_link);

        ll_vip_target=findView(R.id.ll_vip_target);
        ll_month_shop=findView(R.id.ll_month_shop);
        ll_year_shop=findView(R.id.ll_year_shop);

        tvShopQuantity=findView(R.id.tv_shop_quantity);
        tvBarcodeQuantityr=findView(R.id.tv_barcode_quantity);
        tvSaleAmount=findView(R.id.tv_sale_amount);

        tv_month_sales_amount=findView(R.id.tv_month_sales_amount);
        tv_month_sales_target_amount=findView(R.id.tv_month_sales_target_amount);
        tv_month_progress=findView(R.id.tv_month_progress);

        tv_year_sales_amount=findView(R.id.tv_year_sales_amount);
        tv_year_target_sales_amount=findView(R.id.tv_year_target_sales_amount);
        tv_year_progress=findView(R.id.tv_year_progress);

        tv_vip_quantity=findView(R.id.tv_vip_quantity);
        tv_vip_target_quantity=findView(R.id.tv_vip_target_quantity);
        tv_vip_progress=findView(R.id.tv_vip_progress);

        tv_day=findView(R.id.tv_day);
        tv_month=findView(R.id.tv_month);
        tv_year=findView(R.id.tv_year);
        tv_retail_total_amount=findView(R.id.tv_retail_total_amount);

        tv_month_target=findView(R.id.tv_month_target);
        tv_year_target=findView(R.id.tv_year_target);

        pbActualSalesMonth=findView(R.id.pb_actual_sales_month);
        pbActualSalesYear=findView(R.id.pb_actual_sales_year);
        pbVip=findView(R.id.pb_vip);

//        tv_before_day,tv_before_progress,tv_last_week,tv_last_week_progress;
        tv_before_day=findView(R.id.tv_before_day);
        tv_before_progress=findView(R.id.tv_before_progress);
        tv_last_week=findView(R.id.tv_last_week);
        tv_last_week_progress=findView(R.id.tv_last_week_progress);

        sv_shop_img=findView(R.id.sv_shop_img);

        tvAllData= findView(R.id.tv_all_data);
        tvAllData.setOnClickListener(this);
        tvDirectSalesData=findView(R.id.tv_direct_sales_data);
        tvDirectSalesData.setOnClickListener(this);
        tvJoinData=findView(R.id.tv_join_data);
        tvJoinData.setOnClickListener(this);
        tvPoolData=findView(R.id.tv_pool_data);
        tvPoolData.setOnClickListener(this);
        tvAgencyData=findView(R.id.tv_agency_data);
        tvAgencyData.setOnClickListener(this);
        tv_line_sales_data=findView(R.id.tv_line_sales_data);
        tv_line_sales_data.setOnClickListener(this);

        textViewList.add(tvAllData);
        textViewList.add(tvJoinData);
        textViewList.add(tvPoolData);
        textViewList.add(tvAgencyData);
        textViewList.add(tvDirectSalesData);
        textViewList.add(tv_line_sales_data);

        llOnlineShop=findView(R.id.ll_online_shop);
        llShopSellNumber=findView(R.id.ll_shop_sell_number);
        llStoreSales=findView(R.id.ll_store_sales);

        tvFaCaretUp=findView(R.id.tv_fa_caret_up);
        tvFaCaretUp.setTypeface(App.font);

        tvFaCaretDown=findView(R.id.tv_fa_caret_down);
        tvFaCaretDown.setTypeface(App.font);

        ll_base_title_left=findView(R.id.ll_base_title_left);
        ll_base_title_left.setVisibility(View.VISIBLE);
        tvLeftTitle= findView(R.id.tv_title_left);
        tvLeftTitle.setVisibility(View.VISIBLE);
        tvLeftTitle.setTypeface(App.font);
        tvLeftTitle.setText(R.string.fa_align_justify);

        tvTitle= findView(R.id.tv_base_title);
        ivShare= findView(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.share);
        llBack= findView(R.id.ll_base_title_back);
        llBack.setVisibility(View.GONE);
    }

    @Override
    public void initListener() {
        llOnlineShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle","首页");
                bundle.putInt("shopType",shopType);
                Skip.mNextFroData(getActivity(),OnLineShopActivity.class,bundle);
            }
        });
        llShopSellNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle","首页");
                bundle.putInt("shopType",shopType);
                Skip.mNextFroData(getActivity(),ShopSellNumberActivity.class,bundle);
            }
        });
        llStoreSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle","首页");
                bundle.putInt("shopType",shopType);
                Skip.mNextFroData(getActivity(),OnLineShopActivity.class,bundle);
//                Skip.mNextFroData(getActivity(),StoreSalesActivity.class,bundle);
            }
        });

        ll_month_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle","首页");
                Skip.mNextFroData(getActivity(),ShopSellNumberActivity.class,bundle);
            }
        });
        ll_year_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle","首页");
                Skip.mNextFroData(getActivity(),ShopSellNumberActivity.class,bundle);
            }
        });
        ll_vip_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle","首页");
                Skip.mNextFroData(getActivity(),VipTargetActivity.class,bundle);
            }
        });
        tvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseTabBean tabBean=new BaseTabBean();
                tabBean.setTabType(1);
                EventBus.getDefault().post(tabBean);
            }
        });
        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBitmapByView(sv_shop_img);
            }
        });
    }

    @Override
    public void initData() {
        dialog = new WaitDialog(getContext());
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

        userInfoNet=new BossUserInfoNet(getContext());
        userInfoNet.setData();

        if(shopType!=0){
            getSaleDataNet=new ShopSalesNet(getContext());
            getSaleDataNet.setData(shopType);

            shopSaleMonthTargetNet=new ShopSaleMonthTargetNet(getContext());
            shopSaleMonthTargetNet.setData(shopType);

            crmDataNet=new ShopVipTargetNet(getContext());
            crmDataNet.setData(shopType);

            shopYearSaleTargetNet=new ShopYearSaleTargetNet(getContext());
            shopYearSaleTargetNet.setData(shopType);

            shopYestDaySaleStatisticNet=new ShopYestDaySaleStatisticNet(getContext());
            shopYestDaySaleStatisticNet.setData(shopType);

        }else{
            getSaleDataNet=new ShopSalesNet(getContext());
            getSaleDataNet.setData();

            shopSaleMonthTargetNet=new ShopSaleMonthTargetNet(getContext());
            shopSaleMonthTargetNet.setData();

            crmDataNet=new ShopVipTargetNet(getContext());
            crmDataNet.setData();

            shopYearSaleTargetNet=new ShopYearSaleTargetNet(getContext());
            shopYearSaleTargetNet.setData();

            shopYestDaySaleStatisticNet=new ShopYestDaySaleStatisticNet(getContext());
            shopYestDaySaleStatisticNet.setData();
        }
    }


    @Override
    public void processClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_all_data://全部
                index=0;
                shopType=0;
                initData();
                break;
            case R.id.tv_join_data://直营
                index=1;
                shopType=1;
                initData();
                break;
            case R.id.tv_pool_data://加盟
                index=2;
                shopType=2;
                initData();
                break;
            case R.id.tv_agency_data://代理
                index=3;
                shopType=3;
                initData();
                break;
            case R.id.tv_direct_sales_data://联营
                index=4;
                shopType=4;
                initData();
                break;
            case R.id.tv_line_sales_data://线上
                index=5;
                shopType=5;
                initData();
                break;
        }
        setColor(index);
    }

    private void setColor(int index) {
        for(int i=0;i<textViewList.size();i++){
            textViewList.get(i).setTextColor(getContext().getResources().getColor(R.color.white));
        }
        textViewList.get(index).setTextColor(getContext().getResources().getColor(R.color.tv_green));
    }

    @Override
    public void fetchData() {

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
                MediaStore.Images.Media.insertImage(getContext().getContentResolver(), appDir.getAbsolutePath(), fileName, null);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(appDir.getAbsolutePath()!=null){
            // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
            OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
            oss = new OSSClient(getContext(), OSSKey.OSS_ENDPOINT, credentialProvider);
            //压缩图片
            oldFile=new File(appDir.getAbsolutePath());
            newFile = CompressHelper.getDefault(getContext()).compressToFile(oldFile);
            //调用AliOss上传图片
            setUploadAliOss(GetFileNameUtils.getFileName(newFile.getName(),prefs),newFile.getAbsolutePath());
        }else{
            ToastFactory.getLongToast(getContext(),"获取图片失败，请重试！");
        }
    }

    private void showShare(String shareTitle,String imageUrl,String shreUrl,String content) {
        ShareSDK.initSDK(getContext());
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle(shareTitle);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(shreUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("点击查看");
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
        oks.show(getContext());
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
                getActivity().runOnUiThread(new Runnable() {
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
                getActivity().runOnUiThread(new Runnable() {
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                });
                //获取上传阿里云Image
                ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //执行分享操作
                        showShare(tvTitle.getText().toString(),ossImageUrl,ossImageUrl,"首页数据");
                    }
                });
            }
        });
    }
}
