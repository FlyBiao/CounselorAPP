package com.cesaas.android.counselor.order.member.adapter.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.bean.manager.VipFansNumsListByShopIdBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 店长管理店员
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class ManagerAssisTantAdapter extends BaseQuickAdapter<VipFansNumsListByShopIdBean, BaseViewHolder> {

    private List<VipFansNumsListByShopIdBean> mData;
    private Activity mActivity;
    private Context mContext;
    private boolean isShow=false;

    public ManagerAssisTantAdapter(List<VipFansNumsListByShopIdBean> mData, Activity activity, Context context) {
        super( R.layout.item_manager_assistant,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final VipFansNumsListByShopIdBean item) {
        helper.setText(R.id.tv_title,item.getCounselorName());
        helper.setText(R.id.tv_fans_sum,String.valueOf(item.getFansNums()));
        helper.setText(R.id.tv_vip_sum,String.valueOf(item.getVipNums()));
        if(item.getImage()!=null && !"".equals(item.getImage()) && !"NULL".equals(item.getImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_user_icon));
        }else{
            helper.setImageResource(R.id.ivw_user_icon,R.mipmap.ic_launcher);
        }
    }
}
