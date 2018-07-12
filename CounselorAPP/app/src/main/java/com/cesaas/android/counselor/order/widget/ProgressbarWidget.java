package com.cesaas.android.counselor.order.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import com.cesaas.android.counselor.order.R;

/**
 * 自定义百分比进度条
 * @author FGB
 *
 */
public class ProgressbarWidget extends View {

	private Paint paint = new Paint(); // 绘制背景灰色线条画笔
	private Paint paintText = new Paint(); // 绘制下载进度画笔
	private float offset = 0f; // 下载偏移量
	private float maxvalue = 0f; // 下载的总大小
	private float currentValue = 0f; // 下载了多少
	private Rect mBound = new Rect(); // 获取百分比数字的长宽
	private String percentValue = "zero%"; // 要显示的现在百分比
	private float offsetRight = 0f; // 灰色线条距离右边的距离
	private int textSize = 60; // 百分比的文字大小
	private float offsetTop = 30f; // 距离顶部的偏移量

	public ProgressbarWidget(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ProgressbarWidget(Context context, AttributeSet attribute) {
		super(context, attribute);
		getTextWidth();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 绘制底色
		paint.setColor(getResources().getColor(R.color.common_green_1));
		paint.setStrokeWidth(35);
		canvas.drawLine(0, offsetTop, getWidth(), offsetTop, paint);
		// 绘制进度条颜色
		paint.setColor(getResources().getColor(R.color.color_title_bar));
		paint.setStrokeWidth(35);
		canvas.drawLine(0, offsetTop, offset, offsetTop, paint);
		// 绘制白色区域及百分比
//		paint.setColor(getResources().getColor(R.color.white));
		paint.setStrokeWidth(5);
		paintText.setColor(getResources().getColor(R.color.color_title_bar));
		paintText.setTextSize(textSize);
		paintText.setAntiAlias(true);
		paintText.getTextBounds(percentValue, 0, percentValue.length(), mBound);
//		canvas.drawLine(offset, offsetTop, offset + mBound.width() + 4, offsetTop, paint);
		canvas.drawLine(offset, offsetTop, offset, offsetTop, paint);
		canvas.drawText(percentValue, offset, offsetTop + mBound.height() / 2 - 2, paintText);
	}

	/**
	 * 设置当前进度值
	 * 
	 * @param currentValue
	 */
	public void setCurrentValue(float currentValue) {
		this.currentValue = currentValue;
		float value = (float) (this.currentValue / maxvalue * 100);
		if (value < 100) {
			percentValue = value + "%";
		} else {
			percentValue = "100%";
		}
		initCurrentProgressBar();
		invalidate();
	}

	/**
	 * 设置最大值
	 * 
	 * @param maxValue
	 */
	public void setMaxValue(float maxValue) {
		this.maxvalue = maxValue;
	}

	/**
	 * 获取当前进度条长度
	 * 
	 * @param
	 * @param
	 * @return
	 */
	public void initCurrentProgressBar() {
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				if (currentValue < maxvalue) {
					offset = (getWidth() - offsetRight) * currentValue / maxvalue;
				} else {
					offset = getWidth() - offsetRight;
				}
			}
		});

	}

	/**
	 * 获取“100%”的宽度
	 */
	public void getTextWidth() {
		Paint paint = new Paint();
		Rect rect = new Rect();
		paint.setTextSize(textSize);
		paint.setAntiAlias(true);
		paint.getTextBounds("100%", 0, "100%".length(), rect);
		offsetRight = rect.width() + 5;
	}
}