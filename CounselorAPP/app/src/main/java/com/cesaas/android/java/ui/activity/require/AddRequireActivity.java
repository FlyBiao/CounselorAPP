package com.cesaas.android.java.ui.activity.require;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.counselor.order.widget.MClearEditText;
import com.cesaas.android.java.bean.ShopListBean;
import com.cesaas.android.java.bean.require.RequireListBean;
import com.cesaas.android.java.bean.require.ResultRequireAddBean;
import com.cesaas.android.java.net.require.RequireAddNet;
import com.cesaas.android.java.ui.activity.SelectShopActivity;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Author FGB
 * Description 新增退货单
 * Created at 2018/5/24 18:01
 * Version 1.0
 */

public class AddRequireActivity extends BasesActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private TextView tvTitle;
    private TextView tv_date;
    private LinearLayout llBack;
    private LinearLayout ll_date,ll_send_shop,ll_receive_shop,tv_select_trans_type,ll_requier_shop;
    private TextView tv_send_shop,tv_receive_shop,tv_requier_shop;
    private TextView tv_cancel,tv_sure,tv_trans_type;
    private MClearEditText et_remark;

    private int requestCode=10;
    private int billOrderType=0;//1:订货2: 补货3： 铺货
    private int shopType=1;//1发货店铺 2收货店铺
    private long originShopId;
    private long originOrganizationId;
    private long receiveShopId;
    private long receiveOrganizationId;
    private String shipmentsDate;

    private RequireAddNet requireAddNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_requier);
        initView();
        initData();
    }


    /**
     * 接收新增补货
     * @param msg
     */
    public void onEventMainThread(ResultRequireAddBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments != null && msg.arguments.resp.errorCode == 1) {
                ToastFactory.getLongToast(mContext, "新增补货成功！");
                Intent mIntent = new Intent();
                mIntent.putExtra("result", "true");
                setResult(10, mIntent);// 设置结果，并进行传送
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    private void initView() {
        ll_requier_shop= (LinearLayout) findViewById(R.id.ll_requier_shop);
        ll_requier_shop.setOnClickListener(this);
        tv_requier_shop= (TextView) findViewById(R.id.tv_requier_shop);
        tv_trans_type= (TextView) findViewById(R.id.tv_trans_type);
        et_remark= (MClearEditText) findViewById(R.id.et_remark);
        ll_date= (LinearLayout) findViewById(R.id.ll_date);
        tv_select_trans_type= (LinearLayout) findViewById(R.id.tv_select_trans_type);
        tv_select_trans_type.setOnClickListener(this);
        ll_receive_shop= (LinearLayout) findViewById(R.id.ll_receive_shop);
        ll_receive_shop.setOnClickListener(this);
        ll_send_shop= (LinearLayout) findViewById(R.id.ll_send_shop);
        ll_send_shop.setOnClickListener(this);
        ll_date.setOnClickListener(this);
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        llBack.setOnClickListener(this);
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("新增补货");
        tv_date= (TextView) findViewById(R.id.tv_date);
        tv_send_shop= (TextView) findViewById(R.id.tv_send_shop);
        tv_receive_shop= (TextView) findViewById(R.id.tv_receive_shop);
        tv_cancel= (TextView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
    }

    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                Skip.mBack(mActivity);
                break;
            case R.id.tv_sure:
                RequireListBean requireListBean=new RequireListBean();
                requireListBean.setOriginOrganizationId(originOrganizationId);
                requireListBean.setOriginShopId(originShopId);
                requireListBean.setReceiveShopId(receiveShopId);
                requireListBean.setReceiveOrganizationId(receiveOrganizationId);
                requireListBean.setType(2);
                requireListBean.setRemark(et_remark.getText().toString());
                requireListBean.setDate(tv_date.getText().toString());
                requireAddNet=new RequireAddNet(mContext);
                requireAddNet.setData(billOrderType,requireListBean.getRequireInfo());
                break;
            case R.id.ll_base_title_back:
                Skip.mBack(mActivity);
                break;

            case R.id.ll_date:
                getDateSelect(ll_date);
                break;
            case R.id.tv_select_trans_type:
                BaseDialog dialog=new BaseDialog(mContext);
                dialog.mInitShow();
                break;
            case R.id.ll_send_shop:
                Intent intent1 = new Intent(mContext, SelectShopActivity.class);
                intent1.putExtra("Type",String.valueOf(shopType));
                intent1.putExtra("BillType",String.valueOf(5));
                startActivityForResult(intent1, requestCode);
                break;
            case R.id.ll_requier_shop:
                shopType=2;
                Intent intent = new Intent(mContext, SelectShopActivity.class);
                intent.putExtra("Type",String.valueOf(shopType));
                intent.putExtra("BillType",String.valueOf(5));
                startActivityForResult(intent, requestCode);
                break;
        }
    }


    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(AddRequireActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dpd.setThemeDark(false);// boolean,DarkTheme
        dpd.vibrate(true);// boolean,触摸震动
        dpd.dismissOnPause(false);// boolean,Pause时是否Dismiss
        dpd.showYearPickerFirst(false);// boolean,先选择年
        if (true) {// boolean,自定义颜色
            dpd.setAccentColor(getResources().getColor(R.color.new_base_bg));
        }
        if (true) {// boolean,设置标题
            dpd.setTitle("日期选择");
        }
        if (false) {// boolean,只能选择某些日期
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.MONTH, i);
                dates[i + 6] = date;
            }
            dpd.setSelectableDays(dates);
        }
        if (true) {// boolean,部分高亮
            Calendar[] dates = new Calendar[13];
            for (int i = -6; i <= 6; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.WEEK_OF_YEAR, i);
                dates[i + 6] = date;
            }
            dpd.setHighlightedDays(dates);
        }
        if (false) {// boolean,某些日期不可选
            Calendar[] dates = new Calendar[3];
            for (int i = -1; i <= 1; i++) {
                Calendar date = Calendar.getInstance();
                date.add(Calendar.DAY_OF_MONTH, i);
                dates[i + 1] = date;
            }
            dpd.setDisabledDays(dates);
        }
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (++monthOfYear) + "-" + dayOfMonth;
        shipmentsDate=date;
        tv_date.setText(date);
    }

    /**
     * 选择调拨类型
     */
    public class BaseDialog extends Dialog implements View.OnClickListener {
        TextView tvCancel,tv_place_order,tv_fill_goods;

        public BaseDialog(Context context) {
            this(context, R.style.dialog);
        }

        public BaseDialog(Context context, int dialog) {
            super(context, dialog);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            setContentView(R.layout.dialog_requier_type);
            tv_fill_goods= (TextView) findViewById(R.id.tv_fill_goods);
            tv_fill_goods.setOnClickListener(this);
            tv_place_order= (TextView) findViewById(R.id.tv_place_order);
            tv_place_order.setOnClickListener(this);
            tvCancel= (TextView) findViewById(R.id.tv_cancel);
            tvCancel.setOnClickListener(this);

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
                case R.id.tv_place_order:
                    tv_trans_type.setText("按最近销售");
                    billOrderType=1;
                    dismiss();
                    break;
                case R.id.tv_fill_goods:
                    tv_trans_type.setText("普通补货");
                    billOrderType=0;
                    dismiss();
                    break;

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){//
            if(data!=null){
                ShopListBean bean= (ShopListBean) data.getSerializableExtra("result");
                if(shopType==2){
                    tv_receive_shop.setText(bean.getOrganizationName()+" - "+bean.getShopName());
                    receiveShopId=bean.getId();
                    receiveOrganizationId=bean.getOrganizationId();
                }else{
                    tv_send_shop.setText(bean.getOrganizationName()+" - "+bean.getShopName());
                    originShopId=bean.getId();
                    originOrganizationId=bean.getOrganizationId();
                }
            }
        }
    }

}
