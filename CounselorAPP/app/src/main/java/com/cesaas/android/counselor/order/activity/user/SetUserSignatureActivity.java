package com.cesaas.android.counselor.order.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.UserCentreActivity;
import com.cesaas.android.counselor.order.bean.ResultSetUserSignatureBean;
import com.cesaas.android.counselor.order.fragment.HomeFragment;
import com.cesaas.android.counselor.order.net.SetUserSignatureNet;
import com.cesaas.android.counselor.order.net.UserInfoNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;

/**
 * 设置个性签名
 */
public class SetUserSignatureActivity extends BasesActivity implements View.OnClickListener{

    private MClearEditText et_signature;
    private TextView tv_suer_update_signature;
    private LinearLayout iv_nick_back;

    private SetUserSignatureNet mSetUserSignatureNet;
    private String userSignature;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user_signature);
        Bundle bundle=getIntent().getExtras();
        userSignature=bundle.getString("nickSignature");

        initView();
        initData();
    }

    public void initView(){
        et_signature= (MClearEditText) findViewById(R.id.et_signature);
        et_signature.setText(userSignature);
        tv_suer_update_signature= (TextView) findViewById(R.id.tv_suer_update_signature);
        tv_suer_update_signature.setOnClickListener(this);
        iv_nick_back= (LinearLayout) findViewById(R.id.iv_nick_back);
        iv_nick_back.setOnClickListener(this);
    }

    public void initData(){

    }

    public void onEventMainThread(ResultSetUserSignatureBean msg) {
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"设置个性签名成功！");
            Skip.mNext(mActivity, UserCentreActivity.class);
        }else{
            ToastFactory.getLongToast(mContext,"设置个性签名失败！"+msg.Message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_suer_update_signature://
                if(et_signature.getText().toString()!=null){
                    mSetUserSignatureNet=new SetUserSignatureNet(mContext);
                    mSetUserSignatureNet.setData(et_signature.getText().toString());
                }else{
                    ToastFactory.getLongToast(mContext,"签名不能为空！");
                }

            case R.id.iv_nick_back:
                Skip.mBack(mActivity);
                break;
        }
    }
}
