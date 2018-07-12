package com.cesaas.android.counselor.order.shoptask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.BaseWebViewRequestBean;
import com.cesaas.android.counselor.order.base.ContactJsonObject;
import com.cesaas.android.counselor.order.custom.imageview.CircleImageView;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.listener.OnItemClickListener;
import com.cesaas.android.counselor.order.shoptask.bean.ContactJsonArrayBean;
import com.cesaas.android.counselor.order.shoptask.bean.CounselorListBean;
import com.cesaas.android.counselor.order.shoptask.bean.SelectCounselorListBean;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * Author FGB
 * Description 店员联系人Adapter
 * Created 2017/4/20 18:46
 * Version 1.zero
 */
public class ContactAdapter extends SwipeMenuAdapter<ContactAdapter.DefaultViewHolder>{

    private List<CounselorListBean>  mShopTaskListBeanList;
    private List<SelectCounselorListBean> selectCounselorListBeen=new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;
    static Context mContext;

    public ContactAdapter(List<CounselorListBean>  mShopTaskListBeanList, Context mContext){
        this.mShopTaskListBeanList=mShopTaskListBeanList;
        this.mContext=mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_contact, parent, false);
    }

    @Override
    public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new DefaultViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(final DefaultViewHolder holder, final int position) {
        holder.setData(mShopTaskListBeanList.get(position).getCOUNSELOR_NICKNAME(),mShopTaskListBeanList.get(position).getSHOP_NAME()
                ,mShopTaskListBeanList.get(position).getCOUNSELOR_ICON());
        holder.setOnItemClickListener(mOnItemClickListener);

        holder.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.toString();
                // 调整选定条目
                if (isChecked== true) {
                    SelectCounselorListBean bean=new SelectCounselorListBean();
                    bean.setSHOP_ID(mShopTaskListBeanList.get(position).getSHOP_ID());
                    bean.setCOUNSELOR_ID(mShopTaskListBeanList.get(position).getCOUNSELOR_ID());
                    bean.setCOUNSELOR_NICKNAME(mShopTaskListBeanList.get(position).getCOUNSELOR_NICKNAME());
                    bean.setCOUNSELOR_ICON(mShopTaskListBeanList.get(position).getCOUNSELOR_ICON());
                    bean.setPosition(position);
                    selectCounselorListBeen.add(bean);
                    EventBus.getDefault().post(selectCounselorListBeen);
                } else {
                    for (Iterator it = selectCounselorListBeen.iterator(); it.hasNext();){
                        SelectCounselorListBean value= (SelectCounselorListBean) it.next();
                        if(value.getPosition()==position){
                            it.remove();
                        }
                    }
                    EventBus.getDefault().post(selectCounselorListBeen);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShopTaskListBeanList == null ? 0 : mShopTaskListBeanList.size();
    }

    /**
     * DefaultViewHolder 静态类
     */
    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_contact_name,tv_contact_shop_name;
        CircleImageView ivw_contact_icon;
        OnItemClickListener mOnItemClickListener;
        CheckBox cbCheckBox;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_contact_name = (TextView) itemView.findViewById(R.id.tv_contact_name);
            tv_contact_shop_name= (TextView) itemView.findViewById(R.id.tv_contact_shop_name);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            ivw_contact_icon = (CircleImageView) itemView.findViewById(R.id.ivw_contact_icon);

        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        public void setData(String contactName,String contactShopName,String icon) {
            this.tv_contact_name.setText(contactName);
            this.tv_contact_shop_name.setText(contactShopName);
            if(icon!=null && !"".equals(icon)){
                Glide.with(mContext).load(icon).into(this.ivw_contact_icon);
            }else{
                this.ivw_contact_icon.setImageResource(R.mipmap.not_user_icon);
            }

        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
