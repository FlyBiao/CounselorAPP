package com.cesaas.android.counselor.order.group.fragment;

import org.json.JSONArray;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.GetByKeyWordActivity;
import com.cesaas.android.counselor.order.bean.ResultSendArticlesWxMsBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.fans.activity.GroupSendAactivity;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.net.SendArticlesWxMsgNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 群发消息-图文
 * @author FGB
 *
 */
public class GroupImageTextFragment extends BaseFragment implements OnClickListener{
	
	private View view;
	private TextView tv_add_group_image_text,tv_group_shop_url,tv_group_title,tv_group_shop_style,tv_group_shop_price,tv_group_shop_inventory;
	private ImageView iv_group_image_url;
	private RelativeLayout rl_group_image_text_message;
	private LinearLayout ll_group_send;
	
	private int REQUEST_CONTACT = 20;
	final int RESULT_CODE=101;
	
	private String imageUrl;
	private String url;
	private String title;
	private String styleNo;
	private String inventory;
	private String description;
	private String Price;
	
	public JSONArray fansArray;
	
	private SendArticlesWxMsgNet articlesWxMsgNet;
	
	/**
	 * 单例
	 */
	public static GroupImageTextFragment instance=null;
	public static GroupImageTextFragment getInstance(){
		if(instance==null){
			instance=new GroupImageTextFragment();
		}
		return instance;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fansArray=GroupSendAactivity.fansArray;
		
	}

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        view=inflater.inflate(R.layout.fragment_group_image_text, container, false);
	        initView();
	        return view;
	    }
	
	private void initView() {
		iv_group_image_url=(ImageView) view.findViewById(R.id.iv_group_image_url);
		tv_group_shop_url=(TextView) view.findViewById(R.id.tv_group_shop_url);
		tv_group_title=(TextView) view.findViewById(R.id.tv_group_title);
		tv_group_shop_style=(TextView) view.findViewById(R.id.tv_group_shop_style);
		tv_group_shop_price=(TextView) view.findViewById(R.id.tv_group_shop_price);
		tv_group_shop_inventory=(TextView) view.findViewById(R.id.tv_group_shop_inventory);
		tv_add_group_image_text=(TextView) view.findViewById(R.id.tv_add_group_image_text);
		ll_group_send=(LinearLayout) view.findViewById(R.id.ll_group_send);
		rl_group_image_text_message=(RelativeLayout) view.findViewById(R.id.rl_group_image_text_message);
		rl_group_image_text_message.setOnClickListener(this);
		tv_add_group_image_text.setOnClickListener(this);
		
	}

	@Override
	protected void lazyLoad() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.tv_add_group_image_text:
			Intent intent = new Intent();
			intent.setClass(getContext(), GetByKeyWordActivity.class);
			startActivityForResult(intent, REQUEST_CONTACT);
			break;
		
		case R.id.rl_group_image_text_message:
			if(title==null || imageUrl==null){
				ToastFactory.getLongToast(getContext(), "发送商品信息不全，请重新选择！");
			}else{
				articlesWxMsgNet=new SendArticlesWxMsgNet(getContext());
				articlesWxMsgNet.setData(title, title, imageUrl, url, fansArray);
			}
			
			break;

		default:
			break;
		}
	}
	
	/**
	 * 接受发送图片消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultSendArticlesWxMsBean msg) {
		if (msg.IsSuccess==true) {//成功
			sendMsgSuccess(msg.TModel.Total,msg.TModel.SuccessCount,msg.TModel.ErrorCount);
			
		}else{//失败
			ToastFactory.getLongToast(getContext(), "图文消息发送失败!"+msg.Message);
		}
	}
	
	/**
	 * 发送消息成功
	 * @param Total 消息总数
	 * @param SuccessCount 成功数
	 * @param ErrorCount 失败数
	 */
	public void sendMsgSuccess(int Total,int SuccessCount,int ErrorCount) {
		new MyAlertDialog(getContext()).mInitShow("发送成功", "总共"+Total+"条 \n"+"成功："+SuccessCount+"条，失败："+ErrorCount+"条",
			"返回上一页", "再发一次", new ConfirmListener() {
				@Override
				public void onClick(Dialog dialog) {
					Skip.mBack(getActivity());
				}
			}, null);
	}
	
	/**
	 * 处理Activity返回的数据
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_CODE) {
             imageUrl=data.getStringExtra("ImageUrl");
             title=data.getStringExtra("Title");
             url=data.getStringExtra("Url");
             styleNo=data.getStringExtra("NO");
             inventory=data.getStringExtra("TOTALSTOCK");
             Price=data.getStringExtra("Price");
             
             ll_group_send.setVisibility(View.VISIBLE);
             tv_group_shop_url.setText(url);
             tv_group_title.setText(title);
             tv_group_shop_inventory.setText(inventory);
             tv_group_shop_style.setText(styleNo);
             tv_group_shop_price.setText(Price);
             bitmapUtils.display(iv_group_image_url, imageUrl, App.mInstance().bitmapConfig);
        }
	}

}
