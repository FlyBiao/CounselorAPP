package com.cesaas.android.counselor.order.boss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.boss.bean.SelectShopListBean;
import com.cesaas.android.counselor.order.boss.bean.ShopListBean;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.rong.eventbus.EventBus;
import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Author FGB
 * Description
 * Created at 2017/8/15 10:23
 * Version 1.0
 */

public class ShopListAdapter extends IndexableAdapter<ShopListBean> {

    private LayoutInflater mInflater;
    private List<SelectShopListBean> listBeen=new ArrayList<>();

    public ShopListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_shop, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_shop, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, final ShopListBean entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tvName.setText(entity.getNick());
        vh.tvMobile.setText(entity.getMobile());

        //显示checkBox
        vh.checkbox.setChecked(entity.getBo());

        vh.cbCheckBox.setChecked(entity.getBo());
        vh.cbCheckBox.setOnClickListener(null);

        vh.cbCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.toString();
                // 调整选定条目
                if(isChecked==true){
                    SelectShopListBean  shopListBean=new SelectShopListBean();
                    shopListBean.setShopName(entity.getNick());
                    shopListBean.setJoinShop(entity.getMobile());
                    shopListBean.setShopId(entity.getShopId());
                    shopListBean.setPos(entity.getShopId());
                    listBeen.add(shopListBean);
                    EventBus.getDefault().post(listBeen);

                }else{
                    for (Iterator it = listBeen.iterator(); it.hasNext();){
                        SelectShopListBean value= (SelectShopListBean) it.next();
                        if(value.getPos()==entity.getShopId()){
                            it.remove();
                        }
                    }
                    if(listBeen.size()!=0){
                        EventBus.getDefault().post(listBeen);
                    }
                }
            }
        });

    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        public IndexVH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tvName, tvMobile;
        CheckBox cbCheckBox,checkbox;

        public ContentVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvMobile = (TextView) itemView.findViewById(R.id.tv_shop_join);
            cbCheckBox= (CheckBox) itemView.findViewById(R.id.cbCheckBox);
            checkbox= (CheckBox) itemView.findViewById(R.id.checkbox);
        }
    }
}
