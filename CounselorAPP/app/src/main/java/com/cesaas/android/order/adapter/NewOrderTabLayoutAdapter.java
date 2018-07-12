package com.cesaas.android.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cesaas.android.counselor.order.fragment.FragmentInfo;
import com.cesaas.android.counselor.order.fragment.ReceiveOrderFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberLabelFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberOrderFragment;
import com.cesaas.android.counselor.order.member.fragment.MemberServiceRecordFragment;
import com.cesaas.android.counselor.order.view.NoPayOrderStateView;
import com.cesaas.android.counselor.order.view.OkOutOrderStateView;
import com.cesaas.android.counselor.order.view.WaitInOrderStateView;
import com.cesaas.android.counselor.order.view.WaitOutOrderStateView;
import com.cesaas.android.order.route.fragment.RouteBackOrderFragment;
import com.cesaas.android.order.route.fragment.RouteReceiveOrderFragment;
import com.cesaas.android.order.route.fragment.RouteWaitInOrderFragment;
import com.cesaas.android.order.route.fragment.RouteWaitOutOrderFragment;
import com.cesaas.android.order.ui.fragment.NoPayNewOrderStateView;
import com.cesaas.android.order.ui.fragment.OkOutNewOrderStateView;
import com.cesaas.android.order.ui.fragment.ReceiveNewOrderFragment;
import com.cesaas.android.order.ui.fragment.WaitInNewOrderStateView;
import com.cesaas.android.order.ui.fragment.WaitOutNewOrderStateView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 10:13
 * Version 1.0
 */

public class NewOrderTabLayoutAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments=new ArrayList<>();

    public NewOrderTabLayoutAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        mFragments.add(new FragmentInfo("待接单",RouteReceiveOrderFragment.class));
//        mFragments.add(new FragmentInfo("待发货",WaitOutNewOrderStateView.class));
        mFragments.add(new FragmentInfo("待发货",RouteWaitOutOrderFragment.class));
//        mFragments.add(new FragmentInfo("已发货",WaitInNewOrderStateView.class));
        mFragments.add(new FragmentInfo("已发货",RouteWaitInOrderFragment.class));
        mFragments.add(new FragmentInfo("已退货",RouteBackOrderFragment.class));
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

