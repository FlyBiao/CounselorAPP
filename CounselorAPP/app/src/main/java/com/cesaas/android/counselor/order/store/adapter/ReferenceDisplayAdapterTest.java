package com.cesaas.android.counselor.order.store.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.store.bean.Images;
import com.cesaas.android.counselor.order.store.bean.Shows;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;

import java.io.InputStream;
import java.util.List;

/**
 * 参考陈列Adapter
 * @author FGB
 *
 */
public class ReferenceDisplayAdapterTest extends RecyclerView.Adapter<ReferenceDisplayAdapterTest.ListHolder>{

	private Context mContext;
    public static BitmapUtils bitmapUtils;

    private List<Shows> iconA;//参考图片

    public ReferenceDisplayAdapterTest(Context context, List<Shows> iconA) {
        this.mContext = context;
        this.iconA=iconA;
        bitmapUtils = BitmapHelp.getBitmapUtils(mContext.getApplicationContext());
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(mContext).scaleDown(3));
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view =  View.inflate(parent.getContext(), R.layout.reference_display_item, null);
        return new ListHolder(view);
    }


    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return iconA.size();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer position = (Integer) v.getTag();
            final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
            ImageView imgView = getView(position);
            dialog.setView(imgView);
            dialog.show();

            // 全屏显示的方法
//             final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//             ImageView imgView = getView(position);
//             dialog.setContentView(imgView);
//             dialog.show();

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

//        InputStream is = mContext.getResources().openRawResource(iconB.get(position));
//        Drawable drawable = BitmapDrawable.createFromStream(is, null);
        bitmapUtils.display(imgView, iconA.get(position).getUrl(), App.mInstance().bitmapConfig);
//        imgView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_shop_picture));

        return imgView;
    }

	
	public class ListHolder extends RecyclerView.ViewHolder {
		ImageView icon;

        public ListHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(onClickListener);

            icon = (ImageView) itemView.findViewById(R.id.reference_pic);
        }

        public void setData(int position){
            
            itemView.setTag(position);
//            icon.setImageResource(iconA.get(position));
            bitmapUtils.display(icon, iconA.get(position).getUrl(), App.mInstance().bitmapConfig);

        }
    }
}
