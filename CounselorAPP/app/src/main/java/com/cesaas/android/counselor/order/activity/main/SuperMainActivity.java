package com.cesaas.android.counselor.order.activity.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.main.bean.NotNetworkBean;
import com.cesaas.android.counselor.order.bean.ResultActionPowerBean;
import com.cesaas.android.counselor.order.global.ActionPower;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.power.bean.MenuDataBean;
import com.cesaas.android.counselor.order.power.utils.SkipMenuUtils;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONException;

public class SuperMainActivity extends BasesActivity {

    private TextView tv_chongshi,tv_msg,tvRightTitle,tvTitle;
    private ImageView iv_not_net;
    private Button btn_login;

    private ActionPowerNet actionPowerNet;
    private MenuDataBean menuDataBean=new MenuDataBean();
    private String ResultNo="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_main);

        if(getIntent().getStringExtra("ResultNo")!=null && !"".equals(getIntent().getStringExtra("ResultNo"))){
            ResultNo=getIntent().getStringExtra("ResultNo");
        }

        tv_msg= (TextView) findViewById(R.id.tv_msg);
        tv_chongshi= (TextView) findViewById(R.id.tv_chongshi);
        iv_not_net= (ImageView) findViewById(R.id.iv_not_net);
        btn_login= (Button) findViewById(R.id.btn_login);
        tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setText("重新登录");
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("数据加载中...");

        actionPowerNet=new ActionPowerNet(mContext);
        actionPowerNet.setData();

        tv_chongshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionPowerNet=new ActionPowerNet(mContext);
                actionPowerNet.setData();
            }
        });
        iv_not_net.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionPowerNet=new ActionPowerNet(mContext);
                actionPowerNet.setData();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putInt("type",100);
                Skip.mNextFroData(mActivity, LoginActivity.class,bundle,true);
            }
        });
        tvRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.cleanAll();
                bundle.putInt("type",100);
                Skip.mNextFroData(mActivity, LoginActivity.class,bundle,true);
            }
        });
    }

    /**
     * 接收店铺权限菜单数据
     * @param msg
     */
    public void onEventMainThread(NotNetworkBean msg) {
        if(msg.isNetwork()!=false){
            tv_chongshi.setVisibility(View.VISIBLE);
            iv_not_net.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.VISIBLE);
        }
    }

    class ActionPowerNet extends BaseNet {
        public ActionPowerNet(Context context) {
            super(context, true);
            this.uri="User/Sw/Power/GetActionPower";
        }

        public void setData(){
            try {
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mPostNet(); // 开始请求网络
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            mCache.put("ActionPower",rJson);
            ResultActionPowerBean msg = JsonUtils.fromJson(rJson, ResultActionPowerBean.class);
            if(msg.IsSuccess!=false ){
                if(msg.TModel!=null && msg.TModel.size()!=0){
                    menuDataBean=new MenuDataBean();
                    for (int i=0;i<msg.TModel.size();i++){
                        if(msg.TModel.get(i).getACTION_NO().equals(ActionPower.A_R_A_M) || msg.TModel.get(i).getACTION_NO().equals(ActionPower.A_R_Q_M)){
                            menuDataBean.setMenuNo(msg.TModel.get(i).getACTION_NO());
                        }
                    }
                    if(menuDataBean.getMenuNo()!=null){
                        int no=Integer.parseInt(ResultNo);
                        SkipMenuUtils.skipArea(menuDataBean,mActivity,no);
                    }
                    else{
                        tv_msg.setText("该账号没有角色权限使用APP功能");
                        tv_msg.setVisibility(View.VISIBLE);
                    }
                }
            }else{
                tv_msg.setText(msg.Message);
                tv_chongshi.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
            Log.i(Constant.TAG,"**=HttpException="+e+"..=err="+err);
        }

    }

}
