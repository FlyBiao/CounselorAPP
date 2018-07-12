package com.cesaas.android.counselor.order.statistics;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cesaas.android.counselor.order.BasesActivity;
import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.utils.Skip;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 完成率对比
 */
public class CompleteContrastActivity extends BasesActivity {

    private LinearLayout llBack;
    private TextView tvBaseTitle;

    private LineChart chart;
    private LineDataSet dataSet;
    private  LineData data;
    private List<String> xVals = new ArrayList<String>();
    private List<Entry> yVals = new ArrayList<Entry>();
    private Random random = new Random();//产生随机数字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_contrast);
        initView();
        initData();
    }

    private void initView() {
        chart = (LineChart) findViewById(R.id.line_chart);
        llBack=(LinearLayout) findViewById(R.id.ll_base_title_back);
        tvBaseTitle=(TextView) findViewById(R.id.tv_base_title);
        setBack();
    }

    private void initData(){
        tvBaseTitle.setText("完成率对比");

            for(int i = 0 ; i<12; i++) {
                float x = random.nextInt(120);//获取value值
                yVals.add(new Entry(x, i));//创建Entry并且添加到Y值的list中，Y轴的值，一个entry代表一个显示的值
                xVals.add( (i+1) + "月");//横坐标显示xxx月
            }

            dataSet = new LineDataSet(yVals, "金额");//创建数据集并设置标签
            dataSet.setValueTextColor(Color.BLACK);//设置Value值的显示文字颜色，字体大小和字体种类，这里我没有添加对应字体可以自己修改
            dataSet.setLineWidth(2f);//设置曲线大小
            dataSet.setValueTextSize(10.0f);
            dataSet.setCircleSize(6f);

            data = new LineData(xVals, dataSet);//创建LineData,x轴List和Y轴数据集为参数

        chart.setData(data);//给图表添加数据
        chart.setDescription("");//设置图表描述的内容位置，字体等等
        chart.setDescriptionTextSize(15f);
        chart.zoom(1.5f, 1.0f, 0f, 0f);//开始时放大倍数（x轴,y轴,起点（x,y））,
        chart.setDescriptionPosition(540, 40);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的显示位置，通过XAxisPosition枚举类型来设置
        chart.getAxisRight().setEnabled(false);//关闭右边的Y轴，因为默认有两条，左边一条，右边一条，MPAndroidChart中有setEnabled方法的元素基本上都是使能的作用
        chart.getAxisLeft().setEnabled(true);
        chart.setBackgroundColor(getResources().getColor(R.color.white));
        chart.getXAxis().setGridColor(getResources().getColor(R.color.transparent));//设置网格不显示
        chart.animateY(1000);//动画效果，MPAndroidChart中还有很多动画效果可以挖掘
//        chart.setDrawGridBackground(true);
        chart.setDrawGridBackground(false);
    }

    /**
     * 设置返回上一个页面
     */
    public void setBack(){
        llBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Skip.mBack(mActivity);
            }
        });
    }
}
