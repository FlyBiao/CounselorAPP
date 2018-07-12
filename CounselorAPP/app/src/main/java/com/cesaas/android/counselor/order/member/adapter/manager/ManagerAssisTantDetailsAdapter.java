package com.cesaas.android.counselor.order.member.adapter.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.manager.AllVipListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Author FGB
 * Description 店长管理店员
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class ManagerAssisTantDetailsAdapter extends BaseQuickAdapter<AllVipListBean, BaseViewHolder> {

    private List<AllVipListBean> mData;
    private Activity mActivity;
    private Context mContext;
    private boolean isShow=false;

    public ManagerAssisTantDetailsAdapter(List<AllVipListBean> mData, Activity activity, Context context) {
        super( R.layout.item_manager_assistant_details,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AllVipListBean item) {
        helper.setText(R.id.tv_name,item.getName());

        if (item.getShopName()!=null && !"".equals(item.getShopName())){
            helper.setText(R.id.tv_shop_name,item.getShopName());
        }else{
            helper.setText(R.id.tv_shop_name,"暂无店铺");
        }
        if (item.getDevelopShopName()!=null && !"".equals(item.getDevelopShopName())){
            helper.setText(R.id.tv_develop_shop_name,item.getDevelopShopName());
        }else{
            helper.setText(R.id.tv_develop_shop_name,"暂无店铺");
        }
        if (item.getCrDate()!=null && !"".equals(item.getCrDate())){
            helper.setText(R.id.tv_create_date, AbDateUtil.getDateYMDs(item.getCrDate()));
        }else{
            helper.setText(R.id.tv_create_date,"暂无注册时间");
        }
        if (item.getLastServerDate()!=null && !"".equals(item.getLastServerDate())){
            helper.setText(R.id.tv_last_server_date, AbDateUtil.getDateYMDs(item.getLastServerDate()));
        }else{
            helper.setText(R.id.tv_last_server_date,"暂无注册时间");
        }
        if (item.getCounselorName()!=null && !"".equals(item.getCounselorName())){
            helper.setText(R.id.tv_counselor_name, item.getCounselorName());
        }else{
            helper.setText(R.id.tv_counselor_name,"暂无注册时间");
        }

        if(item.getGrade()!=null && !"".equals(item.getGrade())){
            helper.setText(R.id.tv_grade,item.getGrade());
        }else{
            helper.setText(R.id.tv_grade,"暂无等级");
        }
        if(item.getImage()!=null && !"".equals(item.getImage()) && !"NULL".equals(item.getImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_user_icon));
        }else{
            helper.setImageResource(R.id.ivw_user_icon,R.mipmap.ic_launcher);
        }

        helper.setOnClickListener(R.id.ll_show, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow==false){
                    isShow=true;
                    helper.setVisible(R.id.ll_vip_details,true);
                }else{
                    isShow=false;
                    helper.setVisible(R.id.ll_vip_details,false);
                }
            }
        });
    }
}
