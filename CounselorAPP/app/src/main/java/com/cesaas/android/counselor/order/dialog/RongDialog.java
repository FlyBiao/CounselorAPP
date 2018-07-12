package com.cesaas.android.counselor.order.dialog;

import android.view.View;

import com.flybiao.materialdialog.MaterialDialog;

/**
 * Author FGB
 * Description
 * Created at 2018/7/11 16:07
 * Version 1.0
 */

public class RongDialog {

    public static void showDialog(int errorCode, MaterialDialog materialDialog){
        switch (errorCode){

        }
    }


    public static void showMsg( final MaterialDialog materialDialog,String msg){
        if(materialDialog!=null){
            materialDialog.setTitle("温馨提示！")
                    .setMessage(msg)
                    .setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            materialDialog.dismiss();
                        }
                    }).setNegativeButton("返回", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDialog.dismiss();
                }
            }).setCanceledOnTouchOutside(true).show();
        }
    }
}
