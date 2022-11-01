package com.example.unibody.me.fragment.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.unibody.R;


public class CustomRoundImageView extends AppCompatImageView {
    float width, height;

    private int defaultRadius = 0;
    private int mRadius;
    private int leftTopRadius;
    private int rightTopRadius;
    private int rightBottomRadius;
    private int leftBottomRadius;
    private Bitmap bitmapFrame;

    public CustomRoundImageView(Context context) {
        this(context, null);
        init(context, null);
    }

    public CustomRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public CustomRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomRoundImageView);
        mRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundImageView_my_radius, defaultRadius);
        leftTopRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundImageView_left_top_radius, defaultRadius);
        rightTopRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundImageView_right_top_radius, defaultRadius);
        rightBottomRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundImageView_right_bottom_radius, defaultRadius);
        leftBottomRadius = array.getDimensionPixelOffset(R.styleable.CustomRoundImageView_left_bottom_radius, defaultRadius);

        if (defaultRadius == leftTopRadius) {
            leftTopRadius = mRadius;
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = mRadius;
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = mRadius;
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = mRadius;
        }
        array.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int w = getWidth();
        final int h = getHeight();
        Bitmap bitmapOriginal = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmapOriginal);
        super.onDraw(c);
        if (bitmapFrame == null) {
            bitmapFrame = makeRoundRectFrame(w, h);
        }

        int sc = canvas.saveLayer(0, 0, w, h, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(bitmapFrame, 0, 0, bitmapPaint);

        bitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapOriginal, 0, 0, bitmapPaint);
        bitmapPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }


    private Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap makeRoundRectFrame(int w, int h) {
        float[] radiusArray = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, w, h), radiusArray, Path.Direction.CW);
        Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setColor(Color.GREEN);
        c.drawPath(path, bitmapPaint);
        return bm;
    }
}
