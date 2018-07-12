package com.cesaas.android.counselor.order.cashier.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.widget.gridview.BaseViewHolder;

/**
 * ================================================
 * 作    者：FGB
 * 描    述：gridview的Adapter
 * 创建日期：2016/8/9 21:37
 * 版    本：1.zero
 * 修订历史：
 * ================================================
 */
public class CashierGridAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = { "1", "2", "3", "4", "5", "6",
            "7", "8", "9", };

    public CashierGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_count_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);

        tv.setText(img_text[position]);
        return convertView;
    }
}
