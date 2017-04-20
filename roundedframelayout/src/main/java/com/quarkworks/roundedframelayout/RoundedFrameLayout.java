package com.quarkworks.roundedframelayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by zekunwang on 4/19/17.
 */

public class RoundedFrameLayout extends FrameLayout {

    private float topLeftRadius = 30f;
    private float topRightRadius = 10f;
    private float bottomRightRadius = 30f;
    private float bottomLeftRadius = 10f;
    private float borderWidth = 10f;
    private int borderColor = Color.RED;

    private Path borderPath;
    private Paint borderPaint;
    private RectF oval;


    public RoundedFrameLayout(@NonNull Context context) {
        super(context);
        initialize();
    }

    public RoundedFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public RoundedFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            return;
//        }

        borderPath = new Path();
        oval = new RectF();

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);

//        ViewOutlineProvider provider = new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    configurePath(getWidth(), getHeight());
//
//                    outline.setConvexPath(borderPath);
//                }
//            }
//        };
//
//        setOutlineProvider(provider);
//        setClipToOutline(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        configurePath(w, h);
    }

    private void configurePath (int width, int height) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            return;
//        }

        borderPath.rewind();

        float minSize = Math.min(width, height);
        float maxRadiusWidth = 2 * Math.max(Math.max(topLeftRadius, topRightRadius),
                Math.max(bottomLeftRadius, bottomRightRadius));

        if (minSize < maxRadiusWidth) {
            borderPath.addRect(0, 0, width, height, Path.Direction.CCW);
            return;
        }

        // Top left circle
        oval.set(0, 0, 2 * topLeftRadius, 2 * topLeftRadius);
        borderPath.moveTo(0, topLeftRadius);
        borderPath.arcTo(oval, -180, 90);

        borderPath.rLineTo(width - topLeftRadius - topRightRadius, 0);

        // Top right circle
        oval.set(width - 2 * topRightRadius, 0, width, 2 * topRightRadius);
        borderPath.arcTo(oval, -90, 90);

        borderPath.rLineTo(0, height - topRightRadius - bottomRightRadius);

        // Bottom right circle
        oval.set(width - 2 * bottomRightRadius, height - 2 * bottomRightRadius, width, height);
        borderPath.arcTo(oval, 0, 90);

        borderPath.rLineTo(-width + bottomRightRadius + bottomLeftRadius, 0);

        // Bottom left circle
        oval.set(0, height - 2 * bottomLeftRadius, 2 * bottomLeftRadius, height);
        borderPath.arcTo(oval, 90, 90);

        borderPath.rLineTo(0, -height + bottomLeftRadius + topLeftRadius);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(borderPath);
        super.dispatchDraw(canvas);
        canvas.drawPath(borderPath, borderPaint);
        Log.e("draw", "dispatchDraw");
//        canvas.drawPath(borderPath, borderPaint);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawPath(borderPath, borderPaint);
//        }
    }
}
