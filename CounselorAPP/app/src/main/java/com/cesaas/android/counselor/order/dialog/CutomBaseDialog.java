package com.cesaas.android.counselor.order.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.cesaas.android.counselor.order.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;

/**
 * 自定义Dialog
 * Created by cebsaas on 2017/2/22.
 */

public class CutomBaseDialog {

    private Context mContext;

    public CutomBaseDialog(Context mContext){
        this.mContext=mContext;
    }

    /**
     * 通知对话框
     *
     * @param v
     */
    public void showNotifyDialog(View v) {
        /*初始化dialog构造器*/
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("通知对话框:");/*设置dialog的title*/
        builder.setMessage("通知对话框弹出了");/*设置dialog的内容*/
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {/*设置dialog确认按钮的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {/*设置dialog取消按钮的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);/*设置是否可以通过返回键消失, 默认true*/
        builder.show();/*显示dialog*/
        /* 第二种显示方法
        * AlertDialog dialog = builder.create();
        * dialog.show();
        */
    }

    /**
     * 显示列表对话框
     *
     * @param v
     */
    public void showOfficeListDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选中办事处");
        builder.setIcon(R.mipmap.ic_launcher);/*设置图标*/

        List<String> beans=new ArrayList<String>();
        beans.add("罗湖办事处");
        beans.add("南山办事处");
        beans.add("龙岗办事处");

        String[] items = new String[beans.size()];
        for (int i=0;i< beans.size();i++){
            //设置列表的内容;
            items[i]= beans.get(i);
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {/*设置列表的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                GetDialogValueBean bean=new GetDialogValueBean();
//                bean.setGetDialogValue(items[which]);
//                EventBus.getDefault().post(bean);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 显示列表对话框
     *
     * @param v
     */
    public void showListDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选中您要查询的区");
        builder.setIcon(R.mipmap.ic_launcher);/*设置图标*/
        List<String> beans=new ArrayList<String>();
        beans.add("华东地区");
        beans.add("华南地区");
        beans.add("海外地区");
        builder.setCancelable(true);

        String[] items = new String[beans.size()];
        for (int i=0;i< beans.size();i++){
            //设置列表的内容;
            items[i]= beans.get(i);
        }
        builder.setItems(items, new DialogInterface.OnClickListener() {/*设置列表的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Toast.makeText(mContext, beans[which], Toast.LENGTH_SHORT).show();
//                GetDialogValueBean bean=new GetDialogValueBean();
//                bean.setGetDialogValue(items[which]);
//                EventBus.getDefault().post(bean);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 显示单选对话框
     *
     * @param v
     */
    public void showSinpleChioceDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("选择性别:");
        builder.setIcon(R.mipmap.ic_launcher);
        final String[] items = new String[]{"男", "女", "妖"};
        builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {/*设置单选条件的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "选择了："+items[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 显示多选对话框
     *
     * @param v
     */
    public void showMultiChioceDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("于谦:");
        builder.setIcon(R.mipmap.ic_launcher);
        final String[] items = new String[]{"抽烟", "喝酒", "烫头"};/*设置多选的内容*/
        final boolean[] checkedItems = new boolean[]{false, true, false};/*设置多选默认状态*/
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {/*设置多选的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which] = isChecked;
                Toast.makeText(mContext, items[which] + " 状态: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "OK", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "CANCEL", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 显示进度对话框
     *
     * @param v
     */
//    public void showProgressDialog(View v) {
//        final ProgressDialog dialog = new ProgressDialog(mContext);
//        dialog.setTitle("提醒");/*设置进度对话框的title*/
//        dialog.setMessage("正在加载中,请稍后...");/*设置进度对话框的内容*/
//        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);/*设置进度对话框的样式*/
//        dialog.setMax(100);/*设置进度对话框的最大进度*/
//        dialog.setCancelable(false);
//        dialog.show();/*在show时, 别忘了初始化进度*/
//        dialog.setProgress(23);/*设置进度*/
//        new Thread() {
//            public void run() {
//                while (true) {
//                    if (dialog.getProgress() == 100)
//                        return;
//                    SystemClock.sleep(100);
//                    dialog.incrementProgressBy(1);/*更新进度,每一次以5来递增(dialog.setProgress(dialog.getProgress() + 5))*/
//                    if (dialog.getProgress() >= dialog.getMax()) {
//                        ArunOnUiThread(new Runnable() {/*在主线程执行*/
//                            @Override
//                            public void run() {
//                                Toast.makeText(mContext, "加载完毕", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        });
//                    }
//                }
//            }
//        }.start();
//    }
}
