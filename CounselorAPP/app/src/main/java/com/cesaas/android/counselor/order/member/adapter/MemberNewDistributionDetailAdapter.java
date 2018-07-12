package com.cesaas.android.counselor.order.member.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.MemberDistributionDetailBean;
import com.cesaas.android.counselor.order.member.net.CancelBindVipNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flybiao.materialdialog.MaterialDialog;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2018/4/20 9:30
 * Version 1.0
 */

public class MemberNewDistributionDetailAdapter extends BaseQuickAdapter<MemberDistributionDetailBean, BaseViewHolder> {

    private List<MemberDistributionDetailBean> mData;
    private Activity mActivity;
    private Context mContext;
    private MaterialDialog mMaterialDialog;

    public MemberNewDistributionDetailAdapter(List<MemberDistributionDetailBean> mData, Activity activity, Context context) {
        super( R.layout.item_member_distridution_detail,mData);
        this.mData=mData;
        this.mActivity=activity;
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MemberDistributionDetailBean item) {
        if(item.getVipName()!=null && !"".equals(item.getVipName())){
            helper.setText(R.id.tv_member_name,item.getVipName());
        }else{
            helper.setText(R.id.tv_member_name,item.getNickName());
        }

        if(item.getLastBuy()!=null && !"".equals(item.getLastBuy())){
            helper.setText(R.id.tv_last_buy,AbDateUtil.formats(item.getLastBuy()));
        }else{
            helper.setText(R.id.tv_last_buy,"暂无消费");
        }

        if(item.getMobile()!=null && !"".equals(item.getMobile())){
            helper.setText(R.id.tv_member_mobile,item.getMobile());
        }else{
            helper.setText(R.id.tv_member_mobile,"暂无手机号");
        }

        if(item.getBirthday()!=null && !"".equals(item.getBirthday())){
            helper.setText(R.id.tv_meber_birthday,AbDateUtil.toDateYMD(item.getBirthday()));
        }else{
            helper.setText(R.id.tv_meber_birthday,"暂无生日");
        }

        helper.setText(R.id.tv_member_grade,item.getCardName());
        helper.setText(R.id.tv_member_point,item.getPoint()+"");

        if(item.getImage()!=null && !"".equals(item.getImage()) && !"NULL".equals(item.getImage())){
            // 加载网络图片
            Glide.with(mContext).load(item.getImage()).crossFade().into((ImageView) helper.getView(R.id.ivw_member_icon));
        }else{
            helper.setImageResource(R.id.ivw_member_icon,R.mipmap.ic_launcher);
        }


        helper.setOnClickListener(R.id.tv_cancel_bind_vip, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog=new MaterialDialog(mContext);
                if (mMaterialDialog != null) {
                    mMaterialDialog.setTitle("温馨提示！")
                            .setMessage("确定解绑该会员吗？")
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override public void onClick(View v) {
                                    mMaterialDialog.dismiss();
                                    CancelBindVipNet mBindVipNet=new CancelBindVipNet(mContext);
                                    mBindVipNet.setData(item.getVipId(),item.getCounselorId());
                                }
                            })
                            .setNegativeButton("取消",
                                    new View.OnClickListener() {
                                        @Override public void onClick(View v) {
                                            mMaterialDialog.dismiss();
                                            ToastFactory.getLongToast(mContext,"已取消解绑！");
                                        }
                                    })
                            .setCanceledOnTouchOutside(true).show();
                }
            }
        });
    }
}
