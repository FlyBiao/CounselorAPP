package com.cesaas.android.counselor.order.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 瀑布流布局LinearLayout
 * 
 * @author Evan
 *
 */
public class AutoWrapLinearLayout extends LinearLayout {
	private int mWidth;// AutoWrapLinearLayout鎺т欢鐨勫
	private int mHeight;// AutoWrapLinearLayout鎺т欢鐨勯珮
	private int r;
	private int l;
	static final String TAG = "AutoWrapLinearLayout";

	public AutoWrapLinearLayout(Context context) {
		super(context);
	}

	public AutoWrapLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		this.r = r;
		this.l = l;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int cellWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);// 璁╄嚜鎺т欢鑷繁鎸夐渶鎾戝紑

		int cellHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);// 璁╄嚜鎺т欢鑷繁鎸夐渶鎾戝紑

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			try {
				childView.measure(cellWidthSpec, cellHeightSpec);
			} catch (NullPointerException e) {
				// 杩欎釜寮傚父涓嶅奖鍝嶅睍绀�
			}
		}
		foo();
		setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec), resolveSize(mHeight, heightMeasureSpec));

	}

	private void foo() {
		mWidth = r - l;// 瀹藉害鏄窡闅忕埗瀹瑰櫒鑰屽畾鐨�

		// 鑷韩鎺т欢鐨刾adding
		int paddingLeft = getPaddingLeft();
		int paddingRight = getPaddingRight();
		int paddingTop = getPaddingTop();
		int paddingBottom = getPaddingBottom();

		// 鎺т欢鑷韩鍙互琚敤鏉ユ樉绀鸿嚜鎺т欢鐨勫搴�
		int mDisplayWidth = mWidth - paddingLeft - paddingRight;

		// 鑷帶浠剁殑margin
		int cellMarginLeft = 0;
		int cellMarginRight = 0;
		int cellMarginTop = 0;
		int cellMarginBottom = 0;

		int x = 0;// 瀛愭帶浠剁殑榛樿宸︿笂瑙掑潗鏍噚
		int y = 0;// 瀛愭帶浠剁殑榛樿宸︿笂瑙掑潗鏍噛

		int totalWidth = 0;// 璁＄畻姣忎竴琛岄殢鐫�嚜鎺т欢鐨勫鍔犺�鍙樺寲鐨勫搴�
		int count = getChildCount();

		int cellWidth = 0;// 瀛愭帶浠剁殑瀹斤紝鍖呭惈padding
		int cellHeight = 0;// 鑷帶浠剁殑楂橈紝鍖呭惈padding

		int left = 0;// 瀛愭帶浠跺乏涓婅鐨勬í鍧愭爣
		int top = 0;// 瀛愭帶浠跺乏涓婅鐨勭旱鍧愭爣

		LayoutParams lp;

		for (int j = 0; j < count; j++) {
			final View childView = getChildAt(j);
			// 鑾峰彇瀛愭帶浠禼hild鐨勫楂�
			cellWidth = childView.getMeasuredWidth();// 娴嬮噺鑷帶浠跺唴瀹圭殑瀹藉害(杩欎釜鏃跺�padding鏈夎璁＄畻杩涘唴)
			cellHeight = childView.getMeasuredHeight();// 娴嬮噺鑷帶浠跺唴瀹圭殑楂樺害(杩欎釜鏃跺�padding鏈夎璁＄畻杩涘唴)

			lp = (LayoutParams) childView.getLayoutParams();
			cellMarginLeft = lp.leftMargin;
			cellMarginRight = lp.rightMargin;
			cellMarginTop = lp.topMargin;
			cellMarginBottom = lp.bottomMargin;

			// 鍔ㄦ�璁＄畻瀛愭帶浠跺湪涓�閲岄潰鍗犳嵁鐨勫搴�
			// 棰勫垽锛屽厛鍔犱笂涓嬩竴涓灞曠ず鐨勫瓙鎺т欢锛岃绠楄繖涓�澶熶笉澶熸斁
			totalWidth += cellMarginLeft + cellWidth + cellMarginRight;

			if (totalWidth >= mDisplayWidth) {// 涓嶅鏀剧殑鏃跺�闇�鎹㈣
				y += cellMarginTop + cellHeight + cellMarginBottom;// 鏂板涓�
				totalWidth = cellMarginLeft + cellWidth + cellMarginRight;// 杩欐椂鍊欒繖涓�鐨勫搴︿负杩欎釜瀛愭帶浠剁殑瀹藉害
				x = 0;// 閲嶇疆
			}

			// 璁＄畻椤剁偣妯潗鏍�
			left = x + cellMarginLeft + paddingLeft;

			// 璁＄畻椤剁偣绾靛潗鏍�
			top = y + cellMarginTop + paddingTop;

			// 缁樺埗鑷帶浠�
			childView.layout(left, top, left + cellWidth, top + cellHeight);

			// 璁＄畻涓嬩竴涓í鍧愭爣
			x += cellMarginLeft + cellWidth + cellMarginRight;

		}
		mHeight = paddingTop + y + cellMarginTop + cellHeight + cellMarginBottom + paddingBottom;
	}
}