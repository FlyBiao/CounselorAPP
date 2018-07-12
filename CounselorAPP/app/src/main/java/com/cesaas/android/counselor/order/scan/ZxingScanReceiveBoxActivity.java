package com.cesaas.android.counselor.order.scan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.ListViewDecoration;
import com.cesaas.android.java.adapter.receive.ReceiveGoodsDetailScanAdapter;
import com.cesaas.android.java.bean.move.MoveDeliveryChangeByBarcodeNoBean;
import com.cesaas.android.java.bean.move.MoveDeliveryDeleteByBarcodeNoBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryScanBean;
import com.cesaas.android.java.bean.move.MoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.move.ResultMoveDeliveryGoodsDetailBean;
import com.cesaas.android.java.bean.receive.GetDetailListByBoxBean;
import com.cesaas.android.java.bean.receive.ResultGetDetailListByBoxBean;
import com.cesaas.android.java.net.receive.ReceiveChangeByBarcodeNoNet;
import com.cesaas.android.java.net.receive.ReceiveDeleteByBarcodeNoNet;
import com.cesaas.android.java.net.receive.ReceiveGetDetailListByBoxNet;
import com.cesaas.android.java.net.receive.ReceiveGoodsDetailNet;
import com.cesaas.android.java.net.receive.ReceiveScanNet;
import com.cesaas.android.java.utils.FilterConditionDateUtils;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

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
public class ZxingScanReceiveBoxActivity extends BasesActivity implements QRCodeView.Delegate ,View.OnClickListener{

	private TextView tvTitle;
	private TextView tv_count,tv_scan_complete,tv_back;
	private LinearLayout ll_scan_sum;
	private LinearLayout llBack;
	private ImageView scan;
	private SwipeMenuRecyclerView recyclerView;

	private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
	private QRCodeView mQRCodeView;
	private BaseDialog baseDialog;

	private String barcodeNo;
	private int pageIndex=1;
	private int sum;
	private int num=1;
	private long pid;
	private long boxId;
	private boolean isOpenFlashlight=false;

	private ReceiveGoodsDetailScanAdapter adapter;
	private List<MoveDeliveryGoodsDetailBean> mData;
	private ReceiveScanNet moveDeliveryScanNet;
//	private ReceiveGetDetailListByBoxNet receiveGetDetailListByBoxNet;
private ReceiveGoodsDetailNet moveDeliveryGoodsDetailNet;
	private ReceiveChangeByBarcodeNoNet moveDeliveryChangeByBarcodeNoNet;
	private ReceiveDeleteByBarcodeNoNet moveDeliveryDeleteByBarcodeNoNet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zxing_scan_box);
		Bundle bundle=getIntent().getExtras();
		if(bundle!=null){
			pid=bundle.getLong("id");
			boxId=bundle.getLong("boxId");
		}

		initView();
		initData();
	}


	/**
	 * 接收修改
	 * @param msg
	 */
	public void onEventMainThread(MoveDeliveryChangeByBarcodeNoBean msg) {
		if(msg.arguments.resp.errorCode!=0){
			ToastFactory.getLongToast(mContext,"修改数量成功");
			initData();
		}else{
			ToastFactory.getLongToast(mContext,msg.arguments.resp.errorMessage);
		}
	}


	/**
	 * 接收删除商品
	 * @param msg
	 */
	public void onEventMainThread(MoveDeliveryDeleteByBarcodeNoBean msg) {
		if(msg.arguments.resp.errorCode!=0){
			ToastFactory.getLongToast(mContext,"删除成功");
			initData();
		}else{
			ToastFactory.getLongToast(mContext,msg.arguments.resp.errorMessage);
		}
	}

	/**
	 * 接收扫描录入
	 * @param msg
	 */
	public void onEventMainThread(ResultInventoryScanBean msg) {
		if(msg.arguments.resp.errorCode!=0){
			ToastFactory.getLongToast(mContext,"录入成功！");
			initData();
		}else{
			ToastFactory.getLongToast(mContext,msg.arguments.resp.errorMessage);
		}
	}

	/**
	 * 接收商品条码列表
	 * @param msg
	 */
	public void onEventMainThread(ResultMoveDeliveryGoodsDetailBean msg) {
		if(msg.arguments.resp.errorCode!=0){
			if(msg.arguments.resp.records.value!=null && msg.arguments.resp.records.value.size()!=0){
				mData=new ArrayList<>();
				mData.addAll(msg.arguments.resp.records.value);

				adapter=new ReceiveGoodsDetailScanAdapter(mData,mContext);
				recyclerView.setAdapter(adapter);
				for (int i=0;i<mData.size();i++){
					sum+=mData.get(i).getNum();
				}
				tv_count.setText(String.valueOf(sum));
			}
		}else{
			ToastFactory.getLongToast(mContext,msg.arguments.resp.errorMessage);
		}
	}

	private void initData(){
//		receiveGetDetailListByBoxNet=new ReceiveGetDetailListByBoxNet(mContext);
//		receiveGetDetailListByBoxNet.setData(boxId,pid);
		moveDeliveryGoodsDetailNet=new ReceiveGoodsDetailNet(mContext);
		moveDeliveryGoodsDetailNet.setData(pageIndex, FilterConditionDateUtils.getPIdAndBoxId(pid,boxId));
	}

	private void initView(){
		recyclerView= (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器。
		recyclerView.setHasFixedSize(true);// 如果Item够简单，高度是确定的，打开FixSize将提高性能。
		recyclerView.setItemAnimator(new DefaultItemAnimator());// 设置Item默认动画，可选。
		recyclerView.addItemDecoration(new ListViewDecoration());// 添加分割线。
		// 设置菜单创建器。
		recyclerView.setSwipeMenuCreator(swipeMenuCreator);
		// 设置菜单Item点击监听。
		recyclerView.setSwipeMenuItemClickListener(menuItemClickListener);

		ll_scan_sum= (LinearLayout) findViewById(R.id.ll_scan_sum);
		ll_scan_sum.setOnClickListener(this);
		llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
		tvTitle= (TextView) findViewById(R.id.tv_base_title);
		tvTitle.setText("扫描收货");
		tv_count= (TextView) findViewById(R.id.tv_count);
		scan= (ImageView) findViewById(R.id.iv_add_module);
		scan.setImageResource(R.drawable.scan_flashlight);
		scan.setVisibility(View.VISIBLE);
		scan.setOnClickListener(this);
		tv_scan_complete= (TextView) findViewById(R.id.tv_scan_complete);
		tv_scan_complete.setOnClickListener(this);
		tv_back= (TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);

		mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
		mQRCodeView.setDelegate(this);

		llBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Skip.mBack(mActivity);
			}
		});
	}

	/**
	 * 菜单创建器。
	 */
	private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
		@Override
		public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
			int width = getResources().getDimensionPixelSize(R.dimen.item_height);
			// MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
			int height = ViewGroup.LayoutParams.MATCH_PARENT;
			// 添加右侧的，如果不添加，则右侧不会出现菜单。
			{
				SwipeMenuItem addItem = new SwipeMenuItem(mContext)
						.setBackgroundDrawable(R.drawable.selector_red)
						.setText("删除")
						.setTextColor(Color.WHITE)
						.setWidth(width) // 宽度。
						.setHeight(height); // 高度。
				swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。

				SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
						.setBackgroundDrawable(R.drawable.selector_green)
						.setText("更改数量") // 文字，还可以设置文字颜色，大小等。。
						.setTextColor(Color.WHITE)
						.setWidth(width) // 宽度。
						.setHeight(height); // 高度。
				swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

			}
		}
	};

	/**
	 * 菜单点击监听。
	 */
	private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
		@Override
		public void onItemClick(final Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
			switch (menuPosition){
				case 0://删除
					barcodeNo=mData.get(adapterPosition).getBarcodeNo();
					if (materialDialog != null) {
						materialDialog.setTitle("温馨提示！")
								.setMessage("确定需要删除该记录吗？")
								.setPositiveButton("确定", new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										moveDeliveryDeleteByBarcodeNoNet=new ReceiveDeleteByBarcodeNoNet(mContext);
										moveDeliveryDeleteByBarcodeNoNet.setData(pid,boxId,barcodeNo);
										materialDialog.dismiss();
									}
								}).setNegativeButton("返回", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								materialDialog.dismiss();
							}
						}).setCanceledOnTouchOutside(true).show();
					}
					closeable.smoothCloseMenu();
					break;
				case 1://修改数量
					barcodeNo=mData.get(adapterPosition).getBarcodeNo();
					BaseDialog baseDialog=new BaseDialog(mContext);
					baseDialog.setCancelable(false);
					baseDialog.mInitShow();
					closeable.smoothCloseMenu();
					break;
			}
		}
	};
	
	/**
	 * 处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		mQRCodeView.showScanRect();

		if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
			final String picturePath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {
					return QRCodeDecoder.syncDecodeQRCode(picturePath);
				}

				@Override
				protected void onPostExecute(String result) {
					if (TextUtils.isEmpty(result)) {
						Toast.makeText(ZxingScanReceiveBoxActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
					} else {
						//执行扫描盘点操作
						moveDeliveryScanNet=new ReceiveScanNet(mContext);
						moveDeliveryScanNet.setData(pid,boxId,result,num,6);
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
				baseDialog = new BaseDialog(mContext);
				baseDialog.mInitShow();
				baseDialog.setCancelable(false);
				break;
			case R.id.iv_add_module:
				if(isOpenFlashlight==false){
					isOpenFlashlight=true;
					mQRCodeView.openFlashlight();
				}else{
					isOpenFlashlight=false;
					mQRCodeView.closeFlashlight();
				}
				break;
			case R.id.tv_scan_complete:
				Skip.mBack(mActivity);
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
		moveDeliveryScanNet=new ReceiveScanNet(mContext);
		moveDeliveryScanNet.setData(pid,boxId,result,num,6);
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
		TextView tvCancel,tvSure;
		EditText et_goods_num;

		public BaseDialog(Context context) {
			this(context, R.style.dialog);
		}

		public BaseDialog(Context context, int dialog) {
			super(context, dialog);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			setContentView(R.layout.dialog_input_goods_num);
			et_goods_num= (EditText) findViewById(R.id.et_goods_num);
			tvCancel= (TextView) findViewById(R.id.tv_cancel);
			tvCancel.setOnClickListener(this);
			tvSure= (TextView) findViewById(R.id.tv_sure);
			tvSure.setOnClickListener(this);
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
					if(!TextUtils.isEmpty(et_goods_num.getText().toString())){
						int num=Integer.parseInt(et_goods_num.getText().toString());
						moveDeliveryChangeByBarcodeNoNet=new ReceiveChangeByBarcodeNoNet(mContext);
						moveDeliveryChangeByBarcodeNoNet.setData(pid,boxId,barcodeNo,num);
						dismiss();
					}else{
						ToastFactory.getLongToast(mContext,"请输入商品数量！");
					}
					break;
			}
		}
	}
}
