package com.cesaas.android.counselor.order.member;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.base.BaseTestBean;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.member.net.SaveVisitRecordNet;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.shopmange.ChooseClerkActivity;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
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
import com.cesaas.android.counselor.order.zoomimageview.ImagPagerUtil;
import com.flybiao.materialdialog.MaterialDialog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.rong.imkit.RongIM;

/**
 * 商学院
 */
public class BusinessActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;

    private JavascriptInterface javascriptInterface;

    protected WaitDialog mDialog;

    private String url;
    private ArrayList<String> picList;//预览图片集合

    private String VipId;
    private String VipName;

    private int requestCode=200;

    private MaterialDialog mMaterialDialog;


    private WebView wv_performance_distribution;
//    private SwipeRefreshLayout swipe_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        mMaterialDialog=new MaterialDialog(mContext);
        initImageLoader();

        initView();
        initData();
    }

    /**
     * 初始化加载Image
     */
    public void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public void initData() {
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_performance_distribution,mDialog, Urls.BUSINESS);
//        WebViewUtils.initSwipeRefreshLayout(swipe_layout,wv_performance_distribution);

        javascriptInterface = new JavascriptInterface(mContext);
        wv_performance_distribution.addJavascriptInterface(javascriptInterface, "appHome");

    }


    private void initView() {
//        swipe_layout= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshShopLayout);
        wv_performance_distribution= (WebView) findViewById(R.id.wv_business);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("商学院");
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
                    Intent intent1= new Intent(mContext, ShopTagListActivity.class);
                    intent1.putExtra("Id",prefs.getString("ShopId"));
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Skip.mSelActivityResult(mActivity,requestCode,intent1);
                    break;

                case 701://选择店员
                    SelectClerkBean clerkBean=JsonUtils.fromJson(postData,SelectClerkBean.class);
                    Intent intent= new Intent(mContext, ChooseClerkActivity.class);
                    intent.putExtra("RoleIds",clerkBean.getParam().getPosition_id()+"");
                    intent.putExtra("Multi",clerkBean.getParam().getMulti());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    Skip.mSelActivityResult(mActivity,requestCode,intent);
                    break;
                case 401://选择会员
                    SelectMemberBean memberBean=JsonUtils.fromJson(postData,SelectMemberBean.class);
                    Skip.mActivityResult(mActivity,ChooseClerkActivity.class,requestCode);
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
                    new BaseWebViewDialog(mContext,mActivity,promptDialogBean.getParam().getTitle()).mInitShow();
                    break;

                case 104://跳转页面
                    SkipBean skipBean=JsonUtils.fromJson(postData,SkipBean.class);
                    bundle.putString("MemberUrl",skipBean.getParam().getUrl());
                    Skip.mNextFroData(mActivity,MemberPortraitActivity.class,bundle);
                    break;
                case 105://返回
                    Skip.mNext(mActivity,HomeActivity.class);
                    break;
                case 106://右上角接口
                    ToRightBean rightBean=JsonUtils.fromJson(postData,ToRightBean.class);

                    break;

                case 107://修改顶部标题
                    TopTitleBean titleBean=JsonUtils.fromJson(postData,TopTitleBean.class);
                    break;

                case 108://打电话
                    CallPhoneBean phoneBean=JsonUtils.fromJson(postData,CallPhoneBean.class);
                    CallUtils.call(phoneBean.getParam().getMobile(),mActivity);
                    VipId=phoneBean.getParam().getVip_id();
                    VipName=phoneBean.getParam().getName();

                    //调用通话监听
                    TelephonyManager phoneManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
                    phoneManager.listen(new PhoneListenUtils(mActivity),
                            PhoneStateListener.LISTEN_CALL_STATE);
                    break;

                case 109://发送短信
                    SendMsgBean msgBean=JsonUtils.fromJson(postData,SendMsgBean.class);
                    bundle.putString("VipId",msgBean.getParam().getVip_id());
                    bundle.putString("Tel",msgBean.getParam().getMobile());
                    bundle.putString("VipName",msgBean.getParam().getName());
                    Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
                    break;

                case 110://发送微信
                    SendWeChatBean weChatBean=JsonUtils.fromJson(postData,SendWeChatBean.class);
                    //启动单聊会话界面
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().startPrivateChat(mContext, weChatBean.getParam().getVip_id(),weChatBean.getParam().getName());
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
                    ImagPagerUtil imagPagerUtil = new ImagPagerUtil(mActivity, picList);
                    imagPagerUtil.setContentText(content);
                    imagPagerUtil.show();

                    break;

                case 113://打开文件
                    OpenFileBean fileBean=JsonUtils.fromJson(postData,OpenFileBean.class);
                    break;

                case 114://通知App数据改变
                    NotificationAppUpdateDataBean updateDataBean=JsonUtils.fromJson(postData,NotificationAppUpdateDataBean.class);
                    break;
//
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
                        SaveVisitRecordNet recordNet=new SaveVisitRecordNet(mContext);
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /**
                             * 调用js中的方法实现
                             */
                            wv_performance_distribution.loadUrl("javascript:appCallback('"+resultPromptInfoBean.getPromptInfoResult()+"')");
                        }
                    });

                    cancel();
                    break;

            }
        }

    }
}
