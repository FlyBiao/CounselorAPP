package com.cesaas.android.counselor.order.store.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.store.DisplayTaskApprovalActivity;
import com.cesaas.android.counselor.order.store.bean.CompleteImages;
import com.cesaas.android.counselor.order.store.bean.Images;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

import cn.hzw.graffiti.GraffitiActivity;

/**
 * 完成陈列Adapter
 * @author FGB
 *
 */
public class CompleteDisplayAdapter extends RecyclerView.Adapter<CompleteDisplayAdapter.ListHolderBean>{
	
	private Context mContext;
	private Activity mActivity;
    private View view;

    public static BitmapUtils bitmapUtils;
    private List<CompleteImages> iconA;

    public CompleteDisplayAdapter(Context context,Activity activity,List<CompleteImages> iconA) {
        this.mContext = context;
        this.iconA=iconA;
        this.mActivity=activity;
        bitmapUtils = BitmapHelp.getBitmapUtils(mContext.getApplicationContext());
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(3));
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
    }


    @Override
    public ListHolderBean onCreateViewHolder(ViewGroup parent, int viewType) {

        view=  View.inflate(parent.getContext(), R.layout.complete_display_item, null);
        return new ListHolderBean(view);
    }


    @Override
    public void onBindViewHolder(ListHolderBean holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return iconA.size();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	  Skip.mNext(mActivity, DisplayTaskApprovalActivity.class);

            Integer position = (Integer) v.getTag();
            final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
            ImageView imgView = getView(position);
            dialog.setView(imgView);
//            dialog.show();

            // 全屏显示的方法
            // final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            // ImageView imgView = getView();
            // dialog.setContentView(imgView);
            // dialog.show();

            // 点击图片消失
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
        }
    };

    private ImageView getView(int position) {
        ImageView imgView = new ImageView(mContext);
        imgView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        InputStream is = mContext.getResources().openRawResource(iconA.get(position));
//        Drawable drawable = BitmapDrawable.createFromStream(is, null);
//        imgView.setImageDrawable(drawable);
        bitmapUtils.display(imgView, iconA.get(position).getUrl(), App.mInstance().bitmapConfig);

        return imgView;
    }

	
	public class ListHolderBean extends RecyclerView.ViewHolder {
        ImageView icon;

        public ListHolderBean(View itemView) {
            super(itemView);

            itemView.setOnClickListener(onClickListener);

            icon = (ImageView) itemView.findViewById(R.id.complete_pic);
        }

        public void setData(int position){
            
            itemView.setTag(position);
//            icon.setImageResource(iconA.get(position));
            bitmapUtils.display(icon, iconA.get(position).getUrl(), App.mInstance().bitmapConfig);

        }
    }
}
