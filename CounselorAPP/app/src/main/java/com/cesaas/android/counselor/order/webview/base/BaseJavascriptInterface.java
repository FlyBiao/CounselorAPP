package com.cesaas.android.counselor.order.webview.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.base.BaseTestBean;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryReportActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.QueryShopActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.SelectShopActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.imagepicker.imageloader.GlideImageLoader;
import com.cesaas.android.counselor.order.imagepicker.imagepickeradapter.ImagePickerAdapter;
import com.cesaas.android.counselor.order.member.MemberPortraitActivity;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.member.VipListActivity;
import com.cesaas.android.counselor.order.salestalk.activity.SelectVerbalTrickActivity;
import com.cesaas.android.counselor.order.shopmange.InvitationOrderListActivity;
import com.cesaas.android.counselor.order.shopmange.ShopMatchListActivity;
import com.cesaas.android.counselor.order.shopmange.ShopSelectActivity;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.shopmange.ChooseClerkActivity;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;
import com.cesaas.android.counselor.order.webview.bean.AlertDialogBean;
import com.cesaas.android.counselor.order.webview.bean.CallPhoneBean;
import com.cesaas.android.counselor.order.webview.bean.ConfirmDialogBean;
import com.cesaas.android.counselor.order.webview.bean.DataChangeListenerBean;
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
import com.cesaas.android.counselor.order.webview.bean.shop.QueryGoodsEventBean;
import com.cesaas.android.counselor.order.webview.bean.shop.QueryShopEventBean;
import com.cesaas.android.counselor.order.webview.dialog.BaseWebViewInputDialog;
import com.cesaas.android.counselor.order.webview.resultbean.DataChangeTypesBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultDataChangeBean;
import com.cesaas.android.counselor.order.webview.utils.PhoneListenUtils;
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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description 定义JS交互接口
 * Created at 2017/5/21 9:10
 * Version 1.0
 */

public class BaseJavascriptInterface {

    private Context mContext;
    private static AbPrefsUtil prefs;
    private Activity mActivity;
    private MaterialDialog mMaterialDialog;
    private Bundle bundle;

    private WebView mWebView;
    private TextView mTitle;
    private TextView mRightTitle;

    private String title;
    private static String url;
    private String VipId;
    private String VipName;
    private int backType;
    private int type;
    private int queryType;
    private int shopType;
    private int ii;

    private int maxImgCount = 12;//允许选择图片最大数
    private static ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private static ImagePickerAdapter adapter;

    private ArrayList<String> picList;//预览图片集合

    public BaseJavascriptInterface(Context c, AbPrefsUtil prefs) {
        mContext = c;
        this.prefs=prefs;
    }

    public BaseJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backType,int queryType,int i){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        this.prefs=prefs;
        this.mTitle=mTitle;
        this.mRightTitle=mRightTitle;
        this.backType=backType;
        this.queryType=queryType;
        this.ii=i;
    }

    public BaseJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backType){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        this.prefs=prefs;
        this.mTitle=mTitle;
        this.mRightTitle=mRightTitle;
        this.backType=backType;
    }

    public BaseJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backType,int shopType,int shopTypes,int shopType1,int shopType2){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        this.prefs=prefs;
        this.mTitle=mTitle;
        this.mRightTitle=mRightTitle;
        this.backType=backType;
        this.shopType=shopType;
    }

    public BaseJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backType,int type){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        this.prefs=prefs;
        this.mTitle=mTitle;
        this.mRightTitle=mRightTitle;
        this.backType=backType;
        this.type=type;
    }

    public BaseJavascriptInterface(Context mContext, Activity mActivity, MaterialDialog mMaterialDialog, WebView mWebView, Bundle bundle, AbPrefsUtil prefs, TextView mTitle, TextView mRightTitle,int backType,int type,int queryType,int i){
        this.mContext=mContext;
        this.mActivity=mActivity;
        this.mMaterialDialog=mMaterialDialog;
        this.mWebView=mWebView;
        this.bundle=bundle;
        this.prefs=prefs;
        this.mTitle=mTitle;
        this.mRightTitle=mRightTitle;
        this.backType=backType;
        this.type=type;
        this.queryType=queryType;
        this.ii=i;
    }

    public static String getUrl(){
        return url;
    }

    /**
     * 返回UserToken
     * @return UserToken
     */
    @android.webkit.JavascriptInterface
    public String getUserInfo() {
        if(type==101){
            StringBuffer sb=new StringBuffer();
            sb.append(AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN)).append("|").append(AbPrefsUtil.getInstance().getString(Constant.SPF_AUTHKEY));
            return sb.toString();
        }else{
            return AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN);
        }
    }

    /**
     * WebView posMessag
     * @param postData
     */
    @android.webkit.JavascriptInterface
    public void postMessage(final String postData) {
        Log.i("test","posMessag=postData"+postData);
        BaseTestBean bean= JsonUtils.fromJson(postData,BaseTestBean.class);

        switch (bean.getType()){
            case 901://标签筛选
                WebViewRequestJsonBean labelScreening=new WebViewRequestJsonBean();
                labelScreening.setWebviewParamJson(postData);
                labelScreening.setRequestType(901);
//                LabelScreenBean labelScreenBean=JsonUtils.fromJson(labelScreening.getWebviewParamJson(),LabelScreenBean.class);
                Intent intent1= new Intent(mContext, ShopTagListActivity.class);
                intent1.putExtra("Id",prefs.getString("ShopId"));
                mActivity.startActivityForResult(intent1, Constant.H5_TAG_SELECT);
                break;

            case 701://选择店员
                WebViewRequestJsonBean selClerk=new WebViewRequestJsonBean();
                selClerk.setWebviewParamJson(postData);
                selClerk.setRequestType(701);

                SelectClerkBean clerkBean=JsonUtils.fromJson(selClerk.getWebviewParamJson(),SelectClerkBean.class);
                Intent intent= new Intent(mContext, ChooseClerkActivity.class);
                intent.putExtra("RoleIds",clerkBean.getParam().getPosition_id()+"");
                intent.putExtra("Multi",clerkBean.getParam().getMulti());
                Skip.mSelActivityResult(mActivity,Constant.H5_CLERK_SELECT,intent);
                break;
            case 401://选择会员
                WebViewRequestJsonBean selMember=new WebViewRequestJsonBean();
                selMember.setWebviewParamJson(postData);
                selMember.setRequestType(401);

                SelectMemberBean memberBean=JsonUtils.fromJson(selMember.getWebviewParamJson(),SelectMemberBean.class);
                Intent intent2= new Intent(mContext, VipListActivity.class);
                intent2.putExtra("Multi",memberBean.getParam().getMutil());
                mActivity.startActivityForResult(intent2, Constant.H5_MEMBER_SELECT);
                break;
            case 402://选择话术
                Skip.mActivityResult(mActivity,SelectVerbalTrickActivity.class,Constant.H5_VERBALTRICK_SELECT);
                break;

            case 301://商品选择
                WebViewRequestJsonBean selShop=new WebViewRequestJsonBean();
                selShop.setWebviewParamJson(postData);
                selShop.setRequestType(301);
                SelectProductBean selectProductBean=JsonUtils.fromJson(selShop.getWebviewParamJson(),SelectProductBean.class);
                Skip.mActivityResult(mActivity,ShopSelectActivity.class,Constant.H5_PRODUCT_SELECT);

                break;

            case 302://选择搭配
                WebViewRequestJsonBean seldapei=new WebViewRequestJsonBean();
                seldapei.setWebviewParamJson(postData);
                seldapei.setRequestType(302);

                SelectProductBean selectProduct=JsonUtils.fromJson(seldapei.getWebviewParamJson(),SelectProductBean.class);
                Skip.mActivityResult(mActivity,ShopMatchListActivity.class,Constant.H5_COLLOCATE_SELECT);
                break;
            case 303://分享商品
                ShareProductBean productBean=JsonUtils.fromJson(postData,ShareProductBean.class);
                showShare(productBean.getParam().getTitle(),productBean.getParam().getImage_url(),productBean.getParam().getShare_url());
                break;
            case 304://商品筛选条件
                if(queryType!=0){
                    QueryGoodsEventBean goods=new QueryGoodsEventBean();
                    goods.setQueryType(queryType);
                    goods.setType(bean.getType());
                    EventBus.getDefault().post(goods);
                }else{
                    Intent shopQuery= new Intent(mContext, QueryReportActivity.class);
                    shopQuery.putExtra("type",bean.getType());
                    mActivity.startActivityForResult(shopQuery,304);
                }
                break;

            case 201:
                if(queryType!=0){
                    QueryShopEventBean type=new QueryShopEventBean();
                    type.setQueryType(queryType);
                    EventBus.getDefault().post(type);
                }else{
                    Intent intentShop= new Intent(mContext, QueryShopActivity.class);
                    intentShop.putExtra("LeftTitle","店铺业绩");
                    intentShop.putExtra("shopType",shopType);
                    mActivity.startActivityForResult(intentShop,201);
                }
                break;

            case 101://弹出提示框
                WebViewRequestJsonBean alertBean=new WebViewRequestJsonBean();
                alertBean.setWebviewParamJson(postData);
                alertBean.setRequestType(101);
                AlertDialogBean dialogBean=JsonUtils.fromJson(alertBean.getWebviewParamJson(),AlertDialogBean.class);
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("温馨提示！")
                            .setMessage(dialogBean.getParam().getMessage())
                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    //执行确定操作
                                    mMaterialDialog.dismiss();
                                }
                            }).setNegativeButton("返回", new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mMaterialDialog.dismiss();
//
                        }}).setCanceledOnTouchOutside(true).show();
                }
                break;

            case 102://弹出确认框
                WebViewRequestJsonBean confirmBean=new WebViewRequestJsonBean();
                confirmBean.setWebviewParamJson(postData);
                confirmBean.setRequestType(102);
                ConfirmDialogBean dialogBean1=JsonUtils.fromJson(confirmBean.getWebviewParamJson(),ConfirmDialogBean.class);
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
                WebViewRequestJsonBean promptBean=new WebViewRequestJsonBean();
                promptBean.setWebviewParamJson(postData);
                promptBean.setRequestType(103);

                PromptDialogBean promptDialogBean=JsonUtils.fromJson(promptBean.getWebviewParamJson(),PromptDialogBean.class);
                BaseWebViewInputDialog baseWebViewDialog=new BaseWebViewInputDialog(mContext,mActivity,mWebView,promptDialogBean.getParam().getTitle());
                baseWebViewDialog.mInitShow();
                baseWebViewDialog.setCancelable(false);
                break;

            case 104://跳转页面
                WebViewRequestJsonBean skipBean=new WebViewRequestJsonBean();
                skipBean.setWebviewParamJson(postData);
                skipBean.setRequestType(104);
                SkipBean skipBeans=JsonUtils.fromJson(skipBean.getWebviewParamJson(),SkipBean.class);
                bundle.putString("MemberUrl",skipBeans.getParam().getUrl());
                bundle.putInt("type",type);
                Skip.mNextFroData(mActivity,MemberPortraitActivity.class,bundle);
                break;

            case 105://返回
                WebViewRequestJsonBean backBean=new WebViewRequestJsonBean();
                backBean.setWebviewParamJson(postData);
                backBean.setRequestType(105);
                switch (backType){
                    case 1://首页
                        Skip.mNext(mActivity,HomeActivity.class);
                        mActivity.finish();
                        break;
                    case 2:////邀单计划列表页面
                        mMaterialDialog.dismiss();
                        Skip.mNext(mActivity,InvitationOrderListActivity.class);
                        mActivity.finish();
                        break;
                }

            case 106://右上角按钮
                WebViewRequestJsonBean viewRequestJsonBean=new WebViewRequestJsonBean();
                viewRequestJsonBean.setWebviewParamJson(postData);
                viewRequestJsonBean.setRequestType(106);
//
                ToRightBean rightBean=JsonUtils.fromJson(viewRequestJsonBean.getWebviewParamJson(),ToRightBean.class);
                url=rightBean.getParam().getUrl();
                title=rightBean.getParam().getTitle();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRightTitle.setText(title);
                        mRightTitle.setVisibility(View.VISIBLE);
                    }
                });
                break;

            case 107://修改顶部标题
                WebViewRequestJsonBean topTitleBean=new WebViewRequestJsonBean();
                topTitleBean.setWebviewParamJson(postData);
                topTitleBean.setRequestType(107);

                final TopTitleBean titleBean=JsonUtils.fromJson(topTitleBean.getWebviewParamJson(),TopTitleBean.class);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTitle.setText(titleBean.getParam().getTitle());
                    }
                });
                break;

            case 108://打电话
                WebViewRequestJsonBean telBean=new WebViewRequestJsonBean();
                telBean.setWebviewParamJson(postData);
                telBean.setRequestType(108);

                CallPhoneBean phoneBean=JsonUtils.fromJson(telBean.getWebviewParamJson(),CallPhoneBean.class);
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

            case 109://发短信
                WebViewRequestJsonBean msgBean=new WebViewRequestJsonBean();
                msgBean.setWebviewParamJson(postData);
                msgBean.setRequestType(109);

                SendMsgBean msgBeans=JsonUtils.fromJson(msgBean.getWebviewParamJson(),SendMsgBean.class);
                bundle.putString("VipId",msgBeans.getParam().getVip_id());
                bundle.putString("Tel",msgBeans.getParam().getMobile());
                bundle.putString("VipName",msgBeans.getParam().getName());
                Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
                break;

            case 110://发微信
                WebViewRequestJsonBean weChartBean=new WebViewRequestJsonBean();
                weChartBean.setWebviewParamJson(postData);
                weChartBean.setRequestType(110);

                SendWeChatBean weChatBean=JsonUtils.fromJson(weChartBean.getWebviewParamJson(),SendWeChatBean.class);
                //启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(mContext, weChatBean.getParam().getVip_id(),weChatBean.getParam().getName());
                break;

            case 111://图片上传
                WebViewRequestJsonBean imageUploadBean=new WebViewRequestJsonBean();
                imageUploadBean.setWebviewParamJson(postData);
                imageUploadBean.setRequestType(111);

                initImagePicker();
                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intents = new Intent(mContext, ImageGridActivity.class);
                mActivity.startActivityForResult(intents, Constant.H5_PICTURE_UPLOAD);
                break;

            case 112://图片预览
                WebViewRequestJsonBean previewBean=new WebViewRequestJsonBean();
                previewBean.setWebviewParamJson(postData);
                previewBean.setRequestType(112);

                PreviewPictureBean pictureBean=JsonUtils.fromJson(previewBean.getWebviewParamJson(),PreviewPictureBean.class);
                picList = new ArrayList<>();
                for (int i=0;i<pictureBean.getParam().getImage_url().size();i++){
                    picList.add(pictureBean.getParam().getImage_url().get(i));
                }
                final String content ="图片预览";
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final ImagPagerUtil imagPagerUtil = new ImagPagerUtil(mActivity, picList);
                        imagPagerUtil.setContentText(content);
                        initImageLoader();
                        imagPagerUtil.show();
                    }
                });
                break;

            case 113://打开文件
                WebViewRequestJsonBean fileBean=new WebViewRequestJsonBean();
                fileBean.setWebviewParamJson(postData);
                fileBean.setRequestType(113);
                break;

            case 114://通知App数据改变
                WebViewRequestJsonBean appDataUpdateBean=new WebViewRequestJsonBean();
                appDataUpdateBean.setWebviewParamJson(postData);
                appDataUpdateBean.setRequestType(114);
                break;

            case 115://添加通知监听
                WebViewRequestJsonBean dataChangeListener=new WebViewRequestJsonBean();
                dataChangeListener.setWebviewParamJson(postData);
                dataChangeListener.setRequestType(115);

                final DataChangeListenerBean listenerBean=JsonUtils.fromJson(dataChangeListener.getWebviewParamJson(),DataChangeListenerBean.class);
                JSONArray arr=new JSONArray();
                for (int i=0;i<listenerBean.getParam().getTypes().size();i++){
                    arr.put(listenerBean.getParam().getTypes().get(i));
                }
                final ResultDataChangeBean dataChangeBean=new ResultDataChangeBean();
                dataChangeBean.setType(dataChangeListener.getRequestType());
                DataChangeTypesBean changeTypesBean=new DataChangeTypesBean();
                changeTypesBean.setArr(arr);
                dataChangeBean.setParam(changeTypesBean.getDataChangeInfo());
                //调用js中的appCallback方法
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript:appCallback('"+dataChangeBean.getDataChangeResult()+"')");
                    }
                });
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
                mContext).threadPriority(Thread.NORM_PRIORITY - 2)
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
