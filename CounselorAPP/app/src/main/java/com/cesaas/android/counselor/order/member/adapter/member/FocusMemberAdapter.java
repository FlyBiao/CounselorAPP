package com.cesaas.android.counselor.order.member.adapter.member;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.member.bean.service.birth.BirthdayWishesServiceBean;
import com.cesaas.android.counselor.order.member.bean.service.member.FocusEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.net.service.ServiceRemarkNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailActivity;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.member.util.BottonDialogUtils;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员-all
 * Created at 2018/2/27 10:56
 * Version 1.0
 */

public class FocusMemberAdapter extends BaseQuickAdapter<MemberServiceListBean, BaseViewHolder> {

    private List<MemberServiceListBean> mData;
    private Activity mActivity;
    private Context mContext;

    private MyOnItemClickListener myOnItemClickListener;

    public FocusMemberAdapter(List<MemberServiceListBean> mData, Activity activity, Context context) {
        super( R.layout.item_focus_member,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MemberServiceListBean item) {
        MemberServiceListBean myLive=mData.get(helper.getAdapterPosition());

        if (myLive.isSelect()) {
            helper.setImageResource(R.id.check_box,R.mipmap.check);
        } else {
            helper.setImageResource(R.id.check_box,R.mipmap.check_not);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myOnItemClickListener.onItemClickListener(helper.getAdapterPosition(), mData);
            }
        });

        helper.setText(R.id.tv_member_name,item.getName());
        helper.setText(R.id.tv_member_level,item.getGrade());
        helper.setText(R.id.tv_member_mobile,item.getMobile());
        if(item.getBirthday()!=null && !"".equals(item.getBirthday())){
            helper.setText(R.id.tv_member_birthday,AbDateUtil.getDateYMDs(item.getBirthday()));
        }else{
            helper.setText(R.id.tv_member_birthday,"暂无生日");
        }

        if(item.getLastBuyDate()!=null && !"".equals(item.getLastBuyDate())){
            helper.setText(R.id.tv_last_buy_date,AbDateUtil.getDateYMDs(item.getLastBuyDate())+" "+AbDateUtil.formats(item.getLastBuyDate()));
        }else{
            helper.setText(R.id.tv_last_buy_date,"暂无消费");
        }

        if(item.getLastServerDate()!=null && !"".equals(item.getLastServerDate())){
            helper.setText(R.id.tv_last_service_date,AbDateUtil.getDateYMDs(item.getLastServerDate())+" "+AbDateUtil.formats(item.getLastServerDate()));
        }else{
            helper.setText(R.id.tv_last_service_date,"暂无服务");
        }

        if(item.getRemark()!=null && !"".equals(item.getRemark())){
            helper.setText(R.id.tv_remark,item.getRemark());
        }else{
            helper.setText(R.id.tv_remark,"暂无备注!");
        }
        if(item.getImage()!=null && !"".equals(item.getImage()) && !"NULL".equals(item.getImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_user_icon));
        }else{
            helper.setImageResource(R.id.ivw_user_icon,R.mipmap.ic_launcher);
        }

        if(item.getIsFocus()==1){
            helper.setImageResource(R.id.tv_is_focus,R.mipmap.focus);
        }else{
            helper.setImageResource(R.id.tv_is_focus,R.mipmap.focus_not);
        }

        if (item.getIsFaceBind()==1){
            helper.setImageResource(R.id.tv_is_face,R.mipmap.face);
        }

        helper.setOnClickListener(R.id.ivw_user_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转会员详情
                //跳转会员详情
                Bundle bundle=new Bundle();
                bundle.putInt("Id",item.getVipId());//暂时处理
                bundle.putInt("VipId",item.getVipId());
                bundle.putString("Name",item.getName());
                bundle.putString("Phone",item.getMobile());
                bundle.putString("Date",item.getBirthday());
                bundle.putString("Desc",item.getRemark());
                bundle.putString("Remark",item.getRemark());
                bundle.putString("Title",item.getName());
                bundle.putInt("Status",20);
                Skip.mNextFroData(mActivity,MemberReturnVisitDetailsActivity.class,bundle);
            }
        });
        helper.setOnClickListener(R.id.ll_member_audit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottonDialogUtils.showBottonDialog(mContext,mActivity, item,4);
            }
        });helper.setOnClickListener(R.id.tv_is_focus, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getIsFocus()==1){
                    //取消关注
                    FocusEventBusBean t=new FocusEventBusBean();
                    t.setVipId(item.getVipId());
                    t.setMemberType(4);
                    t.setFocusType(1);
                    EventBus.getDefault().post(t);
                }else{
                    //进行关注
                    FocusEventBusBean t=new FocusEventBusBean();
                    t.setVipId(item.getVipId());
                    t.setMemberType(4);
                    t.setFocusType(0);
                    EventBus.getDefault().post(t);
                }
            }
        });helper.setOnClickListener(R.id.ll_remark, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottonDialogUtils.setMemberRemarkDialog(mContext,mActivity,item,4);
            }
        });
    }

    public void notifyAdapter(List<MemberServiceListBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mData = myLiveList;
        } else {
            this.mData.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<MemberServiceListBean> getMyLiveList() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        return mData;
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public interface MyOnItemClickListener {
        void onItemClickListener(int pos,List<MemberServiceListBean> myLiveList);
    }
}
