package com.cleanweather.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cleanweather.widget.PullDownView.OnAnimationCallBack;

public class HeightLessView extends LinearLayout {
	private final static int RATIO = 3;

	private boolean isRecored;
	private int startY;
	private RelativeLayout.LayoutParams currentLayoutParams;
	private int height;
	public OnAnimationCallBack animationCallBack;
//	private VelocityTracker mVelocityTracker;
//	private int nMaximumVelocity;
//	private static final int SNAP_VELOCITY = 1000;

	public HeightLessView(Context context, AttributeSet attrs) {
		super(context, attrs);
		height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48.0f, getResources().getDisplayMetrics());
//		final ViewConfiguration configuration = ViewConfiguration.get(getContext());
//		nMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if (mVelocityTracker == null) {
//			mVelocityTracker = VelocityTracker.obtain();
//		}
//		mVelocityTracker.addMovement(event);
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
//			final VelocityTracker velocityTracker = mVelocityTracker;
//			velocityTracker.computeCurrentVelocity(1000, nMaximumVelocity);
			if (isRecored) {
				if (currentLayoutParams == null) {
					currentLayoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
				}

//				int velocityY = (int) velocityTracker.getYVelocity();
//				Log.e("onTouchEvent", "velocityY==" + velocityY);
//				if (velocityY > SNAP_VELOCITY) {
//					if (animationCallBack != null) {
//						animationCallBack.openViewCallBack();
//					}
//				} else if (velocityY < -SNAP_VELOCITY) {
//					if (animationCallBack != null) {
//						animationCallBack.closeViewCallBack();
//					}
//				} else {
					if (currentLayoutParams.topMargin > -(currentLayoutParams.height / 2 - height) / 2) {
						if (animationCallBack != null) {
							animationCallBack.openViewCallBack();
						}
					} else {
						if (animationCallBack != null) {
							animationCallBack.closeViewCallBack();
						}
					}
//				}
			}
			isRecored = false;
//			if (mVelocityTracker != null) {
//				mVelocityTracker.recycle();
//				mVelocityTracker = null;
//			}
			break;

		case MotionEvent.ACTION_MOVE:
			int tempY = (int) event.getY();
			if (!isRecored && getScrollY() == 0) {
				isRecored = true;
				startY = tempY;
			}
			if (isRecored) {
				if (currentLayoutParams == null) {
					currentLayoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
				}
				int temp = currentLayoutParams.topMargin + (tempY - startY) / RATIO;
				if (temp >= 2 * height - currentLayoutParams.height / 2 && temp <= 0) {
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

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int childTop = 0;
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				if (child.getLayoutParams() != null) {
					final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
					childTop += lp.topMargin;
					final int childHeight = child.getMeasuredHeight();
					child.layout(0, childTop, getMeasuredWidth(), childTop + childHeight);
					childTop += childHeight;
				}
			}
		}
	}
}
