package com.cesaas.android.counselor.order.member.adapter.member;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceListBean;
import com.cesaas.android.counselor.order.utils.AbDateUtil;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/3/20 11:02
 * Version 1.zero
 */
public class FaceGalleryRecyclerAdapter extends RecyclerView.Adapter<FaceGalleryRecyclerAdapter.ViewHolder> {

private List<FaceListBean> models;
private LayoutInflater mInflater;
    private Context mContext;

public FaceGalleryRecyclerAdapter(Context context, List<FaceListBean> models,Context ct){
        this.models=models;
    this.mContext=ct;
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
final View view=mInflater.inflate(R.layout.item_main_face,parent,false);
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
    if(models.get(position).getImageUrl()!=null && !"".equals(models.get(position).getImageUrl()) && !"NULL".equals(models.get(position).getImageUrl())){
        // 加载网络图片
        Glide.with(mContext).load(models.get(position).getImageUrl()).crossFade().into( holder.img);
    }else{
        holder.img.setImageResource(R.mipmap.ic_launcher);
    }
    if(models.get(position).getImageTime()!=null && !"".equals(models.get(position).getImageTime())){
        holder.date.setText(AbDateUtil.formats(models.get(position).getImageTime()));
    }else{
        holder.date.setText("暂无时间");
    }

    holder.itemView.setTag(position);
}

@Override
public int getItemCount() {
        return models.size();
        }

//自定义的ViewHolder，持有每个Item的的所有界面元素
public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView date;
    private ImageView img;
    public ViewHolder(View view){
        super(view);
        img= (ImageView) view.findViewById(R.id.img);
        date=(TextView)view.findViewById(R.id.date);
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
    void onItemClick(View view, int position);
}

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public OnRecyclerViewItemClickListener getOnRecyclerViewItemClickListener() {
        return onRecyclerViewItemClickListener;
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
