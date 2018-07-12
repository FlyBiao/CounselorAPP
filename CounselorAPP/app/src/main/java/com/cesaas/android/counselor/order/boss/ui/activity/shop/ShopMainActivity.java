package com.cesaas.android.counselor.order.boss.ui.activity.shop;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.cesaas.android.counselor.order.bean.ResultBossUserBean;
import com.cesaas.android.counselor.order.boss.adapter.SalesProportionAdapter;
import com.cesaas.android.counselor.order.boss.bean.ResultShopSalesBean;
import com.cesaas.android.counselor.order.boss.bean.SalesProportionBean;
import com.cesaas.android.counselor.order.boss.net.ShopSaleMonthTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopSalesNet;
import com.cesaas.android.counselor.order.boss.net.ShopVipTargetNet;
import com.cesaas.android.counselor.order.boss.net.ShopYestDaySaleStatisticNet;
import com.cesaas.android.counselor.order.boss.ui.activity.BossUserInfoActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.DailyActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.OnLineShopActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.ShopSellNumberActivity;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.BossUserInfoNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.DateFirstLast;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShopMainActivity extends BasesActivity implements View.OnClickListener{

    private TextView tvTitle,tvLeftTitle;
    private TextView tvShopQuantity,tvBarcodeQuantityr,tvSaleAmount;
    private TextView tv_check_shop_quantity,tv_check_check_sale_amount,tv_check_barcode_quantity;
    private TextView tv_day,tv_month,tv_year;
    private TextView tv_retail_total_amount;
    private TextView tv_head_user_name;
    private ImageView ivShare;
    private CircleImageView ivw_head_user_icon;
    private LinearLayout llBack,ll_base_title_left;
    private LinearLayout llOnlineShop,llShopSellNumber,llStoreSales;
    private ScrollView sv_shop_img;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View headerView;
    private RecyclerView mRecyclerView;

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
    private ShopSaleMonthTargetNet shopSaleMonthTargetNet;
    private ShopYestDaySaleStatisticNet shopYestDaySaleStatisticNet;

    private WaitDialog dialog;
    private double retailTotal;

    private SalesProportionAdapter adapter;
    private List<SalesProportionBean> proportionBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        dialog = new WaitDialog(mContext);

        initView();
        initDrawerLayout();
        initData();

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

        proportionBeen=new ArrayList<>();
        for (int i=0;i<5;i++){
            SalesProportionBean bean=new SalesProportionBean();
            bean.setName("类别"+i);
            bean.setNumber1(i);
            bean.setNumber2(i);
            bean.setNumber3(i);
            bean.setNumber4(i);
            proportionBeen.add(bean);
        }
        adapter=new SalesProportionAdapter(proportionBeen);
        mRecyclerView.setAdapter(adapter);
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
        }else{
            ivw_head_user_icon.setImageResource(R.mipmap.not_user_icon);
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

    @Override
    public void onClick(View v) {

    }

    private void setOnClick(){
        llOnlineShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("leftTitle",tvTitle.getText().toString());
                bundle.putInt("type",1);
                Skip.mNextFroData(mActivity,MerchantReportActivity.class,bundle);
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

        mRecyclerView= (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

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


        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        ivShare= (ImageView) findViewById(R.id.iv_add_module);
        ivShare.setVisibility(View.VISIBLE);
        ivShare.setImageResource(R.mipmap.share);

        tvShopQuantity= (TextView) findViewById(R.id.tv_shop_quantity);
        tvBarcodeQuantityr= (TextView) findViewById(R.id.tv_barcode_quantity);
        tvSaleAmount= (TextView) findViewById(R.id.tv_sale_amount);

        tv_day= (TextView) findViewById(R.id.tv_day);
        tv_month= (TextView) findViewById(R.id.tv_month);
        tv_year= (TextView) findViewById(R.id.tv_year);
        tv_retail_total_amount= (TextView) findViewById(R.id.tv_retail_total_amount);

        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setVisibility(View.GONE);
        sv_shop_img= (ScrollView) findViewById(R.id.sv_shop_img);

        llOnlineShop=(LinearLayout) findViewById(R.id.ll_online_shop);
        llShopSellNumber=(LinearLayout) findViewById(R.id.ll_shop_sell_number);
        llStoreSales=(LinearLayout) findViewById(R.id.ll_store_sales);

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
                    case R.id.menu_app_update:
                        mDrawerLayout.closeDrawers();
                        ToastFactory.getLongToast(mContext,"已是最新版本！");
                        break;
                    case R.id.menu_app_setting:
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.menu_app_exit:
                        mDrawerLayout.closeDrawers();
                        exit();
                        break;
                    case R.id.menu_app_user_info:
                        mDrawerLayout.closeDrawers();
                        Skip.mNext(mActivity,BossUserInfoActivity.class);
                        break;
//                    case R.id.menu_app_daily:
//                        mDrawerLayout.closeDrawers();
//                        bundle.putString("leftTitle",tvTitle.getText().toString());
//                        Skip.mNextFroData(mActivity,DailyActivity.class,bundle);
//                        break;
//                    case R.id.menu_app_report:
//                        ToastFactory.getLongToast(mContext,"开发中，敬请期待！");
////                        Skip.mNext(mActivity,QueryReportActivity.class);
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
                                ShopMainActivity.this.finish();
                                setResult(0xe); // 用于退出时关闭本页
                                onExit();
                                Skip.mNextFroData(mActivity, LoginActivity.class, bundle,true);
                            }
                        });
                    }
                }, null);
    }

}
