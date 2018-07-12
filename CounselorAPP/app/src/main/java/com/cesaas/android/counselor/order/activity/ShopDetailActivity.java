package com.cesaas.android.counselor.order.activity;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultGetStylePictureBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.imagepicker.imageloader.GlideImageLoader;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;


@ContentView(R.layout.shop_detail_layout)
public class ShopDetailActivity extends BasesActivity{
	
	@ViewInject(R.id.iv_shop_img)
	private ImageView iv_shop_img;
	@ViewInject(R.id.tv_shop_name)
	private TextView tv_shop_name;
	@ViewInject(R.id.tv_shop_style_code)
	private TextView tv_shop_style_code;
	@ViewInject(R.id.tv_shop_barcode_code)
	private TextView tv_shop_barcode_code;
	@ViewInject(R.id.tv_shop_price)
	private TextView tv_shop_price;
	@ViewInject(R.id.tv_shop_attr)
	private TextView tv_shop_attr;
	@ViewInject(R.id.tv_check_shop)
	private TextView tv_check_shop;
	@ViewInject(R.id.il_order_detail_back)
	private LinearLayout il_order_detail_back;
	
	public JSONArray styleCodeArray;
	private String Url;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		Bundle bundle = getIntent().getExtras();
		if(bundle!=null){

			tv_shop_name.setText(bundle.getString("Title"));
			tv_shop_style_code.setText(bundle.getString("StyleCode"));
			tv_shop_barcode_code.setText(bundle.getString("BarcodeCode"));
			tv_shop_price.setText(bundle.getDouble("Price")+"");
			tv_shop_attr.setText(bundle.getString("Attr"));
			
			if(bundle.getString("ImageUrl")!=null){
				Glide.with(mContext).load(bundle.getString("ImageUrl")).error(R.drawable.no_picture).into(iv_shop_img);
//				bitmapUtils.display(iv_shop_img, bundle.getString("ImageUrl"), App.mInstance().bitmapConfig);
			}else{//商品详情图片为null，就通过GetStylePicture接口根据商品款号获取商品图片
//
				styleCodeArray=new JSONArray();
				styleCodeArray.put(bundle.getString("StyleCode"));
				
				GetShopInfoStylePictureNet getShopInfoStylePictureNet=new GetShopInfoStylePictureNet(mContext);
				getShopInfoStylePictureNet.setData(styleCodeArray);
			}
		}
		
		checkOrder();
		mBack();
	}
	
	private void mBack() {
		il_order_detail_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
				
			}
		});
	}

	/**
	 * 验货
	 */
	public void checkOrder(){
		tv_check_shop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle=new Bundle();
				bundle.putString("barcodeCode", bundle.getString("BarcodeCode"));
				Skip.mNextFroData(mActivity, ScanResultActivity.class, bundle);
			}
		});
	}
	
	/**
	 * 根据商品款号获取商品图片
	 * @author fgb
	 *
	 */
	public class GetShopInfoStylePictureNet extends BaseNet{
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
					bitmapUtils.display(iv_shop_img, Url, App.mInstance().bitmapConfig);
				}
				else{
					iv_shop_img.setImageDrawable(getResources().getDrawable(R.drawable.no_picture));
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
