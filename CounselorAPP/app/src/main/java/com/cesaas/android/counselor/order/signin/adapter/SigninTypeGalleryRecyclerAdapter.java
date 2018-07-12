package com.cesaas.android.counselor.order.signin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.listener.OnRecyclerViewItemClickListener;
import com.cesaas.android.counselor.order.salestalk.bean.GetCategoryBean;
import com.cesaas.android.counselor.order.signin.bean.SigninTypeBean;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/3/20 11:02
 * Version 1.zero
 */
public class SigninTypeGalleryRecyclerAdapter extends RecyclerView.Adapter<SigninTypeGalleryRecyclerAdapter.ViewHolder> {

private List<GetCategoryBean> models;
private LayoutInflater mInflater;

public SigninTypeGalleryRecyclerAdapter(Context context,List<GetCategoryBean> models){
        this.models=models;
        mInflater=LayoutInflater.from(context);
        }

/**
 * 创建Item View  然后使用ViewHolder来进行承载
 * @param parent
 * @param viewType
 * @return
 */
@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
final View view=mInflater.inflate(R.layout.item_signin_type_menu,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        if(onRecyclerViewItemClickListener!=null){
        onRecyclerViewItemClickListener.onItemClick(view,(int)view.getTag());
        }
        }
        });

        return viewHolder;
        }

/**
 * 进行绑定数据
 * @param holder
 * @param position
 */
@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item_tv.setText(models.get(position).Content);
        holder.itemView.setTag(position);
        }

@Override
public int getItemCount() {
        return models.size();
        }

//自定义的ViewHolder，持有每个Item的的所有界面元素
public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView item_tv;
    public ViewHolder(View view){
        super(view);
        item_tv=(TextView)view.findViewById(R.id.item_tv);
    }
}

/**
 * 类似ListView的 onItemClickListener接口
 */
public interface OnRecyclerViewItemClickListener{
    /**
     * Item View发生点击回调的方法
     * @param view   点击的View
     * @param position  具体Item View的索引
     */
    void onItemClick(View view,int position);
}

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
