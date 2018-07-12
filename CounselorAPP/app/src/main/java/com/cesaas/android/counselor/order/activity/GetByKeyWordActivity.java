package com.cesaas.android.counselor.order.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.base.GetByKeyWordBean;
import com.cesaas.android.counselor.order.bean.ResultGetByKeyWordBean;
import com.cesaas.android.counselor.order.bean.ResultGetPriceListBean;
import com.cesaas.android.counselor.order.bean.ResultGetPriceListBean.GetPriceListBean;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.GetByKeyWordNet;
import com.cesaas.android.counselor.order.net.GetPriceListNet;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 * 获取推荐商品列表
 * @author fgb
 *
 */
@ContentView(R.layout.activity_getby_keywoed_layout)
public class GetByKeyWordActivity extends BasesActivity implements OnClickListener{

	@ViewInject(R.id.lv_keyWord)
	private ListView lv_keyWord;
	@ViewInject(R.id.iv_back_getkey)
	private ImageView iv_back_getkey;
	@ViewInject(R.id.tv_shop_sum)
	private TextView tv_shop_sum;
	
	private GetPriceListNet getPriceListNet;
	private GetByKeyWordNet byKeyWordNet;
	private GetByKeyWordAdapter byKeyWordAdapter;
	private ArrayList<GetByKeyWordBean> shopList;
	private ArrayList<GetPriceListBean> shopPriceList;
	
	private JSONArray idArray=new JSONArray();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		iv_back_getkey.setOnClickListener(this);
		
		byKeyWordNet=new GetByKeyWordNet(mContext);
		byKeyWordNet.setData();
		
		setItemClickListener();
	}
	
	/**
	 * 设置ListView Item 点击事件
	 */
	public void setItemClickListener(){
		final int RESULT_CODE=101;
		lv_keyWord.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent();
		        intent.putExtra("ShopStyleId", shopList.get(position).ID);
		        intent.putExtra("ImageUrl", shopList.get(position).IMAGEURL);
		        intent.putExtra("Title", shopList.get(position).TITLE);
		        intent.putExtra("Url", shopList.get(position).URL);
		        intent.putExtra("NO", shopList.get(position).NO);
		        intent.putExtra("TOTALSTOCK",shopList. get(position).TOTALSTOCK);
		        intent.putExtra("Price", ""+shopList.get(position).SELLPRICE);
		        
		        setResult(RESULT_CODE, intent);
		        finish();
			}
		});
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		if (shopList.size() >0) {
			byKeyWordAdapter = new GetByKeyWordAdapter(mContext,shopList,shopPriceList);
			lv_keyWord.setAdapter(byKeyWordAdapter);
		}
	}
	
	/**
	 * 接收推荐商品消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetByKeyWordBean msg) {
		if(msg.IsSuccess==true && msg.TModel!=null){//成功
			shopList=new ArrayList<GetByKeyWordBean>();
			shopList.addAll(msg.TModel);
			
			tv_shop_sum.setText(shopList.size()+"");
			
			//获取商品ID
			for (int i = 0; i < shopList.size(); i++) {
				idArray.put(shopList.get(i).ID);
			}
			//调用商品价格列表接口获取商品价格
			getPriceListNet=new GetPriceListNet(mContext);
			getPriceListNet.setData(idArray);
			
		}else{//失败
			
			ToastFactory.getLongToast(mContext, "获取数据失败！"+msg.Message);
		}
	}
	
	/**
	 * 接收推荐商品价格消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultGetPriceListBean msg) {
		if(msg.IsSuccess==true){//成功
			shopPriceList=new ArrayList<GetPriceListBean>();
			shopPriceList.addAll(msg.TModel);
			
			for (int i = 0; i < shopList.size(); i++) {
				for (int j = 0; j < shopPriceList.size(); j++) {
					if(shopList.get(i).ID.contains(shopPriceList.get(j).ShopStyleId)){
						shopList.get(i).SELLPRICE=shopPriceList.get(j).Price;
					}
				}
			}
			
			//调用初始化数据方法
			initData();
			
		}else{//失败
			
			ToastFactory.getLongToast(mContext, "获取数据失败！"+msg.Message);
		}
	}
	/**
	 * 上回上一个页面
	 */
	@Override
	public void onClick(View v) {
		Skip.mBack(mActivity);
	}
	
	
	/**
	 * 商品列表数据适配器
	 * 
	 * @author fgb
	 *
	 */
	class GetByKeyWordAdapter extends BaseAdapter{

		private Context ct;
		private List<GetByKeyWordBean> data=new ArrayList<GetByKeyWordBean>();
		private List<GetPriceListBean> data2=new ArrayList<GetPriceListBean>();
		public BitmapUtils bitmapUtils;
		
		public GetByKeyWordAdapter(Context ct,List<GetByKeyWordBean> data,List<GetPriceListBean> data2){
			this.ct=ct;
			this.data=data;
			this.data2=data2;
			bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
			bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		}
		
		/**
		 * 当ListView数据发生变化时,调用此方法来更新ListView
		 * 
		 * @param list
		 */
		public void updateListView(List<GetByKeyWordBean> list) {
			this.data = list;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(ct).inflate(R.layout.item_getby_keywoed_view, null);
				viewHolder.tv_goods_title=(TextView) convertView.findViewById(R.id.tv_goods_title);
				viewHolder.tv_goods_no=(TextView) convertView.findViewById(R.id.tv_goods_no);
				viewHolder.tv_totalstock=(TextView) convertView.findViewById(R.id.tv_totalstock);
				viewHolder.tv_goods_sellprice=(TextView) convertView.findViewById(R.id.tv_goods_sellprice);
				viewHolder.iv_goods_imgUrl=(ImageView) convertView.findViewById(R.id.iv_goods_imgUrl);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			GetByKeyWordBean bean=data.get(position);
			
			viewHolder.tv_goods_sellprice.setText("￥"+bean.SELLPRICE);
			viewHolder.tv_goods_title.setText(bean.TITLE);
			viewHolder.tv_goods_no.setText(bean.NO);
			viewHolder.tv_totalstock.setText(bean.TOTALSTOCK);
			if(bean.IMAGEURL!=null){
				bitmapUtils.display(viewHolder.iv_goods_imgUrl, bean.IMAGEURL, App.mInstance().bitmapConfig);
				
			}else{
				viewHolder.iv_goods_imgUrl.setImageDrawable(mContext.getResources().getDrawable(R.drawable.no_shop_picture));
			}
			
			
			return convertView;
		}
	}
	
	static class ViewHolder {
		TextView tv_goods_title;//商品标题
		TextView tv_goods_no;//款号
		TextView tv_totalstock;//总库存
		TextView tv_goods_sellprice;//售价
		ImageView iv_goods_imgUrl;//商品url
		
	}

}
