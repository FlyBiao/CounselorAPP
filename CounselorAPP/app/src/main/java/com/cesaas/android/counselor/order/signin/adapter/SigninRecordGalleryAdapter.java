package com.cesaas.android.counselor.order.signin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.signin.bean.SigninTypeBean;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/3/20 11:02
 * Version 1.zero
 */
public class SigninRecordGalleryAdapter extends RecyclerView.Adapter<SigninRecordGalleryAdapter.ViewHolder> {

    private List<String> models;
    private LayoutInflater mInflater;
    static BitmapUtils bitmapUtils;

    public SigninRecordGalleryAdapter(Context context, List<String> models){
        this.models=models;
        mInflater=LayoutInflater.from(context);
        bitmapUtils = BitmapHelp.getBitmapUtils(context.getApplicationContext());
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(context).scaleDown(3));
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
    }

    /**
     * 创建Item View  然后使用ViewHolder来进行承载
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view=mInflater.inflate(R.layout.item_signin_img,parent,false);
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
//        if(models.size()!=zero){
            bitmapUtils.display(holder.iv_signin_img, models.get(position), App.mInstance().bitmapConfig);
            holder.tv_not_img.setVisibility(View.GONE);
//            Log.i("dtest","有");
//
//        }else{
//            Log.i("dtest","无");
//            holder.tv_not_img.setVisibility(View.VISIBLE);
//            holder.iv_signin_img.setVisibility(View.GONE);
//        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_signin_img;
        private TextView tv_not_img;

        public ViewHolder(View view){
            super(view);
            iv_signin_img=(ImageView) view.findViewById(R.id.iv_signin_img);
            tv_not_img= (TextView) view.findViewById(R.id.tv_not_img);
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
