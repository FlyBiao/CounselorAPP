package com.cesaas.android.order.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.ordermange.ShopOrderActivity;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.order.adapter.NewOrderTabLayoutAdapter;

/**
 * 订单路由首页
 */
public class NewOrderActivity extends BasesActivity {

    private TextView tv_shop_order;
    private LinearLayout ll_order_back;
    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        initView();
        ShopOrder();
        mBack();
        initData();
    }

    public void initData(){
        PagerAdapter adapter= new NewOrderTabLayoutAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 门店订单
     */
    private void ShopOrder() {
        tv_shop_order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Skip.mNext(mActivity, ShopOrderActivity.class);
            }
        });
    }

    /**
     * 返回
     */
    public void mBack(){
        ll_order_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }

    /**
     * 初始化视图控件
     */
    public void initView(){
        tv_shop_order=(TextView) findViewById(R.id.tv_shop_order);
        ll_order_back=(LinearLayout) findViewById(R.id.ll_order_back);
        mTabLayout= (TabLayout) findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) findViewById(R.id.view_pager);
    }
}
