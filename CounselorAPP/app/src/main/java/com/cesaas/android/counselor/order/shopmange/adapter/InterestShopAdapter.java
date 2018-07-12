package com.cesaas.android.counselor.order.shopmange.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shopmange.bean.AllShopBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultAttentionBean;
import com.cesaas.android.counselor.order.shopmange.bean.ResultSetLikeShopBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.JsonUtils;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.exception.HttpException;
import com.wx.goodview.GoodView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Author FGB
 * Description 感兴趣商品Adapter
 * Created 2017/4/27 17:57
 * Version 1.0
 */
public class InterestShopAdapter extends SwipeMenuAdapter<InterestShopAdapter.DefaultViewHolder>{

    private List<AllShopBean> mAllShopBeanList;
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;
    private GoodView mGoodView;
    private int isAttention=0;

    public InterestShopAdapter(List<AllShopBean> mAllShopBeanList,Context mContext){
        this.mAllShopBeanList=mAllShopBeanList;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_all_shop, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {

        holder.setData(mAllShopBeanList.get(position).getTitle(),mAllShopBeanList.get(position).getNo()
                ,mAllShopBeanList.get(position).getImageUrl(),mAllShopBeanList.get(position).getTitle(),mAllShopBeanList.get(position).getIsAttention());
        holder.setOnItemClickListener(mOnItemClickListener);

        holder.iv_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodView=new GoodView(mContext);
                if(mAllShopBeanList.get(position).getIsAttention()==1){//取消关注
                    isAttention=0;
                    mGoodView.setTextInfo("已取消关注", Color.parseColor("#ff941A"), 14);
                    mGoodView.show(holder.iv_attention);
                    holder.iv_attention.setImageResource(R.mipmap.bookmark);

                    AttentionShopNet attentionShopNet=new AttentionShopNet(mContext);
                    attentionShopNet.setData(mAllShopBeanList.get(position).getId(),0);
                }else{//添加关注
                    isAttention=1;
                    mGoodView.setTextInfo("关注成功", Color.parseColor("#ff941A"), 14);
                    mGoodView.show(holder.iv_attention);
                    holder.iv_attention.setImageResource(R.mipmap.bookmark_checked);

                    AttentionShopNet attentionShopNet=new AttentionShopNet(mContext);
                    attentionShopNet.setData(mAllShopBeanList.get(position).getId(),1);
                }
            }
        });

        holder.tv_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoodView=new GoodView(mContext);
                mGoodView.setTextInfo("点赞成功", Color.parseColor("#ea3d27"), 14);
                mGoodView.show(holder.tv_collection);
                holder.tv_collection.setImageResource(R.mipmap.zan);
                SetLikeShopNet mSetLikeShopNet=new SetLikeShopNet(mContext);
                mSetLikeShopNet.setData("1",mAllShopBeanList.get(position).getId()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAllShopBeanList == null ? 0 : mAllShopBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_shop_title,tv_shop_style_code,tv_shop_desc;
        ImageView iv_shop_img,tv_collection,iv_attention;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_shop_title = (TextView) itemView.findViewById(R.id.tv_shop_title);
            tv_shop_style_code= (TextView) itemView.findViewById(R.id.tv_shop_style_code);
            iv_shop_img= (ImageView) itemView.findViewById(R.id.iv_shop_img);
            tv_shop_desc= (TextView) itemView.findViewById(R.id.tv_shop_desc);
            iv_attention= (ImageView) itemView.findViewById(R.id.iv_attention);
            tv_collection= (ImageView) itemView.findViewById(R.id.tv_collection);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String title,String StyleCode,String ImageUrl,String Desc,int IsAttention) {
            if(IsAttention==1){
                iv_attention.setImageResource(R.mipmap.bookmark_checked);
            }else{
                iv_attention.setImageResource(R.mipmap.bookmark);
            }
            this.tv_shop_title.setText(title);
            this.tv_shop_style_code.setText(StyleCode);
            this.tv_shop_desc.setText(Desc);
            Glide.with(mContext).load(ImageUrl).placeholder(R.mipmap.loading).into(iv_shop_img);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    /**
     * Author FGB
     * Description 设置喜欢商品Net
     * Created 2017/4/28 17:07
     * Version 1.0
     */
    public class SetLikeShopNet extends BaseNet {
        public SetLikeShopNet(Context context) {
            super(context, true);
            this.uri="Marketing/Sw/Collocation/SetLike";
        }

        public void setData(String IsLike,String Id){
            try{
                data.put("",IsLike);
                data.put("Id",Id);
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            Log.i("dddddimage",rJson);
            ResultSetLikeShopBean bean= JsonUtils.fromJson(rJson,ResultSetLikeShopBean.class);
            if(bean.IsSuccess==true){

            }else{
                ToastFactory.getLongToast(mContext,"操作失败！"+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }

    /**
     * Author FGB
     * Description 关注/取消关注shop
     * Created 2017/4/27 21:02
     * Version 1.0
     */
    public class AttentionShopNet extends BaseNet {
        public AttentionShopNet(Context context) {
            super(context, true);
            this.uri="Marketing/Sw/Style/Attention";
        }

        public void setData(int Id,int Status){
            try{
                data.put("Id",Id);
                data.put("Status",Status);//1关注  0：取消
                data.put("UserTicket", AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
            }catch (Exception e){
                e.printStackTrace();
            }
            mPostNet();
        }

        @Override
        protected void mSuccess(String rJson) {
            super.mSuccess(rJson);
            ResultAttentionBean bean= JsonUtils.fromJson(rJson,ResultAttentionBean.class);
            if(bean.IsSuccess==true){//关注操作成功
//                notifyDataSetChanged();
            }else{//关注操作失败
                ToastFactory.getLongToast(mContext,"关注操作失败！"+bean.Message);
            }
        }

        @Override
        protected void mFail(HttpException e, String err) {
            super.mFail(e, err);
        }
    }
}
