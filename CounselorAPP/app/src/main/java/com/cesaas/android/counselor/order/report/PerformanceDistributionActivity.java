package com.cesaas.android.counselor.order.report;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
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
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.alioss.OSSKey;
import com.cesaas.android.counselor.order.base.BaseTestBean;
import com.cesaas.android.counselor.order.compressimage.CompressHelper;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.shopmange.ChooseClerkActivity;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;
import com.cesaas.android.counselor.order.webview.base.BaseUserInfo;
import com.cesaas.android.counselor.order.webview.bean.AlertDialogBean;
import com.cesaas.android.counselor.order.webview.bean.ConfirmDialogBean;
import com.cesaas.android.counselor.order.webview.bean.LabelScreenBean;
import com.cesaas.android.counselor.order.webview.bean.NotificationAppUpdateDataBean;
import com.cesaas.android.counselor.order.webview.bean.OpenFileBean;
import com.cesaas.android.counselor.order.webview.bean.PreviewPictureBean;
import com.cesaas.android.counselor.order.webview.bean.PromptDialogBean;
import com.cesaas.android.counselor.order.webview.bean.SelectClerkBean;
import com.cesaas.android.counselor.order.webview.bean.SelectMemberBean;
import com.cesaas.android.counselor.order.webview.bean.SendMsgBean;
import com.cesaas.android.counselor.order.webview.bean.SendWeChatBean;
import com.cesaas.android.counselor.order.webview.bean.SkipBean;
import com.cesaas.android.counselor.order.webview.bean.ToRightBean;
import com.cesaas.android.counselor.order.webview.bean.TopTitleBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptValueBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultPromptInfoBean;
import com.flybiao.materialdialog.MaterialDialog;

import org.json.JSONArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;

/**
 * 业绩分配
 */
public class PerformanceDistributionActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private ImageView iv_add_module;
    private TextView tvBaseTitle,tvBaseRightTitle,tv_message;
    private ScrollView scrollView;

    private JavascriptInterface javascriptInterface;
    protected WaitDialog mDialog;

    private String title;
    private String getUrl;
    private String url;
    private int requestCode=200;
    private MaterialDialog mMaterialDialog;
    private WaitDialog dialog;

    private static String ossImageUrl;//
    private static OSS oss;
    private File oldFile;
    private File newFile;

    private WebView wv_performance_distribution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_distribution);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            getUrl=bundle.getString("url");
            title=bundle.getString("title");
        }

        mMaterialDialog=new MaterialDialog(mContext);

        initView();
        initData();
    }

    public void initData() {
        mDialog = new WaitDialog(mContext);
        dialog = new WaitDialog(mContext);
        if(getUrl!=null){
            WebViewUtils.initWebSettings(wv_performance_distribution,mDialog, getUrl);
        }else{
            WebViewUtils.initWebSettings(wv_performance_distribution,mDialog, Urls.PERFORMANCE_DISTRIBUTION);
        }

//        WebViewUtils.initSwipeRefreshLayout(swipe_layout,wv_performance_distribution);

        javascriptInterface = new JavascriptInterface(mContext);
        wv_performance_distribution.addJavascriptInterface(javascriptInterface, "appHome");

    }


    private void initView() {
        scrollView= (ScrollView) findViewById(R.id.scrollView);
//        swipe_layout= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshShopLayout);
        wv_performance_distribution= (WebView) findViewById(R.id.wv_performance_distribution);
        tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvBaseRightTitle.setVisibility(View.VISIBLE);
        tvBaseRightTitle.setText("");
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText(title);
        tv_message= (TextView) findViewById(R.id.tv_message);
        tv_message.setText("分享");
        tv_message.setVisibility(View.GONE);
        iv_add_module= (ImageView) findViewById(R.id.iv_add_module);
        iv_add_module.setVisibility(View.VISIBLE);
        iv_add_module.setImageResource(R.mipmap.share);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseRightTitle.setOnClickListener(this);
        tv_message.setOnClickListener(this);
        iv_add_module.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                bundle.putString("Url",url);
                Skip.mNextFroData(mActivity,PerformanceDetailActivity.class,bundle);
                break;
            case R.id.tv_message:
                getBitmapByView(scrollView);
                break;
            case R.id.iv_add_module:
                getBitmapByView(scrollView);
                break;
        }
    }

    public void onEventMainThread(final WebViewRequestJsonBean mJson) {

        switch (mJson.getRequestType()){
            case 901://标签筛选
                LabelScreenBean labelScreenBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),LabelScreenBean.class);
                ToastFactory.getLongToast(mContext,"标签筛选");
                break;

            case 701://选择店员
                SelectClerkBean clerkBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SelectClerkBean.class);
                Intent intent= new Intent(mContext, ChooseClerkActivity.class);
                intent.putExtra("RoleIds",clerkBean.getParam().getPosition_id()+"");
                intent.putExtra("Multi",clerkBean.getParam().getMulti());
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Skip.mSelActivityResult(mActivity,requestCode,intent);
                break;
            case 401://选择会员
                SelectMemberBean memberBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SelectMemberBean.class);
                Skip.mActivityResult(mActivity,ChooseClerkActivity.class,requestCode);
                break;
            case 101://弹出对话框
                AlertDialogBean dialogBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),AlertDialogBean.class);
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("温馨提示！")
                            .setMessage(dialogBean.getParam().getMessage())
                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    //执行确定操作
                                    mMaterialDialog.dismiss();
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }}).setCanceledOnTouchOutside(true).show();
                }
                break;
            case 102://弹出确认框
                ConfirmDialogBean dialogBean1=JsonUtils.fromJson(mJson.getWebviewParamJson(),ConfirmDialogBean.class);
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle(dialogBean1.getParam().getTitle())
                            .setMessage(dialogBean1.getParam().getText())
                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    //执行确定操作
                                    mMaterialDialog.dismiss();
                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mMaterialDialog.dismiss();
                        }}).setCanceledOnTouchOutside(true).show();
                }
                break;

            case 103://弹出带输入框的确认框
                PromptDialogBean promptDialogBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),PromptDialogBean.class);
                BaseWebViewDialog baseWebViewDialog=new BaseWebViewDialog(mContext,mActivity,promptDialogBean.getParam().getTitle(),promptDialogBean.getParam().getText());
                baseWebViewDialog.mInitShow();
                baseWebViewDialog.setCancelable(false);
                break;

            case 104://跳转页面
                SkipBean skipBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SkipBean.class);
                ToastFactory.getLongToast(mContext,"页面跳转:"+skipBean.getParam().getUrl());
                break;
            case 105://返回
                Skip.mNext(mActivity,HomeActivity.class);
                break;
            case 106://右上角接口
                ToRightBean rightBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),ToRightBean.class);
                tvBaseRightTitle.setText(rightBean.getParam().getTitle());
                url=rightBean.getParam().getUrl();
                break;

            case 107://修改顶部标题
                TopTitleBean titleBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),TopTitleBean.class);
                tvBaseTitle.setText(titleBean.getParam().getTitle());
                break;

            case 108://打电话
                break;

            case 109://发送短信
                SendMsgBean msgBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SendMsgBean.class);
                bundle.putString("VipId",msgBean.getParam().getVip_id());
                bundle.putString("Tel",msgBean.getParam().getMobile());
                bundle.putString("VipName",msgBean.getParam().getName());
                Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
                break;

            case 110://发送微信
                SendWeChatBean weChatBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SendWeChatBean.class);
                //启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(mContext, weChatBean.getParam().getVip_id(),weChatBean.getParam().getName());
                break;

            case 111://图片上传
                //打开选择,本次允许选择的数量

                break;

            case 112://图片预览
                PreviewPictureBean pictureBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),PreviewPictureBean.class);

                break;

            case 113://打开文件
                OpenFileBean fileBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),OpenFileBean.class);
                break;

            case 114://通知App数据改变
                NotificationAppUpdateDataBean updateDataBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),NotificationAppUpdateDataBean.class);
                wv_performance_distribution.reload();
                break;
        }

    }


        /**
         * 定义JS交互接口
         * @author fgb
         *
         */
    class JavascriptInterface {
        Context mContext;

        JavascriptInterface(Context c) {
            mContext = c;
        }

        /**
         * 返回UserToken
         * @return UserToken
         */
        @android.webkit.JavascriptInterface
        public String getUserInfo() {
            return prefs.getString("token");
        }

        /**
         * WebView posMessag
         * @param postData
         */
        @android.webkit.JavascriptInterface
        public void postMessage(String postData) {
            Log.i(Constant.TAG,"postData"+postData);
            BaseTestBean bean= JsonUtils.fromJson(postData,BaseTestBean.class);
            switch (bean.getType()){

                case 901://标签筛选
                    WebViewRequestJsonBean labelScreening=new WebViewRequestJsonBean();
                    labelScreening.setWebviewParamJson(postData);
                    labelScreening.setRequestType(901);
                    EventBus.getDefault().post(labelScreening);
                    break;

                case 701://选择店员
                    WebViewRequestJsonBean selClerk=new WebViewRequestJsonBean();
                    selClerk.setWebviewParamJson(postData);
                    selClerk.setRequestType(701);
                    EventBus.getDefault().post(selClerk);
                    break;
                case 401://选择会员
                    WebViewRequestJsonBean selMember=new WebViewRequestJsonBean();
                    selMember.setWebviewParamJson(postData);
                    selMember.setRequestType(401);
                    EventBus.getDefault().post(selMember);

                    break;

                case 101://弹出提示框
                    WebViewRequestJsonBean alertBean=new WebViewRequestJsonBean();
                    alertBean.setWebviewParamJson(postData);
                    alertBean.setRequestType(101);
                    EventBus.getDefault().post(alertBean);
                    break;

                case 102://弹出确认框
                    WebViewRequestJsonBean confirmBean=new WebViewRequestJsonBean();
                    confirmBean.setWebviewParamJson(postData);
                    confirmBean.setRequestType(102);
                    EventBus.getDefault().post(confirmBean);
                    break;

                case 103://弹出带输入框的确认框
                    WebViewRequestJsonBean promptBean=new WebViewRequestJsonBean();
                    promptBean.setWebviewParamJson(postData);
                    promptBean.setRequestType(103);
                    EventBus.getDefault().post(promptBean);
                    break;

                case 104://跳转页面
                    WebViewRequestJsonBean skipBean=new WebViewRequestJsonBean();
                    skipBean.setWebviewParamJson(postData);
                    skipBean.setRequestType(104);
                    EventBus.getDefault().post(skipBean);
                    break;

                case 105://返回
                    WebViewRequestJsonBean backBean=new WebViewRequestJsonBean();
                    backBean.setWebviewParamJson(postData);
                    backBean.setRequestType(105);
                    EventBus.getDefault().post(backBean);
                    break;

                case 106://右上角按钮
                    WebViewRequestJsonBean viewRequestJsonBean=new WebViewRequestJsonBean();
                    viewRequestJsonBean.setWebviewParamJson(postData);
                    viewRequestJsonBean.setRequestType(106);
                    EventBus.getDefault().post(viewRequestJsonBean);
                    break;

                case 107://修改顶部标题
                    WebViewRequestJsonBean topTitleBean=new WebViewRequestJsonBean();
                    topTitleBean.setWebviewParamJson(postData);
                    topTitleBean.setRequestType(107);
                    EventBus.getDefault().post(topTitleBean);
                    break;

                case 108://打电话
                    WebViewRequestJsonBean telBean=new WebViewRequestJsonBean();
                    telBean.setWebviewParamJson(postData);
                    telBean.setRequestType(108);
                    EventBus.getDefault().post(telBean);
                    break;

                case 109://发短信
                    WebViewRequestJsonBean msgBean=new WebViewRequestJsonBean();
                    msgBean.setWebviewParamJson(postData);
                    msgBean.setRequestType(109);
                    EventBus.getDefault().post(msgBean);
                    break;

                case 110://发微信
                    WebViewRequestJsonBean weChartBean=new WebViewRequestJsonBean();
                    weChartBean.setWebviewParamJson(postData);
                    weChartBean.setRequestType(110);
                    EventBus.getDefault().post(weChartBean);
                    break;

                case 111://图片上传
                    WebViewRequestJsonBean imageUploadBean=new WebViewRequestJsonBean();
                    imageUploadBean.setWebviewParamJson(postData);
                    imageUploadBean.setRequestType(111);
                    EventBus.getDefault().post(imageUploadBean);
                    break;

                case 112://图片预览
                    WebViewRequestJsonBean previewBean=new WebViewRequestJsonBean();
                    previewBean.setWebviewParamJson(postData);
                    previewBean.setRequestType(112);
                    EventBus.getDefault().post(previewBean);
                    break;

                case 113://打开文件
                    WebViewRequestJsonBean fileBean=new WebViewRequestJsonBean();
                    fileBean.setWebviewParamJson(postData);
                    fileBean.setRequestType(113);
                    EventBus.getDefault().post(fileBean);
                    break;

                case 114://通知App数据改变
                    WebViewRequestJsonBean appDataUpdateBean=new WebViewRequestJsonBean();
                    appDataUpdateBean.setWebviewParamJson(postData);
                    appDataUpdateBean.setRequestType(114);
                    EventBus.getDefault().post(appDataUpdateBean);
                    break;

                case 38://成功返回
                    Skip.mNext(mActivity,HomeActivity.class);
                    break;
                case 29://111图片上传
                    //打开选择,本次允许选择的数量

                    break;
            }

        }

        /**
         * 返回用户信息
         * @return
         */
        @android.webkit.JavascriptInterface
        public String appUserInfo(){
            return JsonUtils.toJson(BaseUserInfo.getUserInfo(prefs));
        }

    }


    /**
     * 添加备注dialog
     *
     * @author FGB
     *
     */
    public class BaseWebViewDialog extends Dialog implements View.OnClickListener {

        private Activity activity;
        private Button btn_confirm_add_return_visit,btn_cancel;
        private EditText et_tag,et_sprint,et_card;
        private TextView tv_dialog_title,tv_text_title;
        private String dialogTitle;
        private String dialogTextTitle;

        private List<String> etValueList;

        public BaseWebViewDialog(Context context, Activity activity,String dialogTitle,String dialogTextTitle) {
            this(context, R.style.dialog);
            this.activity=activity;
            this.dialogTitle=dialogTitle;
            this.dialogTextTitle=dialogTextTitle;
            initView();
        }

        public BaseWebViewDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.add_performance_dialog);

            initView();
        }

        public void initView(){
            btn_confirm_add_return_visit=(Button) findViewById(R.id.btn_confirm_add_return_visit);
            btn_cancel=(Button)findViewById(R.id.btn_cancel);
            et_tag=(EditText) findViewById(R.id.et_tag);
            et_sprint=(EditText) findViewById(R.id.et_sprint);
            et_card=(EditText)findViewById(R.id.et_card);
            tv_dialog_title= (TextView) findViewById(R.id.tv_dialog_title);
            tv_text_title=(TextView) findViewById(R.id.tv_text_title);
            tv_text_title.setText(dialogTextTitle);
            tv_dialog_title.setText(dialogTitle);
            btn_confirm_add_return_visit.setOnClickListener(this);
            btn_cancel.setOnClickListener(this);
        }

        public void mInitShow() {
            show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_confirm_add_return_visit://确定添加

                    etValueList=new ArrayList<>();
                    etValueList.add(et_tag.getText().toString());
                    etValueList.add(et_sprint.getText().toString());
                    etValueList.add(et_card.getText().toString());

                    JSONArray jsonArray=new JSONArray();
                    for (int i=0;i<etValueList.size();i++){
                        PromptValueBean promptValueBean=new PromptValueBean();
                        promptValueBean.setValue(etValueList.get(i));
                        jsonArray.put(promptValueBean.getPromptValue());
                    }

//                    JSONArray jsonArray=new JSONArray();
//                    for (int i=0;i<etValueList.size();i++){
//                        jsonArray.put(etValueList.get(i));
//                    }

                    PromptInfoBean promptInfoBean=new PromptInfoBean();
                    promptInfoBean.setOk(1);
                    promptInfoBean.setItems(jsonArray);

                    final ResultPromptInfoBean resultPromptInfoBean=new ResultPromptInfoBean();
                    resultPromptInfoBean.setType(103);
                    resultPromptInfoBean.setParam(promptInfoBean.getPromptInfo());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 调用js中的方法实现
                             */
                            wv_performance_distribution.loadUrl("javascript:appCallback('"+resultPromptInfoBean.getPromptInfoResult()+"')");
                        }
                    });

                    dismiss();
                    break;

                case R.id.btn_cancel:
                    dismiss();
                    break;

            }
        }

    }

    private Bitmap getBitmapByView(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        // 获取scrollview实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
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
                                  final com.alibaba.sdk.android.oss.ServiceException serviceException) {
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
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastFactory.getLongToast(mContext,"本地异常如网络异常！");
                        }
                    });
                }
                if (serviceException != null) {
                    // 服务异常
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastFactory.getLongToast(mContext,"ErrorCode:"+serviceException.getErrorCode());
                        }
                    });
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
                        showShare(tvBaseTitle.getText().toString(),ossImageUrl,ossImageUrl);
                    }
                });
            }
        });
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

}
