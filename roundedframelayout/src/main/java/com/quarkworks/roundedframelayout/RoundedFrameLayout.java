package com.quarkworks.roundedframelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

/**
 * Created by zekunwang on 4/19/17.
 */

public class RoundedFrameLayout extends FrameLayout {
    private static final String TAG = RoundedFrameLayout.class.getSimpleName();

    private int borderColor;
    private float borderWidth;
    private float cornerRadius;
    private float cornerRadiusTopLeft;
    private float cornerRadiusTopRight;
    private float cornerRadiusBottomRight;
    private float cornerRadiusBottomLeft;

    private Path borderPath;
    private Paint borderPaint;
    private RectF oval;


    public RoundedFrameLayout(@NonNull Context context) {
        super(context);

        initialize();
    }

    public RoundedFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        loadAttributes(context, attrs);

        initialize();
    }

    public RoundedFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void loadAttributes(Context context, AttributeSet attrs) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundedFrameLayout);

        borderColor = attributes.getColor(R.styleable.RoundedFrameLayout_borderColor, Color.BLACK);
        borderWidth = attributes.getDimension(R.styleable.RoundedFrameLayout_borderWidth, 0);
        cornerRadius = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadius, -1);

        // Valid cornerRadius has higher priority
        if (cornerRadius < 0) {
            cornerRadiusTopLeft = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusTopLeft, 0);
            cornerRadiusTopRight = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusTopRight, 0);
            cornerRadiusBottomRight = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusBottomRight, 0);
            cornerRadiusBottomLeft = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusBottomLeft, 0);
        } else {
            cornerRadiusTopLeft = cornerRadius;
            cornerRadiusTopRight = cornerRadius;
            cornerRadiusBottomRight = cornerRadius;
            cornerRadiusBottomLeft = cornerRadius;
        }

        attributes.recycle();
    }

    private void initialize() {

        borderPath = new Path();
        oval = new RectF();

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        configurePath(getWidth(), getHeight());
        canvas.clipPath(borderPath);

        super.dispatchDraw(canvas);

        canvas.drawPath(borderPath, borderPaint);
    }

    private void configurePath (int width, int height) {

        borderPath.rewind();

        // Radius check
//        float minSize = Math.min(width, height);
//        float maxRadiusWidth = 2 * Math.max(Math.max(cornerRadiusTopLeft, cornerRadiusTopRight),
//                Math.max(cornerRadiusBottomLeft, cornerRadiusBottomRight));

//        if (minSize < maxRadiusWidth) {
//            borderPath.addRect(0, 0, width, height, Path.Direction.CCW);
//            return;
//        }

        // Top left circle
        oval.set(0, 0, 2 * cornerRadiusTopLeft, 2 * cornerRadiusTopLeft);
        borderPath.moveTo(0, cornerRadiusTopLeft);
        borderPath.arcTo(oval, -180, 90);

        borderPath.rLineTo(width - cornerRadiusTopLeft - cornerRadiusTopRight, 0);

        // Top right circle
        oval.set(width - 2 * cornerRadiusTopRight, 0, width, 2 * cornerRadiusTopRight);
        borderPath.arcTo(oval, -90, 90);

        borderPath.rLineTo(0, height - cornerRadiusTopRight - cornerRadiusBottomRight);

        // Bottom right circle
        oval.set(width - 2 * cornerRadiusBottomRight, height - 2 * cornerRadiusBottomRight, width, height);
        borderPath.arcTo(oval, 0, 90);

        borderPath.rLineTo(-width + cornerRadiusBottomRight + cornerRadiusBottomLeft, 0);

        // Bottom left circle
        oval.set(0, height - 2 * cornerRadiusBottomLeft, 2 * cornerRadiusBottomLeft, height);
        borderPath.arcTo(oval, 90, 90);

        borderPath.rLineTo(0, -height + cornerRadiusBottomLeft + cornerRadiusTopLeft);
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public float getCornerRadiusTopLeft() {
        return cornerRadiusTopLeft;
    }

    public void setCornerRadiusTopLeft(int dp) {
        this.cornerRadiusTopLeft = dpToPx(dp);
    }

    public float getCornerRadiusTopRight() {
        return cornerRadiusTopRight;
    }

    public void setCornerRadiusTopRight(int dp) {
        this.cornerRadiusTopRight = dpToPx(dp);
    }

    public float getCornerRadiusBottomRight() {
        return cornerRadiusBottomRight;
    }

    public void setCornerRadiusBottomRight(int dp) {
        this.cornerRadiusBottomRight = dpToPx(dp);
    }

    public float getCornerRadiusBottomLeft() {
        return cornerRadiusBottomLeft;
    }

    public void setCornerRadiusBottomLeft(int dp) {
        this.cornerRadiusBottomLeft = dpToPx(dp);
    }

    private float dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
