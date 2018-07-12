package com.cesaas.android.counselor.order.shop.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.base.BaseTestBean;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.label.bean.GetTagListBean;

import com.cesaas.android.counselor.order.member.MemberPortraitActivity;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.member.VipListActivity;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.member.net.SaveVisitRecordNet;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.base.BaseUserInfo;
import com.cesaas.android.counselor.order.webview.bean.AlertDialogBean;
import com.cesaas.android.counselor.order.webview.bean.CallPhoneBean;
import com.cesaas.android.counselor.order.webview.bean.ConfirmDialogBean;
import com.cesaas.android.counselor.order.webview.bean.LabelScreenBean;
import com.cesaas.android.counselor.order.webview.bean.NotificationAppUpdateDataBean;
import com.cesaas.android.counselor.order.webview.bean.OpenFileBean;
import com.cesaas.android.counselor.order.webview.bean.PreviewPictureBean;
import com.cesaas.android.counselor.order.webview.bean.PromptDialogBean;
import com.cesaas.android.counselor.order.webview.bean.SelectMemberBean;
import com.cesaas.android.counselor.order.webview.bean.SendMsgBean;
import com.cesaas.android.counselor.order.webview.bean.SendWeChatBean;
import com.cesaas.android.counselor.order.webview.bean.ShareProductBean;
import com.cesaas.android.counselor.order.webview.bean.SkipBean;
import com.cesaas.android.counselor.order.webview.bean.ToRightBean;
import com.cesaas.android.counselor.order.webview.bean.TopTitleBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptValueBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultPromptInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultSelectMemberBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultTagInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.SelectMemberInfo;
import com.cesaas.android.counselor.order.webview.resultbean.TagInfoBean;
import com.cesaas.android.counselor.order.zoomimageview.ImagPagerUtil;
import com.flybiao.materialdialog.MaterialDialog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description 商品Fragment
 * Created 2017/4/6 18:32
 * Version 1.0
 */
public class ShopFragment extends BaseFragment implements View.OnClickListener {

    private WebView wv_product;
    private JavascriptInterface javascriptInterface;
    private String VipId;
    private String VipName;

    protected WaitDialog mDialog;
    private ArrayList<String> picList;//预览图片集合
    private MaterialDialog mMaterialDialog;

    /**
     * 单例
     */
    public static ShopFragment newInstance() {
        ShopFragment fragmentCommon = new ShopFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void initViews() {
        mMaterialDialog=new MaterialDialog(getContext());
        wv_product=findView(R.id.wv_product);
    }

    @Override
    public void initListener() {}

    /**
     * 初始化加载Image
     */
    public void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    @Override
    public void initData() {
        initImageLoader();
        mDialog = new WaitDialog(getContext());
        WebViewUtils.initWebSettings(wv_product,mDialog, Urls.PRODUCT);

        javascriptInterface = new JavascriptInterface(getContext());
        wv_product.addJavascriptInterface(javascriptInterface, "appHome");

//        WebViewUtils.initSwipeRefreshLayout(swipeRefreshLayout,wv_product);
    }

    @Override
    public void processClick(View v) {}

    @Override
    public void fetchData() {}

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 401:
                if(data!=null){
                    List<SelectVipListBean> selectVipListBeen=(ArrayList<SelectVipListBean>)data.getSerializableExtra("selectList");
                    SelectMemberInfo memberInfo=null;
                    JSONArray jsonArray=new JSONArray();
                    for (int i=0;i<selectVipListBeen.size();i++){
                        memberInfo=new SelectMemberInfo();
                        memberInfo.setId(selectVipListBeen.get(i).getVipId());
                        memberInfo.setName(selectVipListBeen.get(i).getNickName());
                        memberInfo.setLogo(selectVipListBeen.get(i).getImage());
                        memberInfo.setGrade(selectVipListBeen.get(i).getCardName());
                        memberInfo.setMobile(selectVipListBeen.get(i).getMobile());
                        memberInfo.setCounselor_id(selectVipListBeen.get(i).getCounselorId());
                        memberInfo.setBirthday(selectVipListBeen.get(i).getBirthDay());
                        memberInfo.setPoints(selectVipListBeen.get(i).getPoint());
                        jsonArray.put(memberInfo.getMemberInfo());
                    }
                    ResultSelectMemberBean resultSelectMemberBean=new ResultSelectMemberBean();
                    resultSelectMemberBean.setType(401);
                    resultSelectMemberBean.setParam(jsonArray);

                    wv_product.loadUrl("javascript:appCallback('"+resultSelectMemberBean.getSelectMemberResult()+"')");
                }
                break;
            case 901:
                if(data!=null){
                    List<GetTagListBean> selectList=(ArrayList<GetTagListBean>)data.getSerializableExtra("selectList");
                    JSONArray jsonArray=new JSONArray();
                    TagInfoBean tagInfoBean=null;
                    for (int i=0;i<selectList.size();i++){
                        tagInfoBean=new TagInfoBean();
                        tagInfoBean.setCategory_id(selectList.get(i).getCategoryId());
                        tagInfoBean.setCategory_name(selectList.get(i).getCategoryName());//
                        tagInfoBean.setId(selectList.get(i).getTagId());
                        tagInfoBean.setTitle(selectList.get(i).getTagName());
                        jsonArray.put(tagInfoBean.getTagInfo());
                    }
                    ResultTagInfoBean resultTagInfoBean=new ResultTagInfoBean();
                    resultTagInfoBean.setType(901);
                    resultTagInfoBean.setParam(jsonArray);
                    Log.i(Constant.TAG,"标签筛选:"+resultTagInfoBean.getTagInfoResult());
                    //调用js中的appCallback方法
                    wv_product.loadUrl("javascript:appCallback('"+resultTagInfoBean.getTagInfoResult()+"')");
                }
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
                    LabelScreenBean labelScreenBean=JsonUtils.fromJson(postData,LabelScreenBean.class);
                    Intent tagIntent= new Intent(getContext(), ShopTagListActivity.class);
//                    intent.putExtra("Id",prefs.getString("ShopId"));
                    startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
                    break;

                case 401://选择会员
                    SelectMemberBean memberBean=JsonUtils.fromJson(postData,SelectMemberBean.class);
                    Intent intent2= new Intent(getContext(), VipListActivity.class);
                    intent2.putExtra("Multi",memberBean.getParam().getMutil());
                    startActivityForResult(intent2, Constant.H5_MEMBER_SELECT);
                    break;
                case 303://分享商品
                    ShareProductBean productBean=JsonUtils.fromJson(postData,ShareProductBean.class);
//                    shareMsg("分享到",productBean.getParam().getTitle(),productBean.getParam().getDescription(),productBean.getParam().getShare_url());
                    showShare(productBean.getParam().getTitle(),productBean.getParam().getImage_url(),productBean.getParam().getShare_url());
                    break;

                case 101://弹出对话框
                    AlertDialogBean dialogBean=JsonUtils.fromJson(postData,AlertDialogBean.class);
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
                    ConfirmDialogBean dialogBean1=JsonUtils.fromJson(postData,ConfirmDialogBean.class);
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
                    PromptDialogBean promptDialogBean=JsonUtils.fromJson(postData,PromptDialogBean.class);
                    new BaseWebViewDialog(getContext(),getActivity(),promptDialogBean.getParam().getTitle()).mInitShow();
                    break;

                case 104://跳转页面
                    SkipBean skipBean=JsonUtils.fromJson(postData,SkipBean.class);
                    bundle.putString("MemberUrl",skipBean.getParam().getUrl());
                    Skip.mNextFroData(getActivity(),MemberPortraitActivity.class,bundle);
                    break;
                case 105://返回
                    Skip.mNext(getActivity(),HomeActivity.class);
                    break;
                case 106://右上角接口
                    ToRightBean rightBean=JsonUtils.fromJson(postData,ToRightBean.class);

                    break;

                case 107://修改顶部标题
                    TopTitleBean titleBean=JsonUtils.fromJson(postData,TopTitleBean.class);
                    break;

                case 108://打电话
                    CallPhoneBean phoneBean=JsonUtils.fromJson(postData,CallPhoneBean.class);
                    CallUtils.call(phoneBean.getParam().getMobile(),getActivity());
                    VipId=phoneBean.getParam().getVip_id();
                    VipName=phoneBean.getParam().getName();

                    //调用通话监听
                    TelephonyManager phoneManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    phoneManager.listen(new PhoneListenUtils(getContext()),
                            PhoneStateListener.LISTEN_CALL_STATE);
                    break;

                case 109://发送短信
                    SendMsgBean msgBean=JsonUtils.fromJson(postData,SendMsgBean.class);
                    bundle.putString("VipId",msgBean.getParam().getVip_id());
                    bundle.putString("Tel",msgBean.getParam().getMobile());
                    bundle.putString("VipName",msgBean.getParam().getName());
                    Skip.mNextFroData(getActivity(),SendMessageActivity.class,bundle);
                    break;

                case 110://发送微信
                    SendWeChatBean weChatBean=JsonUtils.fromJson(postData,SendWeChatBean.class);
                    //启动单聊会话界面
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().startPrivateChat(getContext(), weChatBean.getParam().getVip_id(),weChatBean.getParam().getName());
                    break;

                case 111://图片上传
                    //打开选择,本次允许选择的数量

                    break;

                case 112://图片预览
                    PreviewPictureBean pictureBean=JsonUtils.fromJson(postData,PreviewPictureBean.class);
                    picList = new ArrayList<>();
                    for (int i=0;i<pictureBean.getParam().getImage_url().size();i++){
                        picList.add(pictureBean.getParam().getImage_url().get(i));
                    }
                    final String content ="图片预览";
                    initImageLoader();
                    ImagPagerUtil imagPagerUtil = new ImagPagerUtil(getActivity(), picList);
                    imagPagerUtil.setContentText(content);
                    imagPagerUtil.show();
                    break;

                case 113://打开文件
                    OpenFileBean fileBean=JsonUtils.fromJson(postData,OpenFileBean.class);
                    break;

                case 114://通知App数据改变
                    NotificationAppUpdateDataBean updateDataBean=JsonUtils.fromJson(postData,NotificationAppUpdateDataBean.class);
                    break;
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
            oks.setSite(getString(R.string.app_name));
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(shreUrl);

            // 启动分享GUI
            oks.show(mContext);
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
        private Button btn_confirm_add_return_visit;
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
            tv_dialog_title.setText(dialogTitle);
            btn_confirm_add_return_visit.setOnClickListener(this);
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

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 调用js中的方法实现
                             */
                            wv_product.loadUrl("javascript:appCallback('"+resultPromptInfoBean.getPromptInfoResult()+"')");
                        }
                    });
                    cancel();
                    break;
            }
        }

    }

    /**
     * Author FGB
     * Description 通话监听Utile
     * Created 2017/4/24 12:02
     * Version 1.zero
     */
    public class PhoneListenUtils extends PhoneStateListener {
        private final Context context;
        //获取本次通话的时间(单位:秒)
        int time = 0;
        //判断是否正在通话
        boolean isCalling;
        //控制循环是否结束
        boolean isFinish;
        private ExecutorService service;
        public PhoneListenUtils(Context context) {
            this.context = context;
            service = Executors.newSingleThreadExecutor();
        }
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    if (isCalling) {
                        isCalling = false;
                        isFinish = true;
                        service.shutdown();
//                        Toast.makeText(context, "本次通话" + time + "秒",
//                                Toast.LENGTH_LONG).show();
                        //调用并保存回访通话时长记录
                        SaveVisitRecordNet recordNet=new SaveVisitRecordNet(getContext());
                        recordNet.setData(Integer.parseInt(VipId),VipName,1,time+"");

                        time = 0;
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    isCalling = true;
                    service.execute(new Runnable() {
                        @Override
                        public void run() {
                            while (!isFinish) {
                                try {
                                    Thread.sleep(1000);
                                    time++;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    isFinish = false;
                    if(service.isShutdown()){
                        service = Executors.newSingleThreadExecutor();
                    }
                    break;
            }
        }
    }
}

//package com.cesaas.android.counselor.order.shop.fragment;
//import android.content.Intent;
//import android.view.View;
//import android.webkit.WebView;
//import android.widget.TextView;
//import com.cesaas.android.counselor.order.R;
//import com.cesaas.android.counselor.order.base.BaseFragment;
//import com.cesaas.android.counselor.order.dialog.WaitDialog;
//import com.cesaas.android.counselor.order.global.Urls;
//import com.cesaas.android.counselor.order.utils.WebViewUtils;
//import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
//import com.cesaas.android.counselor.order.webview.base.BaseInitJavascriptInterface;
//import com.flybiao.materialdialog.MaterialDialog;
//
///**
// * Author FGB
// * Description 商品Fragment
// * Created 2017/4/6 18:32
// * Version 1.zero
// */
//public class ShopFragment extends BaseFragment implements View.OnClickListener {
//    private WebView wv_product;
//    protected WaitDialog mDialog;
//    private MaterialDialog mMaterialDialog;
//    private TextView mTextViewTitle,getmTextViewRightTitle;
//
//    /**
//     * 单例
//     */
//    public static ShopFragment newInstance() {
//        ShopFragment fragmentCommon = new ShopFragment();
//        return fragmentCommon;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_shop;
//    }
//
//    @Override
//    public void initViews() {
//        mMaterialDialog=new MaterialDialog(getContext());
//        wv_product=findView(R.id.wv_product);
//        mTextViewTitle= (TextView) findView(R.id.tv_base_title);
//        getmTextViewRightTitle= (TextView) findView(R.id.tv_base_title_right);
//    }
//
//    // 回调方法，从第二个页面回来的时候会执行这个方法
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////        BaseJavascriptInterface.getResult(requestCode, resultCode, data,getContext(),getActivity(),wv_product);
//        BaseActivityResult result=new BaseActivityResult(getContext(),getActivity(),wv_product);
//        result.getOnActivityResult(requestCode,resultCode,data);
//    }
//
//    @Override
//    public void initData() {
//        mDialog = new WaitDialog(getContext());
//        WebViewUtils.initWebSettings(wv_product,mDialog, Urls.PRODUCT);
//        BaseInitJavascriptInterface.initJavascriptInterface(getContext(),getActivity(),mMaterialDialog,wv_product,bundle,prefs,mTextViewTitle,getmTextViewRightTitle,1);
//    }
//
//    @Override
//    public void initListener() {}
//
//    @Override
//    public void processClick(View v) {}
//
//    @Override
//    public void fetchData() {}
//
//}
