package com.cesaas.android.counselor.order.shopmange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.fragment.RecommendSampleFragment;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.cesaas.android.counselor.order.shoptask.fragment.ChooseClerkSampleFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.webview.WebViewRequestJsonBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 选择店员列表
 */
public class ChooseClerkActivity extends BasesActivity implements View.OnClickListener{

    private LinearLayout llBaseBack;
    private TextView mTextViewTitle,mTextViewRightTitme;

    private int resultCode=701;

    List<SelectCounselorListBean> selectCounselorList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_clerk);

        initView();
        initData();
    }


    public void onEventMainThread(List<SelectCounselorListBean> selectCounselorListBeen) {
        selectCounselorList=new ArrayList<>();
        selectCounselorList=selectCounselorListBeen;
    }

    private void initData(){
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, new ChooseClerkSampleFragment()).commit();
    }

    private void initView() {
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        mTextViewTitle= (TextView) findViewById(R.id.tv_base_title);
        mTextViewTitle.setText("选择人员");
        mTextViewRightTitme= (TextView) findViewById(R.id.tv_base_title_right);
        mTextViewRightTitme.setVisibility(View.VISIBLE);
        mTextViewRightTitme.setText("确定");

        llBaseBack.setOnClickListener(this);
        mTextViewRightTitme.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right:
                if(selectCounselorList.size()!=0){
                    Intent mIntent = new Intent();
                    mIntent.putExtra("selectList",(Serializable)selectCounselorList);
                    setResult(resultCode, mIntent);// 设置结果，并进行传送
                    finish();
                }else{
                    ToastFactory.getLongToast(mContext,"请选择店员！");
                }

                break;
        }
    }
}
