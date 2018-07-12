package com.cesaas.android.counselor.order.activity.user.register;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.BasesSpecialActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.user.adapter.MemberRoleAdapter;
import com.cesaas.android.counselor.order.activity.user.bean.EditTextChangeValueBean;
import com.cesaas.android.counselor.order.activity.user.bean.RegisterAuthorizationBean;
import com.cesaas.android.counselor.order.activity.user.bean.RegisterShopNameBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultDefaultRoleBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultShopNameListBean;
import com.cesaas.android.counselor.order.activity.user.net.DefaultRoleNet;
import com.cesaas.android.counselor.order.bean.ResultRegistCompanyBean;
import com.cesaas.android.counselor.order.bean.ResultSendCodeBean;
import com.cesaas.android.counselor.order.boss.adapter.member.MemberServiceCategoryAdapter;
import com.cesaas.android.counselor.order.boss.bean.member.CategoryDataUtils;
import com.cesaas.android.counselor.order.custom.popupwindow.PopupAdapter;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.SendCodeNet;
import com.cesaas.android.counselor.order.utils.AbDataPrefsUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.EditTextChangeListener;
import com.cesaas.android.counselor.order.utils.MD5;
import com.cesaas.android.counselor.order.utils.NetworkUtil;
import com.cesaas.android.counselor.order.utils.OtherUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 注册
 */
public class RegisterActivity extends BasesSpecialActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;

    private MClearEditText et_authorization_code;
    private MClearEditText et_register_mobile;
    private MClearEditText et_register_contact;
    private MClearEditText et_register_auth_code;
    private MClearEditText et_register_remark;
    private MClearEditText et_shop_name;
    private TextView tv_sel_shop_name,tv_role;
    private Button btn_get_auth_code,btn_query_auth_code;
    private Button bt_register;
    private Button btn_search_shop;
    private RelativeLayout rl_search_shop;

    private static int screenW, screenH;
    private TopMiddlePopup middlePopup;

    private int roleId;
    private boolean roles=false;

    private String itmes;
    private String msgCode;
    private String shopId;
    private String shopName;
    private String shopCity;
    private String registerAuthorization;
    private TimeCount time;
    private SendCodeNet codeNet;
    private RegisterNet mRegisterNet;
    private GetListByNameNet mListByNameNet;
    private RegistCompanyNet companyNet;
    private DefaultRoleNet roleNet;
    private List<RegisterShopNameBean> shopList;
    private List<ResultDefaultRoleBean.DefaultRoleBean> roleBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        getScreenPixels();
    }

    private void initView() {
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBaseBack.setOnClickListener(this);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tv_role= (TextView) findViewById(R.id.tv_role);
        tv_role.setOnClickListener(this);
        tvBaseTitle.setText("注册");

        tv_sel_shop_name= (TextView) findViewById(R.id.tv_sel_shop_name);
        rl_search_shop= (RelativeLayout) findViewById(R.id.rl_search_shop);
        et_authorization_code= (MClearEditText) findViewById(R.id.et_authorization_code);
        et_authorization_code.addTextChangedListener(new EditTextChangeListener());
        et_register_mobile= (MClearEditText) findViewById(R.id.et_register_mobile);
        et_register_contact= (MClearEditText) findViewById(R.id.et_register_contact);
        et_register_auth_code= (MClearEditText) findViewById(R.id.et_register_auth_code);
        et_register_remark= (MClearEditText) findViewById(R.id.et_register_remark);
        et_shop_name= (MClearEditText) findViewById(R.id.et_shop_name);
        btn_get_auth_code= (Button) findViewById(R.id.btn_get_auth_code);
        btn_query_auth_code= (Button) findViewById(R.id.btn_query_auth_code);
        btn_get_auth_code.setOnClickListener(this);
        btn_query_auth_code.setOnClickListener(this);
        bt_register= (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        btn_search_shop= (Button) findViewById(R.id.btn_search_shop);
        btn_search_shop.setOnClickListener(this);
    }

    public void onEventMainThread(com.cesaas.android.counselor.order.activity.user.bean.ResultRegistCompanyBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                roles=true;
                tv_sel_shop_name.setText(msg.TModel);
                //获取账号角色
                roleNet=new DefaultRoleNet(mContext);
                roleNet.setData(msg.TModel);
            }else{
                ToastFactory.getLongToast(mContext,"请检查授权码是否正确！");
            }
        }else{
            ToastFactory.getLongToast(mContext,"Msg:"+msg.Message);
        }
    }

    public void onEventMainThread(EditTextChangeValueBean msg) {
        if(msg!=null){
            companyNet=new RegistCompanyNet(mContext);
            companyNet.setData(et_authorization_code.getText().toString());
        }
    }

    public void onEventMainThread(ResultDefaultRoleBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && msg.TModel.size()!=0){
                roleBeanList=new ArrayList<>();
                roleBeanList.addAll(msg.TModel);
                if(roles==true){
                    roles=false;
                    tv_role.setText(roleBeanList.get(0).getRoleName());
                    roleId=roleBeanList.get(0).getRoleId();
                }else{
                    showPopupWindow(tv_role);
                }
            }
        }else{
            Log.i("test","获取角色失败！"+msg.Message);
        }
    }

    /**
     * 接收店铺列表消息
     * @param msg
     */
    public void onEventMainThread(ResultShopNameListBean msg) {
        if(msg.IsSuccess==true){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                shopList= new ArrayList<>();
                shopList.addAll(msg.TModel);
                setPopup(0);
                middlePopup.show(rl_search_shop);
            }else{
                ToastFactory.getLongToast(mContext,"获取店铺数据为空！");
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取店铺数据失败！"+msg.Message);
        }
    }

    /**
     * 接收Header消息
     * @param msg
     */
    public void onEventMainThread(RegisterAuthorizationBean msg) {
        String authorizationValue=msg.getAuthorization();
        registerAuthorization=authorizationValue.replace("Authorization: ","");
    }

    public void onEventMainThread(ResultSendCodeBean msg) {
        if(msg.IsSuccess==true){
            //填写验证码
            if(msg.TModel.mobile!=null && !"".equals(msg.TModel.mobile)){
                et_register_auth_code.setText(msg.TModel.mobile);
            }

        }else{
            ToastFactory.getLongToast(mContext,"获取验证码失败！"+msg.Message);
        }

    }


    /**
     * 接收商家注册消息
     * @param msg 消息实体类
     */
    public void onEventMainThread(ResultRegistCompanyBean msg) {
        if(msg.IsSuccess==true ){

            ToastFactory.getLongToast(mContext, "恭喜您，注册成功,账号审核中！");
            bundle.putString("Mobile",et_register_mobile.getText().toString());
            Skip.mNextFroData(mActivity,LoginActivity.class,bundle,true);
        }else{
            ToastFactory.getLongToast(mContext,msg.Message);
        }
    }

        @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_role:
                if(!TextUtils.isEmpty(et_authorization_code.getText().toString())){
                    roleNet=new DefaultRoleNet(mContext);
                    roleNet.setData(et_authorization_code.getText().toString());
                }else{
                    ToastFactory.getLongToast(mContext,"请输入企业授权码！");
                }
                break;
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.btn_get_auth_code:
                if(!TextUtils.isEmpty(et_authorization_code.getText().toString())){
                    if(OtherUtil.phoneVerify(mContext, et_register_mobile.getText().toString())){
                        time = new TimeCount(60000, 1000);
                        time.start();
                        codeNet=new SendCodeNet(mContext);
                        codeNet.setData(et_register_mobile.getText().toString(),et_authorization_code.getText().toString());
                    }
                }else{
                    ToastFactory.getLongToast(mContext,"请输入企业授权码！");
                }
                break;
            case R.id.btn_query_auth_code:
                if(!TextUtils.isEmpty(et_authorization_code.getText().toString())){
                    roles=true;
                    companyNet=new RegistCompanyNet(mContext);
                    companyNet.setData(et_authorization_code.getText().toString());
                }else{
                    roles=false;
                    ToastFactory.getLongToast(mContext,"请输入企业授权码！");
                }
                break;
            case R.id.bt_register:
                if(!TextUtils.isEmpty(et_authorization_code.getText().toString())){
                    if(!TextUtils.isEmpty(et_shop_name.getText().toString())){
                        if(OtherUtil.phoneVerify(mContext, et_register_mobile.getText().toString())){
                            if(!TextUtils.isEmpty(et_register_contact.getText().toString())){
                                if(!TextUtils.isEmpty(et_register_auth_code.getText().toString())){
                                    if(shopId!=null && !"".equals(shopId)){
                                        if(roleId!=0){
                                            //执行申请注册操作
                                            mRegisterNet=new RegisterNet(mContext);
                                            mRegisterNet.setData(
                                                    et_register_remark.getText().toString(),
                                                    et_register_mobile.getText().toString(),
                                                    et_register_auth_code.getText().toString(),
                                                    et_register_contact.getText().toString(),
                                                    et_authorization_code.getText().toString(),
                                                    shopId,roleId);
                                        }else{
                                            //执行申请注册操作
                                            mRegisterNet=new RegisterNet(mContext);
                                            mRegisterNet.setData(
                                                    et_register_remark.getText().toString(),
                                                    et_register_mobile.getText().toString(),
                                                    et_register_auth_code.getText().toString(),
                                                    et_register_contact.getText().toString(),
                                                    et_authorization_code.getText().toString(),
                                                    shopId);
                                        }
                                    }else{
                                        ToastFactory.getLongToast(mContext,"请通过企业授权码获取正确的店铺！");
                                    }

                                }else{
                                    ToastFactory.getLongToast(mContext,"请输入验证码！");
                                }
                            }else{
                                ToastFactory.getLongToast(mContext,"请输入联系人！");
                            }
                        }
                    }else{
                        ToastFactory.getLongToast(mContext,"请选择店铺！");
                    }
                }else{
                    ToastFactory.getLongToast(mContext,"请输入企业授权码！");
                }
                break;

            case R.id.btn_search_shop://搜索店铺
                if(!TextUtils.isEmpty(et_authorization_code.getText().toString())){
                    if(!TextUtils.isEmpty(tv_sel_shop_name.getText().toString())){
                        if(!TextUtils.isEmpty(et_shop_name.getText().toString())){
                            mListByNameNet=new GetListByNameNet(mContext);
                            mListByNameNet.setData(et_shop_name.getText().toString(),et_authorization_code.getText().toString());
                        }else{
                            ToastFactory.getLongToast(mContext,"请输入关键字搜索相关店铺");
                        }
                    }else{
                        ToastFactory.getLongToast(mContext,"请点击上方搜索获取品牌名称！");
                    }

                }else{
                    ToastFactory.getLongToast(mContext,"请输入企业授权码！");
                }
                break;
        }

    }


    /**
     * 定时发送
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_get_auth_code.setBackgroundResource(R.drawable.button_ellipse_huise_bg);
            btn_get_auth_code.setClickable(false);
            btn_get_auth_code.setText("("+millisUntilFinished / 1000 +") 秒后重新发送");
        }

        @Override
        public void onFinish() {
            btn_get_auth_code.setText("重新获取验证码");
            btn_get_auth_code.setClickable(true);
            btn_get_auth_code.setBackgroundResource(R.drawable.button_ellipse_blue_bg);

        }
    }


    /**
     * 设置弹窗
     *
     * @param type
     */
    private void setPopup(int type) {
        middlePopup = new TopMiddlePopup(RegisterActivity.this, screenW, screenH, onItemClickListener, shopList, type);
    }

    /**
     * 弹窗点击事件
     */
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            middlePopup.dismiss();
        }
    };

    /**
     * 获取屏幕的宽和高
     */
    public void getScreenPixels() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenW = metrics.widthPixels;
        screenH = metrics.heightPixels;
    }


    /**
     * Author FGB
     * Description 自定义弹窗类
     * Created at 2017/5/4 16:31
     * Version 1.0
     */

    public class TopMiddlePopup extends PopupWindow {

        private Context myContext;
        private ListView myLv;
        private AdapterView.OnItemClickListener myOnItemClickListener;
        private List<RegisterShopNameBean> myItems;
        private int myWidth;
        private int myHeight;
        private int myType;

        // 判断是否需要添加或更新列表子类项
        private boolean myIsDirty = true;

        private LayoutInflater inflater = null;
        private View myMenuView;

        private LinearLayout popupLL;

        private PopupAdapter adapter;

        public TopMiddlePopup(Context context) {
            // TODO Auto-generated constructor stub
        }

        public TopMiddlePopup(Context context, int width, int height,
                              AdapterView.OnItemClickListener onItemClickListener, List<RegisterShopNameBean> items,
                              int type) {

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myMenuView = inflater.inflate(R.layout.top_popup_shop, null);

            this.myContext = context;
            this.myItems = items;
            this.myOnItemClickListener = onItemClickListener;
            this.myType = type;

            this.myWidth = width;
            this.myHeight = height;

            initWidget();
            setPopup();
        }

        /**
         * 初始化控件
         */
        private void initWidget() {
            myLv = (ListView) myMenuView.findViewById(R.id.popup_lv);
            popupLL = (LinearLayout) myMenuView.findViewById(R.id.popup_layout);
            myLv.setOnItemClickListener(myOnItemClickListener);

            if (myType == 1) {
                android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                        .getLayoutParams();
                lpPopup.width = (int) (myWidth * 1.0 / 4);
                lpPopup.setMargins(0, 0, (int) (myWidth * 3.0 / 4), 0);
                popupLL.setLayoutParams(lpPopup);
            } else if (myType == 2) {
                android.widget.RelativeLayout.LayoutParams lpPopup = (android.widget.RelativeLayout.LayoutParams) popupLL
                        .getLayoutParams();
                lpPopup.width = (int) (myWidth * 1.0 / 4);
                lpPopup.setMargins((int) (myWidth * 3.0 / 4), 0, 0, 0);
                popupLL.setLayoutParams(lpPopup);
            }
        }

        /**
         * 设置popup的样式
         */
        private void setPopup() {
            // 设置AccessoryPopup的view
            this.setContentView(myMenuView);
            // 设置AccessoryPopup弹出窗体的宽度
            this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            // 设置AccessoryPopup弹出窗体的高度
            this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
            // 设置AccessoryPopup弹出窗体可点击
            this.setFocusable(true);
            // 设置AccessoryPopup弹出窗体的动画效果
            if (myType == 1) {
                this.setAnimationStyle(R.style.AnimTopLeft);
            } else if (myType == 2) {
                this.setAnimationStyle(R.style.AnimTopRight);
            } else {
                //this.setAnimationStyle(R.style.AnimTop);
                this.setAnimationStyle(R.style.AnimTopMiddle);
            }
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x33000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);

            myMenuView.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int height = popupLL.getBottom();
                    int left = popupLL.getLeft();
                    int right = popupLL.getRight();
                    System.out.println("--popupLL.getBottom()--:"
                            + popupLL.getBottom());
                    int y = (int) event.getY();
                    int x = (int) event.getX();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (y > height || x < left || x > right) {
                            System.out.println("---点击位置在列表下方--");
                            dismiss();
                        }
                    }
                    return true;
                }
            });

            myLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    shopName =myItems.get(position).getName();
                    shopId=myItems.get(position).getShopId();
                    shopCity=myItems.get(position).getShopCity();
                    et_shop_name.setText(shopName);

                    dismiss();
                }
            });
        }

        /**
         * 显示弹窗界面
         *
         * @param view
         */
        public void show(View view) {
            if (myIsDirty) {
                myIsDirty = false;
                adapter = new PopupAdapter(myContext, myItems, myType);
                myLv.setAdapter(adapter);
            }

            showAsDropDown(view, 0, 0);
        }
    }


    /**
     * Author FGB
     * Description
     * Created at 2017/5/5 16:48
     * Version 1.0
     */
    public class RegisterNet extends BaseNet {

        public RegisterNet(Context context) {
            super(context, true);
            this.uri="User/Sw/Regist/Regist";
        }

        public void setData(String remark,String mobile,String code,String name,String authCode,String ShopId){
            try {
                data.put("remark", remark);
                data.put("mobile", mobile);
                data.put("code", code);
                data.put("name", name);
                data.put("authCode",authCode);
                data.put("ShopId",ShopId);

            } catch (Exception e) {
                e.printStackTrace();
            }

            mPostNet();
        }

        public void setData(String remark,String mobile,String code,String name,String authCode,String ShopId,int RoleId){
            try {
                data.put("remark", remark);
                data.put("mobile", mobile);
                data.put("code", code);
                data.put("name", name);
                data.put("authCode",authCode);
                data.put("ShopId",ShopId);
                data.put("RoleId",RoleId);

            } catch (Exception e) {
                e.printStackTrace();
            }

            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Gson gson=new Gson();
            ResultRegistCompanyBean bean=gson.fromJson(rJson, ResultRegistCompanyBean.class);
            EventBus.getDefault().post(bean);
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
            Log.i(Constant.TAG, "err:"+err+"=="+e);
        }
    }


    /**
     * 网络请求基类
     *
     * @author Evan
     *
     */
    public class BaseNet {

        public Context mContext;
        protected String uri;
        protected JSONObject data;
        private boolean ishow;
        private WaitDialog dialog;
        protected AbDataPrefsUtil abData;
        private StringBuffer auth;

        public BaseNet(Context context, boolean show) {
            this.mContext = context;
            data = new JSONObject();
            abData = AbDataPrefsUtil.getInstance();
            this.ishow = show;
            if (show)
                dialog = new WaitDialog(context);
        }

        /**
         * 获取授权
         * @return
         */
        public String getToken() {
            StringBuffer sbuf = new StringBuffer();
            sbuf.append(AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN))
                    .append("SW-AppAuthorizationToken")
                    .append(AbPrefsUtil.getInstance().getString(Constant.SPF_TIME, ""));
            return new MD5().toMD5(sbuf.toString());
        }

        /**
         * 开始请求网络
         */
        public void mPostNet() {
            if (App.mInstance().getNetType() != NetworkUtil.NO_NETWORK) {
                RequestParams params = new RequestParams();
                try {
                    params.setBodyEntity(new StringEntity(data.toString(), "UTF-8"));
                    params.addHeader("Content-Type", "application/json");
                        auth= new StringBuffer();
                        auth.append(registerAuthorization);
                        params.addHeader("Authorization",auth.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("onSuccess","请求Url："+ Constant.IP + uri + "\n" + data+ "\n" );

                HttpUtils http = new HttpUtils();
                http.send(HttpRequest.HttpMethod.POST, Constant.IP + uri, params, new RequestCallBack<HttpUtils>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        super.onLoading(total, current, isUploading);
                        if (isUploading) {
                        } else {
                        }
                    }

                    //加载刷新
                    @Override
                    public void onStart() {
                        super.onStart();
                        if (ishow && dialog != null)
                            dialog.show();
                    }

                    @Override
                    public void onFailure(HttpException arg0, String arg1) {
                        try {
                            if (ishow && dialog != null)
                                dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mFail(arg0, arg1);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<HttpUtils> responseInfo) {
//                        Log.i("onSuccess",
//                                responseInfo.result
//                                        +"\n"+"statusCode:"+responseInfo.statusCode
//                                        +"\n"+"contentType:"+responseInfo.contentType
//                                        +"\n"+"contentEncoding:"+responseInfo.contentEncoding
//                                        +"\n"+"contentLength:"+responseInfo.contentLength
//                                        +"\n"+"reasonPhrase:"+responseInfo.reasonPhrase
//                                        +"\n"+"hashCode:"+responseInfo.hashCode()
//                                        +"\n"+"locale:"+responseInfo.locale
//                                        +"\n"+"protocolVersion:"+responseInfo.protocolVersion
//                                        +"\n"+"getAllHeaders:"+responseInfo.getAllHeaders()
//                                        +"\n"+"responseInfo:"+responseInfo
//                                        +"\n"+"responseInfo:"+responseInfo);

                        try {
                            if (ishow && dialog != null)
                                dialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mSuccess(responseInfo.result + "");
                    }
                });
            } else {
                ToastFactory.show(App.mInstance(), "未Intent网络，请检查后重试！", ToastFactory.CENTER);
            }
        }


        protected void mSuccess(String rJson) {
        }

        protected void mFail(HttpException e, String err) {
            LogUtils.d(Constant.TAG+ err+"=e"+e);
            Log.i(Constant.TAG,"\n\n" +e+"err："+err);
            ToastFactory.show(mContext, "服务器连接或返回错误！", ToastFactory.CENTER);
        }

    }

    private View view;
    // 定义PopupWindow
    private PopupWindow popWindow;
    // 获取手机屏幕分辨率的类
    private DisplayMetrics dm;
    private RecyclerView rv_category_list;
    private MemberRoleAdapter roleAdapter;

    /**
     * 显示PopupWindow弹出菜单
     */
    private void showPopupWindow(View parent) {
        if (popWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.popwindow_service_category_layout, null);
            dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 创建一个PopuWidow对象
            popWindow = new PopupWindow(view, dm.widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_blue_bg));
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(true);
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        rv_category_list= (RecyclerView) view.findViewById(R.id.rv_category_list);
        rv_category_list.setLayoutManager(new LinearLayoutManager(this));
        roleAdapter=new MemberRoleAdapter(roleBeanList,mActivity,mContext);
        rv_category_list.setAdapter(roleAdapter);
        roleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                popWindow.dismiss();
                tv_role.setText(roleBeanList.get(position).getRoleName());
                roleId=roleBeanList.get(position).getRoleId();
            }
        });
    }


}
