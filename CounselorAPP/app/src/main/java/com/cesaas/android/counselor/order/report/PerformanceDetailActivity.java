package com.cesaas.android.counselor.order.report;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseTestBean;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;
import com.cesaas.android.counselor.order.webview.bean.AlertDialogBean;
import com.cesaas.android.counselor.order.webview.bean.ConfirmDialogBean;
import com.cesaas.android.counselor.order.webview.bean.PromptDialogBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.PromptValueBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultPromptInfoBean;
import com.flybiao.materialdialog.MaterialDialog;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 业绩分配详情
 */
public class PerformanceDetailActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;

    private JavascriptInterface javascriptInterface;

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

    protected WaitDialog mDialog;

    private List<String> param;


    private WebView wv_performance_distribution;
    private SwipeRefreshLayout swipe_layout;

    private String url;
    private String VipId;
    private String VipName;

    private int requestCode=200;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_detail);

        mMaterialDialog=new MaterialDialog(mContext);

        Bundle bundle=getIntent().getExtras();
        url=bundle.getString("Url");

        userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();

        initView();
        initData();
    }

    public void initData() {
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_performance_distribution,mDialog,url);
        WebViewUtils.initSwipeRefreshLayout(swipe_layout,wv_performance_distribution);

        javascriptInterface = new JavascriptInterface(mContext);
        wv_performance_distribution.addJavascriptInterface(javascriptInterface, "appHome");

    }


    private void initView() {
        swipe_layout= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshShopLayout);
        wv_performance_distribution= (WebView) findViewById(R.id.wv_performance_distribution_detail);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("分配业绩详情");
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;

        }
    }

//    public void onEventMainThread(final WebViewRequestJsonBean mJson) {
//
//        switch (mJson.getRequestType()){
//            case 901://标签筛选
//                LabelScreenBean labelScreenBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),LabelScreenBean.class);
//                ToastFactory.getLongToast(mContext,"标签筛选");
//                break;
//
//            case 701://选择店员
//                SelectClerkBean clerkBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SelectClerkBean.class);
//                Intent intent= new Intent(mContext, ChooseClerkActivity.class);
//                intent.putExtra("RoleIds",clerkBean.getParam().getPosition_id()+"");
//                intent.putExtra("Multi",clerkBean.getParam().getMulti());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                Skip.mSelActivityResult(mActivity,requestCode,intent);
//                break;
//            case 401://选择会员
//                SelectMemberBean memberBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SelectMemberBean.class);
//                Skip.mActivityResult(mActivity,ChooseClerkActivity.class,requestCode);
//                break;
//            case 101://弹出对话框
//                AlertDialogBean dialogBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),AlertDialogBean.class);
//                if (mMaterialDialog != null) {
//                    mMaterialDialog.setTitle("温馨提示！")
//                            .setMessage(dialogBean.getParam().getMessage())
//                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
//                            .setPositiveButton("确定", new View.OnClickListener() {
//                                @Override public void onClick(View v) {
//                                    //执行确定操作
//                                    mMaterialDialog.dismiss();
//                                }
//                            }).setNegativeButton("取消", new View.OnClickListener() {
//                        @Override public void onClick(View v) {
//                            mMaterialDialog.dismiss();
//                        }}).setCanceledOnTouchOutside(true).show();
//                }
//                break;
//            case 102://弹出确认框
//                ConfirmDialogBean dialogBean1=JsonUtils.fromJson(mJson.getWebviewParamJson(),ConfirmDialogBean.class);
//                if (mMaterialDialog != null) {
//                    mMaterialDialog.setTitle(dialogBean1.getParam().getTitle())
//                            .setMessage(dialogBean1.getParam().getText())
//                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
//                            .setPositiveButton("确定", new View.OnClickListener() {
//                                @Override public void onClick(View v) {
//                                    //执行确定操作
//                                    mMaterialDialog.dismiss();
//                                }
//                            }).setNegativeButton("取消", new View.OnClickListener() {
//                        @Override public void onClick(View v) {
//                            mMaterialDialog.dismiss();
//                        }}).setCanceledOnTouchOutside(true).show();
//                }
//                break;
//
//            case 103://弹出带输入框的确认框
//                PromptDialogBean promptDialogBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),PromptDialogBean.class);
//                BaseWebViewDialog baseWebViewDialog=new BaseWebViewDialog(mContext,mActivity,promptDialogBean.getParam().getTitle(),promptDialogBean.getParam().getText());
//                baseWebViewDialog.mInitShow();
//                baseWebViewDialog.setCancelable(false);
//                break;
//
//            case 104://跳转页面
//                SkipBean skipBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SkipBean.class);
//                ToastFactory.getLongToast(mContext,"页面跳转:"+skipBean.getParam().getUrl());
//                break;
//            case 105://返回
//                Skip.mNext(mActivity,HomeActivity.class);
//                break;
//            case 106://右上角接口
//                ToRightBean rightBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),ToRightBean.class);
//                tvBaseTitle.setText(rightBean.getParam().getTitle());
//                url=rightBean.getParam().getUrl();
//                break;
//
//            case 107://修改顶部标题
//                TopTitleBean titleBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),TopTitleBean.class);
//                tvBaseTitle.setText(titleBean.getParam().getTitle());
//                break;
//
//            case 108://打电话
////                CallPhoneBean phoneBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),CallPhoneBean.class);
////                CallUtils.call(phoneBean.getParam().getMobile(),mActivity);
////                VipId=phoneBean.getParam().getVip_id();
////                VipName=phoneBean.getParam().getName();
////
////                //调用通话监听
////                TelephonyManager phoneManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
////                phoneManager.listen(new PhoneListenUtils(mContext),
////                        PhoneStateListener.LISTEN_CALL_STATE);
//                break;
//
//            case 109://发送短信
//                SendMsgBean msgBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SendMsgBean.class);
//                bundle.putString("VipId",msgBean.getParam().getVip_id());
//                bundle.putString("Tel",msgBean.getParam().getMobile());
//                bundle.putString("VipName",msgBean.getParam().getName());
//                Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
//                break;
//
//            case 110://发送微信
//                SendWeChatBean weChatBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),SendWeChatBean.class);
//                //启动单聊会话界面
//                if (RongIM.getInstance() != null)
//                    RongIM.getInstance().startPrivateChat(mContext, weChatBean.getParam().getVip_id(),weChatBean.getParam().getName());
//                break;
//
//            case 111://图片上传
//                //打开选择,本次允许选择的数量
//
//                break;
//
//            case 112://图片预览
//                PreviewPictureBean pictureBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),PreviewPictureBean.class);
//
//
//                break;
//
//            case 113://打开文件
//                OpenFileBean fileBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),OpenFileBean.class);
//                break;
//
//            case 114://通知App数据改变
//                NotificationAppUpdateDataBean updateDataBean=JsonUtils.fromJson(mJson.getWebviewParamJson(),NotificationAppUpdateDataBean.class);
//                wv_performance_distribution.reload();
//                break;
//        }
//
//    }


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
                    break;

                case 701://选择店员
                    WebViewRequestJsonBean selClerk=new WebViewRequestJsonBean();
                    selClerk.setWebviewParamJson(postData);
                    selClerk.setRequestType(701);
                    break;
                case 401://选择会员
                    WebViewRequestJsonBean selMember=new WebViewRequestJsonBean();
                    selMember.setWebviewParamJson(postData);
                    selMember.setRequestType(401);

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
                                }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                mMaterialDialog.dismiss();
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
                    BaseWebViewDialog baseWebViewDialog=new BaseWebViewDialog(mContext,mActivity,promptDialogBean.getParam().getTitle(),promptDialogBean.getParam().getText());
                    baseWebViewDialog.mInitShow();
                    baseWebViewDialog.setCancelable(false);
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

                    Skip.mNext(mActivity,PerformanceDistributionActivity.class);
                    break;

                case 106://右上角按钮
                    WebViewRequestJsonBean viewRequestJsonBean=new WebViewRequestJsonBean();
                    viewRequestJsonBean.setWebviewParamJson(postData);
                    viewRequestJsonBean.setRequestType(106);
                    break;

                case 107://修改顶部标题
                    WebViewRequestJsonBean topTitleBean=new WebViewRequestJsonBean();
                    topTitleBean.setWebviewParamJson(postData);
                    topTitleBean.setRequestType(107);
                    break;

                case 108://打电话
                    WebViewRequestJsonBean telBean=new WebViewRequestJsonBean();
                    telBean.setWebviewParamJson(postData);
                    telBean.setRequestType(108);
                    break;

                case 109://发短信
                    WebViewRequestJsonBean msgBean=new WebViewRequestJsonBean();
                    msgBean.setWebviewParamJson(postData);
                    msgBean.setRequestType(109);
                    break;

                case 110://发微信
                    WebViewRequestJsonBean weChartBean=new WebViewRequestJsonBean();
                    weChartBean.setWebviewParamJson(postData);
                    weChartBean.setRequestType(110);
                    break;

                case 111://图片上传
                    WebViewRequestJsonBean imageUploadBean=new WebViewRequestJsonBean();
                    imageUploadBean.setWebviewParamJson(postData);
                    imageUploadBean.setRequestType(111);
                    break;

                case 112://图片预览
                    WebViewRequestJsonBean previewBean=new WebViewRequestJsonBean();
                    previewBean.setWebviewParamJson(postData);
                    previewBean.setRequestType(112);
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
            }

        }

        /**
         * 返回用户信息
         * @return
         */
        @android.webkit.JavascriptInterface
        public String appUserInfo(){

            userInfo=new UserInfo();
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
}
