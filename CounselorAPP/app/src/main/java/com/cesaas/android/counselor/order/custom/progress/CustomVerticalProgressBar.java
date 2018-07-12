package com.cesaas.android.counselor.order.custom.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.cesaas.android.counselor.order.R;

/**
 * Author FGB
 * Description 自定义垂直进度条 ProgressBar
 * Created 2017/3/14 16:17
 * Version 1.zero
 */
public class CustomVerticalProgressBar extends View {

    private Paint paint;// 画笔
    private float progress;// 进度值
    private int width;// 宽度值
    private int height;// 高度值

    public CustomVerticalProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomVerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomVerticalProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth() - 1;// 宽度值
        height = getMeasuredHeight() - 1;// 高度值
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(getResources().getColor(R.color.base_text_color));// 设置画笔颜色
        canvas.drawRect(0, height - progress / 100f * height, width, height,
                paint);// 画矩形

        super.onDraw(canvas);
    }

    /** 设置progressbar进度 */
    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }
}
