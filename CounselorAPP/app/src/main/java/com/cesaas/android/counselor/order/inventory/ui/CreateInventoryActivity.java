package com.cesaas.android.counselor.order.inventory.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.custom.pop.InitSpinerPopWindow;
import com.cesaas.android.counselor.order.custom.pop.SpinerListBean;
import com.cesaas.android.counselor.order.custom.pop.SpinerPopWindow;
import com.cesaas.android.counselor.order.inventory.bean.ResultGetShopAllBean;
import com.cesaas.android.counselor.order.inventory.net.GetShopAllNet;
import com.cesaas.android.counselor.order.utils.AbDateUtil;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.utils.ToastFactory;
import com.cesaas.android.java.bean.inventory.ResultInventoryAddBean;
import com.cesaas.android.java.bean.inventory.ResultInventoryEditSaveBean;
import com.cesaas.android.java.net.inventory.InventoryAddNet;
import com.cesaas.android.java.net.inventory.InventorySaveNet;
import com.sing.datetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 新建盘点单
 */
public class CreateInventoryActivity extends BasesActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{
    private TextView tvTitle,tvLeftTitle,tvRightTitle;
    private TextView tv_select_value,tv_overall,tv_suction;
    private TextView tv_create_inventory_data;
    private LinearLayout llBack;
    private String leftTitle;
    private ArrayList<TextView> tvs=new ArrayList<TextView>();

    private SpinerPopWindow<String> mSpinerPopWindow;
    private List<SpinerListBean> list;
    private InventoryAddNet inventoryNet;
    private GetShopAllNet getShopAllNet;
    private InventorySaveNet inventorySaveNet;

    private int id;
    private int type;
    private int inventoryType=0;//0全盘  1抽盘
    private String shopName;
    private String shopId;
    private String createData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            id=bundle.getInt("id");
            type=bundle.getInt("type");
        }
        Intent intent=getIntent();
        leftTitle = intent.getStringExtra("leftTitle");

        inventorySaveNet=new InventorySaveNet(mContext,2);
        inventoryNet=new InventoryAddNet(mContext);
        getShopAllNet=new GetShopAllNet(mContext);

        initView();
        initData();
}

    private void initData(){
//        getShopAllNet.setData();

        shopName=prefs.getString("shopName");
        shopId=prefs.getString("ShopId");
        createData=AbDateUtil.getCurrentDate("yyyy-MM-dd");

        tv_create_inventory_data.setText(createData);
        if(shopName!=null){
            tv_select_value.setText(shopName);
        }else{
            tv_select_value.setText("请选择店铺");
        }
    }

    /**
     * 接收所有店铺数据信息
     * @param msg
     */
    public void onEventMainThread(ResultGetShopAllBean msg) {
        if(msg.IsSuccess!=false && msg.TModel.size()!=0){
            list = new ArrayList<>();
            for (int i=0;i<msg.TModel.size();i++){
                SpinerListBean bean=new SpinerListBean();
                bean.setName(msg.TModel.get(i).getShopName());
                bean.setId(msg.TModel.get(i).getShopId());
                list.add(bean);
            }

            InitSpinerPopWindow initSpinerPopWindow=new InitSpinerPopWindow(mContext,tv_select_value);
            mSpinerPopWindow = new SpinerPopWindow<>(this, list,itemClickListener);
            tv_select_value.setOnClickListener(initSpinerPopWindow.showPopupWindow(mSpinerPopWindow));
            mSpinerPopWindow.setOnDismissListener(initSpinerPopWindow.dismissListener);

        }else{
            ToastFactory.getLongToast(mContext,"获取店铺信息失败！"+msg.Message);
        }
    }


    /**
     * 接收编辑盘点单
     * @param msg
     */
    public void onEventMainThread(ResultInventoryEditSaveBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments!=null && msg.arguments.resp.errorCode ==1) {
                ToastFactory.getLongToast(mContext, "编辑点单成功！");
                Skip.mNext(mActivity, InventoryMainActivity.class, true);
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * 接收新建盘点单结果数据
     * @param msg
     */
    public void onEventMainThread(ResultInventoryAddBean msg) {
        if(msg.getError()!=null){
            ToastFactory.getLongToast(mContext,msg.getError().getCode()+"："+msg.getError().getMessage());
        }else {
            if (msg.arguments!=null && msg.arguments.resp.errorCode ==1) {
                ToastFactory.getLongToast(mContext, "新建盘点单成功！");
                Intent mIntent = new Intent();
                setResult(10, mIntent);// 设置结果，并进行传送
                finish();
            } else {
                ToastFactory.getLongToast(mContext, "失败 errorCode:" + msg.arguments.resp.errorCode+"  "+msg.arguments.resp.errorMessage);
            }
        }
    }

    /**
     * popupwindow显示的ListView的item点击事件
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mSpinerPopWindow.dismiss();
            tv_select_value.setText(list.get(position).getName());
            shopId=list.get(position).getId()+"";
            shopName=list.get(position).getName();
        }
    };

    @Override
    public void onClick(View v) {
        int index=-1;
        switch (v.getId()){
            case R.id.tv_overall:
                index=0;
                inventoryType=0;
                break;
            case R.id.tv_suction:
                index=1;
                inventoryType=1;
                break;
        }
        setColor(index);
    }

    private void setColor(int index) {
        for(int i=0;i<tvs.size();i++){
            tvs.get(i).setTextColor(mContext.getResources().getColor(R.color.new_base_bg));
            tvs.get(i).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_hui_bg));
        }
        tvs.get(index).setTextColor(mContext.getResources().getColor(R.color.text_color_gray_3));
        tvs.get(index).setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_org_bg));
    }

    private void initView(){
        llBack= (LinearLayout) findViewById(R.id.ll_base_title_back);
        tvRightTitle= (TextView) findViewById(R.id.tv_base_title_right);
        tvRightTitle.setVisibility(View.VISIBLE);
        tvRightTitle.setText("确定");
        tvTitle= (TextView) findViewById(R.id.tv_base_title);
        tvTitle.setText("新建盘点单");
        tvLeftTitle= (TextView) findViewById(R.id.tv_base_title_left);
        tvLeftTitle.setText(leftTitle);

        tv_create_inventory_data=(TextView) findViewById(R.id.tv_create_inventory_data);
        tv_create_inventory_data.setOnClickListener(this);
        tv_select_value= (TextView) findViewById(R.id.tv_select_value);
        tv_overall= (TextView) findViewById(R.id.tv_overall);
        tv_overall.setOnClickListener(this);
        tv_suction= (TextView) findViewById(R.id.tv_suction);
        tv_suction.setOnClickListener(this);
        tvs.add(tv_suction);
        tvs.add(tv_overall);

        tvRightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==2){//编辑
                    inventorySaveNet.setData(Long.parseLong(id+""),Long.parseLong(shopId),createData+" 23:59:59",inventoryType);
                }else{//新增
                    inventoryNet.setData(Long.parseLong(shopId),shopName,createData+" 23:59:59",inventoryType);
                }
            }
        });
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
        tv_create_inventory_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateSelect(tv_create_inventory_data);
            }
        });
    }


    /**
     * 日期选择选择
     * @param v
     */
    public void getDateSelect(View v){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(CreateInventoryActivity.this,now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
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
        tv_create_inventory_data.setText(date);
    }
}
