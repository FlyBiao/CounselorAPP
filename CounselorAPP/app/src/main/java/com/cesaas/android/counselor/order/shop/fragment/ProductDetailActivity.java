package com.cesaas.android.counselor.order.shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseActivity;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.bean.UserInfo;
import com.cesaas.android.counselor.order.dialog.WaitDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.global.Urls;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.utils.WebViewUtils;
import com.cesaas.android.counselor.order.webview.WebViewBaseBean;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends BaseActivity {

    private TextView tvBaseTitle;
    private LinearLayout llBaseBack;

    private String ProductDetailUrl;

    private WebView wv_product;

    private JavascriptInterface javascriptInterface;
    private SwipeRefreshLayout swipeRefreshLayout;

    public String shopId;//店铺ID
    public String shopMobile;//店铺电话
    public String typeName;//用户身份：店员，店长
    public String typeId;//1：店长，2：店员
    public String vipId;
    public String imToken;
    public int tId;

    private UserInfoNet userInfoNet;
    private UserInfo userInfo;

    protected WaitDialog mDialog;

    private List<String> param;

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    public void initViews() {
        userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();

        swipeRefreshLayout=findView(R.id.swipeRefreshShopLayout);
        wv_product=findView(R.id.wv_product_detail);


        tvBaseTitle=findView(R.id.tv_base_title);
        tvBaseTitle.setText("商品详情");
        llBaseBack=findView(R.id.ll_base_title_back);

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        Bundle bundle=getIntent().getExtras();
        ProductDetailUrl=bundle.getString("ProductDetailUrl");

        mDialog = new WaitDialog(mContext);
        WebViewUtils.initWebSettings(wv_product,mDialog, ProductDetailUrl);

        javascriptInterface = new JavascriptInterface(mContext);
        wv_product.addJavascriptInterface(javascriptInterface, "appHome");

        WebViewUtils.initSwipeRefreshLayout(swipeRefreshLayout,wv_product);
    }

    @Override
    public void processClick(View v) {

    }

    public void onEventMainThread(ResultUserBean msg) {

        if (msg != null) {
            shopId=msg.TModel.ShopId;//店铺ID
            shopMobile=msg.TModel.ShopMobile;//店铺电话
            typeName=msg.TModel.TypeName;//用户身份：店员，店长
            typeId=msg.TModel.TypeId;//1：店长，2：店员
            vipId=msg.TModel.VipId+"";
            imToken=msg.TModel.ImToken;
            tId=msg.TModel.tId;

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
            WebViewBaseBean bean= JsonUtils.fromJson(postData,WebViewBaseBean.class);
            param=new ArrayList<>();
            param.addAll(bean.getParam());
            Log.i(Constant.TAG,"postData"+postData);

            switch (bean.getType()){
//                case 1://商品详情
//
//                    break;
            }

        }

        /**
         * 返回用户信息
         * @return
         */
        @android.webkit.JavascriptInterface
        public String appUserInfo(){

            userInfo=new UserInfo();
            userInfo.setImToken(imToken);
            userInfo.setShopId(shopId);
            userInfo.setShopMobile(shopMobile);
            userInfo.setTypeId(typeId);
            userInfo.setTypeName(typeName);
            userInfo.setVipId(vipId);
            userInfo.settId(tId);

            return JsonUtils.toJson(userInfo);
        }

    }

}
