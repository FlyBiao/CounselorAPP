package com.cesaas.android.counselor.order.member.fragment;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.main.NewMainActivity;
import com.cesaas.android.counselor.order.base.BaseFragment;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.ResultVipGetOneBean;
import com.cesaas.android.counselor.order.member.bean.service.apply.ResultChangeMemberMobileBean;
import com.cesaas.android.counselor.order.member.net.service.ChangeMemberMobileNet;
import com.cesaas.android.counselor.order.member.service.MemberUpdateDetailActivity;
import com.cesaas.android.counselor.order.shoptask.bean.ResultShopTaskListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 更改会员手机号
 * Created at 2018/2/27 17:33
 * Version 1.record
 */

public class MemberMobileFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private ChangeMemberMobileNet net;

    private LinearLayout ll_sure_submit;
    private EditText et_mobile,et_remark;
    private TextView tv_mobile;

    private int VipId;
    private String VipMobile;

    /**
     * 单例
     */
    public static MemberMobileFragment newInstance() {
        MemberMobileFragment fragmentCommon = new MemberMobileFragment();
        return fragmentCommon;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_member_mobile;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        et_mobile=findView(R.id.et_mobile);
        tv_mobile=findView(R.id.tv_mobile);
        et_remark=findView(R.id.et_remark);
        ll_sure_submit=findView(R.id.ll_sure_submit);
        ll_sure_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(getContext()).mInitShow("温馨提示！", "是否确定修改该会员手机号？",
                    "我知道", "点错了", new MyAlertDialog.ConfirmListener() {
                        @Override
                        public void onClick(Dialog dialog) {
                            net=new ChangeMemberMobileNet(getContext());
                            net.setData(VipId,et_mobile.getText().toString(),et_remark.getText().toString());
                        }
                    }, null);
            }
        });
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {

    }

    public void onEventMainThread(ResultVipGetOneBean msg) {
        if(msg.IsSuccess!=false){
            if(msg.TModel!=null && !"".equals(msg.TModel)){
                VipId=msg.TModel.getVipId();
                VipMobile=msg.TModel.getMobile();
                tv_mobile.setText(VipMobile);
            }
        }
    }

    @Override
    public void processClick(View v) {
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onRefresh() {

    }
}
