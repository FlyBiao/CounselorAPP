package com.cesaas.android.counselor.order.shoptask.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cesaas.android.counselor.order.fragment.FragmentInfo;
import com.cesaas.android.counselor.order.shoptask.fragment.CompleteHandleShopTaskFragment;
import com.cesaas.android.counselor.order.shoptask.fragment.NotHandleShopTaskFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description TablayoutViewPagerTaskAdapter
 * Created at 2017/5/11 10:37
 * Version 1.0
 */

public class TablayoutViewPagerTaskAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments=new ArrayList<>(4);

    public TablayoutViewPagerTaskAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        mFragments.add(new FragmentInfo("未完成",NotHandleShopTaskFragment.class));
        mFragments.add(new FragmentInfo("已完成",CompleteHandleShopTaskFragment.class));
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return (Fragment) mFragments.get(position).getFragment().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
