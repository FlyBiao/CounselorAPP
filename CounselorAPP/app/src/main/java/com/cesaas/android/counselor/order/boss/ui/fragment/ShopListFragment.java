package com.cesaas.android.counselor.order.boss.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.adapter.ShopListAdapter;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.IndexableLayout;

/**
 * Author FGB
 * Description
 * Created at 2017/8/15 16:28
 * Version 1.0
 */

public class ShopListFragment extends BaseFragment {

    private View view;

    private IndexableLayout indexableLayout;
    private ShopListAdapter mAdapter;

    /**
     * 单例
     */
    public static ShopListFragment instance=null;
    public static ShopListFragment getInstance(){
        if(instance==null){
            instance=new ShopListFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_select_shop, container, false);

//        initView();
//        initData();

        return view;
    }

//    private void initData(){
//        // setAdapter
//        mAdapter = new ShopListAdapter(getContext());
//        indexableLayout.setAdapter(mAdapter);
////        // set Datas
//        mAdapter.setDatas(initDatas());
//        // set Material Design OverlayView
//        indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
////        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序
//        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
//
//    }
//
//    private List<ShopListBean> initDatas() {
//        List<ShopListBean> list = new ArrayList<>();
//        // 初始化数据
//        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.city_array));
//        List<String> mobileStrings =Arrays.asList(getResources().getStringArray(R.array.city_join));
//
//        for (int i = 0; i < contactStrings.size(); i++) {
//            ShopListBean contactEntity = new ShopListBean(contactStrings.get(i), mobileStrings.get(i));
//            list.add(contactEntity);
//        }
//
//        return list;
//    }
//
//    private void initView(){
//        indexableLayout = (IndexableLayout) view.findViewById(R.id.indexableLayout);
//        indexableLayout.setLayoutManager(new LinearLayoutManager(getContext()));
//    }


    @Override
    protected void lazyLoad() {

    }
}
