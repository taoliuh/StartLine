package me.sonaive.lab.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import me.sonaive.lab.R;

public class RoundCornerProgressBar extends View {

	private static final int DEFAULT_BACKGROUND_COLOR = 0x00009688;
	
	private static final int DEFAULT_PROGRESS_COLOR = 0xff009688;

	private Path mHorizontalBgPath;

	private Paint mHorizontalBgPaint;

	private Path mProgressPath;

	private Paint mProgressPaint;

	private int mProgress = 0;

	private int mMax = 100;

	private long mProgressBarWidth;

	private int mPathViewHeight = 0;

	private int mInitMoveToX = 0;

	private int mInitMoveToY = 0;

	private int mDefaultRoundCornerWidth;

	private int mRoundCornerBackgroundColor;

	private int mRoundCornerProgressColor;

	public RoundCornerProgressBar(Context context) {
		super(context);
		mDefaultRoundCornerWidth = dip2px(getContext(), 4);
		init();
	}

	public RoundCornerProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray attributes = context.obtainStyledAttributes(attrs,
				R.styleable.RoundCornerProgressBar);
		mDefaultRoundCornerWidth = attributes.getDimensionPixelSize(
				R.styleable.RoundCornerProgressBar_round_corner_width,
				dip2px(getContext(),
						4));
		mRoundCornerBackgroundColor = attributes.getColor(
				R.styleable.RoundCornerProgressBar_round_corner_background_color,
				DEFAULT_BACKGROUND_COLOR);
		mRoundCornerProgressColor = attributes.getColor(
				R.styleable.RoundCornerProgressBar_round_corner_progress_color,
				DEFAULT_PROGRESS_COLOR);
		attributes.recycle();
		init();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int dw = getMeasuredWidth();
		int dh = mPathViewHeight;
		mDefaultRoundCornerWidth = MeasureSpec.getSize(heightMeasureSpec);
		mHorizontalBgPaint.setStrokeWidth(mDefaultRoundCornerWidth);
		mProgressPaint.setStrokeWidth(mDefaultRoundCornerWidth);
		mInitMoveToX = Math.round(mDefaultRoundCornerWidth / 2f);
		mInitMoveToY = Math.round(MeasureSpec.getSize(heightMeasureSpec) / 2f);
		mProgressBarWidth = getMeasuredWidth() - mInitMoveToX;
		setMeasuredDimension(resolveSizeAndState(dw, widthMeasureSpec, 0),
				resolveSizeAndState(dh, heightMeasureSpec, 0));
	}

	private void init() {
		mHorizontalBgPath = new Path();
		mProgressPath = new Path();
		mHorizontalBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHorizontalBgPaint.setStrokeWidth(mDefaultRoundCornerWidth);
		mHorizontalBgPaint.setColor(mRoundCornerBackgroundColor);
		mHorizontalBgPaint.setDither(true);
		mHorizontalBgPaint.setFilterBitmap(true);
		mHorizontalBgPaint.setStrokeJoin(Join.ROUND);
		mHorizontalBgPaint.setStyle(Paint.Style.STROKE);
		mHorizontalBgPaint.setStrokeCap(Paint.Cap.ROUND);

		mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mProgressPaint.setStrokeWidth(mDefaultRoundCornerWidth);
		mProgressPaint.setDither(true);
		mProgressPaint.setFilterBitmap(true);
		mProgressPaint.setStrokeJoin(Join.ROUND);
		mProgressPaint.setColor(mRoundCornerProgressColor);
		mProgressPaint.setStyle(Paint.Style.STROKE);
		mProgressPaint.setStrokeCap(Paint.Cap.ROUND);

		mPathViewHeight = (int) Math.ceil(mProgressPaint.getStrokeWidth());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mHorizontalBgPath.moveTo(mInitMoveToX, mInitMoveToY);
		mHorizontalBgPath.lineTo(mProgressBarWidth, mInitMoveToY);
		mProgressPath.moveTo(mInitMoveToX, mInitMoveToY);
		float minX = (mProgressBarWidth * 1.0f * getProgress()) / mMax * 1.0f;
		if (((int) Math.floor(minX)) < mInitMoveToX) {
			minX = mInitMoveToX;
		}
		mProgressPath.lineTo(minX, mInitMoveToY);
		canvas.drawPath(mHorizontalBgPath, mHorizontalBgPaint);
		canvas.drawPath(mProgressPath, mProgressPaint);
	}

	public void setProgress(int progress) {
		mProgress = progress;
		if (mProgress > mMax) {
			mProgress = mMax;
		}
		invalidate();
	}

	public int getProgress() {
		return mProgress;
	}

	public void setMax(int max) {
		mMax = max;
	}

	public int getMax() {
		return mMax;
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}