package com.wu.my.android_letterindexviewrecyclerview.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Administrator on 2016/6/17.
 */
public class CircleTransformation extends BitmapTransformation {
    private static final int STROKE_WIDTH = 0;

    public CircleTransformation(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int x = (toTransform.getWidth() - size) / 2;
        int y = (toTransform.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(toTransform, x, y, size, size);
        if (squaredBitmap != toTransform) {
            toTransform.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, toTransform.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint avatarPaint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP);
        avatarPaint.setShader(shader);
        Paint outlinePaint = new Paint();
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(STROKE_WIDTH);
        outlinePaint.setColor(Color.TRANSPARENT);
        outlinePaint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, avatarPaint);
        canvas.drawCircle(r, r, r - STROKE_WIDTH / 2, outlinePaint);
        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String getId() {
        return "circleTransformation()";
    }
}
