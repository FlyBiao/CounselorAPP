package com.cesaas.android.java.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.global.ActionPower;
import com.cesaas.android.counselor.order.inventory.ui.CheckInventoryDifferenceActivity;
import com.cesaas.android.counselor.order.inventory.ui.InventoryDetailsActivity;
import com.cesaas.android.counselor.order.power.bean.MenuDataBean;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.java.bean.inventory.InventoryListBeanBean;
import com.cesaas.android.java.net.inventory.InventoryCheckNet;
import com.cesaas.android.java.net.inventory.InventoryConfirmDifferenceNet;
import com.cesaas.android.java.net.inventory.InventoryCreateDifferenceNet;
import com.cesaas.android.java.net.inventory.InventoryDeletesNet;
import com.cesaas.android.java.net.inventory.InventoryRejectNet;
import com.cesaas.android.java.net.inventory.InventorySaveNet;
import com.cesaas.android.java.net.inventory.InventorySubmitNet;

import java.util.ArrayList;
import java.util.List;

/**
 * Author FGB
 * Description 盘点单权限
 * Created at 2018/6/27 13:40
 * Version 1.0
 */

public class InventoryPowerUtils {
    public static List<MenuDataBean> menuList=new ArrayList<>();

    public static List<MenuDataBean> getInventoryPower(int status){
        menuList=new ArrayList<>();
        switch (status){
            case 0://未生成差异
                MenuDataBean create=new MenuDataBean();
                create.setMenuName("生成差异");
                create.setStatus(0);
                create.setMenuImage(R.string.fa_puzzle_piece);
                menuList.add(create);
                MenuDataBean edit=new MenuDataBean();
                edit.setMenuName("编辑");
                edit.setStatus(1);
                edit.setMenuImage(R.string.fa_pencil);
                menuList.add(edit);
                MenuDataBean del=new MenuDataBean();
                del.setMenuName("删除");
                del.setStatus(2);
                del.setMenuImage(R.string.fa_del_o);
                menuList.add(del);
                MenuDataBean remark=new MenuDataBean();
                remark.setMenuName("备注");
                remark.setStatus(8);
                remark.setMenuImage(R.string.fa_tag);
                menuList.add(remark);
                break;
            case 10://已生成差异
                MenuDataBean check1=new MenuDataBean();
                check1.setMenuName("查看差异");
                check1.setStatus(3);
                check1.setMenuImage(R.string.fa_search);
                menuList.add(check1);
                MenuDataBean sure=new MenuDataBean();
                sure.setMenuName("确认差异");
                sure.setStatus(4);
                sure.setMenuImage(R.string.fa_check);
                menuList.add(sure);
                MenuDataBean remark1=new MenuDataBean();
                remark1.setMenuName("备注");
                remark1.setStatus(8);
                remark1.setMenuImage(R.string.fa_tag);
                menuList.add(remark1);
                break;
            case 20://已确认差异
                MenuDataBean check2=new MenuDataBean();
                check2.setMenuName("查看差异");
                check2.setStatus(3);
                check2.setMenuImage(R.string.fa_search);
                menuList.add(check2);
                MenuDataBean submit1=new MenuDataBean();
                submit1.setMenuName("提交");
                submit1.setStatus(5);
                submit1.setMenuImage(R.string.fa_check);
                menuList.add(submit1);
                break;
            case 30://已提交
                MenuDataBean check4=new MenuDataBean();
                check4.setMenuName("查看差异");
                check4.setStatus(3);
                check4.setMenuImage(R.string.fa_glass);
                menuList.add(check4);
                MenuDataBean sureCheck=new MenuDataBean();
                sureCheck.setMenuName("确认");
                sureCheck.setStatus(6);
                sureCheck.setMenuImage(R.string.fa_check);
                menuList.add(sureCheck);
                MenuDataBean reject=new MenuDataBean();
                reject.setMenuName("驳回");
                reject.setStatus(7);
                reject.setMenuImage(R.string.fa_times);
                menuList.add(reject);
                break;
            case 40://已确认
                MenuDataBean check3=new MenuDataBean();
                check3.setMenuName("查看差异");
                check3.setStatus(3);
                check3.setMenuImage(R.string.fa_glass);
                menuList.add(check3);
                break;
            case 50://已驳回
                MenuDataBean create1=new MenuDataBean();
                create1.setMenuName("生成差异");
                create1.setStatus(0);
                create1.setMenuImage(R.string.fa_puzzle_piece);
                menuList.add(create1);
                MenuDataBean edit1=new MenuDataBean();
                edit1.setMenuName("编辑");
                edit1.setStatus(1);
                edit1.setMenuImage(R.string.fa_pencil);
                menuList.add(edit1);
                MenuDataBean del1=new MenuDataBean();
                del1.setMenuName("删除");
                del1.setStatus(2);
                del1.setMenuImage(R.string.fa_del_o);
                menuList.add(del1);
                break;
        }
        return menuList;
    }

    public static void skipInventory(int status, Context mContext, Activity mActivity, Bundle bundle, InventoryListBeanBean bean){
        switch (status){
            case 0:
                InventoryCreateDifferenceNet createDifferenceNet = new InventoryCreateDifferenceNet(mContext);
                createDifferenceNet.setData(bean.getId());
                break;
            case 1:
                bundle.putString("id",String.valueOf(bean.getId()));
                bundle.putInt("type",1);
                Skip.mNextFroData(mActivity, InventoryDetailsActivity.class,bundle);
                break;
            case 2:
                InventoryDeletesNet deletesNet=new InventoryDeletesNet(mContext);
                deletesNet.setData(FilterConditionDateUtils.getArrayIds(bean.getId()));
                break;
            case 3:
                bundle.putString("pid", String.valueOf(bean.getId()));
                bundle.putInt("Status",bean.getStatus());
                Skip.mNextFroData(mActivity, CheckInventoryDifferenceActivity.class, bundle);
                break;
            case 4:
                InventoryConfirmDifferenceNet confirmDifferenceNet=new InventoryConfirmDifferenceNet(mContext);
                confirmDifferenceNet.setData(bean.getId());
                break;
            case 5:
                InventorySubmitNet inventorySubmitNet=new InventorySubmitNet(mContext);
                inventorySubmitNet.setData(bean.getId());
                break;
            case 6:
                InventoryCheckNet inventoryCheckNet=new InventoryCheckNet(mContext);
                inventoryCheckNet.setData(bean.getId(),bean.getStatus());
                break;
            case 7:
                InventoryRejectNet inventoryRejectNet=new InventoryRejectNet(mContext);
                inventoryRejectNet.setData(bean.getId());
                break;
            case 8:
                setInventoryRemarkDialog(mContext,mActivity,bean);
                break;
        }
    }

    public static TextView tv_remark;
    public static EditText et_service_remark;
    private static LinearLayout ll_service_remark;
    public static View dialogContentView;
    private static Dialog bottomDialog;
    /**
     * 盘点单备注
     * @param mContext
     * @param mActivity
     * @param bean
     * @param
     */
    public static void setInventoryRemarkDialog(final Context mContext, final Activity mActivity, final InventoryListBeanBean bean){
        bottomDialog = new Dialog(mContext, R.style.BottomDialog);
        dialogContentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_member_service_remark, null);
        bottomDialog.setContentView(dialogContentView);
        ViewGroup.LayoutParams layoutParams = dialogContentView.getLayoutParams();
        layoutParams.width = mContext.getResources().getDisplayMetrics().widthPixels;
        dialogContentView.setLayoutParams(layoutParams);

        ll_service_remark= (LinearLayout) bottomDialog.findViewById(R.id.ll_service_remark);
        tv_remark= (TextView) bottomDialog.findViewById(R.id.tv_remark);
        et_service_remark= (EditText) bottomDialog.findViewById(R.id.et_service_remark);
        tv_remark.setText("盘点单备注");
        ll_service_remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
                InventorySaveNet saveNet=new InventorySaveNet(mContext,1);
                saveNet.setData(bean.getId(),et_service_remark.getText().toString());
            }
        });

        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.show();
    }
}
