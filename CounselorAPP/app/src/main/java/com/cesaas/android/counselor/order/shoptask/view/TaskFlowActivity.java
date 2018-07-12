package com.cesaas.android.counselor.order.shoptask.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.MemberListActivity;
import com.cesaas.android.counselor.order.member.MemberPortraitActivity;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.report.bean.PerformanceDetailBusBean;
import com.cesaas.android.counselor.order.shopmange.ChooseClerkActivity;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.WebViewBaseBean;
import com.cesaas.android.counselor.order.webview.WebViewJsonBean;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;

/**
 * 任务流程页面
 */
public class TaskFlowActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,getmTextViewRightTitle;

    private WebView wv_member;

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
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_flow);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            url=bundle.getString("Url");
        }

        initView();
        initData();
    }

    public void initView(){
        userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();

        wv_member= (WebView) findViewById(R.id.wv_task_flow_activity);
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText("任务流程");
        getmTextViewRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        getmTextViewRightTitle.setVisibility(View.VISIBLE);


        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    public void initData(){
        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_member,mDialog, url);

        javascriptInterface = new JavascriptInterface(mContext);
        wv_member.addJavascriptInterface(javascriptInterface, "appHome");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:

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
//            WebViewJsonBean beanJson= JsonUtils.fromJson(postData,WebViewJsonBean.class);
            WebViewBaseBean bean= JsonUtils.fromJson(postData,WebViewBaseBean.class);
            param=new ArrayList<>();
            param.addAll(bean.getParam());

            PerformanceDetailBusBean detailBusBean=new PerformanceDetailBusBean();
            detailBusBean.setDate(param.get(0));
            detailBusBean.setUrl(param.get(1));
            EventBus.getDefault().post(detailBusBean);

            switch (bean.getType()){
                case 1://会员画像
                    bundle.putString("MemberUrl",param.get(0));
                    Skip.mNextFroData(mActivity,MemberPortraitActivity.class,bundle);
                    break;

                case 30://拨打电话
                    CallUtils.call(param.get(1),mActivity);
                    break;

                case 31://发短信
                    Bundle bundlen=new Bundle();
                    bundlen.putString("Tel",param.get(1));
                    Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
                    break;

                case 32://标签筛选
                    ToastFactory.getLongToast(mContext, "标签筛选！");
                    break;

                case 27://发送微信会话
                    //启动单聊会话界面
                    if (RongIM.getInstance() != null)
                        RongIM.getInstance().startPrivateChat(mContext, param.get(0),"微信会话");
                    break;

                case 23://会员标签列表
                    Skip.mNext(mActivity,MemberListActivity.class);
                    break;

                case 35://提示文字接口
                    ToastFactory.getLongToast(mContext, "提示文字接口");
                    break;

                case 37://提示输入对话框
                    break;

//                case 42://选择店员
//                    Skip.mNext(mActivity,ChooseClerkActivity.class);
//                    break;

                case 41://选择商品
                    break;

                case 44://选择话术
                    break;
            }

            WebViewJsonBean beanJson= JsonUtils.fromJson(postData,WebViewJsonBean.class);
            if(beanJson.getType()==42){
                Skip.mNext(mActivity,ChooseClerkActivity.class);
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
}
