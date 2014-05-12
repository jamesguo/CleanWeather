package com.cleanweather.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout.LayoutParams;

import com.cleanweather.R;
import com.cleanweather.application.CleanWeatherApplication;

public class ExpandAnimation extends Animation {
	private View mAnimatedView;//���Ŷ�����view
	private LayoutParams mViewLayoutParams;//���Ŷ�����view�Ĳ���
	private int mMarginStart, mMarginEnd;//������ʼ������λ��
	private boolean mWasEndedAlready = false;
	/**
	 * Creates a new instance of CtripExpandAnimation.
	 * @param view
	 * @param duration
	 * @param mIsVisibleAfter
	 */
	public ExpandAnimation(View view, int duration,boolean mIsVisibleAfter) {
		setDuration(duration);
		setInterpolator(new DecelerateInterpolator(1.5f));
		float pix = view.getContext().getResources().getDimension(R.dimen.bottomPreferredHeight);
		mAnimatedView = view;
		mViewLayoutParams = (LayoutParams) view.getLayoutParams();
		if(mIsVisibleAfter){
			mMarginStart = mViewLayoutParams.topMargin;
			mMarginEnd = (int) (-CleanWeatherApplication.weatherInfoHeight/2+pix);
		}else{
			mMarginStart = mViewLayoutParams.topMargin;
			mMarginEnd = 0;
		}
		view.setVisibility(View.VISIBLE);
	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		if (interpolatedTime < 1.0f) {
			mViewLayoutParams.topMargin = mMarginStart + (int) ((mMarginEnd - mMarginStart) * interpolatedTime);
			mAnimatedView.requestLayout();
		} else if (!mWasEndedAlready) {
			mViewLayoutParams.topMargin = mMarginEnd;
			mAnimatedView.requestLayout();
			mWasEndedAlready = true;
		}
	}
}
