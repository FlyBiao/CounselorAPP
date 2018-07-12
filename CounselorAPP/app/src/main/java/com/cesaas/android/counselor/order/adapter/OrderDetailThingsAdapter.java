package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.ScanResultActivity;
import com.cesaas.android.counselor.order.bean.ResultDistributionOrderBean.DistributionOrderItemBean;
import com.cesaas.android.counselor.order.bean.ResultGetStylePictureBean;
import com.cesaas.android.counselor.order.bean.ResultOrderDetailBean.OrderItemBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by flybiao on 2016/7/5.
 */

public class OrderDetailThingsAdapter extends BaseAdapter {
	ImageView ivImg;
	TextView tvName;
	TextView tvAttr;
	TextView tvTypeCode;
	TextView tvQuantity;
	TextView tvCheck;

	List<OrderItemBean> list = new ArrayList<OrderItemBean>();
	Context context;
	static BitmapUtils bitmapUtils;
	private String Url;
	private JSONArray styleCodeArray;

	public OrderDetailThingsAdapter(Context ct, List<OrderItemBean> data) {
		this.context = ct;
		this.list = data;
		bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
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
		convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail_things, parent, false);
		ivImg = (ImageView) convertView.findViewById(R.id.iv_order_detail_things_img);
		tvName = (TextView) convertView.findViewById(R.id.tv_order_detail_things_name);
		tvAttr = (TextView) convertView.findViewById(R.id.tv_order_detail_things_attr);
		tvTypeCode = (TextView) convertView.findViewById(R.id.tv_order_detail_things_code);
		tvQuantity = (TextView) convertView.findViewById(R.id.tv_order_detail_things_quantity);
		tvCheck = (TextView) convertView.findViewById(R.id.tv_order_detail_things_check);

		ivImg.setScaleType(ImageView.ScaleType.FIT_XY);
		
		tvName.setText(list.get(position).Title);
		tvAttr.setText(list.get(position).Attr);
		tvTypeCode.setText(list.get(position).StyleCode);
		tvQuantity.setText("x" + list.get(position).Quantity);

		if(list.get(position).ImageUrl!=null){
			if(list.get(position).Title.contains("独立收银")){
				ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.no_shop_picture));
			}else{
				Glide.with(context).load(list.get(position).ImageUrl).error(R.drawable.no_picture).into(ivImg);
			}

		}else{
			//商品详情图片为null，就通过GetStylePicture接口根据商品款号获取商品图片
			styleCodeArray=new JSONArray();
			styleCodeArray.put(list.get(position).StyleCode);

			GetShopInfoStylePictureNet getShopInfoStylePictureNet=new GetShopInfoStylePictureNet(context);
			getShopInfoStylePictureNet.setData(styleCodeArray);
		}

		
		final String barcodeCode=list.get(position).BarcodeCode.toString();
		
		/**
		 * 扫描验货
		 */
		tvCheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Bundle bundle=new Bundle();
				bundle.putString("barcodeCode", barcodeCode);
				Skip.mNextFroData((Activity)context, ScanResultActivity.class,bundle);
			}
		});
		return convertView;
	}

	/**
	 * 根据商品款号获取商品图片
	 * @author fgb
	 *
	 */
	public class GetShopInfoStylePictureNet extends BaseNet {
		private static final String TAG = "GetStylePictureNet";

		public GetShopInfoStylePictureNet(Context context) {
			super(context, true);
			this.uri = "Marketing/Sw/Style/GetStylePicture";
		}

		public void setData(JSONArray styleCode) {
			try {
				data.put("StyleCode",styleCode);//商品款号集合
				data.put("UserTicket",
						AbPrefsUtil.getInstance().getString(Constant.SPF_TOKEN));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			mPostNet(); // 开始请求网络
		}

		@Override
		protected void mSuccess(String rJson) {
			super.mSuccess(rJson);
			Log.i(TAG, rJson);
			Gson gson = new Gson();
			ResultGetStylePictureBean lbean = gson.fromJson(rJson, ResultGetStylePictureBean.class);

			if (lbean.IsSuccess) {

				if(lbean.TModel!=null){
					for (int i = 0; i < lbean.TModel.size(); i++) {
						Url=lbean.TModel.get(i).Url;
					}
				}

				if(Url!=null){
					Glide.with(context).load(Url).error(R.drawable.no_picture).into(ivImg);
				}
				else{
					ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.no_picture));
				}

			} else {
				ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
			}
		}

		@Override
		protected void mFail(HttpException e, String err) {
			super.mFail(e, err);
			Log.i(TAG, "err===" + e + "********=err==" + err);
		}
	}
}
