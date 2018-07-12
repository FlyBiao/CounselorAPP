package com.cesaas.android.counselor.order.member.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionBean;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionDetailBean;
import com.cesaas.android.counselor.order.member.net.CancelBindVipNet;
import com.cesaas.android.counselor.order.report.net.DeleteTryWearStyleDateNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.flybiao.adapterlibrary.widget.MyImageViewWidget;
import com.flybiao.materialdialog.MaterialDialog;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 会员分配Adapter
 * Created 2017/4/11 18:33
 * Version 1.zero
 */
public class MemberDistributionDetailAdapter extends SwipeMenuAdapter<MemberDistributionDetailAdapter.DefaultViewHolder>{

    //
    SparseBooleanArray mSelectedPositions =new SparseBooleanArray();

    private List<MemberDistributionDetailBean> mMemberDistributionBeanList;
    private OnItemClickListener mOnItemClickListener;
    public static Context mContext;
    public static Activity mActivity;
    private MaterialDialog mMaterialDialog;

    public MemberDistributionDetailAdapter(List<MemberDistributionDetailBean> mMemberDistributionBeanList, Context mContext){
        this.mContext=mContext;
        this.mMemberDistributionBeanList=mMemberDistributionBeanList;
    }

    //用来为Adapter 里的数据item设置标记，默认每个条目为false，选中的话就设置为true
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member_distridution_detail, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    //绑定界面，设置监听
    @Override
    public void onBindViewHolder(DefaultViewHolder holder, final int position) {

        holder.setData(mMemberDistributionBeanList.get(position).getNickName()
                ,mMemberDistributionBeanList.get(position).getVipName()
        ,mMemberDistributionBeanList.get(position).getPoint()
        ,mMemberDistributionBeanList.get(position).getCardName()
        ,mMemberDistributionBeanList.get(position).getBirthday()
        ,mMemberDistributionBeanList.get(position).getImage()
        ,mMemberDistributionBeanList.get(position).getLastBuy()
        ,mMemberDistributionBeanList.get(position).getMobile());
        holder.setOnItemClickListener(mOnItemClickListener);

        holder.tv_cancel_bind_vip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog=new MaterialDialog(mContext);
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("温馨提示！")
                            .setMessage(
                                    "确定解绑该会员吗？")
                            //mMaterialDialog.setBackgroundResource(R.drawable.background);
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    CancelBindVipNet mBindVipNet=new CancelBindVipNet(mContext);
                                    mBindVipNet.setData(mMemberDistributionBeanList.get(position).getVipId(),mMemberDistributionBeanList.get(position).getCounselorId());
                                }
                            })
                            .setNegativeButton("取消",
                                    new View.OnClickListener() {
                                        @Override public void onClick(View v) {
                                            mMaterialDialog.dismiss();
                                            ToastFactory.getLongToast(mContext,"已取解绑！");
                                        }
                                    })
                            .setCanceledOnTouchOutside(true).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMemberDistributionBeanList == null ? 0 : mMemberDistributionBeanList.size();
    }


    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_member_name,tv_member_grade,tv_member_point,tv_meber_birthday,tv_last_buy,tv_cancel_bind_vip,tv_member_mobile;
        MyImageViewWidget ivw_member_icon;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_member_name = (TextView) itemView.findViewById(R.id.tv_member_name);
            tv_member_grade= (TextView) itemView.findViewById(R.id.tv_member_grade);
            tv_member_point= (TextView) itemView.findViewById(R.id.tv_member_point);
            tv_meber_birthday= (TextView) itemView.findViewById(R.id.tv_meber_birthday);
            ivw_member_icon= (MyImageViewWidget) itemView.findViewById(R.id.ivw_member_icon);
            tv_last_buy= (TextView) itemView.findViewById(R.id.tv_last_buy);
            tv_cancel_bind_vip= (TextView) itemView.findViewById(R.id.tv_cancel_bind_vip);
            tv_member_mobile= (TextView) itemView.findViewById(R.id.tv_member_mobile);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String name,String vipName,int point,String grade,String birthday,String imgUrl,String lastBuy,String mobile) {
            if(vipName!=null &&!"".equals(vipName)){
                this.tv_member_name.setText(vipName);
            }else{
                this.tv_member_name.setText(name);
            }

            if(imgUrl!=null && !"".equals(imgUrl)){
                Glide.with(mContext).load(imgUrl).into(this.ivw_member_icon);
            }else {
                this.ivw_member_icon.setImageResource(R.mipmap.ic_launcher);
            }
            if(lastBuy!=null && !"".equals(lastBuy)){
                this.tv_last_buy.setText(AbDateUtil.formats(lastBuy));
            }else{
                this.tv_last_buy.setText("暂无消费");
            }

            if(mobile!=null && !"".equals(mobile)){
                this.tv_member_mobile.setText(mobile);
            }else{
                this.tv_member_mobile.setText("暂无手机号");
            }

            if(birthday!=null){
                this.tv_meber_birthday.setText("生日 "+ AbDateUtil.toDateYMD(birthday));
            }else{
                this.tv_meber_birthday.setText("暂无生日");
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
