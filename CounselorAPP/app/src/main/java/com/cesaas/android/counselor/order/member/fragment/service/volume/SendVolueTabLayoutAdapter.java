package com.cesaas.android.counselor.order.member.fragment.service.volume;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cesaas.android.counselor.order.fragment.FragmentInfo;
import com.cesaas.android.order.route.fragment.RouteBackOrderFragment;
import com.cesaas.android.order.route.fragment.RouteReceiveOrderFragment;
import com.cesaas.android.order.route.fragment.RouteWaitInOrderFragment;
import com.cesaas.android.order.route.fragment.RouteWaitOutOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 10:13
 * Version 1.0
 */

public class SendVolueTabLayoutAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments=new ArrayList<>();

    public SendVolueTabLayoutAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        mFragments.add(new FragmentInfo("优惠券",CouponsFragment.class));
        mFragments.add(new FragmentInfo("礼品券",GiftFragment.class));
        mFragments.add(new FragmentInfo("优惠码",PromoCodeFragment.class));
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

