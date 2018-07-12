package com.cesaas.android.order.adapter;

import android.widget.ImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetStylePictureBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean.OrderItemBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.order.bean.UnReceiveOrderBean;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description
 * Created at 2017/7/24 18:32
 * Version 1.0
 */

public class ReceiveOrderThingsAdapter extends BaseAdapter {
    ImageView ivImg;
    TextView tvName;
    TextView tvAttr;
    TextView tvTypeCode;
    TextView tvQuantity;
    TextView tv_order_price;

    List<UnReceiveOrderBean.OrderItem> list = new ArrayList<>();
    Context context;
    private String Url;
    private JSONArray styleCodeArray;

    public ReceiveOrderThingsAdapter(Context ct, List<UnReceiveOrderBean.OrderItem> data) {
        this.context = ct;
        this.list = data;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_order_things, parent, false);
        ivImg = (ImageView) convertView.findViewById(R.id.iv_img);
        tvName = (TextView) convertView.findViewById(R.id.tv_order_name);
        tvAttr = (TextView) convertView.findViewById(R.id.tv_order_attr);
        tvTypeCode = (TextView) convertView.findViewById(R.id.tv_type_code);
        tvQuantity = (TextView) convertView.findViewById(R.id.tv_quantity);
        tv_order_price=(TextView) convertView.findViewById(R.id.tv_order_price);

        tvName.setText(list.get(position).getSkuValue1());
        tvAttr.setText(list.get(position).getSkuValue2());
        tvTypeCode.setText(""+list.get(position).getBarcodeNo());
        tvQuantity.setText("x" + list.get(position).getQuantity());
        tv_order_price.setText("￥"+list.get(position).getPayMent());

        ivImg.setImageResource(R.drawable.no_shop_picture);

        return convertView;
    }


}
