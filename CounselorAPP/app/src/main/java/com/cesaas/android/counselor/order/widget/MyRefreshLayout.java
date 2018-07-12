package com.cesaas.android.counselor.order.widget;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ListView;

import com.cesaas.android.counselor.order.R;
import com.lidroid.xutils.util.LogUtils;

/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 * 
 * @author Evan
 *
 */
public class MyRefreshLayout extends SwipeRefreshLayout implements OnScrollListener {

	// 滑动到最下面时的上拉操作
	private int mTouchSlop;
	// ListView实例
	private ListView mListView;
	private GridView mGridView;
	// 上拉监听器, 到了最底部的上拉加载操作
	private OnLoadListener mOnLoadListener;
	// ListView的加载中footer
	private View mListViewFooter;
	// 按下时的y坐标
	private int mYDown;
	// 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
	private int mLastY;
	// 是否在加载中 ( 上拉加载更多 )
	private boolean isLoading = false;
	private int mDownX;
	private int mDownY;
	private boolean up;
	private boolean down;
	private View mTarget;

	// private PhotoGestureDetector mGestureDetector;

	/**
	 * @param context
	 */
	public MyRefreshLayout(Context context) {
		this(context, null);
	}

	public MyRefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
		// mGestureDetector = new PhotoGestureDetector(context, new
		// YScrollDetector());
	}

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// return super.onInterceptTouchEvent(ev) &&
	// mGestureDetector.onTouchEvent(ev);
	// }
	//
	// private class YScrollDetector extends SimpleOnGestureListener {
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// /**
	// * 如果我们滚动更接近水平方向,返回false,让子视图来处理它
	// */
	// return (Math.abs(distanceY) > Math.abs(distanceX));
	// }
	// }

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// 初始化ListView对象
		if (mListView == null || mGridView == null) {
			getListView();
		}
	}

	/**
	 * 获取ListView对象
	 */
	private void getListView() {
		int childs = getChildCount();
		if (childs > 0) {
			mTarget = getChildAt(0);
			if (mTarget instanceof ListView) {
				mListView = (ListView) mTarget;
				// 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
				mListView.setOnScrollListener(this);
				LogUtils.d("找到listview");
			} else if (mTarget instanceof GridView) {
				mGridView = (GridView) mTarget;
				// 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
				mGridView.setOnScrollListener(this);
				LogUtils.d("找到gridview");
			}
		}
	}

	/*
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			mYDown = (int) event.getRawY();
			up = canChildScrollUp();
			down = canChildScrollDown();
			break;
		case MotionEvent.ACTION_MOVE:
			// 移动
			mLastY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_UP:
			// 抬起
			if (canLoad()) {
				loadData();
			}
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 首先拦截down事件,记录y坐标
			mDownX = (int) e.getRawX();
			mDownY = (int) e.getRawY();
			up = canChildScrollUp();
			down = canChildScrollDown();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) e.getRawX();
			int moveY = (int) e.getRawY();
			// 满足此条件屏蔽SildingFinishLayout里面子类的touch事件
			if (Math.abs(moveX - mDownX) > 8) {
				return false;
			}
			if (Math.abs(moveY - mDownY) < 8) {
				return false;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return super.onInterceptTouchEvent(e);
	}

	/**
	 * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
	 * 
	 * @return
	 */
	private boolean canLoad() {
		return isBottom() && !isLoading && isPullUp();
	}

	/**
	 * 判断是否到了最底部
	 */
	private boolean isBottom() {
		if (mListView != null && mListView.getAdapter() != null) {
			// return mListView.getLastVisiblePosition() ==
			// (mListView.getAdapter().getCount() - 1);
			return isListViewReachBottomEdge(mListView);
		} else if (mGridView != null && mGridView.getAdapter() != null) {
			return isGridViewReachBottomEdge(mGridView);
		}
		return false;
	}

	/**
	 * 是否是上拉操作
	 * 
	 * @return
	 */
	private boolean isPullUp() {
		return (mYDown - mLastY) >= mTouchSlop;
	}

	/**
	 * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
	 */
	private void loadData() {
		if (mOnLoadListener != null) {
			// 设置状态
			setLoading(true);
			mOnLoadListener.onLoad();
		}
	}

	/**
	 * @param loading
	 */
	public void setLoading(boolean loading) {
		isLoading = loading;
		if (isLoading) {
			if (mListView != null)
				mListView.addFooterView(mListViewFooter);
		} else {
			if (mListView != null)
				mListView.removeFooterView(mListViewFooter);
			mYDown = 0;
			mLastY = 0;
		}
	}

	/**
	 * @param loadListener
	 */
	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
	}

	public boolean isListViewReachBottomEdge(final ListView listView) {
		boolean result = false;
		if (listView != null) {
			if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
				final View bottomChildView = listView
						.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
				if (bottomChildView != null)
					result = (listView.getHeight() >= bottomChildView.getBottom());
			}
		}
		return result;
	}

	public boolean isGridViewReachBottomEdge(final GridView gridView) {
		boolean result = false;
		if (gridView != null) {
			if (gridView.getLastVisiblePosition() == (gridView.getCount() - 1)) {
				final View bottomChildView = gridView
						.getChildAt(gridView.getLastVisiblePosition() - gridView.getFirstVisiblePosition());
				if (bottomChildView != null)
					result = (gridView.getHeight() >= bottomChildView.getBottom());
			}
		}
		return result;
	}

	// private int getLastVisiblePosition = zero, lastVisiblePositionY = zero;

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		/*
		 * if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { // 滚动到底部 if
		 * (view.getLastVisiblePosition() == (view.getCount() - 1)) { View v =
		 * (View) view.getChildAt(view.getChildCount() - 1); int[] location =
		 * new int[2]; v.getLocationOnScreen(location);// 获取在整个屏幕内的绝对坐标 int y =
		 * location[1];
		 * 
		 * if (view.getLastVisiblePosition() != getLastVisiblePosition &&
		 * lastVisiblePositionY != y) { // 第一次拖至底部 //
		 * Toast.makeText(view.getContext(), "已经拖动至底部，再次拖动即可翻页", // 500).show();
		 * getLastVisiblePosition = view.getLastVisiblePosition();
		 * lastVisiblePositionY = y; return; } else if
		 * (view.getLastVisiblePosition() == getLastVisiblePosition &&
		 * lastVisiblePositionY == y) { // 第二次拖至底部 // 执行翻页操作 //
		 * mCallback.execute(); Toast.makeText(view.getContext(), "加载",
		 * 500).show(); loadData(); } } // 未滚动到底部，第二次拖至底部都初始化
		 * getLastVisiblePosition = zero; lastVisiblePositionY = zero; }
		 */
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// 滚动时到了最底部也可以加载更多
		// if (canLoad()) {
		// loadData();
		// }
	}

	/**
	 * 设置刷新
	 */
	public static void setRefreshing(SwipeRefreshLayout refreshLayout, boolean refreshing, boolean notify) {
		Class<? extends SwipeRefreshLayout> refreshLayoutClass = refreshLayout.getClass();
		if (refreshLayoutClass != null) {
			try {
				Method setRefreshing = refreshLayoutClass.getDeclaredMethod("setRefreshing", boolean.class,
						boolean.class);
				setRefreshing.setAccessible(true);
				setRefreshing.invoke(refreshLayout, refreshing, notify);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return Whether it is possible for the child view of this layout to
	 *         scroll up. Override this if the child view is a custom view.
	 */
	public boolean canChildScrollUp() {
		if (android.os.Build.VERSION.SDK_INT < 14) {
			if (mTarget instanceof AbsListView) {
				final AbsListView absListView = (AbsListView) mTarget;
				return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
						|| absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
			} else {
				return mTarget.getScrollY() > 0;
			}
		} else {
			return ViewCompat.canScrollVertically(mTarget, -1);
		}
	}

	public boolean canChildScrollDown() {
		if (android.os.Build.VERSION.SDK_INT < 14) {
			if (mTarget instanceof AbsListView) {
				final AbsListView absListView = (AbsListView) mTarget;
				View lastChild = absListView.getChildAt(absListView.getChildCount() - 1);
				if (lastChild != null) {
					return (absListView.getLastVisiblePosition() == (absListView.getCount() - 1))
							&& lastChild.getBottom() > absListView.getPaddingBottom();
				} else {
					return false;
				}
			} else {
				return mTarget.getHeight() - mTarget.getScrollY() > 0;
			}
		} else {
			return ViewCompat.canScrollVertically(mTarget, 1);
		}
	}

	/**
	 * 加载更多的监听器
	 * 
	 * @author mrsimple
	 */
	public static interface OnLoadListener {
		public void onLoad();
	}

}