package com.cesaas.android.counselor.order.view;

import io.rong.imkit.RongIM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.CheckCargoActivity;
import com.cesaas.android.counselor.order.activity.OrderDetailActivity;
import com.cesaas.android.counselor.order.activity.ScanSendActivity;
import com.cesaas.android.counselor.order.activity.ShopDetailActivity;
import com.cesaas.android.counselor.order.activity.order.bean.ResultWaitOutCounselorOrderBean;
import com.cesaas.android.counselor.order.activity.order.net.WaitOutCounselorOrderNet;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBean;
import com.cesaas.android.counselor.order.bean.ResultGetByCounselorBean.GetByCounselorBeanItemBean;
import com.cesaas.android.counselor.order.bean.ResultGetStylePictureBean;
import com.cesaas.android.counselor.order.bean.ResultGetUnReceiveOrderBean;
import com.cesaas.android.counselor.order.bean.ResultOrderOutStockBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.global.BaseNet;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.net.GetByCounselorNet;
import com.cesaas.android.counselor.order.net.OrderStoreBackNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.BitmapHelp;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.LoadMoreListView;
import com.cesaas.android.counselor.order.widget.RefreshAndLoadMoreView;
import com.flybiao.adapterlibrary.ViewHolder;
import com.flybiao.adapterlibrary.abslistview.CommonAdapter;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.exception.HttpException;

/**
 * 未发货订单分销状态
 * @author FGB
 *
 */
public class WaitOutOrderStateView  extends BaseFragment{
	
	 private WaitOutCounselorOrderNet byCounselorNet;
	 private CommonAdapter<GetByCounselorBean> adapter;
	 private ArrayList<GetByCounselorBean> orderLis= new ArrayList<GetByCounselorBean>();
		
		private static int pageIndex = 1;
		private boolean isHaveMoreData = false;
		private LoadMoreListView mLoadMoreListView;
		private RefreshAndLoadMoreView mRefreshAndLoadMoreView;
		private boolean refresh=false;
		
		private ListView lv;
		
		public JSONArray styleCodeArray;
		
		/**
		 * 单例
		 */
		public static WaitOutOrderStateView instance=null;
		public static WaitOutOrderStateView getInstance(){
			if(instance==null){
				instance=new WaitOutOrderStateView();
			}
			return instance;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
		}
		
		public void initData() {
			loadData(1);
			mRefreshAndLoadMoreView.setLoadMoreListView(mLoadMoreListView);
			mLoadMoreListView.setRefreshAndLoadMoreView(mRefreshAndLoadMoreView);
			// 设置下拉刷新监听
			mRefreshAndLoadMoreView
					.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
						@Override
						public void onRefresh() {
							refresh=true;
							pageIndex = 1;
							loadData(pageIndex);
						}
					});
			// 设置加载监听
			mLoadMoreListView
					.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
						@Override
						public void onLoadMore() {
							refresh=false;
							loadData(pageIndex + 1);
						}
					});
		}
		
		public void onEventMainThread(ResultWaitOutCounselorOrderBean msg) {
			
			if (msg != null) {
				if (msg.TModel.size() > 0&&msg.TModel.size()==10) {				
					mLoadMoreListView.setHaveMoreData(true);
				} else {
					mLoadMoreListView.setHaveMoreData(false);
					
				}
				if(msg.TModel.size()!=0){
					orderLis.addAll(msg.TModel);
					/**
					 * 按照时间排序显示
					 */
					Collections.sort(orderLis, new Comparator<GetByCounselorBean>() {
						@Override
						public int compare(GetByCounselorBean lhs, GetByCounselorBean rhs) {

							Date date0=null;
							Date date1=null;
							for (int i = 0; i < orderLis.size(); i++) {
								date0= AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
								date1=AbDateUtil.stringToDate(orderLis.get(i).CreateDate);
							}

							// 对日期字段进行升序，如果欲降序可采用after方法
							if (date0.after(date1)) {

								return 1;
							}

							return -1;
						}
					});
				}
				mRefreshAndLoadMoreView.setRefreshing(false);
				mLoadMoreListView.onLoadComplete();
			}
		}
		
		public void onEventMainThread(ResultOrderOutStockBean msg) {
			
			if(msg.IsSuccess==true){
				ToastFactory.getLongToast(getContext(), "退单成功");
			}else{
				ToastFactory.getLongToast(getContext(),"退单失败！"+ msg.Message);
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.activity_all_order_state_view, container, false);
			mLoadMoreListView = (LoadMoreListView) view.findViewById(R.id.load_more_allOrderState_list);
			mRefreshAndLoadMoreView = (RefreshAndLoadMoreView) view.findViewById(R.id.refresh_allOrderState_and_load_more);
			initListener();
			
			//获取Adapter 设置数据
			adapter=new CommonAdapter<GetByCounselorBean>(getContext(),R.layout.item_all_order_state,orderLis) {
				
				@Override
				public void convert(ViewHolder holder, final GetByCounselorBean bean,final int postion) {
					
					lv=holder.getView(R.id.list_receive_order_things);
					holder.setText(R.id.tv_order_createTime, bean.CreateDate);
					holder.setText(R.id.tv_order_id, bean.TradeId);
					holder.setText(R.id.tv_shop_name, bean.CounselorName);
					
					//判断是否显示订单所属店员
//					if(abpUtil.getString("TypeId").equals("1")){//店长
//						holder.getView(R.id.view_shop_assistant).setVisibility(View.VISIBLE);
//						holder.getView(R.id.ll_shop_assistant).setVisibility(View.VISIBLE);
//					}else{//店员
//						holder.getView(R.id.view_shop_assistant).setVisibility(View.GONE);
//						holder.getView(R.id.ll_shop_assistant).setVisibility(View.GONE);
//					}
					
					final List<GetByCounselorBeanItemBean> list = new ArrayList<GetByCounselorBeanItemBean>();
					styleCodeArray=new JSONArray();
					for (int i = 0; i < bean.OrderItem.size(); i++) {
						GetByCounselorBeanItemBean itemBean=new ResultGetByCounselorBean().new GetByCounselorBeanItemBean();
						itemBean=bean.OrderItem.get(i);
						list.add(itemBean);
						//获取商品编号并添加到JsonArray中
						styleCodeArray.put(list.get(i).StyleCode);
					}
					
					MyReceiveOrderThingsAdapter adapter=new MyReceiveOrderThingsAdapter(mContext, list);
					int totalHeight = 0;
					for (int i = 0; i < adapter.getCount(); i++) {
						View listItem = adapter.getView(i, null, lv);
						listItem.measure(0, 0);
						totalHeight += listItem.getMeasuredHeight();
					}

					ViewGroup.LayoutParams params = lv.getLayoutParams();
					params.height = totalHeight + (lv.getDividerHeight() * (adapter.getCount() - 1));
					((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
					lv.setLayoutParams(params);
						
						if(bean.ExpressType==0){//物流发货
							holder.setText(R.id.tv_my_receive_user, bean.NickName+"("+bean.Mobile+")");
							holder.getView(R.id.tv_order_session1).setVisibility(View.VISIBLE);
							holder.getView(R.id.tv_order_session).setVisibility(View.GONE);
							holder.getView(R.id.slv_right_tri_express).setVisibility(View.VISIBLE);
							
							//物流订单发货点击事件监听
							holder.getView(R.id.tv_express_send).setVisibility(View.VISIBLE);
							holder.getView(R.id.tv_scan_send).setVisibility(View.GONE);
							holder.setOnClickListener(R.id.tv_express_send, new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									Bundle bundle = new Bundle();
									bundle.putString("TradeId", bean.TradeId);
									Skip.mNextFroData(getActivity(), CheckCargoActivity.class, bundle);
								}
							});
							
							//退单
							holder.getView(R.id.tv_back_order).setVisibility(View.VISIBLE);
							holder.setOnClickListener(R.id.tv_back_order, new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									new MyAlertDialog(getContext()).mInitShow("温馨提示", "是否确定退单",
									"退单", "点错了", new ConfirmListener() {
										@Override
										public void onClick(Dialog dialog) {
											OrderStoreBackNet backNet=new OrderStoreBackNet(getContext());
											backNet.setData(bean.TradeId);
											orderLis.remove(postion);
											notifyDataSetChanged();
											
										}
									}, null);
								}
							});
							
					}else if(bean.ExpressType==1){//到店自提扫描发货
						holder.getView(R.id.tv_back_order).setVisibility(View.GONE);
						holder.setText(R.id.tv_my_receive_user, bean.NickName);
						holder.getView(R.id.tv_order_session1).setVisibility(View.GONE);
						holder.getView(R.id.tv_order_session).setVisibility(View.VISIBLE);
							holder.getView(R.id.slv_right_tri_self_lift).setVisibility(View.VISIBLE);
					
						//扫描发货点击事件监听
						holder.getView(R.id.tv_express_send).setVisibility(View.GONE);
						holder.getView(R.id.tv_scan_send).setVisibility(View.VISIBLE);
						holder.setOnClickListener(R.id.tv_scan_send, new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								Skip.mNext(getActivity(), ScanSendActivity.class);
							}
						});
						
					}
						
					/**
					 * 查看订单详情
					 */
					lv.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
							Bundle bundle = new Bundle();
							bundle.putString("Title", list.get(i).Title);
							bundle.putString("ImageUrl", list.get(i).ImageUrl);
							bundle.putString("StyleCode", list.get(i).StyleCode);
							bundle.putString("BarcodeCode", list.get(i).BarcodeCode);
							bundle.putDouble("Price", list.get(i).Price);
							bundle.putString("Attr", list.get(i).Attr);
							
							Skip.mNextFroData(getActivity(), ShopDetailActivity.class, bundle);
						}
					});
					
					lv.setAdapter(adapter);
					
					//会话
					holder.setOnClickListener(R.id.tv_order_session, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// 启动单聊会话界面
							if (RongIM.getInstance() != null)
								RongIM.getInstance().startPrivateChat(getContext(), bean.VipId, bean.CounselorName);
						}
					});
					
					holder.setOnClickListener(R.id.tv_order_session1, new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// 启动单聊会话界面
							if (RongIM.getInstance() != null)
								RongIM.getInstance().startPrivateChat(getContext(), bean.VipId, bean.CounselorName);
							
						}
					});
				}
			};
			mLoadMoreListView.setAdapter(adapter);
			
			initData();
			
			return view;
		}
		
		/**
		 * 订单详情
		 */
		public void initListener() {
			mLoadMoreListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Bundle bundle = new Bundle();
					bundle.putString("TradeId", orderLis.get(position).TradeId);
					Skip.mNextFroData(getActivity(), OrderDetailActivity.class, bundle);
				}
			});
		}
		
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			Log.i("TestLog","未发货");
		}

		protected void loadData(final int page) {
			// TODO Auto-generated method stub
			if (page == 1) {
				orderLis.clear();
			}
			byCounselorNet=new WaitOutCounselorOrderNet(getActivity());
			byCounselorNet.setData(1,30,page);

			pageIndex = page;

		}
		
		
	 
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
	
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

		List<GetByCounselorBeanItemBean> list = new ArrayList<GetByCounselorBeanItemBean>();
		Context context;
		private BitmapUtils bitmapUtils;
		
		private String Url;
		
		public MyReceiveOrderThingsAdapter(Context ct, List<GetByCounselorBeanItemBean> data) {
			this.context = ct;
			this.list = data;
			bitmapUtils = BitmapHelp.getBitmapUtils(ct.getApplicationContext());
			bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(ct).scaleDown(3));
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
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

			ivImg.setScaleType(ImageView.ScaleType.FIT_XY);
			
			tvName.setText(list.get(position).Title);
			tvAttr.setText(list.get(position).Attr);
			tvTypeCode.setText("条码"+list.get(position).BarcodeCode);
			tvQuantity.setText("x" + list.get(position).Quantity);
			tv_order_price.setText("￥"+list.get(position).Price);
			
			if(list.get(position).ImageUrl!=null){
				if(list.get(position).Title.contains("独立收银")){
					ivImg.setImageDrawable(getResources().getDrawable(R.drawable.no_shop_picture));
					
				}else{
					bitmapUtils.display(ivImg, list.get(position).ImageUrl, App.mInstance().bitmapConfig);
				}
				
			}else{
				ivImg.setImageDrawable(getResources().getDrawable(R.drawable.no_shop_picture));
			}
			
			return convertView;
		}
		
		/**
		 * 根据商品款号获取商品图片
		 * @author fgb
		 *
		 */
		public class GetShopAdapterStylePictureNet extends BaseNet{

			public GetShopAdapterStylePictureNet(Context context) {
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
				Log.i(Constant.TAG, rJson);
				Gson gson = new Gson();
				ResultGetStylePictureBean lbean = gson.fromJson(rJson, ResultGetStylePictureBean.class);

				if (lbean.IsSuccess) {
					if(lbean.TModel!=null){
						for (int i = 0; i < lbean.TModel.size(); i++) {
							Url=lbean.TModel.get(i).Url;
						}
						if(Url!=null){
							bitmapUtils.display(ivImg, Url, App.mInstance().bitmapConfig);
						}
						else{
							ivImg.setImageDrawable(getResources().getDrawable(R.drawable.no_shop_picture));
						}
					}
				} else {
					ToastFactory.show(mContext, lbean.Message, ToastFactory.CENTER);
				}
			}

			@Override
			protected void mFail(HttpException e, String err) {
				super.mFail(e, err);
				Log.i(Constant.TAG, "err===" + e + "********=err==" + err);
			}

		}
	}
}
