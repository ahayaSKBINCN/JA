package com.ahaya.earthquakeviewer.compass;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ahaya.earthquakeviewer.R;


//指南针
public class CompassView extends View {

    private Paint markerPaint;
    private Paint textPaint;
    private Paint circlePaint;

    private String northString;
    private String eastString;
    private String southString;
    private String westString;

    private int textHeight;

    private float mBearing;



    public void setBearing(float bearing) {
        mBearing = bearing;
        invalidate();
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
         super.dispatchPopulateAccessibilityEvent(event);
         if(isShown()){
             String bearingStr = String.valueOf(mBearing);
             event.getText().add(bearingStr);
             return true;
         }else{
             return false;
         }
    }

    public float getBearing() {
        return mBearing;
    }

    public CompassView(Context context) {
        this(context, null);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(true);
        final TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CompassView, defStyleAttr, 0);
        if (arr.hasValue(R.styleable.CompassView_bearing)) {
            setBearing(arr.getFloat(R.styleable.CompassView_bearing, 0));
        }
        arr.recycle();
        Resources r = getResources();

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(ContextCompat.getColor(context, R.color.background_color));
        circlePaint.setStrokeWidth(1);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        northString = r.getString(R.string.cardinal_north);
        westString = r.getString(R.string.cardinal_west);
        eastString = r.getString(R.string.cardinal_east);
        southString = r.getString(R.string.cardinal_south);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(ContextCompat.getColor(context, R.color.text_color));
        textHeight = (int) textPaint.measureText("yY");

        markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        markerPaint.setColor(ContextCompat.getColor(context, R.color.marker_color));



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //罗盘是个圆，它被允许填充尽可能大的空间；
        int mWidth = measure(widthMeasureSpec);
        int mHeight = measure(heightMeasureSpec);
        //通过计算，获取宽高并得到最短边的值；
        int d = Math.min(mWidth, mHeight);
        setMeasuredDimension(d, d);
    }

    private int measure(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            result = 200;
        } else {
            result = specSize;
        }
        return result;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mWidth = getMeasuredWidth();
        int mHeight = getMeasuredHeight();

        int px = mWidth / 2;
        int py = mHeight / 2;
        int radius = Math.min(px, py);

        canvas.drawCircle(px, py, radius, circlePaint);
        canvas.save();
        canvas.rotate(-mBearing, px, py);

        int textWidth = (int) textPaint.measureText("W");
        int cardinalX = px - textWidth / 2;
        int cardinalY = py - radius + textHeight;

        for (int i = 0; i < 24; i++) {
            canvas.drawLine(px, py - radius, px, py - radius + 10, markerPaint);

            canvas.save();
            canvas.translate(0, textHeight);

            if (i % 6 == 0) {
                String dirString = "";
                switch (i) {
                    case (0): {
                        dirString = northString;
                        int arrowY = 2 * textHeight;
                        canvas.drawLine(px, arrowY, px - 5, 3 * textHeight, markerPaint);
                        canvas.drawLine(px, arrowY, px + 5, 3 * textHeight, markerPaint);
                        break;
                    }
                    case (6):
                        dirString = eastString;
                        break;
                    case (12):
                        dirString = southString;
                        break;
                    case (18):
                        dirString = westString;
                        break;
                }
                canvas.drawText(dirString,cardinalX,cardinalY,textPaint);
            }else if(i%3 ==0){
                String angle = String.valueOf(i*15);
                float angleTextWidth = textPaint.measureText(angle);

                int angleTextX = (int) (px-angleTextWidth/2);
                int angleTextY = py-radius+textHeight;
                canvas.drawText(angle,angleTextX,angleTextY,textPaint);
            }
            canvas.restore();
            canvas.rotate(15,px,py);
        }
        canvas.restore();
    }
}

