package com.cesaas.android.counselor.order.activity.user;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.UserCentreActivity;
import com.cesaas.android.counselor.order.base.BaseTemplateActivity;
import com.cesaas.android.counselor.order.bean.ResultSetUserSignatureBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.SetUserSignatureNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.io.Serializable;

/**
 * 应用设置
 */
public class AppSettingActivity extends BaseTemplateActivity {

    private TextView tvTitle;
    private LinearLayout llBack,ll_sing_name;
    private EditText tv_niciname;
    private RecyclerView rv_main_menu_list;
    private CheckBox cb_sales_data,cb_task_data,cb_order_data,cb_visit_data;

    //用户信息
    private UserInfoNet userInfoNet;
    private SetUserSignatureNet mSetUserSignatureNet;

    @Override
    public int getLayoutId() {
        return R.layout.activity_app_setting;
    }

    public void onEventMainThread(ResultUserBean msg) {
        if(msg.IsSuccess!=false && msg.TModel!=null){
            tv_niciname.setText(msg.TModel.Siganature);
        }
    }

    public void onEventMainThread(ResultSetUserSignatureBean msg) {
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"设置个性签名成功！");
        }else{
            ToastFactory.getLongToast(mContext,"设置个性签名失败！"+msg.Message);
        }
    }

    @Override
    public void initViews() {
        ll_sing_name=findView(R.id.ll_sing_name);
        tv_niciname=findView(R.id.tv_niciname);
        cb_sales_data=findView(R.id.cb_sales_data);
        cb_sales_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    mCache.put("cb_sales_data","cb_sales_data");
                }else{
                    mCache.remove("cb_sales_data");
                }
            }
        });
        cb_task_data=findView(R.id.cb_task_data);
        cb_task_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    mCache.put("cb_task_data","cb_task_data");
                }else{
                    mCache.remove("cb_task_data");
                }
            }
        });
        cb_order_data=findView(R.id.cb_order_data);
        cb_order_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    mCache.put("cb_order_data","cb_order_data");
                }else{
                    mCache.remove("cb_order_data");
                }
            }
        });
        cb_visit_data=findView(R.id.cb_visit_data);
        cb_visit_data.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    mCache.put("cb_visit_data","cb_visit_data");
                }else{
                    mCache.remove("cb_visit_data");
                }
            }
        });
        tvTitle=findView(R.id.tv_base_title);
        tvTitle.setText("系统设置");
        llBack=findView(R.id.ll_base_title_back);
        rv_main_menu_list=findView(R.id.rv_main_menu_list);
        rv_main_menu_list.setLayoutManager(new GridLayoutManager(this,2));

        ll_sing_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetUserSignatureNet=new SetUserSignatureNet(mContext);
                mSetUserSignatureNet.setData(tv_niciname.getText().toString());
            }
        });
    }

    @Override
    public void initListener() {
        llBack.setOnClickListener(this);
    }

    @Override
    public void initData() {

        userInfoNet=new UserInfoNet(mContext);
        userInfoNet.setData();

        if(mCache.getAsString("cb_sales_data")!=null && !"".equals(mCache.getAsString("cb_sales_data"))){
            cb_sales_data.setChecked(true);
        }else{
            cb_sales_data.setChecked(false);
        }
        if(mCache.getAsString("cb_task_data")!=null && !"".equals(mCache.getAsString("cb_task_data"))){
            cb_task_data.setChecked(true);
        }else{
            cb_task_data.setChecked(false);
        }
        if(mCache.getAsString("cb_order_data")!=null && !"".equals(mCache.getAsString("cb_order_data"))){
            cb_order_data.setChecked(true);
        }else{
            cb_order_data.setChecked(false);
        }
        if(mCache.getAsString("cb_visit_data")!=null && !"".equals(mCache.getAsString("cb_visit_data"))){
            cb_visit_data.setChecked(true);
        }else{
            cb_visit_data.setChecked(false);
        }
    }

    @Override
    public void processClick(View v) {

        switch (v.getId()){
            case R.id.ll_base_title_back:
//                Skip.mBack(mActivity);
                Intent mIntent = new Intent();
                mIntent.putExtra("selectList","1022");
                setResult(1022, mIntent);// 设置结果，并进行传送
                finish();
            break;
            default:
                break;
        }
    }

}
