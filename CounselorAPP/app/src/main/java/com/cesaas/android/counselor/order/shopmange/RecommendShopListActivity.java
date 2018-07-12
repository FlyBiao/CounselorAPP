package com.cesaas.android.counselor.order.shopmange;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.main.fragment.HomeSampleFragment;
import com.cesaas.android.counselor.order.activity.main.fragment.RecommendSampleFragment;
import com.cesaas.android.counselor.order.custom.tablayout.TabLayout;
import com.cesaas.android.counselor.order.custom.tablayout.bean.Fragment;
import com.cesaas.android.counselor.order.custom.tablayout.bean.TabItem;
import com.cesaas.android.counselor.order.fragment.HomeFragment;
import com.cesaas.android.counselor.order.member.MemberFragment;
import com.cesaas.android.counselor.order.shop.fragment.ShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.AllShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.CollectionShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.InterestShopFragment;
import com.cesaas.android.counselor.order.shopmange.fragment.MyAttentionShopFragment;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.workbench.fragment.WorkbenchFragment;

import java.util.ArrayList;

/**
 * 推荐商品List
 */
public class RecommendShopListActivity extends BasesActivity implements TabLayout.OnTabClickListener{

    private LinearLayout llBaseBack;
    private TextView tvBaseTitle;
    private ImageView iv_bottom_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_shop_list);

        initView();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, new RecommendSampleFragment()).commit();
    }

    private void initView() {
        llBaseBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle= (TextView) findViewById(R.id.tv_base_title);
        tvBaseTitle.setText("商品推荐");

        iv_bottom_query= (ImageView) findViewById(R.id.iv_bottom_query);

        llBaseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        iv_bottom_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastFactory.getLongToast(mContext,"这里搜索什么？");
            }
        });
    }


    @Override
    public void onTabClick(TabItem tabItem) {
        tvBaseTitle.setText(tabItem.lableResId);
    }

}
