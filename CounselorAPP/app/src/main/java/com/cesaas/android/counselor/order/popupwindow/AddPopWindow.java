package com.cesaas.android.counselor.order.popupwindow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.activity.GetListByShopIdActivity;
import com.cesaas.android.counselor.order.activity.LoginActivity;
import com.cesaas.android.counselor.order.activity.ScanSendActivity;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog;
import com.cesaas.android.counselor.order.dialog.MyAlertDialog.ConfirmListener;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.utils.AbPrefsUtil;
import com.cesaas.android.counselor.order.utils.Skip;

/** 
 * 自定义popupWindow 
 *  
 * @author flybiao 
 *  
 *  
 */  
public class AddPopWindow extends PopupWindow {  
    private View conentView;  
    private App myapp;
    protected AbPrefsUtil prefs;
  
    public AddPopWindow(final Activity context) {
    	myapp = App.mInstance();
    	prefs = AbPrefsUtil.getInstance();
        LayoutInflater inflater = (LayoutInflater) context  
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
        conentView = inflater.inflate(R.layout.add_popup_dialog, null);  
        int h = context.getWindowManager().getDefaultDisplay().getHeight();  
        int w = context.getWindowManager().getDefaultDisplay().getWidth();  
        // 设置SelectPicPopupWindow的View  
        this.setContentView(conentView);  
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(w / 2 + 50);  
        // 设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.WRAP_CONTENT);  
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);  
        this.setOutsideTouchable(true);  
        // 刷新状态  
        this.update();  
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);  
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);  
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.AnimationPreview);  
        LinearLayout addScanSend = (LinearLayout) conentView  .findViewById(R.id.add_scan_send);  
        LinearLayout searchVip = (LinearLayout) conentView  .findViewById(R.id.search_vip); 
        LinearLayout black_logo=(LinearLayout) conentView.findViewById(R.id.black_logo);
        
        //扫描发货
        addScanSend.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View arg0) { 
            	Skip.mNext(context, ScanSendActivity.class);
//            	Skip.mNext(context, CaptureActivity.class);
            	
                AddPopWindow.this.dismiss();  
            }  
        });  
  
        //会员查询
        searchVip.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	Skip.mNext(context, GetListByShopIdActivity.class);
                AddPopWindow.this.dismiss();  
            }  
        });  
        
        //退出登录
        black_logo.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				AddPopWindow.this.dismiss(); 
				new MyAlertDialog(context).mInitShow("退出登录", "是否退出登录，退出后将不能接收最新消息",
						"我知道", "点错了", new ConfirmListener() {
							@Override
							public void onClick(Dialog dialog) {
								myapp.mExecutorService.execute(new Runnable() {

									@Override
									public void run() {
										prefs.cleanAll();
										Skip.mNext(context, LoginActivity.class, true);
										// setResult(0xe); // 用于退出时关闭本页
										// onExit();
									}
								});
							}
						}, null);
			}
		});
    }  
 
    /** 
     * 显示popupWindow 
     *  
     * @param parent 
     */  
    public void showPopupWindow(View parent) {  
        if (!this.isShowing()) {  
            // 以下拉方式显示popupwindow  
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);  
        } else {  
            this.dismiss();  
        }  
    }

} 
