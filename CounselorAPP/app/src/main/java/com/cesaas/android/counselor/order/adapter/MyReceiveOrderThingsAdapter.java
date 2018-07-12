package com.cesaas.android.counselor.order.adapter;

import java.util.ArrayList;
import java.util.List;

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
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.bean.ResultGetStylePictureBean;
import com.cesaas.android.counselor.order.cache.CacheManager;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.GetShopInfoStylePictureNet;
import com.cesaas.android.counselor.order.statistics.bean.CacheJsonBean;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.exception.HttpException;

import org.json.JSONArray;
import org.json.JSONException;

import io.rong.eventbus.EventBus;

/**
 * Created by flybiao on 2016/7/4.
 */

public class MyReceiveOrderThingsAdapter extends BaseAdapter {
	ImageView ivImg;
	TextView tvName;
	TextView tvAttr;
	TextView tvTypeCode;
	TextView tvQuantity;
	TextView tv_order_price;

	List<GetByCounselorBeanItemBean> list;
	Context context;
	static BitmapUtils bitmapUtils;
	private String Url;
	private JSONArray styleCodeArray;//商品款号Array
	List<ResultGetStylePictureBean.ImageArray> pictureList;
	private CacheManager mCacheManager;

	public MyReceiveOrderThingsAdapter(Context ct, List<GetByCounselorBeanItemBean> data) {
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
		convertView = LayoutInflater.from(context).inflate(R.layout.item_receive_order_things, parent, false);
		ivImg = (ImageView) convertView.findViewById(R.id.iv_reveice_img);
		tvName = (TextView) convertView.findViewById(R.id.tv_reveice_order_name);
		tvAttr = (TextView) convertView.findViewById(R.id.tv_reveice_order_attr);
		tvTypeCode = (TextView) convertView.findViewById(R.id.tv_reveice_type_code);
		tvQuantity = (TextView) convertView.findViewById(R.id.tv_reveice_quantity);
		tv_order_price=(TextView) convertView.findViewById(R.id.tv_order_price);
		
		tvName.setText(list.get(position).Title);
		tvAttr.setText(list.get(position).Attr);
		tvTypeCode.setText("条码"+list.get(position).BarcodeCode);
		tvQuantity.setText("x" + list.get(position).Quantity);
		tv_order_price.setText("￥"+list.get(position).Price);

		if(list.get(position).ImageUrl!=null){
			if(list.get(position).Title.contains("独立收银")){
				ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.no_shop_picture));
			}else{
				Glide.with(context).load(list.get(position).ImageUrl).error(R.drawable.no_picture).into(ivImg);
			}
		}
		//else{
//			mCacheManager=CacheManager.getInstance(context);
//			String strJson=mCacheManager.readCache("testJson");
//			if(strJson!=null){
//				Gson gson = new Gson();
//				ResultGetStylePictureBean lbean = gson.fromJson(strJson, ResultGetStylePictureBean.class);
//				pictureList=new ArrayList<>();
//				pictureList.addAll(lbean.TModel);
//				initPicture(pictureList);
//
//			}else{
				//商品详情图片为null，就通过GetStylePicture接口根据商品款号获取商品图片
//				styleCodeArray=new JSONArray();
//				styleCodeArray.put(list.get(position).StyleCode);
//				GetShopInfoStylePictureNet getShopInfoStylePictureNet=new GetShopInfoStylePictureNet(context);
//				getShopInfoStylePictureNet.setData(styleCodeArray);
//			}
//		}
		return convertView;
	}

	/**
	 * 初始化商品款号图片
	 * @param pictureList
     */
	private void initPicture(List<ResultGetStylePictureBean.ImageArray> pictureList){
		for (int i = 0; i < pictureList.size(); i++) {
			Url=pictureList.get(i).Url;
		}
		if(Url!=null){
			Glide.with(context).load(Url).error(R.drawable.no_picture).into(ivImg);
		}
		else{
			ivImg.setImageDrawable(context.getResources().getDrawable(R.drawable.no_picture));
		}
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
			mCacheManager=CacheManager.getInstance(context);
			mCacheManager.writeCache("testJson",rJson);
			Gson gson = new Gson();
			ResultGetStylePictureBean lbean = gson.fromJson(rJson, ResultGetStylePictureBean.class);
			if(lbean.IsSuccess==true && lbean.TModel!=null){
				pictureList=new ArrayList<>();
				pictureList.addAll(lbean.TModel);
				initPicture(pictureList);
			}
		}

		@Override
		protected void mFail(HttpException e, String err) {
			super.mFail(e, err);
			Log.i(TAG, "err===" + e + "********=err==" + err);
		}
	}
	
}
