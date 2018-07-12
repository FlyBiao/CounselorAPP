package com.cesaas.android.counselor.order.statistics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.progress.CustomAffiliateCenterProgress;
import com.cesaas.android.counselor.order.custom.progress.CustomArcCircleProgress;
import com.cesaas.android.counselor.order.custom.progress.CustomBusinessCenterProgress;
import com.cesaas.android.counselor.order.custom.progress.CustomDirectSellingCenterProgress;
import com.cesaas.android.counselor.order.custom.progress.CustomSalesCenterProgress;
import com.cesaas.android.counselor.order.utils.Skip;

/**
 * 销量总览
 */
public class SalesOverviewActivity extends BasesActivity {

    private LinearLayout llBack;
    private TextView tvBaseTitle;

    private CustomArcCircleProgress arcCircleProgress;
    private CustomSalesCenterProgress salesCenterProgress;
    private CustomBusinessCenterProgress businessCenterProgress;
    private CustomAffiliateCenterProgress affiliateCenterProgress;
    private CustomDirectSellingCenterProgress directSellingCenterProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_overview);
        initView();
        initData();
    }

    private void initView() {
        arcCircleProgress= (CustomArcCircleProgress) findViewById(R.id.pro_view_nationwide);
        salesCenterProgress= (CustomSalesCenterProgress) findViewById(R.id.pro_view_sales);
        businessCenterProgress= (CustomBusinessCenterProgress) findViewById(R.id.pro_view_business);
        affiliateCenterProgress= (CustomAffiliateCenterProgress) findViewById(R.id.pro_view_affiliate);
        directSellingCenterProgress= (CustomDirectSellingCenterProgress) findViewById(R.id.pro_view_directselling);
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        setBack();
    }

    private void initData(){
        tvBaseTitle.setText("销量总览");
        arcCircleProgress.setCurrentCount(79);//全国当前进度
        salesCenterProgress.setCurrentCount(85);//销售中心
        businessCenterProgress.setCurrentCount(35);//商务中心
        affiliateCenterProgress.setCurrentCount(55);//加盟
        directSellingCenterProgress.setCurrentCount(65);//直营


    }

    /**
     * 设置返回上一个页面
     */
    public void setBack(){
        llBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }
}
