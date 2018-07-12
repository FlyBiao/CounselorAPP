package com.cesaas.android.counselor.order.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.view.AllOrderStateView;
import com.cesaas.android.counselor.order.view.NoPayOrderStateView;
import com.cesaas.android.counselor.order.view.OkOutOrderStateView;
import com.cesaas.android.counselor.order.view.WaitInOrderStateView;
import com.cesaas.android.counselor.order.view.WaitOutOrderStateView;
import com.cesaas.android.counselor.order.viewpager.indicator.PagerIndicator;

public class TestFragment extends Fragment{

	 private ViewPager viewPager;
     private PagerIndicator indicator;
     private FragmentPagerAdapter mAdapter;
     private List<Fragment> mList;
     private List<String> mDatas;
     private int itemCount = 5;
     private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.pager_indicator, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.vp);
        indicator = (PagerIndicator)view.findViewById(R.id.indicator);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        mList = new ArrayList<Fragment>();
        
        mList.add(new AllOrderStateView());
        mList.add(new NoPayOrderStateView());
        mList.add(new WaitInOrderStateView());
        mList.add(new WaitOutOrderStateView());
        mList.add(new OkOutOrderStateView());
        
        mDatas = new ArrayList<String>();
//        for (int i = zero; i < itemCount; i++) {
        	mDatas.add("全部");
            mDatas.add("未付款");
            mDatas.add("未发货");
            mDatas.add("已付款");
            mDatas.add("已完成");
	        
//        }

        mAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };

        viewPager.setAdapter(mAdapter);
        //将viewpager与indicator绑定
        indicator.setDatas(mDatas);
        indicator.setViewPager(viewPager);
    }
}

