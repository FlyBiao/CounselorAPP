package com.cesaas.android.counselor.order.group.fragment;

import org.json.JSONArray;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BaseFragment;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.bean.ResultSendTextWxMsBean;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.fans.activity.GroupSendAactivity;
import com.cesaas.android.counselor.order.net.SendTextWxMsNet;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;

/**
 * 群发消息-文字
 * @author FGB
 *
 */
public class GroupTextFragment extends BaseFragment implements OnClickListener{
	
	private TextView et_send_text_msg;
	
	private View view;
	private RelativeLayout rl_group_text_message;
	
	private SendTextWxMsNet sendTextWxMsNet;
	
	public JSONArray fansArray;
	private String Content;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fansArray=GroupSendAactivity.fansArray;
		
	}
	
	/**
	 * 单例
	 */
	public static GroupTextFragment instance=null;
	public static GroupTextFragment getInstance(){
		if(instance==null){
			instance=new GroupTextFragment();
		}
		return instance;
	}

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        view=inflater.inflate(R.layout.fragment_group_text, container, false);
	        initView();
	        return view;
	    }
	
	private void initView() {
		et_send_text_msg=(TextView) view.findViewById(R.id.et_send_text_msg);
		rl_group_text_message=(RelativeLayout) view.findViewById(R.id.rl_group_text_message);
		rl_group_text_message.setOnClickListener(this);
	}

	@Override
	protected void lazyLoad() {
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_group_text_message:
			if(TextUtils.isEmpty(et_send_text_msg.getText().toString())){
				ToastFactory.getLongToast(getContext(), "请输入消息内容!");
				
			}else{
				Content=et_send_text_msg.getText().toString();
				sendTextWxMsNet=new SendTextWxMsNet(getContext());
				sendTextWxMsNet.setData(Content, fansArray);
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 接受EventBus发送消息
	 * @param msg 消息实体类
	 */
	public void onEventMainThread(ResultSendTextWxMsBean msg) {
		if (msg.IsSuccess==true) {//成功
			sendMsgSuccess(msg.TModel.Total,msg.TModel.SuccessCount,msg.TModel.ErrorCount);
			
		}else{//失败
			ToastFactory.getLongToast(getContext(), "消息发送失败!"+msg.Message);
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
	
}
