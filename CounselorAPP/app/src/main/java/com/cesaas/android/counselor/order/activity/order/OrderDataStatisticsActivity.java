package com.cesaas.android.counselor.order.activity.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.user.VipDataScreenActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * Author FGB
 * Description 订单数据统计页面
 * Created 2017/3/2 10:30
 * Version 1.zero
 */
public class OrderDataStatisticsActivity extends BasesActivity implements View.OnClickListener {

    private LinearLayout llBack;
    private TextView tvBaseTitle,tvBaseRightTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_data_statistics);
        initView();
        setData();
    }

    private void setData() {
        tvBaseTitle.setText("订单统计报表");
        tvBaseRightTitle.setVisibility(View.VISIBLE);
        tvBaseRightTitle.setText("筛选");

    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        tvBaseRightTitle= (TextView) findViewById(R.id.tv_base_title_right);


        //设置视图控件监听
        llBack.setOnClickListener(this);
        tvBaseRightTitle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_base_title_back://返回
                Skip.mBack(mActivity);
                break;
            case R.id.tv_base_title_right://筛选
                Skip.mNext(mActivity,OrderDataScreenActivity.class);
                break;
        }

    }
}
