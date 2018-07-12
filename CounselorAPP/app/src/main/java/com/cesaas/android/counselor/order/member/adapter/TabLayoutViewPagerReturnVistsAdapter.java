package com.cesaas.android.counselor.order.member.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cesaas.android.counselor.order.fragment.FragmentInfo;
import com.cesaas.android.counselor.order.member.fragment.MemberLabelFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberLabelsFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberOrderView;
import com.cesaas.android.counselor.order.member.fragment.MemberOrdersFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberServiceRecordFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberServiceRecordsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description TabLayoutViewPagerAdapter
 * Created 2017/4/2 zero:41
 * Version 1.zero
 */
public class TabLayoutViewPagerReturnVistsAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments=new ArrayList<>();

    public TabLayoutViewPagerReturnVistsAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        mFragments.add(new FragmentInfo("标签",MemberLabelsFragment.class));
        mFragments.add(new FragmentInfo("服务记录",MemberServiceRecordsFragment.class));
//        mFragments.add(new FragmentInfo("订单",MemberOrderFragment.class));
        mFragments.add(new FragmentInfo("订单",MemberOrdersFragment.class));
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
