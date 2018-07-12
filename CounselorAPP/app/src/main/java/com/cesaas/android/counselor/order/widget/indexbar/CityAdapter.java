package com.cesaas.android.counselor.order.widget.indexbar;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;

/**
 * Created by FGB .
 * Date: 16/08/28
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private Context mContext;
    private List<CityBean> mDatas;
    private LayoutInflater mInflater;
    private int checkNum; // 记录选中的条目数量 
    
  //用来控制checkBox的选中状态
    private static HashMap<Integer, Boolean> isSelected;
    //对isSelected进行封装
    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }
    //设置是否选择
    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
    	CityAdapter.isSelected = isSelected;
    }

    public CityAdapter(Context mContext, List<CityBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
        isSelected=new HashMap<Integer,Boolean>();
        //往集合中添加位置
        initdata();
    }
    
    private void initdata() {
        for (int i = 0; i < mDatas.size(); i++) {
            isSelected.put(i, false);//默认为false状态
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_city, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CityBean cityBean = mDatas.get(position);
        holder.tvCity.setText(cityBean.FANS_NICKNAME);
     // 根据isSelected来设置checkbox的选中状况 
        holder.item_cb.setChecked(getIsSelected().get(position));
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	// 改变CheckBox的状态 
                holder.item_cb.toggle(); 
            	CityAdapter.getIsSelected().put(position, holder.item_cb.isChecked()); 
                 // 调整选定条目 
                 if (holder.item_cb.isChecked() == true) { 
                     checkNum++; 
//                     Toast.makeText(mContext, "dd="+mDatas.get(position).FANS_NICKNAME, zero).show();
                 } else { 
                     checkNum--; 
                 } 
            	
                Toast.makeText(mContext, "pos:"+checkNum, Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        CheckBox item_cb;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            item_cb=(CheckBox) itemView.findViewById(R.id.cbCheckBox);
        }
    }
}
