package com.cesaas.android.counselor.order.webview.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.imagepicker.imageloader.GlideImageLoader;
import com.cesaas.android.counselor.order.imagepicker.imagepickeradapter.ImagePickerAdapter;
import com.cesaas.android.counselor.order.member.MemberPortraitActivity;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.member.VipListActivity;
import com.cesaas.android.counselor.order.salestalk.activity.SelectVerbalTrickActivity;
import com.cesaas.android.counselor.order.shopmange.ShopMatchListActivity;
import com.cesaas.android.counselor.order.shopmange.ShopSelectActivity;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.shopmange.ChooseClerkActivity;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.webview.bean.AlertDialogBean;
import com.cesaas.android.counselor.order.webview.bean.CallPhoneBean;
import com.cesaas.android.counselor.order.webview.bean.ConfirmDialogBean;
import com.cesaas.android.counselor.order.webview.bean.NotificationAppUpdateDataBean;
import com.cesaas.android.counselor.order.webview.bean.OpenFileBean;
import com.cesaas.android.counselor.order.webview.bean.PreviewPictureBean;
import com.cesaas.android.counselor.order.webview.bean.PromptDialogBean;
import com.cesaas.android.counselor.order.webview.bean.SelectClerkBean;
import com.cesaas.android.counselor.order.webview.bean.SelectMemberBean;
import com.cesaas.android.counselor.order.webview.bean.SelectProductBean;
import com.cesaas.android.counselor.order.webview.bean.SendMsgBean;
import com.cesaas.android.counselor.order.webview.bean.SendWeChatBean;
import com.cesaas.android.counselor.order.webview.bean.ShareProductBean;
import com.cesaas.android.counselor.order.webview.bean.SkipBean;
import com.cesaas.android.counselor.order.webview.bean.ToRightBean;
import com.cesaas.android.counselor.order.webview.bean.TopTitleBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptValueBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultPromptInfoBean;
import com.cesaas.android.counselor.order.webview.utils.PhoneListenUtils;
import com.cesaas.android.counselor.order.webview.utils.ShareUtils;
import com.cesaas.android.counselor.order.zoomimageview.ImagPagerUtil;
import com.flybiao.materialdialog.MaterialDialog;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;

import java.util.ArrayList;

import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description
 * Created at 2017/5/20 19:07
 * Version 1.0
 */

public class WebViewEventBus {

    private  Context mContext;
    private  Activity mActivity;
    private MaterialDialog mMaterialDialog;
    private Bundle bundle;

    private  WebView mWebView;
    private TextView mTitle;
    private TextView mRightTitle;

    private String VipId;
    private String VipName;
    private static String url;


    private int maxImgCount = 12;//允许选择图片最大数
    private static ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private static ImagePickerAdapter adapter;

    private ArrayList<String> picList;//预览图片集合
    public static AbPrefsUtil prefs;


    public WebViewEventBus( Context mContext, Activity mActivity,MaterialDialog mMaterialDialog,WebView mWebView,Bundle bundle,TextView mTitle,TextView mRightTitle){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        this.mTitle=mTitle;
        this.mRightTitle=mRightTitle;
        prefs = AbPrefsUtil.getInstance();
    }

    public WebViewEventBus( Context mContext, Activity mActivity,MaterialDialog mMaterialDialog,WebView mWebView,Bundle bundle){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        prefs = AbPrefsUtil.getInstance();
    }

    public static String getUrl(){
        return url;
    }

    public void getSwitch(int type, String getWebviewParamJson){
        switch (type){
            case 901://标签筛选
//                LabelScreenBean labelScreenBean=JsonUtils.fromJson(getWebviewParamJson,LabelScreenBean.class);
                Intent intent1= new Intent(mContext, ShopTagListActivity.class);
                intent1.putExtra("Id",prefs.getString("ShopId"));
                mActivity.startActivityForResult(intent1, Constant.H5_TAG_SELECT);
                break;

            case 701://选择店员
                SelectClerkBean clerkBean=JsonUtils.fromJson(getWebviewParamJson,SelectClerkBean.class);
                Intent intent= new Intent(mContext, ChooseClerkActivity.class);
                intent.putExtra("RoleIds",clerkBean.getParam().getPosition_id()+"");
                intent.putExtra("Multi",clerkBean.getParam().getMulti());
                Skip.mSelActivityResult(mActivity,Constant.H5_CLERK_SELECT,intent);
                break;
            case 401://选择会员
                SelectMemberBean memberBean=JsonUtils.fromJson(getWebviewParamJson,SelectMemberBean.class);
                Intent intent2= new Intent(mContext, VipListActivity.class);
                intent2.putExtra("Multi",memberBean.getParam().getMutil());
                mActivity.startActivityForResult(intent2, Constant.H5_MEMBER_SELECT);
                break;
            case 402://选择话术
                Skip.mActivityResult(mActivity,SelectVerbalTrickActivity.class,Constant.H5_VERBALTRICK_SELECT);
            case 301://选择商品
                SelectProductBean selectProductBean=JsonUtils.fromJson(getWebviewParamJson,SelectProductBean.class);
                Skip.mActivityResult(mActivity,ShopSelectActivity.class,Constant.H5_PRODUCT_SELECT);
                break;
            case 302://选择搭配
                SelectProductBean selectProduct=JsonUtils.fromJson(getWebviewParamJson,SelectProductBean.class);
                Skip.mActivityResult(mActivity,ShopMatchListActivity.class,Constant.H5_COLLOCATE_SELECT);
                break;

            case 303://商品分享
                ShareProductBean shareProductBean=JsonUtils.fromJson(getWebviewParamJson,ShareProductBean.class);
                ShareUtils.showShare(shareProductBean.getParam().getTitle(),shareProductBean.getParam().getImage_url(),shareProductBean.getParam().getShare_url(),mContext);
                break;
            case 101://弹出对话框
                AlertDialogBean dialogBean=JsonUtils.fromJson(getWebviewParamJson,AlertDialogBean.class);
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
                ConfirmDialogBean dialogBean1=JsonUtils.fromJson(getWebviewParamJson,ConfirmDialogBean.class);
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
                PromptDialogBean promptDialogBean=JsonUtils.fromJson(getWebviewParamJson,PromptDialogBean.class);
                BaseWebViewDialog baseWebViewDialog=new BaseWebViewDialog(mContext,mActivity,promptDialogBean.getParam().getTitle());
                baseWebViewDialog.mInitShow();
                baseWebViewDialog.setCancelable(false);
                break;

            case 104://跳转页面
                SkipBean skipBean=JsonUtils.fromJson(getWebviewParamJson,SkipBean.class);
                bundle.putString("MemberUrl",skipBean.getParam().getUrl());
                Skip.mNextFroData(mActivity,MemberPortraitActivity.class,bundle);
                break;
            case 105://返回
                Skip.mNext(mActivity,HomeActivity.class);
                break;
            case 106://右上角接口
                ToRightBean rightBean=JsonUtils.fromJson(getWebviewParamJson,ToRightBean.class);
                mRightTitle.setText(rightBean.getParam().getTitle());
                mRightTitle.setVisibility(View.VISIBLE);
                url=rightBean.getParam().getUrl();
                break;

            case 107://修改顶部标题
                TopTitleBean titleBean=JsonUtils.fromJson(getWebviewParamJson,TopTitleBean.class);
                mTitle.setText(titleBean.getParam().getTitle());
                break;

            case 108://打电话
                CallPhoneBean phoneBean=JsonUtils.fromJson(getWebviewParamJson,CallPhoneBean.class);
                CallUtils.call(phoneBean.getParam().getMobile(),mActivity);
                VipId=phoneBean.getParam().getVip_id();
                VipName=phoneBean.getParam().getName();
//
//                //调用通话监听
                TelephonyManager phoneManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
                PhoneListenUtils phoneListenUtils=new PhoneListenUtils(mContext,VipId,VipName);
                phoneManager.listen(phoneListenUtils,
                        PhoneStateListener.LISTEN_CALL_STATE);
                break;

            case 109://发送短信
                SendMsgBean msgBean=JsonUtils.fromJson(getWebviewParamJson,SendMsgBean.class);
                bundle.putString("VipId",msgBean.getParam().getVip_id());
                bundle.putString("Tel",msgBean.getParam().getMobile());
                bundle.putString("VipName",msgBean.getParam().getName());
                Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
                break;

            case 110://发送微信
                SendWeChatBean weChatBean=JsonUtils.fromJson(getWebviewParamJson,SendWeChatBean.class);
                //启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(mContext, weChatBean.getParam().getVip_id(),weChatBean.getParam().getName());
                break;

            case 111://图片上传
                initImagePicker();
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intents = new Intent(mContext, ImageGridActivity.class);
                mActivity.startActivityForResult(intents, Constant.H5_PICTURE_UPLOAD);
                break;

            case 112://图片预览
                PreviewPictureBean pictureBean=JsonUtils.fromJson(getWebviewParamJson,PreviewPictureBean.class);
                picList = new ArrayList<>();
                for (int i=0;i<pictureBean.getParam().getImage_url().size();i++){
                    picList.add(pictureBean.getParam().getImage_url().get(i));
                }
                final String content ="图片预览";

                initImageLoader();
                ImagPagerUtil imagPagerUtil = new ImagPagerUtil(mActivity, picList);
                imagPagerUtil.setContentText(content);
                imagPagerUtil.show();
                break;

            case 113://打开文件
                OpenFileBean fileBean=JsonUtils.fromJson(getWebviewParamJson,OpenFileBean.class);
                break;

            case 114://通知App数据改变
                NotificationAppUpdateDataBean updateDataBean=JsonUtils.fromJson(getWebviewParamJson,NotificationAppUpdateDataBean.class);
                break;
        }
    }

    /**
     * 初始化图片选项
     */
    private void initImagePicker() {

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //许裁剪（单选才有效）
        imagePicker.setMultiMode(true);                         //是否允许允多选（默认多选）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1200);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(1200);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(mActivity, selImageList, maxImgCount);
    }

    /**
     * 初始化加载Image
     */
    public void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mActivity.getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    public static void getResult(int requestCode,int resultCode,Intent data,Context mContext,Activity mActivity,WebView mWebView){
        BaseActivityResult result=new BaseActivityResult(mContext,mActivity,mWebView,selImageList,adapter);
        result.getOnActivityResult(requestCode,resultCode,data,prefs);
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
        private EditText et_return_visit_content;
        private TextView tv_dialog_title;
        private String dialogTitle;

        public BaseWebViewDialog(Context context, Activity activity,String dialogTitle) {
            this(context, R.style.dialog);
            this.activity=activity;
            this.dialogTitle=dialogTitle;
            initView();
        }

        public BaseWebViewDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.add_return_visit_dialog);

            initView();
        }

        public void initView(){
            btn_confirm_add_return_visit=(Button) findViewById(R.id.btn_confirm_add_return_visit);
            et_return_visit_content=(EditText) findViewById(R.id.et_return_visit_content);
            tv_dialog_title= (TextView) findViewById(R.id.tv_dialog_title);
            btn_cancel= (Button) findViewById(R.id.btn_cancel);
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

                    PromptValueBean promptValueBean=new PromptValueBean();
                    promptValueBean.setValue(et_return_visit_content.getText().toString());

                    JSONArray jsonArray=new JSONArray();
                    jsonArray.put(promptValueBean.getPromptValue());

                    PromptInfoBean promptInfoBean=new PromptInfoBean();
                    promptInfoBean.setOk(1);
                    promptInfoBean.setItems(jsonArray);

                    final ResultPromptInfoBean resultPromptInfoBean=new ResultPromptInfoBean();
                    resultPromptInfoBean.setType(103);
                    resultPromptInfoBean.setParam(promptInfoBean.getPromptInfo());
                    Log.i(Constant.TAG,"getPromptInfo:"+resultPromptInfoBean.getPromptInfoResult());

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 调用js中的方法实现
                             */
                            mWebView.loadUrl("javascript:appCallback('"+resultPromptInfoBean.getPromptInfoResult()+"')");
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

}
