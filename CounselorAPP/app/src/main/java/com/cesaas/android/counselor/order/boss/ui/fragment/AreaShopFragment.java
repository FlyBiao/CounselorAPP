package com.cesaas.android.counselor.order.boss.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;

/**
 * Author FGB
 * Description
 * Created at 2017/8/15 16:29
 * Version 1.0
 */

public class AreaShopFragment extends BaseFragment {

    private View view;

    /**
     * 单例
     */
    public static AreaShopFragment instance=null;
    public static AreaShopFragment getInstance(){
        if(instance==null){
            instance=new AreaShopFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_area_shop, container, false);

        return view;
    }

    @Override
    protected void lazyLoad() {

    }
}
