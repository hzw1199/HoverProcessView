package com.wuadam.hoverprocess;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * @Description: 半透明的进度覆盖层
 * @Author: wuadam
 * @Date: 2019-12-12 16:27
 */
public class HoverProcessView extends View {

    /**
     * 进度条画笔对象
     */
    private Paint paint;

    /**
     * 擦除画笔对象
     */
    private Paint paintErase;

    /**
     * 背景画笔对象
     */
    private Paint paintBackground;

    private Interpolator interpolator = new AccelerateDecelerateInterpolator();
    private ValueAnimator progressValueAnimator;
    private int duration = 400;

    /**
     * View宽度
     */
    private int width;

    /**
     * View高度
     */
    private int height;

    /**
     * padding值
     */
    private float padding = 0;

    /**
     * 蒙版的颜色
     */
    private int hoverColor;

    /**
     * 圆环宽度
     */
    private float ringWidth = 5;

    /**
     * 最大值
     */
    private int max = 100;

    /**
     * 进度值
     */
    private int progress = 0;
    private int lastProgress;

    /**
     * 圆环中心
     */
    private int centreX, centreY;

    /**
     * 圆环半径
     */
    private float radius = 50;

    /**
     * 圆角半径
     */
    private float roundCornerRadius = 0;

    private boolean isRunning = false;

    private OnHoverProcessListener listener;


    public HoverProcessView(Context context) {

        this(context, null);
    }


    public HoverProcessView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }


    public HoverProcessView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        // PorterDuff.Mode.CLEAR不支持硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //初始化属性
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.HoverProcessView);

        hoverColor = mTypedArray.getColor(R.styleable.HoverProcessView_hoverColor, Color.BLACK);
        radius = mTypedArray.getDimension(R.styleable.HoverProcessView_radius, radius);
        roundCornerRadius = mTypedArray.getDimension(R.styleable.HoverProcessView_roundCornerRadius, roundCornerRadius);
        ringWidth = mTypedArray.getDimension(R.styleable.HoverProcessView_ringWidth, ringWidth);
        max = mTypedArray.getInteger(R.styleable.HoverProcessView_max, max);
        lastProgress = progress = mTypedArray.getInteger(R.styleable.HoverProcessView_progress, progress);
        padding = mTypedArray.getDimension(R.styleable.HoverProcessView_ringPadding, padding);
        duration = mTypedArray.getInteger(R.styleable.HoverProcessView_duration, duration);

        mTypedArray.recycle();

        // 初始化画笔
        initPaint();
    }

    private void initValueAnimator() {
        progressValueAnimator = new ValueAnimator();
        progressValueAnimator.setInterpolator(interpolator);
        progressValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        progressValueAnimator.addUpdateListener(new ProgressAnimatorUpdateListenerImp());
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        centreX = getWidth() / 2;
        centreY = getHeight() / 2;

        //绘制外层蒙版
        drawBackground(canvas);
        //绘制镂空圆形
        drawErasedCircle(canvas);
        //绘制进度条
        drawProgress(canvas);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(0);
        paint.setColor(hoverColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);

        paintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBackground.setColor(hoverColor);

        paintErase = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintErase.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }


    /**
     * 绘制外层蒙版
     */
    private void drawBackground(Canvas canvas) {
        RectF backRect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(backRect, roundCornerRadius, roundCornerRadius, paintBackground);
    }


    /**
     * 绘制镂空圆形
     */
    private void drawErasedCircle(Canvas canvas) {
        canvas.drawCircle(centreX, centreY, radius - padding, paintErase);
    }


    /**
     * 绘制进度条
     */
    private void drawProgress(Canvas canvas) {
        //绘制进度 根据设置的样式进行绘制
        RectF fillOval = new RectF(centreX - radius + ringWidth + padding,
                centreY - radius + ringWidth + padding, centreX + radius - ringWidth - padding,
                centreY + radius - ringWidth - padding);

        if (progress != 0) {
            canvas.drawArc(fillOval, -90, 360 * progress / max, true, paint);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取宽高的mode和size
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //测量宽度
        if (widthMode == MeasureSpec.AT_MOST) {
            width = (int) ((radius + padding) * 2);
        } else {
            width = widthSize;
        }

        //测量高度
        if (heightMode == MeasureSpec.AT_MOST) {
            height = (int) ((radius + padding) * 2);
        } else {
            height = heightSize;
        }

        //设置测量的宽高值
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        //确定View的宽高
        width = w;
        height = h;
    }


    /**
     * 获取当前的最大进度值
     */
    public synchronized int getMax() {

        return max;
    }


    /**
     * 设置最大进度值
     */
    public synchronized void setMax(int max) {

        if (max < 0) {
            throw new IllegalArgumentException("The max progress of 0");
        }
        this.max = max;
    }


    /**
     * 获取进度值
     */
    public synchronized int getProgress() {

        return progress;
    }


    /**
     * 设置进度值 根据进度值进行View的重绘刷新进度
     */
    private void setProgress(int progress) {
        this.progress = progress;
        invalidate();
        if (listener != null) {
            listener.onProcess(this, progress);
        }
    }

    /**
     * 设置进度值 根据进度值进行View的重绘刷新进度，可设置是否开启动画
     * @param start
     * @param progress
     * @param animate
     */
    public void setProgress(int start, int progress, boolean animate) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > max) {
            progress = max;
        }
        if (!animate) {
            setProgress(progress);
            lastProgress = progress;
        } else {
            stop();
            initValueAnimator();
            progressValueAnimator.setIntValues(start, progress);
            progressValueAnimator.setDuration(duration);
            progressValueAnimator.setRepeatCount(0);
            progressValueAnimator.start();
            isRunning = true;
        }
    }

    /**
     * 设置进度值 根据进度值进行View的重绘刷新进度，可设置是否开启动画
     * @param progress
     * @param animate
     */
    public void setProgress(int progress, boolean animate) {
        setProgress(lastProgress, progress, animate);
    }

    /**
     * 停止动画
     */
    public void stop() {
        if (progressValueAnimator != null) {
            progressValueAnimator.cancel();
            progressValueAnimator.removeAllUpdateListeners();
            isRunning = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    /**
     * 开始无限转圈
     */
    public void startInfiniteProgress() {
        stop();
        initValueAnimator();
        progressValueAnimator.setIntValues(0, 100);
        progressValueAnimator.setDuration(duration);
        progressValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        progressValueAnimator.start();
        isRunning = true;
    }

    /**
     * 停止无限转圈
     */
    public void stopInfiniteProgress() {
        stop();
    }


    /**
     * 获取圆环的颜色
     */
    public int getHoverColor() {

        return hoverColor;
    }


    /**
     * 设置圆环的颜色
     */
    public void setHoverColor(int hoverColor) {

        this.hoverColor = hoverColor;
    }


    /**
     * 获取圆环的宽度
     */
    public float getRingWidth() {

        return ringWidth;
    }


    /**
     * 设置圆环的宽度
     */
    public void setRingWidth(float ringWidth) {

        this.ringWidth = ringWidth;
    }

    /**
     * 设置动画时长
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setListener(OnHoverProcessListener listener) {
        this.listener = listener;
    }

    private class ProgressAnimatorUpdateListenerImp implements ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Integer value = (Integer) valueAnimator.getAnimatedValue();
            setProgress(value);
            lastProgress = value;
        }
    }

    public interface OnHoverProcessListener{
        /**
         * process changed
         * @param view
         * @param value
         */
        void onProcess(HoverProcessView view, int value);
    }

}
