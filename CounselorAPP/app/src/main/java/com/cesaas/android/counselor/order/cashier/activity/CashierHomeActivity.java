package com.cesaas.android.counselor.order.cashier.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.cesaas.android.counselor.order.widget.gridview.MyGridView;

public class CashierHomeActivity extends BasesActivity implements View.OnClickListener{

    private MyGridView gridview;
    private TextView tv_title;
    private TextView tv_title_left;
    private TextView tv_title_right;
    private TextView tv_sum;
    private TextView tv_clear;
    private TextView tv_subtract;
    private TextView tv_proceeds;
    private TextView tv_point;
    private TextView tv_zero;
    private ImageView iv_remark;

    private long exitTime = 0; // 退出点击间隔时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_home);
        initView();
        mClick();
    }

    private void initView() {
        tv_sum= (TextView) findViewById(R.id.tv_sum);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title_left= (TextView) findViewById(R.id.tv_title_left);
        tv_title_right= (TextView) findViewById(R.id.tv_title_right);
        tv_title.setText("收银");
        tv_title_left.setText("返回");
        tv_title_left.setOnClickListener(this);
        tv_title_right.setBackgroundDrawable(getResources().getDrawable(R.drawable.scanss));

        tv_clear= (TextView) findViewById(R.id.tv_clear);
        tv_clear.setOnClickListener(this);
        tv_subtract= (TextView) findViewById(R.id.tv_subtract);
        tv_subtract.setOnClickListener(this);
        tv_proceeds= (TextView) findViewById(R.id.tv_proceeds);
        tv_proceeds.setOnClickListener(this);
        tv_point= (TextView) findViewById(R.id.tv_point);
        tv_point.setOnClickListener(this);
        tv_zero= (TextView) findViewById(R.id.tv_zero);
        tv_zero.setOnClickListener(this);
        iv_remark= (ImageView) findViewById(R.id.iv_remark);
        iv_remark.setOnClickListener(this);

        gridview=(MyGridView) findViewById(R.id.gridview);
        gridview.setAdapter(new CashierGridAdapter(this));


    }

    public void mClick(){
        //九宫格点击事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                switch (position) {
                    case 0:
                        tv_sum.setText("1");
                        break;
                    case 1:
                        tv_sum.setText("2");
                        break;

                    case 2:
                        tv_sum.setText("4");
                        break;
                    case 3:
                        tv_sum.setText("4");
                        break;
                    case 4:
                        tv_sum.setText("5");
                        break;
                    case 5:
                        tv_sum.setText("6");
                        break;
                    case 6:
                        tv_sum.setText("7");
                        break;
                    case 7:
                        tv_sum.setText("8");
                        break;
                    case 8:
                        tv_sum.setText("9");
                        break;

                    default:
                        break;
                }
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_clear:
                tv_sum.setText("");
                break;
            case R.id.tv_subtract:
                break;
            case R.id.tv_proceeds:
                Bundle bundle=new Bundle();
                bundle.putString("money",tv_sum.getText().toString());
                Skip.mNextFroData(mActivity, CashierActivity.class,bundle);
                break;
            case R.id.tv_point:
                tv_sum.setText(".");
                break;
            case R.id.tv_zero:
                tv_sum.setText("zero");
                break;
            case R.id.iv_remark:
                Skip.mNext(mActivity, CashierRemarkActivity.class);
                break;
                
            case R.id.tv_title_left:
            	Skip.mBack(mActivity);
            	break;
        }
    }

    /**
     * 退出应用
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    for (int i = 0; i < BasesActivity.activityList.size(); i++) {
                        if (null != BasesActivity.activityList.get(i)) {
                            Skip.mBack(BasesActivity.activityList.get(i));
                        }
                    }
                    Skip.mBack(this);
                }
                return true;
            } catch (Exception e) {
                Skip.mBack(this);
                e.printStackTrace();
            }
        }
        return false;
    }
}
