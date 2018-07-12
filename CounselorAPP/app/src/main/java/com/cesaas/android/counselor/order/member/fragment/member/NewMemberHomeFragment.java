package com.cesaas.android.counselor.order.member.fragment.member;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.base.BaseTabBean;
import com.cesaas.android.counselor.order.member.bean.service.SearchVipEventBean;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description
 * Created at 2018/1/23 14:56
 * Version 1.0
 */

public class NewMemberHomeFragment extends BaseFragment {

    private LinearLayout ll_search_vip;
    private EditText et_search;
    private int TabType=10;

    /**
     * 单例
     */
    public static NewMemberHomeFragment newInstance() {
        NewMemberHomeFragment fragmentCommon = new NewMemberHomeFragment();
        return fragmentCommon;
    }

    public void onEventMainThread(BaseTabBean msg) {
        TabType=msg.getTabType();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_home_member;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        ll_search_vip=findView(R.id.ll_search_vip);
        et_search=findView(R.id.et_search);
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    SearchVipEventBean bean=new SearchVipEventBean();
                    bean.setType(TabType);
                    bean.setContent(et_search.getText().toString());
                    EventBus.getDefault().post(bean);
                }
                return false;
            }
        });
    }

    @Override
    public void initListener() {
        ll_search_vip.setOnClickListener(this);
    }

    @Override
    public void initData() {
        getFragmentManager().beginTransaction().add(R.id.new_member_frame_layout, new NewMemberSampleFragment()).commit();
    }


    @Override
    public void processClick(View v) {
        switch (v.getId()){
            case R.id.ll_search_vip:
                SearchVipEventBean bean=new SearchVipEventBean();
                bean.setType(TabType);
                bean.setContent(et_search.getText().toString());
                EventBus.getDefault().post(bean);
                break;
        }
    }

    @Override
    public void fetchData() {

    }
}
