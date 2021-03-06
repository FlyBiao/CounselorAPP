package com.cesaas.android.counselor.order.widget.gridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 解决在scrollview中只显示第一行数据的问题
 * @author FGB
 *
 */
public class CostomMangerGridView extends GridView {
	public CostomMangerGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CostomMangerGridView(Context context) {
		super(context);
	}

	public CostomMangerGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	

}
