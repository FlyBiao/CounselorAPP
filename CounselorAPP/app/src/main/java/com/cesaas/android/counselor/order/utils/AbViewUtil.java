package com.cesaas.android.counselor.order.utils;

import java.lang.reflect.Field;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cesaas.android.counselor.order.global.Constant;

/**
 * 
 * @author Evan
 * @date 2015-9-19
 *
 */
public class AbViewUtil {

	/**
	 * SparseArray这个类，优化过的存储integer和object键值对的hashmap 只需静态调用get这个方法填入当前Adapter的
	 * convertView 与 子控件的id,就可以实现复用。
	 * 
	 * 使用：if(convertView==null) convertView =
	 * inflat.inflater(R.layout.mlist,parent,false); ImageView img =
	 * ABViewUtil.getVH(convertView,R.id.list_img); return convertView;
	 * 
	 * @param convertView
	 * @param id
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T getVH(View convertView, int id) {
		SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
		if (holder == null) {
			holder = new SparseArray<View>();
			convertView.setTag(holder);
		}
		View childView = holder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			holder.put(id, childView);
		}
		return (T) childView;
	}

	/**
	 * view设置background drawable
	 *
	 * @param view
	 * @param drawable
	 */
	@SuppressLint("NewApi")
	public static void setBackgroundDrawable(View view, Drawable drawable) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackgroundDrawable(drawable);
		} else {
			view.setBackground(drawable);
		}
	}

	/**
	 * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
	 *
	 * @param view
	 * @return
	 */
	public static int getViewMeasuredHeight(View view) {
		// int height = view.getMeasuredHeight();
		// if(zero < height){
		// return height;
		// }
		calcViewMeasure(view);
		return view.getMeasuredHeight();
	}

	/**
	 * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
	 *
	 * @param view
	 * @return
	 */
	public static int getViewMeasuredWidth(View view) {
		// int width = view.getMeasuredWidth();
		// if(zero < width){
		// return width;
		// }
		calcViewMeasure(view);
		return view.getMeasuredWidth();
	}

	/**
	 * 测量控件的尺寸
	 *
	 * @param view
	 */
	public static void calcViewMeasure(View view) {
		// int width =
		// View.MeasureSpec.makeMeasureSpec(zero,View.MeasureSpec.UNSPECIFIED);
		// int height =
		// View.MeasureSpec.makeMeasureSpec(zero,View.MeasureSpec.UNSPECIFIED);
		// view.measure(width,height);

		int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		view.measure(width, expandSpec);
	}

	public static int getAllListViewSectionCounts(ListView lv, List dataSource) {
		if (null == lv || AbStrUtil.isEmpty(dataSource)) {
			return 0;
		}
		return dataSource.size() + lv.getHeaderViewsCount() + lv.getFooterViewsCount();
	}

	/**
	 * 使用ColorFilter来改变亮度
	 *
	 * @param imageview
	 * @param brightness
	 */
	public static void changeBrightness(ImageView imageview, float brightness) {
		imageview.setColorFilter(getBrightnessMatrixColorFilter(brightness));
	}

	public static void changeBrightness(Drawable drawable, float brightness) {
		drawable.setColorFilter(getBrightnessMatrixColorFilter(brightness));
	}

	private static ColorMatrixColorFilter getBrightnessMatrixColorFilter(float brightness) {
		ColorMatrix matrix = new ColorMatrix();
		matrix.set(
				new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		return new ColorMatrixColorFilter(matrix);
	}

	/**
	 * 设置PX padding.
	 *
	 * @param view
	 *            the view
	 * @param left
	 *            the left padding in pixels
	 * @param top
	 *            the top padding in pixels
	 * @param right
	 *            the right padding in pixels
	 * @param bottom
	 *            the bottom padding in pixels
	 */
	public static void setPadding(View view, int left, int top, int right, int bottom) {
		int scaledLeft = scaleValue(view.getContext(), left);
		int scaledTop = scaleValue(view.getContext(), top);
		int scaledRight = scaleValue(view.getContext(), right);
		int scaledBottom = scaleValue(view.getContext(), bottom);
		view.setPadding(scaledLeft, scaledTop, scaledRight, scaledBottom);
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scaleValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		// 为了兼容尺寸小密度大的情况
		if (mDisplayMetrics.scaledDensity > Constant.UI_DENSITY) {
			// 密度
			if (mDisplayMetrics.widthPixels > Constant.UI_WIDTH) {
				value = value * (1.3f - 1.0f / mDisplayMetrics.scaledDensity);
			} else if (mDisplayMetrics.widthPixels < Constant.UI_WIDTH) {
				value = value * (1.0f - 1.0f / mDisplayMetrics.scaledDensity);
			}
		}
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels, value);
	}

	/**
	 * 描述：根据屏幕大小缩放.
	 *
	 * @param displayWidth
	 *            the display width
	 * @param displayHeight
	 *            the display height
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scale(int displayWidth, int displayHeight, float pxValue) {
		if (pxValue == 0) {
			return 0;
		}
		float scale = 1;
		try {
			float scaleWidth = (float) displayWidth / Constant.UI_WIDTH;
			float scaleHeight = (float) displayHeight / Constant.UI_HEIGHT;
			scale = Math.min(scaleWidth, scaleHeight);
		} catch (Exception e) {
		}
		return Math.round(pxValue * scale + 0.5f);
	}

	/**
	 * 缩放文字大小,这样设置的好处是文字的大小不和密度有关， 能够使文字大小在不同的屏幕上显示比例正确
	 * 
	 * @param textView
	 *            button
	 * @param sizePixels
	 *            px值
	 * @return
	 */
	public static void setTextSize(TextView textView, float sizePixels) {
		float scaledSize = scaleTextValue(textView.getContext(), sizePixels);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledSize);
	}

	/**
	 * 描述：根据屏幕大小缩放文本.
	 *
	 * @param context
	 *            the context
	 * @param pxValue
	 *            the px value
	 * @return the int
	 */
	public static int scaleTextValue(Context context, float value) {
		DisplayMetrics mDisplayMetrics = AbAppUtil.getDisplayMetrics(context);
		// 为了兼容尺寸小密度大的情况
		if (mDisplayMetrics.scaledDensity > 2) {
			// 缩小到密度分之一
			// value = value*(1.1f - 1.0f/mDisplayMetrics.scaledDensity);
		}
		return scale(mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels, value);
	}

	/**
	 * 测量这个view 最后通过getMeasuredWidth()获取宽度和高度.
	 * 
	 * @param view
	 *            要测量的view
	 * @return 测量过的view
	 */
	public static void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 获取Listview的高度，然后设置ViewPager的高度
	 * 
	 * @param listView
	 * @return
	 */
	public static int setListViewHeightBasedOnChildren1(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return 0;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
		return params.height;
	}

	/**
	 * 获取GridView的高度，然后设置ViewPager的高度
	 * 
	 * @param gridView
	 * @return
	 */
	public static int setGridViewHeightBasedOnChildren(GridView gridView) {
		// 获取GridView对应的Adapter
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return 0;
		}
		int rows;
		int columns = 0;
		int horizontalBorderHeight = 0;
		Class<?> clazz = gridView.getClass();
		try {
			// 利用反射，取得每行显示的个数
			Field column = clazz.getDeclaredField("mRequestedNumColumns");
			column.setAccessible(true);
			columns = (Integer) column.get(gridView);
			// 利用反射，取得横向分割线高度
			Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
			horizontalSpacing.setAccessible(true);
			horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
		if (listAdapter.getCount() % columns > 0) {
			rows = listAdapter.getCount() / columns + 1;
		} else {
			rows = listAdapter.getCount() / columns;
		}
		int totalHeight = 0;
		for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
		gridView.setLayoutParams(params);
		return params.height;
	}

	// List数组排序
	// public void mListSort(ArrayList<?> list) {
	// Collections.sort(list, new Comparator<Object>() {
	// public int compare(Object a, Object b) {
	// int one= ((ChatsBean) a).mid;
	// int two = ((ChatsBean) b).mid;
	// return one- two;
	// }
	// });
	// }

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px ( 像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取当前分辨率下指定单位对应的像素大小（根据设备信息） px,dip,sp -> px
	 * 
	 * Paint.setTextSize()单位为px
	 * 
	 * 代码摘自：TextView.setTextSize()
	 * 
	 * @param unit
	 *            TypedValue.COMPLEX_UNIT_*
	 * @param size
	 * @return
	 */
	public float getRawSize(Context c, int unit, float size) {
		Resources r;
		if (c == null)
			r = Resources.getSystem();
		else
			r = c.getResources();
		return TypedValue.applyDimension(unit, size, r.getDisplayMetrics());
	}

	// 获取字体大小
	public static int adjustFontSize(int screenWidth, int screenHeight) {
		screenWidth = screenWidth > screenHeight ? screenWidth : screenHeight;
		/**
		 * 1. 在视图的 onsizechanged里获取视图宽度，一般情况下默认宽度是320，所以计算一个缩放比率 rate = (float)
		 * w/320 w是实际宽度 2.然后在设置字体尺寸时 paint.setTextSize((int)(8*rate));
		 * 8是在分辨率宽为320 下需要设置的字体大小 实际字体大小 = 默认字体大小 x rate
		 */
		int rate = (int) (5 * (float) screenWidth / 320); // 我自己测试这个倍数比较适合，当然你可以测试后再修改
		return rate < 15 ? 15 : rate; // 字体太小也不好看的
	}
	
}
