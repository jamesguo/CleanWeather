package com.cleanweather.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PullDownView extends RelativeLayout {

	public static interface OnAnimationCallBack {
		public void openViewCallBack();

		public void closeViewCallBack();
	}

	private final static int RATIO = 3;

	private boolean isRecored;
	private int startY;
	private LinearLayout.LayoutParams currentLayoutParams;
	private int height;
	private OnAnimationCallBack animationCallBack;

	public PullDownView(Context context) {
		super(context);
	}

	public PullDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 功能描述:初始化
	 * 
	 * <pre>
	 *     yrguo:   2012-11-22      新建
	 * </pre>
	 * 
	 */
	public void init() {
		height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48.0f, getResources().getDisplayMetrics());
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent) 手势处理
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (getScrollY() == 0 && !isRecored) {
				isRecored = true;
				startY = (int) event.getY();
				return true;
			}
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (isRecored) {
				if (currentLayoutParams == null) {
					currentLayoutParams = (LinearLayout.LayoutParams) getLayoutParams();
				}
				if (currentLayoutParams.topMargin > -(currentLayoutParams.height - height) / 2) {
					if (animationCallBack != null) {
						animationCallBack.openViewCallBack();
					}
				} else {
					if (animationCallBack != null) {
						animationCallBack.closeViewCallBack();
					}
				}
			}
			isRecored = false;
			break;

		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			if (!isRecored && getScrollY() == 0) {
				isRecored = true;
				startY = tempY;
			}
			if (isRecored) {
				if (currentLayoutParams == null) {
					currentLayoutParams = (LinearLayout.LayoutParams) getLayoutParams();
				}
				int temp = currentLayoutParams.topMargin + (tempY - startY) / RATIO;
				if (temp >= 2 * height - currentLayoutParams.height && temp <= 0) {
					currentLayoutParams.topMargin = currentLayoutParams.topMargin + (tempY - startY) / RATIO;
					requestLayout();
				}
			}
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	public OnAnimationCallBack getAnimationCallBack() {
		return animationCallBack;
	}

	public void setAnimationCallBack(OnAnimationCallBack animationCallBack) {
		this.animationCallBack = animationCallBack;
	}
}
