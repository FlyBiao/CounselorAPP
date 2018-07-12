package com.cesaas.android.counselor.order.member.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.App;
import com.cesaas.android.counselor.order.member.SendMessageActivity;
import com.cesaas.android.counselor.order.member.bean.service.apply.MemberApplyListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.FaceEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.MemberServiceListBean;
import com.cesaas.android.counselor.order.member.bean.service.member.RemarkEventBusBean;
import com.cesaas.android.counselor.order.member.bean.service.member.TagEventBusBean;
import com.cesaas.android.counselor.order.member.net.service.AgreeNet;
import com.cesaas.android.counselor.order.member.net.service.RejectNet;
import com.cesaas.android.counselor.order.member.service.MemberServiceResultActivitys;
import com.cesaas.android.counselor.order.member.service.MemberUpdateDetailActivity;
import com.cesaas.android.counselor.order.power.adapter.InventoryMenuPowerAdapter;
import com.cesaas.android.counselor.order.shoptask.bean.ShopTaskListBean;
import com.cesaas.android.counselor.order.shoptask.bean.TaskTransferEventBean;
import com.cesaas.android.counselor.order.utils.CallUtils;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.inventory.InventoryListBeanBean;
import com.cesaas.android.java.bean.move.MoveDeliveryBoxListBean;
import com.cesaas.android.java.bean.move.MoveDeliveryListBeanBean;
import com.cesaas.android.java.bean.notice.NoticeListListBean;
import com.cesaas.android.java.bean.require.RequireListBean;
import com.cesaas.android.java.net.move.MoveDeliveryConfirmNet;
import com.cesaas.android.java.net.move.MoveDeliveryDeleteNet;
import com.cesaas.android.java.net.move.MoveDeliveryRejectNet;
import com.cesaas.android.java.net.move.MoveDeliverySubmitNet;
import com.cesaas.android.java.net.receive.ReceivingSubmitNet;
import com.cesaas.android.java.net.receive.ReceivingSureNet;
import com.cesaas.android.java.net.receive.ReceivingSyncNumNet;
import com.cesaas.android.java.net.require.RequireConfirmNet;
import com.cesaas.android.java.net.require.RequireDeleteNet;
import com.cesaas.android.java.net.require.RequireRejectNet;
import com.cesaas.android.java.net.require.RequireSubmitNet;
import com.cesaas.android.java.ui.activity.move.TransPackingActivity;
import com.cesaas.android.java.ui.activity.receive.ReceiveCheckDifferenceActivity;
import com.cesaas.android.java.ui.activity.receive.ReceiveDetailsActivity;
import com.cesaas.android.java.utils.InventoryPowerUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;

/**
 * Author FGB
 * Description
 * Created at 2018/3/22 14:41
 * Version 1.0
 */

public class BottonDialogUtils {
    public static LinearLayout ll_transfer,ll_schedule,ll_close_task;
    public static LinearLayout ll_weixin_service,ll_call_phone,ll_send_msg,ll_add_service_result,ll_set_tag,ll_set_face,ll_service_update,ll_agreed_service,ll_reject_service,ll_service_remark;
    public static TextView tv_remark;
    public static EditText et_service_remark;
    public static View dialogContentView;
    private static Dialog bottomDialog;

    /**
     * 会员列表Dialog
     * @param mContext
     * @param mActivity
     * @param bean
     */
    public static void showBottonDialog(final Context mContext, final Activity mActivity, final MemberServiceListBean bean,final int MemberType){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_add_service_result= (LinearLayout) bottomDialog.findViewById(R.id.ll_add_service_result);
        ll_send_msg= (LinearLayout) bottomDialog.findViewById(R.id.ll_send_msg);
        ll_call_phone= (LinearLayout) bottomDialog.findViewById(R.id.ll_call_phone);
        ll_weixin_service= (LinearLayout) bottomDialog.findViewById(R.id.ll_weixin_service);
        ll_set_face= (LinearLayout) bottomDialog.findViewById(R.id.ll_set_face);
        ll_set_tag= (LinearLayout) bottomDialog.findViewById(R.id.ll_set_tag);
        ll_service_update= (LinearLayout) bottomDialog.findViewById(R.id.ll_service_update);
        ll_weixin_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                //启动单聊会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(mContext, bean.getVipId()+"",bean.getName());
            }
        });
        ll_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                Bundle bundle=new Bundle();
                bundle.putString("VipId",bean.getVipId()+"");
                bundle.putString("Tel",bean.getMobile());
                bundle.putString("VipName",bean.getName());
                Skip.mNextFroData(mActivity,SendMessageActivity.class,bundle);
            }
        });ll_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                CallUtils.call(bean.getMobile(),mActivity);
                //调用通话监听
                TelephonyManager phoneManager = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
                MemberPhoneListenUtils phoneListenUtils=new MemberPhoneListenUtils(mContext,mActivity,bean.getVipId(),bean.getName(),bean.getMobile());
                phoneManager.listen(phoneListenUtils, PhoneStateListener.LISTEN_CALL_STATE);

            }
        });ll_add_service_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                Bundle bundle=new Bundle();
                bundle.putInt("Id",bean.getVipId());
                bundle.putString("Name",bean.getName());
                bundle.putString("Phone",bean.getMobile());
                bundle.putString("sex","1");
                Skip.mNextFroData(mActivity, MemberServiceResultActivitys.class,bundle);
            }
        });ll_set_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                TagEventBusBean t=new TagEventBusBean();
                t.setMemberType(MemberType);
                t.setVipId(bean.getVipId());
                EventBus.getDefault().post(t);
            }
        });ll_set_face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                FaceEventBusBean t=new FaceEventBusBean();
                t.setVipId(bean.getVipId());
                t.setMemberType(MemberType);
                t.setMobile(bean.getMobile());
                EventBus.getDefault().post(t);
            }
        });ll_service_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                Bundle bundle=new Bundle();
                bundle.putInt("VipId",bean.getVipId());
                Skip.mNextFroData(mActivity,MemberUpdateDetailActivity.class,bundle);
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }


    private static LinearLayout ll_notice_send,ll_notice_return,ll_notice_edit;
    /**
     * 通知单Dialog
     */
    public static void getNoticeDialog(final Context mContext, final Activity mActivity, final NoticeListListBean bean){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_notice, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_notice_send= (LinearLayout) bottomDialog.findViewById(R.id.ll_notice_send);
        ll_notice_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ll_notice_return= (LinearLayout) bottomDialog.findViewById(R.id.ll_notice_return);
        ll_notice_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ll_notice_edit= (LinearLayout) bottomDialog.findViewById(R.id.ll_notice_edit);
        ll_notice_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomDialog.dismiss();
            }
        });
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }


    private static LinearLayout ll_create_difference,ll_check_difference,ll_sure_difference,ll_edit_difference,ll_submit_inventory,ll_del_inventory,ll_remark_inventory;

    private static LinearLayout ll_del,ll_submit,ll_sure_send,ll_log,ll_check_packing,ll_reject;


    /**
     * 收货单 箱列表Dialog
     */
    public static void getReceiveBoxDialog(final Context mContext, final Activity mActivity, final MoveDeliveryBoxListBean bean,final long id){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_move_dlivery_receive_box_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_submit_icon= (TextView) bottomDialog.findViewById(R.id.tv_submit_icon);
        tv_submit_icon.setText(R.string.fa_check);
        tv_submit_icon.setTypeface(App.font);
        tv_log_icon= (TextView) bottomDialog.findViewById(R.id.tv_log_icon);
        tv_log_icon.setText(R.string.fa_calendars);
        tv_log_icon.setTypeface(App.font);

        ll_receive= (LinearLayout) bottomDialog.findViewById(R.id.ll_receive);
        ll_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceiveBoxDialog dialog=new ReceiveBoxDialog(mContext);
                dialog.setCancelable(false);
                dialog.mInitShow(id,mContext,bean);
                bottomDialog.dismiss();
            }
        });
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    private static LinearLayout ll_r_termination,ll_r_del,ll_r_submit,ll_r_sure;
    /**
     * 补货单Dialog
     */
    public static void getRequireDialog(final Context mContext, final Activity mActivity, final RequireListBean bean){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_move_dlivery_require_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_del_icon= (TextView) bottomDialog.findViewById(R.id.tv_del_icon);
        tv_del_icon.setText(R.string.fa_del_o);
        tv_del_icon.setTypeface(App.font);
        tv_submit_icon= (TextView) bottomDialog.findViewById(R.id.tv_submit_icon);
        tv_submit_icon.setText(R.string.fa_check);
        tv_submit_icon.setTypeface(App.font);
        tv_sure_icon= (TextView) bottomDialog.findViewById(R.id.tv_sure_icon);
        tv_sure_icon.setText(R.string.fa_check_square_o);
        tv_sure_icon.setTypeface(App.font);
        tv_reject_icon= (TextView) bottomDialog.findViewById(R.id.tv_reject_icon);
        tv_reject_icon.setText(R.string.fa_times);
        tv_reject_icon.setTypeface(App.font);

        ll_r_termination= (LinearLayout) bottomDialog.findViewById(R.id.ll_r_termination);
        ll_r_termination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ll_r_del= (LinearLayout) bottomDialog.findViewById(R.id.ll_r_del);
        ll_r_sure= (LinearLayout) bottomDialog.findViewById(R.id.ll_r_sure);
        ll_reject= (LinearLayout) bottomDialog.findViewById(R.id.ll_reject);
        ll_r_submit= (LinearLayout) bottomDialog.findViewById(R.id.ll_r_submit);

        switch (bean.getStatus()){
            case 0:
                ll_r_sure.setVisibility(View.GONE);
                break;
            case 30:
                ll_sure_send.setVisibility(View.VISIBLE);
                ll_reject.setVisibility(View.VISIBLE);
                ll_r_termination.setVisibility(View.GONE);
                ll_r_del.setVisibility(View.GONE);
                ll_r_submit.setVisibility(View.GONE);
                break;
            case 50:
                ll_sure_send.setVisibility(View.GONE);
                ll_reject.setVisibility(View.GONE);
                break;
        }

        ll_r_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequireDeleteNet requireDeleteNet=new RequireDeleteNet(mContext);
                requireDeleteNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });
        ll_r_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequireSubmitNet requireSubmitNet=new RequireSubmitNet(mContext);
                requireSubmitNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });
        ll_r_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequireConfirmNet requireConfirmNet=new RequireConfirmNet(mContext);
                requireConfirmNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });
        ll_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequireRejectNet rejectNet=new RequireRejectNet(mContext);
                rejectNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }



    private static LinearLayout ll_receive,ll_receive_log,ll_receive_check_difference,ll_order_details,ll_sure;
    private static TextView tv_order_icon;
    /**
     * 收货单Dialog
     */
    public static void getReceiveDialog(final Context mContext, final Activity mActivity, final MoveDeliveryListBeanBean bean){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_move_dlivery_receive_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_submit_icon= (TextView) bottomDialog.findViewById(R.id.tv_submit_icon);
        tv_submit_icon.setText(R.string.fa_check);
        tv_submit_icon.setTypeface(App.font);
        tv_log_icon= (TextView) bottomDialog.findViewById(R.id.tv_log_icon);
        tv_log_icon.setText(R.string.fa_calendars);
        tv_log_icon.setTypeface(App.font);
        tv_edit_icon= (TextView) bottomDialog.findViewById(R.id.tv_edit_icon);
        tv_edit_icon.setText(R.string.fa_pencil);
        tv_edit_icon.setTypeface(App.font);
        tv_order_icon= (TextView) bottomDialog.findViewById(R.id.tv_order_icon);
        tv_order_icon.setText(R.string.fa_calendar);
        tv_order_icon.setTypeface(App.font);
//
        ll_receive= (LinearLayout) bottomDialog.findViewById(R.id.ll_receive);
        ll_receive_log= (LinearLayout) bottomDialog.findViewById(R.id.ll_receive_log);
        ll_receive_check_difference= (LinearLayout) bottomDialog.findViewById(R.id.ll_receive_check_difference);
        ll_order_details= (LinearLayout) bottomDialog.findViewById(R.id.ll_order_details);
        switch (bean.getStatus()){
            case 30:
                ll_receive_log.setVisibility(View.GONE);
                break;
            case 40:
                ll_receive_log.setVisibility(View.GONE);
                ll_receive.setVisibility(View.GONE);
                break;
        }

        ll_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle=new Bundle();
//                bundle.putLong("id",bean.getId());
//                bundle.putSerializable("moveDeliveryListBeanBean",bean);
//                Skip.mNextFroData(mActivity, ReceiveGetBoxListActivity.class,bundle);
                bottomDialog.dismiss();
                ReceivingSureNet receivingSureNet=new ReceivingSureNet(mContext);
                receivingSureNet.setData(bean.getId());
            }
        });

        ll_receive_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReceivingSubmitNet receivingSubmitNet=new ReceivingSubmitNet(mContext);
                receivingSubmitNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });

        ll_receive_check_difference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putLong("id",bean.getId());
                bundle.putSerializable("moveDeliveryListBeanBean",bean);
                Skip.mNextFroData(mActivity,ReceiveCheckDifferenceActivity.class,bundle);
                bottomDialog.dismiss();
            }
        });

        ll_order_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                Bundle bundle=new Bundle();
                bundle.putLong("id", bean.getId());
                Skip.mNextFroData(mActivity, ReceiveDetailsActivity.class, bundle);
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    private static TextView tv_edit_icon,tv_del_icon,tv_submit_icon,tv_sure_icon,tv_log_icon,tv_reject_icon
            ,tv_remark_icon,tv_edit_difference_icon,tv_sure_difference_icon,tv_check_difference_icon,tv_create_difference_icon;
    /**
     * 调拨单Dialog
     * @param mContext
     * @param mActivity
     * @param bean
     */
    public static void showBottonDialog(final Context mContext, final Activity mActivity, final MoveDeliveryListBeanBean bean,final int type){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_move_dlivery_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_edit_icon= (TextView) bottomDialog.findViewById(R.id.tv_edit_icon);
        tv_edit_icon.setText(R.string.fa_pencil);
        tv_edit_icon.setTypeface(App.font);
        tv_del_icon= (TextView) bottomDialog.findViewById(R.id.tv_del_icon);
        tv_del_icon.setText(R.string.fa_del_o);
        tv_del_icon.setTypeface(App.font);
        tv_submit_icon= (TextView) bottomDialog.findViewById(R.id.tv_submit_icon);
        tv_submit_icon.setText(R.string.fa_check);
        tv_submit_icon.setTypeface(App.font);
        tv_sure_icon= (TextView) bottomDialog.findViewById(R.id.tv_sure_icon);
        tv_sure_icon.setText(R.string.fa_check_square_o);
        tv_sure_icon.setTypeface(App.font);
        tv_log_icon= (TextView) bottomDialog.findViewById(R.id.tv_log_icon);
        tv_log_icon.setText(R.string.fa_calendars);
        tv_log_icon.setTypeface(App.font);
        tv_reject_icon= (TextView) bottomDialog.findViewById(R.id.tv_reject_icon);
        tv_reject_icon.setText(R.string.fa_times);
        tv_reject_icon.setTypeface(App.font);

        ll_del= (LinearLayout) bottomDialog.findViewById(R.id.ll_del);
        ll_submit= (LinearLayout) bottomDialog.findViewById(R.id.ll_submit);
        ll_sure_send= (LinearLayout) bottomDialog.findViewById(R.id.ll_sure_send);
        ll_log= (LinearLayout) bottomDialog.findViewById(R.id.ll_log);
        ll_check_packing= (LinearLayout) bottomDialog.findViewById(R.id.ll_check_packing);
        ll_reject= (LinearLayout) bottomDialog.findViewById(R.id.ll_reject);

        switch (bean.getStatus()){
            case 0:
                ll_sure_send.setVisibility(View.GONE);
                ll_reject.setVisibility(View.GONE);
            break;
            case 30:
                ll_sure_send.setVisibility(View.VISIBLE);
                ll_reject.setVisibility(View.VISIBLE);
                ll_del.setVisibility(View.GONE);
                ll_submit.setVisibility(View.GONE);
                ll_log.setVisibility(View.GONE);
                ll_check_packing.setVisibility(View.GONE);
                break;
            case 50:
                ll_sure_send.setVisibility(View.GONE);
                ll_reject.setVisibility(View.GONE);
                break;
        }

        ll_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                MoveDeliveryDeleteNet moveDeliveryDeleteNet=new MoveDeliveryDeleteNet(mContext,type);
                moveDeliveryDeleteNet.setData(bean.getId());
            }
        });

        ll_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveDeliverySubmitNet moveDeliverySubmitNet=new MoveDeliverySubmitNet(mContext,type);
                moveDeliverySubmitNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });

        ll_sure_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveDeliveryConfirmNet confirmNet=new MoveDeliveryConfirmNet(mContext,type);
                confirmNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });

        ll_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveDeliveryRejectNet rejectNet=new MoveDeliveryRejectNet(mContext,type);
                rejectNet.setData(bean.getId());
                bottomDialog.dismiss();
            }
        });

        ll_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        ll_check_packing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                Bundle bundle=new Bundle();
                bundle.putLong("id",bean.getId());
                bundle.putSerializable("moveDeliveryListBeanBean",bean);
                bundle.putInt("netType",type);
                Skip.mNextFroData(mActivity,TransPackingActivity.class,bundle);
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }


    private static RecyclerView mMenuRecyclerView;
    private static InventoryMenuPowerAdapter inventoryMenuPowerAdapter;
    /**
     * 盘点单Dialog
     * @param mContext
     * @param mActivity
     * @param bean
     */
    public static void showBottonDialog(final Context mContext, final Activity mActivity, final InventoryListBeanBean bean){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_inventory_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        mMenuRecyclerView= (RecyclerView) bottomDialog.findViewById(R.id.rv_inventory_menu_list);
        mMenuRecyclerView.setLayoutManager(new GridLayoutManager(mContext,4));

        inventoryMenuPowerAdapter=new InventoryMenuPowerAdapter(InventoryPowerUtils.getInventoryPower(bean.getStatus()),mContext);
        mMenuRecyclerView.setAdapter(inventoryMenuPowerAdapter);
        inventoryMenuPowerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bottomDialog.dismiss();
                Bundle bundle=new Bundle();
                InventoryPowerUtils.skipInventory(InventoryPowerUtils.getInventoryPower(bean.getStatus()).get(position).getStatus(),mContext,mActivity,bundle,bean);
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    /**
     * 会员资料修改审核Dialog
     * @param mContext
     * @param mActivity
     * @param bean
     */
    public static void showMemberApplyBottonDialog(final Context mContext, final Activity mActivity, final MemberApplyListBean bean){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_member_apply_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_agreed_service= (LinearLayout) bottomDialog.findViewById(R.id.ll_agreed_service);
        ll_reject_service= (LinearLayout) bottomDialog.findViewById(R.id.ll_reject_service);
        ll_agreed_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                setApplyRemarkDialog(mContext,mActivity,bean,"审核备注",true);
            }
        });ll_reject_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                setApplyRemarkDialog(mContext,mActivity,bean,"审核备注",false);
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    /**
     * 会员审批备注
     * @param mContext
     * @param mActivity
     * @param bean
     * @param title
     * @param isApply
     */
    public static void setApplyRemarkDialog(final Context mContext, final Activity mActivity, final MemberApplyListBean bean,final String title,final boolean isApply){
        bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_remark, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_service_remark= (LinearLayout) bottomDialog.findViewById(R.id.ll_service_remark);
        tv_remark= (TextView) bottomDialog.findViewById(R.id.tv_remark);
        et_service_remark= (EditText) bottomDialog.findViewById(R.id.et_service_remark);
        tv_remark.setText(title);
        ll_service_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isApply==true){
                    bottomDialog.dismiss();
                    AgreeNet net=new AgreeNet(mContext);
                    net.setData(bean.getId(),et_service_remark.getText().toString());
                }else{
                    bottomDialog.dismiss();
                    RejectNet net=new RejectNet(mContext);
                    net.setData(bean.getId(),et_service_remark.getText().toString());
                }
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }


    public static void setMemberRemarkDialog(final Context mContext, final Activity mActivity,final MemberServiceListBean b,final int type){
        bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_remark, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        tv_remark= (TextView) bottomDialog.findViewById(R.id.tv_remark);
        tv_remark.setText("添加会员备注");
        et_service_remark= (EditText) bottomDialog.findViewById(R.id.et_service_remark);
        et_service_remark.setText(b.getRemark());
        ll_service_remark= (LinearLayout) bottomDialog.findViewById(R.id.ll_service_remark);
        ll_service_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                RemarkEventBusBean bean=new RemarkEventBusBean();
                bean.setMemberType(type);
                bean.setRemark(et_service_remark.getText().toString());
                bean.setVipId(b.getVipId());
                EventBus.getDefault().post(bean);
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }


    public static void showBottonTaskDialog(final Context mContext, final Activity mActivity, final ShopTaskListBean item, final int MemberType){
        bottomDialog= new Dialog(mContext, R.style.BottomDialog);
        dialogContentView= LayoutInflater.from(mContext).inflate(R.layout.dialog_member_task_content_normal, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_transfer= (LinearLayout) bottomDialog.findViewById(R.id.ll_transfer);
        ll_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                TaskTransferEventBean bean=new TaskTransferEventBean();
                bean.setFlowId(item.getFlowId());
                bean.setType(MemberType);
                EventBus.getDefault().post(bean);
            }
        });
        ll_schedule= (LinearLayout) bottomDialog.findViewById(R.id.ll_schedule);
        ll_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastFactory.getLongToast(mContext,"添加日程成功！");
                bottomDialog.dismiss();
            }
        });
        ll_close_task= (LinearLayout) bottomDialog.findViewById(R.id.ll_close_task);
        ll_close_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }

    public static class ReceiveBoxDialog extends Dialog implements View.OnClickListener {
        TextView tvCancel,tv_sure,tv_box_no,tv_count_num;
        long id;
        Context ct;
        MoveDeliveryBoxListBean bean;

        public ReceiveBoxDialog(Context context) {
            this(context, R.style.dialog);
        }

        public ReceiveBoxDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_sure_receive);
            tv_count_num= (TextView) findViewById(R.id.tv_count_num);
            tv_box_no= (TextView) findViewById(R.id.tv_box_no);
            tv_sure= (TextView) findViewById(R.id.tv_sure);
            tv_sure.setOnClickListener(this);
            tvCancel= (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);
        }

        public void mInitShow(long id,Context ct,MoveDeliveryBoxListBean bean) {
            show();
            this.id=id;
            this.ct=ct;
            this.bean=bean;
            tv_box_no.setText(String.valueOf(bean.getBoxNo()));
            tv_count_num.setText(String.valueOf(bean.getNum()));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    dismiss();
                    break;
                case R.id.tv_sure:
                    ReceivingSyncNumNet net=new ReceivingSyncNumNet(ct);
                    net.setData(id,bean.getBoxId(),6);
                    dismiss();
                    break;
            }
        }
    }

}
