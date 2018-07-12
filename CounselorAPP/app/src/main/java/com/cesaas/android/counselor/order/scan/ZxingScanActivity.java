package com.cesaas.android.counselor.order.scan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.adapter.InventoryGoodsListAdapter;
import com.cesaas.android.java.bean.inventory.InventoryGetSubListBean;
import com.cesaas.android.java.bean.inventory.InventoryListBeanBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryDeleteStyleBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryGetSubListBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryScanBean;
import com.cesaas.android.java.net.inventory.InventoryDeleteStyleNet;
import com.cesaas.android.java.net.inventory.InventoryGetGoodsListNet;
import com.cesaas.android.java.net.inventory.InventoryGetSubListNet;
import com.cesaas.android.java.net.inventory.InventoryScanNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * 扫描发货页面
 * @author cebsaas
 *
 */
public class ZxingScanActivity extends BasesActivity implements QRCodeView.Delegate ,View.OnClickListener{

	private TextView tvTitle,tvRightTitle,tv_tishi;
	private TextView tv_count,tv_scan_sum;
	private LinearLayout ll_scan_sum;
	private LinearLayout llBack;
	private RecyclerView recyclerView;
	private ItemTouchHelper mItemTouchHelper;
	private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;

	private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
	private QRCodeView mQRCodeView;
	public MClearEditText et_style_code;
	private BaseDialog baseDialog;

	private InventoryScanNet scanNet;
	private InventoryGetGoodsListNet inventoryGetGoodsListNet;
	private InventoryDeleteStyleNet deleteStyleNet;

	private InventoryGetSubListNet inventoryGetSubListNet;

	private List<InventoryGetSubListBean> mData=new ArrayList<>();
	private InventoryGoodsListAdapter goodsListAdapter;

	private String barcodeNo;
	private String title;
	private int sanType;
	private int adapterPosition=0;
	private int num=1;
	private long pid;
	private long shelfId;
	private InventoryListBeanBean inventoryListBeanBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zxing_scan);
//		Bundle bundle=getIntent().getExtras();
//		if(bundle!=null){
//			pid=bundle.getLong("pid");
//			shelfId=bundle.getLong("shelfId");
//			title=bundle.getString("leftTitle");
//			inventoryListBeanBean= (InventoryListBeanBean) bundle.getSerializable("inventoryListBeanBean");
//		}
		Intent intent=getIntent();
		title= intent.getStringExtra("title");
		String pids = intent.getStringExtra("pid");
		pid=Long.parseLong(pids);
		String shelfIds = intent.getStringExtra("shelfId");
		shelfId=Long.parseLong(shelfIds);
		inventoryListBeanBean= (InventoryListBeanBean) intent.getSerializableExtra("inventoryListBeanBean");

		initView();
		initData();

		if(inventoryListBeanBean.getStatus()==0 || inventoryListBeanBean.getStatus()==50){
			mQRCodeView.setVisibility(View.VISIBLE);
			tvRightTitle.setVisibility(View.VISIBLE);
			tv_tishi.setVisibility(View.VISIBLE);
		}else{
			mQRCodeView.setVisibility(View.GONE);
			tvRightTitle.setVisibility(View.GONE);
			tv_tishi.setVisibility(View.GONE);
		}
	}


	/**
	 * 接收删除商品
	 * @param msg
	 */
	public void onEventMainThread(ResultInventoryDeleteStyleBean msg) {
		if(msg.getError()!=null){
			ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
		}else {
			if (msg.arguments.resp.errorCode != 0) {
				ToastFactory.getLongToast(mContext, "删除成功！");
				mData.remove(adapterPosition);
				goodsListAdapter.notifyDataSetChanged();
			} else {
				ToastFactory.getLongToast(mContext, "删除失败！" + msg.arguments.resp.errorMessage);
			}
		}
	}

	/**
	 * 接收添加盘点商品数据
	 * @param msg
	 */
	public void onEventMainThread(ResultInventoryScanBean msg) {
		if(msg.getError()!=null){
			ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
		}else {
			if (msg.arguments.resp.errorCode != 0) {
				ToastFactory.getLongToast(mContext, "添加盘点商品成功！");
				ll_scan_sum.setVisibility(View.VISIBLE);
				initData();
				barcodeNo = msg.arguments.resp.scan.getBarcodeNo();
				tv_scan_sum.setText(String.valueOf(msg.arguments.resp.scan.getNum()));
			} else {
				ToastFactory.getLongToast(mContext, "添加盘点商品失败！" + msg.arguments.resp.errorMessage);
			}
		}
	}

	/**
	 * 接收添盘点商品列表
	 * @param msg
	 */
	public void onEventMainThread(ResultInventoryGetSubListBean msg) {
		if(msg.getError()!=null){
			ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
		}else {
			if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
				if (msg.arguments.resp.records.value != null && msg.arguments.resp.records.value.size() != 0) {
					tv_count.setText("(" + msg.arguments.resp.records.value.size() + ")");
					mData = new ArrayList<>();
					mData.addAll(msg.arguments.resp.records.value);

					OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
						@Override
						public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
							BaseViewHolder holder = ((BaseViewHolder) viewHolder);
						}

						@Override
						public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {
							BaseViewHolder holder = ((BaseViewHolder) viewHolder);
						}

						@Override
						public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
							final BaseViewHolder holder = ((BaseViewHolder) viewHolder);
							adapterPosition = holder.getAdapterPosition();
							goodsListAdapter.notifyDataSetChanged();
							if (materialDialog != null) {
								materialDialog.setTitle("温馨提示！")
										.setMessage("确定需要删除该盘点记录吗？")
										.setPositiveButton("确定", new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												materialDialog.dismiss();
												deleteStyleNet = new InventoryDeleteStyleNet(mContext);
												deleteStyleNet.setData(pid, mData.get(adapterPosition).getId(), shelfId);
											}
										}).setNegativeButton("返回", new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										materialDialog.dismiss();
									}
								}).setCanceledOnTouchOutside(true).show();
							}
						}

						@Override
						public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
							canvas.drawColor(ContextCompat.getColor(mContext, R.color.orangered));
						}
					};

					goodsListAdapter = new InventoryGoodsListAdapter(mData, mActivity, mContext);
					mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(goodsListAdapter);
					mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
					mItemTouchHelper.attachToRecyclerView(recyclerView);

					mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
					goodsListAdapter.enableSwipeItem();
					goodsListAdapter.setOnItemSwipeListener(onItemSwipeListener);
					goodsListAdapter.enableDragItem(mItemTouchHelper);

					recyclerView.setAdapter(goodsListAdapter);
				}
			} else {
				ToastFactory.getLongToast(mContext, msg.arguments.resp.errorMessage);
			}
		}
	}

	private void initData(){
//		inventoryGetGoodsListNet=new InventoryGetGoodsListNet(mContext);
//		inventoryGetGoodsListNet.setData(pid,shelfId);

		inventoryGetSubListNet=new InventoryGetSubListNet(mContext);
		inventoryGetSubListNet.setData(FilterConditionDateUtils.getPIdAndBoxIdGetSub(pid,shelfId));
	}

	private void initView(){
		recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		tv_tishi= (TextView) findViewById(R.id.tv_tishi);
		tv_scan_sum= (TextView) findViewById(R.id.tv_scan_sum);
		ll_scan_sum= (LinearLayout) findViewById(R.id.ll_scan_sum);
		ll_scan_sum.setOnClickListener(this);
		llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
		tvTitle= (TextView) findViewById(R.id.tv_base_title);
		if(title!=null){
			tvTitle.setText(title);
		}else{
			tvTitle.setText("扫描盘点");
		}
		tv_count= (TextView) findViewById(R.id.tv_count);
		tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
		tvRightTitle.setText("手动输入");
		tvRightTitle.setVisibility(View.VISIBLE);

		mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
		mQRCodeView.setDelegate(this);

		tvRightTitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sanType=1;
				baseDialog = new BaseDialog(mContext);
				baseDialog.mInitShow();
				baseDialog.setCancelable(false);
			}
		});
		llBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.putExtra("result", "true");
				setResult(10, mIntent);// 设置结果，并进行传送
				finish();
			}
		});
	}
	
	/**
	 * 处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		mQRCodeView.showScanRect();

		if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
			final String picturePath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);
            /*
            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					return QRCodeDecoder.syncDecodeQRCode(picturePath);
				}

				@Override
				protected void onPostExecute(String result) {
					if (TextUtils.isEmpty(result)) {
						Toast.makeText(ZxingScanActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
					} else {
						//执行扫描盘点操作
						scanNet=new InventoryScanNet(mContext);
						scanNet.setData(pid,shelfId,result,num);
					}
				}
			}.execute();
		}
	}

	@Override
	public void onScanQRCodeOpenCameraError() {
		Log.e(TAG, "打开相机出错");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ll_scan_sum:
				sanType=2;
				baseDialog = new BaseDialog(mContext);
				baseDialog.mInitShow();
				baseDialog.setCancelable(false);
				break;
//			case R.id.open_flashlight:
//				mQRCodeView.openFlashlight();
//				break;
//			case R.id.close_flashlight:
//				mQRCodeView.closeFlashlight();
//				break;
//			case R.id.scan_barcode:
//				mQRCodeView.changeToScanBarcodeStyle();
//				break;
//			case R.id.scan_qrcode:
//				mQRCodeView.changeToScanQRCodeStyle();
//				break;
//			case R.id.choose_qrcde_from_gallery:
//				startActivityForResult(BGAPhotoPickerActivity.newIntent(mContext, null, 1, null, false), REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
//				break;
//			case R.id.inventory_record:
////				bundle.putString("leftTitle",tvTitle.getText().toString());
////				Skip.mNextFroData(mActivity, InventoryRecordActivity.class,bundle);
////				bundle.putInt("id",id);
////				bundle.putInt("shelvesId",shelvesId);
////				Skip.mNextFroData(mActivity,InventoryShelvesDetailsActivity.class,bundle);
//				break;
		}
	}

	private void vibrate() {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(200);
	}

	@Override
	public void onScanQRCodeSuccess(String result) {
		vibrate();
		mQRCodeView.startSpot();
		//执行扫描盘点操作
		scanNet=new InventoryScanNet(mContext);
		scanNet.setData(pid,shelfId,result,num);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mQRCodeView.startSpot();
		mQRCodeView.startCamera();
		mQRCodeView.showScanRect();
	}

	@Override
	protected void onStop() {
		mQRCodeView.stopCamera();
		mQRCodeView.stopSpot();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		mQRCodeView.onDestroy();
		mQRCodeView.stopSpot();
		super.onDestroy();
	}

	public class BaseDialog extends Dialog implements View.OnClickListener {
		TextView tvCancel,tvSure,tv_dialog_title;

		public BaseDialog(Context context) {
			this(context, R.style.dialog);
		}

		public BaseDialog(Context context, int dialog) {
			super(context, dialog);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			setContentView(R.layout.dialog_input_inventory);
			tvCancel= (TextView) findViewById(R.id.tv_cancel);
			tvCancel.setOnClickListener(this);
			tvSure= (TextView) findViewById(R.id.tv_sure);
			tvSure.setOnClickListener(this);
			tv_dialog_title= (TextView) findViewById(R.id.tv_dialog_title);
			et_style_code= (MClearEditText) findViewById(R.id.et_style_code);

			if(sanType==1){
				tv_dialog_title.setText("商品款号");
				et_style_code.setHint("请输入商品款号");
			}else{
				tv_dialog_title.setText("商品数量");
				et_style_code.setHint("请输入商品数量");
			}
		}

		public void mInitShow() {
			show();
		}

		@Override
		public void onClick(View view) {
			switch (view.getId()) {
				case R.id.tv_cancel:
					dismiss();
					break;
				case R.id.tv_sure:
					//执行手动输入商品条码操作
					if(!TextUtils.isEmpty(et_style_code.getText().toString())){
						dismiss();
						if(sanType==1){
							scanNet=new InventoryScanNet(mContext);
							scanNet.setData(pid,shelfId,et_style_code.getText().toString(),num);
						}else{
							int sum=Integer.parseInt(et_style_code.getText().toString());
							scanNet=new InventoryScanNet(mContext);
							scanNet.setData(pid,shelfId,barcodeNo,sum);
						}
					}else{
						ToastFactory.getLongToast(mContext,"请输入商品条码！");
					}
					break;
			}
		}
	}
}
