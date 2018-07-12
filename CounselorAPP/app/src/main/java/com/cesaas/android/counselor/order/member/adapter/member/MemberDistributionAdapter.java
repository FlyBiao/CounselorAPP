package com.cesaas.android.counselor.order.member.adapter.member;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.BindVipIdBean;
import com.cesaas.android.counselor.order.member.bean.PublicMemberBean;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员-分配
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class MemberDistributionAdapter extends BaseQuickAdapter<PublicMemberBean, BaseViewHolder> {

    private List<PublicMemberBean> mData;
    private List<BindVipIdBean> selectBindVipIdBeen=new ArrayList<>();
    private Activity mActivity;
    private Context mContext;

    public MemberDistributionAdapter(List<PublicMemberBean> mData, Activity activity, Context context) {
        super( R.layout.item_public_member,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final PublicMemberBean item) {
        helper.setText(R.id.tv_member_grade,item.getCardName());

        if(item.getBirthday()!=null && !"".equals(item.getBirthday())){
            helper.setText(R.id.tv_member_birthday,"生日:"+AbDateUtil.getDateMDs(item.getBirthday()));
        }else{
            helper.setText(R.id.tv_member_birthday,"暂无生日");
        }

        if(item.getMobile()!=null && !"".equals(item.getMobile())){
            helper.setText(R.id.tv_member_mobile,item.getMobile());
        }else{
            helper.setText(R.id.tv_member_mobile,"暂无手机号");
        }

        if(item.getVipName()!=null && !"".equals(item.getVipName())){
            helper.setText(R.id.tv_member_name,item.getVipName());
        }else{
            helper.setText(R.id.tv_member_name,item.getNickName());
        }

        if(item.getRegisterDate()!=null && !"".equals(item.getRegisterDate())){
            helper.setText(R.id.tv_member_point,AbDateUtil.getDateYMDs(item.getRegisterDate())+"  "+AbDateUtil.formats(item.getRegisterDate()));
        }else{
            helper.setText(R.id.tv_member_point,"暂无注册时间");
        }
        if(item.getImage()!=null && !"".equals(item.getImage()) && !"NULL".equals(item.getImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_member_icon));
        }else{
            helper.setImageResource(R.id.ivw_member_icon,R.mipmap.ic_launcher);
        }

        helper.setOnCheckedChangeListener(R.id.cbCheckBox, new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.toString();
                // 调整选定条目
                if (isChecked== true) {
                    BindVipIdBean bindVipIdBean=new BindVipIdBean();
                    bindVipIdBean.setVipId(mData.get(helper.getAdapterPosition()).getVipId());
                    bindVipIdBean.setPos(helper.getAdapterPosition());
                    selectBindVipIdBeen.add(bindVipIdBean);
                    if(selectBindVipIdBeen.size()!=0){
                        EventBus.getDefault().post(selectBindVipIdBeen);
                    }
                } else {
                    for (Iterator it = selectBindVipIdBeen.iterator(); it.hasNext();){
                        BindVipIdBean value= (BindVipIdBean) it.next();
                        if(value.getPos()==helper.getAdapterPosition()){
                            it.remove();
                        }
                    }
                    if(selectBindVipIdBeen.size()!=0){
                        EventBus.getDefault().post(selectBindVipIdBeen);
                    }
                }
            }
        });

    }
}
