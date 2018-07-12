package com.cesaas.android.counselor.order.custom;

import java.lang.reflect.Field;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.cesaas.android.counselor.order.R;
import com.cesaas.android.counselor.order.R.drawable;
import com.cesaas.android.counselor.order.salestalk.bean.GetCategoryBean;

/**
 * 自定义的tab切换
 */
public class MyGallery implements OnClickListener {
	private Context context = null;
	/**
	 * 消息回调
	 */
	private Handler msgHandler = null;
	/**
	 * 控件管理布局
	 */
	private LinearLayout galleryLinearLayout = null;
	/**
	 * 当前选中的item布局中的IamgeView
	 */
	private HorizontalScrollView hScrollView = null;
	/**
	 * 设备屏幕宽度
	 */
	private int screenW;

	/**
	 * item菜单宽度
	 */
	private int itemW;

	/**
	 * item名称数组
	 */
	private List<GetCategoryBean> menuStrings = null;

	/**
	 * item图标数组
	 */
	private int icon_res;

	/**
	 * 上次选中项序列号
	 */
	private int lastSelectedIndex = 0;

	/**
	 * 当前选中项序列号
	 */
	private int currentSelectedIndex = 0;

	private int color_normal = Color.BLACK;
	private int color_select = Color.RED;

	// �?��的tab
	private ImageView[] imgaViews = null;
	// �?��的字符标�?
	private TextView[] txtViews = null;

	/**
	 * 
	 * @param context
	 * @param handler
	 * @param menuArray
	 *            icon下面的字�?
	 * @param
	 *            icon
	 */
	public MyGallery(Context context, Handler handler, List<GetCategoryBean> menuArray,
			int icon) {
		this.context = context;
		msgHandler = handler;
		// 计算每个item的宽度，使得�?��显示5个按�?
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		screenW = dm.widthPixels;
		// 默认每一个屏幕显示五�?
		itemW = screenW / 3;
		menuStrings = menuArray;
		this.icon_res = icon;
	}

	/**
	 * 生成Gallery视图
	 * 
	 * @return
	 */
	public View getView() {
		LayoutParams layoutParams = new LayoutParams(1, 1);
		layoutParams.width = itemW;
		layoutParams.height = LayoutParams.WRAP_CONTENT;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 容纳item的布�?
		View layoutView = inflater.inflate(R.layout.frame_layout_tab_menu,
				null);
		hScrollView = (HorizontalScrollView) layoutView;
		// 容纳item的布�?inearLayout
		galleryLinearLayout = (LinearLayout) layoutView
				.findViewById(R.id.linearLayout_gallery);
		View itemView = null;
		TextView textView = null;
		ImageView imageView = null;
		imgaViews = new ImageView[menuStrings.size()];
		txtViews = new TextView[menuStrings.size()];
		for (int i = 0; i < menuStrings.size(); i++) {
			// item布局
			itemView = inflater.inflate(
					R.layout.frame_layout_tab_menu_item, null);
			// 设置item的LayoutParams
			itemView.setLayoutParams(layoutParams);
			// 名称
			textView = (TextView) itemView.findViewById(R.id.textview_menuname);
			textView.setTextColor(color_normal);
			textView.setText(menuStrings.get(i).Content);
			// tab
			imageView = (ImageView) itemView
					.findViewById(R.id.imageview_menu_icon);
			imageView.setBackgroundResource(icon_res);
			imageView.setVisibility(View.INVISIBLE);
			itemView.setTag(i);
			galleryLinearLayout.addView(itemView, i);
			itemView.setOnClickListener(this);
			// 初始选中
			if (i == 0) {
				imageView.setVisibility(View.VISIBLE);
				textView.setTextColor(color_select);
			}
			imgaViews[i] = imageView;
			txtViews[i] = textView;
		}
		return layoutView;
	}

	/**
	 * 获取图片资源id
	 * 
	 * @param name
	 * @return
	 */
	public static int getDrawableId(String name) {
		Class<drawable> drawable = R.drawable.class;
		try {
			Field field = drawable.getDeclaredField(name);
			return field.getInt(name);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * @see OnClickListener#onClick(View)
	 */
	@Override
	public void onClick(View v) {
		int index = (Integer) v.getTag();
		lastSelectedIndex = currentSelectedIndex;
		currentSelectedIndex = index;
		if (null != msgHandler) {
			msgHandler.sendEmptyMessage(index);
		}
		changBg(index);
		int left = (imgaViews[0].getWidth()) * (index);

		// 使当前�?中item居中显示
		hScrollView.smoothScrollTo(left - (screenW / 2) + (itemW / 2), 0);
		if (null != itemSelectListener) {
			itemSelectListener.onItemSelect(index);
		}
	}

	private void changBg(int index) {
		for (ImageView imageView : imgaViews) {
			imageView.setVisibility(View.INVISIBLE);
		}
		for (TextView textView : txtViews) {
			textView.setTextColor(color_normal);
		}
		imgaViews[index].setVisibility(View.VISIBLE);
		txtViews[index].setTextColor(color_select);
	}

	/**
	 * 设置选中�?
	 * 
	 * @param index
	 */
	public void setSelected(int index) {
		lastSelectedIndex = currentSelectedIndex;
		currentSelectedIndex = index;
		RelativeLayout itemLayout = (RelativeLayout) galleryLinearLayout
				.getChildAt(index);
		changBg(index);
		int left = (imgaViews[0].getWidth()) * (index);
		// 使当前�?中item居中显示
		hScrollView.smoothScrollTo(left - (screenW / 2) + (itemW / 2), 0);
	}

	/**
	 * 设置上一项�?�?
	 */
	public void setLastSelected() {
		setSelected(lastSelectedIndex);
	}

	/**
	 * 控件可用处理
	 * 
	 * @param enable
	 */
	public void setEnabled(boolean enable) {
		View itemView = null;
		for (int i = 0; i < menuStrings.size(); i++) {
			itemView = (View) galleryLinearLayout.getChildAt(i);
			if (itemView != null)
				itemView.setEnabled(enable);
		}
	}

	private Handler scrollHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				hScrollView.smoothScrollTo(itemW * menuStrings.size(), 0);
				break;
			case 1:
				hScrollView.smoothScrollTo(0, 0);
				break;

			default:
				break;
			}
		}

	};

	/**
	 * 启动后，左右滚动动画
	 */
	public void doScrollAnimation() {
		scrollHandler.sendEmptyMessageDelayed(0, 1000);
		scrollHandler.sendEmptyMessageDelayed(1, 1500);
	}

	private OnItemSelectListener itemSelectListener;

	public void setItemSelectListener(OnItemSelectListener itemSelectListener) {
		this.itemSelectListener = itemSelectListener;
	}

	public interface OnItemSelectListener {
		public abstract void onItemSelect(int position);
	}

}
