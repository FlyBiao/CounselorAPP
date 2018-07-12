package com.cesaas.android.counselor.order.ordermange.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cesaas.android.counselor.order.fragment.FragmentInfo;
import com.cesaas.android.counselor.order.member.fragment.AlreadyDistributionMemberFragment;
import com.cesaas.android.counselor.order.member.fragment.PublicMemberFragment;
import com.cesaas.android.counselor.order.view.FinshShopOrderFragment;
import com.cesaas.android.counselor.order.view.NoPayShopOrderFragment;
import com.cesaas.android.counselor.order.view.WaitInShopOrderFragment;
import com.cesaas.android.counselor.order.view.WaitOutShopOrderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/4/22 15:53
 * Version 1.zero
 */
public class ShopOrderTabLayoutAdapter extends FragmentStatePagerAdapter {

    private List<FragmentInfo> mFragments=new ArrayList<>(4);

    public ShopOrderTabLayoutAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        mFragments.add(new FragmentInfo("未支付",NoPayShopOrderFragment.class));
        mFragments.add(new FragmentInfo("未发货",WaitOutShopOrderFragment.class));
        mFragments.add(new FragmentInfo("已发货",WaitInShopOrderFragment.class));
        mFragments.add(new FragmentInfo("完成",FinshShopOrderFragment.class));

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
