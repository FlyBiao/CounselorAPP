package com.cesaas.android.counselor.order.member;

import android.content.Context;
import android.content.Intent;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;


import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.HomeActivity;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.base.BaseTestBean;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;

import com.cesaas.android.counselor.order.label.bean.GetTagListBean;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.member.net.SaveVisitRecordNet;
import com.cesaas.android.counselor.order.shop.fragment.ShopFragment;
import com.cesaas.android.counselor.order.shopmange.ShopTagListActivity;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;
import com.cesaas.android.counselor.order.webview.base.BaseActivityResult;
import com.cesaas.android.counselor.order.webview.base.BaseUserInfo;
import com.cesaas.android.counselor.order.webview.base.InitJavascriptInterface;
import com.cesaas.android.counselor.order.webview.base.WebViewEventBus;

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
import com.cesaas.android.counselor.order.webview.resultbean.ResultSelectMemberBean;
import com.cesaas.android.counselor.order.webview.resultbean.ResultTagInfoBean;
import com.cesaas.android.counselor.order.webview.resultbean.SelectMemberInfo;
import com.cesaas.android.counselor.order.webview.resultbean.TagInfoBean;
import com.cesaas.android.counselor.order.zoomimageview.ImagPagerUtil;
import com.flybiao.materialdialog.MaterialDialog;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description 会员Fragment
 * Created 2017/4/6 18:33
 * Version 1.zero
 */
public class MemberFragment extends BaseFragment implements View.OnClickListener {

    private WebView wv_member;
    protected WaitDialog mDialog;

    private MaterialDialog mMaterialDialog;
    private JavascriptInterface javascriptInterface;

    private String VipId;
    private String VipName;

    /**
     * 单例
     */
    public static MemberFragment newInstance() {
        MemberFragment fragmentCommon = new MemberFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    public void initViews() {
        mMaterialDialog=new MaterialDialog(getContext());
        wv_member=findView(R.id.wv_member);
    }

    @Override
    public void initData() {
        //通过EventBus订阅事件
//        EventBus.getDefault().register(this);
        mDialog = new WaitDialog(getContext());
        WebViewUtils.initWebSettings(wv_member,mDialog, Urls.MEMBER);

        javascriptInterface = new JavascriptInterface(getContext());
        wv_member.addJavascriptInterface(javascriptInterface, "appHome");

    }


    @Override
    public void initListener() {

    }

    @Override
    public void processClick(View v) {

    }

    @Override
    public void fetchData() {

    }


    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==401){
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

                wv_member.loadUrl("javascript:appCallback('"+resultSelectMemberBean.getSelectMemberResult()+"')");
            }
        }
        if(requestCode==901){//标签筛选
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
                //调用js中的appCallback方法
                wv_member.loadUrl("javascript:appCallback('"+resultTagInfoBean.getTagInfoResult()+"')");
            }
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
         *
         * @return UserToken
         */
        @android.webkit.JavascriptInterface
        public String getUserInfo() {

            return prefs.getString("token");
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
         * WebView posMessag
         *
         * @param postData
         */
        @android.webkit.JavascriptInterface
        public void postMessage(String postData) {
            Log.i(Constant.TAG, "postData" + postData);
            BaseTestBean bean = JsonUtils.fromJson(postData, BaseTestBean.class);
            switch (bean.getType()) {

                case 901://标签筛选
//                    LabelScreenBean labelScreenBean = JsonUtils.fromJson(postData, LabelScreenBean.class);
                    Intent tagIntent = new Intent(getContext(), ShopTagListActivity.class);
//                    intent.putExtra("Id",prefs.getString("ShopId"));
                    startActivityForResult(tagIntent, Constant.H5_TAG_SELECT);
                    break;

                case 401://选择会员
                    SelectMemberBean memberBean = JsonUtils.fromJson(postData, SelectMemberBean.class);
                    Intent intent2 = new Intent(getContext(), VipListActivity.class);
                    intent2.putExtra("Multi", memberBean.getParam().getMutil());
                    startActivityForResult(intent2, Constant.H5_MEMBER_SELECT);
                    break;
                case 403://选择人脸识别
                    ToastFactory.getLongToast(getContext(),"选择人脸识别");
                    break;

                case 101://弹出对话框
                    AlertDialogBean dialogBean = JsonUtils.fromJson(postData, AlertDialogBean.class);
                    if (mMaterialDialog != null) {
                        mMaterialDialog.setTitle("温馨提示！")
                                .setMessage(dialogBean.getParam().getMessage())
                                //mMaterialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //执行确定操作
                                        mMaterialDialog.dismiss();
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        }).setCanceledOnTouchOutside(true).show();
                    }
                    break;
                case 102://弹出确认框
                    ConfirmDialogBean dialogBean1 = JsonUtils.fromJson(postData, ConfirmDialogBean.class);
                    if (mMaterialDialog != null) {
                        mMaterialDialog.setTitle(dialogBean1.getParam().getTitle())
                                .setMessage(dialogBean1.getParam().getText())
                                //mMaterialDialog.setBackgroundResource(R.drawable.background);
                                .setPositiveButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //执行确定操作
                                        mMaterialDialog.dismiss();
                                    }
                                }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mMaterialDialog.dismiss();
                            }
                        }).setCanceledOnTouchOutside(true).show();
                    }
                    break;

                case 104://跳转页面
                    SkipBean skipBean = JsonUtils.fromJson(postData, SkipBean.class);
                    bundle.putString("MemberUrl", skipBean.getParam().getUrl());
                    Skip.mNextFroData(getActivity(), MemberPortraitActivity.class, bundle);
                    break;

                case 108://打电话
                    CallPhoneBean phoneBean = JsonUtils.fromJson(postData, CallPhoneBean.class);
                    CallUtils.call(phoneBean.getParam().getMobile(), getActivity());
                    VipId = phoneBean.getParam().getVip_id();
                    VipName = phoneBean.getParam().getName();

                    //调用通话监听
                    TelephonyManager phoneManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                    phoneManager.listen(new PhoneListenUtils(getContext()),
                            PhoneStateListener.LISTEN_CALL_STATE);
                    break;

                case 109://发送短信
                    SendMsgBean msgBean = JsonUtils.fromJson(postData, SendMsgBean.class);
                    bundle.putString("VipId", msgBean.getParam().getVip_id());
                    bundle.putString("Tel", msgBean.getParam().getMobile());
                    bundle.putString("VipName", msgBean.getParam().getName());
                    Skip.mNextFroData(getActivity(), SendMessageActivity.class, bundle);
                    break;

                case 110://发送微信
                    SendWeChatBean weChatBean = JsonUtils.fromJson(postData, SendWeChatBean.class);
                    //启动单聊会话界面
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().startPrivateChat(getContext(), weChatBean.getParam().getVip_id(), weChatBean.getParam().getName());
                    break;

                case 113://打开文件
                    OpenFileBean fileBean = JsonUtils.fromJson(postData, OpenFileBean.class);
                    break;

                case 114://通知App数据改变
                    NotificationAppUpdateDataBean updateDataBean = JsonUtils.fromJson(postData, NotificationAppUpdateDataBean.class);
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
