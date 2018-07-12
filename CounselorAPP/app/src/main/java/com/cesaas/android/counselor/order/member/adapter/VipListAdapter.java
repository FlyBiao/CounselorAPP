package com.cesaas.android.counselor.order.member.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.Fans;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.bean.SelectVipListBean;
import com.cesaas.android.counselor.order.member.bean.VipListBean;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 会员列表数据适配器
 * Created at 2017/5/16 14:42
 * Version 1.0
 */

public class VipListAdapter extends SwipeMenuAdapter<VipListAdapter.DefaultViewHolder>{

    private List<VipListBean> mVipListBeen;
    private List<SelectVipListBean> selectVipListBeen=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    public static Context mContext;
    public static Activity mActivity;

    public VipListAdapter(List<VipListBean> mVipListBeen, Context mContext){
        this.mContext=mContext;
        this.mVipListBeen=mVipListBeen;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_list, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {
        holder.setData(mVipListBeen.get(position).getNickName()
                ,mVipListBeen.get(position).getMobile()
                ,mVipListBeen.get(position).getPoint()+""
                ,mVipListBeen.get(position).getBirthDay()
                ,mVipListBeen.get(position).getCardName()
                ,mVipListBeen.get(position).getImage());
        holder.setOnItemClickListener(mOnItemClickListener);

        //设置选框监听
        holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked== true) {//选中

                    SelectVipListBean bean=new SelectVipListBean();
                    bean.setVipId(mVipListBeen.get(position).getVipId());
                    bean.setNickName(mVipListBeen.get(position).getNickName());
                    bean.setImage(mVipListBeen.get(position).getImage());
                    bean.setCardName(mVipListBeen.get(position).getCardName());
                    bean.setMobile(mVipListBeen.get(position).getMobile());
                    bean.setCounselorId(mVipListBeen.get(position).getCounselorId());
                    bean.setPoint(mVipListBeen.get(position).getPoint());
                    bean.setBirthDay(mVipListBeen.get(position).getBirthDay());
                    bean.setPosition(position);
                    selectVipListBeen.add(bean);
                    EventBus.getDefault().post(selectVipListBeen);
                }else{//未选中
                    for (Iterator it = selectVipListBeen.iterator(); it.hasNext();){
                        SelectVipListBean value= (SelectVipListBean) it.next();
                        if(value.getPosition()==position){
                            it.remove();
                        }
                    }
                    EventBus.getDefault().post(selectVipListBeen);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mVipListBeen == null ? 0 : mVipListBeen.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_member_name,tv_member_grade,tv_member_mobile,tv_member_point,tv_member_birthday;
        CheckBox cbCheckBox;
        MyImageViewWidget ivw_member_icon;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_member_name = (TextView) itemView.findViewById(R.id.tv_member_name);
            tv_member_grade= (TextView) itemView.findViewById(R.id.tv_member_grade);
            tv_member_mobile= (TextView) itemView.findViewById(R.id.tv_member_mobile);
            tv_member_point= (TextView) itemView.findViewById(R.id.tv_member_point);
            tv_member_birthday= (TextView) itemView.findViewById(R.id.tv_member_birthday);
            ivw_member_icon= (MyImageViewWidget) itemView.findViewById(R.id.ivw_member_icon);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String mobile,String point,String birthday,String grade,String imgUrl) {
            if(name!=null || name!=""){
                this.tv_member_name.setText(name);
            }else{
                this.tv_member_name.setText("找不到该会员");
            }

            if(birthday!=null){
                this.tv_member_birthday.setText("生日 "+birthday);
            }else{
                this.tv_member_birthday.setVisibility(View.GONE);
            }

            if(imgUrl!=null){
                Glide.with(mContext).load(imgUrl).into(this.ivw_member_icon);
            }else{
                Glide.with(mContext).load(imgUrl).error(R.drawable.no_picture).into(this.ivw_member_icon);
            }

            if(mobile!=null){
                this.tv_member_mobile.setText(mobile);
            }else{
                this.tv_member_mobile.setText("暂无手机号");
            }

            this.tv_member_grade.setText(grade);
            this.tv_member_point.setText(""+point);

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
