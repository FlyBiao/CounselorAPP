package com.cesaas.android.counselor.order.member.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cesaas.android.counselor.order.fragment.FragmentInfo;
import com.cesaas.android.counselor.order.member.fragment.service.MemberBirthdayFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberMobileFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description TabLayoutViewPagerAdapter
 * Created 2017/4/2 zero:41
 * Version 1.zero
 */
public class TabLayoutViewPagerUpdateDetailAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments=new ArrayList<>(4);

    public TabLayoutViewPagerUpdateDetailAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        mFragments.add(new FragmentInfo("更改手机号",MemberMobileFragment.class));
        mFragments.add(new FragmentInfo("更改生日",MemberBirthdayFragment.class));
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
