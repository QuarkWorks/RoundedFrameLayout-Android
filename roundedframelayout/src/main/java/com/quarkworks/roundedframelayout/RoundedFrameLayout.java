package com.quarkworks.roundedframelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

/**
 * Created by zekunwang on 4/19/17.
 */

public class RoundedFrameLayout extends FrameLayout {
    private static final String TAG = RoundedFrameLayout.class.getSimpleName();

    private int clippedBackgroundColor;
    private int borderColor;
    // Smooth drawn bound of RoundedFrameLayout when below LOLLIPOP
    // Should be close to or same as background color
    private int softBorderColor;
    private float borderWidth;
    private float softBorderWidth = 1;  // 1 px just to activate Paint.ANTI_ALIAS_FLAG
    private float cornerRadius;         // Will override four corners
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

        loadAttributes(context, attrs);

        initialize();
    }

    private void loadAttributes(Context context, AttributeSet attrs) {

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundedFrameLayout);

        clippedBackgroundColor = attributes.getColor(R.styleable.RoundedFrameLayout_clippedBackgroundColor, Color.TRANSPARENT);
        borderColor = attributes.getColor(R.styleable.RoundedFrameLayout_borderColor, Color.TRANSPARENT);
        borderWidth = attributes.getDimension(R.styleable.RoundedFrameLayout_borderWidth, 0);
        softBorderColor = attributes.getColor(R.styleable.RoundedFrameLayout_softBorderColor, Color.TRANSPARENT);
        cornerRadius = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadius, -1);

        cornerRadiusTopLeft = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusTopLeft, 0);
        cornerRadiusTopRight = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusTopRight, 0);
        cornerRadiusBottomRight = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusBottomRight, 0);
        cornerRadiusBottomLeft = attributes.getDimension(R.styleable.RoundedFrameLayout_cornerRadiusBottomLeft, 0);

        borderWidth = Math.max(0, borderWidth);

        // Valid cornerRadius has higher priority
        if (cornerRadius >= 0) {
            setAllCornerRadius();
        }

        attributes.recycle();
    }

    private void initialize() {

        borderPath = new Path();
        oval = new RectF();

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);

        // ViewOutlineProvider does not support clipping customized path
        if (useViewOutlineProvider()) {
            ViewOutlineProvider provider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {

                    if (useViewOutlineProvider()) {
                        outline.setRoundRect(0, 0, getWidth(), getHeight(), cornerRadius);
                    }
                }
            };

            setOutlineProvider(provider);
            setClipToOutline(true);
        }
    }

    private boolean useViewOutlineProvider() {
        return cornerRadius >= 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    @Override
    public void requestLayout() {
        super.requestLayout();

        if (useViewOutlineProvider()) {
            invalidateOutline();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        // Path for border in both cases
        configurePath(getWidth(), getHeight());

        if (!useViewOutlineProvider()) {
            canvas.clipPath(borderPath);
        }

        if (clippedBackgroundColor != Color.TRANSPARENT) {
            canvas.drawColor(clippedBackgroundColor);
        }

        super.dispatchDraw(canvas);

        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
        canvas.drawPath(borderPath, borderPaint);

        if (softBorderColor != Color.TRANSPARENT) {

            borderPaint.setColor(softBorderColor);
            borderPaint.setStrokeWidth(softBorderWidth);
            canvas.drawPath(borderPath, borderPaint);
        }
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

//        // Top left circle
//        oval.set(0, 0, 2 * cornerRadiusTopLeft, 2 * cornerRadiusTopLeft);
//        borderPath.moveTo(0, cornerRadiusTopLeft);
//        borderPath.arcTo(oval, -180, 90);
//
//        borderPath.rLineTo(width - cornerRadiusTopLeft - cornerRadiusTopRight, 0);
//
//        // Top right circle
//        oval.set(width - 2 * cornerRadiusTopRight, 0, width, 2 * cornerRadiusTopRight);
//        borderPath.arcTo(oval, -90, 90);
//
//        borderPath.rLineTo(0, height - cornerRadiusTopRight - cornerRadiusBottomRight);
//
//        // Bottom right circle
//        oval.set(width - 2 * cornerRadiusBottomRight, height - 2 * cornerRadiusBottomRight, width, height);
//        borderPath.arcTo(oval, 0, 90);
//
//        borderPath.rLineTo(-width + cornerRadiusBottomRight + cornerRadiusBottomLeft, 0);
//
//        // Bottom left circle
//        oval.set(0, height - 2 * cornerRadiusBottomLeft, 2 * cornerRadiusBottomLeft, height);
//        borderPath.arcTo(oval, 90, 90);
//
//        borderPath.rLineTo(0, -height + cornerRadiusBottomLeft + cornerRadiusTopLeft);
//
//        borderPath.close();

        float[] cornerRadii = {cornerRadiusTopLeft, cornerRadiusTopLeft, cornerRadiusTopRight, cornerRadiusTopRight,
                cornerRadiusBottomRight, cornerRadiusBottomRight, cornerRadiusBottomLeft, cornerRadiusBottomLeft};
        borderPath.addRoundRect(new RectF(0, 0, width, height), cornerRadii, Path.Direction.CW);
    }

    private void setAllCornerRadius() {
        setAllCornerRadius(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
    }

    public void setAllCornerRadius(int topLeft, int topRight, int bottomRight, int bottomLeft) {
        setAllCornerRadius(dpToPx(topLeft), dpToPx(topRight), dpToPx(bottomRight), dpToPx(bottomLeft));
    }

    private void setAllCornerRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        if (topLeft < 0 || topRight < 0 || bottomRight < 0 || bottomLeft < 0) {
            return;
        }

        cornerRadiusTopLeft = topLeft;
        cornerRadiusTopRight = topRight;
        cornerRadiusBottomRight = bottomRight;
        cornerRadiusBottomLeft = bottomLeft;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderWidth() {
        return pxToDp(borderWidth);
    }

    public void setBorderWidth(int dp) {
        this.borderWidth = dpToPx(dp);
    }

    public int getSoftBorderColor() {
        return softBorderColor;
    }

    public void setSoftBorderColor(int softBorderColor) {
        this.softBorderColor = softBorderColor;
    }

    public int getClippedBackgroundColor() {
        return clippedBackgroundColor;
    }

    public void setClippedBackgroundColor(int clippedBackgroundColor) {
        this.clippedBackgroundColor = clippedBackgroundColor;
    }

    public int getCornerRadius() {
        return pxToDp(Math.max(0, cornerRadius));
    }

    public void setCornerRadius(int dp) {
        if (dp >= 0) {
            this.cornerRadius = dpToPx(dp);
            setAllCornerRadius();
        }
    }

    public int getCornerRadiusTopLeft() {
        return pxToDp(cornerRadiusTopLeft);
    }

    public void setCornerRadiusTopLeft(int dp) {
        if (dp >= 0) {
            this.cornerRadiusTopLeft = dpToPx(dp);
        }
    }

    public int getCornerRadiusTopRight() {
        return pxToDp(cornerRadiusTopRight);
    }

    public void setCornerRadiusTopRight(int dp) {
        if (dp >= 0) {
            this.cornerRadiusTopRight = dpToPx(dp);
        }
    }

    public int getCornerRadiusBottomRight() {
        return pxToDp(cornerRadiusBottomRight);
    }

    public void setCornerRadiusBottomRight(int dp) {
        if (dp >= 0) {
            this.cornerRadiusBottomRight = dpToPx(dp);
        }
    }

    public int getCornerRadiusBottomLeft() {
        return pxToDp(cornerRadiusBottomLeft);
    }

    public void setCornerRadiusBottomLeft(int dp) {
        if (dp >= 0) {
            this.cornerRadiusBottomLeft = dpToPx(dp);
        }
    }

    private float dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private int pxToDp(float px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
