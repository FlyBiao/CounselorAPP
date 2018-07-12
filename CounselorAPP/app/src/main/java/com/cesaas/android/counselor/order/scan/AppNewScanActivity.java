package com.cesaas.android.counselor.order.scan;

import android.content.Intent;
import android.os.Bundle;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.activity.ScanSendDetailActivity;
import com.cesaas.android.counselor.order.bean.ResultGetByBarcodeCodeBean;
import com.cesaas.android.counselor.order.bean.ResultUserBean;
import com.cesaas.android.counselor.order.boss.ui.activity.shop.ScanShopActivity;
import com.cesaas.android.counselor.order.boss.ui.activity.shop.ScanVipActivity;
import com.cesaas.android.counselor.order.global.Constant;
import com.cesaas.android.counselor.order.member.bean.service.ResultCheckInBean;
import com.cesaas.android.counselor.order.member.bean.service.ResultGetOneByMobileBean;
import com.cesaas.android.counselor.order.member.net.service.CheckInNet;
import com.cesaas.android.counselor.order.member.net.service.GetOneByMobileNet;
import com.cesaas.android.counselor.order.member.service.MemberReturnVisitDetailsActivity;
import com.cesaas.android.counselor.order.net.GetByBarcodeCodeNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

import java.util.ArrayList;

import cn.hugo.android.scanner.CaptureActivity;

/**
 * APP首页扫描面
 * @author cebsaas
 *
 */
public class AppNewScanActivity extends BasesActivity{

	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE = 101;

	private CheckInNet inNet;
	private GetOneByMobileNet mobileNet;
	private GetByBarcodeCodeNet codeNet;

	private String scanCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Skip.mScanMainResult(mActivity, CaptureActivity.class, REQUEST_CONTACT);
	}

	public void onEventMainThread(ResultGetByBarcodeCodeBean msg) {
		if(msg.IsSuccess!=false){
			if(msg.TModel!=null && !"".equals(msg.TModel)){
				bundle.putInt("Id",msg.TModel.getShopStyleId());
				Skip.mNextFroData(mActivity, ScanShopActivity.class,bundle,true);
			}else{
				ToastFactory.getLongToast(mContext,"获取商品信息失败："+msg.Message);
				finish();
			}
		}else{
			ToastFactory.getLongToast(mContext,"获取商品信息失败："+msg.Message);
			finish();
		}
	}

	public void onEventMainThread(ResultGetOneByMobileBean msg) {
		if(msg.IsSuccess!=false){
			if(msg.TModel!=null && !"".equals(msg.TModel)){
				bundle.putInt("Id",msg.TModel.getId());//暂时处理
				bundle.putInt("VipId",msg.TModel.getId());
				bundle.putString("Name",msg.TModel.getName());
				bundle.putString("Phone",scanCode);
				bundle.putString("Date",msg.TModel.getName());
				bundle.putString("Desc",msg.TModel.getName());
				bundle.putString("Remark",msg.TModel.getName());
				bundle.putString("Title",msg.TModel.getName());
				bundle.putInt("Status",20);
				Skip.mNextFroData(mActivity,MemberReturnVisitDetailsActivity.class,bundle,true);
			}else{
				ToastFactory.getLongToast(mContext,"获取会员信息失败："+msg.Message);
				finish();
			}

		}else{
			ToastFactory.getLongToast(mContext,"获取会员信息失败："+msg.Message);
			finish();
		}
	}

	public void onEventMainThread(ResultCheckInBean msg) {
		if(msg.IsSuccess!=false){
			ToastFactory.getLongToast(mContext,"活动签到成功！");
			finish();
		}else{
			ToastFactory.getLongToast(mContext,"活动签到失败："+msg.Message);
			finish();
		}
	}

	/**
	 * 获取处理扫描Activity返回的数据
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CODE && data.getStringExtra("resultCode")!=null) {
			if(data.getStringExtra("scanMainResultCode")!=null){
				if(data.getStringExtra("scanMainResultCode").equals("013")){
					scanCode=data.getStringExtra("resultCode");
					switch (data.getStringExtra("scanType")){
						case "1":
							codeNet=new GetByBarcodeCodeNet(mContext);
							codeNet.setData(scanCode);
							break;
						case "2":
							mobileNet=new GetOneByMobileNet(mContext);
							mobileNet.setData(scanCode);
							break;
						case "3":
							inNet=new CheckInNet(mContext);
							inNet.setData(scanCode);
							break;
						default:
							finish();
							break;
					}
				}
			}

		}else{
			finish();
			ToastFactory.show(mContext, "没有获取扫描结果！！", ToastFactory.CENTER);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
