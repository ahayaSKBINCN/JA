package com.ahaya.earthquakeviewer.Sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class CustomView extends View {
    Paint mTextPaint;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureHeight = measureHeight(heightMeasureSpec);
        int measureWidth = measureWidth(widthMeasureSpec);
        setMeasuredDimension(measureWidth, measureHeight);
    }


    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //如果没有限制则使用默认大小
        int result = 500;
        // 计算高度
        if (specMode == MeasureSpec.AT_MOST) {
            //在最大尺寸范围内计算控件的理想尺寸；
            //如果控件填充了整个可用控件则返回外部边界值；
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            //如果控件能够符合这些界限，则返回该值；
            result = specSize;
        }

        return result;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //如果没有限制则使用默认大小
        int result = 500;
        // 计算高度
        if (specMode == MeasureSpec.AT_MOST) {
            //在最大尺寸范围内计算控件的理想尺寸；
            //如果控件填充了整个可用控件则返回外部边界值；
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            //如果控件能够符合这些界限，则返回该值；
            result = specSize;
        }
        return result;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果处理了该事件就返回true
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //如果处理了该事件就返回true
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取事件表示的动作类型
        int actionPerformed = event.getAction();
        if(actionPerformed == MotionEvent.ACTION_BUTTON_RELEASE){

        }
        //如果处理了该事件就返回true
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //TODO 绘制可视化界面
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();

        int px = width / 2;
        int py = height / 2;

        mTextPaint.setColor(Color.WHITE);
        String displayText = "Hello World!";
        float textWidth = mTextPaint.measureText(displayText);
        canvas.drawText(displayText, px - textWidth / 2, py, mTextPaint);
    }


}

abstract class OpenGLView extends SurfaceView {

    public OpenGLView(Context context) {
        super(context);
    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OpenGLView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
