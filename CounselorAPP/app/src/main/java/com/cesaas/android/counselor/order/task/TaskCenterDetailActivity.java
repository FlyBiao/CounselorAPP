package com.cesaas.android.counselor.order.task;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
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
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.callback.ICallBack;
import com.cesaas.android.counselor.order.dialog.CameraDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.imagepicker.imageloader.GlideImageLoader;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.report.ReportDetailActivity;
import com.cesaas.android.counselor.order.store.bean.DisplayImagesBean;
import com.cesaas.android.counselor.order.task.bean.TaskImageParam;
import com.cesaas.android.counselor.order.utils.AbPhotoUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务中心详情
 */
public class TaskCenterDetailActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBack;
    private TextView tvBaseTitle;
    private WebView wv_task_center_detail;
    private SwipeRefreshLayout swipeRefreshLayout;

    public String mobile;//手机号
    public String icon;//用户头像
    public String name;//用户名称
    public String nickName;//用户昵称
    public String sex;//性别
    public String shopId;//店铺ID
    public String shopName;//店铺名称
    public String shopMobile;//店铺电话
    public String typeName;//用户身份：店员，店长
    public String typeId;//1：店长，2：店员
    public String vipId;
    public String ticket;//生成拉粉二维码用票
    public String imToken;
    public String counselorId;//顾问ID
    public String gzNo;//公众号GzNo
    public int tId;

    public String shopPostCode;//商品提交码
    public String shopProvince;//商品所在省
    public String shopAddress;//商品所在地址
    public String shopArea;//商品所在区域
    public String shopCity;//商品所在城市

    private UserInfoNet userInfoNet;
    private UserInfo userInfo;

    private JavascriptInterface javascriptInterface;
    private String type ;

    private String formId;//表单Id
    private String flowId;//流程编号
    private String taskId;//任务编号

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private int maxImgCount = 8;//允许选择图片最大数
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private String ossImageUrl;//
    private OSS oss;
    private JSONArray displayImages;//陈列图片数据

    private List<String> imgStringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_center_detail);

        Bundle bundle=getIntent().getExtras();
        formId=bundle.getString("formId");
        flowId=bundle.getString("flowId");
        taskId=bundle.getString("taskId");

        userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();

        initView();
        loadData();
        initImagePicker();
    }

    private void loadData() {
        WebViewUtils.initWebSettings(wv_task_center_detail,mDialog,Urls.TEST_TASK_DETAILS+"formid="+formId+"&flowid="+flowId+"&taskid="+taskId);

        javascriptInterface = new JavascriptInterface(mContext);
        wv_task_center_detail.addJavascriptInterface(javascriptInterface, "appHome");

        WebViewUtils.initSwipeRefreshLayout(swipeRefreshLayout,wv_task_center_detail);

    }

    private void initView() {
        wv_task_center_detail= (WebView) findViewById(R.id.wv_task_center_detail);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshShopLayout);

        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);

        tvBaseTitle.setText("任务详情");
        llBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);

                break;
        }
    }


    /**
     * 设置添加图片
     */
    public void setAddImage(){
        //打开选择,本次允许选择的数量
        selImageList = new ArrayList<>();
        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    /**
     * 获取添加图片返回结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                displayImages=new JSONArray();
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
                displayImages=new JSONArray();
                for (int i=0;i<selImageList.size();i++){
                    //调用AliOss上传图片
                    setUploadAliOss(selImageList.get(i).name,selImageList.get(i).path);
                }
            }
        }
    }

    /**
     * 构造上传请求
     * bucketName - 上传到Bucket的名字
     * objectKey - 上传到OSS后的ObjectKey
     * uploadFilePath - 上传文件的本地路径
     */
    public void setUploadAliOss(String imageName,String imageUrl){
        PutObjectRequest put = new PutObjectRequest(OSSKey.BUCKET_NAME, imageName, imageUrl);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
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
                    // 服务异常
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
                ossImageUrl=OSSKey.IMAGE_URL+request.getObjectKey();
//                DisplayImagesBean imagesBean=new DisplayImagesBean();
//                imagesBean.setDescribe("陈列图片");
//                imagesBean.setImageUrl(ossImageUrl);
//                displayImages.put(imagesBean.getDisplayImageArray());
//                Log.i(Constant.TAG,"阿里OSS图片路劲："+displayImages+"=="+ossImageUrl);
                //调用JS方法
//                wv_task_center_detail.loadUrl("javascript:showInfoFromApp('" + ossImageUrl + "')");

                imgStringList.add(ossImageUrl);
                TaskImageParam bean=new TaskImageParam();
                bean.setType(29);
                bean.setParam(imgStringList);
                Log.i(Constant.TAG,"阿里OSS图片路劲："+"json:"+JsonUtils.toJson(bean));
            }
        });
    }


    public void onEventMainThread(ResultUserBean msg) {

        if (msg != null) {
            mobile=msg.TModel.Mobile;
            icon=msg.TModel.Icon;//用户头像
            name=msg.TModel.Name;//用户名称
            nickName=msg.TModel.NickName;//用户昵称
            sex=msg.TModel.Sex;//性别
            shopId=msg.TModel.ShopId;//店铺ID
            shopName=msg.TModel.ShopName;//店铺名称
            shopMobile=msg.TModel.ShopMobile;//店铺电话
            typeName=msg.TModel.TypeName;//用户身份：店员，店长
            typeId=msg.TModel.TypeId;//1：店长，2：店员
            vipId=msg.TModel.VipId+"";
            ticket=msg.TModel.Ticket;//生成拉粉二维码用票
            imToken=msg.TModel.ImToken;
            counselorId=msg.TModel.CounselorId;//顾问ID
            gzNo=msg.TModel.GzNo;//公众号GzNo
            tId=msg.TModel.tId;

            shopPostCode=msg.TModel.ShopPostCode;//商品提交码
            shopProvince=msg.TModel.ShopProvince;//商品所在省
            shopAddress=msg.TModel.ShopAddress;//商品所在地址
            shopArea=msg.TModel.ShopArea;//商品所在区域
            shopCity=msg.TModel.ShopCity;//商品所在城市
        }
    }

    /**
     * 初始化图片选项
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }



    /**
     * 定义JS交互接口
     * @author fgb
     *
     */
    class JavascriptInterface implements ICallBack{
        Context mContext;

        JavascriptInterface(Context c) {
            mContext = c;
        }

        /**
         * 返回UserToken
         *
         * @return UserToken
         */
        @android.webkit.JavascriptInterface
        public String getUserInfo() {
            return prefs.getString("token");
        }


        /**
         * WebView posMessag
         *
         * @param postData
         */
        @android.webkit.JavascriptInterface
        public void postMessage(String postData) {
            try {
                JSONObject mJson = new JSONObject(postData);
                type = mJson.optString("type");
                if ("29".equals(type)) {//上传图片
                    setAddImage();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        /**
         * 返回用户信息
         *
         * @return
         */
        @android.webkit.JavascriptInterface
        public String appUserInfo() {

            userInfo = new UserInfo();
            userInfo.setCounselorId(counselorId);
            userInfo.setGzNo(gzNo);
            userInfo.setIcon(icon);
            userInfo.setImToken(imToken);
            userInfo.setName(name);
            userInfo.setMobile(mobile);
            userInfo.setNickName(nickName);
            userInfo.setSex(sex);
            userInfo.setShopAddress(shopAddress);
            userInfo.setShopArea(shopArea);
            userInfo.setShopCity(shopCity);
            userInfo.setShopId(shopId);
            userInfo.setShopMobile(shopMobile);
            userInfo.setShopName(shopName);
            userInfo.setShopPostCode(shopPostCode);
            userInfo.setShopProvince(shopProvince);
            userInfo.setTicket(ticket);
            userInfo.setTypeId(typeId);
            userInfo.setTypeName(typeName);
            userInfo.setVipId(vipId);
            userInfo.settId(tId);

            return JsonUtils.toJson(userInfo);
        }

        @Override
        public void solve(String result) {
            Log.i("ICallBack","执行了毁掉"+result);
        }
    }
}
