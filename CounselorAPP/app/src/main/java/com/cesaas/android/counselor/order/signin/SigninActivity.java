package com.cesaas.android.counselor.order.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.custom.edittext.CustomNumEditText;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.imagepicker.imagepickeradapter.ImagePickerSigninAdapter;
import com.cesaas.android.counselor.order.signin.adapter.SigninTypeGalleryRecyclerAdapter;
import com.cesaas.android.counselor.order.signin.bean.ResultGetSignInTypeBean;
import com.cesaas.android.counselor.order.signin.bean.ResultSigninBean;
import com.cesaas.android.counselor.order.signin.bean.SigninTypeBean;
import com.cesaas.android.counselor.order.signin.map.MapFixedPointActivity;
import com.cesaas.android.counselor.order.signin.net.GetSignInTypeNet;
import com.cesaas.android.counselor.order.signin.net.SigninNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 签到页面
 */
public class SigninActivity extends BasesActivity implements View.OnClickListener ,ImagePickerSigninAdapter.OnRecyclerViewItemClickListener{

    private LinearLayout llBack,llSubmitSignin;
    private TextView tvBaseTitle,tvLocation;
    private CustomNumEditText etTextNum;
    private RecyclerView rvSigninTypeMenu;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerSigninAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数

    private JSONArray signinImage=new JSONArray();//
    private String ossImageUrl="";//
    private OSS oss;

    private  List<SigninTypeBean> models;
    private SigninTypeGalleryRecyclerAdapter mGalleryRecyclerAdapter;
    private GetSignInTypeNet signInTypeNet;

    private SigninNet signinNet;
    private String remark;
    private String address;
    private String Img="";
    private int stype;
    private int longtitude;
    private int latitude;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signInTypeNet=new GetSignInTypeNet(mContext);
        signInTypeNet.setData();

        initView();

        initWidget();
        initData();
        initAMapLocation();

    }

    /**
     * 初始化高德地图定位
     */
    private void initAMapLocation(){

        mLocationListener= new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                tvLocation.setText(aMapLocation.getAddress());
                longtitude=(int)aMapLocation.getLongitude();
                latitude=(int)aMapLocation.getLatitude();
                address=aMapLocation.getAddress();
            }
        };
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
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
     * 接收获取签到类型消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultSigninBean msg) {
        if (msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"签到成功！");
            Skip.mNext(mActivity, HomeActivity.class);

        }else{
            ToastFactory.getLongToast(mContext,"签到失败！"+msg.Message);
        }
    }

    /**
     * 接收获取签到类型消息
     * @param msg 消息实体类
     */
	public void onEventMainThread(ResultGetSignInTypeBean msg) {
        if(msg.IsSuccess==true){
            models=new ArrayList<>();
            models.addAll(msg.TModel);

            initrvAdapter();
        }else{
            ToastFactory.getLongToast(mContext,"获取数据失败！"+msg.Message);
        }
    }

    public void initrvAdapter(){

        //固定高度
        rvSigninTypeMenu.setHasFixedSize(true);
        //创建布局管理器
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //设置横向
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        //设置布局管理器
        rvSigninTypeMenu.setLayoutManager(linearLayoutManager);

        //创建适配器
//        mGalleryRecyclerAdapter=new SigninTypeGalleryRecyclerAdapter(this,models);
        //绑定适配器
        rvSigninTypeMenu.setAdapter(mGalleryRecyclerAdapter);
        //setOnRecyclerViewItemClickListener
        mGalleryRecyclerAdapter.setOnRecyclerViewItemClickListener(new SigninTypeGalleryRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                stype=models.get(position).getTypeNumber();
            }
        });
    }

    /**
     * 初始化图片上传控件
     */
    private void initWidget() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerSigninAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tvBaseTitle.setText("签到");
    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        rvSigninTypeMenu= (RecyclerView) findViewById(R.id.rv_signin_type_menu);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        llSubmitSignin= (LinearLayout) findViewById(R.id.ll_submit_signin);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvLocation= (TextView) findViewById(R.id.tv_location);

        etTextNum= (CustomNumEditText) findViewById(R.id.et_text_num);
        etTextNum.setEtHint("输入签到备注(可选)")//设置提示文字
                .setEtMinHeight(200)//设置最小高度，单位px
                .setLength(200)//设置总字数
                .setType(CustomNumEditText.PERCENTAGE)//TextView显示类型(SINGULAR单数类型)(PERCENTAGE百分比类型)
//                .setLineColor("#3F51B5")//设置横线颜色
                .show();

        llBack.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        llSubmitSignin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.tv_location://定位
                Skip.mNext(mActivity,MapFixedPointActivity.class);
                break;

            case R.id.ll_submit_signin://确定签到
                if(stype!=0){
                    signinNet=new SigninNet(mContext);
                    signinNet.setData(stype,longtitude,latitude,signinImage,remark,address);

                }else{
                    ToastFactory.getLongToast(mContext,"请选择签到类型！");
                }

                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSKey.ACCESS_ID, OSSKey.ACCESS_KEY);
        oss = new OSSClient(mContext, OSSKey.OSS_ENDPOINT, credentialProvider);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                for (int i=0;i<selImageList.size();i++){
                    //调用AliOss上传图片
                    setUploadAliOss(selImageList.get(i).name,selImageList.get(i).path);
                    Log.i(Constant.TAG,"本地图片："+selImageList.get(i).path);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
                for (int i=0;i<selImageList.size();i++){
                    //调用AliOss上传图片
                    setUploadAliOss(selImageList.get(i).name,selImageList.get(i).path);
                }
            }
        }
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
//				Log.i("PutObject", "Uploading..."+"=getUploadFilePath=="+request.getUploadFilePath());
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
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {

                }
            }

            /**
             * 上传成功
             * @param request
             * @param result
             */
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //获取上传阿里云Image
                if(OSSKey.IMAGE_URL+request.getObjectKey()!=null){
//                    ossImageUrl+=OSSKey.IMAGE_URL+request.getObjectKey()+",";
//                    Img=ossImageUrl.substring(zero,ossImageUrl.length()-1);
//                    Log.i(Constant.TAG,"阿里OSS图片路劲："+Img);

                    ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
                    signinImage.put(ossImageUrl);
                    Log.i(Constant.TAG,"阿里OSS图片路劲："+signinImage);
                }
            }
        });
    }

}
