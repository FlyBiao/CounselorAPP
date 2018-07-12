package com.cesaas.android.counselor.order.signin.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.signin.bean.SigninRecordBean;
import com.cesaas.android.counselor.order.signin.bean.SigninTypeBean;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.nostra13.universalimageloader.utils.L;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 签到记录Aadapter
 * Created 2017/3/17 17:11
 * Version 1.zero
 */
public class SigninRecordAdapter extends SwipeMenuAdapter<SigninRecordAdapter.DefaultViewHolder> {

    private List<SigninRecordBean> signinRecordBeen=new ArrayList<>();
    private  List<String> testList;
    private  Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public SigninRecordAdapter(List<SigninRecordBean> signinRecordBeen,Context mContext) {
        this.signinRecordBeen = signinRecordBeen;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return signinRecordBeen == null ? 0 : signinRecordBeen.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signin_record, parent, false);
    }

    @Override
    public SigninRecordAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView,mContext);
    }

    @Override
    public void onBindViewHolder(SigninRecordAdapter.DefaultViewHolder holder, int position) {

        testList=new ArrayList<>();
//            for(int i=zero;i<signinRecordBeen.get(position).getIMAGE_PATH().size();i++){
//                testList.add(signinRecordBeen.get(position).getIMAGE_PATH().get(i));
//            }

        holder.setData(
                signinRecordBeen.get(position).getCREATE_TIME(),
                signinRecordBeen.get(position).getSIGN_TYPE(),
                signinRecordBeen.get(position).getADDRES_BY_NOW(),
                signinRecordBeen.get(position).getSIGN_TYPE_NAME(),
                signinRecordBeen.get(position).getREMARK(),
                testList,
                mContext);
        holder.setOnItemClickListener(mOnItemClickListener);

    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle,tvSigninSatus,tvSigninAddress,tvRemark;
        LinearLayout ll_hide_content;
        RecyclerView mRecyclerView;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView ,Context mContext) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSigninSatus= (TextView) itemView.findViewById(R.id.tv_signin_status);
            tvSigninAddress= (TextView) itemView.findViewById(R.id.tv_signin_address);
            tvRemark= (TextView) itemView.findViewById(R.id.remark);
            ll_hide_content= (LinearLayout) itemView.findViewById(R.id.ll_hide_content);
            ll_hide_content.setVisibility(View.GONE);

            mRecyclerView= (RecyclerView) itemView.findViewById(R.id.rv_signin_image);//固定高度
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(mContext);//创建布局管理器
            linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);//设置横向
            mRecyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器

            tvSigninAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ll_hide_content.getVisibility()==View.GONE){//展开
                        ll_hide_content.setVisibility(View.VISIBLE);
                    }
                    else if(ll_hide_content.getVisibility()==View.VISIBLE){//隐藏
                        ll_hide_content.setVisibility(View.GONE);
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title, int status, String address, String signinTypeName, String remark, final List<String> list, final Context mContext) {
            this.tvTitle.setText(title);
            this.tvSigninAddress.setText(address);
            this.tvSigninSatus.setText(signinTypeName);
            if(remark!=null){
                this.tvRemark.setText(remark);
            }else{
                this.tvRemark.setText("暂无签到备注！");
            }

            if(status==1){//上班
                this.tvSigninSatus .setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.et_signin_bule_bg));
            }else if(status==2){//会议
                this.tvSigninSatus .setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.et_signin_org_bg));
            }else if(status==3){//培训
                this.tvSigninSatus .setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.et_signin_green_bg));
            }else if(status==4){//下班
                this.tvSigninSatus .setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.et_signin_mediumturquoise_bg));
            }else{
                this.tvSigninSatus .setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.et_signin_bule_bg));
            }

            SigninRecordGalleryAdapter recordGalleryAdapter=new SigninRecordGalleryAdapter(mContext,list);
            mRecyclerView.setAdapter(recordGalleryAdapter);
            recordGalleryAdapter.setOnRecyclerViewItemClickListener(new SigninRecordGalleryAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ToastFactory.getLongToast(mContext,"GET："+list.get(position));
                }
            });

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
