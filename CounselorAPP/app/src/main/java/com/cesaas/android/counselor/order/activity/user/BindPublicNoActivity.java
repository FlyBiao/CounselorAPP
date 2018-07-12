package com.cesaas.android.counselor.order.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.user.bean.ResultBindPublicNoBean;
import com.cesaas.android.counselor.order.activity.user.bean.ResultBindPublicNoCodeBean;
import com.cesaas.android.counselor.order.activity.user.net.BindPublicNoCodeNet;
import com.cesaas.android.counselor.order.activity.user.net.BindPublicNoNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 绑定微信公众号
 */
public class BindPublicNoActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout mBack,mBind;
    private TextView mBaseTitle;
    private ImageView iv_bind_code;

    private BindPublicNoCodeNet mBindPublicNoCodeNet;
    private BindPublicNoNet mBindPublicNoNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_public_no);
        mBindPublicNoCodeNet=new BindPublicNoCodeNet(mContext);
        mBindPublicNoCodeNet.setData();

        initView();
    }

    public void onEventMainThread(final ResultBindPublicNoCodeBean msg) {
        if(msg.IsSuccess==true){
            Glide.with(mContext).load(msg.TModel).placeholder(R.mipmap.loading).into(iv_bind_code);

        }else{
            ToastFactory.getLongToast(mContext,"获取商家二维码失败!"+msg.Message);
        }
    }

    public void onEventMainThread(final ResultBindPublicNoBean msg) {
        if(msg.IsSuccess==true){
            ToastFactory.getLongToast(mContext,"恭喜您！绑定公众号成功!"+msg.Message);
            Skip.mNext(mActivity, LoginActivity.class,true);
        }else{
            ToastFactory.getLongToast(mContext,"绑定失败!"+msg.Message);
        }

     }

    private void initView() {
        iv_bind_code= (ImageView) findViewById(R.id.iv_bind_code);
        mBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        mBaseTitle.setText("绑定公众号");
        mBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mBind= (LinearLayout) findViewById(R.id.ll_bind);
        mBack.setOnClickListener(this);
        mBind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;

            case R.id.ll_bind://绑定
                mBindPublicNoNet=new BindPublicNoNet(mContext);
                mBindPublicNoNet.setData();
                break;
        }
    }
}
