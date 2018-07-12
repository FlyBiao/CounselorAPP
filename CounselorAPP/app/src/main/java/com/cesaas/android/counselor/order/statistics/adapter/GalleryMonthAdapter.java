package com.cesaas.android.counselor.order.statistics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.statistics.bean.GalleryMonthBean;

import java.util.List;

/**
 * Author FGB
 * Description 各月报表Adapter
 * Created 2017/3/14 11:26
 * Version 1.zero
 */
public class GalleryMonthAdapter extends
        RecyclerView.Adapter<GalleryMonthAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<GalleryMonthBean> mDatas;

    public GalleryMonthAdapter(Context context, List<GalleryMonthBean> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        TextView id_index_gallery_month_volume;
        TextView id_index_volume_progress;
        TextView id_index_gallery_month;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.item_month_rv,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.id_index_gallery_month_volume = (TextView) view.findViewById(R.id.id_index_gallery_month_volume);
        viewHolder.id_index_gallery_month= (TextView) view.findViewById(R.id.id_index_gallery_month);
        viewHolder.id_index_volume_progress= (TextView) view.findViewById(R.id.id_index_volume_progress);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        viewHolder.id_index_volume_progress.setHeight(R.dimen.size_20);
        viewHolder.id_index_gallery_month_volume.setText(mDatas.get(i).getVolume()+"");
        viewHolder.id_index_gallery_month.setText(mDatas.get(i).getMonth()+"月");
    }

}
