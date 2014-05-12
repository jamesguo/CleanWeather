package com.cleanweather.widget;

import com.cleanweather.model.WeatherDetailModel;
import com.cleanweather.util.StringUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class WeatherInfoView extends View {
	private Paint paint;
	public WeatherDetailModel detailModel;
	private float size;
	public WeatherInfoView(Context context) {
		super(context);
		initPaint();
	}
	public WeatherInfoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}
	private void initPaint() {
		// TODO Auto-generated method stub
		paint = new Paint();
		paint.setAntiAlias(true);
		size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, getContext().getResources().getDisplayMetrics());
	}
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		paint.setColor(Color.parseColor("#333333"));
		paint.setStrokeWidth(1);
		canvas.drawLine(10*size, 10*size, getMeasuredWidth()-10*size, 10*size, paint);
		paint.setColor(Color.parseColor("#fbfbfb"));
		canvas.drawLine(10*size, 10*size+1, getMeasuredWidth()-10*size, 10*size+1, paint);
		paint.setColor(Color.parseColor("#333333"));
		paint.setTextSize(size * 15);
		paint.setStrokeWidth(size * 2);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText("今日气温：", 20*size, size * 50, paint);
		if(detailModel!=null&&!StringUtil.isEmpty(detailModel.getTempStr())){
			paint.setColor(Color.parseColor("#0065ca"));
			paint.setTextSize(size * 20);
			paint.setTextAlign(Align.RIGHT);
			paint.setStrokeWidth(size * 5);
			canvas.drawText(detailModel.getTempStr(), getMeasuredWidth()-20*size, size * 50, paint);
		}
		
		paint.setColor(Color.parseColor("#333333"));
		paint.setTextSize(size * 15);
		paint.setStrokeWidth(size * 2);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText("今日天气：", 20*size, size * 90, paint);
		if(detailModel!=null&&!StringUtil.isEmpty(detailModel.weatherInfo)){
			paint.setColor(Color.parseColor("#0065ca"));
			paint.setTextAlign(Align.RIGHT);
			paint.setTextSize(size * 20);
			paint.setStrokeWidth(size * 5);
			canvas.drawText(detailModel.weatherInfo, getMeasuredWidth()-20*size, size * 90, paint);
		}
		
		paint.setColor(Color.parseColor("#333333"));
		paint.setTextSize(size * 15);
		paint.setStrokeWidth(size * 2);
		paint.setTextAlign(Align.LEFT);
		canvas.drawText("相对湿度：", 20*size, size * 130, paint);
		if(detailModel!=null&&!StringUtil.isEmpty(detailModel.humidity)){
			paint.setColor(Color.parseColor("#0065ca"));
			paint.setTextAlign(Align.RIGHT);
			paint.setStrokeWidth(size * 5);
			paint.setTextSize(size * 20);
			canvas.drawText(detailModel.humidity, getMeasuredWidth()-20*size, size * 130, paint);
		}
	}

}
